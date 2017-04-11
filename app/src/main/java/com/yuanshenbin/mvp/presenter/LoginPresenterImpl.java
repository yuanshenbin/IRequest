package com.yuanshenbin.mvp.presenter;


import com.yuanshenbin.base.BasePresenter;
import com.yuanshenbin.bean.PuBuLiuModel;
import com.yuanshenbin.mvp.constant.MvpTag;
import com.yuanshenbin.mvp.contract.LoginContract;
import com.yuanshenbin.mvp.model.LoginModelImpl;
import com.yuanshenbin.rx.RxSubscriber;

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

        mSubscription = add(mModel.getLogin1(account, pass), new RxSubscriber<PuBuLiuModel>(mContext, true) {
            @Override
            protected void _onNext(PuBuLiuModel result) {

                mView.onLogin1(result);
            }

            @Override
            protected void _onError() {
                mView.onError(MvpTag.TAG1);
            }
        });
    }

    @Override
    public void getRegister2(String account, String pass) {
        mSubscription = add(mModel.getRegister2(account, pass), new RxSubscriber<PuBuLiuModel>(mContext, true) {
            @Override
            protected void _onNext(PuBuLiuModel result) {

                mView.onRegister2(result);
            }

            @Override
            protected void _onError() {
                mView.onError(MvpTag.TAG2);
            }
        });
    }

    @Override
    public void getVerification3(String account, String pass) {
        mSubscription = add(mModel.getVerification3(account, pass), new RxSubscriber<PuBuLiuModel>(mContext, true) {
            @Override
            protected void _onNext(PuBuLiuModel result) {

                mView.onVerification3(result);
            }

            @Override
            protected void _onError() {
                mView.onError(MvpTag.TAG3);
            }
        });
    }
}