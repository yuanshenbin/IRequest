package com.libhttp.nohttp;

import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.libhttp.util.JsonUtils;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

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
            XLog.e("", new NullPointerException());
            throw new NullPointerException();
        } else {
            XLog.json(result);
            return JsonUtils.object(result, classOfT);
        }
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }
}
