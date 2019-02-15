package com.ruitu.router_module.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String id;//id
    private String nickName; //昵称
    private String userName; //用户名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
