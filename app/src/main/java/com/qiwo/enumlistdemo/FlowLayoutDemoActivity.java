package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/18.
 */
public class FlowLayoutDemoActivity extends BaseSwipeBackAppcompatActivity {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_flowlayout)
    com.zhy.view.flowlayout.TagFlowLayout idFlowlayout;

    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initViewAndListener() {
        toolbarTitle.setText("FlowLayout");
        final ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 10; i ++) {
            list.add("标签" + i);
        }

        idFlowlayout.setAdapter(new TagAdapter(list) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.layout_tv, idFlowlayout, false);
                tv.setText(list.get(position));
                return tv;
            }
        });

//        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//
//        if (wm != null && !wm.isWifiEnabled()) {
//            wm.setWifiEnabled(true);
//        }

//        for (ScanResult result:
//        wm.getScanResults()) {
//            int width = result.channelWidth;
//            Log.d(getLocalClassName(), "name:" + result.SSID);
//            Log.d(getLocalClassName(), "chanel:" + result.frequency + "\n-------------");
//        }

//        Log.d(getLocalClassName(), "name:" + wm.getConnectionInfo().getSSID());
//        Log.d(getLocalClassName(), "RSSI:" + wm.getConnectionInfo().getRssi() + "\n-------------");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flowlayout;
    }

}
