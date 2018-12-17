package com.yuanshenbin.network.request;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yuanshenbin.network.constant.Constants;
import com.yuanshenbin.network.error.ResultError;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.RecordModel;

import java.lang.reflect.Type;

/**
 * author : yuanshenbin
 * time   : 2018/1/23
 * desc   :
 */

public class EntityRequest<T> extends Request<T> {
    private Type mType;

    private String url, param;
    private long start;

    public EntityRequest(String url, RequestMethod requestMethod, Type type) {
        super(url, requestMethod);
        this.mType = type;
        this.url = url;
        this.start = System.currentTimeMillis();

    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (NetworkManager.getInstance().getInitializeConfig().getIPrintLog() != null) {
            NetworkManager.getInstance().getInitializeConfig().getIPrintLog().onPrintResult(result);
        }
        int resCode = responseHeaders.getResponseCode();
        if (resCode >= 200 && resCode < 300) { // Http层成功，这里只可能业务逻辑错误。
            try {
                if (NetworkManager.getInstance().getInitializeConfig().getIDevelopMode() != null) {
                    NetworkManager.getInstance().getInitializeConfig().getIDevelopMode().onRecord(new RecordModel(url, param, result));
                    NetworkManager.getInstance().getInitializeConfig().getIDevelopMode().onRecord(new RecordModel(url, param, result, System.currentTimeMillis() - start));
                }
                T data = NetworkManager.getInstance().getInitializeConfig().getFromJson().onFromJson(result, mType);
                return data;
            } catch (Exception e) {
                throw new ResultError(Constants.HTTP_SERVER_DATA_FORMAT_ERROR);
            }

        } else if (resCode >= 400 && resCode < 500) {
            throw new ResultError(Constants.HTTP_UNKNOW_ERROR);
        } else {
            throw new ResultError(Constants.HTTP_SERVER_ERROR);
        }
    }
}
