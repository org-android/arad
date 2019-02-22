package com.ruitu.arad.base;

// mvp v
public interface BaseView {

    void hideProgress();

    void onReqComplete(int code, boolean isOk, Object data);
}
