package com.ysb.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Jacky on 2016/12/12.
 */

public class StringUtils {
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
