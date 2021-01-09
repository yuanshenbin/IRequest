package com.yuanshenbin.network.manager;


import com.yuanshenbin.network.NetworkConfig;

/**
 * author : yuanshenbin
 * time   : 2018/1/23
 * desc   :网络管理
 */

public class NetworkManager {

    private static NetworkManager instance;

    private static NetworkConfig mNetworkConfig;

    public static NetworkManager getInstance() {
        if (instance == null)
            synchronized (NetworkManager.class) {
                instance = new NetworkManager();
            }
        return instance;
    }


    public void InitializationConfig(NetworkConfig config) {
        mNetworkConfig = config;
    }

    public NetworkConfig getInitializeConfig() {
        return mNetworkConfig;
    }

}
