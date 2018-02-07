package com.yuanshenbin.network;

import com.yanzhenjie.nohttp.rest.Request;

/**
 * author : yuanshenbin
 * time   : 2018/1/27
 * desc   :
 */

public interface IHeader {

    <T> void onHeader(Request<T> request);
}
