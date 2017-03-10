package com.qiwo.utils;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by Administrator on 2016/9/5.
 */
public class BezierEvaluator implements TypeEvaluator<PointF>{

    PointF mControlPoint;

    public BezierEvaluator(PointF controler){
        mControlPoint = controler;
    }

    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        return BezierUtils.CalculateBezierPointForQuadratic(v, pointF, mControlPoint, t1);
    }
}

