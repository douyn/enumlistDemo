package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HealthCalStepActivity extends AppCompatActivity {

    @Bind(R.id.donut_progress)
    DonutProgress donutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_cal_step);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int max = 2000;
        int now = new Random().nextInt(1000) + 1000;

        donutProgress.setTextSize(100f);
        donutProgress.setText(now+"");
        donutProgress.setProgress(100 * now / max);
    }
}
