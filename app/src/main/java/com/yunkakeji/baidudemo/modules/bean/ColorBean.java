package com.yunkakeji.baidudemo.modules.bean;

/**
 * Created by Administrator
 * on 2020/1/10 9:35
 */
public class ColorBean {

    private int alpha;
    private int red;
    private int green;
    private int blue;

    public ColorBean(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
