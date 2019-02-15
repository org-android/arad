package com.ruitu.arad.demo.mvp.model;

import com.ruitu.arad.base.BaseModel;
import com.ruitu.arad.demo.dao.RxUtil;
import com.ruitu.router_module.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class UserModel extends BaseModel {

    public Observable<List<User>> getUserList(int currPage) {
        List<User> userList = new ArrayList<>();
        for (int i = currPage * 100; i < currPage * 100 + 20; i++) {
            User u = new User();
            u.setId(System.currentTimeMillis() + "");
            u.setNickName("用户昵称" + i);
            u.setUserName("用户名" + i);
            userList.add(u);
        }
        return Observable.fromArray(userList).compose(RxUtil.<List<User>>subIoObMain());
    }
}
