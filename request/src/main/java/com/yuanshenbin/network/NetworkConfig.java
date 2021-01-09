package com.yuanshenbin.network;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;

/**
 * author : yuanshenbin
 * time   : 2018/1/22
 * desc   : 网络相关配置
 */

public class NetworkConfig {

    private IFromJson mIFromJson;
    private INetDialog mIDialog;
    private IPrintLog mIPrintLog;
    private IDevelopMode mIDevelopMode;
    private IToastFailed mIToastFailed;
    private SSLContext mSSLContext;
    private INetworkListener mINetworkLinstener;
    private IHeader mIHeader;

    private IContentType mIContentType = new IContentType() {
        @Override
        public String getContentType() {
            return Headers.HEAD_VALUE_CONTENT_TYPE_JSON;
        }
    };
    private int mThreadPoolSize = 5;

    public NetworkConfig(final Builder builder) {
        NoHttp.initialize(builder.mNoHttpConfig);

        if (builder.mFromJson == null) {
            throw new ExceptionInInitializerError("Please invoke  IFromJson~");
        }
        mIFromJson = builder.mFromJson;
        mIDialog = builder.mDialog;
        mIDevelopMode = builder.mDevelopMode;
        mIPrintLog = builder.mPrintLog;
        mSSLContext = builder.mSSLContext;
        mIToastFailed = builder.mToastFailed;
        mINetworkLinstener = builder.mNetworkLinstener;
        mThreadPoolSize = builder.mThreadPoolSize;
        mIHeader = builder.mHeader;
        mIContentType = builder.mContentType;

    }

    public static final class Builder {


        /**
         * 对话框接口
         */
        private INetDialog mDialog;

        /**
         * 初始化nohttp
         */
        private InitializationConfig mNoHttpConfig;

        /**
         * 数据解析相关
         */
        private IFromJson mFromJson;

        /**
         * 打印相关
         */
        private IPrintLog mPrintLog;

        /**
         * 开发者模式相关
         */
        private IDevelopMode mDevelopMode;

        /**
         * https 证书
         */
        private SSLContext mSSLContext;

        /**
         * 网络失败统一处理
         */
        private IToastFailed mToastFailed;

        /**
         * 统一需要处理相关事件
         */
        private INetworkListener mNetworkLinstener;

        /**
         * 头部
         */
        private IHeader mHeader;

        /**
         * 队列总数
         */
        private int mThreadPoolSize = 5;

        private IContentType mContentType = new IContentType() {
            @Override
            public String getContentType() {
                return Headers.HEAD_VALUE_CONTENT_TYPE_JSON;
            }
        };


        public Builder dialog(INetDialog dialog) {
            this.mDialog = dialog;
            return this;
        }

        public Builder noHttpConfig(InitializationConfig config) {
            mNoHttpConfig = config;
            return this;
        }

        public Builder printLog(IPrintLog printLog) {
            mPrintLog = printLog;
            return this;
        }

        public Builder fromJson(IFromJson fromJson) {
            mFromJson = fromJson;
            return this;
        }

        public Builder developMode(IDevelopMode developMode) {
            mDevelopMode = developMode;
            return this;
        }

        public Builder SSLContext(SSLContext sslContext) {
            mSSLContext = sslContext;
            return this;
        }

        public Builder toastFailed(IToastFailed toastFailed) {
            mToastFailed = toastFailed;
            return this;
        }

        public Builder networkLinstener(INetworkListener linstener) {
            this.mNetworkLinstener = linstener;
            return this;
        }

        public Builder threadPoolSize(int threadPoolSize) {
            this.mThreadPoolSize = threadPoolSize;
            return this;
        }

        public Builder header(IHeader header) {
            this.mHeader = header;
            return this;
        }


        public Builder contentType(IContentType contentType) {
            this.mContentType = contentType;
            return this;
        }

        public NetworkConfig build() {
            return new NetworkConfig(this);
        }
    }

    public IFromJson getFromJson() {
        return mIFromJson;
    }


    public INetDialog getDialog() {
        return mIDialog;
    }

    public IPrintLog getIPrintLog() {
        return mIPrintLog;
    }

    public IDevelopMode getIDevelopMode() {
        return mIDevelopMode;
    }

    public SSLContext getSSLContext() {
        return mSSLContext;
    }

    public IToastFailed getToastFailed() {
        return mIToastFailed;
    }

    public INetworkListener getNetworkLinstener() {
        return mINetworkLinstener;
    }

    public IHeader getHeader() {
        return mIHeader;
    }

    public int getThreadPoolSize() {
        return mThreadPoolSize;
    }

    public String getContentType() {
        return mIContentType.getContentType();
    }

}
