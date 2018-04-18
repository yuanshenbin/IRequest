package com.yuanshenbin.network.request;

import android.text.TextUtils;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yuanshenbin.network.manager.NetworkManager;

/**
 * author : yuanshenbin
 * time   : 2018/4/17
 * desc   :
 */

public class StringRequest extends Request<String> {

    public StringRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = com.yanzhenjie.nohttp.rest.StringRequest.parseResponseString(responseHeaders, responseBody);
        NetworkManager.getInstance().getInitializeConfig().getIPrintLog().onPrintResult(result);
        if (TextUtils.isEmpty(result)) {
            throw new NullPointerException("EntityRequest------result  null");
        } else {
            return result;
        }
    }
}
