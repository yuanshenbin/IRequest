package com.yuanshenbin.network.request;


import android.content.Context;

import com.elvishew.xlog.XLog;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.util.StringUtils;


/**
 * Created by Jacky on 2016/10/31.
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(Context context, String url) {
        this.url = url;
        this.context = context;

    }
    public <T> void execute(AbstractResponse<T> l) {
        requestMethod(RequestMethod.GET);
        this.url = StringUtils.Joint(this.url, this.mapParams);
        XLog.e(url);
        RequestManager.load(this, l);
    }
}
