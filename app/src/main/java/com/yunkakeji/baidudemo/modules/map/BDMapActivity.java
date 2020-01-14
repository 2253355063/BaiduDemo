package com.yunkakeji.baidudemo.modules.map;


import android.graphics.Color;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityBaiduMapBinding;

import androidx.databinding.DataBindingUtil;

/**
 * Created by Administrator
 * on 2020/1/7 11:57
 */
public class BDMapActivity extends BaseActivity {

    private ActivityBaiduMapBinding mBD;
    private BaiduMapOptions options = new BaiduMapOptions();

    private BaiduMap baiduMap;

    @Override
    protected void initView() {
        mBD= DataBindingUtil.setContentView(this, R.layout.activity_baidu_map);

        baiduMap=mBD.bmapView.getMap();

        //分别代表严重拥堵，拥堵，缓行，畅通
        baiduMap.setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
        changeMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBD.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_common:
                        changeMapType(BaiduMap.MAP_TYPE_NORMAL);
                        break;
                    case R.id.rb_wx:
                        changeMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        break;
                }
            }
        });

        mBD.chLk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isPressed())
                    return;

                //开启交通图
                baiduMap.setTrafficEnabled(b);
                mBD.bmapView.refreshDrawableState();
            }
        });
        mBD.chCity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isPressed())
                    return;
                //开启热力图
                baiduMap.setBaiduHeatMapEnabled(b);
                mBD.bmapView.refreshDrawableState();
            }
        });

        //实例化UiSettings类对象
        UiSettings uiSettings = baiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        uiSettings.setCompassEnabled(true);
    }

    @Override
    protected void initData() {

    }

    private void changeMapType(int value){

        baiduMap.setMapType(value);
        mBD.bmapView.refreshDrawableState();
    }



    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBD.bmapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBD.bmapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBD.bmapView.onDestroy();
    }
}
