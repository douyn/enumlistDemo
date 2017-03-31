package com.qiwo.enumlistdemo;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Created by Dou on 2017/3/31.
 */
public class DragViewHelperDemoActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    int getLayoutId() {
        return R.layout.activity_dragview_demo;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
