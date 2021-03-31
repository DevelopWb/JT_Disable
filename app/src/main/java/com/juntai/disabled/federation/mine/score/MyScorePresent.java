package com.juntai.disabled.federation.mine.score;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.UserScoreListBean;
import com.juntai.disabled.federation.mine.MyCenterContract;
import com.juntai.disabled.federation.utils.RxScheduler;

/**
 * Describe:我的积分p
 * Create by zhangzhenlong
 * 2020/3/10
 * email:954101549@qq.com
 */
public class MyScorePresent extends BasePresenter<IModel, MyCenterContract.BaseIMyScoreView> implements MyCenterContract.IMyScorePresent {
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getScoreDetail(String type, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserScore(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),type,pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserScoreListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(UserScoreListBean o) {
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
