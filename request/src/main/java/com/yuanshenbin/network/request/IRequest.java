package com.yuanshenbin.network.request;


import android.content.Context;


/**
 * Created by yuanshenbin on 2016/10/31.
 */
public class IRequest {

    /**
     * get请求
     */
    public static GetRequest get(Context context, String url) {
        return new GetRequest(context, url);
    }
    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new GetRequest( url);
    }

    /**
     * get请求
     */
    public static <T> GetRequest get(Context context, String url, T params) {
        return new GetRequest(context, url, params);
    }
    /**
     * get请求
     */
    public static <T> GetRequest get( String url, T params) {
        return new GetRequest(url, params);
    }
    /**
     * get请求
     */
    public static GetRequest get(Context context, String url, String params) {
        return new GetRequest(context, url, params);
    }

    /**
     * post请求
     */
    public static <T> PostRequest post(Context context, String url, T params) {
        return new <T>PostRequest(context, url, params);
    }

    /**
     * post请求
     */
    public static PostRequest post(Context context, String url, String params) {
        return new PostRequest(context, url, params);
    }

    /**
     * post请求
     */
    public static PostRequest post(String url, String params) {
        return new PostRequest(url, params);
    }

    /**
     * post请求
     */
    public static <T> PostRequest post(Context context, String url) {
        return new <T>PostRequest(context, url);
    }

    /**
     * post请求
     */
    public static <T> PostRequest post(String url) {
        return new <T>PostRequest(url);
    }

    /**
     * 下载请求
     */
    public static DownloadRequest download(Context context, String url) {
        return new DownloadRequest(context, url);
    }

    /**
     * 上传请求
     */
    public static <T> UploadRequest upload(Context context, String url) {
        return new <T>UploadRequest(context, url);
    }

    /**
     * 上传请求
     */
    public static <T> UploadRequest upload(Context context, String url, T params) {
        return new <T>UploadRequest(context, url, params);
    }

    /**
     * 上传请求
     */
    public static UploadRequest upload(Context context, String url, String params) {
        return new UploadRequest(context, url, params);
    }

}
