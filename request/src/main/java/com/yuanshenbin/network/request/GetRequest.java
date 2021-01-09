package com.yuanshenbin.network.request;


import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.manager.NetworkManager;

/**
 * Created by Jacky on 2016/10/31.
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public <T> GetRequest(Context context, String url, T param) {
        this.context = context;
        this.url = Joint(url, NetworkManager.getInstance().getInitializeConfig().getFromJson().onJsonToMap(param));
    }

    public <T> GetRequest(Context context, String url) {
        this.url = url;
        this.context = context;

    }

    public <T> void execute(AbstractResponse<T> l) {
        requestMethod(RequestMethod.GET);
        if (this.mapParams.size() != 0) {
            this.url = Joint(this.url, this.mapParams);
        }
        RequestManager.getInstance().load(this, l);
    }

    public Response<String> loadSynch() {
        requestMethod(RequestMethod.GET);
        if (this.mapParams.size() != 0) {
            this.url = Joint(this.url, this.mapParams);
        }
        return RequestManager.getInstance().loadSynch(this);
    }
}
