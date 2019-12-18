package com.yunkakeji.baidudemo.modules.bean;

import com.yunkakeji.baidudemo.net.basic.NetBasicResp;

/**
 * 用户实体
 */
public class User extends NetBasicResp {

    /**
     * mobilePhoneNumber : mobilePhoneNumber
     * createdAt : YYYY-mm-dd HH:ii:ss
     * sessionToken : sessionToekn
     * mobilePhoneVerified : true
     * objectId : objectId
     * username : username
     * updatedAt : YYYY-mm-dd HH:ii:ss
     */
    private String mobilePhoneNumber;
    private String createdAt;
    private String sessionToken;
    private boolean mobilePhoneVerified;
    private String objectId;
    private String username;
    private String updatedAt;

    /**
     * 头像
     */
    private BmobFile headerImage;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别 false为女性，true为男性，默认有男性
     */
    private Boolean gender;

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getSessionToken() {
        if (sessionToken==null)
            sessionToken="";
        return sessionToken;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return username;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public BmobFile getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(BmobFile headerImage) {
        this.headerImage = headerImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

}
