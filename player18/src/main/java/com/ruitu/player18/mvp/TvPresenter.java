package com.ruitu.player18.mvp;

import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.player18.bean.TvItem;
import com.ruitu.player18.tv.TvActivity;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class TvPresenter extends BasePresenter<TvActivity, TvModel> {

    public Disposable initTvItemList() {
        return m.initTvItemList().subscribe(new Consumer<List<TvItem>>() {
            @Override
            public void accept(List<TvItem> tvItems) throws Exception {
                v.onReqComplete(1, true, tvItems);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                v.hideProgress();
                ToastUtils.showShortSafe("获取节目单失败");
            }
        });
    }
}
