package com.yunkakeji.baidudemo.modules.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.modules.bean.HomeData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator
 * on 2019/12/18 9:33
 */
public class BaseAdapter extends BaseQuickAdapter<HomeData, BaseViewHolder> {

    public BaseAdapter(@Nullable List<HomeData> data) {
        super(R.layout.item_main, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeData item) {

        helper.setText(R.id.tv_title,item.getTitle());
    }
}
