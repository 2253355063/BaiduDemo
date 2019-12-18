package com.yunkakeji.baidudemo.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.ycbjie.webviewlib.X5WebUtils;
import com.yunkakeji.baidudemo.R;
import com.yunkakeji.baidudemo.modules.bean.User;

import androidx.multidex.MultiDex;

/**
 * Created by Administrator
 * on 2019/11/5 10:54
 */
public class APP extends Application {

    private static User instance;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                // layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.darker_gray);//全局设置主题颜色

//                return new DropBoxHeader(context);
//                return new FunGameBattleCityHeader(context);
//                return new FunGameHitBlockHeader(context);
//                return new BezierCircleHeader(context);
                MaterialHeader materialHeader=new MaterialHeader(context);
                materialHeader.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);
                return materialHeader;
                //return new BezierRadarHeader(context);
                //return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorAccent, android.R.color.darker_gray);//全局设置主题颜色

                return new BallPulseFooter(context);
                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        X5WebUtils.init(getApplicationContext());
        /**
         * 预先加载一级列表显示 全国所有城市市的数据
         */
        CityListLoader.getInstance().loadCityData(this);

        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);
    }

    public static User getInstance() {

        if (instance==null){
            String userJson= SPUtils.getInstance().getString(User.class.getSimpleName());
            if (!TextUtils.isEmpty(userJson)){
                Gson gson=new Gson();
                instance=gson.fromJson(userJson,User.class);
            }
        }
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplicationContext());
    }
}
