package com.yuanshenbin.network;


import com.yuanshenbin.network.model.ResponseModel;
import com.yuanshenbin.network.manager.NetworkManager;

/**
 * Created by Jacky on 2016/10/31.
 */
public abstract class AbstractResponse<T> {
    public abstract void onSuccess(T result);

    public void onFailed() {

        NetworkManager.getInstance().getInitializeConfig().getToastFailed().onFailed();
    }

    public void onResponseState(ResponseModel result) {

    }

}
