package com.yuanshenbin.network.request;

import com.elvishew.xlog.XLog;
import com.yuanshenbin.util.JsonUtils;

import java.util.Map;

import rx.Observable;

/**
 * Created by Jacky on 2017/2/4.
 */

public class UploadRequestRx extends BaseRequest<UploadRequestRx> {
    public <T> UploadRequestRx(String url, T params) {
        this.url = url;
        this.mapParams = (Map<Object, Object>) params;
        XLog.json(JsonUtils.string(this.mapParams));
    }
    public <T> Observable<T> execute(Class<T> classOfT) {
        return RequestManager.createUploadArray(this, classOfT);
    }

}
