package com.ruitu.arad.base;

// mvp v
public interface BaseView {

    void onReqComplete(int code, boolean isOk, Object data);
}
