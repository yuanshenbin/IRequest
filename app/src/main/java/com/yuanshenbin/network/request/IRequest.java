package com.yuanshenbin.network.request;


import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by Jacky on 2016/10/31.
 */
public class IRequest {
    /**
     * post请求
     */
    public static <T> PostRequest post(@NonNull Context context, @NonNull String url, @NonNull T params) {
        return new <T>PostRequest(context, url, params);
    }

    /**
     * post请求
     */
    public static <T> PostRequest post(@NonNull Context context, @NonNull String url) {
        return new <T>PostRequest(context, url);
    }

    /**
     * get请求
     */
    public static GetRequest get(@NonNull Context context, @NonNull String url) {
        return new GetRequest(context, url);
    }

    /**
     * 下载请求
     */
    public static DownloadRequest download(@NonNull Context context, @NonNull String url) {
        return new DownloadRequest(context, url);
    }

    /**
     * 上传请求
     */
    public static <T> UploadRequest upload(@NonNull Context context, @NonNull String url) {
        return new <T>UploadRequest(context, url);
    }

    /**
     * 上传请求
     */
    public static <T> UploadRequest upload(@NonNull Context context, @NonNull String url, @NonNull T params) {
        return new <T>UploadRequest(context, url, params);
    }

}
