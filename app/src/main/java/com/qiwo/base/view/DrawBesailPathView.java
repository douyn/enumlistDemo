package com.qiwo.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2016/8/31.
 */
public class DrawBesailPathView extends View {

    private Paint mPaint;
    private Path mPath;
    private float offset = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public DrawBesailPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawBesailPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        mPath = new Path();
    }

    public DrawBesailPathView(Context context) {
        super(context);
    }

    float preX = 0;
    float preY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                float downX = event.getX();
                float downY = event.getY();
                mPath.moveTo(downX, downY);
                preX = downX;
                preY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();

                float contrX = (moveX + preX)/2;
                float contrY = (moveY + preY)/2;

                //                mPath.lineTo(moveX, moveY);
                float offsetX = Math.abs(moveX - preX);
                float offsetY = Math.abs(moveY - preY);

                if (offsetX > offset || offsetY > offset) {
                    mPath.quadTo(preX, preY, contrX, contrY);
                }

                preX = moveX;
                preY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }
}
