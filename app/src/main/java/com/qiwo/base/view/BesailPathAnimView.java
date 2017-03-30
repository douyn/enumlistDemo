package com.qiwo.base.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.qiwo.utils.BezierEvaluator;

/**
 * Created by Administrator on 2016/9/5.
 */
public class BesailPathAnimView extends View implements View.OnClickListener{

    Path mPath;
    Paint mPointPaint;
    Paint mBesailPaint;

    float mStartPoaintX = 0;
    float mStartPoaintY = 0;
    float mEndPoaintX = 0;
    float mEndPoaintY = 0;
    float mMovePoaintX = 0;
    float mMovePoaintY = 0;
    float mControlPoaintX = 0;
    float mControlPoaintY = 0;

    float mPrePointX = 0;
    float mPrePointY = 0;
    float mMoveControlPoaintX = 0;
    float mMoveControlPoaintY = 0;

    ValueAnimator mAnimator;
    public BesailPathAnimView(Context context) {
        super(context);
    }

    public BesailPathAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBesailPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        mBesailPaint.setStyle(Paint.Style.STROKE);
        mBesailPaint.setStrokeWidth(5);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPoaintX = 100;
        mStartPoaintY = 100;
        mEndPoaintX = 600;
        mEndPoaintY = 600;
        mMovePoaintX = mStartPoaintX;
        mMovePoaintY = mStartPoaintY;
        mControlPoaintX = 500;
        mControlPoaintY = 0;

        mPrePointX = mStartPoaintX;
        mPrePointY = mStartPoaintY;

        mPath = new Path();
        setOnClickListener(this);
    }

    public BesailPathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    long lastClickTime = 0l;

    @Override
    public void onClick(View view) {
        long cur_time = System.currentTimeMillis();
        if(cur_time - lastClickTime < 3000){
            return;
        }

        lastClickTime = cur_time;
        mPath.reset();
        mPrePointX = mStartPoaintX;
        mPrePointY = mStartPoaintY;
        mAnimator = ValueAnimator.ofObject(new BezierEvaluator(new PointF(mControlPoaintX, mControlPoaintY)),
                new PointF(mStartPoaintX, mStartPoaintY),
                new PointF(mEndPoaintX, mEndPoaintY));

        mAnimator.setDuration(1500);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pf = (PointF) valueAnimator.getAnimatedValue();
                mMovePoaintX = pf.x;
                mMovePoaintY = pf.y;
                if (mMovePoaintX == mStartPoaintX && mMovePoaintY == mStartPoaintY) {
                    mPath.moveTo(mStartPoaintX, mStartPoaintY);
                } else{
                    mMoveControlPoaintX = (mPrePointX + mMovePoaintX) / 2;
                    mMoveControlPoaintY = (mPrePointY + mMovePoaintY) / 2;
                    mPath.quadTo(mPrePointX, mPrePointY, mMoveControlPoaintX, mMoveControlPoaintY);
                    mPrePointY = mMovePoaintY;
                    mPrePointX = mMovePoaintX;
                }
                invalidate();
            }
        });
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.reset();
        canvas.drawCircle(mStartPoaintX, mStartPoaintY, 30, mPointPaint);
        canvas.drawCircle(mEndPoaintX, mEndPoaintY, 30, mPointPaint);

//        mPath.moveTo(mStartPoaintX, mStartPoaintY);
//        mPath.quadTo(mControlPoaintX, mControlPoaintY, mEndPoaintX, mEndPoaintY);
        canvas.drawPath(mPath, mBesailPaint);

        canvas.drawCircle(mMovePoaintX, mMovePoaintY, 30, mPointPaint);
    }
}
