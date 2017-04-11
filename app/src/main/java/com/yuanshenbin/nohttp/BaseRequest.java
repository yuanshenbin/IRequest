package com.yuanshenbin.nohttp;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yuanshenbin.bean.UploadFile;
import com.yuanshenbin.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jacky on 2016/10/31.
 */
public abstract class BaseRequest<T extends BaseRequest> {
    /**
     * 地址
     */
    protected String url;
    /**
     * 上下文
     */
    protected Context context;
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
    protected String params;

    protected Map<Object, Object> mapParams = new HashMap<>();

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
    /**
     * 上传文件对象
     */
    protected List<UploadFile> uploadFiles = new ArrayList<>();
    /**
     * 保存的文件夹
     **/
    protected String fileFolder = FileUtils.getCachePath();
    /**
     * 文件名 不传 默认是时间戳
     **/
    protected String fileName = String.valueOf(System.currentTimeMillis());
    /**
     * 是否断点续传下载 默认是
     **/
    protected boolean isRange = true;
    /**
     * 如果发现文件已经存在是否删除后重新下载
     **/
    protected boolean isDeleteOld = false;

    protected RequestMethod requestMethod = RequestMethod.GET;

    protected boolean isPostMap = false;


    /**
     * 控制rx里面的线程池队列
     */
    protected boolean isQueueEnd = false;

    /**
     * 让当前线程休眠
     */
    protected boolean isWait = true;

    /**
     * 缓存
     */
    protected CacheMode cacheMode = CacheMode.DEFAULT;

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

    public T fileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
        return (T) this;
    }

    public T fileName(String fileName) {
        this.fileName = fileName;
        return (T) this;
    }

    public T isRange(boolean isRange) {
        this.isRange = isRange;
        return (T) this;
    }

    public T isDeleteOld(boolean isDeleteOld) {
        this.isDeleteOld = isRange;
        return (T) this;
    }

    public T uploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
        return (T) this;
    }

    public T requestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return (T) this;
    }

    public T params(String key, String value) {
        mapParams.put(key, value);
        return (T) this;
    }

    public T cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (T) this;
    }
}
