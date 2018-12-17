package com.yuanshenbin.network.request;


import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.manager.NetworkManager;

import io.reactivex.Observable;


/**
 * Created by yuanshenbin on 2017/2/4.
 */

public class UploadRequestRx extends BaseRequest<UploadRequestRx> {
    public <T> UploadRequestRx(String url) {
        this.url = url;
        this.isPostMap = true;
    }

    public <T> UploadRequestRx(String url, T param) {
        this.url = url;
        this.mapParams = NetworkManager.getInstance().getInitializeConfig().getFromJson().onJsonToMap(param);

    }

    public <T> Observable<T> execute(AdaptResponse<T> l) {
        return RequestManager.getInstance().upload(this, l);
    }

}
