package com.android.frame.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/9.
 * 网络请求参数类
 */
public class RequestParams {

    private List<Param> params;


    public RequestParams() {
        params = new ArrayList<>();
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public void addParam(String key, String value) {
        params.add(new Param(key, value));
    }

    public void addParam(String key, File file) {
        params.add(new Param(key, file));
    }

    public class Param {
        private String key;
        private Object value;

        public Param() {
        }

        public Param(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

}
