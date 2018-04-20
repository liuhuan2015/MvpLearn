package com.liuh.mvplearn.service;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huan on 2017/11/14 10:02.
 */

public class RetrofitHelper {
    private static Retrofit mRetrofit = null;

    private RetrofitHelper() {
    }

    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            OkHttpClient.Builder mBuild = new OkHttpClient.Builder();//使用这个mBuild可以做一些对请求进行拦截的事情
            OkHttpClient mOkHttpClient = mBuild.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/v2/")
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Rxjava
                    .build();
        }
        return mRetrofit;
    }

}
