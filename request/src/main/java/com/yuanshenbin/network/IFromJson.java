package com.yuanshenbin.network;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * author : yuanshenbin
 * time   : 2018/1/23
 * desc   : 数据解析
 */

public interface IFromJson {

    <T> T onFromJson(String json, Type type);

    String onToJson(Object object);

    Map<String, Object> onJsonToMap(Object param);
}
