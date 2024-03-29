package com.ruitu.router_module;

import com.ruitu.arad.api.RuituInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

// 全局配置信息
public class Config {
    public static boolean IS_DEBUG = true;

    public static final String HTTP_URL = "https://www.jbshch.com/app/";
    public static final String BASE_URL = "https://www.jbshch.com";
    public static final String URL = "https://www.jbshch.com/app/";

    public static final String SAVE_CITY_KEY = "save_city_key";//城市名称
    public static final String SAVE_CITY_ID = "save_city_id";//城市id
    public static final String SAVE_CITY_PA_NAME = "save_city_pa_name";//省份名称
    public static final String SAVE_CITY_PA_ID = "save_city_pa_id";//省份id

    static HttpLoggingInterceptor loggingInterceptor;

    public static HttpLoggingInterceptor getLoggingInterceptor() {
        if (null == loggingInterceptor) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }
        if (IS_DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }

    static RuituInterceptor ruituInterceptor;

    public static RuituInterceptor getRuituInterceptor() {
        if (null == ruituInterceptor) {
            ruituInterceptor = new RuituInterceptor();
        }
        return ruituInterceptor;
    }

    static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(getRuituInterceptor())
                    .addInterceptor(getLoggingInterceptor())
                    .build();
        }
        return client;
    }
}
