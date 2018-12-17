package com.yuanshenbin.network;


import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yuanshenbin.network.constant.Constants;
import com.yuanshenbin.network.error.ResultError;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.ResponseModel;

/**
 * Created by yuanshenbin on 2016/10/31.
 */
public abstract class AbstractResponse<T> {
    public abstract void onSuccess(T result);

    public void onFailed(Exception exception) {
        String stringRes = Constants.HTTP_UNKNOW_ERROR;
        if (exception instanceof NetworkError) {
            stringRes = Constants.HTTP_EXCEPTION_NETWORK;
        } else if (exception instanceof TimeoutError) {
            stringRes = Constants.HTTP_EXCEPTION_CONNECT_TIMEOUT;
        } else if (exception instanceof UnKnownHostError) {
            stringRes = Constants.HTTP_EXCEPTION_HOST;
        } else if (exception instanceof URLError) {
            stringRes = Constants.HTTP_EXCEPTION_URL;
        } else if (exception instanceof ResultError) {
            stringRes = exception.getMessage();
        }
        NetworkManager.getInstance().getInitializeConfig().getToastFailed().onFailed(new ResultError(stringRes, exception));
    }

    public void onResponseState(ResponseModel result) {

    }
}
