package com.ysb.base;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class BasePresenter<V> {
    public V mView;
    public CompositeDisposable mDisposable;
    public Context mContext;

    public void attach(V view, Context context) {
        mView = view;
        this.mContext = context;
        mDisposable=  new CompositeDisposable();
    }

    public void detach() {
        mView = null;
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    public <T> void append(Observable<T> observable, Observer<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
