package com.qiwo.enumlistdemo;

import android.view.View;
import android.widget.Button;

import com.liuguangqiang.ipicker.IPicker;
import com.liuguangqiang.ipicker.events.IPickerEvent;
import com.liuguangqiang.ipicker.internal.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/12/10.
 */

public class IPickDemoActivity extends BaseActivity {
    @Bind(R.id.btn_photo)
    Button btnPhoto;

    @Override
    int getLayoutId() {
        return R.layout.activity_ipicker_demo;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.initView();

        IPicker.setLimit(1);
        IPicker.setCropEnable(true);
        IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
            @Override
            public void onSelected(List<String> paths) {
                Logger.i("on delected");
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IPicker.open(IPickDemoActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(IPickerEvent event) {
    }


}
