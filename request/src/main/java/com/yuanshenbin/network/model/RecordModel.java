package com.yuanshenbin.network.model;

import java.io.Serializable;
import java.util.Map;

/**
 * author : yuanshenbin
 * time   : 2018/9/3
 * desc   : 记录请求数据
 */

public class RecordModel implements Serializable {

    private Exception exception;
    private String url;
    private String param;
    private String result;
    private long requestTime;
    private int httpCode;


    private Map<String, String> headers;
    /**
     * 链接质量
     */
    private String connectionQuality;

    /**
     * 每秒流量值
     */
    private double KBitsPerSecond;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public RecordModel() {
    }

    public RecordModel(String url, String param, String result, String connectionQuality, double KBitsPerSecond, Exception e, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
        this.exception = e;
        this.headers = headers;
    }

    public RecordModel(String url, String param, String result, Exception e, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.exception = e;
        this.headers = headers;
    }

    @Deprecated
    public RecordModel(String url, String param, String result, String connectionQuality, double KBitsPerSecond, Exception e) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
        this.exception = e;
    }

    @Deprecated
    public RecordModel(String url, String param, String result, long requestTime, String connectionQuality, double KBitsPerSecond) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
    }

    public RecordModel(String url, String param, String result, long requestTime, String connectionQuality, double KBitsPerSecond, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
        this.headers = headers;
    }

    public RecordModel(String url, String param, String result, long requestTime, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.headers = headers;
    }

    @Deprecated
    public RecordModel(String url, String param, String result, long requestTime, String connectionQuality, double KBitsPerSecond, Exception e) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
        this.exception = e;
    }

    public RecordModel(String url, String param, String result, long requestTime, String connectionQuality, double KBitsPerSecond, Exception e, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.connectionQuality = connectionQuality;
        this.KBitsPerSecond = KBitsPerSecond;
        this.exception = e;
        this.headers = headers;
    }



    public RecordModel(String url, String param, String result, long requestTime, Exception e, Map<String, String> headers) {
        this.url = url;
        this.param = param;
        this.result = result;
        this.requestTime = requestTime;
        this.exception = e;
        this.headers = headers;
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


    public String getConnectionQuality() {
        return connectionQuality;
    }

    public void setConnectionQuality(String connectionQuality) {
        this.connectionQuality = connectionQuality;
    }

    public double getKBitsPerSecond() {
        return KBitsPerSecond;
    }

    public void setKBitsPerSecond(double KBitsPerSecond) {
        this.KBitsPerSecond = KBitsPerSecond;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
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
