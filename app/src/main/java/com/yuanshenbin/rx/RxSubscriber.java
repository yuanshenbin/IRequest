package com.yuanshenbin.rx;

import android.content.Context;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.yuanshenbin.widget.LoadingDialog;

import java.io.IOException;

import rx.Subscriber;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:45
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private boolean isLoading;
    private Context mContext;
    private LoadingDialog mDialog;

    public RxSubscriber(Context context, boolean loading) {
        isLoading = loading;
        this.mContext = context;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        //统一处理请求异常的情况
        if (e instanceof IOException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        XLog.e("rx",e);

        _onError();

        cancelLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (isLoading) {
            mDialog = new LoadingDialog(mContext);
            mDialog.show();
        }
    }

    private void cancelLoading() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    protected abstract void _onNext(T result);

    protected abstract void _onError();

}
