package com.yunkakeji.baidudemo.net.basic;

import java.util.List;

/**
 * 网络请求数据list泛型
 * @param <T>
 */
public class BaseListResultResp<T> extends NetBasicResp {

    private List<T> results;//网络请求List数据泛型

    public List<T> getResults() {
        return results;
    }
}
