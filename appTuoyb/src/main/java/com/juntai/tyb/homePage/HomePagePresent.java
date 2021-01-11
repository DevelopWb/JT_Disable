package com.juntai.tyb.homePage;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.tyb.AppNetModule;
import com.juntai.tyb.bean.CityBean;
import com.juntai.tyb.bean.careTaker.YearsBean;
import com.juntai.tyb.bean.healthOrg.HealthOrgPositionBean;
import com.juntai.tyb.bean.HomePageMenuBean;
import com.juntai.tyb.bean.ServicePeoplePositionBean;
import com.juntai.tyb.bean.SearchResultBean;
import com.juntai.tyb.bean.careTaker.CareRecordPositionBean;
import com.juntai.tyb.bean.healthOrg.HealthOrganizeDetailBean;
import com.juntai.tyb.bean.stream.StreamCameraBean;
import com.juntai.tyb.bean.weather.ResponseForcastWeather;
import com.juntai.tyb.bean.weather.ResponseRealTimeWeather;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/21 16:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/21 16:05
 */

public class HomePagePresent extends BasePresenter<IModel, HomePageContract.IHomePageView> implements HomePageContract.IHomePagePresent {


    @Override
    public void getAllStreamCameras(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getAllStreamCameras(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraBean>(getView()) {
                    @Override
                    public void onSuccess(StreamCameraBean o) {
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
    public void getAllYears(String tag) {
        AppNetModule
                .createrRetrofit()
                .getAllYears()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<YearsBean>(getView()) {
                    @Override
                    public void onSuccess(YearsBean o) {
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
    public void search(RequestBody body, String tag) {
        AppNetModule
                .createrRetrofit()
                .search(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SearchResultBean>(getView()) {
                    @Override
                    public void onSuccess(SearchResultBean o) {
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
    public void getCareRecordPosition(RequestBody body, String tag) {

        AppNetModule
                .createrRetrofit()
                .careRecordPosition(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CareRecordPositionBean>(getView()) {
                    @Override
                    public void onSuccess(CareRecordPositionBean o) {
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

    public void openStream(String channelid, String type, String videourltype, String tag) {
        AppNetModule.createrRetrofit()
                .openStream(channelid, type, videourltype)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OpenLiveBean>(getView()) {
                    @Override
                    public void onSuccess(OpenLiveBean o) {
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

    /**
     * 获取菜单
     *
     * @return
     */
    protected List<HomePageMenuBean> getMenus() {

        List<HomePageMenuBean> arrays = new ArrayList<>();
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_MAP_TYPE, R.drawable.home_menu_map));
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_WEATHER, R.drawable.home_menu_weather));
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_CAMERA, R.drawable.home_menu_camera));
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_CARE_TAKER, R.drawable.home_menu_care_taker));
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_SERVICE_PEOPLE, R.drawable.home_menu_service_people));
        arrays.add(new HomePageMenuBean(HomePageContract.MENUE_HEALTH_ORGNAIZE, R.drawable.home_menu_health_organize));
        return arrays;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", UserInfoManager.getUserAccount())
                .addFormDataPart("userId", String.valueOf(UserInfoManager.getUserId()))
                .addFormDataPart("token", UserInfoManager.getUserToken());
    }

    @Override
    public void getAllStreamCamerasFromVCR(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getAllStreamCamerasFromVCR(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraBean>(getView()) {
                    @Override
                    public void onSuccess(StreamCameraBean o) {
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
    public void getWeatherRealTime(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit()
                .getWeatherRealtime(lng, lat)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseRealTimeWeather>(getView()) {
            @Override
            public void onSuccess(ResponseRealTimeWeather o) {
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
    public void getForcastWeather(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit().getForcast(lng, lat).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<ResponseForcastWeather>(getView()) {
            @Override
            public void onSuccess(ResponseForcastWeather o) {
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
    public void getPrivince(String tag) {
        AppNetModule.createrRetrofit().getProvince().compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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
    public void getCitys(String tag, int privinceNum) {
        AppNetModule.createrRetrofit().getCity(privinceNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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
    public void getTowns(String tag, int cityNum) {
        AppNetModule.createrRetrofit().getArea(cityNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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
    public void getStreets(String tag, int townNum) {
        AppNetModule.createrRetrofit()
                .getStreet(townNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CityBean>(getView()) {
                    @Override
                    public void onSuccess(CityBean o) {
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
    public void getServicePeoplesPosition(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getServicePeoplesPosition(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ServicePeoplePositionBean>(getView()) {
                    @Override
                    public void onSuccess(ServicePeoplePositionBean o) {
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
    public void getHealthOrganizePosition(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getHealthOrganizePosition(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HealthOrgPositionBean>(getView()) {
                    @Override
                    public void onSuccess(HealthOrgPositionBean o) {
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
    public void getHealthOrganizeDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getHealthOrganizeDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HealthOrganizeDetailBean>(getView()) {
                    @Override
                    public void onSuccess(HealthOrganizeDetailBean o) {
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
