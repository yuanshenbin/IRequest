package com.yuanshenbin.network.error;

/**
 * author : yuanshenbin
 * time   : 2018/12/16
 * desc   :
 */

public class ResultError extends Exception {


    public ResultError() {
    }

    public ResultError(String message) {
        super(message);
    }

    public ResultError(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultError(Throwable throwable) {
        super(throwable);
    }
}
