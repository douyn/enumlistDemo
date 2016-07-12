package com.qiwo.enumlistdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.listview_enum)
    ListView listviewEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyAdapter myAdapter = new MyAdapter(this);
        listviewEnum.setAdapter(myAdapter);
        listviewEnum.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(MainActivity.this, MyEnum.values()[i].clz));
    }

    private enum MyEnum{
        OPTIONONE(0, "开启手机热点", "option one", HotePointActivity.class),
        OPTIONTWO(1, "手机录像", "option two", RecordActivity.class),
        OPTIONTHREE(2, "加载动画", "option three", AVLoadingActivity.class),
        OPTIONFOUR(3, "蓝牙低功耗", "ble", BLEActivity.class),
        OPTIONFIVE(4, "截取屏幕", "screen capture", ScreenCaptureActivity.class),
        OPTIONSIX(5, "登录窗口", "login window", Main3Activity.class),
        OPTIONSEVEN(6, "视频播放", "video player", VideoActivity.class),
        OPTIONEIGHT(7, "自定义view", "custom view", RainbowBarActivity.class),
//        OPTIONINE(8, "百度地图DEMO", "baidu map", BaiduMapDemoActivity.class),
        OPTIOTEN(9, "高德地图DEMO", "god map", GodMapDemoActivity.class),
        OPTIOELEVEN(10, "RETROFIT2.0 DEMO", "retrofit 2.0", RetrofitDemoActivity.class),
        OPTIO12(10, "Metrictal Design", "login demo", MetricalDesignDemoActivity.class),
        OPTIO13(11, "toolbar+negaviteview", "toolbar+negaviteview", ToolBarDemoActivity.class),
        OPTIO14(12, "健康计步", "circle progress", HealthCalStepActivity.class),
        OPTIO15(13, "上课禁用", "swipelistview+datepickerview", StudyTimeDisableActivity.class),
        OPTIO16(14, "数字选择", "NumberPickerView", DayPickerActivity.class),
        OPTIO17(15, "侧滑recyclerview", "侧滑recyclerview", SwipeRycyclerActivity.class);


        int index;
        String desc;
        Class clz;
        String title;

        MyEnum(int index, String title, String desc, Class clz){
            this.title = title;
            this.index = index;
            this.desc = desc;
            this.clz = clz;
        }
    }

    private class MyAdapter extends ArrayAdapter{
        public MyAdapter(Context ctx){
            super(ctx, R.layout.item_arraylist);
        }

        @Override
        public int getCount() {
            return MyEnum.values().length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_arraylist, null);
            }

            MyEnum option = MyEnum.values()[position];

            TextView tv_title = (TextView) convertView.findViewById(R.id.textView);
            TextView tv_desc = (TextView) convertView.findViewById(R.id.textView2);

            tv_title.setText(option.title);
            tv_desc.setText(option.desc);

            return convertView;
        }
    }
}
