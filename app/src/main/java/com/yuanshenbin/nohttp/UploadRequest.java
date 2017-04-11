package com.yuanshenbin.nohttp;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;

import java.util.Map;


/**
 * Created by Jacky on 2016/10/31.
 */
public class UploadRequest extends BaseRequest<UploadRequest> {
    public <T> UploadRequest(Context context, String url, T params) {
        this.url = url;
        this.context = context;
        this.mapParams = (Map<Object, Object>) params;
    }

    public <T> void execute(RequestListener<T> l) {
        requestMethod(RequestMethod.POST);
        RequestManager.upload(this, l);
    }
}