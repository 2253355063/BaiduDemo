package com.yunkakeji.baidudemo.net.dataModel.resetpassword;


import com.google.gson.Gson;
import com.yunkakeji.baidudemo.modules.bean.BaseResultBean;
import com.yunkakeji.baidudemo.net.basic.NetProvider;
import com.yunkakeji.baidudemo.net.dataModel.CommonNet;

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
 * 忘记密码通过短信验证码重置
 */
public class ResetPasswordBySmsCodeViewModel extends ViewModel {


    private MutableLiveData<BaseResultBean> mResetPasswordBySmsCodeNet;

    private Gson gson=new Gson();

    public MutableLiveData<BaseResultBean> getResetPasswordBySmsCodeNet() {
        if (mResetPasswordBySmsCodeNet==null)
            mResetPasswordBySmsCodeNet=new MutableLiveData<>();
        return mResetPasswordBySmsCodeNet;
    }

    /**
     *  忘记密码通过短信验证码重置方法
     * @param smsCode 验证码
     * @param password 密码
     */
    public Disposable putResetPasswordBySmsCodeNet(String smsCode, String password){

        Map<String, String> map=new HashMap<>();
        map.put("password",password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
       return NetProvider.INSTANCE()
                .create(CommonNet.class)
                .resetPasswordBySmsCode(smsCode,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<BaseResultBean>>() {
                    @Override
                    public void accept(Response<BaseResultBean> baseResultBeanResponse) throws Exception {
                        int code = baseResultBeanResponse.code();
                        BaseResultBean baseResultBean;
                        if (baseResultBeanResponse.body()!=null)
                            baseResultBean=baseResultBeanResponse.body();
                        else
                            baseResultBean=new BaseResultBean();
                        baseResultBean.setCode(code);
                        mResetPasswordBySmsCodeNet.postValue(baseResultBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        BaseResultBean baseResultBean = new BaseResultBean();
                        if (throwable instanceof HttpException) {
                            ResponseBody body = ((HttpException) throwable).response().errorBody();
                            if (body != null) {
                                baseResultBean = gson.fromJson(body.string(), BaseResultBean.class);
                            } else {
                                baseResultBean.setThrowable(throwable);
                            }
                        } else {
                            baseResultBean.setThrowable(throwable);
                        }
                        mResetPasswordBySmsCodeNet.postValue(baseResultBean);
                    }
                });
    }
}
