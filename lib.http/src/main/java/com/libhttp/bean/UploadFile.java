package com.libhttp.bean;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Jacky on 2016/10/31.
 * 上传需要的参数
 */

public class UploadFile implements Serializable {
    /**
     * 不想监听默认是0
     */
    private int what = 0;

    /**
     * 文件file名字
     */
    private File file;

    /**
     * 上传文件key的名字
     */
    private String key;


    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UploadFile(int what, String key, File file) {
        this.what = what;
        this.file = file;
        this.key = key;
    }

    public UploadFile(String key, File file) {
        this.key = key;
        this.file = file;
    }

    public UploadFile() {
    }
}


