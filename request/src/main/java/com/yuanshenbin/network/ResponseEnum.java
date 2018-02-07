package com.yuanshenbin.network;

/**
 * Created by yuanshenbin on 2017/6/22.
 */

public enum ResponseEnum {
    开始(0),
    成功(1),
    失败(2),
    结束(3);

    public int state;

    ResponseEnum(int state) {
        this.state = state;
    }
}
