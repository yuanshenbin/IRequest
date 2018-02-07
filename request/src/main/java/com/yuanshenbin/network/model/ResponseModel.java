package com.yuanshenbin.network.model;



import com.yuanshenbin.network.ResponseEnum;

import java.io.Serializable;

/**
 * author : yuanshenbin
 * time   : 2017/9/28
 * desc   :
 */

public class ResponseModel implements Serializable {

    private ResponseEnum state;

 
    private Throwable exception;

    public ResponseModel(ResponseEnum state) {
        this.state = state;
    }

    public ResponseModel(ResponseEnum state, Throwable exception) {
        this.state = state;
        this.exception = exception;
    }

    public ResponseEnum getState() {
        return state;
    }

    public void setState(ResponseEnum state) {
        this.state = state;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "state=" + state +
                ", exception=" + exception +
                '}';
    }
}
