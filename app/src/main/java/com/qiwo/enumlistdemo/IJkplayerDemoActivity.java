package com.qiwo.enumlistdemo;

import android.net.Uri;
import android.os.Environment;
import android.widget.TableLayout;

import com.qiwo.base.view.ijk.AndroidMediaController;
import com.qiwo.base.view.ijk.IjkVideoView;
import com.qiwo.base.view.ijk.Settings;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 2016/9/19.
 */
public class IJkplayerDemoActivity extends BaseActivity{
    @Override
    int getLayoutId() {
        return R.layout.activity_ijkplayer;
    }

//    IjkVideoView videoView;
    IjkVideoView videoView;
    TableLayout hubview;
    AndroidMediaController mMediaController;
    Settings mSettings;

    @Override
    protected void initView() {
        super.initView();

        mSettings = new Settings(this);
        videoView = (IjkVideoView) findViewById(R.id.video_view);
        hubview = (TableLayout) findViewById(R.id.hud_view);
        videoView.setHudView(hubview);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        videoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory() + "/Pictures/start.mp4"));
//        videoView.setVideoURI(Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8"));
//        videoView.setVideoURI(Uri.parse("http://106.36.45.36/live.aishang.ctlcdn.com/00000110240001_1/encoder/1/playlist.m3u8"));
        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                videoView.start();
            }
        });
    }
}
