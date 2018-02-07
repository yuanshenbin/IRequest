package com.ysb.network;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Interceptor;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestHandler;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yanzhenjie.nohttp.tools.IOUtils;
import com.ysb.bean.Base;
import com.ysb.bean.param.LoginDTO;
import com.ysb.bean.result.BaseVO;
import com.ysb.bean.result.LoginVO;
import com.ysb.util.SSLContextUtil;

import java.io.IOException;

import javax.net.ssl.SSLContext;

/**
 * author : yuanshenbin
 * time   : 2018/1/12
 * desc   : token 拦截器
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public <T> Response<T> intercept(RequestHandler handler, Request<T> request) {

        String body = null;
        try {
            if (request.getDefineRequestBody() != null) {
                body = IOUtils.toString(request.getDefineRequestBody());
                request.setDefineRequestBodyForJson(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: 2018/2/7    下面responseTemp  我直接retrun，下面的逻辑只是告诉大家如果使用，如果换成自己项目业务后，就把responseTemp相关的干掉
        Response<T> responseTemp = handler.handle(request);
        if (true) {
            return responseTemp;
        }

        Response<T> response = handler.handle(request);


        if (response.isSucceed()) {

            Object o = response.get();
            Base base = null;
            if (o instanceof String) {
                base = new Gson().fromJson((String) o, Base.class);
            } else {
                base = (Base) o;
            }

            // TODO: 2018/2/7   自己定义token的枚举 来替换 true
            if (body != null && base.getCode() != null && true) {
                //拿到获取token的request
                LoginDTO loginDTO = new LoginDTO();

                Request<String> loginRequest = new StringRequest("填写自己获取token的地址", RequestMethod.POST);
                loginRequest.setDefineRequestBodyForJson(new Gson().toJson(loginDTO));

                SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
                if (sslContext != null) {
                    loginRequest.setSSLSocketFactory(sslContext.getSocketFactory());
                }
                Response<String> tokenResponse = handler.handle(loginRequest);
                if (tokenResponse.isSucceed()) {

                    String tokenJson = tokenResponse.get();


                    BaseVO<LoginVO> loginVO = new Gson().fromJson(tokenJson, new BaseVO<LoginVO>().getClass());

                    //条件都成功，则就给上一次的request的handle头的token重新赋值
                    if (loginVO.isSuccess() && loginVO != null && loginVO.getData() != null) {


                        // TODO: 2018/2/7   保存的token
                        // AppConfig.getInstance().putString(AppConfig.TOKEN, loginVO.getData().getToken());

                        request.setHeader("token", loginVO.getData().getToken());
                        request.setDefineRequestBodyForJson(body);
                        //然后再次请求上一次接口，如果上一次，出现其他异常，则不会在进入拦截器了
                        return handler.handle(request);


                    } else {
                        //如果token接口有null值，则就返回之前的response
                        return response;
                    }

                } else {
                    //如果token接口请求失败，则就返回之前的request
                    return response;
                }


            } else {
                //如果没token过期，则就正常请求
                return response;
            }
        } else {
            //如果基本的请求都失败了，则就直接返回当前的response
            return response;
        }
    }

}
