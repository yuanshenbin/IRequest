package com.yuanshenbin.network.request;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yuanshenbin.network.model.UploadFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    protected String fileFolder = getAlbumRootPath().getAbsolutePath();
    /**
     * 文件名 不传 默认是时间戳
     **/
    protected String fileName = String.valueOf(System.currentTimeMillis());
    /**
     * 是否断点续传下载 默认是
     **/
    protected boolean isRange = false;
    /**
     * 如果发现文件已经存在是否删除后重新下载
     **/
    protected boolean isDeleteOld = true;

    protected RequestMethod requestMethod = RequestMethod.POST;

    protected boolean isPostMap = false;


    /**
     * 缓存
     */
    protected CacheMode cacheMode = CacheMode.DEFAULT;

    /**
     * 是否禁止手动关闭对话框
     */
    protected boolean isCloseDialog =true;


    /**
     * 头部参数
     */
    protected  Map<Object,Object> headerParam =new HashMap<>(); 
    


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

    public T requestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
        return (T) this;
    }

    public T uploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
        return (T) this;
    }
    public T uploadFile(UploadFile uploadFile) {
        if(uploadFile.getMode()!=null)
        {
            this.uploadFiles.add(uploadFile);
        }
        return (T) this;
    }
    public T params(String key, Object value) {
        mapParams.put(key, value);
        return (T) this;
    }
    public T params(Map<Object, Object>  maps) {
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

    /**
     * Get a writable root directory.
     *
     * @return {@link File}.
     */
    private static File getAlbumRootPath() {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return new File("/data/com.IRequesr");
        }
    }

    /**
     * SD card is available.
     *
     * @return true, other wise is false.
     */
    private static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().canWrite();
        } else
            return false;
    }

    protected String Joint(String url, Map<Object, Object> params) {
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
