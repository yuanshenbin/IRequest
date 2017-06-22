package com.yuanshenbin.mvp.model;

import com.yuanshenbin.bean.PuBuLiuModel;
import com.yuanshenbin.mvp.contract.LoginContract;
import com.yuanshenbin.network.request.IRequestRx;

import rx.Observable;

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
                .execute(PuBuLiuModel.class);
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
                .execute(PuBuLiuModel.class);
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
                .execute(PuBuLiuModel.class);
    }
}
