package com.libhttp.nohttp;


import android.content.Context;

import com.elvishew.xlog.XLog;
import com.libhttp.util.StringUtils;
import com.yanzhenjie.nohttp.RequestMethod;


/**
 * Created by Jacky on 2016/10/31.
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(Context context, String url) {
        this.url = url;
        this.context = context;

    }
    public <T> void execute(RequestListener<T> l) {
        requestMethod(RequestMethod.GET);
        this.url = StringUtils.Joint(this.url, this.mapParams);
        XLog.e(url);
        RequestManager.load(this, l);
    }
}
