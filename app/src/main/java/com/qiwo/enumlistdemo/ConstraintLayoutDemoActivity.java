package com.qiwo.enumlistdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;

/**
 * Created by Administrator on 2017/2/5.
 */
public class ConstraintLayoutDemoActivity extends AppCompatActivity {

    ConstraintSet constraintSetOne;
    ConstraintSet constraintSetTwo;
    ConstraintSet constraintSetThree;
    ConstraintSet constraintSetfour;
    ConstraintSet constraintSetFive;
    ConstraintSet constraintSetSix;
    ConstraintSet constraintSetSeven;

    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_constraint_layout);

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        constraintSetOne = new ConstraintSet();
        constraintSetTwo = new ConstraintSet();
        constraintSetThree = new ConstraintSet();
        constraintSetfour = new ConstraintSet();
        constraintSetFive = new ConstraintSet();
        constraintSetSix = new ConstraintSet();
        constraintSetSeven = new ConstraintSet();

        constraintSetOne.clone(mConstraintLayout);
        constraintSetTwo.clone(mConstraintLayout);
        constraintSetThree.clone(mConstraintLayout);
        constraintSetfour.clone(mConstraintLayout);
        constraintSetFive.clone(mConstraintLayout);
        constraintSetSix.clone(mConstraintLayout);
        constraintSetSeven.clone(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void clickApply (View v) {
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        constraintSetTwo.setMargin(R.id.button1, ConstraintSet.START, 8);
        constraintSetTwo.applyTo(mConstraintLayout);
    }

    public void clickReset (View v) {
        constraintSetOne.applyTo(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void click_center (View v) {

        TransitionManager.beginDelayedTransition(mConstraintLayout);
        constraintSetThree.centerHorizontally(R.id.button1, R.id.guideline);
        constraintSetThree.centerHorizontally(R.id.button2, R.id.guideline);
        constraintSetThree.centerHorizontally(R.id.button3, R.id.guideline);

        constraintSetThree.applyTo(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void click_center_parent (View v) {

        TransitionManager.beginDelayedTransition(mConstraintLayout);

        constraintSetfour.centerHorizontally(R.id.button3, R.id.guideline);
        constraintSetfour.centerVertically(R.id.button3, R.id.guideline_horizontal);
        constraintSetfour.applyTo(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void click_change_width (View v) {
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        constraintSetFive.constrainWidth(R.id.button1, 600);
        constraintSetFive.constrainWidth(R.id.button2, 600);
        constraintSetFive.constrainWidth(R.id.button3, 600);

        constraintSetFive.constrainHeight(R.id.button1, 100);
        constraintSetFive.constrainHeight(R.id.button2, 100);
        constraintSetFive.constrainHeight(R.id.button3, 100);

        constraintSetFive.applyTo(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void click_scrren (View v) {
        TransitionManager.beginDelayedTransition(mConstraintLayout);

        constraintSetSix.setVisibility(R.id.button2, ConstraintSet.GONE);
        constraintSetSix.setVisibility(R.id.button3, ConstraintSet.GONE);

        constraintSetSix.clear(R.id.button1);
        constraintSetSix.connect(R.id.button1, ConstraintSet.LEFT, R.id.constraint_layout, ConstraintSet.LEFT, 0);
        constraintSetSix.connect(R.id.button1, ConstraintSet.TOP, R.id.constraint_layout, ConstraintSet.TOP, 0);
        constraintSetSix.connect(R.id.button1, ConstraintSet.RIGHT, R.id.constraint_layout, ConstraintSet.RIGHT, 0);
        constraintSetSix.connect(R.id.button1, ConstraintSet.BOTTOM, R.id.constraint_layout, ConstraintSet.BOTTOM, 0);

        constraintSetSix.applyTo(mConstraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void center_top_link (View v) {
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        constraintSetSeven.clear(R.id.button1);
        constraintSetSeven.clear(R.id.button2);
        constraintSetSeven.clear(R.id.button3);

        constraintSetSeven.constrainWidth(R.id.button1,ConstraintSet.WRAP_CONTENT);
        constraintSetSeven.constrainWidth(R.id.button2,ConstraintSet.WRAP_CONTENT);
        constraintSetSeven.constrainWidth(R.id.button3,ConstraintSet.WRAP_CONTENT);
        constraintSetSeven.constrainHeight(R.id.button1,ConstraintSet.WRAP_CONTENT);
        constraintSetSeven.constrainHeight(R.id.button2,ConstraintSet.WRAP_CONTENT);
        constraintSetSeven.constrainHeight(R.id.button3,ConstraintSet.WRAP_CONTENT);

        constraintSetSeven.connect(R.id.button1, ConstraintSet.LEFT, R.id.constraint_layout, ConstraintSet.LEFT, 0);
        constraintSetSeven.connect(R.id.button1, ConstraintSet.TOP, R.id.constraint_layout, ConstraintSet.TOP, 0);
        constraintSetSeven.connect(R.id.button3, ConstraintSet.RIGHT, R.id.constraint_layout, ConstraintSet.RIGHT, 0);
        constraintSetSeven.connect(R.id.button3, ConstraintSet.TOP, R.id.constraint_layout, ConstraintSet.TOP, 0);

        constraintSetSeven.connect(R.id.button2, ConstraintSet.LEFT, R.id.button1, ConstraintSet.RIGHT, 0);
        constraintSetSeven.connect(R.id.button1, ConstraintSet.RIGHT, R.id.button2, ConstraintSet.LEFT, 0);
        constraintSetSeven.connect(R.id.button2, ConstraintSet.RIGHT, R.id.button3, ConstraintSet.LEFT, 0);
        constraintSetSeven.connect(R.id.button3, ConstraintSet.LEFT, R.id.button2, ConstraintSet.RIGHT, 0);

        constraintSetSeven.createHorizontalChain(R.id.button1, R.id.button3, new int[]{R.id.button1, R.id.button3}, null, ConstraintWidget.CHAIN_SPREAD_INSIDE);
        constraintSetSeven.applyTo(mConstraintLayout);
    }
}
