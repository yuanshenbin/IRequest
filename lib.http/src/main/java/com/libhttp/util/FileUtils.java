package com.libhttp.util;

import android.os.Environment;

/**
 * Created by Jacky on 2016/10/31.
 */

public class FileUtils {
    /**
     * 判断是否有SD卡
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回手机内存路径优先内存卡
     *
     * @return
     */
    public static String getCachePath() {
        if (isExistSDCard()) {
            return Environment.getExternalStorageDirectory() + "/yuanshenbin/";
        } else {
            return "/data" + "/yuanshenbin/";
        }
    }
}
