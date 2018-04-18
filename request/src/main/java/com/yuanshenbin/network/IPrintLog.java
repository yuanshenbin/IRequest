package com.yuanshenbin.network;

/**
 * author : yuanshenbin
 * time   : 2018/1/24
 * desc   : 相关打印
 */

public interface IPrintLog {


    void onPrintParam(Object object);

    void onPrintResult(Object object);

    void onPrintException(Exception e);
}
