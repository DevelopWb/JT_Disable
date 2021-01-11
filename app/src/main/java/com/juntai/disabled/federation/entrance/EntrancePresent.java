package com.juntai.disabled.federation.entrance;

import android.annotation.SuppressLint;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.utils.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/3/5 15:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:55
 */
public class EntrancePresent extends BasePresenter<IModel, EntranceContract.IEntranceView> implements EntranceContract.IEntrancePresent {
    private BaseIView iView;
    public void  setCallBack(BaseIView iView) {
        this.iView = iView;
    }

    @Override
    protected IModel createModel() {
        return null;
    }


    @SuppressLint("CheckResult")
    @Override
    public void login(String account, String password, String weChatId, String qqId,String tag) {
        BaseIView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
                viewCallBack.showLoading();
            }
        }else{
            viewCallBack = getView();
            viewCallBack.showLoading();
        }
        BaseIView finalViewCallBack = viewCallBack;
        AppNetModule
                .createrRetrofit()
                .login(account, password, weChatId, qqId, 1)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean userBean) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onSuccess(tag, userBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onError(tag, "服务器开小差了");
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindQQOrWeChat(String account, String weChatId, String weChatName, String qqId, String qqName, String tag) {
        BaseIView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
                viewCallBack.showLoading();
            }
        }else{
            viewCallBack = getView();
            viewCallBack.showLoading();
        }
        BaseIView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .bindQQAndWeChat(account, 1, weChatId, weChatName, qqId, qqName)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<BaseResult>() {
                    @Override
                    public void accept(BaseResult baseResult) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onSuccess(tag, baseResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onError(tag, "服务器开小差了");
                        }
                    }
                });
    }
}
