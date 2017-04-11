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
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
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
        NoHttp.initialize(this, new NoHttp.Config()
                // 使用OkHttp
                .setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setDebug(true);


        /**
         * 调试模式 有可能会造成ui卡顿，比较要打印很长的json，正式包会自动关闭打印，所以可以无视
         */
        XLog.init(new LogConfiguration                                             // 如果没有指定 LogConfiguration，会默认使用 new LogConfiguration.Builder().build()
                .Builder()                                               // 打印日志时会用到的配置
                .logLevel((BuildConfig.DEBUG ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : LogLevel.NONE))
                .tag("@@") .b()                                     // 默认: "XLOG"
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