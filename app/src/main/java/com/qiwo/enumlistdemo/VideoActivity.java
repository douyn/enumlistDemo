package com.qiwo.enumlistdemo;

import android.app.Activity;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends Activity {

    JCVideoPlayerStandard custom_standard;
    JCVideoPlayerSimple custom_simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        custom_simple = (JCVideoPlayerSimple) findViewById(R.id.custom_videoplayer_simple);
        custom_standard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100) //缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
        String url = "http://2449.vod.myqcloud.com/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4";
        String img = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640";
        String title = "暴走大事件";
        Map<String, String> headData = new HashMap<>();
        headData.put("key1", "value1");
        headData.put("key2", "value2");
        custom_simple.setUp("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8", headData);

        custom_standard.setUp(url, title);
        ImageLoader.getInstance().displayImage(img, custom_standard.thumbImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    /*private final static String TAG = "MainActivity";
    private Context mContext = this;
    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;
    private MediaPlayer mediaPlayer = null;
    private ImageView imageView_main_show = null;

    // 自定义的控制条及其上的控件
    private View controllerView;
    private PopupWindow popupWindow;

    private ImageView imageView_play;
    private ImageView imageView_fullscreen;
    private SeekBar seekBar;
    private TextView textView_playTime;
    private TextView textView_duration;
    private String filePath = null;

    private float densityRatio = 1.0f; // 密度比值系数（密度比值：一英寸中像素点除以160）

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            // 又回到了主线程
            showOrHiddenController();
        }
    };

    private MyVideoBroadcastReceiver receiver = null;

    // 设置定时器
    private Timer timer = null;
    private final static int WHAT = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT:
                    if (mediaPlayer != null) {
                        int currentPlayer = mediaPlayer.getCurrentPosition();
                        if (currentPlayer > 0) {
                            mediaPlayer.getCurrentPosition();
                            textView_playTime.setText(formatTime(currentPlayer));

                            // 让seekBar也跟随改变
                            int progress = (int) ((currentPlayer / (float) mediaPlayer
                                    .getDuration()) * 100);

                            seekBar.setProgress(progress);
                        } else {
                            textView_playTime.setText("00:00");
                            seekBar.setProgress(0);
                        }
                    }

                    break;

                default:
                    break;
            }
        };
    };

    // 自动隐藏自定义播放器控制条的时间
    private static final int HIDDEN_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();

        initMediaPlayer();

        initController();

        // 动态注册广播接受者
        receiver = new MyVideoBroadcastReceiver();
        registerReceiver(receiver, new IntentFilter(
                "com.amy.day43_03_SurfaceViewMediaPlayer"));
    }

    private String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    private void initController() {

        controllerView = getLayoutInflater().inflate(
                R.layout.popupwindow, null);

        // 初始化popopWindow
        popupWindow = new PopupWindow(controllerView,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

        imageView_play = (ImageView) controllerView
                .findViewById(R.id.imageView_play);
        imageView_fullscreen = (ImageView) controllerView
                .findViewById(R.id.imageView_fullscreen);

        seekBar = (SeekBar) controllerView.findViewById(R.id.seekbar);

        textView_playTime = (TextView) controllerView
                .findViewById(R.id.textView_playtime);
        textView_duration = (TextView) controllerView
                .findViewById(R.id.textView_totaltime);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            // 表示手指拖动seekbar完毕，手指离开屏幕会触发以下方法
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 让计时器延时执行
                handler.postDelayed(r, HIDDEN_TIME);
            }

            // 在手指正在拖动seekBar，而手指未离开屏幕触发的方法
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 让计时器取消计时
                handler.removeCallbacks(r);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    int playtime = progress * mediaPlayer.getDuration() / 100;
                    mediaPlayer.seekTo(playtime);
                }

            }
        });

        // 点击播放的时候,判断是播放还是暂停
        imageView_play.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (imageView_main_show.getVisibility() == View.VISIBLE) {
                    imageView_main_show.setVisibility(View.GONE);
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imageView_play.setImageResource(R.drawable.delete);
                } else {
                    mediaPlayer.start();
                    imageView_play.setImageResource(R.drawable.delete);

                }

            }
        });

        // 实现全屏和退出全屏(内容物横竖屏,不是屏幕的横竖屏)
        imageView_fullscreen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    imageView_fullscreen
                            .setImageResource(R.drawable.full_screen);

                    // 重新设置surfaceView的高度和宽度
                    surfaceView.getLayoutParams().width = LayoutParams.MATCH_PARENT;
                    surfaceView.getLayoutParams().height = (int) (260 * densityRatio);
                } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    imageView_fullscreen
                            .setImageResource(R.drawable.bg_bottum);

                    surfaceView.getLayoutParams().width = LayoutParams.MATCH_PARENT;
                    surfaceView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
                }

                surfaceView.setLayoutParams(surfaceView.getLayoutParams());
            }
        });
    }

    private void showOrHiddenController() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            // 将dp转换为px
            int controllerHeightPixel = (int) (densityRatio * 50);
            popupWindow.showAsDropDown(surfaceView, 0, -controllerHeightPixel);
            // 延时执行
            handler.postDelayed(r, HIDDEN_TIME);
        }
    }

    private void initMediaPlayer() {
        filePath = Environment.getExternalStorageDirectory()
                + "Pictures/vh__video/"+ "123.mp4";

        if (mediaPlayer == null) {
            // 1,创建MediaPlay对象
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
                // mediaPlayer.start();
                mediaPlayer.setLooping(false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // 表示准备完成，设置总的时长，使用时间格式化工具

                // String duration = mediaPlayer.getDuration() ;
                textView_duration.setText(formatTime(mediaPlayer.getDuration()));
                // 初始化定时器
                timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.sendEmptyMessage(WHAT);
                    }
                }, 0, 1000);
            }
        });

        mediaPlayer.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.reset();

                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 发送广播，播放下一首歌曲

                Intent intent = new Intent();
                intent.setAction("com.amy.day43_03_SurfaceViewMediaPlayer");
                sendBroadcast(intent);
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        densityRatio = getResources().getDisplayMetrics().density; // 表示获取真正的密度

        imageView_main_show = (ImageView) findViewById(R.id.imageView_main_play);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView_main);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(new Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                if (mediaPlayer != null) {

                    mediaPlayer.setDisplay(surfaceHolder);
                    // mediaPlayer.start() ;

                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }
        });

        // 设置屏幕的触摸监听
        surfaceView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 表示在点击的瞬间就显示控制条
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showOrHiddenController();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    *//**
     * 设置控件的监听事件
     *
     * @param v
     *//*
    public void clickButton(View v) {
        switch (v.getId()) {
            case R.id.imageView_main_play:

                imageView_main_show.setVisibility(View.GONE);
                mediaPlayer.start();

                break;

            default:
                break;
        }
    }

   *//* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*//*

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        unregisterReceiver(receiver);
        timer.cancel();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        handler.removeCallbacksAndMessages(null);
    }

    class MyVideoBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    "com.amy.day43_03_SurfaceViewMediaPlayer")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.delete)
                        .setTitle("提示")
                        .setMessage("视屏播放完毕，是否播放")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        mediaPlayer.reset();
                                        try {
                                            mediaPlayer.setDataSource(filePath);
                                            mediaPlayer.prepare();
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        mediaPlayer.setLooping(false);

                                        mediaPlayer.start();
                                    }
                                }).show();

            }
        }

    }*/

}