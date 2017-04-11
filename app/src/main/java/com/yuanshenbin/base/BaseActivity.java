package com.yuanshenbin.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.elvishew.xlog.XLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jacky on 2016/9/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getName();
    protected Activity mContext;
    protected Unbinder mUnbinder;

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int initLayoutId();

    /**
     * 初始化传来的数据或者默认数据
     * query_userId =getIntent().getStringxxxxx
     * query_index =getIntent().getIntxxxx
     * xxx = new xxx();
     */
    protected abstract void initDatas();

    /**
     * 初始化监听事件
     */
    protected abstract void initEvents();

    /**
     * 初始化适配器
     **/
    protected abstract void initAdapter();


    /**
     * 如果页面有eventbug的时候 实现该方法返回true
     * 需要注意，如果设置为true，没有通讯的方法是会报错的
     */
    protected boolean isEventBug() {
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initLayoutId());
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            super.setContentView(layoutResID);
        }
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        if (isEventBug()) {
            try {
                EventBus.getDefault().register(this);
            } catch (Exception e) {
                XLog.e(TAG,e);
            }
        }
        initDatas();
        initEvents();
        initAdapter();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        if (isEventBug()) {
            try {
                EventBus.getDefault().register(this);
            } catch (Exception e) {
                XLog.e(TAG,e);
            }
        }
        initDatas();
        initEvents();
        initAdapter();
    }


    /**
     * 短暂显示Toast提示(来自res)
     **/
    protected void IShowToast(int resId) {

        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    protected void IShowToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 不带参数跳转
     */
    protected void IStartActivity(Class<?> sla) {
        startActivity(new Intent(mContext, sla));
    }

    /**
     * 带参数跳转
     */
    protected void IStartActivity(Bundle bundle, Class<?> sla) {
        Intent intent = new Intent(mContext, sla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    /**
     * 不带参数回传
     */
    protected void IStartActivityForResult(int requestCode, Class<?> sla) {
        startActivityForResult(new Intent(mContext, sla), requestCode);
    }


    /**
     * 带参数回传回执
     */
    protected void ISetResult(int resultCode, Class<?> sla) {
        Intent intent = new Intent(mContext, sla);
        setResult(resultCode, intent);
    }

    /**
     * 带参数回传回执 带bundle
     */
    protected void ISetResult(Bundle bundle, int resultCode, Class<?> sla) {
        Intent intent = new Intent(mContext, sla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
    }
    /**
     * 显示控件
     *
     * @param view
     */
    protected void getVISIBLE(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 占住位置影藏控件
     *
     * @param view
     */
    protected void getINVISIBLE(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * 影藏控件
     *
     * @param view
     */
    protected void getGONE(View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * 初始化view
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }


    /**
     * 改变系统字体不影响app的字体
     *
     * @return
     */
    @Override
    public Resources getResources() {
        try {
            Resources res = super.getResources();
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
            return res;
        } catch (Exception e) {

            XLog.e(TAG,e);
            return super.getResources();
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        if (isEventBug()) {
            try {
                EventBus.getDefault().unregister(this);
            } catch (Exception e) {
                XLog.e(TAG,e);
            }
        }
    }
}
