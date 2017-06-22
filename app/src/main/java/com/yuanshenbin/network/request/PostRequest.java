package com.yuanshenbin.network.request;

import android.content.Context;

import com.elvishew.xlog.XLog;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.util.JsonUtils;

/**
 * Created by Jacky on 2016/10/31.
 */
public class PostRequest extends BaseRequest<PostRequest> {



    public <T> PostRequest(Context context, String url, T params) {
        this.url = url;
        this.context = context;
        this.params = JsonUtils.string(params);
        XLog.json(this.params);
    }

    public <T> PostRequest(Context context, String url) {
        this.url = url;
        this.context = context;
        this.isPostMap = true;
    }

    public <T> void execute(AbstractResponse<T> l) {
        if (isPostMap) {
            this.params = JsonUtils.string(mapParams);
            XLog.json(this.params);
        }
        requestMethod(RequestMethod.POST);
        RequestManager.load(this, l);
    }
}
