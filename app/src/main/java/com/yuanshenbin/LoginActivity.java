package com.yuanshenbin;

import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.yuanshenbin.base.BaseMVPActivity;
import com.yuanshenbin.bean.PuBuLiuModel;
import com.yuanshenbin.mvp.constant.MvpTag;
import com.yuanshenbin.mvp.contract.LoginContract;
import com.yuanshenbin.mvp.presenter.LoginPresenterImpl;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by Jacky on 2016/12/12.
 */

public class LoginActivity extends BaseMVPActivity<LoginContract.View, LoginPresenterImpl> implements LoginContract.View {
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
        RxView.clicks(vBtn1).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                mPresenter.getLogin1("", "");
            }
        });
        RxView.clicks(vBtn2).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPresenter.getRegister2("", "");
            }
        });
        RxView.clicks(vBtn3).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
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
}
