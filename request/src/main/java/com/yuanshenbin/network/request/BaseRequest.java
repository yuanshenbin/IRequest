package com.yuanshenbin.network.request;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yuanshenbin.network.INetDialog;
import com.yuanshenbin.network.model.UploadFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanshenbin on 2016/10/31.
 */
public abstract class BaseRequest<T extends BaseRequest> {

    /**
     * 地址
     */
    public String url;
    /**
     * 上下文
     */
    public Context context;
    /**
     * 是否显示loading
     */
    protected boolean isLoading = false;
    /**
     * loading提示文字
     */
    protected String loadingTitle;
    /**
     * post请求参数
     * 目前这里写的body
     * 如果传的是map或者对象其他
     * 把这个参数改下就行，哪里需要了这个参数，同时也改掉
     */
    public String params = "";

    public Map<String, Object> mapParams = new HashMap<>();

    /**
     * 超时时间
     */
    protected int timeOut = 30000;
    /**
     * 重试次数
     */
    protected int retry = 0;
    /**
     * 请求标示
     */
    protected int what = 0;


    protected RequestMethod requestMethod = RequestMethod.POST;

    protected boolean isPostMap = false;

    /**
     * 缓存
     */
    protected CacheMode cacheMode = CacheMode.DEFAULT;

    /**
     * 是否禁止手动关闭对话框
     */
    protected boolean isCloseDialog = true;


    /**
     * 头部参数
     */
    public Map<Object, Object> headerParam = new HashMap<>();

    /**
     * 请求类型
     */
    protected String contentType;

    protected INetDialog netDialog;


    public T loadingImpl(INetDialog netDialog) {
        this.netDialog = netDialog;
        return (T) this;
    }

    public T loading(boolean loading) {
        this.isLoading = loading;
        return (T) this;
    }

    public T loadingTitle(String title) {
        this.loadingTitle = title;
        return (T) this;
    }

    public T timeOut(int timeOut) {
        this.timeOut = timeOut;
        return (T) this;
    }

    public T retry(int retry) {
        this.retry = retry;
        return (T) this;
    }

    public T what(int what) {
        this.what = what;
        return (T) this;
    }


    public T requestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return (T) this;
    }

    public T params(String key, Object value) {
        mapParams.put(key, value);
        return (T) this;
    }

    public T params(Map<String, Object> maps) {
        mapParams.putAll(maps);
        return (T) this;
    }

    public T cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (T) this;
    }

    public T isCloseDialog(boolean close) {
        this.isCloseDialog = close;
        return (T) this;
    }


    public T headerParam(String key, String value) {
        headerParam.put(key, value);
        return (T) this;
    }

    public T contentType(String contentType) {
        this.contentType = contentType;
        return (T) this;
    }



    protected String Joint(String url, Map<String, Object> params) {
        if (url.indexOf("?") < 0) {
            url += "?";
        }
        if (params != null) {
            for (Object name : params.keySet()) {
                try {

                    if (TextUtils.isEmpty((String) params.get(name))) {
                        url += "&" + name + "="
                                + "";
                    } else {
                        url += "&" + name + "="
                                + URLEncoder.encode((String) params.get(name), "UTF-8");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }
}
