package com.qiwo.enumlistdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import ru.yandex.yandexmapkit.MapView;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SchemeActivity extends BaseActivity {
    @Bind(R.id.button_intent)
    Button button_intent;
    @Bind(R.id.button_scheme)
    Button button_scheme;
    @Bind(R.id.mapview)
    MapView mapview;

    @Override
    int getLayoutId() {
        return R.layout.activity_scheme;
    }

    @OnClick({R.id.button_intent, R.id.button_scheme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_intent:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                Class<?> clz = null;
                try {
                    clz = Class.forName("com.qiwo.papet.ui.AddPetActivity");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String pkg = getPackageManager().getInstallerPackageName("com.qiwo.papet");
                ComponentName cn = new ComponentName("com.qiwo.papet", "com.qiwo.papet.ui.ScanGuideActivity");
//                String/Context pkg, String clz;
                intent.setComponent(cn);
                startActivity(intent);
                break;

            case R.id.button_scheme:
                Intent intent_scheme = new Intent(Intent.ACTION_VIEW, Uri.parse("papet://scan:8888/scanqrcode"));
                startActivity(intent_scheme);
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
