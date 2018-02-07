--------------------------------------------1.2.0--------------------------------------------

1.正常sdk升级

2.添加token拦截器

3.IRequest内部重构，业务实现全部抽离出，适应公司多个项目

 a.添加支持BaseVO<LoginVO> 类型请求

 b.添加网络业务实现接口

  a>. IDevelopMode  开发者模式
  （我公司有这个，测试提bug，你怀疑是后台数据问题，让测试把入参和出参，分享qq，
   *注意，用系统分享即可，不要集成第三方的，具体实现的就不提供了，公司内部组件）

  c>. IFromJson    数据解析

  d>. IHeader    头部

  e>. INetDialog  请求对话框

  f>. IPrintLog   入参，出参，异常打印

  g>. IToastFailed   统一管理接口请求失败的提示

4.记得初始化

  NetworkManager.getInstance().
                 InitializationConfig(new NetworkConfig.Builder()
                         .fromJson(new FromJsonImpl())
                         .developMode(new DevelopModeImpl())
                         .dialog(new NetDialogImpl())
                         .printLog(new PrintLogImpl())
                         .noHttpConfig(config)
                         .toastFailed(new ToastFailedImpl())
                         .networkLinstener(new NetworkImplLinstener())
                         .build());

5.里面有mvp的代码，互相学习，有生成的插件。另外如果懂python的朋友，可以不需要生成mvp的插件
 而是用python写个工具，可以根据后台接口实现的代码
 来自动生产android的代码，自动把后台的模型，mvp逻辑的代码，自动生成，然后拷贝项目即可，
 而不是用最原始的方法，先定义mvp结构，在定义VO和DTO，在写请求代码，写请求的回掉
 全部自动生产，全部自动生产，全部自动生产，重要的事说3此，
 由于每个后台的接口实现不一样，同样也是内部东西，就不提供l


--------------------------------------------1.1.0--------------------------------------------

1.rxjava1升级rxjava2
2.nohttp正常升级
3.命名修改
4.请求对话框改成即接口方式，
5.利用枚举管理回调得状态，适合页面加载视图又多种状态得情况

--------------------------------------------1.0.0--------------------------------------------
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
