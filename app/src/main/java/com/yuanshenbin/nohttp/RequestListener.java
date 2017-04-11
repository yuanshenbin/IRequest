package com.yuanshenbin.nohttp;


import android.widget.Toast;

import com.yuanshenbin.app.App;


/**
 * Created by Jacky on 2016/10/31.
 */
public abstract class RequestListener<T> extends OnUploadListener {

    /**
     * 成功
     */
    public abstract void onSuccess(T result);

    /**
     * 错误
     */
    public void onFailed(Exception e) {

        Toast.makeText(App.getInstance(), "网络不给力···", Toast.LENGTH_SHORT).show();
    }
}
