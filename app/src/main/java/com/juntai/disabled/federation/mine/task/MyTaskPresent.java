package com.juntai.disabled.federation.mine.task;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.task.TaskDetailBean;
import com.juntai.disabled.federation.bean.task.TaskListBean;
import com.juntai.disabled.federation.bean.task.TaskSubmitedBean;
import com.juntai.disabled.federation.mine.MyCenterContract;
import com.juntai.disabled.federation.utils.RxScheduler;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class MyTaskPresent extends BasePresenter<IModel, MyCenterContract.ITaskView> implements MyCenterContract.ITaskPresent {
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getMyTaskList(int pageNum, int pageSize, String keyWord, String typeId, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserTask(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize, keyWord, typeId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<TaskListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(TaskListBean o) {
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
    public void getTaskInfo(int missionId, int taskPeopleId, String tag) {
        IView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;

        AppNetModule.createrRetrofit()
                .getTaskInfo(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), missionId, taskPeopleId)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<TaskDetailBean>(viewCallBack) {
                    @Override
                    public void onSuccess(TaskDetailBean o) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getTaskSubmitedDetail(int reportId, String tag) {
        AppNetModule.createrRetrofit()
                .getTaskSubmitDetail(MyApp.getAccount(), MyApp.getUserToken(),reportId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<TaskSubmitedBean>(getView()) {
                    @Override
                    public void onSuccess(TaskSubmitedBean o) {
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
