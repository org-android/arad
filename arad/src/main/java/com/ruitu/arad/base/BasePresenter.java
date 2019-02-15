package com.ruitu.arad.base;

import android.app.Activity;

// mvp p 持有 m 和 v
public class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected Activity activity;
    protected M m;
    protected V v;//一般是activity 或者 fragment

    public void setMV(M m, V v) {
        this.m = m;
        this.v = v;
    }
}