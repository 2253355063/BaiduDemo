package com.yunkakeji.baidudemo.base.fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.yunkakeji.baidudemo.net.basic.JsonTag;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import androidx.fragment.app.Fragment;

/**
 * Fragment抽象类
 * Fragment中事件总线的注册和取消
 * 事件总线消息接收和显示
 */
public abstract class EventBusFragment extends Fragment {

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
            ToastUtils.showShort(jsonObject.get(JsonTag.TAG_ERROR).getAsString());
        }
    }

}
