package com.yuanshenbin.rx;


import android.content.Context;
import android.widget.Toast;

import com.yuanshenbin.network.IDialog;
import com.yuanshenbin.util.ILogger;
import com.yuanshenbin.widget.LoadingDialog;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yuanshenbin on 2017/7/6.
 */

public abstract class NetObserver<T> implements Observer<T> {

    private boolean isLoading;
    private Context mContext;
    private IDialog mDialog;

    public NetObserver(Context context, boolean loading) {
        isLoading = loading;
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        _onSubscribe(d);
        showLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        //统一处理请求异常的情况
        if (e instanceof IOException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ILogger.e("rx", e);

        _onError(e);

        cancelLoading();
    }

    @Override
    public void onComplete() {
        cancelLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (isLoading) {
            mDialog = new LoadingDialog();
            mDialog.init(mContext);
            mDialog.show();
        }
    }

    private void cancelLoading() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    public abstract void _onSubscribe(Disposable d);

    public abstract void _onNext(T result);

    public abstract void _onError(Throwable e);

}
