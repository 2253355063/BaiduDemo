package com.yunkakeji.baidudemo.modules.bean;

/**
 * 文件实体类
 */
public class BmobFile {

    /**
     * filename : bg_launch_default2.png
     * __type : File
     * cdn : upyun
     * url : http://bmob-cdn-12912.b0.upaiyun.com/2018/07/07/1702c598402275d480649c423b71f33b.png
     */
    private String filename;
    private String __type;
    private String cdn;
    private String url;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public String get__type() {
        return __type;
    }

    public String getCdn() {
        return cdn;
    }

    public String getUrl() {
        return url;
    }
}
