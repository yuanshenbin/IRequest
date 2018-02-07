package com.ysb.bean;

import java.io.Serializable;

/**
 * author : yuanshenbin
 * time   : 2018/2/7
 * desc   :
 */

public class Base implements Serializable {

    private String code; //状态码
    private String desc; //	状态描述信息
    private String serverTime;  //服务器时间
    private  boolean success; //是否成功

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Deprecated
    public String getDescription() {
        return desc;
    }


    @Override
    public String toString() {
        return "Base{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", success=" + success +
                '}';
    }
}
