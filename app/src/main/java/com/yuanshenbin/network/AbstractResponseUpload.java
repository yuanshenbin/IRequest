package com.yuanshenbin.network;

import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;

/**
 * Created by Jacky on 2016/10/31.
 * 把回调接口设置抽象对象，不一定每个回调都是必须
 * 这样需要哪个回调 就自己实现出来
 */
public abstract class AbstractResponseUpload<T> extends AbstractResponse<T> {

    /**
     * At the start of the upload is invoked.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)
     */
    public void onStart(int what) {
    }


    /**
     * Called when the upload was cancelled.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)
     */
    public void onCancel(int what) {
    }


    /**
     * Invoked when the upload progress changes.
     *
     * @param what     what of {@link FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)}.
     * @param progress progress
     * @see FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)
     */
    public void onProgress(int what, int progress) {
    }


    /**
     * Upload is complete is invoked.
     *
     * @param what what of {@link FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)}.
     * @see FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)
     */
    public void onFinish(int what) {
    }


    /**
     * Upload error is called.
     *
     * @param what      what of {@link FileBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)}. * @param exception
     *                  upload is called when an error occurs.
     * @param exception error type.
     * @see BasicBinary#setUploadListener(int, com.yanzhenjie.nohttp.OnUploadListener)
     */
    public void onError(int what, Exception exception) {
    }

}

