package com.qiwo.base.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by Administrator on 2016/8/31.
 */
public class BeisailAnimationView extends View implements View.OnClickListener {

    Paint mPaintBezier;
    Paint mPaintAuxiliary;
    Paint mPaintAuxiliaryText;

    Path mPath = new Path();
    public BeisailAnimationView(Context context) {
        super(context);
    }

    public BeisailAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(2);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(20);

        setOnClickListener(this);
    }

    public BeisailAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View view) {
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);

        mPath.cubicTo(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);

        canvas.drawPoint(mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
        canvas.drawPoint(mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
        canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
        canvas.drawText("辅助点1", mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliaryText);
        canvas.drawText("辅助点2", mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliaryText);

        // 辅助线
        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
        canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
        canvas.drawLine(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
    }

    float mStartPointX;
    float mStartPointY;
    float mEndPointX;
    float mEndPointY;
    float mAuxiliaryOneX;
    float mAuxiliaryOneY;
    float mAuxiliaryTwoX;
    float mAuxiliaryTwoY;

    ValueAnimator mAnimator;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 起点
        mStartPointX = w / 4;
        mStartPointY = h / 4;

        // 终点
        mEndPointX = 3 * w / 4;
        mEndPointY = h / 4;

        mAuxiliaryOneX = mStartPointX;
        mAuxiliaryOneY = mStartPointY;

        mAuxiliaryTwoX = mEndPointX;
        mAuxiliaryTwoY = mEndPointY;

        mAnimator = ValueAnimator.ofFloat(mStartPointY, (float) 3 * h / 4);
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAuxiliaryOneY = (float) valueAnimator.getAnimatedValue();
                mAuxiliaryTwoY = (float) valueAnimator.getAnimatedValue();

                invalidate();
            }
        });

//        // 控制点1
//        mAuxiliaryOneX = w / 4;
//        mAuxiliaryOneY = 3 * h / 4;
//
//        // 控制点2
//        mAuxiliaryTwoX =  3 * w / 4;
//        mAuxiliaryTwoY = 3 * h / 4;


    }
}
