package com.qiwo.enumlistdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qiwo.services.DoubleClickExitHelper;
import com.qiwo.services.GetuiServices;

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

//        PushManager.getInstance().initialize(this.getApplicationContext());
        startService(new Intent(MainActivity.this, GetuiServices.class));

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
        OPTIO17(15, "侧滑recyclerview", "侧滑recyclerview", SwipeRycyclerActivity.class),
        OPTIO18(16, "标签栏", "tablayout+viewpager", TabLayoutDemoActivity.class),
        OPTIO19(17, "日期选择器和时间选择器", "datepickerdialog+timepickdialog", DatePickerDialogDemoActivity.class),
        OPTIO20(18, "网络相册", "grideview + gallery", AlbumDemoActivity.class),
        OPTIO21(19, "Gallery", "gallery", GalleryActivity2.class),
        OPTIO22(20, "可展开recycleview，带侧滑，带上落下啦刷新", "ExpandableRecycleView", ExpandableRecycleViewActivity.class),
        OPTIO23(21, "轨迹动画", "贝塞尔曲线/动画", PathAnimationDemoActivity.class),
        OPTIO24(22, "scheme", "启动另一个app", SchemeActivity.class),
        OPTIO25(23, "ijkplayer", "ijkplayer", IJkplayerDemoActivity.class),
        OPTIO26(24, "android setting", "android风格设置页面", AndroidStyleSettingActivity.class),
        OPTIO27(27, "IPicker", "自定义 图片选择和截取", IPickDemoActivity.class),
        OPTIO28(28, "导航", "调用手机高德/百度导航功能", DaoHangActivity.class),
        OPTIO29(29, "Progressbar", "Progressbar在6.0上不更新进度", ProgressbarDemoActivity.class),
        OPTIO30(30, "ConstraintLayout", "约束性布局 ConstraintLayout", ConstraintLayoutDemoActivity.class),
        OPTIO31(31, "Toolbar", "ToolBar复习以及BaseSwipebackActivity", ToolBarDemoSecondActivity.class),
        OPTIO32(32, "Swipe Card View", "Swipe Card View", SwipeCardViewDemoActivity.class),
        OPTIO33(33, "Flip View", "Flip View", FlipViewDemoActivity.class),
        OPTIO34(34, "FlowLayout", "FlowLayout", FlowLayoutDemoActivity.class),
        OPTIO35(35, "一些动画的新特性", "sample", AnimationDemoActivity.class),
        OPTIO36(36, "BLE", "BLE", BLEDemoActivity.class),
        OPTIO37(37, "Retrofit支持加密HTTPS传输", "Retrofit https", RetrofitHttpsDemoActivity.class),
        OPTIO38(38, "Android new API - Transition", "transition", TransitionDemoActivity.class),
        OPTIO39(39, "Android Reference", "Android Reference", AndroidReferenceDemoActivity.class),
        OPTIO40(40, "Swipe RecyclerView", "Custome SwipeRecyclerView", CustomeSwipeRecyclerViewDemoActivity.class),
        OPTIO41(41, "ViewDragHelper", "ViewDragHelper", DragViewHelperDemoActivity.class);

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

    DoubleClickExitHelper dce = new DoubleClickExitHelper(this);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            return dce.onKeyDown(keyCode, event);
        }
        return true;
    }
}
