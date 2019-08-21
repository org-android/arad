package com.ruitu.arad.demo.mvp.model;

import com.ruitu.arad.api.BaseResult;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.arad.demo.dao.APIRetrofit;
import com.ruitu.arad.demo.dao.RxUtil;
import com.ruitu.router_module.Logg;
import com.ruitu.router_module.bean.RongToken;
import com.ruitu.router_module.bean.Version;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class Http1Model extends BaseModel {
    public Observable<RongToken> getRongToken() {
        return APIRetrofit.getRongApi().reqRongToken("", "", ""
                , "", "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseResult<Version>> reqNewVersion() {
        return APIRetrofit.getDefault()
                .reqNewVersion()
                .compose(RxUtil.<BaseResult<Version>>subIoObMain());
    }

    public Observable<String> testDispose() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    emitter.onNext("" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Logg.i("睡眠出错了:" + e.getCause() + "----" + e.getMessage());
                    }
                }
            }
        }).compose(RxUtil.<String>subIoObMain());
    }
}
