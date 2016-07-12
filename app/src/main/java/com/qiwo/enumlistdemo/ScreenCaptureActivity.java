package com.qiwo.enumlistdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenCaptureActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_capture);
        ButterKnife.bind(this);
        button2.setOnClickListener(this);
    }


    @OnClick({R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                doScreenCapture_v1();
                break;
            case R.id.button3:

                break;
        }
    }

    private void doScreenCapture_v1() {
        View viewScreen = getWindow().getDecorView();
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(),0,0,width,height);
        viewScreen.destroyDrawingCache();
        imageView.setImageBitmap(bitmap);
        //显示壁纸
//        imageView.setImageDrawable(getWallpaper());
    }
}
