package com.yuanshenbin.network.request;


import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yuanshenbin.network.AbstractResponse;



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
