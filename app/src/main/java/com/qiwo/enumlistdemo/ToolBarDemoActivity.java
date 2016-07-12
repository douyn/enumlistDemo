package com.qiwo.enumlistdemo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by l on 2016/6/10.
 */
public class ToolBarDemoActivity extends AppCompatActivity {
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.content_fragment)
//    FrameLayout contentFragment;
//    @Bind(R.id.negaviteview)
//    NavigationView negaviteview;
//    @Bind(R.id.drawer)
//    DrawerLayout drawer;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.activity_toolbar_demo);
//        ButterKnife.bind(this);
//        setupToolBar();
//        setupDrawer();
//        setupNegavite();
//    }
//
//    private void setupNegavite() {
////        negaviteview.setNavigationItemSelectedListener();
//    }
//
//    private void setupDrawer() {
//        ActionBarDrawerToggle drawertoggle = new ActionBarDrawerToggle(ToolBarDemoActivity.this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
//        drawertoggle.syncState();
//        drawer.setDrawerListener(drawertoggle);
//    }
//
//    private void setupToolBar() {
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
////        getSupportActionBar().setHomeButtonEnabled(true);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("菜单");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_demo);
        //设置ToolBar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        //设置抽屉DrawerLayout
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatactionbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "这是一个snackbar的样式,你知道吗?", Snackbar.LENGTH_LONG);
                snackbar.show();

                snackbar.setAction("真漂亮!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
            }
        });

        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.negaviteview);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item_one:
                        showDialog();
                        break;
                    case R.id.item_two:
                        break;
                    case R.id.item_three:
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    public void showDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("标题");
        builder.setMessage("内容");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}
