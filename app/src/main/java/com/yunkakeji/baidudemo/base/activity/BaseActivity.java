package com.yunkakeji.baidudemo.base.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.yunkakeji.baidudemo.net.basic.JsonTag;

import org.simple.eventbus.EventBus;

import androidx.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 所有Activity基类
 * 定义初始化视图和初始化数据抽象方法
 *
 */
public abstract class BaseActivity extends EventBusActivity{

    protected  CompositeDisposable mCompositeDisposable;

    /**
     * 标记是否刷新
     */
    protected boolean mIsRefreshing;

    /**
     * 初始化UI
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private ProgressDialog progressDialog;

    protected void show(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void receive(JsonObject jsonObject) {
        dismiss();
        super.receive(jsonObject);
    }

    /**
     * 发送错误信息
     *
     * @param throwable 异常
     */
    protected void postError(Throwable throwable) {
        if (throwable == null)
            return;
        JsonObject jsonObject = new JsonObject();
        if (throwable.getMessage().contains("timeout")) {
            jsonObject.addProperty(JsonTag.TAG_ERROR, "网络连接超时");
        } else if (throwable.getMessage().contains("127.0.0.1")) {
            jsonObject.addProperty(JsonTag.TAG_ERROR, "请重新登录");
        } else if (throwable.getMessage().contains("reset")) {
            jsonObject.addProperty(JsonTag.TAG_ERROR, "等待连接");
        } else {
            jsonObject.addProperty(JsonTag.TAG_ERROR, throwable.getMessage());
        }
        EventBus.getDefault().post(jsonObject);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // when destroy UI
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear(); // clear时网络请求会随即cancel
            mCompositeDisposable = null;
        }
    }
}
