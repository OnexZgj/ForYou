package com.it.onex.foryou.activity.login;

import com.it.onex.foryou.base.BaseContract;

/**
 * Created by Linsa on 2018/8/20:11:23.
 * des:
 */

public interface LoginActivityContract {


    interface View extends BaseContract.BaseView{
        /** 登录成功 */
        void  showLoginSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        /**
         * 登录用户
         * @param account 用户名
         * @param password 密码
         */
        void Login(String account, String password);

    }



}
