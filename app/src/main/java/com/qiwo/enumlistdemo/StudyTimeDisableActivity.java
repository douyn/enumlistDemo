package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.handmark.pulltorefresh.library.SwipeListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StudyTimeDisableActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    SwipeListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time_disable);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }
}
