package com.yunkakeji.baidudemo.modules.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkakeji.baidudemo.modules.bean.HomeData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator
 * on 2019/12/18 9:33
 */
public class MainAdapter extends BaseQuickAdapter<HomeData, BaseViewHolder> {

    public MainAdapter(int layoutResId, @Nullable List<HomeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeData item) {

    }
}
