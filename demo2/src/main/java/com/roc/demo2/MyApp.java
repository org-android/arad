package com.roc.demo2;

import com.ruitu.arad.AradApplication;
import com.ruitu.arad.AradApplicationConfig;

public class MyApp extends AradApplication {
    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
