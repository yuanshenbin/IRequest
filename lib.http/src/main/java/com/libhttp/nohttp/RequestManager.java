package com.libhttp.nohttp;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.libhttp.bean.UploadFile;
import com.libhttp.util.JsonUtils;
import com.libhttp.widget.LoadingDialog;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by Jacky on 2016/10/31.
 */
public class RequestManager {

    /**
     * rx需要用的线程池
     *
     * @return
     */
    public static ExecutorService getQueue() {
        return Queue.mExecutorService;
    }

    private static class Queue {
        private static final ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    }

    private static RequestQueue mRequestQueue;
    private static DownloadQueue mDownloadQueue;

    /**
     * `
     * 数据请求的Queue
     *
     * @return
     */
    static RequestQueue getInstance() {
        if (mRequestQueue == null) {
            synchronized (RequestManager.class) {
                mRequestQueue = NoHttp.newRequestQueue(5);
            }
        }

        return mRequestQueue;
    }

    /**
     * 文件下载的Queue
     *
     * @return
     */
    static DownloadQueue getInstance1() {
        if (mDownloadQueue == null) {
            synchronized (RequestManager.class) {
                mDownloadQueue = NoHttp.newDownloadQueue(5);
            }
        }
        return mDownloadQueue;
    }


    private static class RequestThreadQueue extends Thread {
        private BaseRequest mParam;

        public RequestThreadQueue(BaseRequest param) {
            this.mParam = param;
        }

        @Override
        public void run() {
            super.run();
            //设置不是休眠，让rx里面正常执行
            mParam.isWait = false;
            //rx执行完后,释放当前线程
            while (!mParam.isQueueEnd) {

            }
        }
    }

    private RequestManager() {

    }

    /**
     * 统一添加头部
     *
     * @param request
     * @param <T>
     */
    private static <T> void getHeader(Request<T> request) {
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json; charset=UTF-8");
        request.addHeader("PLATFORM", "android");
        request.addHeader("OS_NAME", "android");
        request.addHeader("OS_VERSION", "5.0.1");
        request.addHeader("APP_VERSION", "2.6.2");
        request.addHeader("MAC_ID", "CC3A61066EDC");
        request.addHeader("IMEI", "A0000044559EAA");
        request.addHeader("COOKIE", "PHPSESSID=uqgapn23iapj13v2bvuvsvalj7");
        request.addHeader("MEMBER_TOKEN", "d4016952efb53698d526b97e7518fb0e");
    }

    public static <T> Observable<T> createUploadArray(final BaseRequest params, final Class<T> classOfT) {
        //放到线程池中，实现队列
        getQueue().submit(new RequestThreadQueue(params));
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    while (params.isWait) {

                    }
                    Request<String> request = NoHttp.createStringRequest(params.url, RequestMethod.POST);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);
                    SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
                    if (sslContext != null) {
                        request.setSSLSocketFactory(sslContext.getSocketFactory());
                    }

                    /**
                     * 给上传文件做个监听，可以不需要
                     */
                    List<UploadFile> files = params.uploadFiles;

