package com.qiwo.enumlistdemo;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/1/18.
 */
public class DaoHangActivity extends BaseActivity{
    @Override
    int getLayoutId() {
        return R.layout.activity_daohang;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    public void startDaohang (View v) {
        double lat = 22.543099d;
        double lon = 114.0556793d;

        if (isInstallByread("com.autonavi.minimap")) {
            if (!(lat == 0 && lon == 0)) {
                Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://navi?sourceApplication=税源地图&lat=" + lat + "&lon=" + lon + "&dev=0&style=2"));
                intent.setPackage("com.autonavi.minimap");
                startActivity(intent); // 启动调用
            }
        } else {
            startBaiduMap(lat, lon);
        }
    }

    //判断是否安装目标应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    //移动APP调起Android百度地图方式
    private void startBaiduMap(double lat, double lon) {
        Intent intent = null;
        try {
            intent = Intent.getIntent
                    ("intent://map/navi?location="+lat+","+lon +
                            "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                            "package=com.baidu.BaiduMap;end");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (isInstallByread("com.baidu.BaiduMap")) {
            startActivity(intent); // 启动调用
        } else {
            Toast.makeText(DaoHangActivity.this, "没有安装高德/百度地图客户端", Toast.LENGTH_SHORT).show();
        }
    }
}
