package com.yuanshenbin.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elvishew.xlog.XLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jacky on 2016/2/1.
 */
public abstract class BaseFragment extends Fragment {
    private String TAG = getClass().getName();
    protected Unbinder mUnbinder;

    private View mView;

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
    public boolean isEventBug() {
        return false;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        } else {
            mView = inflater.inflate(initLayoutId(), container, false);
        }
        mUnbinder = ButterKnife.bind(this,mView);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isEventBug()) {
            try{
                EventBus.getDefault().register(this);
            }catch ( Exception e)
            {
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

        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    protected void IShowToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
    /**
     * 不带参数跳转
     */
    protected void IStartActivity(Class<?> sla) {
        startActivity(new Intent(getActivity(), sla));
    }

    /**
     * 带参数跳转
     */
    protected void IStartActivity(Bundle bundle, Class<?> sla) {
        Intent intent = new Intent(getActivity(), sla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    /**
     * 不带参数回传
     */
    protected void IStartActivityForResult(int requestCode, Class<?> sla) {
        startActivityForResult(new Intent(getActivity(), sla), requestCode);
    }

    /**
     * 参数回传 API最低9
     */
    @SuppressLint("NewApi")
    protected void IStartActivityForResult1(Bundle bundle, int requestCode,
                                            Class<?> sla) {

        Intent intent = new Intent(getActivity(), sla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivityForResult(intent, requestCode, bundle);

    }

    /**
     * 带参数回传回执
     */
    protected void ISetResult(int resultCode, Class<?> sla) {
        Intent intent = new Intent(getActivity(), sla);
        getActivity().setResult(resultCode, intent);
    }

    /**
     * 带参数回传回执 带bundle
     */
    protected void ISetResult(Bundle bundle, int resultCode, Class<?> sla) {
        Intent intent = new Intent(getActivity(), sla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().setResult(resultCode, intent);
    }

    /**
     * 初始化view
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
        if (isEventBug()) {
            try{
                EventBus.getDefault().unregister(this);
            }catch ( Exception e)
            {
                XLog.e(TAG,e);
            }
        }
    }
}
