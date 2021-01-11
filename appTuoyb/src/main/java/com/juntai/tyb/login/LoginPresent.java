package com.juntai.tyb.login;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.tyb.AppNetModule;
import com.juntai.tyb.bean.LoginBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/9 14:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 14:13
 */
public class LoginPresent  extends BasePresenter<IModel,LoginContract.ILoginView> implements LoginContract.ILoginPresent {
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void login(String username, String password, String tag) {
        AppNetModule
                .createrRetrofit()
                .login(username,password)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LoginBean>(getView()) {
                    @Override
                    public void onSuccess(LoginBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
}
