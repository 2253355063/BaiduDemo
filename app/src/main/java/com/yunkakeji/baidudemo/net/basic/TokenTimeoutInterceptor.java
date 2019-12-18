package com.yunkakeji.baidudemo.net.basic;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator
 * on 2019/10/23 10:32
 * 启动时验证token是否过期超时拦截器
 */
public class TokenTimeoutInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String questUrl = request.url().toString();
        boolean checkSession = questUrl.contains(Api.CHECK_SESSION);
//        if (checkSession){
//            return chain.withConnectTimeout(NetTimeOutUtil.CHECK_SESSION_TIMEOUT, TimeUnit.SECONDS)
//                    .withReadTimeout(NetTimeOutUtil.CHECK_SESSION_TIMEOUT,TimeUnit.SECONDS)
//                    .proceed(request);
//
//        }
        return chain.proceed(request);
    }

}
