package com.qiwo.enumlistdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import butterknife.Bind;

/**
 * Created by Dou on 2017/3/8.
 */
public class BaseTransitionActivity extends BaseSwipeBackAppcompatActivity implements View.OnClickListener {

    @Bind(R.id.scene_root)
    ViewGroup scene_root;
    @Bind(R.id.view_change)
    ImageView view_change;
    @Bind(R.id.btn_change)
    Button btn_change;
    @Bind(R.id.btn_change_size)
    Button btn_change_size;
    @Bind(R.id.btn_change_position)
    Button btn_change_position;

    Scene scene_a;
    Scene scene_b;

    boolean isSceneB;
    boolean sizeChanged;
    boolean positionChanged;

    int saveSize;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViewAndListener() {
        setupWindowAnimations();
        btn_change.setOnClickListener(this);
        btn_change_size.setOnClickListener(this);
        btn_change_position.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_transition;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                startActivity(BaseTransitionSceneActivity.class);
                break;
            case R.id.btn_change_size:
                TransitionManager.beginDelayedTransition(scene_root);

                ViewGroup.LayoutParams params = view_change.getLayoutParams();
                if (sizeChanged) {
                    params.width = 100;
                    params.height = 100;
                } else {
                    params.width = 200;
                    params.height = 200;
                }
                sizeChanged = !sizeChanged;
                view_change.setLayoutParams(params);
                break;
            case R.id.btn_change_position:
                TransitionManager.beginDelayedTransition(scene_root);
                LinearLayout.LayoutParams layoutParams_poi = (LinearLayout.LayoutParams) view_change.getLayoutParams();

                if (positionChanged) {
                    layoutParams_poi.gravity = Gravity.LEFT;
                } else {
                    layoutParams_poi.gravity = Gravity.CENTER_HORIZONTAL;
                }

                positionChanged = !positionChanged;
                view_change.setLayoutParams(layoutParams_poi);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().setReenterTransition(new android.transition.Fade());
    }
}
