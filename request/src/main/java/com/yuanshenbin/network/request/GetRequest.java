package com.yuanshenbin.network.request;


import android.content.Context;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


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
        if(this.mapParams.size()!=0) {
            this.url = Joint(this.url, this.mapParams);
        }
        RequestManager.load(this, l);
    }


}
