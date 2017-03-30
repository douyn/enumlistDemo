package com.qiwo.enumlistdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by l on 2016/5/10.
 */
public class RainbowBar extends View {

    //progress bar color
    int barColor = Color.parseColor("#1E88E5");
    //every bar segment width
    int hSpace = 80;
    //every bar segment height
    int vSpace = 4;
    //space among bars
    int space = 10;
    float startX = 0;
    float delta = 10f;
    Paint mPaint;

    public RainbowBar(Context context) {
        super(context, null);
    }

    public RainbowBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RainbowBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.rainbowbar, 0, 0);
        barColor = ta.getColor(R.styleable.rainbowbar_rainbowbar_color, barColor);
        hSpace = ta.getDimensionPixelSize(R.styleable.rainbowbar_rainbowbar_hspace, hSpace);
        vSpace = ta.getDimensionPixelSize(R.styleable.rainbowbar_rainbowbar_vspace, vSpace);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
        mPaint.setStrokeWidth(vSpace);
    }

    //draw be invoke numbers.
    int index = 0;

    @Override
    public void draw(Canvas canvas) {
        try{
            super.draw(canvas);
        }catch(java.lang.NullPointerException e){
            //you can log if you want to you can leave your friends behind
            Log.e("mytag","MyCustomView::draw():"+e);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //get screen width
        float sw = this.getMeasuredWidth();
        if (startX >= sw + (hSpace + space) - (sw % (hSpace + space))) {
            startX = 0;
        } else {
            startX += delta;
        }
        float start = startX;
        // draw latter parse
        while (start < sw) {
            canvas.drawLine(start, 5, start + hSpace, 5, mPaint);
            start += (hSpace + space);
        }

        start = startX - space - hSpace;

        // draw front parse
        while (start >= -hSpace) {
            canvas.drawLine(start, 5, start + hSpace, 5, mPaint);
            start -= (hSpace + space);
        }
        if (index >= 700000) {
            index = 0;
        }
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
