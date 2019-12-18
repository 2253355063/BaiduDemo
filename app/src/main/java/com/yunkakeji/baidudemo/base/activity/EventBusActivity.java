package com.yunkakeji.baidudemo.base.activity;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.yunkakeji.baidudemo.net.basic.JsonTag;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 抽象类
 * 1、处理事件总线的注册和取消
 * 2、时间消息事件接收和信息显示
 */
public abstract class EventBusActivity extends AppCompatActivity {

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    protected void receive(JsonObject jsonObject) {
        if (jsonObject.has(JsonTag.TAG_ERROR)) {
            ToastUtils.showShort( jsonObject.get(JsonTag.TAG_ERROR).getAsString());
        }
    }

}
