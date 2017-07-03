package com.yuanshenbin.network;


import android.widget.Toast;

import com.yanzhenjie.nohttp.rest.Response;
import com.yuanshenbin.app.App;


/**
 * Created by Jacky on 2016/10/31.
 */
public abstract class AbstractResponse<T> {
    public abstract void onSuccess(T result);

    public void onSuccess(int what, Response<T> response) {

    }

    public void onFailed() {

        Toast.makeText(App.getInstance(), "网络不给力···", Toast.LENGTH_SHORT).show();
    }

    public void onFailed(int what, Response<T> response) {
     //
    }

    public void onResponseState(ResponseEnum result) {

    }

}
