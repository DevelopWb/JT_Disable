package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;


import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
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
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.bean.business.MyBusinessDetailBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.utils.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class BusinessPresent extends BasePresenter<IModel, BusinessContract.IBusinessView> implements BusinessContract.IBusinessPresent {
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
    public void deleteUserBusiness(List<Integer> ids,String tag) {
        AppNetModule.createrRetrofit()
                .deleteUserBusiness(UserInfoManager.getUserAccount(),UserInfoManager.getUserToken(),ids)
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
    public void getDisabledAIDS(String tag) {
        AppNetModule.createrRetrofit()
                .getDisabledAIDS()
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
    public void addOpinionsAndSuggestions(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .addOpinionsAndSuggestions(requestBody)
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
     * 获取业务列表
     *
     * @return
     */
    public List<MyMenuBean> getBusinessList() {
        List<MyMenuBean> menus = new ArrayList<>();
        String[] names = {"残疾证办理", "残疾证换领", "残疾证等级变更", "残疾证补办", "残疾证迁入", "残疾证迁出", "残疾证注销", "残疾人就业 登记", "残疾人儿童抢 救性康复项目",
                "残疾学生助学金", "家庭无障碍改 造项目", "残疾人辅助用品 用具申请",};
        int[] images = {R.mipmap.business_hukou_by_present, R.mipmap.business_die_unregist, R.mipmap.business_join,
                R.mipmap.business_house_move, R.mipmap.business_hukou_by_house, R.mipmap.business_hukou_out_by_prove,
                R.mipmap.business_hukou_out_by_school, R.mipmap.business_change_name, R.mipmap.business_change_idcard
                , R.mipmap.business_idcard_iterim, R.mipmap.business_receive_live_card, R.mipmap.business_more};
        for (int i = 0; i < 12; i++) {
            menus.add(new MyMenuBean(names[i], i, images[i]));
        }
        return menus;
    }

    /**
     * 获取业主基本信息
     *
     * @param businessName 业务名称
     * @return
     */
    public List<TextListBean> getUserBaseInfo(String businessName) {
        List<TextListBean> arrays = new ArrayList<>();
        arrays.add(new TextListBean("事项名称", businessName));
        arrays.add(new TextListBean("姓名", MyApp.getUser().getData().getRealName()));
        arrays.add(new TextListBean("身份证号", MyApp.getUser().getData().getIdNumber()));
        arrays.add(new TextListBean("手机号码", MyApp.getUser().getData().getAccount()));
        return arrays;
    }

    /**
     * 残疾证办理数据
     *
     * @return
     */
    public List<MultipleItem> getHandlerIdCardAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, 0, new String[]{"农业户口", "非农业户口"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_RELATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER_TYPE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_UNIT_NATURE);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_WEEL_COMPANY, 1, null);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_CARD_TYPE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申办残疾人证承诺书"));
        return arrays;
    }

    /**
     * 期满换证 登记变更 遗失补办 迁入迁出 注销等业务
     *
     * @return
     */
    public List<MultipleItem> getBaseChildAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACT_MODE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_MATERIAL_PIC, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, 3, "")));
        return arrays;
    }

    /**
     * 培训申请
     *
     * @return
     */
    public List<MultipleItem> getTrainingRequestAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_HUKOU_ADDR));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0)));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACTER);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CURRENT_LIVE_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_SPECIALTY);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_JOB_STATUS);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, "希望参加何种培训"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_TRAIN_TYPE, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_TRAIN_TYPE), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME_WORK));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME_WORK);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME_TRAIN));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME_TRAIN);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, 1, "")));


        return arrays;
    }
    /**
     * 居家托养
     *
     * @return
     */
    public List<MultipleItem> getHomeCareAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "监护人家庭情况"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_AGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_JOB);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_RELATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1, new String[]{"低保家庭", "建档立卡贫困家庭", "其他困难"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, "家庭地址:河东区"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0)));

        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "残疾人概况"));

        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_AGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, "家庭地址:河东区"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_STREET, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_STREET), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_VILLAGE, null,
                        String.format("%s%s", "请输入", BusinessContract.TABLE_TITLE_VILLAGE), 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PHOTO, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GUARDIAN_ID_PIC, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_HUKOU_RELATION_PIC, 3, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF, 4, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人签字", null, 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE, "注：此表一式两份，区残联、居家托养服务机构各执一份"));
        return arrays;
    }

    /**
     * 辅助用品
     *
     * @return
     */
    public List<MultipleItem> getAssistToolAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ASSIST_TOOL_AMOUNT);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("领取人签字", null, 0)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "备注：严格按照样表填写，器具类别数量填写一栏，数量用汉字写（壹）并且器具前后画杠，一张表只填一种器具，填写不规范一概不发放"));

        return arrays;
    }

    /**
     * 就业登记
     *
     * @return
     */
    public List<MultipleItem> getEmploymentRegistAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SPECIAL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_REG_MODE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR_LIVE_NOW);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_DISABILITY_HEAR, 1, null);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_DISABILITY_LIMB, 1, new String[]{"上肢残疾", "下肢残疾"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_RESUME));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_RESUME);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "求职意向"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WANTED_POST);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORK_AREA);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_SALARY);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, -1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE, "说明：求职登记需携带身份证、残疾人证原件及复印件一份"));
        return arrays;
    }


    /**
     * 残疾学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityStudentBursaryAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityStudentBursaryAdapterData(arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_ADMISSION_NOTICE_PIC, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 3, "")));
        return arrays;
    }

    /**
     * 残疾家庭学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityFamilyStudentBursaryAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityFamilyStudentBursaryAdapterData(arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_IDCARD, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_PRESENT_DISBILITY_IDCARD, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GROUP_PHOTO, 3, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_ACCOUNT_BOOK, 4, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_ADMISSION_NOTICE_PIC, 5, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 6, "")));
        return arrays;
    }

    /**
     * 残疾家庭学生助学金
     *
     * @return
     */
    public List<MultipleItem> getDisabilityFamilyStudentBursaryNextYearAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityFamilyStudentBursaryAdapterData(arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_GROUP_PHOTO, 2, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 3, "")));
        return arrays;
    }

    /**
     * 获取残疾大学生助学金
     *
     * @param arrays
     */
    private void getBaseDisabilityStudentBursaryAdapterData(List<MultipleItem> arrays) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_EMAIL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_FATHER_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MATHER_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDRESS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "受助学生银行卡资料（工商银行）"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_BANK);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CARD_NUM);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
    }

    /**
     * 获取残疾大学生助学金
     *
     * @param arrays
     */
    private void getBaseDisabilityFamilyStudentBursaryAdapterData(List<MultipleItem> arrays) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_STUDENT_RELATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_KINDS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_DISABILITY_LEVEL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_EMAIL);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_FATHER_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MATHER_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDRESS);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "受助学生银行卡资料（工商银行）"));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ACCOUNT_BANK);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CARD_NUM);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
    }

    /**
     * 残疾学生助学金2年以后申请
     *
     * @return
     */
    public List<MultipleItem> getDisabilityStudentBursaryNextYearAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        getBaseDisabilityStudentBursaryAdapterData(arrays);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC, 1, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_TUITION_PIC, 2, "")));
        return arrays;
    }

    /**
     * 智力残疾
     *
     * @return
     */
    public List<MultipleItem> getMoronRecoveryData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, null,
                        null, 0)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, 1, new String[]{"国家", "省级", "市级", "县级"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_CHILD_IQ);
        initRecycleviewType(arrays, getOtherDisabilities(), BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY, 0, 0,
                false);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(), BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true);
        initRecycleviewType(arrays, getPoorFamilyResion(), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, 1, new String[]{"是", "否"});
        initRecycleviewType(arrays, getMedicalSafes(), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, 0, new String[]{"农业户口", "非农业户口"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", null, 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    /**
     * 脑瘫儿童恢复
     *
     * @return
     */
    public List<MultipleItem> getBrainPalsyRecoveryData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, null,
                        null, 0)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, 1, new String[]{"国家", "省级", "市级", "县级"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE);
        initRecycleviewType(arrays, getOtherDisabilitiesOfBrainPalsy(),
                BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY, 0, 0,
                false);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(), BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true);
        initRecycleviewType(arrays, getPoorFamilyResion(), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, 1, new String[]{"是", "否"});
        initRecycleviewType(arrays, getMedicalSafes(), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, 0, new String[]{"农业户口", "非农业户口"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", null, 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));
        return arrays;
    }

    /**
     * 孤独症儿童康复
     *
     * @return
     */
    public List<MultipleItem> getLonelyChildRecoveryData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, null,
                        null, 0)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, 1, new String[]{"国家", "省级", "市级", "县级"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PRESENT_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACT_MODE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(), BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true);
        initRecycleviewType(arrays, getPoorFamilyResion(), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2, false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, 1, new String[]{"是", "否"});
        initRecycleviewType(arrays, getMedicalSafes(), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", null, 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    /**
     * 聋哑儿童康复
     *
     * @return
     */
    public List<MultipleItem> getDeafDumbChildRecoveryData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_YEAR,
                new BusinessTextValueBean(BusinessContract.TABLE_TITLE_YEAR, null,
                        null, 0)));
        initRadioType(arrays, BusinessContract.TABLE_TITLE_PROJECT_LEVEL, 1, new String[]{"国家", "省级", "市级", "县级"});
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", -1, "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0, new String[]{"男", "女"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CHILD_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_ID_CARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOUSE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_MOBILE_NUM);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_COMMUNICATION_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                BusinessContract.TABLE_TITLE_HEARING_LOSE_RECOVERY));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_DISCOVER_DISABILITY_YEAR);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_FAMILY_HAS_DISABILITY, 0, new String[]{"无", "有"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_DEAF_TABLE, new DeafBean(1)));
        initRecycleviewType(arrays, getRecoveryStatus(), BusinessContract.TABLE_TITLE_CURRENT_RECOVERY, 2,
                2, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HAS_CARE_WORKER, 0, new String[]{"无", "有"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT2, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(), BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0, true);
        initRecycleviewType(arrays, getMedicalSafes(), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2, true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, 0, new String[]{"农业户口", "非农业户口"});
        initRecycleviewType(arrays, getRequestsRecovery(), BusinessContract.TABLE_TITLE_REQUEST_RECOVERY, 1,
                2, true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,
                BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));


        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN, new ItemSignBean("申请人", null, 1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE,
                "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    private void initRecycleviewType(List<MultipleItem> arrays, List<ItemCheckBoxBean> data, String typeName,
                                     int layoutType, int spanCount, boolean isSigleSelect) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                typeName));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW,
                new RecycleBean(layoutType, spanCount, data, typeName, isSigleSelect)));
    }

    /**
     * 获取其他残疾类型
     *
     * @return
     */
    private List<ItemCheckBoxBean> getOtherDisabilities() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("视力", false));
        arrays.add(new ItemCheckBoxBean("听力", false));
        arrays.add(new ItemCheckBoxBean("肢体", false));
        arrays.add(new ItemCheckBoxBean("言语", false));
        arrays.add(new ItemCheckBoxBean("精神", false));
        return arrays;
    }

    /**
     * 获取其他残疾类型
     *
     * @return
     */
    private List<ItemCheckBoxBean> getOtherDisabilitiesOfBrainPalsy() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("视力", false));
        arrays.add(new ItemCheckBoxBean("智力", false));
        arrays.add(new ItemCheckBoxBean("听力", false));
        arrays.add(new ItemCheckBoxBean("言语", false));
        arrays.add(new ItemCheckBoxBean("精神", false));
        return arrays;
    }

    /**
     * 康复状态
     *
     * @return
     */
    private List<ItemCheckBoxBean> getRecoveryStatus() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("机构康复", false));
        arrays.add(new ItemCheckBoxBean("家庭康复", false));
        arrays.add(new ItemCheckBoxBean("未接受康复", false));
        return arrays;
    }

    /**
     * 获取贫困家庭
     *
     * @return
     */
    private List<ItemCheckBoxBean> getPoorFamilyResion() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("双胞胎患儿", false));
        arrays.add(new ItemCheckBoxBean("一户多残", false));
        arrays.add(new ItemCheckBoxBean("单亲家庭", false));
        arrays.add(new ItemCheckBoxBean("无业职工家庭", false));
        arrays.add(new ItemCheckBoxBean("其他困难", false));
        return arrays;
    }

    /**
     * 享受医疗保险的情况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getMedicalSafes() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("享受城乡居民基本医疗", false));
        arrays.add(new ItemCheckBoxBean("享受医疗救助", false));
        arrays.add(new ItemCheckBoxBean("享受其他保险", false));
        arrays.add(new ItemCheckBoxBean("无医疗保险", false));
        return arrays;
    }

    /**
     * 康复需求项目
     *
     * @return
     */
    private List<ItemCheckBoxBean> getRequestsRecovery() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("聋儿人工耳蜗植入及术后首次语训救助项目", false));
        arrays.add(new ItemCheckBoxBean("聋儿（助听器或耳蜗）语训项目", false));
        return arrays;
    }

    /**
     * 获取家庭经济状况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getFamilyEcomanicStatus() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("家庭人均收入低于当地城乡居民最低生活保障线", false));
        arrays.add(new ItemCheckBoxBean("当地政府有关部门认定的低收入家庭", false));
        return arrays;
    }


    /**
     * @param arrays
     * @param typeName
     */
    private void initRadioType(List<MultipleItem> arrays, String typeName, int defaultIndex, String[] values) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_RADIO, new BusinessRadioBean(typeName, defaultIndex,
                values)));
    }

    /**
     * initTextType
     *
     * @param arrays
     * @param typeName
     */
    private void initTextType(List<MultipleItem> arrays, int layoutType, String typeName) {
        switch (layoutType) {
            case MultipleItem.ITEM_BUSINESS_SELECT:
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SELECT,
                        new BusinessTextValueBean(typeName, null, String.format("%s%s", "请选择",
                                typeName), 0)));
                break;
            case MultipleItem.ITEM_BUSINESS_EDIT:
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                        new BusinessTextValueBean(typeName, null,
                                String.format("%s%s", "请输入", typeName), 0)));
                break;
            case MultipleItem.ITEM_BUSINESS_EDIT2:
                arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT2,
                        new BusinessTextValueBean(typeName, null,
                                String.format("%s%s", "请输入", typeName), 0)));
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
    private void initEditHighType(List<MultipleItem> arrays, String typeName) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                new BusinessTextValueBean(typeName, null,
                        "点击输入内容...", 1)));

    }
}
