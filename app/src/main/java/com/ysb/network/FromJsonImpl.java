package com.ysb.network;

import com.google.gson.Gson;
import com.yuanshenbin.network.IFromJson;

import java.lang.reflect.Type;

/**
 * author : yuanshenbin
 * time   : 2018/1/23
 * desc   :
 */

public class FromJsonImpl implements IFromJson {


    @Override
    public <T> T onFromJson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    @Override
    public String onToJson(Object object) {
        return new Gson().toJson(object);
    }
}
