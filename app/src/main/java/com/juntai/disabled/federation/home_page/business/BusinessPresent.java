package com.juntai.disabled.federation.home_page.business;


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
import com.juntai.disabled.federation.bean.business.BusinessListBean;
import com.juntai.disabled.federation.bean.business.BusinessNeedInfoBean;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.bean.business.MyBusinessDetailBean;

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
        String[] names = {"出生随父母落户", "死亡注销", "参军入伍", "住址变动所内移居", "购房落户", "持准迁证户口迁出", "升学户口迁出", "变更姓氏并改名字", "换领身份证",
                "临时身份证", "申领居住证", "更多",};
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
                new BusinessPicBean("", "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_SEX, 0);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_BIRTH);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_NATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_MARRIAGE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_EDUCATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOMETOWN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ZIP_CODE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HUKOU);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_RELATION);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_WORKER_TYPE);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_UNIT_NATURE);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_WEEL_COMPANY, 1);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_CARD_TYPE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申办残疾人证承诺书"));
        return arrays;
    }

    /**
     * 期满换证数据
     *
     * @return
     */
    public List<MultipleItem> getRenewalAdapterData() {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "申请人基本信息"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, BusinessContract.TABLE_TITLE_PIC));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_HEAD_PIC,
                new BusinessPicBean("", "")));
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_ADDR);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_CONTACT_MODE);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, "上传资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_DISABLE_PIC, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_MATERIAL_PIC, "")));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_PIC,
                new BusinessPicBean(BusinessContract.TABLE_TITLE_LIFE_PIC, "")));
        return arrays;
    }

    /**
     * @param arrays
     * @param typeName
     */
    private void initRadioType(List<MultipleItem> arrays, String typeName, int defaultIndex) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_RADIO, new BusinessRadioBean(typeName, defaultIndex)));
    }

    /**
     * initTextType
     *
     * @param arrays
     * @param typeName
     */
    private void initTextType(List<MultipleItem> arrays, int layoutType, String typeName) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
        if (MultipleItem.ITEM_BUSINESS_SELECT == layoutType) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SELECT,
                    new BusinessTextValueBean(typeName, null, String.format("%s%s", "请选择你的",
                            typeName))));
        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                    new BusinessTextValueBean(typeName, null,
                            String.format("%s%s", "请输入你的", typeName))));
        }

    }
}
