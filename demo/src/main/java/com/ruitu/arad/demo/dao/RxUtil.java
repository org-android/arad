package com.ruitu.arad.demo.dao;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    private final static ObservableTransformer schedulersTransformer =
            new ObservableTransformer() {
                @Override
                public ObservableSource apply(Observable upstream) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };

    public static <T> ObservableTransformer<T, T> subIoObMain() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }
}
