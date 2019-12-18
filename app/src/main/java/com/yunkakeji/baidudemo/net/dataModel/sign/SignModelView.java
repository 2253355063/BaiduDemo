package com.yunkakeji.baidudemo.net.dataModel.sign;


import com.google.gson.Gson;
import com.yunkakeji.baidudemo.modules.bean.SignUpBean;
import com.yunkakeji.baidudemo.modules.bean.User;
import com.yunkakeji.baidudemo.net.basic.NetProvider;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Administrator
 * on 2019/6/18 11:20
 */
public class SignModelView extends ViewModel {

    private MutableLiveData<SignUpBean> mSignUpBean;

    private Gson gson = new Gson();

    public MutableLiveData<SignUpBean> getSignUpBean() {
        if (mSignUpBean == null)
            mSignUpBean = new MutableLiveData<>();
        return mSignUpBean;
    }

    /**
     * 用户注册方法
     *
     * @param mobilePhoneNumber 手机号
     * @param smsCode           验证码
     * @param password          密码
     */
    public Disposable postSignUpBean(String mobilePhoneNumber, String smsCode, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("mobilePhoneNumber", mobilePhoneNumber);
        map.put("smsCode", smsCode);
        map.put("password", password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
        return NetProvider.INSTANCE()
                .create(SignNet.class)
                .signUp(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<SignUpBean>>() {
                    @Override
                    public void accept(Response<SignUpBean> signUpBeanResponse) throws Exception {

                        int code = signUpBeanResponse.code();
                        SignUpBean signUpBean;
                        if (signUpBeanResponse.body() != null)
                            signUpBean = signUpBeanResponse.body();
                        else
                            signUpBean = new SignUpBean();
                        signUpBean.setCode(code);
                        mSignUpBean.postValue(signUpBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        SignUpBean signUpBean = new SignUpBean();
                        if (throwable instanceof HttpException) {
                            ResponseBody body = ((HttpException) throwable).response().errorBody();
                            if (body != null) {
                                signUpBean = gson.fromJson(body.string(), SignUpBean.class);
                            } else {
                                signUpBean.setThrowable(throwable);
                            }
                        } else {
                            signUpBean.setThrowable(throwable);
                        }
                        mSignUpBean.postValue(signUpBean);
                    }
                });
    }

    private MutableLiveData<User> mUser;

    public MutableLiveData<User> getUser() {
        if (mUser == null)
            mUser = new MutableLiveData<>();
        return mUser;
    }

    /**
     * 用户登录方法
     *
     * @param username 手机号
     * @param password 密码
     */
    public Disposable postSignIn(String username, String password) {

        return NetProvider.INSTANCE()
                .create(SignNet.class)
                .signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<User>>() {
                    @Override
                    public void accept(Response<User> userResponse) throws Exception {
                        int code = userResponse.code();
                        User user;
                        if (userResponse.body() != null)
                            user = userResponse.body();
                        else
                            user = new User();
                        user.setCode(code);

                        mUser.postValue(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        User user = new User();
                        if (throwable instanceof HttpException) {
                            ResponseBody body = ((HttpException) throwable).response().errorBody();
                            if (body != null) {
                                user = new Gson().fromJson(body.string(), User.class);

                            } else {
                                user.setThrowable(throwable);
                            }
                        } else {
                            user.setThrowable(throwable);
                        }
                        mUser.postValue(user);
                    }
                });

    }

}
