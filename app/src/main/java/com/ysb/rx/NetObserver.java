package com.ysb.rx;


import android.widget.Toast;

import com.ysb.base.BasePresenter;
import com.yuanshenbin.network.INetDialog;
import com.yuanshenbin.network.manager.NetworkManager;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yuanshenbin on 2017/7/6.
 */

public abstract class NetObserver<T> implements Observer<T> {

    private boolean isLoading;
    private BasePresenter mPresenter;
    private INetDialog mDialog;

    public NetObserver(BasePresenter presenter, boolean loading) {
        isLoading = loading;
        mPresenter =presenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
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
            Toast.makeText(mPresenter.mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mPresenter.mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // ILogger.e("rx", e);

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
            mDialog = NetworkManager.getInstance().getInitializeConfig().getDialog();
            mDialog.init(mPresenter.mContext);
            mDialog.show();
        }
    }

    private void cancelLoading() {
        if (mDialog != null)
            mDialog.dismiss();
    }



    public abstract void _onNext(T result);

    public abstract void _onError(Throwable e);

}