                    request.add(params.mapParams);
                    for (UploadFile file : files) {
                        FileBinary fileBinary = new FileBinary(file.getFile());
                        request.add(file.getKey(), file.getFile());
                        request.add("", fileBinary);
                    }
                    final Response<String> response = NoHttp.startRequestSync(request);
                    if (response.isSucceed()) {
                        String json = response.get();
                        XLog.json(json);
                        subscriber.onNext(JsonUtils.object(json, classOfT));
                    } else {
                        subscriber.onError(response.getException());
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                params.isQueueEnd = true;
                subscriber.onCompleted();


            }
        });
    }

    public static <T> Observable<T> load(final BaseRequest params, final Class<T> classOfT) {
        //放到线程池中，实现队列
        getQueue().submit(new RequestThreadQueue(params));
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    while (params.isWait) {

                    }
                    Request<T> request = (Request<T>) NoHttp.createStringRequest(params.url, params.requestMethod);
                    if (RequestMethod.POST == params.requestMethod) {
                        request.setDefineRequestBodyForJson(params.params);
                    }
                    request.setCacheKey(params.url);
                    request.setCacheMode(params.cacheMode);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);
                    SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
                    if (sslContext != null) {
                        request.setSSLSocketFactory(sslContext.getSocketFactory());
                    }
                    final Response<T> response = NoHttp.startRequestSync(request);

                    if (response.isSucceed()) {
                        String json = (String) response.get();
                        XLog.json(json);
                        if (classOfT.equals(String.class)) {
                            subscriber.onNext((T) json);
                        } else {
                            try {
                                subscriber.onNext(JsonUtils.object(json, classOfT));
                            } catch (Exception e) {
                                subscriber.onError(e);
                            }
                        }
                    } else {
                        subscriber.onError(response.getException());
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                    XLog.d("", e);
                }
                params.isQueueEnd = true;
                subscriber.onCompleted();
            }
        });
    }

    /**
     * @param params 参数
     * @param l      回调
     * @param <T>
     */
    public static <T> void load(final BaseRequest params, final RequestListener<T> l) {

        Class<T> entityClass = (Class<T>) ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final Request<T> request;
        if (entityClass == String.class) {
            request = (Request<T>) NoHttp.createStringRequest(params.url, params.requestMethod);
        } else {
            request = new ArrayRequest(params.url, params.requestMethod, entityClass);
        }
        if (RequestMethod.POST == params.requestMethod) {
            request.setDefineRequestBodyForJson(params.params);
        }
        final LoadingDialog dialog = new LoadingDialog(params.context);
        if (!TextUtils.isEmpty(params.loadingTitle)) {
            dialog.setLoadText(params.loadingTitle);
        }
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                request.cancel();
            }
        });

        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        request.setConnectTimeout(params.timeOut);
        request.setRetryCount(params.retry);
        request.setTag(params.context);
        request.setCacheMode(params.cacheMode);
        request.setCancelSign(params.context);
        /**
         * 如果有头部请求则调用getHeader
         * 把自己需要定义的参数都传过去即可
         */
        //getHeader(request);
        getInstance().add(0, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (dialog != null && !dialog.isShowing() && params.isLoading)
                    dialog.show();
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onSuccess(response.get());
                }
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onFailed(response.getException());
                }
                XLog.d("", response.getException());
            }

            @Override
            public void onFinish(int what) {
                if (dialog != null && dialog.isShowing() && params.isLoading)
                    dialog.dismiss();
            }
        });
    }

    /**
     * 下载文件
     *
     * @param context     地址
     * @param url         请求标记
     * @param what        文件路径
     * @param fileFolder  文件名字
     * @param filename    是否续传
     * @param isRange     存在是否删除
     * @param isDeleteOld 回调
     * @param l
     */

    public static void loadDownload(Context context, String url, int what, String fileFolder, String filename, boolean isRange, boolean isDeleteOld, final DownloadListener l) {
        final DownloadRequest request = NoHttp.createDownloadRequest(url, fileFolder, filename, isRange, isDeleteOld);
        request.setTag(context);
        request.setCancelSign(context);

        getInstance1().add(what, request, new DownloadListener() {

            @Override
            public void onDownloadError(int what, Exception exception) {
                if (l != null) {
                    l.onDownloadError(what, exception);
                }
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

                if (l != null) {
                    l.onStart(what, isResume, rangeSize, responseHeaders, allCount);
                }

            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {

                if (l != null) {
                    l.onProgress(what, progress, fileCount, speed);
                }
            }

            @Override
            public void onFinish(int what, String filePath) {
                if (l != null) {
                    l.onFinish(what, filePath);
                }
            }

            @Override
            public void onCancel(int what) {
                if (l != null) {
                    l.onCancel(what);
                }
            }
        });
    }

    /**
     * 反回String
     *
     * @param params 参数
     * @param l
     */
    public static <T> void upload(final BaseRequest params, final RequestListener<T> l) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final Request<T> request;
        if (entityClass == String.class) {
            request = (Request<T>) NoHttp.createStringRequest(params.url, RequestMethod.POST);
        } else {
            request = new ArrayRequest(params.url, params.requestMethod, entityClass);
        }


        if (!TextUtils.isEmpty(params.params)) {
            request.setDefineRequestBodyForJson(params.params);
        }
        /**
         * 给上传文件做个监听，可以不需要
         */
        List<UploadFile> files = params.uploadFiles;
        for (UploadFile file : files) {

            /**
             * 需要注意
             * FileBinary这里支持多种上传方式
             * 如：FileBinary
             *    BitmapBinary
             *    InputStreamBinary
             *    这里只处理一种 因为一个项目应该不会出现2种上传类型的方式
             *    如果自己的项目用的BitmapBinary 只需要把 UploadFile里面的类型修改就行了
             *
             * BasicBinary binary3 = new InputStreamBinary(new FileInputStream(file3), file3.getName());
             * BasicBinary binary2 = new BitmapBinary(file2, "userHead.jpg");// 或者：BasicBinary binary2 = new BitmapBinary(file2, null);
             *
             */
            FileBinary fileBinary = new FileBinary(file.getFile());
            request.add(file.getKey(), file.getFile());
            fileBinary.setUploadListener(file.getWhat(), new OnUploadListener() {
                @Override
                public void onStart(int what) {
                    if (l != null) {
                        l.onStart(what);
                    }
                }

                @Override
                public void onCancel(int what) {
                    if (l != null) {
                        l.onCancel(what);
                    }
                }

                @Override
                public void onProgress(int what, int progress) {
                    if (l != null) {
                        l.onProgress(what, progress);
                    }
                }

                @Override
                public void onFinish(int what) {
                    if (l != null) {
                        l.onFinish(what);
                    }
                }

                @Override
                public void onError(int what, Exception exception) {
                    if (l != null) {
                        l.onError(what, exception);
                    }
                }
            });
            /**
             * 这个key可以不传
             * 目前没发现有什么不一样的
             */
            request.add("", fileBinary);
        }
        /**
         * 如果有头部请求则调用getHeader
         * 把自己需要定义的参数都传过去即可
         */
        //getHeader(request);
        request.setTag(params.context);
        final LoadingDialog dialog = new LoadingDialog(params.context);
        if (!TextUtils.isEmpty(params.loadingTitle)) {
            dialog.setLoadText(params.loadingTitle);
        }
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                request.cancel();
            }
        });
        getInstance().add(0, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (dialog != null && !dialog.isShowing() && params.isLoading)
                    dialog.show();
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onSuccess(response.get());
                }
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onFailed(response.getException());
                }
                XLog.d("", response.getException());
            }

            @Override
            public void onFinish(int what) {
                if (dialog != null && dialog.isShowing() && params.isLoading)
                    dialog.dismiss();
            }
        });
    }


    public static void clearAll() {
        getInstance().cancelAll();
    }
}
