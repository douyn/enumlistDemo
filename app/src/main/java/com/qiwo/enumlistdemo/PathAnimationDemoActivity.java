package com.qiwo.enumlistdemo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/31.
 */
public class PathAnimationDemoActivity extends BaseActivity {
    @Bind(R.id.btn_drawline)
    Button btnDrawline;
    @Bind(R.id.btn_changecircular)
    Button btnChangecircular;
    @Bind(R.id.btn_drawpathanim)
    Button btnDrawpathanim;
    @Bind(R.id.btn_bolang)
    Button btnBolang;
    @Bind(R.id.qq_demo)
    Button qqDemo;

    @Override
    int getLayoutId() {
        return R.layout.activity_besal_path;
    }

    @OnClick({R.id.btn_drawline, R.id.btn_changecircular, R.id.btn_drawpathanim, R.id.btn_bolang, R.id.qq_demo})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_drawline:
                intent.setClass(this, DrawLineActivity.class);
                break;
            case R.id.btn_changecircular:
                intent.setClass(this, ChangeCircularActivity.class);
                break;
            case R.id.btn_drawpathanim:
                intent.setClass(this, DrawPathAnimActivity.class);
                break;
            case R.id.btn_bolang:
                intent.setClass(this, BoLangActivity.class);
                break;
            case R.id.qq_demo:
                intent.setClass(this, QQDeleteDemoActivity.class);
                break;
        }
        startActivity(intent);
    }
}
