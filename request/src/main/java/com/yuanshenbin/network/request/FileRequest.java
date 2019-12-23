package com.yuanshenbin.network.request;

import android.os.Environment;
import com.yuanshenbin.network.model.UploadFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanshenbin on 2016/10/31.
 */
public abstract class FileRequest<T extends BaseRequest> extends BaseRequest<T> {

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

    /**
     * minetype类型
     */
    protected  String mimeType;

    /***
     * 文件上传的key
     */
    protected String fileKey = "file";


    public T mimeType(String mimeType) {
        this.mimeType = mimeType;
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
        this.isDeleteOld = isDeleteOld;
        return (T) this;
    }

    public T uploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
        return (T) this;
    }

    public T uploadFile(UploadFile uploadFile) {
        if (uploadFile.getMode() != null) {
            this.uploadFiles.add(uploadFile);
        }
        return (T) this;
    }

    public T fileKey(String fileKey) {
        this.fileKey = fileKey;
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

}
