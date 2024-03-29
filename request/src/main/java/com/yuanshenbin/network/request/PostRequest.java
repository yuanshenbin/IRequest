package com.yuanshenbin.network.request;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.SyncResult;

/**
 * Created by yuanshenbin on 2016/10/31.
 */
public class PostRequest extends BaseRequest<PostRequest> {


    public <T> PostRequest(Context context, String url, T param) {
        this.url = url;
        this.context = context;
        this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(param);

    }

    public PostRequest(String url, String param) {
        this.url = url;
        this.params = param;

    }

    public PostRequest(Context context, String url, String param) {
        this.url = url;
        this.context = context;
        this.params = param;

    }

    public <T> PostRequest(Context context, String url) {
        this.url = url;
        this.context = context;
        this.isPostMap = true;
    }

    public <T> PostRequest(String url) {
        this.url = url;
        this.isPostMap = true;
    }

    public <T> void execute(AbstractResponse<T> l) {
        if (isPostMap) {
            this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(this.mapParams);
        }
        requestMethod(RequestMethod.POST);
        RequestManager.getInstance().load(this, l);
    }

    public <T> SyncResult loadSync(AdaptResponse<T> l) {
        if (isPostMap) {
            this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(this.mapParams);
        }
        requestMethod(RequestMethod.POST);
        return RequestManager.getInstance().loadSync(this, l);
    }
}
