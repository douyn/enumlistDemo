package com.qiwo.api;

import android.content.Context;

import com.qiwo.enumlistdemo.R;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DemoHttpsApi {
    DemoHttpsService mDemoHttpsService;

    public DemoHttpsApi (Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String baseUrl = "https://papet.qiwocloud2.com/";
//        String baseUrl = "https://kyfw.12306.cn/";
//        String baseUrl = "https://skyish-test.yunext.com";

        int[] certificates = {R.raw.papet};
        String[] hostUrls = {baseUrl};
        OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .socketFactory(HTTPSUtils.getSSLSocketFactory(context, new int[]{R.raw.papet}))
                .hostnameVerifier(HTTPSUtils.getHostNameVerifier(hostUrls))
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        mDemoHttpsService = retrofit.create(DemoHttpsService.class);
    }

    public void login (Subscriber subscriber) {
        mDemoHttpsService.login("111111", "fe976fb08fbaeaf87e824dbf564f5cec", "86", "18236886950", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sample (Subscriber subscriber) {
        mDemoHttpsService.sample()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
