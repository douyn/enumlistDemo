package com.qiwo.enumlistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/10.
 */
public class ToolBarDemoSecondActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_toolbar);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        btn.setOnClickListener(this);

        // ---------------------------ToolBar 配置---------------------------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // navigation icon
        toolbar.setNavigationIcon(R.drawable.bg_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // logo
//        toolbar.setLogo(resId);
        // title
//        toolbar.setTitle("");
//        toolbar.setTitleTextColor(resId);
//        toolbar.setTitleTextAppearance(context, resId);
        // sub title
//        toolbar.setSubtitle("");
//        toolbar.setSubtitleTextColor(resId);
//        toolbar.setSubtitleTextAppearance(context, resId);

        // ---------------------------ToolBar 配置-------------------------

    }
    
    // 创建menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, SimpleActivity.class));
    }
}
