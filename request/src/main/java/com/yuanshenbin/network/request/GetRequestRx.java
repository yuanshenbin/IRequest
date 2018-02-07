package com.yuanshenbin.network.request;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AdaptResponse;


import io.reactivex.Observable;


/**
 * Created by Jacky on 2016/12/1.
 */
public class GetRequestRx extends BaseRequest<GetRequestRx> {


    public GetRequestRx(String url) {
        this.url = url;
    }

    public <T> Observable<T> execute(AdaptResponse<T> l) {
        requestMethod(RequestMethod.GET);
        if (this.mapParams.size() != 0) {
            this.url = Joint(this.url, this.mapParams);
        }
        return RequestManager.load(this, l);
    }
}