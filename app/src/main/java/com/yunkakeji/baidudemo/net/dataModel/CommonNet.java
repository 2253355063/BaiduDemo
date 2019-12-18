package com.yunkakeji.baidudemo.net.dataModel;


import com.yunkakeji.baidudemo.modules.bean.BaseResultBean;
import com.yunkakeji.baidudemo.modules.bean.SMSBean;
import com.yunkakeji.baidudemo.net.basic.Api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * Created by Administrator
 * on 2019/9/4 17:51
 * 常用的接口
 */
public interface CommonNet {

    /**
     * 获取验证码
     * @param body 请求体
     * @return
     */
    @HTTP(method = "POST",path = "1/requestSmsCode",hasBody = true)
    Observable<Response<SMSBean>> requestSmsCode(@Body RequestBody body);

    /**
     * 检查登陆是否过期接口
     * @param objectID 用户数据id
     * @return
     */
    @HTTP(method = "GET",path = Api.CHECK_SESSION,hasBody = false)
    Observable<Response<BaseResultBean>> checkSession(@Path("objectID") String objectID);

    /**
     * 重置密码接口
     * @param smsCode 验证码
     * @param body 请求体
     * @return
     */
    @HTTP(method = "PUT",path = "1/resetPasswordBySmsCode/{smsCode}",hasBody = true)
    Observable<Response<BaseResultBean>> resetPasswordBySmsCode(@Path("smsCode") String smsCode, @Body RequestBody body);

}
