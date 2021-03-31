package com.juntai.disabled.federation.home_page;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.inspection.InspectionDetailBean;
import com.juntai.disabled.federation.bean.key_personnel.InterviewDetailBean;
import com.juntai.disabled.federation.utils.RxScheduler;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-3-19
 * email:954101549@qq.com
 */
public class InfoDetailPresent extends BasePresenter<IModel, InfoDetailContract.BaseIInfoDetailView> implements InfoDetailContract.IInfoDetailPresent {
    private BaseIView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(BaseIView iView) {
        this.iView = iView;
    }

    @Override
    public void getInspection(int patrolId,String tag) {
        AppNetModule.createrRetrofit()
                .getInspectionDetail(MyApp.getAccount(), MyApp.getUserToken(),patrolId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InspectionDetailBean>(getView()) {
                    @Override
                    public void onSuccess(InspectionDetailBean o) {
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
    public void addShare(int isType, int typeId, int shareId, String tag) {
        AppNetModule.createrRetrofit()
                .addOrDeleteShares(MyApp.getAccount(), MyApp.getUserToken(),String.valueOf(MyApp.getUid()),isType,
                        String.valueOf(typeId), String.valueOf(shareId), null)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
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
    public void getInterviewDetail(int id, String tag) {
        AppNetModule.createrRetrofit()
                .getInterviewDetail(MyApp.getAccount(), MyApp.getUserToken(), id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InterviewDetailBean>(null) {
                    @Override
                    public void onSuccess(InterviewDetailBean o) {
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
