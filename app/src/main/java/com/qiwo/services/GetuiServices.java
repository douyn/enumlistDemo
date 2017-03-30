package com.qiwo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.qiwo.enumlistdemo.R;

/**
 * Created by Administrator on 2016/9/2.
 */
public class GetuiServices extends Service {
    private static int notify_id = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e("dou", "Service create");
        PushManager.getInstance().initialize(this.getApplicationContext());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1000; i++) {
//
//                    generateNotification(GetuiServices.this,
//                            i+"",
//                            i+"");
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        //通过代码的方式动态注册MyBroadcastReceiver
//        GetuiPushBroadcast receiver=new GetuiPushBroadcast();

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();

                switch (bundle.getInt(PushConsts.CMD_ACTION)) {
                    case PushConsts.GET_CLIENTID:

                        String cid = bundle.getString("clientid");
                        Log.e("dou", cid);
                        // TODO:处理cid返回
                        break;
                    case PushConsts.GET_MSG_DATA:

                        String taskid = bundle.getString("taskid");
                        String messageid = bundle.getString("messageid");
                        byte[] payload = bundle.getByteArray("payload");
                        if (payload != null) {
                            String data = new String(payload);
                            // TODO:接收处理透传（payload）数据
                            Log.e("dou", data);

                            Log.e("dou", "创建通知");
//                            generateNotification(context, "title", data, data);
                        }

                        break;
                    default:
                        break;
                }
            }
        };

        IntentFilter filter=new IntentFilter();
        filter.addAction("com.igexin.sdk.action.${GETUI_APP_ID}");
        //注册receiver
        registerReceiver(br, filter);
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("dou", "Service destroy");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("dou", "Service destroy");
        super.onDestroy();
    }

    private static void generateNotification(Context context, String title, String message) {
        Log.e("dou", "notification");
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(message);
//        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
//        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icon_meme);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_meme));
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(notify_id++, builder.build());
    }
}
