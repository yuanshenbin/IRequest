package com.yuanshenbin.app;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yuanshenbin.BuildConfig;

/**
 * Created by Jacky on 2016/10/31.
 */

public class App extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        InitializationConfig config = InitializationConfig.newBuilder(this)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(30 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(this).setEnable(true)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(this).setEnable(true)
                )
                // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new OkHttpNetworkExecutor())
                // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
                // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
                .retry(1) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();

        NoHttp.initialize(config);


        Logger.setDebug(true);


        /**
         * 调试模式 有可能会造成ui卡顿，主要打印很长的json，正式包会自动关闭打印，所以可以无视
         */
        XLog.init(new LogConfiguration                                             // 如果没有指定 LogConfiguration，会默认使用 new LogConfiguration.Builder().build()
                .Builder()                                               // 打印日志时会用到的配置
                .logLevel((BuildConfig.DEBUG ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : LogLevel.NONE))
                .tag("@@").b()                                     // 默认: "XLOG"
                .build(), new AndroidPrinter(), new ConsolePrinter(), new FilePrinter                      // Printer that print the log to the file system
                .Builder("/sdcard/xlog/")                              // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)

                .build());

    }

    public static Application getInstance() {
        return instance;
    }
}
