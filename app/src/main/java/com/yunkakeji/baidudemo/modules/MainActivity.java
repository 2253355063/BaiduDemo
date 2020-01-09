package com.yunkakeji.baidudemo.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityMainBinding;
import com.yunkakeji.baidudemo.modules.adapter.BaseAdapter;
import com.yunkakeji.baidudemo.modules.adapter.SignAdapter;
import com.yunkakeji.baidudemo.modules.bean.HomeData;
import com.yunkakeji.baidudemo.modules.bean.SignBean;
import com.yunkakeji.baidudemo.modules.map.BDMapActivity;
import com.yunkakeji.baidudemo.modules.map.LocationActivity;
import com.yunkakeji.baidudemo.modules.ocr.TextRecognitionActivity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBD;

    private BaseAdapter adapter;

    private List<Class> activityClass = new ArrayList<>();

    private SignAdapter signAdapter;

    @Override
    protected void initView() {
        mBD = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBD.toolbar);

        mBD.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter(new ArrayList<HomeData>());
        mBD.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ((HomeData) adapter.getData().get(position)).getActivity());
                startActivity(intent);
            }
        });

        mBD.rvSign.setLayoutManager(new GridLayoutManager(this, 7));
        signAdapter = new SignAdapter(new ArrayList<SignBean>());
        mBD.rvSign.setAdapter(signAdapter);

        mBD.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (SignBean signBean : signAdapter.getData()) {
                    if (!signBean.isSigned()) {
                        signBean.setSigned(true);
                        break;
                    }
                }
                signAdapter.notifyDataSetChanged();
                setTotalCoin();
            }
        });
    }

    @Override
    protected void initData() {

        String[] baiDuTypes = getResources().getStringArray(R.array.BaiDuType);

        activityClass.add(TextRecognitionActivity.class);
        activityClass.add(BDMapActivity.class);
        activityClass.add(LocationActivity.class);

        for (int i = 0; i < activityClass.size(); i++) {
            adapter.getData().add(new HomeData(baiDuTypes[i], activityClass.get(i)));
        }
        adapter.notifyDataSetChanged();


        signAdapter.getData().add(new SignBean(5, 1, false));
        signAdapter.getData().add(new SignBean(10, 2, false));
        signAdapter.getData().add(new SignBean(15, 3, false));
        signAdapter.getData().add(new SignBean(20, 4, false));
        signAdapter.getData().add(new SignBean(25, 5, false));
        signAdapter.getData().add(new SignBean(30, 6, false));
        signAdapter.getData().add(new SignBean(35, 7, false));
        signAdapter.notifyDataSetChanged();

        setTotalCoin();
    }


    private void setTotalCoin(){

        int total=0;
        for (SignBean signBean:signAdapter.getData()){
            if (signBean.isSigned())
                total+=signBean.getCoin();
        }
        mBD.tvTotal.setText("签到积分："+total);
    }
}
