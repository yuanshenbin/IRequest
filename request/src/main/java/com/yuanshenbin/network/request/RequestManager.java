package com.yuanshenbin.network.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.InputStreamBinary;
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
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.AbstractUploadResponse;
import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.INetDialog;
import com.yuanshenbin.network.NetworkConfig;
import com.yuanshenbin.network.ResponseEnum;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.ResponseModel;
import com.yuanshenbin.network.model.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.net.ssl.SSLContext;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by Jacky on 2016/10/31.
 */
public class RequestManager {


    private static RequestQueue mRequestQueue;
    private static DownloadQueue mDownloadQueue;

    /**
     * 数据请求的Queue
     *
     * @return
     */
    private static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (RequestManager.class) {
                mRequestQueue = NoHttp.newRequestQueue(NetworkManager.getInstance().getInitializeConfig().getThreadPoolSize());
            }
        }

        return mRequestQueue;
    }

    /**
     * 文件下载的Queue
     *
     * @return
     */
    private static DownloadQueue getDownloadQueue() {
        if (mDownloadQueue == null) {
            synchronized (RequestManager.class) {
                mDownloadQueue = NoHttp.newDownloadQueue(NetworkManager.getInstance().getInitializeConfig().getThreadPoolSize());
            }
        }
        return mDownloadQueue;
    }


    private RequestManager() {

    }

    public static <T> Observable<T> upload(final BaseRequest params, final AdaptResponse<T> l) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
                try {
                    Request<String> request = new StringRequest(params.url, RequestMethod.POST);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);
                    if (networkConfig.getHeader() != null) {
                        networkConfig.getHeader().onHeader(request);
                    }
                    request.add(params.mapParams);
                    if (params.headerParam.size() != 0) {
                        request.add(params.headerParam);

                    }
                    SSLContext sslContext = networkConfig.getSSLContext();
                    if (sslContext != null) {
                        request.setSSLSocketFactory(sslContext.getSocketFactory());
                    }

                    /**
                     * 上传文件
                     */
                    List<UploadFile> files = params.uploadFiles;
                    for (UploadFile file : files) {
                        BasicBinary basicBinary = null;
                        if (file.getMode() instanceof File) {
                            basicBinary = new FileBinary((File) file.getMode(), file.getKey());
                        } else if (file.getMode() instanceof Bitmap) {
                            basicBinary = new BitmapBinary((Bitmap) file.getMode(), file.getKey());

                        } else if (file.getMode() instanceof FileInputStream) {
                            basicBinary = new InputStreamBinary((FileInputStream) file.getMode(), file.getKey());
                        }
                        request.add(file.getKey(), basicBinary);
                    }

                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                                params.mapParams);
                    }

                    final Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                    if (response.isSucceed()) {
                        String json = response.get();
                        networkConfig.getIPrintLog().onPrintResult(json);
                        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        if (type == String.class) {
                            e.onNext((T) json);
                        } else {
                            e.onNext((T) networkConfig.getFromJson().onFromJson(json, type));
                        }

                    } else {
                        e.onError(response.getException());
                    }
                    if (networkConfig.getIDevelopMode() != null) {
                        networkConfig.getIDevelopMode().onRecord(params, response.get());
                    }


                } catch (Exception exception) {
                    e.onError(exception);
                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintException(exception);
                    }
                }
                e.onComplete();
            }
        });

    }

    public static <T> Observable<T> load(final BaseRequest params, final AdaptResponse<T> l) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
                try {
                    Request<String> request = new StringRequest(params.url, params.requestMethod);
                    if (RequestMethod.POST == params.requestMethod) {
                        request.setDefineRequestBodyForJson(params.params);
                    }
                    request.setCacheKey(params.url);
                    request.setCacheMode(params.cacheMode);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);
                    if (networkConfig.getHeader() != null) {
                        networkConfig.getHeader().onHeader(request);
                    }
                    SSLContext sslContext = networkConfig.getSSLContext();
                    if (sslContext != null) {
                        request.setSSLSocketFactory(sslContext.getSocketFactory());
                    }

                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                                params.mapParams);
                    }
                    final Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                    if (response.isSucceed()) {
                        String json = response.get();
                        networkConfig.getIPrintLog().onPrintResult(json);
                        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        if (type == String.class) {
                            e.onNext((T) json);
                        } else {
                            e.onNext((T) networkConfig.getFromJson().onFromJson(json, type));
                        }

                    } else {
                        e.onError(response.getException());
                    }
                    if (networkConfig.getIDevelopMode() != null) {
                        networkConfig.getIDevelopMode().onRecord(params, response.get());
                    }


                } catch (Exception exception) {
                    e.onError(exception);
                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintException(exception);
                    }
                }
                e.onComplete();
            }
        });

    }

    /**
     * @param params 参数
     * @param l      回调
     * @param <T>
     */
    public static <T> void load(final BaseRequest params, final AbstractResponse<T> l) {

        final NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
        final INetDialog iDialog = networkConfig.getDialog();

        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final Request<T> request;
        if (type == String.class) {
            request = (Request<T>) new com.yuanshenbin.network.request.StringRequest(params.url, params.requestMethod);
        } else {
            request = new EntityRequest<>(params.url, params.requestMethod, type);
        }
        if (RequestMethod.POST == params.requestMethod) {
            request.setDefineRequestBodyForJson(params.params);
        }
        if (iDialog != null) {
            iDialog.init(params.context);
            iDialog.setCancelable(params.isCloseDialog);
            if (!TextUtils.isEmpty(params.loadingTitle)) {
                iDialog.setMessage(params.loadingTitle);
            }
        }

        SSLContext sslContext = networkConfig.getSSLContext();
        if (sslContext != null) {
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        request.setConnectTimeout(params.timeOut);
        request.setRetryCount(params.retry);
        request.setCacheMode(params.cacheMode);
        request.setTag(params.context);
        request.setCancelSign(params.context);
        if (networkConfig.getHeader() != null) {
            networkConfig.getHeader().onHeader(request);
        }

        if (networkConfig.getIPrintLog() != null) {
            networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                    params.mapParams);
        }

        getRequestQueue().add(params.what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (iDialog != null && params.isLoading)
                    iDialog.show();
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.开始));
                }
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.成功));
                    l.onSuccess(response.get());
                }
                if (networkConfig.getIDevelopMode() != null) {
                    networkConfig.getIDevelopMode().onRecord(params, response.get());
                }
                if (networkConfig.getNetworkLinstener() != null) {
                    networkConfig.getNetworkLinstener().onResetToken(params.context, response.get());
                }


            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.失败, response.getException()));
                    l.onFailed(response.getException());
                }
                if (networkConfig.getIDevelopMode() != null) {
                    networkConfig.getIDevelopMode().onRecord(params, response.get());
                }

                if (networkConfig.getIPrintLog() != null) {
                    networkConfig.getIPrintLog().onPrintException(response.getException());
                }
            }

            @Override
            public void onFinish(int what) {
                if (iDialog != null && params.isLoading)
                    iDialog.dismiss();
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.结束));
                }
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

        SSLContext sslContext = NetworkManager.getInstance().getInitializeConfig().getSSLContext();
        if (sslContext != null) {
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        getDownloadQueue().add(what, request, new DownloadListener() {

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
     * @param params 参数
     * @param l
     */
    public static <T> void upload(final BaseRequest params, final AbstractUploadResponse<T> l) {
        final NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
        final INetDialog iDialog = networkConfig.getDialog();

        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final Request<T> request;
        if (type == String.class) {
            request = (Request<T>) new com.yuanshenbin.network.request.StringRequest(params.url, params.requestMethod);
        } else {
            request = new EntityRequest<>(params.url, params.requestMethod, type);
        }
        request.add(params.mapParams);
        SSLContext sslContext = networkConfig.getSSLContext();
        if (sslContext != null) {
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        if (networkConfig.getHeader() != null) {
            networkConfig.getHeader().onHeader(request);
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
            BasicBinary basicBinary = null;
            if (file.getMode() instanceof File) {
                basicBinary = new FileBinary((File) file.getMode(), file.getKey());
            } else if (file.getMode() instanceof Bitmap) {
                basicBinary = new BitmapBinary((Bitmap) file.getMode(), file.getKey());

            } else if (file.getMode() instanceof FileInputStream) {
                basicBinary = new InputStreamBinary((FileInputStream) file.getMode(), file.getKey());
            }
            request.add(file.getKey(), basicBinary);

            basicBinary.setUploadListener(file.getWhat(), new OnUploadListener() {
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
        }

        if (iDialog != null) {
            iDialog.init(params.context);
            iDialog.setCancelable(params.isCloseDialog);
            if (!TextUtils.isEmpty(params.loadingTitle)) {
                iDialog.setMessage(params.loadingTitle);
            }
        }
        request.setTag(params.context);
        request.setCancelSign(params.context);
        if (networkConfig.getIPrintLog() != null) {
            networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                    params.mapParams);
        }
        getRequestQueue().add(params.what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.开始));
                }
                if (iDialog != null && params.isLoading)
                    iDialog.show();
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.成功));
                    l.onSuccess(response.get());
                }
                if (networkConfig.getIDevelopMode() != null) {
                    networkConfig.getIDevelopMode().onRecord(params, response.get());
                }
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.失败, response.getException()));
                    l.onFailed(response.getException());
                }

                if (networkConfig.getIDevelopMode() != null) {
                    networkConfig.getIDevelopMode().onRecord(params, response.get());
                }
                if (networkConfig.getIPrintLog() != null) {
                    networkConfig.getIPrintLog().onPrintException(response.getException());
                }
            }

            @Override
            public void onFinish(int what) {
                if (iDialog != null && params.isLoading)
                    iDialog.dismiss();
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.结束));
                }
            }
        });
    }


    public static void clearRequestAll() {
        getRequestQueue().cancelAll();
    }

    public static void clearRequest(Context context) {
        getRequestQueue().cancelBySign(context);
    }

    public static void clearDownload(Context context) {
        getDownloadQueue().cancelBySign(context);
    }

    public static void clearDownloadAll() {
        getDownloadQueue().cancelAll();
    }
}
