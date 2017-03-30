package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wang.avi.AVLoadingIndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AVLoadingActivity extends AppCompatActivity {

    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.avloadingIndicatorView)
    AVLoadingIndicatorView avloadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avloading);
        ButterKnife.bind(this);
        onClick(btn);
        onClick(avloadingIndicatorView);
    }

    @OnClick({R.id.btn, R.id.avloadingIndicatorView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                avloadingIndicatorView.setVisibility(View.VISIBLE);
                break;
            case R.id.avloadingIndicatorView:
                avloadingIndicatorView.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
