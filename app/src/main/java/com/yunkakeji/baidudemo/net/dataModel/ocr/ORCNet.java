package com.yunkakeji.baidudemo.net.dataModel.ocr;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;

/**
 * Created by Administrator
 * on 2020/1/6 16:03
 * 文字识别接口
 */
public interface ORCNet {

    /**
     * 获取百度的AccessToken
     * @param grantType 授权类型
     * @param clientID appkey
     * @param clientSecret secretID
     * @return
     */
    @FormUrlEncoded
    @HTTP(method = "POST",path = "/oauth/2.0/token",hasBody = true)
    Observable<ResponseBody>baiDuAccessToken(@Field(value = "grant_type") String grantType, @Field(value = "client_id") String clientID, @Field(value = "client_secret") String clientSecret);

    /**
     * 身份证图片识别
     * @param token 百度的accessToken
     * @param imgBase64  身份证图片base64字符串
     * @param id_card_side 正面/背面  front/back
     * @return
     */
    @FormUrlEncoded
    @HTTP(method = "POST",path = "/rest/2.0/ocr/v1/idcard",hasBody = true)
    Observable<ResponseBody>idCardInfo(@Field(value = "access_token") String token, @Field(value = "image") String imgBase64, @Field(value = "id_card_side") String id_card_side);

    /**
     * 通过文字图片的URL地址获取文字
     * @param token token
     * @param textImageURL 图片文件的URL地址
     * @return
     */
    @FormUrlEncoded
    @HTTP(method = "POST",path = "/rest/2.0/ocr/v1/general_basic",hasBody = true)
    Observable<ResponseBody>getTextByImageURL(@Field(value = "access_token") String token, @Field(value = "url") String textImageURL);
}
