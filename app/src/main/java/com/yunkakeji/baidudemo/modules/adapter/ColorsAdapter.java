package com.yunkakeji.baidudemo.modules.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.modules.bean.ColorBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator
 * on 2020/1/10 9:35
 */
public class ColorsAdapter extends BaseQuickAdapter<ColorBean, BaseViewHolder> {


    public ColorsAdapter(@Nullable List<ColorBean> data) {
        super(R.layout.item_color, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ColorBean item) {

        helper.setBackgroundColor(R.id.tv_color, Color.parseColor("#"+item.getRed()+item.getGreen()+item.getBlue()));

    }
}
