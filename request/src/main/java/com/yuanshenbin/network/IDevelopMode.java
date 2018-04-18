package com.yuanshenbin.network;

import com.yuanshenbin.network.request.BaseRequest;

/**
 * author : yuanshenbin
 * time   : 2018/1/24
 * desc   : 开发者相关
 */

public interface IDevelopMode {

    void  onRecord( BaseRequest params,Object response);
}
