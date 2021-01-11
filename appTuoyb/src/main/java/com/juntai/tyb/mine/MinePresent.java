package com.juntai.tyb.mine;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.tyb.AppNetModule;
import com.juntai.tyb.bean.MineMenuBean;
import com.juntai.tyb.bean.ServiceRecordBean;
import com.juntai.tyb.bean.UserBaseMsgBean;
import com.juntai.tyb.bean.mine.MyMsgBean;
import com.juntai.tyb.hcb.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/16 16:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/16 16:15
 */
public class MinePresent extends BasePresenter<IModel, MineContract.IMineView> implements MineContract.IMinePresent {
    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 获取我的 菜单
     * @return
     */
    public List<MineMenuBean> getMenus() {
        List<MineMenuBean> arrays = new ArrayList<>();
        arrays.add(new MineMenuBean(MineContract.MINE_SERVICE_RECORD, R.mipmap.mine_service_record, 0));
        arrays.add(new MineMenuBean(MineContract.MINE_MSG, R.mipmap.mine_msg, 0));
        arrays.add(new MineMenuBean(MineContract.MINE_MODIFY_PWD, R.mipmap.mine_modify_pwd, 0));
        arrays.add(new MineMenuBean(MineContract.MINE_CLEAR, R.mipmap.mine_clear, 0));
        arrays.add(new MineMenuBean(MineContract.MINE_UPDATE, R.mipmap.mine_update, 0));
        arrays.add(new MineMenuBean(MineContract.MINE_ABOUT_US, R.mipmap.mine_about, 0));

        return arrays;
    }

    @Override
    public void logout(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .logout(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getServiceRecord(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .serviceRecord(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ServiceRecordBean>(getView()) {
                    @Override
                    public void onSuccess(ServiceRecordBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getUserBaseInfo(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .persionalInfo(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBaseMsgBean>(getView()) {
                    @Override
                    public void onSuccess(UserBaseMsgBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void modifyHeadIcon(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .modifyHeadIcon(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void modifyPwd(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .modifyPwd(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void myNotice(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .myNotice(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MyMsgBean>(getView()) {
                    @Override
                    public void onSuccess(MyMsgBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void readMsg(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .msgIsRead(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag,o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag,msg);
                        }
                    }
                });
    }


}
