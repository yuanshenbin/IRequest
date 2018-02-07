package com.ysb.mvp.model;

import com.ysb.bean.PuBuLiuModel;
import com.ysb.mvp.contract.LoginContract;
import com.yuanshenbin.network.AdaptResponse;
import com.yuanshenbin.network.request.IRequestRx;

import io.reactivex.Observable;

/**
* Created by Jacky on 2017/03/17
*/

public class LoginModelImpl implements LoginContract.Model{
    /**
     * 登陆
     *
     * @param account 账号
     * @param pass    密码
     * @return
     */
    @Override
    public Observable<PuBuLiuModel> getLogin1(String account, String pass) {


        String mUrl = "http://image.baidu.com/channel/listjson?pn=" + 1
                + "&rn=" + 22
                + "&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8";
        return IRequestRx.get(mUrl)
                .execute(new AdaptResponse<PuBuLiuModel>() {
                });
    }

    /**
     * 注册
     *
     * @param account 账号
     * @param pass    密码
     * @return
     */
    @Override
    public Observable<PuBuLiuModel> getRegister2(String account, String pass) {
        String mUrl = "http://image.baidu.com/channel/listjson?pn=" + 1
                + "&rn=" + 22
                + "&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8";

        return IRequestRx.get(mUrl)
                .execute(new AdaptResponse<PuBuLiuModel>() {
                });
    }

    /**
     * 验证码
     *
     * @param account 账号
     * @param pass    密码
     * @return
     */
    @Override
    public Observable<PuBuLiuModel> getVerification3(String account, String pass) {
        String mUrl = "http://image.baidu.com/channel/listjson?pn=" + 1
                + "&rn=" + 22
                + "&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8";
        return IRequestRx.get(mUrl)
                .execute(new AdaptResponse<PuBuLiuModel>() {
                });
    }
}
