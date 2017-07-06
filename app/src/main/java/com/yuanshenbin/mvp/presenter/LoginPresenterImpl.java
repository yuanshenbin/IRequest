package com.yuanshenbin.mvp.presenter;


import com.yuanshenbin.base.BasePresenter;
import com.yuanshenbin.bean.PuBuLiuModel;
import com.yuanshenbin.mvp.constant.MvpTag;
import com.yuanshenbin.mvp.contract.LoginContract;
import com.yuanshenbin.mvp.model.LoginModelImpl;
import com.yuanshenbin.rx.NetObserver;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jacky on 2017/03/17
 */

public class LoginPresenterImpl extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model mModel;

    public LoginPresenterImpl() {

        mModel = new LoginModelImpl();

    }

    @Override
    public void getLogin1(String account, String pass) {
        append(mModel.getLogin1(account, pass), new NetObserver<PuBuLiuModel>(mContext, true) {
            @Override
            public void _onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void _onNext(PuBuLiuModel result) {
                mView.onLogin1(result);
            }

            @Override
            public void _onError(Throwable e) {
                mView.onError(MvpTag.TAG1);
            }
        });
    }

    @Override
    public void getRegister2(String account, String pass) {
        append(mModel.getRegister2(account, pass), new NetObserver<PuBuLiuModel>(mContext, true) {
            @Override
            public void _onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void _onNext(PuBuLiuModel result) {
                mView.onRegister2(result);
            }

            @Override
            public void _onError(Throwable e) {
                mView.onError(MvpTag.TAG2);
            }
        });
    }

    @Override
    public void getVerification3(String account, String pass) {
        append(mModel.getVerification3(account, pass), new NetObserver<PuBuLiuModel>(mContext, true) {
            @Override
            public void _onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void _onNext(PuBuLiuModel result) {
                mView.onVerification3(result);
            }

            @Override
            public void _onError(Throwable e) {
                mView.onError(MvpTag.TAG3);
            }
        });
    }
}
