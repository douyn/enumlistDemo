package com.qiwo.enumlistdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BLEActivity extends AppCompatActivity implements View.OnClickListener {

    public boolean mScanning = false;

    BluetoothAdapter mBluetoothAdapter;

    Handler mHandler = new Handler();

    public static final String TAG = "BLEActivity";
    @Bind(R.id.button)
    Button button;

    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        ButterKnife.bind(this);
        button.setOnClickListener(this);
    }

    @OnClick(R.id.button)
    public void onClick(View v) {
        useBLE();
    }

    // 如何使用BLE
    public void useBLE() {
//    1、准备BLE
        //    1）获取BluetoothAdapter
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //    2）检测蓝牙是否打开
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 100);
        }

//    2、查找BLE设备
        scanLeDevice(true);
        //    1）扫描到需要的设备后，马上停止扫描；
        //    2）给扫描一个时间限制
    }

    private void scanLeDevice(boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, 30000);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

//    3、连接到GATT Server

//    4、读BLE属性

//    5、收到GATT通知

//    6、关闭客户端蓝牙


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rafi,
                                     byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mLeDeviceListAdapter.addDevice(device);
//                            mLeDeviceListAdapter.notifyDataSetChanged();
                            Log.e(TAG, "name:"+device.getName());
                            Log.e(TAG, "address:"+device.getAddress());
                            if(device.getName() != null && device.getName().equalsIgnoreCase("W362")){
                                Log.e(TAG, "扫描结束");
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            }
                        }
                    });
                }
            };
}
