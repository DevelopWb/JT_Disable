package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;


import android.Manifest;
import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.MyMenuBean;
import com.juntai.disabled.federation.bean.TextListBean;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;
import com.juntai.disabled.federation.bean.business.BusinessListBean;
import com.juntai.disabled.federation.bean.business.BusinessNeedInfoBean;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessPropertyBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.ChildBusinessesBean;
import com.juntai.disabled.federation.bean.business.DeafBean;
import com.juntai.disabled.federation.bean.business.DisabledBaseInfoBean;
import com.juntai.disabled.federation.bean.business.ImportantTagBean;
import com.juntai.disabled.federation.bean.business.ToolInfoBean;
import com.juntai.disabled.federation.bean.business.detail.AssistToolDetailBean;
import com.juntai.disabled.federation.bean.business.detail.BusinessChildDetailBean;
import com.juntai.disabled.federation.bean.business.detail.StudentBursaryDetailBean;
import com.juntai.disabled.federation.bean.business.detail.EmploymentRegDetailBean;
import com.juntai.disabled.federation.bean.business.detail.HandlerCardDetailBean;
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.bean.business.MyBusinessDetailBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.bean.business.detail.HomeCareDetailBean;
import com.juntai.disabled.federation.bean.business.detail.RecoveryDetailBean;
import com.juntai.disabled.federation.bean.business.detail.TrainRequestDetailBean;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;
import com.juntai.disabled.video.record.VideoPreviewActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class BusinessPresent extends BasePresenter<IModel, BusinessContract.IBusinessView> implements BusinessContract.IBusinessPresent {

    public String FAMILY_TAG = "F";
    public String PERSIONAL_TAG = "P";

    @Override
    protected IModel createModel() {
        return null;
    }


    @Override
    public void businessList(String account, String token, String keyWord, int pageSize, int currentPage, String tag) {
        AppNetModule.createrRetrofit()
                .businessList(account, token, keyWord, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessListBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessListBean o) {
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
    public void businessDataNeeded(int declareId, String tag) {
        AppNetModule.createrRetrofit()
                .businessDataNeeded(MyApp.getAccount(), MyApp.getUserToken(), declareId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessNeedInfoBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessNeedInfoBean o) {
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
    public void creatBusiness(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .creatBusiness(requestBody)
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
    public void businessDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .businessDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MyBusinessDetailBean>(getView()) {
                    @Override
                    public void onSuccess(MyBusinessDetailBean o) {
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
    public void businessProgress(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .businessProgress(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MyBusinessBean>(getView()) {
                    @Override
                    public void onSuccess(MyBusinessBean o) {
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
    public void deleteUserBusiness(List<Integer> ids, String tag) {
        AppNetModule.createrRetrofit()
                .deleteUserBusiness(UserInfoManager.getUserAccount(), UserInfoManager.getUserToken(), ids)
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
    public void getAllBusinesses(String tag) {
        AppNetModule.createrRetrofit()
                .getAllBusinesses()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<AllBusinessBean>(getView()) {
                    @Override
                    public void onSuccess(AllBusinessBean o) {
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
    public void getChildBusinesses(int matterId, String tag) {
        AppNetModule.createrRetrofit()
                .getChildBusinesses(matterId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ChildBusinessesBean>(getView()) {
                    @Override
                    public void onSuccess(ChildBusinessesBean o) {
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
    public void getDisabledCategory(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledCategory()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getDisabledLevel(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledLevel()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getDisabledEducation(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledEducation()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getDisabledNation(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledNation()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getDisabledAIDS(int categoryId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledAIDS(categoryId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getDisabledBarrier(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledBarrier()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void getTrainingIntention(String tag) {
        AppNetModule.createrRetrofit()
                .getTrainingIntention()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessPropertyBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessPropertyBean o) {
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
    public void addDisabilityCertificate(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabilityCertificate(requestBody)
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
    public void addCertificatesExchange(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesExchange(requestBody)
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
    public void addCertificatesChange(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesChange(requestBody)
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
    public void addCertificatesReissue(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesReissue(requestBody)
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
    public void addCertificatesMovein(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesMovein(requestBody)
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
    public void addCertificatesMoveout(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesMoveout(requestBody)
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
    public void addCertificatesCancel(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addCertificatesCancel(requestBody)
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
    public void addDisabledObtainEmployment(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledObtainEmployment(requestBody)
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
    public void addDisabledChildrenCerebralPalsy(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledChildrenCerebralPalsy(requestBody)
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
    public void addDisabledChildrenDeaf(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledChildrenDeaf(requestBody)
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
    public void addDisabledChildrenAutism(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledChildrenAutism(requestBody)
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
    public void addDisabledChildrenIntellectual(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledChildrenIntellectual(requestBody)
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
    public void addDisabledStudentGrant(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledStudentGrant(requestBody)
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
    public void addDisabledStudentFamilyGrant(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addDisabledStudentFamilyGrant(requestBody)
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
    public void addAIDS(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addAIDS(requestBody)
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
    public void getDisabledAIDSInfo(int aidsId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledAIDSInfo(aidsId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ToolInfoBean>(getView()) {
                    @Override
                    public void onSuccess(ToolInfoBean o) {
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
    public void addTrain(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addTrain(requestBody)
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
    public void addHomCare(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addHomCare(requestBody)
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
    public void getDisabilityCertificateInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabilityCertificateInfo(UserInfoManager.getUserAccount(), UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HandlerCardDetailBean>(getView()) {
                    @Override
                    public void onSuccess(HandlerCardDetailBean o) {
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
    public void getCertificatesExchangeInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesExchangeInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getCertificatesChangeInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesChangeInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getCertificatesReissueInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesReissueInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getCertificatesMoveinInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesMoveinInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getCertificatesMoveoutInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesMoveoutInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getCertificatesCancelInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getCertificatesCancelInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessChildDetailBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessChildDetailBean o) {
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
    public void getDisabledObtainEmploymentInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledObtainEmploymentInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<EmploymentRegDetailBean>(getView()) {
                    @Override
                    public void onSuccess(EmploymentRegDetailBean o) {
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
    public void getDisabledChildrenCerebralPalsyInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledChildrenCerebralPalsyInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<RecoveryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(RecoveryDetailBean o) {
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
    public void getDisabledChildrenDeafInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledChildrenDeafInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<RecoveryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(RecoveryDetailBean o) {
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
    public void getDisabledChildrenAutismInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledChildrenAutismInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<RecoveryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(RecoveryDetailBean o) {
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
    public void getDisabledChildrenIntellectualInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledChildrenIntellectualInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<RecoveryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(RecoveryDetailBean o) {
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
    public void getDisabledStudentFamilyGrantInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledStudentFamilyGrantInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StudentBursaryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(StudentBursaryDetailBean o) {
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
    public void getDisabledStudentGrantInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledStudentGrantInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StudentBursaryDetailBean>(getView()) {
                    @Override
                    public void onSuccess(StudentBursaryDetailBean o) {
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
    public void getAIDSInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getAIDSInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<AssistToolDetailBean>(getView()) {
                    @Override
                    public void onSuccess(AssistToolDetailBean o) {
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
    public void getTrainInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getTrainInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<TrainRequestDetailBean>(getView()) {
                    @Override
                    public void onSuccess(TrainRequestDetailBean o) {
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
    public void getHomCareInfo(int businessId, String tag) {
        AppNetModule.createrRetrofit()
                .getHomCareInfo(UserInfoManager.getUserAccount(),
                        UserInfoManager.getUserToken(),
                        String.valueOf(businessId))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HomeCareDetailBean>(getView()) {
                    @Override
                    public void onSuccess(HomeCareDetailBean o) {
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
    public void getDisabledBaseInfo(String idNo, String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledBaseInfo(idNo)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<DisabledBaseInfoBean>(getView()) {
                    @Override
                    public void onSuccess(DisabledBaseInfoBean o) {
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
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }


    /**
     * 残疾证办理数据
     *
     * @return
     */
    public List<MultipleItem> getHandlerIdCardAdapterData(HandlerCardDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ?
                "" : dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男", "女"}, false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION,
                dataBean == null ? ""
                        : dataBean.getNationName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE,
                dataBean == null ?
                        "" : dataBean.getMarriageName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL,
                dataBean == null ? "" : dataBean.getEducationName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN,
                dataBean == null ? ""
                        : dataBean.getNativePlace(), true);

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR, dataBean == null ? "" :
                dataBean.getAddress(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, dataBean == null ? 0 : dataBean.getAccountType() - 1,
                new String[]{
                        "农业户口", "非农业户口"}, false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN,
                dataBean == null ? ""
                        : dataBean.getGuardian(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_RELATION,
                dataBean == null ? "" : dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER, dataBean == null ?
                "" :
                dataBean.getWorkingUnit(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER_TYPE, dataBean == null
                ? "" : dataBean.getProfession(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_UNIT_NATURE, dataBean == null
                ? "" : dataBean.getUnitNature(), false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_WEEL_COMPANY, dataBean == null ? 1 :
                dataBean.getUnitWelfare(), null, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC_IDCARD, 1, dataBean == null
                        ? "" : dataBean.getIdPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, 2, dataBean == null ? "" :
                        dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_MATERIAL_PIC, 3, dataBean == null ? "" :
                        dataBean.getCasePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GUARDIAN_HUJI_PIC, 4, dataBean == null ? "" :
                        dataBean.getGuardianRegisterPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申办残疾人证承诺书"));
        return arrays;
    }

    /**
     * 期满换证 登记变更 遗失补办 迁入、迁出 注销等业务
     *
     * @return
     */
    public List<MultipleItem> getBaseChildAdapterData(BusinessChildDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR, dataBean == null ? "" :
                dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACT_MODE,
                dataBean == null ? "" : dataBean.getTelephone(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_ALL, 1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_MATERIAL_PIC, 2, dataBean == null ? "" :
                        dataBean.getCasePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, 3, dataBean == null ? "" :
                        dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人电子签名", dataBean == null ? "" :
                dataBean.getApplicantSign(), 1)));

        return arrays;
    }

    /**
     * 培训申请
     *
     * @return
     */
    public List<MultipleItem> getTrainingRequestAdapterData(TrainRequestDetailBean.DataBean dataBean) {
        String street = null;
        String village = null;
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" : dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(), new String[]{
                "男", "女"}, false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION,
                dataBean == null ? ""
                        : dataBean.getNationName(), false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_HUKOU_ADDR,
                        false)));
        if (dataBean != null) {
            String hukouAddr = dataBean.getResidenceAddress();
            if (StringTools.isStringValueOk(hukouAddr)) {
                if (hukouAddr.contains(",")) {
                    String[] addrs = hukouAddr.split(",");
                    street = addrs[0];
                    village = addrs[1];
                }
            }
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET, dataBean == null ? "" : street==null?
                        "暂无":street,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0, false)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE, dataBean == null ? "" : village==null
                        ?"暂无":village,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0, false)));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL,
                dataBean == null ? "" : dataBean.getEducationName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE,
                dataBean == null ?
                        "" : dataBean.getMarriageName(), false);

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" : dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACTER, dataBean == null ?
                "" : dataBean.getContacts(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CURRENT_LIVE_ADDR,
                dataBean == null ?
                        "" : dataBean.getResidentialAddress(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null ?
                        "" : dataBean.getCategoryName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null ?
                        "" : dataBean.getLevelName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SPECIALTY, dataBean == null ?
                "" : dataBean.getSpecialty(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_JOB_STATUS,
                dataBean == null ?
                        "" : dataBean.getJobSituationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_HOPE_TRAIN_TYPE,
                dataBean == null ?
                        "" : dataBean.getTrainsName(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME_WORK));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME_WORK, dataBean == null ?
                "" : dataBean.getWorkingResume(), false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME_TRAIN));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME_TRAIN, dataBean == null ?
                "" : dataBean.getTrainingResume(), false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料（申请人残疾证照片）"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }

        return arrays;
    }

    /**
     * 居家托养
     *
     * @return
     */
    public List<MultipleItem> getHomeCareAdapterData(HomeCareDetailBean.DataBean dataBean) {
        String fStreet = null;
        String pStreet = null;
        String fVillage = null;
        String pVillage = null;

        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "监护人家庭情况"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME_FAMILY, dataBean == null
                ? "" : dataBean.getGuardianName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX_FAMILY, dataBean == null
                ? 0 : dataBean.getGuardianSex(), new String[]{"男", "女"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_AGE_FAMILY, dataBean == null
                ? "" : String.valueOf(dataBean.getGuardianAge()), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_JOB, dataBean == null
                ? "" : dataBean.getProfession(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_RELATION
                , dataBean == null
                        ? "" : dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null
                ? "" : dataBean.getTelephone(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, dataBean == null
                ? 1 : dataBean.getFamilyEconomy() - 1, new String[]{"低保家庭", "建档立卡贫困家庭",
                "其他困难"}, true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean("家庭地址:河东区",
                true)));
        if (dataBean != null) {
            String fAddr = dataBean.getAddress();
            if (StringTools.isStringValueOk(fAddr)) {
                if (fAddr.contains(",")) {
                    fStreet = fAddr.split(",")[0];
                    fVillage = fAddr.split(",")[1];
                }
            }
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET_FAMILY, fStreet,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE_FAMILY, fVillage,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0, true)));

        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "残疾人概况"));

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME_PERSIONAL,
                dataBean == null
                        ? "" : dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX_PERSIONAL, dataBean == null
                ? 0 : dataBean.getSex(), new String[]{"男", "女"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_AGE_PERSIONAL,
                dataBean == null
                        ? "" : String.valueOf(dataBean.getAge()), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL,
                dataBean == null
                        ? "" : dataBean.getEducationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null
                        ? "" : dataBean.getCategoryName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null
                        ? "" : dataBean.getLevelName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null
                        ? "" : dataBean.getDisabilityCertificate(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean("家庭地址:河东区",
                true)));
        if (dataBean != null) {
            String pAddr = dataBean.getResidentialAddress();
            if (StringTools.isStringValueOk(pAddr)) {
                if (pAddr.contains(",")) {
                    pStreet = pAddr.split(",")[0];
                    pVillage = pAddr.split(",")[1];
                }
            }
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET_PERSIONAL, pStreet,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE_PERSIONAL, pVillage,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PHOTO_ALL, 1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GUARDIAN_ID_PIC, 2, dataBean == null
                        ? "" : dataBean.getGuardianIdPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_HUKOU_RELATION_PIC, 3, dataBean == null
                        ? "" : dataBean.getHouseholdRegisterPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF, 4, dataBean == null
                        ? "" : dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC_HOUSE, 5, dataBean == null
                        ? "" : dataBean.getHousePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人签字", dataBean == null
                ? "" : dataBean.getApplicantSign(), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE, "注：此表一式两份，区残联、居家托养服务机构各执一份"));
        return arrays;
    }

    /**
     * 辅助用品
     *
     * @return
     */
    public List<MultipleItem> getAssistToolAdapterData(AssistToolDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 :
                dataBean.getSex(), new String[]{"男", "女"}, true);
//        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH, dataBean == null ?
//                "" :
//                dataBean.getBirth(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null ? "" :
                        dataBean.getCategoryName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null ? "" :
                        dataBean.getLevelName(), true);

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2,
                dataBean == null ? "" :
                        dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" :
                        dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ASSIST_TOOL_AMOUNT,
                "1", true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DELIVERY_METHOD,
                dataBean == null ? "" :
                        dataBean.getDeliveryWayName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL,
                dataBean == null ? "" :
                        dataBean.getAidsName(), true);

        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("领取人签字", dataBean == null ? "" :
                dataBean.getApplicantSign(), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_ALL, 1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, 2, dataBean == null ? "" :
                        dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "备注：严格按照样表填写，器具类别数量填写一栏，数量用汉字写（壹）并且器具前后画杠，一张表只填一种器具，填写不规范一概不发放"));

        return arrays;
    }

    /**
     * 就业登记
     *
     * @return
     */
    public List<MultipleItem> getEmploymentRegistAdapterData(EmploymentRegDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(), new String[]{
                "男", "女"}, false);
//        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH, dataBean == null ?
//                "" :
//                dataBean.getBirth(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL,
                dataBean == null ? "" : dataBean.getEducationName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SPECIAL, dataBean == null
                ? ""
                : dataBean.getSpecialty(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE, dataBean == null
                ? "" : dataBean.getMarriageName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR, dataBean == null ?
                "" : dataBean.getResidenceAddress(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR_LIVE_NOW,
                dataBean == null ? "" : dataBean.getResidentialAddress(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null ? "" : dataBean.getCategoryName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null ? "" : dataBean.getLevelName(), false);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_DISABILITY_HEAR, dataBean == null ? 1 :
                        dataBean.getHearingDisability(),
                null, false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_DISABILITY_LIMB, dataBean == null ? 2 :
                        dataBean.getPhysicalDisability(),
                new String[]{"上肢残疾", "下肢残疾"}, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME, dataBean == null ? "" :
                dataBean.getMineResume(), false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "求职意向"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WANTED_POST,
                dataBean == null ? "" : dataBean.getPostIntention(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORK_AREA, dataBean == null ?
                "" : dataBean.getAreaIntention(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_TRAIN_INTENT));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_TRAIN_INTENT, dataBean == null ? "" :
                dataBean.getTrainingIntention(), false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_REMARK));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_REMARK, dataBean == null ? "" : dataBean.getRemark(),
                false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料（申请人残疾证照片）"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        return arrays;
    }


    /**
     * 残疾学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityStudentBursaryAdapterData(StudentBursaryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityStudentBursaryAdapterData(dataBean, arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PHOTO_ALL, 1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC_IDCARD, 2, dataBean == null
                        ? "" : dataBean.getIdPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 3, dataBean == null ? "" :
                        dataBean.getPayPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC, 4,
                        dataBean == null ? "" : dataBean.getStudentCertificatePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF, 5, dataBean == null
                        ? "" : dataBean.getLifePicture())));
        return arrays;
    }

    /**
     * 残疾家庭学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityFamilyStudentBursaryAdapterData(StudentBursaryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityFamilyStudentBursaryAdapterData(dataBean, arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_IDCARD, 1,
                        dataBean == null ? "" : dataBean.getStudentIdPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PRESENT_DISBILITY_IDCARD, 2, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GROUP_PHOTO, 3, dataBean == null ? "" :
                        dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_ACCOUNT_BOOK, 4,
                        dataBean == null ? "" : dataBean.getHouseholdRegisterPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_ADMISSION_NOTICE_PIC, 5, dataBean == null ? "" :
                        dataBean.getNoticePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 6, dataBean == null ? "" :
                        dataBean.getPayPicture())));
        return arrays;
    }

    /**
     * 残疾家庭学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityFamilyStudentBursaryNextYearAdapterData(StudentBursaryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityFamilyStudentBursaryAdapterData(dataBean, arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC, 1,
                        dataBean == null ? "" : dataBean.getStudentCertificatePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GROUP_PHOTO, 2, dataBean == null ? "" :
                        dataBean.getLifePicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 3, dataBean == null ? "" :
                        dataBean.getPayPicture())));
        return arrays;
    }

    /**
     * 获取残疾大学生助学金
     *
     * @param arrays
     */
    private void getBaseDisabilityStudentBursaryAdapterData(StudentBursaryDetailBean.DataBean dataBean,
                                                            List<MultipleItem> arrays) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男"
                        , "女"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION,
                dataBean == null ? ""
                        : dataBean.getNationName(), true);

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null ? "" : dataBean.getCategoryName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null ? "" : dataBean.getLevelName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE,
                dataBean == null ? "" : dataBean.getCollege(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL,
                dataBean == null ? "" : dataBean.getMajor(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION, dataBean == null
                ? "" : dataBean.getEducationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM,
                dataBean == null ? "" : dataBean.getSystem(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDRESS,
                dataBean == null ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WCHAT_PHONE,
                dataBean == null ? "" : dataBean.getWechatPhone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_ADMISSION_TIME,
                dataBean == null ? "" : dataBean.getStartSchoolTimeName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_TOWN_STREET,
                dataBean == null ? "" : dataBean.getTownStreet(), true);

        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "受助学生银行卡信息"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_NAME,
                dataBean == null ? "" : dataBean.getAccountName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_BANK,
                dataBean == null ? "" : dataBean.getBankName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CARD_NUM,
                dataBean == null ? "" : dataBean.getCardNumber(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
    }

    /**
     * 获取残疾大学生助学金
     *
     * @param arrays
     */
    private void getBaseDisabilityFamilyStudentBursaryAdapterData(StudentBursaryDetailBean.DataBean dataBean,
                                                                  List<MultipleItem> arrays) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, dataBean == null ? "" :
                dataBean.getName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(), new String[]{
                "男", "女"}, true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH,
        //        dataBean == null ?
        //                "" :
        //                dataBean.getBirth(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN,
        //        dataBean == null ?
        //                "" : dataBean.getNativePlace(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, dataBean == null ?
                "" :
                dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_NAME,
                dataBean == null ? "" : dataBean.getSeverelyDisabledName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT,
                BusinessContract.TABLE_TITLE_DISABILITY_STUDENT_RELATION, dataBean == null ? "" :
                        dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS,
                dataBean == null ? "" : dataBean.getCategoryName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL,
                dataBean == null ? "" : dataBean.getLevelName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE,
                dataBean == null ? "" : dataBean.getCollege(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL,
                dataBean == null ? "" : dataBean.getMajor(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION,
                dataBean == null ? "" : dataBean.getEducationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM,
                dataBean == null ? "" : dataBean.getSystem(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_FATHER_NAME,
        //                dataBean == null ? "" : dataBean.getFatherName(), true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MATHER_NAME,
        //                dataBean == null ? "" : dataBean.getMotherName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDRESS,
                dataBean == null ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WCHAT_PHONE,
                dataBean == null ? "" : dataBean.getWechatPhone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_ADMISSION_TIME,
                dataBean == null ? "" : dataBean.getStartSchoolTimeName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_TOWN_STREET,
                dataBean == null ? "" : dataBean.getTownStreet(), true);

        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "受助学生银行卡信息"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_NAME,
                dataBean == null ? "" : dataBean.getAccountName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_BANK,
                dataBean == null ? "" : dataBean.getBankName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CARD_NUM,
                dataBean == null ? "" : dataBean.getCardNumber(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
    }

    /**
     * 残疾学生助学金2年以后申请
     *
     * @return
     */
    public List<MultipleItem> getDisabilityStudentBursaryNextYearAdapterData(StudentBursaryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityStudentBursaryAdapterData(dataBean, arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PHOTO_ALL, 1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificatePicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK, -1, dataBean == null ? "" :
                        dataBean.getDisabilityCertificateBackPicture())));
        if (dataBean == null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                    new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE, -1,
                            BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC_IDCARD, 2, dataBean == null
                        ? "" : dataBean.getIdPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 3, dataBean == null ? "" :
                        dataBean.getPayPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF, 4, dataBean == null
                        ? "" : dataBean.getLifePicture())));
        return arrays;
    }

    /**
     * 智力残疾
     *
     * @return
     */
    public List<MultipleItem> getMoronRecoveryData(RecoveryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, dataBean == null ? "" : dataBean.getYear(),
                        null, 0, true)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, dataBean == null ? -1 :
                        dataBean.getGrand() - 1,
                new String[]{"国家", "省级", "市级", "县级"}, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME, dataBean == null
                ? "" : dataBean.getName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男"
                        , "女"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD,
                dataBean == null ? "" : dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY,
                dataBean == null ? "" : dataBean.getDiagnoseAgency(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT,
                dataBean == null ? "" : dataBean.getDiagnoseResult(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME,
                dataBean == null ? "" : dataBean.getGuardian(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD,
                dataBean == null ? "" : dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2, dataBean == null
                ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" :
                dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_CHILD_IQ, dataBean == null
                ? "" : dataBean.getIqName(), true);


        initRecycleviewType(arrays, getOtherDisabilities(dataBean),
                BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY, 0, 0,
                false, true);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(dataBean),
                BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true, true);
        initRecycleviewType(arrays, getPoorFamilyResion(dataBean), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, dataBean == null ? 1 :
                        dataBean.getAlleviation()
                , new String[]{"是", "否"}, true);
        initRecycleviewType(arrays, getMedicalSafes(dataBean), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, dataBean == null ? 1 : dataBean.getAccountType() - 1,
                new String[]{
                        "农业户口", "非农业户口"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY,
                dataBean == null ? "" : dataBean.getRecoveryInstitution(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION, -1,
                        dataBean == null ? "" :
                                dataBean.getRiPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST, dataBean == null ? "" :
                dataBean.getGuardianApply(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", dataBean == null ? "" :
                dataBean.getApplicantSign(), 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }


    /**
     * 孤独症儿童康复
     *
     * @return
     */
    public List<MultipleItem> getLonelyChildRecoveryData(RecoveryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, dataBean == null ? "" : dataBean.getYear(),
                        null, 0, true)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, dataBean == null ? -1 :
                        dataBean.getGrand() - 1,
                new String[]{"国家", "省级", "市级", "县级"}, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME, dataBean == null
                ? "" : dataBean.getName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男", "女"}, true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH,
        //        dataBean == null ?
        //                "" : dataBean.getBirth(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD,
                dataBean == null ? "" : dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY,
                dataBean == null ? "" : dataBean.getDiagnoseAgency(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT,
                dataBean == null ? "" : dataBean.getDiagnoseResult(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PRESENT_NAME,
                dataBean == null ? "" : dataBean.getGuardian(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD,
                dataBean == null ? "" : dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2,
                dataBean == null ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACT_MODE,
                dataBean == null ? "" : dataBean.getTelephone(), true);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(dataBean),
                BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true, true);
        initRecycleviewType(arrays, getPoorFamilyResion(dataBean), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, 1, new String[]{"是", "否"}, true);
        initRecycleviewType(arrays, getMedicalSafes(dataBean), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY,
                dataBean == null ? "" : dataBean.getRecoveryInstitution(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION, -1,
                        dataBean == null ? "" :
                                dataBean.getRiPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST, dataBean == null ? "" :
                dataBean.getGuardianApply(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", dataBean == null ? "" :
                dataBean.getApplicantSign(), 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    /**
     * 聋儿童康复
     *
     * @return
     */
    public List<MultipleItem> getDeafDumbChildRecoveryData(RecoveryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, dataBean == null ? "" : dataBean.getYear(),
                        null, 0, true)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, dataBean == null ? -1 :
                        dataBean.getGrand() - 1,
                new String[]{"国家", "省级", "市级", "县级"}, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME, dataBean == null
                ? "" : dataBean.getName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男", "女"}, true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH,
        //        dataBean == null ?
        //                "" : dataBean.getBirth(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD,
                dataBean == null ? "" : dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME,
                dataBean == null ? "" : dataBean.getGuardian(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER,
                dataBean == null ? "" : dataBean.getWorkingUnit(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD,
                dataBean == null ? "" : dataBean.getRelationship(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_ID_CARD,
                dataBean == null ? "" : dataBean.getGuardianId(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOUSE_PHONE,
                dataBean == null ? "" : dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2,
                dataBean == null ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MOBILE_NUM,
                dataBean == null ? "" : dataBean.getPhone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_COMMUNICATION_ADDR,
                dataBean == null ? "" : dataBean.getPostalAddress(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_HEARING_LOSE_RECOVERY,
                        true)
        ));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_DISCOVER_DISABILITY_YEAR,
                dataBean == null ? "" : dataBean.getFindTime(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_FAMILY_HAS_DISABILITY, dataBean == null ? 0 :
                dataBean.getGeneticHistory(), new String[]{"无", "有"}, true);
        //有无家族遗传史
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_F,
                dataBean == null ? "" : dataBean.getGeneticHistoryRelationship(), true);
        DeafBean deafBean = new DeafBean();
        deafBean.setLeftEar(dataBean == null ? "" : dataBean.getLeftEar());
        deafBean.setRightEar(dataBean == null ? "" : dataBean.getRightEar());
        deafBean.setWear(dataBean == null ? 1 : dataBean.getWear());
        if (dataBean != null) {
            String wearTime = dataBean.getWearTime();
            if (StringTools.isStringValueOk(wearTime)) {
                String year = wearTime.substring(0, wearTime.indexOf("年"));
                String month = wearTime.substring(wearTime.indexOf("年") + 1, wearTime.indexOf("个"));
                deafBean.setWearTimeYear(year);
                deafBean.setWearTimeMonth(month);
            } else {
                deafBean.setWearTimeYear("");
                deafBean.setWearTimeMonth("");
            }
        } else {
            deafBean.setWearTimeYear("");
            deafBean.setWearTimeMonth("");
        }
        deafBean.setWearEar(dataBean == null ? 0 : dataBean.getWearEar());
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_DEAF_TABLE, deafBean));
        initRecycleviewType(arrays, getRecoveryStatus(dataBean), BusinessContract.TABLE_TITLE_CURRENT_RECOVERY, 2,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HAS_CARE_WORKER, dataBean == null ? 0 :
                dataBean.getAccompany(), new String[]{"无", "有"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_C,
                dataBean == null ? "" : dataBean.getAccompanyRelationship(), true);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(dataBean),
                BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true, true);
        initRecycleviewType(arrays, getMedicalSafes(dataBean), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, dataBean == null ? 0 : dataBean.getAccountType() - 1,
                new String[]{"农业户口",
                        "非农业户口"}, true);
        initRecycleviewType(arrays, getRequestsRecovery(dataBean), BusinessContract.TABLE_TITLE_REQUEST_RECOVERY, 1,
                2, true, true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION, -1,
                        dataBean == null ? "" :
                                dataBean.getRiPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));


        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST, dataBean == null ? "" :
                dataBean.getGuardianApply(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", dataBean == null ? "" :
                dataBean.getApplicantSign(), 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    /**
     * 脑瘫儿童恢复
     *
     * @return
     */
    public List<MultipleItem> getBrainPalsyRecoveryData(RecoveryDetailBean.DataBean dataBean) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, dataBean == null ? "" : dataBean.getYear(),
                        null, 0, true)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, dataBean == null ? -1 :
                        dataBean.getGrand() - 1,
                new String[]{"国家", "省级", "市级", "县级"}, false);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                new ImportantTagBean(BusinessContract.TABLE_TITLE_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PIC, -1,
                        dataBean == null ? "" : dataBean.getPhoto())));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME, dataBean == null
                ? "" : dataBean.getName(), true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, dataBean == null ? 0 : dataBean.getSex(),
                new String[]{"男", "女"}, true);
        //        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BIRTH,
        //        dataBean == null ?
        //                "" : dataBean.getBirth(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION, dataBean == null ?
                "" : dataBean.getNationName(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD,
                dataBean == null ? "" : dataBean.getIdNumber(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID,
                dataBean == null ? "" : dataBean.getDisabilityCertificate(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME,
                dataBean == null ? "" : dataBean.getGuardian(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER, dataBean == null ?
                "" : dataBean.getWorkingUnit(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2,
                dataBean == null ? "" : dataBean.getAddress(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE, dataBean == null ?
                "" : dataBean.getTelephone(), true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE,
                dataBean == null ?
                        "" : dataBean.getTypeName(), true);
        initRecycleviewType(arrays, getOtherDisabilitiesOfBrainPalsy(dataBean),
                BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY, 0, 0,
                false, true);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(dataBean),
                BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true, true);
        initRecycleviewType(arrays, getPoorFamilyResion(dataBean), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, dataBean == null ? 0 :
                        dataBean.getAlleviation(),
                new String[]{"是", "否"}, true);
        initRecycleviewType(arrays, getMedicalSafes(dataBean), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, dataBean == null ? 0 : dataBean.getAccountType() - 1,
                new String[]{"农业户口",
                        "非农业户口"}, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY,
                dataBean == null ? "" : dataBean.getRecoveryInstitution(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION, -1,
                        dataBean == null ? "" :
                                dataBean.getRiPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST, dataBean == null ? "" :
                dataBean.getGuardianApply(), true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", dataBean == null ? "" :
                dataBean.getApplicantSign(), 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));
        return arrays;
    }


    /**
     * 建议
     *
     * @return
     */
    public List<MultipleItem> getSuggestionAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME, null, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD, null, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MOBILE_NUM, null, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHECK_CODE, null, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_CONTENT_TYPE, 0,
                new String[]{"意见", "建议"}, true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_CONTENT));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_CONTENT, null, true);


        return arrays;
    }


    private void initRecycleviewType(List<MultipleItem> arrays, List<ItemCheckBoxBean> data, String typeName,
                                     int layoutType, int spanCount, boolean isSigleSelect, boolean isImportant) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(typeName,
                isImportant)
        ));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW,
                new RecycleBean(layoutType, spanCount, data, typeName, isSigleSelect, isImportant)));
    }

    /**
     * 获取其他残疾类型
     *
     * @return
     */
    private List<ItemCheckBoxBean> getOtherDisabilities(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "视力", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("视力")));
        arrays.add(new ItemCheckBoxBean(2, "听力", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("听力")));
        arrays.add(new ItemCheckBoxBean(3, "肢体", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("肢体")));
        arrays.add(new ItemCheckBoxBean(4, "言语", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("言语")));
        arrays.add(new ItemCheckBoxBean(5, "精神", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("精神")));
        return arrays;
    }

    /**
     * 获取其他残疾类型
     *
     * @return
     */
    private List<ItemCheckBoxBean> getOtherDisabilitiesOfBrainPalsy(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "视力", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("视力")));
        arrays.add(new ItemCheckBoxBean(2, "智力", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("智力")));
        arrays.add(new ItemCheckBoxBean(3, "听力", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("听力")));
        arrays.add(new ItemCheckBoxBean(4, "言语", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("言语")));
        arrays.add(new ItemCheckBoxBean(5, "精神", dataBean == null ? false :
                dataBean.getOtherDisabled().contains("精神")));
        return arrays;
    }

    /**
     * 康复状态
     *
     * @return
     */
    private List<ItemCheckBoxBean> getRecoveryStatus(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "机构康复", dataBean == null ? false : 1 == dataBean.getRecovery()));
        arrays.add(new ItemCheckBoxBean(2, "家庭康复", dataBean == null ? false : 2 == dataBean.getRecovery()));
        arrays.add(new ItemCheckBoxBean(3, "未接受康复", dataBean == null ? false : 3 == dataBean.getRecovery()));
        return arrays;
    }

    /**
     * 获取贫困家庭
     *
     * @return
     */
    private List<ItemCheckBoxBean> getPoorFamilyResion(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "双胞胎患儿", dataBean == null ? false : 1 == dataBean.getPoorFamily()));
        arrays.add(new ItemCheckBoxBean(2, "一户多残", dataBean == null ? false : 2 == dataBean.getPoorFamily()));
        arrays.add(new ItemCheckBoxBean(3, "单亲家庭", dataBean == null ? false : 3 == dataBean.getPoorFamily()));
        arrays.add(new ItemCheckBoxBean(4, "无业职工家庭", dataBean == null ? false : 4 == dataBean.getPoorFamily()));
        arrays.add(new ItemCheckBoxBean(5, "其他困难", dataBean == null ? "" : dataBean.getPoorFamilyExplain(),
                dataBean == null ? false :
                        5 == dataBean.getPoorFamily()));
        return arrays;
    }

    /**
     * 享受医疗保险的情况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getMedicalSafes(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "享受城乡居民基本医疗", dataBean == null ? false :
                1 == dataBean.getMedicalInsurance()));
        arrays.add(new ItemCheckBoxBean(2, "享受医疗救助", dataBean == null ? false : 2 == dataBean.getMedicalInsurance()));
        arrays.add(new ItemCheckBoxBean(3, "享受其他保险", dataBean == null ? false : 3 == dataBean.getMedicalInsurance()));
        arrays.add(new ItemCheckBoxBean(4, "无医疗保险", dataBean == null ? false : 4 == dataBean.getMedicalInsurance()));
        return arrays;
    }

    /**
     * 康复需求项目
     *
     * @return
     */
    private List<ItemCheckBoxBean> getRequestsRecovery(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "聋儿人工耳蜗植入及术后首次语训救助项目", dataBean == null ? false :
                1 == dataBean.getRecoveryProject()));
        arrays.add(new ItemCheckBoxBean(2, "聋儿（助听器或耳蜗）语训项目", dataBean == null ? false :
                2 == dataBean.getRecoveryProject()));
        return arrays;
    }

    /**
     * 获取家庭经济状况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getFamilyEcomanicStatus(RecoveryDetailBean.DataBean dataBean) {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean(1, "家庭人均收入低于当地城乡居民最低生活保障线", dataBean == null ? false :
                1 == dataBean.getFamilyEconomy()));
        arrays.add(new ItemCheckBoxBean(2, "当地政府有关部门认定的低收入家庭", dataBean == null ? false :
                2 == dataBean.getFamilyEconomy()));
        return arrays;
    }


    /**
     * @param arrays
     * @param typeName
     */
    private void initRadioType(List<MultipleItem> arrays, String typeName, int defaultIndex, String[] values,
                               boolean isImportant) {
        String titleName = null;
        if (typeName.contains("F") || typeName.contains("P")) {
            titleName = typeName.substring(1, typeName.length());
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(titleName,
                    isImportant)));
        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(typeName,
                    isImportant)));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_RADIO, new BusinessRadioBean(typeName, defaultIndex,
                values)));
    }


    /**
     * initTextType
     *
     * @param arrays
     * @param typeName
     */
    private void initTextType(List<MultipleItem> arrays, int layoutType, String typeName, String value,
                              boolean isImportant) {
        switch (layoutType) {
            case MultipleItem.ITEM_BUSINESS_SELECT:
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(typeName,
                        isImportant)));
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SELECT,
                        new BusinessTextValueBean(typeName, value, String.format("%s%s", "请选择",
                                typeName), 0, isImportant)));
                break;
            case MultipleItem.ITEM_BUSINESS_EDIT:
                String titleName = null;
                if (typeName.contains("F") || typeName.contains("P")) {
                    titleName = typeName.substring(1, typeName.length());
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(titleName,
                            isImportant)));
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                            new BusinessTextValueBean(typeName, value,
                                    String.format("%s%s", "请输入", titleName), 0, isImportant)));
                } else {
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, new ImportantTagBean(typeName,
                            isImportant)));
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                            new BusinessTextValueBean(typeName, value,
                                    String.format("%s%s", "请输入", typeName), 0, isImportant)));
                }

                break;
            case MultipleItem.ITEM_BUSINESS_EDIT2:
                if (typeName.contains("F") || typeName.contains("C")) {
                    titleName = typeName.substring(1, typeName.length());
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT2,
                            new BusinessTextValueBean(typeName, value,
                                    String.format("%s%s", "请输入", titleName), 0, isImportant)));
                } else {
                    arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT2,
                            new BusinessTextValueBean(typeName, value,
                                    String.format("%s%s", "请输入", typeName), 0, isImportant)));
                }

                break;
            default:
                break;
        }

    }

    /**
     * initTextType
     *
     * @param arrays
     * @param typeName
     */
    private void initEditHighType(List<MultipleItem> arrays, String typeName, String value, boolean isImportant) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(typeName, value,
                        "点击输入内容...", 1, isImportant)));

    }
}
