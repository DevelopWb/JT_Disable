package com.juntai.disabled.federation.mine.mypublish;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.PublishListBean;
import com.juntai.disabled.federation.mine.MyCenterContract;
import com.juntai.disabled.federation.utils.RxScheduler;

import java.util.List;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-3-12
 * email:954101549@qq.com
 */
public class MyPublishListPresent extends BasePresenter<IModel,MyCenterContract.IMyPublishListView>
        implements MyCenterContract.IMyPublishListPresent {

    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getPublishCaseList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserPublishCase(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PublishListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(PublishListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getPublishInspectionList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserPublishInspection(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PublishListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(PublishListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getPublishSiteList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserPublishSite(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PublishListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(PublishListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getPublishNewsList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserPublishNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PublishListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(PublishListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deletePublishCase(List<Integer> ids,String tag) {
        AppNetModule.createrRetrofit()
                .deletePublishCase(MyApp.getAccount(), MyApp.getUserToken(), ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deletePublishInspection(List<Integer> ids, String tag) {
        AppNetModule.createrRetrofit()
                .deletePublishInspection(MyApp.getAccount(), MyApp.getUserToken(), ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deletePublishSite(List<Integer> ids, String tag) {
        AppNetModule.createrRetrofit()
                .deletePublishSite(MyApp.getAccount(), MyApp.getUserToken(), ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deletePublishNews(List<Integer> ids, String tag) {
        AppNetModule.createrRetrofit()
                .deletePublishNews(MyApp.getAccount(), MyApp.getUserToken(), ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }
}
