package com.yuanshenbin.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Jacky on 2016/12/12.
 */

public class StringUtils {
    public static String Joint(String url, Map<Object, Object> params) {
        if (url.indexOf("?") < 0) {
            url += "?";
        }
        if (params != null) {
            for (Object name : params.keySet()) {
                try {

                    if (TextUtils.isEmpty((String) params.get(name))) {
                        url += "&" + name + "="
                                + "";
                    } else {
                        url += "&" + name + "="
                                + URLEncoder.encode((String) params.get(name), "UTF-8");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    /**
     * @param o        带泛型对象
     * @param position 泛型的位置
     * @param <T>      返回示例类型
     * @return
     */
    public static <T> T getT(Object o, int position) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[position])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
