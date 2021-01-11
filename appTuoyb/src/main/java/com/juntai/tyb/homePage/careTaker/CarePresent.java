package com.juntai.tyb.homePage.careTaker;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.tyb.AppNetModule;
import com.juntai.tyb.bean.careTaker.CareRecordDetailBean;
import com.juntai.tyb.bean.careTaker.CareTakerBaseInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoMoreBean;
import com.juntai.tyb.bean.careTaker.CareTakerPicBean;
import com.juntai.tyb.bean.careTaker.ServicePeoplesBean;
import com.juntai.tyb.bean.careTaker.ServiceTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/21 16:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/21 16:05
 */

public class CarePresent extends BasePresenter<IModel, CareContract.ICareView> implements CareContract.ICarePresent {

    private Disposable disposable;


    @Override

    protected IModel createModel() {
        return null;
    }

    /**
     * 获取图片相关的数据
     *
     * @return
     */
    public List<CareTakerPicBean> getPics() {
        List<CareTakerPicBean> arrays = new ArrayList<>();
        arrays.add(new CareTakerPicBean("房屋", "", true));
        arrays.add(new CareTakerPicBean("室内", "", true));
        arrays.add(new CareTakerPicBean("人员", "", true));
        arrays.add(new CareTakerPicBean("大门", "", true));
        arrays.add(new CareTakerPicBean("街道", "", true));
        arrays.add(new CareTakerPicBean("其他标志", "", true));
        return arrays;
    }

    /**
     * 获取图片相关的数据
     *
     * @return
     */
    public List<CareTakerPicBean> getRecordPics() {
        List<CareTakerPicBean> arrays = new ArrayList<>();
        arrays.add(new CareTakerPicBean("服务1", "", true));
        arrays.add(new CareTakerPicBean("服务2", "", true));
        arrays.add(new CareTakerPicBean("服务3", "", true));
        arrays.add(new CareTakerPicBean("服务4", "", false));
        arrays.add(new CareTakerPicBean("服务5", "", false));
        arrays.add(new CareTakerPicBean("服务6", "", false));
        return arrays;
    }

    @Override
    public void startTime(String tag) {
        if (disposable == null) {
            disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (getView() != null) {
                                getView().onSuccess(tag, "");
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                        }
                    });
        }

    }

    @Override
    public void endTime() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void getCareInfo(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .careInfo(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CareTakerInfoBean>(getView()) {
                    @Override
                    public void onSuccess(CareTakerInfoBean o) {
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
    public void getCareInfoMore(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .careInfoMore(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CareTakerInfoMoreBean>(getView()) {
                    @Override
                    public void onSuccess(CareTakerInfoMoreBean o) {
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
    public void getCareRecord(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .careRecord(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CareRecordDetailBean>(getView()) {
                    @Override
                    public void onSuccess(CareRecordDetailBean o) {
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
    public void careTakerBaseInfo(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .careTakerBaseInfo(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CareTakerBaseInfoBean>(getView()) {
                    @Override
                    public void onSuccess(CareTakerBaseInfoBean o) {
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
    public void addCareTaker(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .addCareTaker(requestBody)
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
    public void modifyCareTaker(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .modifyCareTaker(requestBody)
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
    public void getServicePeople(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .getServicePeople(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ServicePeoplesBean>(getView()) {
                    @Override
                    public void onSuccess(ServicePeoplesBean o) {
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
    public void commitCareRecord(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .commitCareRecord(requestBody)
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
    public void getServiceType(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .getServiceType(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ServiceTypeBean>(getView()) {
                    @Override
                    public void onSuccess(ServiceTypeBean o) {
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
