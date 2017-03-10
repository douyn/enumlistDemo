package com.qiwo.enumlistdemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.Settings;

import com.qiwo.services.NetworkChangeReceiver;

/**
 * Created by Administrator on 2016/9/27.
 */

public class PreferenceSettingFragment extends android.preference.PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        // 刷新用户信息
        flushUserInfo();
    }

    /**
     *
     */
    public boolean getNetWorkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                return true;
           }
        }
        return false;
    }

    /**
     * 属性用户信息
     */
    private void flushUserInfo() {
        Preference cur_user = findPreference("cur_user");
        Preference log_out = findPreference("log_out");
        boolean isLogin = false;

        if (isLogin) {
            cur_user.setTitle("你好，setting");
            cur_user.setSummary("你当前的积分为：40");
            cur_user.setIntent(null);

            log_out.setEnabled(true);
        } else{
            log_out.setEnabled(false);
            if(getNetWorkAvailable()){
                cur_user.setTitle("未登录");
                cur_user.setSummary("点击登录");
                cur_user.setIntent(new Intent(getActivity(), SchemeActivity.class));
            } else{
                cur_user.setTitle("网路链接失败");
                cur_user.setSummary("点击打开网络连接");
                cur_user.setIntent(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
            }
        }
    }

    // 当fragment显示时去监听网络状况 TODO
    NetworkChangeReceiver receiver;
    @Override
    public void onStart() {
        super.onStart();
        receiver = new NetworkChangeReceiver();
        getActivity().registerReceiver(receiver,
                new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    // 当页面不显示时取消监听 TODO
    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

}
