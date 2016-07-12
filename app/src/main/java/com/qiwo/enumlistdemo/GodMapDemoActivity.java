package com.qiwo.enumlistdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by l on 2016/5/12.
 */
public class GodMapDemoActivity extends Activity implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnMapClickListener, TextWatcher, PoiSearch.OnPoiSearchListener {

    private static final String TAG = GodMapDemoActivity.class.getSimpleName();
    MapView mMapView = null;
    AMap mMap = null;
    OnLocationChangedListener mListener = null;
    @Bind(R.id.tv_key)
    AutoCompleteTextView tvKey;
    @Bind(R.id.btn_search)
    Button btnSearch;

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godmapdemo);
        ButterKnife.bind(this);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();
        tvKey.addTextChangedListener(this);
        //设置定位资源。如果不设置此定位资源则定位按钮不可点击。
//        mMap.setLocationSource(this);
        //设置默认定位按钮是否显示
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        mMap.setMyLocationEnabled(true);
//        mMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        addLineToMap();
        addCircleToMap();
        addMarker();
		
        tvKey.setThreshold(1);
    }

    private void addMarker() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.40, 113.88)).icon(BitmapDescriptorFactory.fromResource(R.drawable.btn_trackguide_close_normal)));
    }

    private void addCircleToMap() {
        mMap.addCircle(new CircleOptions().center(new LatLng(22.40, 113.88))
                .radius(4000).strokeColor(Color.BLUE).fillColor(Color.WHITE)
                .strokeWidth(3));
    }

    private void addLineToMap() {
        PolylineOptions poloLineOptions = new PolylineOptions().add(new LatLng(22.40, 113.88), new LatLng(25, 120)).setDottedLine(true).geodesic(true).color(Color.RED);
        mMap.addPolyline(poloLineOptions);
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
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
//                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Log.e(TAG, "location success!");
            } else {
                Log.e(TAG, "location faild!" + amapLocation.getErrorInfo());
//                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
//                Log.e("AmapErr",errText);
//                mLocationErrText.setVisibility(View.VISIBLE);
//                mLocationErrText.setText(errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e(TAG, "on marker click");
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.e(TAG, "on map click");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String str = charSequence.toString().trim();
        if (!TextUtils.isEmpty(str)) {
            PoiSearch.Query query = new PoiSearch.Query(str, null);
            query.setPageSize(10);
            query.setPageNum(0);
            PoiSearch search = new PoiSearch(GodMapDemoActivity.this, query);
            search.setOnPoiSearchListener(this);
            search.searchPOIAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            Log.e(TAG, "get search result.");
            Log.e(TAG, poiResult.getPois().toString());
            if (poiResult != null && poiResult.getQuery() != null) {
                ArrayList<PoiItem> poiItems = poiResult.getPois();
                tvKey.setAdapter(new SearchAdapter(GodMapDemoActivity.this, R.layout.item_arraylist, poiItems));
                tvKey.showDropDown();
            }
        } else{
            Log.e(TAG, "search faild.");
            Log.e(TAG, poiResult.getPois().toString());
        }
    }

    public class SearchAdapter extends ArrayAdapter {
        Context mCtx;
        List<PoiItem> mList;

        public SearchAdapter(Context context, int resource, List list) {
            super(context, resource, list);
            this.mCtx = context;
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.item_arraylist, null);
            }

            TextView tv_title = (TextView) view.findViewById(R.id.textView);
            TextView tv_des = (TextView) view.findViewById(R.id.textView2);

            final PoiItem item = mList.get(i);
            String adName = item.getAdName();
            String ctName = item.getCityName();
            tv_title.setText(item.toString());
            tv_des.setText(ctName + "   " + adName + "   " + item.getSnippet());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, item.getLatLonPoint().toString());
                    double lat = item.getLatLonPoint().getLatitude();
                    double lon = item.getLatLonPoint().getLongitude();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)));
                    if(tvKey.isPopupShowing()){
                        tvKey.dismissDropDown();
                    }
                }
            });
            return view;
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
