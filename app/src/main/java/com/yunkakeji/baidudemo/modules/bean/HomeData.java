package com.yunkakeji.baidudemo.modules.bean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator
 * on 2019/12/18 9:34
 */
public class HomeData {

    private String title;

    private Class activity;

    public HomeData(String title, Class activity) {
        this.title = title;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
