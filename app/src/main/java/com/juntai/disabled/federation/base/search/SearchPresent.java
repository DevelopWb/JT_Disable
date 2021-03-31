package com.juntai.disabled.federation.base.search;


import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.bean.collect.CollectSearchResultBean;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/9 15:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 15:01
 */
public class SearchPresent extends BasePresenter<IModel, SearchContract.BaseISearchView> implements SearchContract.ISearchPresent {

    @Override
    public void getStreets(String tag) {
//        AppNetModule
//                .createrRetrofit()
//                .getStreets()
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<StreetBean>(getView()) {
//                    @Override
//                    public void onSuccess(StreetBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void getAllYears(String tag) {
//        AppNetModule
//                .createrRetrofit()
//                .getAllYears()
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<YearsBean>(getView()) {
//                    @Override
//                    public void onSuccess(YearsBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void searchAllCareTakers(RequestBody body, String tag) {
//        AppNetModule
//                .createrRetrofit()
//                .searchCareTaker(body)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<SearchedPeopleBean>(getView()) {
//                    @Override
//                    public void onSuccess(SearchedPeopleBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void searchAllDisabledPeoples(RequestBody body, String tag) {
//        AppNetModule
//                .createrRetrofit()
//                .searchDisabledPeoples(body)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<SearchedPeopleBean>(getView()) {
//                    @Override
//                    public void onSuccess(SearchedPeopleBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void collectDisabledSearch(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .collectDisabledSearch(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectSearchResultBean>(getView()) {
                    @Override
                    public void onSuccess(CollectSearchResultBean o) {
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
    protected IModel createModel() {
        return null;
    }
}
