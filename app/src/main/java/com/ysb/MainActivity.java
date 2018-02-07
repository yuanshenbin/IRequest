package com.ysb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.AbstractResponseUpload;
import com.yuanshenbin.network.AbstractUploadResponse;
import com.yuanshenbin.network.ResponseEnum;
import com.yuanshenbin.network.model.ResponseModel;
import com.yuanshenbin.network.request.IRequest;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String mUrl = "http://image.baidu.com/channel/listjson?pn=" + 1
                + "&rn=" + 22
                + "&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8";
        IRequest.get(this, mUrl)
                .params("", "")
                .loading(true)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }
                });

        IRequest.get(this, "")
                .execute(new AbstractResponse<File>() {
                    @Override
                    public void onSuccess(File result) {

                    }

                    @Override
                    public void onResponseState(ResponseModel result) {
                        super.onResponseState(result);
                    }
                });
        IRequest.upload(this, "")
                .execute(new AbstractUploadResponse<Object>() {
                    @Override
                    public void onSuccess(Object result) {

                    }
                });
        IRequest.download(this, "")
                .execute(new DownloadListener() {
                    @Override
                    public void onDownloadError(int what, Exception exception) {

                    }

                    @Override
                    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

                    }

                    @Override
                    public void onProgress(int what, int progress, long fileCount, long speed) {

                    }

                    @Override
                    public void onFinish(int what, String filePath) {

                    }

                    @Override
                    public void onCancel(int what) {

                    }
                });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}
