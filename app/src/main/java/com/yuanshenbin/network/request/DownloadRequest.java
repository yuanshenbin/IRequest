package com.yuanshenbin.network.request;

import android.content.Context;

import com.yanzhenjie.nohttp.download.DownloadListener;


/**
 * Created by Jacky on 2016/10/31.
 */
public class DownloadRequest extends BaseRequest<DownloadRequest> {
    public DownloadRequest(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public void execute(DownloadListener l) {

        RequestManager.loadDownload(context, url, what, fileFolder, fileName, isRange, isDeleteOld, l);
    }
}
