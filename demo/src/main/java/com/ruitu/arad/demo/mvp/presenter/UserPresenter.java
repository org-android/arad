package com.ruitu.arad.demo.mvp.presenter;

import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.base.BaseView;
import com.ruitu.arad.demo.mvp.model.UserModel;
import com.ruitu.router_module.bean.User;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class UserPresenter extends BasePresenter<BaseView, UserModel> {
    public Disposable getUserList(int currPage) {
        return m.getUserList(currPage).subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) throws Exception {
                v.hideProgress();
                v.onReqComplete(1, true, users);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                v.hideProgress();
            }
        });
    }
}
