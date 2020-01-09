package com.yunkakeji.baidudemo.net.dataModel.ocr;

import com.yunkakeji.baidudemo.net.basic.NetProvider;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator
 * on 2020/1/6 16:08
 */
public class ORCViewModel extends ViewModel {

    private MutableLiveData<ResponseBody>mAccessToken;

    public MutableLiveData<ResponseBody> getAccessToken() {
        if (null==mAccessToken)
            mAccessToken=new MutableLiveData<>();
        return mAccessToken;
    }


    private MutableLiveData<ResponseBody>mIdCardInfo;

    public MutableLiveData<ResponseBody> getIdCardInfo() {

        if (mIdCardInfo==null)
            mIdCardInfo=new MutableLiveData<>();
        return mIdCardInfo;
    }

    private MutableLiveData<ResponseBody>mTextImageURL;

    public MutableLiveData<ResponseBody> getTextImageURL() {
        if (mTextImageURL==null)
            mTextImageURL=new MutableLiveData<>();
        return mTextImageURL;
    }

    /**
     * 获取百度的AccessToken
     * @param grantType 授权类型
     * @param clientID appID
     * @param clientSecret secretID
     */
    public Disposable postAccessToken(String grantType, String clientID, String clientSecret){

        return NetProvider.INSTANCE()
                .create(ORCNet.class)
                .baiDuAccessToken(grantType,clientID,clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                        mAccessToken.postValue(responseBody);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mAccessToken.postValue(null);
                    }
                });

    }

    /**
     * 身份证文字识别
     * @param token baidu AccessToken
     * @param imgBase64 身份证图片Base64编码字符串
     * @param id_card_side 正面或背面 front/back
     * @return
     */
    public Disposable postIdCardInfo(String token, String imgBase64, String id_card_side){

        return NetProvider.INSTANCE()
                .create(ORCNet.class)
                .idCardInfo(token,imgBase64,id_card_side)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                        mIdCardInfo.postValue(responseBody);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mIdCardInfo.postValue(null);
                    }
                });

    }

    /**
     * 通过文字图片的URL识别文字信息
     * @param token token
     * @param textImageURL 文字图片的URL
     * @return
     */
    public Disposable postImageTextURL(String token, String textImageURL){

        return NetProvider.INSTANCE()
                .create(ORCNet.class)
                .getTextByImageURL(token,textImageURL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                        mTextImageURL.postValue(responseBody);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mTextImageURL.postValue(null);
                    }
                });

    }
}
