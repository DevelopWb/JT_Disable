package com.juntai.disabled.federation.base;


import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;

/**
 * Describe:首页present
 * Create by zhangzhenlong
 * 2020-8-8
 * email:954101549@qq.com
 */
public class MainPagePresent extends BasePresenter<IModel, MainPageContract.BaseIMainPageView> implements MainPageContract.IMainPagePresent {

    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void deleteNewsDrafts(String informationId, String tag) {
        AppNetModule.createrRetrofit()
                .deleteNewsPhoto(MyApp.getAccount(), MyApp.getUserToken(), informationId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            //                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    @Override
    public void uploadHistory(String data, String tag) {
        AppNetModule.createrRetrofit()
                .uploadHistory(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), 1, data)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            //                            getView().onError(tag, msg);
                        }
                    }
                });
    }
}
