package com.qiwo.enumlistdemo;

import android.app.Activity;


/**
 * Created by l on 2016/5/12.
 */
public class BaiduMapDemoActivity extends Activity {

    /*private static final String TAG = BaiduMapDemoActivity.class.getSimpleName();

    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "on create");
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap_demo);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        addMarker();

        addCircle();
    }

    private void addCircle() {
        OverlayOptions option = new CircleOptions().center(new LatLng(39.963175,116.400244)).radius(500).fillColor(R.color.common_action_bar_splitter);
        mBaiduMap.addOverlay(option);
    }

    private void addMarker() {
        OverlayOptions option = new MarkerOptions().position(new LatLng(39.963175, 116.400244)).icon(BitmapDescriptorFactory.fromResource(R.drawable.btn_trackguide_close_normal));
        mBaiduMap.addOverlay(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.e(TAG, "on map click");
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        Log.e(TAG, "on mapPoi click");
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e(TAG, "on map click");
        Log.e(TAG, "lat:"+ marker.getPosition().latitude + ",lon:"+marker.getPosition().longitude);
        return false;
    }*/
}

