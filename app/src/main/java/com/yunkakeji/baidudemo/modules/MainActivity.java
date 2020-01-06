package com.yunkakeji.baidudemo.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityMainBinding;
import com.yunkakeji.baidudemo.modules.adapter.BaseAdapter;
import com.yunkakeji.baidudemo.modules.bean.HomeData;
import com.yunkakeji.baidudemo.modules.ocr.TextRecognitionActivity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBD;

    private BaseAdapter adapter;

    private List<Class>activityClass=new ArrayList<>();

    @Override
    protected void initView() {
        mBD = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(mBD.toolbar);

        mBD.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new BaseAdapter(new ArrayList<HomeData>());
        mBD.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,((HomeData)adapter.getData().get(position)).getActivity());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

       String[]baiDuTypes=getResources().getStringArray(R.array.BaiDuType);

       activityClass.add(TextRecognitionActivity.class);

       for (int i=0;i<activityClass.size();i++){
           adapter.getData().add(new HomeData(baiDuTypes[i],activityClass.get(i)));
       }
       adapter.notifyDataSetChanged();
    }


}
