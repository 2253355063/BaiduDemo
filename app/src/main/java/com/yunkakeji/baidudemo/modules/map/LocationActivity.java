package com.yunkakeji.baidudemo.modules.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.base.activity.BaseActivity;
import com.yunkakeji.baidudemo.databinding.ActivityLocationBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import io.reactivex.annotations.NonNull;

/**
 * Created by Administrator
 * on 2020/1/9 15:38
 * 百度定位
 */
public class LocationActivity extends BaseActivity {

    private ActivityLocationBinding mBD;

    private BaiduMap baiduMap;

    private LocationClient mLocationClient;

    private boolean isFirstLocate = true;

    @Override
    protected void initView() {

        mBD = DataBindingUtil.setContentView(this, R.layout.activity_location);

        baiduMap = mBD.mapView.getMap();

        // 显示我自己
        baiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void initData() {
        initLocationOption();
        List<String> permissionLList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionLList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionLList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionLList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionLList.isEmpty()) {
            String[] permissions = permissionLList.toArray(new String[permissionLList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this, permissions, 1);
        } else {
            startLocationService();
        }


    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有授权才能使用此功能",
                                    Toast.LENGTH_LONG).show();
                            // 此处如果不启动该权限强制退出App时使用
//                            finish();
                            return;
                        }
                    }
                    startLocationService();
                } else {
                    Toast.makeText(this, "发生未知错误",
                            Toast.LENGTH_LONG).show();
                    // 此处如果不启动该权限强制退出App时使用
//                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBD.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBD.mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mBD.mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    /**
     * 初始化定位参数配置
     */
    private void initLocationOption() {
        // 声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        locationOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为 bd09ll;
        locationOption.setCoorType("bd09ll");
        // 可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(5000);
        // 可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        // 可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        // 可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候
        // 杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe
        // 里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        // 可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        // 可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        // 可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        // 设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动
        // 回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化
        // 就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
        // 设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动
        // 回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1,
                LocationClientOption.LOC_SENSITIVITY_HIGHT);

        MyLocationListener myLocationListener = new MyLocationListener();
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myLocationListener);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(locationOption);
    }

    public void startLocationService() {
        if (mLocationClient != null) {
            //开始定位
            mLocationClient.start();

        } else {
            initLocationOption();
        }
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;

            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_mark1);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        baiduMap.setMyLocationData(data);
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int locType = location.getLocType();
            if (locType == BDLocation.TypeGpsLocation
                    || locType == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }

            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();


            StringBuilder sb = new StringBuilder();
            if (locType == BDLocation.TypeGpsLocation) {
                sb.append("GPS获取位置:\n");
            } else if (locType == BDLocation.TypeNetWorkLocation) {
                sb.append("网络获取位置:\n");
            }
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            sb.append(" 经度: ").append(longitude);
            sb.append(" 纬度: ").append(latitude);

            String country = location.getCountry();
            String province = location.getProvince();
            String city = location.getCity();
            String district = location.getDistrict();
            String street = location.getStreet();
            String streetNumber = location.getStreetNumber();
            sb.append(country).append(" ");
            sb.append(province);
            sb.append(city);
            sb.append(district);
            sb.append(street);
            sb.append(streetNumber).append(" ");

            mBD.tvInfo.setText(sb.toString());
        }
    }
}


