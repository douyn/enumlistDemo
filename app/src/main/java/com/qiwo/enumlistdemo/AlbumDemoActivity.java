package com.qiwo.enumlistdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.qiwo.bean.enumlistdemo.PicList;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;

public class AlbumDemoActivity extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    @Bind(R.id.grideview)
    GridView grideview;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static int screenWidth;
    public static int screenHeight;
    int getLayoutId() {
        return R.layout.activity_album_demo;
    }

    @Override
    protected void initView() {
        super.initView();
        screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        getPics();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    OkHttpClient mOkHttp;

    private void getPics() {
        mOkHttp = new OkHttpClient();
        String req_url = "http://jufanclub.juooo.net.cn/star/starGalleryInfo?star_gallery_group_id=2&page=1&pageSize=20" +
                "&ju_token=2641cf63b86f3aab2ef4f305602e86e6&&client_type=1&version=4&version_id=1";
        Request request = new Request.Builder().url(req_url).build();
        Call callBack = mOkHttp.newCall(request);
        callBack.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);
                Gson gson = new Gson();
                PicList picList = gson.fromJson(result, PicList.class);
                String code = picList.getCode();
                final List<PicList.DataBean.PicItem> data = picList.getData().getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlbumAdapter adapter = new AlbumAdapter(AlbumDemoActivity.this, data);
                        grideview.setAdapter(adapter);
                    }
                });
            }
        });
    }

    public class AlbumAdapter extends BaseAdapter {

        List<PicList.DataBean.PicItem> list;
        Context context;
        public AlbumAdapter(Context context, List<PicList.DataBean.PicItem> data){
            this.list = data;
            this.context =context;
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
            ImageView iv;
            if(view == null){
                iv = new ImageView(context);
                iv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else{
                iv = (ImageView) view;
            }

            PicList.DataBean.PicItem item = list.get(i);
            String url = item.getThumb_url();
            Glide.with(context).load(url).into(iv);

            




            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AlbumDemoActivity.this, PicDetailActivity.class);
                }
            });
            return iv;
        }
    }
}
