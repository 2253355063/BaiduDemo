package com.yunkakeji.baidudemo.modules.bean;


import com.yunkakeji.baidudemo.net.basic.NetBasicResp;

/**
 * 短信验证码
 */
public class SMSBean extends NetBasicResp {
    /**
     * 短信验证码ID，可用于后面使用查询短信状态接口来查询该短信验证码是否发送成功和是否验证过：
     */
    private String smsId;

    public String getSmsId() {
        return smsId;
    }

}
