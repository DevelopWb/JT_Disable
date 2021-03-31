package com.juntai.disabled.federation.home_page.collectInfos;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.bean.collect.CollectDisabledDetailBean;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/3/17 16:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/17 16:47
 */
public class CollectInfoPresent extends BasePresenter<IModel, CollectInfoContract.TakeInfoViewBase> implements  CollectInfoContract.ITakeInfoPresent{
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getCollectDisabledDetail(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .getCollectDisabledDetail(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectDisabledDetailBean>(getView()) {
                    @Override
                    public void onSuccess(CollectDisabledDetailBean o) {
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
    @Override
    public void insertEvent(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .insertEvent(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
    @Override
    public void insertCase2(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .insertCase2(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
