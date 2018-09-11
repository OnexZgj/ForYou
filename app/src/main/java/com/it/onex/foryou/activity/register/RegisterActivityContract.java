package com.it.onex.foryou.activity.register;

import com.it.onex.foryou.base.BaseContract;

/**
 * Created by Linsa on 2018/8/20:11:23.
 * des:
 */

public interface RegisterActivityContract {


    interface View extends BaseContract.BaseView{
        /** 注册成功后业务逻辑 */
        void  showRegisterSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        /**
         * 注册用户接口
         * @param account 账号
         * @param password 密码
         * @param rePassword 确认密码
         */
        void register(String account,String password,String rePassword);

    }



}
