# 特点
### 链式请求
### 支持mvp+rxjava1(支持队列)    项目有mvp的例子，修改了一下MVPHelper自动生成mvp代码
### 易于根据自己项目扩展

NoHttp作者
[https://github.com/yanzhenjie/NoHttp](url)

MVPHelper作者
[https://github.com/githubwing/MVPHelper](url)

okhttputils作者(参考里面的链式写法)
[https://github.com/hongyangAndroid/okhttputils](url)

# 请求例子

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