package com.yuanshenbin.network.request;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractUploadResponse;
import com.yuanshenbin.network.manager.NetworkManager;

import java.util.HashMap;


/**
 * Created by yuanshenbin on 2016/10/31.
 */
public class UploadRequest extends BaseRequest<UploadRequest> {
    public <T> UploadRequest(Context context, String url, T param) {
        this.url = url;
        this.context = context;
        this.mapParams = NetworkManager.getInstance().getInitializeConfig().getFromJson().onJsonToMap(param);
    }

    public UploadRequest(Context context, String url, String param) {
        this.url = url;
        this.context = context;
        this.mapParams = NetworkManager.getInstance().getInitializeConfig().getFromJson().onFromJson(param, HashMap.class);
    }

    public <T> UploadRequest(Context context, String url) {
        this.url = url;
        this.context = context;
        this.isPostMap = true;
    }

    public <T> void execute(AbstractUploadResponse<T> l) {
        requestMethod(RequestMethod.POST);
        RequestManager.getInstance().upload(this, l);
    }
}
