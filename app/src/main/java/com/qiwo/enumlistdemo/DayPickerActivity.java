package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by l on 2016/7/4.
 */
public class DayPickerActivity extends AppCompatActivity implements NumberPickerView.OnScrollListener, NumberPickerView.OnValueChangeListener {

    private final String TAG = this.getClass().getSimpleName();
    TextView tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daypicker_view);
        NumberPickerView picker = (NumberPickerView) this.findViewById(R.id.picker);
        tv_show = (TextView) findViewById(R.id.tv_show);
        picker.setOnScrollListener(this);
        picker.setOnValueChangedListener(this);
        picker.refreshByNewDisplayedValues(getResources().getStringArray(R.array.hour_display));
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {
        Log.e(TAG, "on scroll");
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, final int newVal) {
        Log.e(TAG, "on value changed");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_show.setText("当前选中的数字是:" + newVal);
            }
        });
    }
}
