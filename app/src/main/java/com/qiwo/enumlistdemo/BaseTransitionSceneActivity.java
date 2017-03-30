package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import butterknife.Bind;

/**
 * Created by Dou on 2017/3/9.
 */
public class BaseTransitionSceneActivity extends BaseSwipeBackAppcompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.scene_root)
    FrameLayout sceneRoot;
    @Bind(R.id.btn_scene_1)
    Button btnScene1;
    @Bind(R.id.btn_scene_2)
    Button btnScene2;
    @Bind(R.id.btn_scene_3)
    Button btnScene3;
    @Bind(R.id.btn_scene_4)
    Button btnScene4;

    Scene scene_a;
    Scene scene_b;
    Scene scene_c;
    Scene scene_d;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {
        initScene();
        btnScene1.setOnClickListener(this);
        btnScene2.setOnClickListener(this);
        btnScene3.setOnClickListener(this);
        btnScene4.setOnClickListener(this);
    }

    private void initScene() {

        scene_a = Scene.getSceneForLayout(sceneRoot, R.layout.basic_scene_a, this);
        scene_b = Scene.getSceneForLayout(sceneRoot, R.layout.basic_scene_b, this);
        scene_c = Scene.getSceneForLayout(sceneRoot, R.layout.basic_scene_c, this);
        scene_d = Scene.getSceneForLayout(sceneRoot, R.layout.basic_scene_d, this);

        TransitionManager.go(scene_a);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_scene;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.btn_scene_1:
                TransitionManager.go(scene_a);
                break;
            case  R.id.btn_scene_2:
                TransitionManager.go(scene_b);
                break;
            case  R.id.btn_scene_3:
                TransitionManager.go(scene_c);
                break;
            case  R.id.btn_scene_4:
                TransitionManager.go(scene_d);
                break;
        }
    }
}
