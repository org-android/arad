package com.ruitu.player18.bean;

import java.io.Serializable;

public class TvItem implements Serializable {
    private String name;
    private String url;
    private String logo;
    private int type; // 0 不能看 1 正在测试 2 可以看 3 正在播放

    public TvItem() {
    }

    public TvItem(int t, String name, String url) {
        this.type = t;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
