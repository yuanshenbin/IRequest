package com.yuanshenbin.network.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
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
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.AbstractUploadResponse;
import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.INetDialog;
import com.yuanshenbin.network.NetworkConfig;
import com.yuanshenbin.network.ResponseEnum;
import com.yuanshenbin.network.constant.Constants;
import com.yuanshenbin.network.error.ResultError;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.RecordModel;
import com.yuanshenbin.network.model.ResponseModel;
import com.yuanshenbin.network.model.SyncResult;
import com.yuanshenbin.network.model.UploadFile;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by yuanshenbin on 2016/10/31.
 */
public class RequestManager {


    private RequestQueue mRequestQueue;
    private DownloadQueue mDownloadQueue;
    private Map<String, INetDialog> mNetDialogMap = new HashMap<>();


    private static RequestManager instance;

    public static RequestManager getInstance() {
        if (instance == null)
            synchronized (RequestManager.class) {
                if (instance == null)
                    instance = new RequestManager();
            }
        return instance;
    }

    private RequestManager() {

        getRequestQueue();
        getDownloadQueue();
    }

    /**
     * 数据请求的Queue
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = NoHttp.newRequestQueue(NetworkManager.getInstance().getInitializeConfig().getThreadPoolSize());
        }
        return mRequestQueue;
    }

    /**
     * 文件下载的Queue
     *
     * @return
     */
    public DownloadQueue getDownloadQueue() {
        if (mDownloadQueue == null) {
            mDownloadQueue = NoHttp.newDownloadQueue(NetworkManager.getInstance().getInitializeConfig().getThreadPoolSize());
        }
        return mDownloadQueue;
    }


