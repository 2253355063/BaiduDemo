package com.yunkakeji.baidudemo.modules.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.modules.bean.SignBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator
 * on 2020/1/8 16:58
 */
public class SignAdapter  extends BaseQuickAdapter<SignBean, BaseViewHolder> {


    public SignAdapter(@Nullable List<SignBean> data) {
        super(R.layout.item_sign, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SignBean item) {

        helper.setText(R.id.tv_coin,"+"+item.getCoin());
        if (item.getDay()==4||item.getDay()==7){
            helper.setTextColor(R.id.tv_coin,Color.parseColor("#FBA948"));
        }else {
            helper.setTextColor(R.id.tv_coin,Color.parseColor("#E4F0FF"));
        }

        if (item.isSigned()){
            helper.setBackgroundColor(R.id.line_left, Color.parseColor("#0068FF"));
            helper.setBackgroundColor(R.id.line_right, Color.parseColor("#0068FF"));

        }else {
            helper.setBackgroundColor(R.id.line_left, Color.parseColor("#E3EFFF"));
            helper.setBackgroundColor(R.id.line_right, Color.parseColor("#E3EFFF"));
        }

        helper.setText(R.id.rd_day,item.getDay()+"");
        helper.setChecked(R.id.rd_day,item.isSigned());


    }
}
