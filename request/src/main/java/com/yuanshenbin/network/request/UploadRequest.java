package com.yuanshenbin.network.request;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.AbstractUploadResponse;


/**
 * Created by Jacky on 2016/10/31.
 */
public class UploadRequest extends BaseRequest<UploadRequest> {
    public <T> UploadRequest(Context context, String url, T params) {
        this.url = url;
        this.context = context;
        this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(params);
    }

    public <T> UploadRequest(Context context, String url) {
        this.url = url;
        this.context = context;
        this.isPostMap = true;
    }

    public <T> void execute(AbstractUploadResponse<T> l) {
        if (isPostMap) {
            this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(mapParams);
        }
        requestMethod(RequestMethod.POST);
        RequestManager.upload(this, l);
    }
}