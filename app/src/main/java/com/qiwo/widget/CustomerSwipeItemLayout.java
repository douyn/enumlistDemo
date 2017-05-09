package com.qiwo.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import java.util.LinkedHashMap;

/**
 * Created by Dou on 2017/3/31.
 */

public class CustomerSwipeItemLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private int mTouchSlop;
    private int mVelcity;

    private boolean mSwipeEnable = true;

    private View mCurrentMenu;

    /**
     * mMenus : 存放菜单的集合
     * key :
     */
    LinkedHashMap<Integer, View> mMenus = new LinkedHashMap<>();

    public CustomerSwipeItemLayout(Context context) {
        this(context, null);
    }

    public CustomerSwipeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomerSwipeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop(); // 获取能够手势滑动的距离
        mVelcity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity(); // 获取进行Fling手势的最小速度
        mViewDragHelper = ViewDragHelper.create(this, 1, new DragItemCallBack());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        addMenu();
    }

    /**
     * 当菜单被ContentView遮住的时候，要设置菜单为Invisible，防止已隐藏的菜单接收到点击事件。
     */
    public void addMenu () {
        View contentView = getContentView();
        if (contentView != null) {
            int contentLeft = contentView.getLeft();
            if (contentLeft == 0) {
                for (View view : mMenus.values()) {
                    if (view.getVisibility() != INVISIBLE) {
                        view.setVisibility(INVISIBLE);
                    }
                }
            } else {
                if (mCurrentMenu != null && mCurrentMenu.getVisibility() == INVISIBLE) {
                    mCurrentMenu.setVisibility(VISIBLE);
                }
            }
        }
    }

    /**
     * 获取contentview，最上层显示的View即为contentview
     */
    private View getContentView () {
        return getChildAt(getChildCount() - 1);
    }

    class DragItemCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // menu和content都可以抓取，因为在menu的宽度为MatchParent的时候，是无法点击到content的
            return child == getContentView() || mMenus.containsValue(child);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}
