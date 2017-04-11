package com.yuanshenbin.nohttp;

/**
 * Created by Jacky on 2016/12/1.
 */

public class IRequestRx {

    /**
     * post请求
     */
    public static<T> PostRequestRx post( String url) {
        return new <T>PostRequestRx( url);
    }
    /**
     * get请求
     */
    public static GetRequestRx get( String url) {
        return new GetRequestRx( url);
    }

}
