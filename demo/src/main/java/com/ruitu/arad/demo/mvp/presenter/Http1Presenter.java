package com.ruitu.arad.demo.mvp.presenter;

import android.text.TextUtils;

import com.ruitu.arad.api.BaseResult;
import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.demo.mvp.model.Http1Model;
import com.ruitu.arad.demo.ui.activity.Http1Activity;
import com.ruitu.arad.util.Logg;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.router_module.bean.Version;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Http1Presenter extends BasePresenter<Http1Activity, Http1Model> {
    public Disposable reqVersion() {
        return m.reqNewVersion().subscribe(new Consumer<BaseResult<Version>>() {
            @Override
            public void accept(BaseResult<Version> versionResult) throws Exception {
                Version version = versionResult.getData();
                Logg.i("请求的数据是:" + version.toString());
                v.onReqComplete(1, true, version);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                Logg.i("请求错误:" + t.getMessage());
                ToastUtils.showShort("请求版本信息失败");
            }
        });
    }

    public Disposable testDispose() {
        return m.testDispose().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logg.i("请求的数据是:" + s);
                if (TextUtils.equals("4", s)) {
                    v.hideProgress();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                Logg.i("请求错误:" + t.getMessage());
            }
        });
    }
}
