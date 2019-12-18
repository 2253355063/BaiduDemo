package com.yunkakeji.baidudemo.net.basic;

/**
 * 网络请求返回数据基类
 */
public class NetBasicResp{

    private int code;
    private String error;
    private Throwable throwable;

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
