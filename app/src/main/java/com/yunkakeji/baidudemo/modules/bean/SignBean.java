package com.yunkakeji.baidudemo.modules.bean;

/**
 * Created by Administrator
 * on 2020/1/8 16:58
 * 签到
 */
public class SignBean {

    //签到获取的积分
    private int coin;
    /**
     * 第几天
     */
    private int day;
    //是否签到
    private boolean signed;

    public SignBean(int coin, int day, boolean signed) {
        this.coin = coin;
        this.day = day;
        this.signed = signed;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
