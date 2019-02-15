package com.ruitu.router_module.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/22.
 */
public class RongToken implements Serializable {
    //    {"code":200,"userId":"userId","token":"tokenStr"}
    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
