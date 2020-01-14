package com.yunkakeji.baidudemo.modules;

import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityColorsBinding;
import com.yunkakeji.baidudemo.modules.adapter.ColorsAdapter;
import com.yunkakeji.baidudemo.modules.bean.ColorBean;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Created by Administrator
 * on 2020/1/10 9:30
 */
public class ColorsActivity extends BaseActivity {

    private ActivityColorsBinding mBD;
    private ColorsAdapter adapter;


    @Override
    protected void initView() {
        mBD = DataBindingUtil.setContentView(this, R.layout.activity_colors);

        mBD.rvColors.setLayoutManager(new GridLayoutManager(this, 255));
        adapter = new ColorsAdapter(new ArrayList<ColorBean>());
        mBD.rvColors.setAdapter(adapter);


    }

    @Override
    protected void initData() {


        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {

                }
            }
        }

    }




}
