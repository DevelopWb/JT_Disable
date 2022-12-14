package com.juntai.disabled.federation.mine;


import android.annotation.SuppressLint;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MyMenuBean;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.bean.message.UnReadCountBean;
import com.juntai.disabled.federation.home_page.business.my_business.MyBusinessActivity;
import com.juntai.disabled.federation.home_page.conciliation.conciliation_list.ConciliationListActivity;
import com.juntai.disabled.federation.mine.message.MyMessageActivity;
import com.juntai.disabled.federation.mine.mycollect.MyCollectActivity;
import com.juntai.disabled.federation.mine.mypublish.MyPublishListActivity;
import com.juntai.disabled.federation.mine.score.MyScoreActivity;
import com.juntai.disabled.federation.mine.setting.MySettingActivity;
import com.juntai.disabled.federation.mine.task.MyTaskListActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/7
 * email:954101549@qq.com
 */
public class MyCenterPresent extends BasePresenter<IModel, MyCenterContract.ICenterView> implements MyCenterContract.ICenterPresent {
    List<MyMenuBean> menuBeans = new ArrayList<>();
    private IView iView;

    public void setCallBack(IView iView) {
        this.iView = iView;
    }
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getUserData(String tag) {
        AppNetModule.createrRetrofit()
                .getUserData(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), 1)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(null) {
                    @Override
                    public void onSuccess(UserBean o) {
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
    public void getUnReadCount(String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getUnReadCount(getPublishMultipartBody().build())
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<UnReadCountBean>(null) {
                    @Override
                    public void onSuccess(UnReadCountBean o) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginOut(String tag) {
        LogUtil.e("longitude:"+ MyApp.app.getMyLocation().longitude + "latitude"+ MyApp.app.getMyLocation().latitude);
        AppNetModule.createrRetrofit()
                .loginOut(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),
                        String.valueOf(MyApp.app.getMyLocation().longitude),
                        String.valueOf(MyApp.app.getMyLocation().latitude),
                        1)
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
                            getView().onError(tag, "????????????");
                        }
                    }
                });
    }

    @Override
    public void initList() {
        menuBeans.clear();
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_favorite, MyCenterContract.CENTER_SHOUCANG_TAG, MyCollectActivity.class));
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_share, MyCenterContract.CENTER_SHARE_TAG, MyCollectActivity.class));
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_push, MyCenterContract.CENTER_FABU_TAG, MyPublishListActivity.class));
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_business, MyCenterContract.CENTER_BUSINESS_TAG,
                MyBusinessActivity.class));
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_message, MyCenterContract.CENTER_MESSAGE_TAG, MyMessageActivity.class));
        menuBeans.add(new MyMenuBean("????????????", -1, R.mipmap.my_set_list, MyCenterContract.CENTER_SETTING_TAG, MySettingActivity.class));
        getView().refreshAdapter();
    }

    @Override
    public List<MyMenuBean> getMenuBeans(){
        return menuBeans;
    }

    /**
     * ??????builder
     *
     * @return
     */
    public static MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }
}
