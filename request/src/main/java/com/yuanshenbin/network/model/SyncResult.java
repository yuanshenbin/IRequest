package com.yuanshenbin.network.model;

import java.io.Serializable;

/**
 * author : yuanshenbin
 * time   : 2021-06-17
 * desc   : 同步请求结果
 */
public class SyncResult<T>  implements Serializable {
    private Exception exception;
    /***
     * 会有空的情况
     */
    private T result;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SyncResult{" +
                "exception=" + exception +
                ", result=" + result +
                '}';
    }
}
