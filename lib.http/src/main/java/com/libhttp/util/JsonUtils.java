package com.libhttp.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 2016/10/31.
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    /**
     * json管理
     */
    public static Object fromJson(String json, Type typeOfT) {

        return gson.fromJson(json, typeOfT);
    }

    public static <T> T object(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> String toJson(Class<T> param) {
        return gson.toJson(param);
    }

    public static String string(Object object) {
        return gson.toJson(object);
    }

    public static <T> List<T> getArrays(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
