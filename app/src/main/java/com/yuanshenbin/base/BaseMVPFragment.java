package com.yuanshenbin.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanshenbin.util.StringUtils;


/**
 * Author: Othershe
 * Time:  2016/8/11 17:53
 */
public abstract class BaseMVPFragment<V, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = StringUtils.getT(this, 1);
        mPresenter.attach((V) this, getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
