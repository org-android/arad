package com.ruitu.router_module.bean;

/**
 * Created by  on 2018/7/3.
 */

public class Version {

    /**
     * id : 2676327842045083168
     * code : 1
     * name : 1.1.0
     * url : /static/version/1530330505606_2062378949128309415.png
     * content : 版本更新测试
     * type : 1
     */

    private String id;
    private int code;
    private String name;
    private String url;
    private String content;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Version{" +
                "版本id='" + id + '\'' +
                ", 版本code=" + code +
                ", 版本name='" + name + '\'' +
                ", 版本url='" + url + '\'' +
                ", 版本content='" + content + '\'' +
                ", 版本type='" + type + '\'' +
                '}';
    }
}
