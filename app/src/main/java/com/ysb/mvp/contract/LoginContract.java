package com.ysb.mvp.contract;



import com.ysb.base.BaseView;
import com.ysb.bean.PuBuLiuModel;

import io.reactivex.Observable;

/**
 * Created by Jacky on 2017/2/4.
 */

public class LoginContract {


    public interface View extends BaseView {
        /**
         * 登陆
         *
         * @param result
         */
        void onLogin1(PuBuLiuModel result);

        /**
         * 注册
         *
         * @param result
         */
        void onRegister2(PuBuLiuModel result);

        /**
         * 获取验证
         *
         * @param result
         */
        void onVerification3(PuBuLiuModel result);
    }

    public interface Presenter {
        /**
         * 登陆
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        void getLogin1(String account, String pass);

        /**
         * 注册
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        void getRegister2(String account, String pass);

        /**
         * 验证码
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        void getVerification3(String account, String pass);
    }

    public interface Model {
        /**
         * 登陆
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        Observable<PuBuLiuModel> getLogin1(String account, String pass);

        /**
         * 注册
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        Observable<PuBuLiuModel> getRegister2(String account, String pass);

        /**
         * 验证码
         *
         * @param account 账号
         * @param pass    密码
         * @return
         */
        Observable<PuBuLiuModel> getVerification3(String account, String pass);
    }


}
