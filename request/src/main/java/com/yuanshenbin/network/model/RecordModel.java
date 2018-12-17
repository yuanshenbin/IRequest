package com.yuanshenbin.network.model;

import java.io.Serializable;

/**
 * author : yuanshenbin
 * time   : 2018/9/3
 * desc   : 记录请求数据
 */

public class RecordModel implements Serializable {

    private  String url;
    private String param;
    private  String result;
    private  long  requestTime;

    public RecordModel() {
    }

    public RecordModel(String url, String param, String result) {
        this.url = url;
        this.param = param;
        this.result = result;
    }

    public RecordModel(String url, String param, String result, long requestTime) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "RecordModel{" +
                "url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", result='" + result + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }
}
