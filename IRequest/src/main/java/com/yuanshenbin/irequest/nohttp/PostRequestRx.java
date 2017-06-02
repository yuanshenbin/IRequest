package com.yuanshenbin.irequest.nohttp;

import com.elvishew.xlog.XLog;
import com.yuanshenbin.irequest.util.JsonUtils;
import com.yanzhenjie.nohttp.RequestMethod;

import rx.Observable;

/**
 * Created by Jacky on 2016/12/1.
 */
public class PostRequestRx extends BaseRequest<PostRequestRx> {

    public <T> PostRequestRx(String url) {
        this.url = url;
        this.params = JsonUtils.string(mapParams);
        XLog.json(this.params);
    }

    public <T> Observable<T> execute(Class<T> classOfT) {
        requestMethod(RequestMethod.POST);
        return RequestManager.load(this, classOfT);
    }
}
