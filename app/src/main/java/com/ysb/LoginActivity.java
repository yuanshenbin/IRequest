package com.ysb;

import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.ysb.base.BaseMVPActivity;
import com.ysb.bean.PuBuLiuModel;
import com.ysb.mvp.constant.MvpTag;
import com.ysb.mvp.contract.LoginContract;
import com.ysb.mvp.presenter.LoginPresenterImpl;
;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Jacky on 2016/12/12.
 */

public class LoginActivity extends BaseMVPActivity<LoginContract.View ,LoginPresenterImpl> implements LoginContract.View {
    @BindView(R.id.btn1)
    Button vBtn1;
    @BindView(R.id.btn2)
    Button vBtn2;
    @BindView(R.id.btn3)
    Button vBtn3;



    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {

        RxView.clicks(vBtn1).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                mPresenter.getLogin1("", "");
            }
        });
        RxView.clicks(vBtn2).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                mPresenter.getRegister2("", "");
            }
        });
        RxView.clicks(vBtn3).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                mPresenter.getVerification3("", "");
            }
        });
    }

    @Override
    protected void initAdapter() {

    }

    @Override
    public void onLogin1(PuBuLiuModel result) {
        IShowToast("我登陆了");
    }

    @Override
    public void onRegister2(PuBuLiuModel result) {
        IShowToast("我注册了");
    }

    @Override
    public void onVerification3(PuBuLiuModel result) {
        IShowToast("我发送验证码了");
    }

    @Override
    public void onError(int tag) {
        switch (tag) {
            case MvpTag.TAG1:
                onShowToast("错误登陆");
                break;
            case MvpTag.TAG2:
                onShowToast("错误注册");
                break;
            case MvpTag.TAG3:
                onShowToast("错误验证码");
                break;
        }
    }

    @Override
    public void onShowToast(String msg) {
        IShowToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
