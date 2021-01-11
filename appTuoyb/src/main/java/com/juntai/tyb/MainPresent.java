package com.juntai.tyb;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.tyb.base.update.UpdatePresent;
import com.juntai.tyb.bean.mine.UnReadMsgBean;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/30 11:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/30 11:03
 */
public class MainPresent extends UpdatePresent{
    @Override
    protected IModel createModel() {
        return null;
    }

    public void unReadMsg(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .unReadMsgAmount(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UnReadMsgBean>(getView()) {
                    @Override
                    public void onSuccess(UnReadMsgBean o) {
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
