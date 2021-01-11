package com.juntai.disabled.federation.entrance.regist;


import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.PoliceBranchBean;
import com.juntai.disabled.federation.bean.PolicePositionBean;
import com.juntai.disabled.federation.bean.weather.PoliceGriddingBean;
import com.juntai.disabled.federation.entrance.sendcode.ISendCode;
import com.juntai.disabled.federation.entrance.sendcode.SendCodeModel;
import com.juntai.disabled.federation.utils.RxScheduler;

import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/3/5 15:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:55
 */
public class RegistPresent extends BasePresenter<IModel, RegistContract.IRegistView> implements RegistContract.IRegistPresent, ISendCode.IUpdateView {

    private SendCodeModel sendCodeModel;
    private BaseIView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void setCallBack(BaseIView iView) {
        this.iView = iView;
    }

    public RegistPresent() {
        sendCodeModel = new SendCodeModel(this);
    }


    @Override
    public void sendCheckCode(String mobile,String tempCode) {
        if (checkMobile(mobile)) {
            getCheckCodeFromNet(mobile,tempCode);
        }
    }

    /**
     * 初始化获取验证码
     */
    public void initGetTestCodeButtonStatus() {
        sendCodeModel.initGetTestCodeButtonStatus();
    }

    @Override
    public void receivedCheckCodeAndDispose() {
        sendCodeModel.receivedCheckCodeAndDispose();
    }

    @Override
    public void getPolicePosition(String tag) {//职务
        AppNetModule.createrRetrofit()
                .getPolicePosition(getBaseFormBodyBuilder().build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PolicePositionBean>(getView()) {
                    @Override
                    public void onSuccess(PolicePositionBean o) {
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
    public void getPoliceBranch(String keyWord, String tag) {
        AppNetModule.createrRetrofit()
                .getPoliceBranch(getBaseFormBodyBuilder().add("keyWord", keyWord).build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PoliceBranchBean>(getView()) {
                    @Override
                    public void onSuccess(PoliceBranchBean o) {
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
    public void getPoliceGridding(int departmentId, String tag) {
        BaseIView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        BaseIView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getPoliceGridding(getBaseFormBodyBuilder().add("departmentId", departmentId+"").build())
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<PoliceGriddingBean>(getView()) {
                    @Override
                    public void onSuccess(PoliceGriddingBean o) {
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

    @Override
    public void regist(String tag, RequestBody body) {
        AppNetModule.createrRetrofit()
                .regist(body)
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
    public void setPwd(String tag, String account, String password) {
        AppNetModule.createrRetrofit()
                .setPwd(account, password)
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
    public void updateAccount(String tag, String newAccount, String password, String oldPassword) {
        AppNetModule.createrRetrofit()
                .updatePhone(MyApp.getAccount(), MyApp.getUserToken(),
                        MyApp.getUid(), newAccount, password, oldPassword)
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
    public void addUserInfo(String tag, RequestBody body) {
        AppNetModule.createrRetrofit()
                .addUserInfo(body)
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


    /**
     * 从网络获取验证码
     *
     * @param mobile
     */
    public void getCheckCodeFromNet(String mobile,String tempCode) {
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode(tempCode,"+86", mobile);
    }

    @Override
    public void startTiming(long value) {
        if (getView() != null) {
            getView().updateSendCheckCodeViewStatus(value);
        }
    }

    @Override
    public void endTiming(long value) {
        if (getView() != null) {
            getView().updateSendCheckCodeViewStatus(value);
        }
    }

    @Override
    public void checkFormatError(String error) {
        if (getView() != null) {
            getView().checkFormatError(error);
        }
    }

    /**
     * 检查手机号的格式
     */
    public boolean checkMobile(String mobile) {
        return sendCodeModel.checkMobile(mobile);
    }

    public FormBody.Builder getBaseFormBodyBuilder() {
        return new FormBody.Builder()
                .add("account", MyApp.getAccount())
                .add("token", MyApp.getUserToken())
                .add("userId", MyApp.getUid()+"");
    }
}
