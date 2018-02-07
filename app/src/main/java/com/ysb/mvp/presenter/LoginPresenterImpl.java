package com.ysb.mvp.presenter;


import com.ysb.base.BasePresenter;
import com.ysb.bean.PuBuLiuModel;
import com.ysb.mvp.constant.MvpTag;
import com.ysb.mvp.contract.LoginContract;
import com.ysb.mvp.model.LoginModelImpl;
import com.ysb.rx.NetObserver;

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
        append(mModel.getLogin1(account, pass), new NetObserver<PuBuLiuModel>(this, true) {


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
        append(mModel.getRegister2(account, pass), new NetObserver<PuBuLiuModel>(this, true) {

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
        append(mModel.getVerification3(account, pass), new NetObserver<PuBuLiuModel>(this, true) {

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
