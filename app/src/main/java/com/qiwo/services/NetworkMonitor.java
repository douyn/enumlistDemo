package com.qiwo.services;

/**
 * Created by Administrator on 2016/9/27.
 */

public class NetworkMonitor {

    private boolean networkAvailable;
    private NetworkMonitor(){
    }

    private static NetworkMonitor instance = new NetworkMonitor();

    public static void setNetworkStatus(boolean networkStatus){
        instance.networkAvailable = networkStatus;
    }

    public static boolean isNetworkAvailable(){
        return instance.networkAvailable;
    }
}