    public <T> Observable<T> upload(final BaseRequest params, final AdaptResponse<T> l) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
                try {
                    StringRequest request = new StringRequest(params.url, RequestMethod.POST);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);
                    if (networkConfig.getHeader() != null) {
                        networkConfig.getHeader().onHeader(request);
                    }
                    request.add(params.mapParams);
                    handleAddHeader(params, request);
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
                        request.add(params.fileKey, basicBinary);
                    }

                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                                params.mapParams);
                    }

                    final Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                    if (response.isSucceed()) {
                        String json = response.get();
                        networkConfig.getIPrintLog().onPrintResult(json);
                        int resCode = response.getHeaders().getResponseCode();
                        if (resCode >= 200 && resCode < 300) { // Http层成功，这里只可能业务逻辑错误。
                            Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            if (type == String.class) {
                                e.onNext((T) json);
                            } else {
                                try {
                                    e.onNext((T) networkConfig.getFromJson().onFromJson(json, type));
                                } catch (Exception fromjson) {
                                    record(params, request, fromjson);
                                    e.onError(new ResultError(Constants.HTTP_SERVER_DATA_FORMAT_ERROR));
                                }
                            }

                        } else if (resCode >= 400 && resCode < 500) {
                            record(params, request, new Exception(json));
                            e.onError(new ResultError(Constants.HTTP_UNKNOW_ERROR + resCode));
                        } else {
                            record(params, request, new Exception(json));
                            e.onError(new ResultError(Constants.HTTP_SERVER_ERROR + resCode));
                        }

                    } else {
                        int resCode = -1;
                        if (response.getHeaders() != null) {
                            resCode = response.getHeaders().getResponseCode();
                        }
                        Exception exception = response.getException();
                        String stringRes = Constants.HTTP_UNKNOW_ERROR + resCode;
                        if (exception instanceof NetworkError) {
                            stringRes = Constants.HTTP_EXCEPTION_NETWORK + resCode;
                        } else if (exception instanceof TimeoutError) {
                            stringRes = Constants.HTTP_EXCEPTION_CONNECT_TIMEOUT + resCode;
                        } else if (exception instanceof UnKnownHostError) {
                            stringRes = Constants.HTTP_EXCEPTION_HOST + resCode;
                        } else if (exception instanceof URLError) {
                            stringRes = Constants.HTTP_EXCEPTION_URL + resCode;
                        } else if (exception instanceof ResultError) {
                            stringRes = exception.getMessage() + resCode;
                        }

                        record(params, request, new Exception(stringRes, exception));

                        e.onError(new ResultError(stringRes, exception));
                        if (networkConfig.getIPrintLog() != null) {
                            networkConfig.getIPrintLog().onPrintException(new Exception(stringRes, exception));
                        }

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

    public void record(BaseRequest params, StringRequest request, Exception exception) {
        DeviceBandwidthSampler.getInstance().stopSampling();
        RecordModel model = null;
        if (NetworkManager.getInstance().getInitializeConfig().getIDevelopMode() != null) {
            String connectionQuality = ConnectionClassManager.getInstance().getCurrentBandwidthQualityStr();
            double downloadKBitsPerSecond = ConnectionClassManager.getInstance().getDownloadKBitsPerSecond();
            model = new RecordModel(params.url, request.getParam(), "", System.currentTimeMillis() - request.getStart(), connectionQuality, downloadKBitsPerSecond, exception, request.getHeaders().toRequestHeaders());
            NetworkManager.getInstance().getInitializeConfig().getIDevelopMode().onRecord(model);
        }
    }


    public <T> SyncResult loadSync(final BaseRequest params, final AdaptResponse<T> l) {
        SyncResult<T> syncResult = new SyncResult<>();
        NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
        try {
            StringRequest request = new StringRequest(params.url, params.requestMethod);
            String body = "";
            if (params.requestMethod.getValue().equals(RequestMethod.POST.getValue())) {
                body = postConversion(networkConfig, params, request);
            }
            if (TextUtils.isEmpty(params.contentType)) {
                request.setContentType(networkConfig.getContentType());
            } else {
                request.setContentType(params.contentType);
            }
            request.setCacheKey(params.url);
            request.setCacheMode(params.cacheMode);
            request.setConnectTimeout(params.timeOut);
            request.setRetryCount(params.retry);

            if (networkConfig.getHeader() != null) {
                networkConfig.getHeader().onHeader(request);
            }
            handleAddHeader(params, request);

            SSLContext sslContext = networkConfig.getSSLContext();
            if (sslContext != null) {
                request.setSSLSocketFactory(sslContext.getSocketFactory());
            }

            if (networkConfig.getIPrintLog() != null) {
                networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                        params.params);
            }
            final Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
            if (response.isSucceed()) {
                String json = response.get();
                networkConfig.getIPrintLog().onPrintResult(json);
                int resCode = response.getHeaders().getResponseCode();
                if (resCode >= 200 && resCode < 300) { // Http层成功，这里只可能业务逻辑错误。
                    Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    if (type == String.class) {
                        syncResult.setResult((T) json);
                    } else {
                        try {
                            syncResult.setResult((T) networkConfig.getFromJson().onFromJson(json, type));
                        } catch (Exception fromjson) {
                            record(params, request, fromjson);
                            syncResult.setException(new ResultError(Constants.HTTP_SERVER_DATA_FORMAT_ERROR));
                        }
                    }

                } else if (resCode >= 400 && resCode < 500) {
                    record(params, request, new Exception(json));
                    syncResult.setException(new ResultError(Constants.HTTP_UNKNOW_ERROR+resCode));
                } else {
                    record(params, request, new Exception(json));
                    syncResult.setException(new ResultError(Constants.HTTP_SERVER_ERROR+resCode));
                }
            } else {
                int resCode = -1;
                if (response.getHeaders() != null) {
                    resCode = response.getHeaders().getResponseCode();
                }
                Exception exception = response.getException();
                String stringRes = Constants.HTTP_UNKNOW_ERROR+resCode;
                if (exception instanceof NetworkError) {
                    stringRes = Constants.HTTP_EXCEPTION_NETWORK+resCode;
                } else if (exception instanceof TimeoutError) {
                    stringRes = Constants.HTTP_EXCEPTION_CONNECT_TIMEOUT+resCode;
                } else if (exception instanceof UnKnownHostError) {
                    stringRes = Constants.HTTP_EXCEPTION_HOST+ resCode;
                } else if (exception instanceof URLError) {
                    stringRes = Constants.HTTP_EXCEPTION_URL+ resCode;
                } else if (exception instanceof ResultError) {
                    stringRes = exception.getMessage()+ resCode;
                }

                record(params, request, new Exception(stringRes, exception));

                syncResult.setException(new ResultError(stringRes, exception));
                if (networkConfig.getIPrintLog() != null) {
                    networkConfig.getIPrintLog().onPrintException(new Exception(stringRes, exception));
                }

            }


        } catch (Exception exception) {
            syncResult.setException(exception);
            if (networkConfig.getIPrintLog() != null) {
                networkConfig.getIPrintLog().onPrintException(exception);
            }
        }

        return syncResult;
    }

    public <T> Observable<T> load(final BaseRequest params, final AdaptResponse<T> l) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();
                try {
                    StringRequest request = new StringRequest(params.url, params.requestMethod);
                    String body = "";
                    if (params.requestMethod.getValue().equals(RequestMethod.POST.getValue())) {
                        body = postConversion(networkConfig, params, request);
                    }

                    if (TextUtils.isEmpty(params.contentType)) {
                        request.setContentType(networkConfig.getContentType());
                    } else {
                        request.setContentType(params.contentType);
                    }
                    request.setCacheKey(params.url);
                    request.setCacheMode(params.cacheMode);
                    request.setConnectTimeout(params.timeOut);
                    request.setRetryCount(params.retry);

                    if (networkConfig.getHeader() != null) {
                        networkConfig.getHeader().onHeader(request);
                    }
                    handleAddHeader(params, request);

                    SSLContext sslContext = networkConfig.getSSLContext();
                    if (sslContext != null) {
                        request.setSSLSocketFactory(sslContext.getSocketFactory());
                    }

                    if (networkConfig.getIPrintLog() != null) {
                        networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                                params.params);
                    }
                    final Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                    if (response.isSucceed()) {
                        String json = response.get();
                        networkConfig.getIPrintLog().onPrintResult(json);
                        int resCode = response.getHeaders().getResponseCode();
                        if (resCode >= 200 && resCode < 300) { // Http层成功，这里只可能业务逻辑错误。
                            Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            if (type == String.class) {
                                e.onNext((T) json);
                            } else {
                                try {
                                    e.onNext((T) networkConfig.getFromJson().onFromJson(json, type));
                                } catch (Exception fromjson) {
                                    record(params, request, fromjson);
                                    e.onError(new ResultError(Constants.HTTP_SERVER_DATA_FORMAT_ERROR));
                                }
                            }

                        } else if (resCode >= 400 && resCode < 500) {
                            record(params, request, new Exception(json));
                            e.onError(new ResultError(Constants.HTTP_UNKNOW_ERROR + resCode));
                        } else {
                            record(params, request, new Exception(json));
                            e.onError(new ResultError(Constants.HTTP_SERVER_ERROR + resCode));
                        }
                    } else {
                        int resCode = -1;
                        if (response.getHeaders() != null) {
                            resCode = response.getHeaders().getResponseCode();
                        }
                        Exception exception = response.getException();
                        String stringRes = Constants.HTTP_UNKNOW_ERROR + resCode;
                        if (exception instanceof NetworkError) {
                            stringRes = Constants.HTTP_EXCEPTION_NETWORK + resCode;
                        } else if (exception instanceof TimeoutError) {
                            stringRes = Constants.HTTP_EXCEPTION_CONNECT_TIMEOUT + resCode;
                        } else if (exception instanceof UnKnownHostError) {
                            stringRes = Constants.HTTP_EXCEPTION_HOST + resCode;
                        } else if (exception instanceof URLError) {
                            stringRes = Constants.HTTP_EXCEPTION_URL + resCode;
                        } else if (exception instanceof ResultError) {
                            stringRes = exception.getMessage() + resCode;
                        }

                        record(params, request, new Exception(stringRes, exception));

                        e.onError(new ResultError(stringRes, exception));
                        if (networkConfig.getIPrintLog() != null) {
                            networkConfig.getIPrintLog().onPrintException(new Exception(stringRes, exception));
                        }

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
    public <T> void load(final BaseRequest params, final AbstractResponse<T> l) {

        final NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();

        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final Request<T> request;
        if (type == String.class) {
            request = (Request<T>) new StringRequest(params.url, params.requestMethod);
        } else {
            request = new EntityRequest<>(params.url, params.requestMethod, type);
        }
        String body = "";
        if (params.requestMethod.getValue().equals(RequestMethod.POST.getValue())) {
            body = postConversion(networkConfig, params, request);
        }
        initDialog(params);
        SSLContext sslContext = networkConfig.getSSLContext();
        if (sslContext != null) {
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        if (TextUtils.isEmpty(params.contentType)) {
            request.setContentType(networkConfig.getContentType());
        } else {
            request.setContentType(params.contentType);
        }
        request.setConnectTimeout(params.timeOut);
        request.setRetryCount(params.retry);
        request.setCacheMode(params.cacheMode);
        if (networkConfig.getHeader() != null) {
            networkConfig.getHeader().onHeader(request);
        }
        handleAddHeader(params, request);
        request.setCancelSign(params.context);
        if (networkConfig.getIPrintLog() != null) {
            networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                    params.params);
        }
        final Map<String, String> requestHeaders = request.getHeaders().toRequestHeaders();
        getRequestQueue().add(params.what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                showDialog(params);
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.开始));
                }
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.成功, response.getHeaders().toResponseHeaders()));
                    l.onSuccess(response.get());
                }

                if (networkConfig.getNetworkLinstener() != null) {
                    networkConfig.getNetworkLinstener().onResetToken(params.context, response.get());
                }


            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.失败, response.getException(),response.getHeaders()!=null response.getHeaders().toResponseHeaders():new HashMap<>()));
                    l.onFailed(response.getException());
                }
                DeviceBandwidthSampler.getInstance().stopSampling();

                if (networkConfig.getIDevelopMode() != null) {
                    String connectionQuality = ConnectionClassManager.getInstance().getCurrentBandwidthQualityStr();
                    double downloadKBitsPerSecond = ConnectionClassManager.getInstance().getDownloadKBitsPerSecond();
                    networkConfig.getIDevelopMode().onRecord(new RecordModel(params.url, params.params, response.get() == null ? "" : response.get().toString(), connectionQuality, downloadKBitsPerSecond, response.getException(), requestHeaders));
                }

                if (networkConfig.getIPrintLog() != null) {
                    networkConfig.getIPrintLog().onPrintException(response.getException());
                }
            }

            @Override
            public void onFinish(int what) {
                dismissDialog(params);
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

    public void loadDownload(Context context, String url, int what, String fileFolder, String filename, boolean isRange, boolean isDeleteOld, final DownloadListener l) {
        final DownloadRequest request = NoHttp.createDownloadRequest(url, fileFolder, filename, isRange, isDeleteOld);
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
    public <T> void upload(final BaseRequest params, final AbstractUploadResponse<T> l) {
        final NetworkConfig networkConfig = NetworkManager.getInstance().getInitializeConfig();

        Type type = ((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Request<T> request;
        if (type == String.class) {
            request = (Request<T>) new StringRequest(params.url, params.requestMethod);
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
        handleAddHeader(params, request);

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
                basicBinary = new FileBinary((File) file.getMode(), file.getKey(),params.mimeType);
            } else if (file.getMode() instanceof Bitmap) {
                basicBinary = new BitmapBinary((Bitmap) file.getMode(), file.getKey(),params.mimeType);

            } else if (file.getMode() instanceof FileInputStream) {
                basicBinary = new InputStreamBinary((FileInputStream) file.getMode(), file.getKey(),params.mimeType);
            }
            request.add(params.fileKey, basicBinary);

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

        initDialog(params);

        request.setCancelSign(params.context);
        if (networkConfig.getIPrintLog() != null) {
            networkConfig.getIPrintLog().onPrintParam(params.url + "\n" +
                    params.mapParams);
        }
        final Map<String, String> requestHeaders = request.getHeaders().toRequestHeaders();
        getRequestQueue().add(params.what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.开始));
                }
                showDialog(params);
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.成功, response.getHeaders().toResponseHeaders()));
                    l.onSuccess(response.get());
                }

            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.失败, response.getException(), response.getHeaders().toResponseHeaders()));
                    l.onFailed(response.getException());
                }
                DeviceBandwidthSampler.getInstance().stopSampling();
                if (networkConfig.getIDevelopMode() != null) {
                    String connectionQuality = ConnectionClassManager.getInstance().getCurrentBandwidthQualityStr();
                    double downloadKBitsPerSecond = ConnectionClassManager.getInstance().getDownloadKBitsPerSecond();
                    networkConfig.getIDevelopMode().onRecord(new RecordModel(params.url, params.mapParams.toString(), response.get() == null ? "" : response.get().toString(), connectionQuality, downloadKBitsPerSecond, response.getException(), requestHeaders));
                }

                if (networkConfig.getIPrintLog() != null) {
                    networkConfig.getIPrintLog().onPrintException(response.getException());
                }
            }

            @Override
            public void onFinish(int what) {
                dismissDialog(params);
                if (l != null) {
                    l.onResponseState(new ResponseModel(ResponseEnum.结束));
                }
            }
        });
    }


    /**
     * 处理addHeader数据
     *
     * @param params
     * @param request
     * @param <T>
     */
    private <T> void handleAddHeader(BaseRequest params, Request<T> request) {
        if (params.headerParam.size() != 0) {
            Iterator<Map.Entry<String, String>> iterator = params.headerParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                request.setHeader(key, value);
            }
        }
    }

    /**
     * post请求参数转换
     */
    private <T> String postConversion(NetworkConfig networkConfig, BaseRequest params, Request<T> request) {

        String param = "";
        if (TextUtils.isEmpty(params.contentType)) {
            if (networkConfig.getContentType().equals(Headers.HEAD_VALUE_CONTENT_TYPE_JSON)) {
                if (!TextUtils.isEmpty(params.params)) {
                    request.setDefineRequestBodyForJson(params.params);
                    param = params.params;
                } else {
                    param = new JSONObject().toString();
                    request.setDefineRequestBodyForJson(param);
                }


            } else {
                if (params.mapParams.size() == 0) {
                    param = params.params;
                    Map<String, Object> formMap = networkConfig.getFromJson().onFromJson(params.params, HashMap.class);
                    request.add(formMap);
                } else {
                    request.add(params.mapParams);
                    param = params.mapParams.toString();
                }
            }
        } else {

            if (params.contentType.equals(Headers.HEAD_VALUE_CONTENT_TYPE_JSON)) {
                if (!TextUtils.isEmpty(params.params)) {
                    request.setDefineRequestBodyForJson(params.params);
                    param = params.params;
                } else {
                    param = new JSONObject().toString();
                    request.setDefineRequestBodyForJson(param);
                }
            } else {
                if (params.mapParams.size() == 0) {
                    param = params.params;
                    Map<String, Object> formMap = networkConfig.getFromJson().onFromJson(params.params, HashMap.class);
                    request.add(formMap);
                } else {
                    request.add(params.mapParams);
                    param = params.mapParams.toString();
                }
            }
        }
        if (request instanceof StringRequest) {
            ((StringRequest) request).setParam(param);
        } else if (request instanceof EntityRequest) {
            ((EntityRequest) request).setParam(param);
        }

        return param;

    }

    private void initDialog(BaseRequest params) {
        if (params.isLoading) {
            INetDialog iDialog;
            if (params.netDialog != null) {
                iDialog = params.netDialog;
                mNetDialogMap.put(String.valueOf(params.hashCode()), iDialog);
            } else {
                iDialog = NetworkManager.getInstance().getInitializeConfig().getDialog();
                mNetDialogMap.put(String.valueOf(params.hashCode()), iDialog);
            }
            iDialog.init(params.context);
            iDialog.setCancelable(params.isCloseDialog);
            if (!TextUtils.isEmpty(params.loadingTitle)) {
                iDialog.setMessage(params.loadingTitle);
            }

        }
    }

    private void showDialog(BaseRequest params) {
        if (params.isLoading) {
            INetDialog iDialog;
            if (params.netDialog != null) {
                iDialog = params.netDialog;
            } else {
                iDialog = mNetDialogMap.get(String.valueOf(params.hashCode()));
            }
            if (iDialog != null && !iDialog.isShowing()) {
                iDialog.show();
            }
        }
    }

    private void dismissDialog(BaseRequest params) {
        if (params.isLoading) {

            INetDialog iDialog;
            if (params.netDialog != null) {
                iDialog = params.netDialog;
            } else {
                iDialog = mNetDialogMap.get(String.valueOf(params.hashCode()));
            }
            if (iDialog != null && iDialog.isShowing()) {
                iDialog.dismiss();
                if (params.netDialog == null) {
                    mNetDialogMap.remove(String.valueOf(params.hashCode()));
                }
            }

        }
    }

    public void clearRequestAll() {
        getRequestQueue().cancelAll();
    }

    public void clearRequest(Context context) {
        getRequestQueue().cancelBySign(context);
    }

    public void clearDownload(Context context) {
        getDownloadQueue().cancelBySign(context);
    }

    public void clearDownloadAll() {
        getDownloadQueue().cancelAll();
    }
}
