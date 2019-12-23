package com.yuanshenbin.network.request;


import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.manager.NetworkManager;

import io.reactivex.Observable;


/**
 * Created by yuanshenbin on 2016/12/1.
 */
public class PostRequestRx extends BaseRequest<PostRequestRx> {

    public <T> PostRequestRx(String url) {
        this.url = url;
        this.isPostMap = true;
    }

    public <T> PostRequestRx(String url, T param) {
        this.url = url;
        this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(param);
    }

    public <T> Observable<T> execute(AdaptResponse<T> l) {
        if (isPostMap) {
            this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(this.mapParams);
        }
        requestMethod(RequestMethod.POST);
        return RequestManager.getInstance().load(this, l);
    }
}
