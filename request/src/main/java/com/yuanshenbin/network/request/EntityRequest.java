package com.yuanshenbin.network.request;

import android.text.TextUtils;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yuanshenbin.network.manager.NetworkManager;


import java.lang.reflect.Type;

/**
 * Created by Jacky on 2016/10/31.
 */
public class EntityRequest<T> extends RestRequest<T> {
    private Type mType;


    public EntityRequest(String url, RequestMethod requestMethod, Type type) {
        super(url, requestMethod);
        this.mType = type;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (TextUtils.isEmpty(result)) {
            throw new NullPointerException("EntityRequest------result  null");
        } else {
            return NetworkManager.getInstance().getInitializeConfig().getFromJson().onFromJson(result, mType);
        }
    }
}
