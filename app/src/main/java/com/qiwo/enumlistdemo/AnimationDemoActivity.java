package com.qiwo.enumlistdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/23.
 */
public class AnimationDemoActivity extends BaseSwipeBackAppcompatActivity {
    @Bind(R.id.id_image)
    ImageView idImage;
    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {
        PropertyValuesHolder holder_alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1);
        PropertyValuesHolder hodler_scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder hodler_scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1);

        ObjectAnimator animator_iv = ObjectAnimator.ofPropertyValuesHolder(idImage, holder_alpha, hodler_scaleX, hodler_scaleY);
        ObjectAnimator animator_tv = ObjectAnimator.ofPropertyValuesHolder(tvText, holder_alpha, hodler_scaleX, hodler_scaleY);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_iv, animator_tv);
        set.setDuration(2000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(SwipeCardViewDemoActivity.class);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        set.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animation_sample;
    }
}
