package com.yuanshenbin.nohttp;

import com.elvishew.xlog.XLog;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.util.StringUtils;

import rx.Observable;

/**
 * Created by Jacky on 2016/12/1.
 */
public class GetRequestRx extends BaseRequest<GetRequestRx> {


    public GetRequestRx(String url) {
        this.url = url;
    }

    public <T> Observable<T> execute(Class<T> classOfT) {
        requestMethod(RequestMethod.GET);
        this.url = StringUtils.Joint(this.url, this.mapParams);
        XLog.e(url);
        return RequestManager.load(this, classOfT);
    }
}
