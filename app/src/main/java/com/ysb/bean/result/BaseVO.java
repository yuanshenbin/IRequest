package com.ysb.bean.result;

import com.ysb.bean.Base;

import java.io.Serializable;

/**
 * author : yuanshenbin
 * time   : 2018/2/7
 * desc   :
 */
public class BaseVO<T> extends Base implements Serializable {
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RSBase{" +
                "data=" + data +
                '}';
    }
}
