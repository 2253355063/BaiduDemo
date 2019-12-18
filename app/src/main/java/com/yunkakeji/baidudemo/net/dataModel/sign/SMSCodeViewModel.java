package com.yunkakeji.baidudemo.net.dataModel.sign;

import com.google.gson.Gson;
import com.yunkakeji.baidudemo.modules.bean.SMSBean;
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
 * 获取验证码
 */
public class SMSCodeViewModel extends ViewModel {

    private  MutableLiveData<SMSBean> mSMSCode;

    private Gson gson=new Gson();

    public MutableLiveData<SMSBean> getSMSCode() {
        if (mSMSCode==null)
            mSMSCode=new MutableLiveData<>();
        return mSMSCode;
    }

    /**
     * 获取短信
     * @param mobilePhoneNumber 手机号
     * @param template 模板
     */
    public Disposable postSMSCode(String mobilePhoneNumber, String template){
        Map<String, String> map=new HashMap<>();
        map.put("mobilePhoneNumber",mobilePhoneNumber);
//        map.put("template",template);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(map));
        return NetProvider.INSTANCE()
                .create(CommonNet.class)
                .requestSmsCode(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<SMSBean>>() {
                    @Override
                    public void accept(Response<SMSBean> smsBeanResponse) throws Exception {

                        int code=smsBeanResponse.code();
                        SMSBean smsBean;
                        if (smsBeanResponse.body()!=null)
                            smsBean=smsBeanResponse.body();
                        else
                            smsBean=new SMSBean();
                        smsBean.setCode(code);
                        mSMSCode.postValue(smsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        SMSBean smsBean = new SMSBean();
                        if (throwable instanceof HttpException) {
                            ResponseBody body = ((HttpException) throwable).response().errorBody();
                            if (body != null) {
                                smsBean = gson.fromJson(body.string(), SMSBean.class);
                            } else {
                                smsBean.setThrowable(throwable);
                            }
                        } else {
                            smsBean.setThrowable(throwable);
                        }
                        mSMSCode.postValue(smsBean);
                    }
                });
    }
}
