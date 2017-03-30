package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/6.
 */
public class TransitionDemoActivity extends BaseSwipeBackAppcompatActivity {

    @Bind(R.id.button7)
    Button button7;
    @Bind(R.id.button13)
    Button button13;
    @Bind(R.id.button14)
    Button button14;
    @Bind(R.id.button15)
    Button button15;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transition;
    }

    @OnClick({R.id.button7, R.id.button13, R.id.button14, R.id.button15})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button7:
                startActivity(BaseTransitionActivity.class);
                break;
            case R.id.button13:
                break;
            case R.id.button14:
                break;
            case R.id.button15:
                break;
        }
    }
}
