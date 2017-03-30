package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;

/**
 * Created by Administrator on 2017/2/13.
 */
public class SimpleActivity extends BaseSwipeBackAppcompatActivity {
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
        return R.layout.activity_simple_activity;
    }

    @Override
    protected void handleToolbar(ToolbarHelper toolbarHelper) {
        super.handleToolbar(toolbarHelper);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(SimpleActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }
}
