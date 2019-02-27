package com.ruitu.player18.mvp;

import android.content.ContentResolver;

import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.player18.bean.MusicInfo;
import com.ruitu.player18.radio.FmOnlineActivity;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RadioPresenter extends BasePresenter<FmOnlineActivity, RadioModel> {
    public Disposable scanMusiList(ContentResolver contentResolver) {
        return m.scanMusiList(contentResolver).subscribe(new Consumer<List<MusicInfo>>() {
            @Override
            public void accept(List<MusicInfo> musicInfos) throws Exception {
                v.hideProgress();
                v.onReqComplete(1, true, musicInfos);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                v.hideProgress();
                ToastUtils.showShortSafe("扫描音乐发生错误");
                throwable.printStackTrace();
            }
        });
    }
}
