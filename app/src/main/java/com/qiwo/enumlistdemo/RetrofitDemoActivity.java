package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

public class RetrofitDemoActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://v.juhe.cn/cell/get?mnc=0&cell=4510&lac=9772&key=b365973ed619776d522fd66";
    private static final String TAG = RetrofitDemoActivity.class.getSimpleName();
    @Bind(R.id.button6)
    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button6)
    public void onClick() {
        Log.e(TAG, "onclick");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserBiz userBiz = retrofit.create(IUserBiz.class);
        Call<List<User>> call = userBiz.getUsers();
        call.enqueue(new Callback<List<User>>()
        {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                Log.e(TAG, "normalGet:" + response.body() + "");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "error:"+t.toString());
            }
        });
    }

    public interface IUserBiz {
        @GET("null")
        Call<List<User>> getUsers();
    }
}