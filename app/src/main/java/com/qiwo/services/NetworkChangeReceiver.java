package com.qiwo.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/9/27.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAvailable = getNetWorkAvailable(context);
        NetworkMonitor.setNetworkStatus(isAvailable);
    }

    /**
     *
     */
    public boolean getNetWorkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;
    }
}

