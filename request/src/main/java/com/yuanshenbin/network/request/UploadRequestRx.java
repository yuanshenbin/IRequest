package com.yuanshenbin.network.request;


import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.manager.NetworkManager;

import io.reactivex.Observable;


/**
 * Created by Jacky on 2017/2/4.
 */

public class UploadRequestRx extends BaseRequest<UploadRequestRx> {
    public <T> UploadRequestRx(String url) {
        this.url = url;
        this.isPostMap = true;
    }

    public <T> UploadRequestRx(String url, T params) {
        this.url = url;
        this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(params);

    }

    public <T> Observable<T> execute(AdaptResponse<T> l) {
        if (isPostMap) {
            this.params = NetworkManager.getInstance().getInitializeConfig().getFromJson().onToJson(mapParams);
        }
        return RequestManager.upload(this, l);
    }

}
