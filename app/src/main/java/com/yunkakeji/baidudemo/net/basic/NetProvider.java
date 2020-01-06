package com.yunkakeji.baidudemo.net.basic;

import com.yunkakeji.baidudemo.app.APP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetProvider {

//    public static  String BASE_URL_Bmob = "https://api2.bmob.cn/";//bmob地址
    public static  String BASE_URL_BaiDu = "https://aip.baidubce.com/";//百度地址
    private static final HttpLoggingInterceptor sLogging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //Logger.t(TAG).d(message);
            //Logger.t(TAG).json(message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final BaseUrlInterceptor baseUrlInterceptor=new BaseUrlInterceptor();
    public static Retrofit INSTANCE() {

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL_BaiDu)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(NetTimeOutUtil.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(NetTimeOutUtil.DEFAULT_DATA_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(NetTimeOutUtil.DEFAULT_DATA_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request requestOrigin = chain.request();
                                Headers headersOrigin = requestOrigin.headers();
                                String token="";
                                if (APP.getInstance()!=null){
                                    token =APP.getInstance().getSessionToken();
                                }
                                Headers headers = headersOrigin.newBuilder()
                                        .set("Content-Type", "application/json;charset=UTF-8")
//                                        .set("Connection","keep-alive")
                                        .set("Accept","*/*")
                                        .set("X-Bmob-Application-Id","99321c19be9d32f9b5118d674188f5ba")
                                        .set("X-Bmob-REST-API-Key","68646df0e92cc6908dadc527d76f8f3a")
                                        .set("X-Bmob-Session-Token",token )
                                        .set("Accept-Language", "CN")
                                        .build();
                                Request request = requestOrigin.newBuilder().headers(headers).build();
                                return chain.proceed(request);
                            }
                        })
                        .addInterceptor(sLogging)
//                        .addInterceptor(baseUrlInterceptor)
                        .addInterceptor(new TokenTimeoutInterceptor())
                        .build())
                .build();
    }

}
