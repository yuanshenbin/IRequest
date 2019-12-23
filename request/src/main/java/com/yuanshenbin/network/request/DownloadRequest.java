package com.yuanshenbin.network.request;

import android.content.Context;

import com.yanzhenjie.nohttp.download.DownloadListener;


/**
 * Created by yuanshenbin on 2016/10/31.
 */
public class DownloadRequest extends FileRequest<DownloadRequest> {
    public DownloadRequest(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public void execute(DownloadListener l) {

        RequestManager.getInstance().loadDownload(context, url, what, fileFolder, fileName, isRange, isDeleteOld, l);
    }
}
