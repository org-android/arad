package com.ruitu.router_module.bean;

/**
 * Created by wubin on 2017/6/8.
 */

public class EventModel<B> {
    private long eventCode;//事件代码(17010901 年月日+两位数字)

    private B eventObj;

    public EventModel(long eventCode) {//事件码的构造器
        this.eventCode = eventCode;
    }

    public EventModel(B eventObj) {//事件对象的构造器
        this.eventObj = eventObj;
    }

    public EventModel(int eventCode, B eventObj) {//事件码和事件对象的构造器
        this.eventCode = eventCode;
        this.eventObj = eventObj;
    }

    public long getEventCode() {
        return eventCode;
    }

    public void setEventCode(long eventCode) {
        this.eventCode = eventCode;
    }

    public B getEventObj() {
        return eventObj;
    }

    public void setEventObj(B eventObj) {
        this.eventObj = eventObj;
    }
}
