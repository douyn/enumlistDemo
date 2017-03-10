package com.qiwo.enumlistdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

/**
 * Created by Administrator on 2017/2/18.
 */
public class FlipViewDemoActivity extends BaseSwipeBackAppcompatActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.flip_view)
    FlipView flipView;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {

        toolbarTitle.setText("FlipView");

        flipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        flipView.peakPrevious(true);
        flipView.peakNext(true);

        ArrayList<String> list = new ArrayList<>();
        list.add("Android");
        list.add("Java");
        list.add("IOS");
        list.add("C++");

        flipView.setAdapter(new SampleAdapter(this, list));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flipview;
    }

    class SampleAdapter extends BaseAdapter {

        Context context;
        List<String> list;

        public SampleAdapter (Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textview = new TextView(context);
            textview.setText(list.get(i));
            return textview;
        }
    }
}
