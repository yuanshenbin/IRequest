package com.yuanshenbin.network.request;

import android.text.TextUtils;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yuanshenbin.util.ILogger;
import com.yuanshenbin.util.JsonUtils;

/**
 * Created by Jacky on 2016/10/31.
 */
public class ArrayRequest<T> extends RestRequest<T> {
    private Class<T> classOfT;


    public ArrayRequest(String url, RequestMethod requestMethod, Class<T> classOfT) {
        super(url, requestMethod);
        this.classOfT = classOfT;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (TextUtils.isEmpty(result)) {
            ILogger.e("", new NullPointerException());
            throw new NullPointerException();
        } else {
            ILogger.json(result);
            return JsonUtils.object(result, classOfT);
        }
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }
}
