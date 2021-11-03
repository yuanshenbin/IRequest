package com.yuanshenbin.network.model;

import java.io.Serializable;

/**
 * Created by yuanshenbin on 2016/7/4.
 * 上传需要的参数
 */

public class UploadFile implements Serializable {
    /**
     * 不想监听默认是0
     */
    private int what = 0;

    /**
     * 文件模式
     * File
     * Bitmap
     * FileInputStream
     */
    private Object mode;

    /**
     * 上传文件key的名字
     */
    private String key;

    public Object getMode() {
        return mode;
    }

    public void setMode(Object mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UploadFile(int what, Object mode, String key) {
        this.what = what;
        this.mode = mode;
        this.key = key;
    }

    public UploadFile(String key, Object mode) {
        this.mode = mode;
        this.key = key;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }
}

