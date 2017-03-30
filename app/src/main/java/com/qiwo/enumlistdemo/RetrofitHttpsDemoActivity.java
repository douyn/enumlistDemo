package com.qiwo.enumlistdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.doudou.common.base.BaseSwipeBackAppcompatActivity;
import com.qiwo.api.DemoHttpsApi;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/27.
 */
public class RetrofitHttpsDemoActivity extends BaseSwipeBackAppcompatActivity{
    @Override
    protected void doBusiness() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initViewAndListener() {
        try {
            new DemoHttpsApi(this).login(new Subscriber() {
                @Override
                public void onCompleted() {
                    Log.e(TAG, "onCompleted: ");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ");
                    e.printStackTrace();
                }

                @Override
                public void onNext(Object o) {
                    Log.e(TAG, "onNext: ");
                }
            });
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ble;
    }
}
