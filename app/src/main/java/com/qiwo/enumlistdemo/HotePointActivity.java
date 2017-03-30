package com.qiwo.enumlistdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotePointActivity extends AppCompatActivity {
    WifiManager manager;
    @Bind(R.id.button)
    Button button;

    boolean isOpen;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.button_gsm)
    Button buttonGsm;
    @Bind(R.id.button_gps)
    Button buttonWifi;
    @Bind(R.id.textView_gsm_info)
    TextView textViewGsmInfo;
    @Bind(R.id.textView_wifi_info)
    TextView textViewWifiInfo;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hote_point);
        ButterKnife.bind(this);
        buttonGsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String location_gsm = getGsmLocation();
                        System.out.println("location:" + location_gsm);
                    }
                }).start();
            }
        });
        final boolean[] et_flag = {false};
        buttonWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGPSLocation();
            }
        });
        manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    setWifiApEnabled(!isOpen);
                    isOpen = false;

                } else {
                    setWifiApEnabled(!isOpen);
                    isOpen = true;
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_flag[0]){
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_flag[0] = false;
                } else{
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_flag[0] = true;
                }
                startActivity(new Intent(HotePointActivity.this, Main3Activity.class));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                //IMEI
                String deviceId = tm.getDeviceId();
                /*
                   * 电话状态：
                   * 1.tm.CALL_STATE_IDLE=0     无活动
                   * 2.tm.CALL_STATE_RINGING=1  响铃
                   * 3.tm.CALL_STATE_OFFHOOK=2  摘机
                */
                int callstate = tm.getCallState();
                String networkOperator = tm.getNetworkOperator();
                String mmc = networkOperator.substring(0, 3);
                String mnc = networkOperator.substring(3);
                //获取手机方位
                GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                String cellLocation = "cid:" + location.getCid() + ",lac:" + location.getLac();

                //软件版本号
                String softVersion = tm.getDeviceSoftwareVersion();
                //手机号
                String phoneNum = tm.getLine1Number();
                //获取ISO标准的国家码，即国际长途区号。
                String country = tm.getNetworkCountryIso();

                textView3.setText("IMEI:" + deviceId + "\ncallstate:" + callstate
                        + "\ncellLocation:" + cellLocation + "\nsoftVersion:" + softVersion
                        + "\nphoneNum:" + phoneNum + "\ncountry:" + country);
            }
        });
    }


    // wifi热点开关
    public boolean setWifiApEnabled(boolean enabled) {
        if (enabled) { // disable WiFi in any case
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            manager.setWifiEnabled(false);
        }
        try {
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = "12334569871";
            //配置热点的密码
            apConfig.preSharedKey = "12122112";

            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            //通过反射调用设置热点
            Method method = manager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            //返回热点打开状态
            return (Boolean) method.invoke(manager, apConfig, enabled);
        } catch (Exception e) {
            return false;
        }
    }

    @OnClick({R.id.button_gsm, R.id.button_gps})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_gsm:
                String location_gsm = getGsmLocation();
                System.out.println("location:" + location_gsm);
                break;
            case R.id.button_gps:
                getGPSLocation();
                break;
        }
    }

    private void getGPSLocation() {
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS已经打开", Toast.LENGTH_SHORT).show();

            Criteria criteria = new Criteria();
            // 获得最好的定位效果
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            // 使用省电模式
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            // 获得当前的位置提供者
            String provider = lManager.getBestProvider(criteria, true);
            // 获得当前的位置
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = lManager.getLastKnownLocation(provider);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            textViewWifiInfo.setText("gps-->lat:" + latitude + "\ngps-->lon:" + longitude);
        } else {
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            getGPSLocation();
        }
    }


    private String getGsmLocation() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
        int mcc = Integer.parseInt(tm.getNetworkOperator().substring(0, 3));
        int mnc = Integer.parseInt(tm.getNetworkOperator().substring(3));
        int lac = location.getLac();
        int cid = location.getCid();
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("mnc", mnc);
        reqParams.put("cell", cid);
        reqParams.put("lac", lac);
        reqParams.put("hex", 28);
        reqParams.put("key", "b365973ed62fd3753f519776d522fd66");
        StringBuffer params = new StringBuffer();
        StringBuffer response = new StringBuffer();
        Iterator it = reqParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            params.append(entry.getKey());
            params.append("=");
            params.append(entry.getValue());
            params.append("&");
        }

        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }

        String url = "http://v.juhe.cn/cell/get";
        try {
            URL reqUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) reqUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Length", String
                    .valueOf(params.length()));
            conn.setConnectTimeout(1000 * 60);
            conn.setReadTimeout(1000 * 60);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.write(params.toString());
            out.flush();
            BufferedReader in = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append("\n").append(line);
                }

                JSONObject json = new JSONObject(response.toString().trim());
                //{"resultcode":"200","reason":"Return Successd!","result":{"data":[{"MCC":"460","MNC":"0","LAC":"9772","CELL":"4510","LNG":"114.02459759917","LAT":"22.631149984065","O_LNG":"114.029634",
                // "O_LAT":"22.628347","PRECISION":"1500","ADDRESS":"广东省深圳市宝安区中梅路"}]},"error_code":0}
                JSONObject data = json.getJSONObject("result").getJSONArray("data").getJSONObject(0);
                final String address = data.getString("ADDRESS");
                Log.e("hotepointactivity", "address:" + address);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewGsmInfo.setText(address);
                    }
                });
            }

            conn.disconnect();
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("hotepointactivity", "response:" + response.toString());
        return response.toString();
    }
}
