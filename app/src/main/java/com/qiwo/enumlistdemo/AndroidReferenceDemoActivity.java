package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Dou on 2017/3/30.
 */
public class AndroidReferenceDemoActivity extends BaseSwipeBackAppcompatActivity {
    @Bind(R.id.button21)
    Button button21;
    @Bind(R.id.button22)
    Button button22;
    @Bind(R.id.button23)
    Button button23;
    @Bind(R.id.button24)
    Button button24;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reference_demo;
    }

    @OnClick({R.id.button21, R.id.button22, R.id.button23, R.id.button24})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button21: // 强引用
                A obj1 = new A();
                break;
            case R.id.button22: // 软引用
                A obj2 = new A();
                SoftReference<A> sf = new SoftReference<A>(obj2);

                obj2 = null;

                if (sf.get() == null) {
                    Log.d(TAG, "onClick: >>> " + "obj2 进入垃圾回收流程");
                } else {
                    Log.e(TAG, "onClick: >>> " + "obj2 没有被回收 " + sf.get());
                }

                System.gc();

                if (sf.get() == null) {
                    Log.d(TAG, "onClick: >>> " + "obj2 进入垃圾回收流程");
                } else {
                    Log.e(TAG, "onClick: >>> " + "obj2 没有被回收 " + sf.get());
                }

                break;
            case R.id.button23: // 弱引用

                B obj3 = new B();
                WeakReference<B> wf = new WeakReference<B>(obj3);

                obj3 = null;

                if (wf.get() == null) {
                    Log.i(TAG, "onClick: >>> " + "obj3 进入垃圾回收流程");
                } else {
                    Log.e(TAG, "onClick: >>> " + "obj3 没有被回收 " + wf.get());
                }

                System.gc();

                if (wf.get() == null) {
                    Log.i(TAG, "onClick: >>> " + "obj3 进入垃圾回收流程");
                } else {
                    Log.e(TAG, "onClick: >>> " + "obj3 没有被回收 " + wf.get());
                }
                break;
            case R.id.button24: // 虚引用
                A obj4 = new A();
                break;
        }
    }

    class A {

    }

    class B {

    }
}
