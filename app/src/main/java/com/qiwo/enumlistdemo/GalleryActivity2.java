package com.qiwo.enumlistdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiwo.utils.GaussianBlurUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/7.
 */
public class GalleryActivity2 extends Activity implements AdapterView.OnItemSelectedListener {
    @Bind(R.id.gallery)
    Gallery gallery;
    @Bind(R.id.ll_bottum)
    LinearLayout llBottum;

    private final String TAG = GalleryActivity2.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        initView();
    }
    GalleryAdapter adapter;
    private void initView() {
        adapter = new GalleryAdapter(GalleryActivity2.this);
        gallery.setAdapter(adapter);
        gallery.setSpacing(5);
        gallery.setOnItemSelectedListener(this);
        adapter.setSelectItem(3);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapter.setSelectItem(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class GalleryAdapter extends BaseAdapter{

        Context context;
        private int[] drawables = new int[]{R.drawable.icon_bb,  R.drawable.icon_dd
        , R.drawable.icon_gg, R.drawable.icon_ll, R.drawable.icon_ly};
        int selectItem;

        public GalleryAdapter(GalleryActivity2 galleryActivity2) {
            context = galleryActivity2;
        }

        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(drawables[i % drawables.length]);

            if (i == selectItem) {
                Log.e(TAG, "选中放大");
                iv.setLayoutParams(new Gallery.LayoutParams(300, 360));
//                iv.setImageResource(drawables[i % drawables.length]);
            } else{
                iv.setAlpha(0.5f);
                iv.setLayoutParams(new Gallery.LayoutParams(175, 210));
//                iv.setImageDrawable(GaussianBlurUtil.BoxBlurFilter(BitmapFactory.decodeResource(getResources(), drawables[i % drawables.length])));
            }
            return iv;
        }

        public void setSelectItem(int i){
            if (i != selectItem) {
                selectItem = i;
                notifyDataSetChanged();
            }
        }
    }
}
