package com.yuanshenbin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yuanshenbin.bean.PuBuLiuModel;
import com.yuanshenbin.nohttp.IRequest;
import com.yuanshenbin.nohttp.RequestListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * 所有回调都用抽象类来处理，
         * 我是这样想的，如果实现接口，接口太多还得都实现出来，不喜欢
         * 而且如果实现抽象类来回调，我需要什么回调，我就实现什么回调
         * 日后还有扩展其他回调也方便
         * get和post 都默认显示onSuccess回调
         * onFailed 可以根据自己需求实现出来，里面可以统一做异常处理
         */


        /**
         * 这个post里面只处理了Body请求方式，如果你需要传map
         * 只需要把BaseRequest 里面的params修改成map就行
         * 把他对应的方法也改成map
         * 在到RequestManager 里面的load的方法把map循环 一一复制给Request即可
         */

        /**
         * Header 也封装了，统一管理 在在到RequestManager里面
         */


        /**
         * 所有请求都采用链式请求
         * 在BaseRequest 里面有多种常用的样式
         *  目前里面没有加入缓存
         *  大家可以参考aseRequest 加入缓存，
         *  按着一样的写就行了
         */


        /**
         * 目前就只写了4中请求，基本是常用的,
         * 如果还有人用到delete 之类的 则在
         * IRequest里面添加就行了
         */


        /**
         * 如果需要用到https 只需要去在到RequestManager
         *
         * loadString 里面吧注视打开即可
         */

        /**
         * 文件下载 如果只单文件下载和断点 则 IRequest已经处理好了
         *  如果是多文件的话 要知道每个文件的状态和当前进度   则参考 nohttp官方的例子写就好了
         */


        /**
         * 上传支持多文件处理
         * 也支持多文件回调
         */

        String mUrl = "http://image.baidu.com/channel/listjson?pn=" + 1
                + "&rn=" + 22
                + "&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8";

        IRequest.get(this, mUrl)
                .execute(new RequestListener<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }
                });
        IRequest.get(this, mUrl)
                .params("", "")
                .loading(true)
                .execute(new RequestListener<String>() {

                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onFailed(Exception e) {
                        super.onFailed(e);
                    }
                });
        IRequest.get(this, mUrl)
                .params("", "")
                .loading(true)
                .execute(new RequestListener<PuBuLiuModel>() {
                    @Override
                    public void onSuccess(PuBuLiuModel result) {
                        Toast.makeText(MainActivity.this, result.getStart_index() + "", Toast.LENGTH_SHORT).show();
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
