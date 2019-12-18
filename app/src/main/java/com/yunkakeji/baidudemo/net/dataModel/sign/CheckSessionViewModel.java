package com.yunkakeji.baidudemo.net.dataModel.sign;

import com.google.gson.Gson;
import com.yunkakeji.baidudemo.modules.bean.BaseResultBean;
import com.yunkakeji.baidudemo.net.basic.NetProvider;
import com.yunkakeji.baidudemo.net.dataModel.CommonNet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/***
 * 检查登陆是否过期
 *
 */
public class CheckSessionViewModel extends ViewModel {

    /**
     * 检查登陆是否过期 返回的数据
     */
    private MutableLiveData<BaseResultBean> mCheckSession;

    public MutableLiveData<BaseResultBean> getCheckSession() {
        if (mCheckSession==null)
            mCheckSession=new MutableLiveData<>();
        return mCheckSession;
    }

    /**
     *  检查登陆是否过期方法
     * @param objectID 用户id
     */
    public Disposable getCheckSession(String objectID){

        return NetProvider.INSTANCE()
                .create(CommonNet.class)
                .checkSession(objectID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<BaseResultBean>>() {
                    @Override
                    public void accept(Response<BaseResultBean> baseResultBeanResponse) throws Exception {

                        int code=baseResultBeanResponse.code();
                        BaseResultBean baseResultBean;
                        if (baseResultBeanResponse.body()!=null)
                            baseResultBean=baseResultBeanResponse.body();
                        else
                            baseResultBean=new BaseResultBean();
                        baseResultBean.setCode(code);

                        mCheckSession.postValue(baseResultBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        BaseResultBean baseResultBean = new BaseResultBean();
                        if (throwable instanceof HttpException) {
                            ResponseBody body = ((HttpException) throwable).response().errorBody();
                            if (body != null) {
                                baseResultBean = new Gson().fromJson(body.string(), BaseResultBean.class);
                            } else {
                                baseResultBean.setThrowable(throwable);
                            }

                        } else {
                            baseResultBean.setThrowable(throwable);
                        }
                        mCheckSession.postValue(baseResultBean);
                    }
                });
    }
}
