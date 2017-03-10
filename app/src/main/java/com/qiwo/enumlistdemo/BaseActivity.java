package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by l on 2016/7/18.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private boolean hasActionBar() {
        return true;
    };

    protected void initData(){};

    protected void initView(){};

    abstract int getLayoutId();

}
