package com.ruitu.arad.api;

/**
 * Created by Administrator on 2018/5/2.
 */
public class BaseResult<B> {
    private boolean success;
    private String code;
    private String msg;
    private B data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public B getData() {
        return data;
    }

    public void setData(B data) {
        this.data = data;
    }
}
