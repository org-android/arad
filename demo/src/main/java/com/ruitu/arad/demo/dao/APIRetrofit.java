package com.ruitu.arad.demo.dao;

import com.ruitu.arad.base.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofit {
    private static ApiService SERVICE;

    public static ApiService getDefault() {
        if (SERVICE == null) {

            SERVICE = new Retrofit.Builder()
//                    .baseUrl(Constant.RETROFIT_URL)
                    .baseUrl(Config.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(Config.getClient())
                    .build().create(ApiService.class);
        }
        return SERVICE;
    }

    //融云
    private static ApiService RONG_SERVICE;

    public static ApiService getRongApi() {
        if (RONG_SERVICE == null) {
            RONG_SERVICE = new Retrofit.Builder()
                    .baseUrl("http://api.cn.ronghub.com/user/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getRongClient())
                    .build().create(ApiService.class);
        }
        return RONG_SERVICE;
    }

    private static OkHttpClient client;

    private static OkHttpClient getRongClient() {
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(Config.getLoggingInterceptor())
                    .build();
        }
        return client;
    }
}
