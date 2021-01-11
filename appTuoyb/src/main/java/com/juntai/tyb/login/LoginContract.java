package com.juntai.tyb.login;


import com.juntai.disabled.basecomponent.mvp.BaseIView;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/9 14:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 14:08
 */
public interface LoginContract {

    interface ILoginView extends BaseIView {
    }

    interface ILoginPresent {
        /**
         * 登录
         * @param username
         * @param password
         * @param tag
         */
        void login(String username, String password,String tag);
    }
}
