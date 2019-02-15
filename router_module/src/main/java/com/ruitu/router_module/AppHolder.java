package com.ruitu.router_module;

import com.ruitu.router_module.bean.User;

/**
 * Created by on 2018/4/18.
 */
public class AppHolder {
    public AppHolder() {
    }

    private static AppHolder appHolder = new AppHolder();

    public static AppHolder instance() {
        return appHolder;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
