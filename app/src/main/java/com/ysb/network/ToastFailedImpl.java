package com.ysb.network;

import com.yuanshenbin.network.IToastFailed;

/**
 * author : yuanshenbin
 * time   : 2018/1/24
 * desc   :
 */

public class ToastFailedImpl implements IToastFailed {
    @Override
    public void onFailed() {

       //Toast.makeText(AppManager.getInstance().getContext(), "网络不给力...", Toast.LENGTH_SHORT).show();
    }
}
