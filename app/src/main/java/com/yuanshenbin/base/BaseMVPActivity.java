package com.yuanshenbin.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuanshenbin.util.StringUtils;


public abstract class BaseMVPActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = StringUtils.getT(this, 1);
        mPresenter.attach((V) this, this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
