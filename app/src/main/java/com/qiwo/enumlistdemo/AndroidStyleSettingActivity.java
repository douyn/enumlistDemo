package com.qiwo.enumlistdemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27.
 */
public class AndroidStyleSettingActivity extends Activity {
    @Bind(R.id.fragment_setting)
    FrameLayout fragmentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_style_setting);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_setting, new PreferenceSettingFragment());
        fragmentTransaction.commit();
    }
}
