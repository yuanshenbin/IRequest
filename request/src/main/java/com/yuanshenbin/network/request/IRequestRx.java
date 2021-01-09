package com.yuanshenbin.network.request;

/**
 * Created by Jacky on 2016/12/1.
 */

public class IRequestRx {


    public static <T> PostRequestRx post(String url) {

        return new <T>PostRequestRx(url);
    }

    public static <T> PostRequestRx post(String url, T param) {
        return new <T>PostRequestRx(url, param);
    }

    public static <T> UploadRequestRx upload(String url, T param) {
        return new <T>UploadRequestRx(url, param);
    }

    public static <T> UploadRequestRx upload(String url) {

        return new <T>UploadRequestRx(url);
    }

    public static GetRequestRx get(String url) {
        return new GetRequestRx(url);
    }
}
