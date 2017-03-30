package com.qiwo.api;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/27.
 */

public interface DemoHttpsService {

    //https://papet.qiwocloud2.com/?action=login&password=111111&push_token=fe976fb08fbaeaf87e824dbf564f5cec&country_code=86&plat=1&username=18236886950
    @POST("login")
    Observable<Object> login(
            @Query("password") String password,
            @Query("push_token") String push_token,
            @Query("country_code") String country_code,
            @Query("username") String username,
            @Query("plat") String plat
    );

    @GET("/otn")
    Observable<Object> sample();
}
