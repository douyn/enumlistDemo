package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/16.
 */
public class SwipeCardViewDemoActivity extends BaseSwipeBackAppcompatActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipFlingView)
    SwipeFlingAdapterView swipFlingView;

    ArrayList<String> arrayList;
    
    @Override
    protected void doBusiness() {
    }

    @Override
    protected void initParms(Bundle bundle) {
    }

    @Override
    protected void initViewAndListener() {
        toolbarTitle.setText("探探");

        swipeFlingConfig();
    }

    private void swipeFlingConfig() {
        arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("IOS");
        arrayList.add("C++");
        arrayList.add("WEB");

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.item_swipe_card, R.id.textView, arrayList);
        swipFlingView.setAdapter(adapter);

        swipFlingView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(MotionEvent event, View v, Object dataObject) {
                showToast(dataObject.toString());
            }
        });

        swipFlingView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                arrayList.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(SwipeCardViewDemoActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(SwipeCardViewDemoActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                arrayList.add("XML");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float progress, float scrollXProgress) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_path_demo;
    }
}
