package com.yunkakeji.baidudemo.net.dataModel.sign;

import com.yunkakeji.baidudemo.modules.bean.SignUpBean;
import com.yunkakeji.baidudemo.modules.bean.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

/**
 * Created by Administrator
 * on 2019/9/4 17:47
 * 登录注册相关的接口
 */
public interface SignNet {

    /**
     * 登录接口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @HTTP(method = "GET",path = "1/login")
    Observable<Response<User>> signIn(@Query("username") String username, @Query("password") String password);

    /**
     * 注册接口
     * @param body 请求体
     * @return
     */
    @HTTP(method = "POST",path = "1/users",hasBody = true)
    Observable<Response<SignUpBean>> signUp(@Body RequestBody body);
}
