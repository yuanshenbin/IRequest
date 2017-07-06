package com.yuanshenbin.network.request;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.util.ILogger;
import com.yuanshenbin.util.JsonUtils;

import io.reactivex.Observable;


/**
 * Created by Jacky on 2016/12/1.
 */
public class PostRequestRx extends BaseRequest<PostRequestRx> {

    public <T> PostRequestRx(String url) {
        this.url = url;
        this.params = JsonUtils.string(mapParams);
        ILogger.json(this.params);
    }

    public <T> Observable<T> execute(Class<T> classOfT) {
        requestMethod(RequestMethod.POST);
        return RequestManager.load(this, classOfT);
    }
}
