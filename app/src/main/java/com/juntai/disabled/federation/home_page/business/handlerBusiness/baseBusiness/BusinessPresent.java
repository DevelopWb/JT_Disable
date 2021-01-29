package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;


import android.widget.ArrayAdapter;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.bdmap.utils.MapUtil;
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
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.bean.business.MyBusinessDetailBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;

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
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HUKOU);
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
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_RELATION);
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
     * 脑残儿童恢复
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
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_IDCARD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DISABLE_CARD_ID);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_GUARDIAN_NAME);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_RELATION_TO_CHILD);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_SELECT, BusinessContract.TABLE_TITLE_CHILD_IQ);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HOME_ADDR2);
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_PHONE);
        initRecycleviewType(arrays, getOtherDisabilities(), BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY, 0, 0,false);
        initRecycleviewType(arrays, getFamilyEcomanicStatus(), BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS, 1,
                0,true);
        initRecycleviewType(arrays, getPoorFamilyResion(), BusinessContract.TABLE_TITLE_POOR_FAMILY, 1,
                2,false);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_IS_POOR_FAMILY, 1, new String[]{"是", "否"});
        initRecycleviewType(arrays, getMedicalSafes(), BusinessContract.TABLE_TITLE_MEDICALSAFE, 2,
                2,true);
        initRadioType(arrays, BusinessContract.TABLE_TITLE_HUKOU, 0, new String[]{"农业户口", "非农业户口"});
        initTextType(arrays, MultipleItem.ITEM_BUSINESS_EDIT, BusinessContract.TABLE_TITLE_HEALTH_AGENCY);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST));
        initEditHighType(arrays, BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST);
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SIGN,new ItemSignBean("申请人",null,1)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NOTICE, "说明：本表由县（区）残联组织填写。受助儿童监护人提出申请时，需携带本人和患儿户口本或身份证或居住证原件及复印件，持患儿残疾"));

        return arrays;
    }

    private void initRecycleviewType(List<MultipleItem> arrays, List<ItemCheckBoxBean> data, String typeName,
                                     int layoutType, int spanCount,boolean isSigleSelect) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL,
                typeName));
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW,
                new RecycleBean(layoutType, spanCount, data,typeName,isSigleSelect)));
    }

    /**
     * 获取其他残疾类型
     *
     * @return
     */
    private List<ItemCheckBoxBean> getOtherDisabilities() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("视力",false));
        arrays.add(new ItemCheckBoxBean("听力",false));
        arrays.add(new ItemCheckBoxBean("肢体",false));
        arrays.add(new ItemCheckBoxBean("言语",false));
        arrays.add(new ItemCheckBoxBean("精神",false));
        return arrays;
    }

    /**
     * 获取贫困家庭
     *
     * @return
     */
    private List<ItemCheckBoxBean> getPoorFamilyResion() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("双胞胎患儿",false));
        arrays.add(new ItemCheckBoxBean("一户多残",false));
        arrays.add(new ItemCheckBoxBean("单亲家庭",false));
        arrays.add(new ItemCheckBoxBean("无业职工家庭",false));
        arrays.add(new ItemCheckBoxBean("其他困难",false));
        return arrays;
    }

    /**
     * 享受医疗保险的情况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getMedicalSafes() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("享受城乡居民基本医疗",false));
        arrays.add(new ItemCheckBoxBean("享受医疗救助",false));
        arrays.add(new ItemCheckBoxBean("享受其他保险",false));
        arrays.add(new ItemCheckBoxBean("无医疗保险",false));
        return arrays;
    }

    /**
     * 获取家庭经济状况
     *
     * @return
     */
    private List<ItemCheckBoxBean> getFamilyEcomanicStatus() {
        List<ItemCheckBoxBean> arrays = new ArrayList<>();
        arrays.add(new ItemCheckBoxBean("家庭人均收入低于当地城乡居民最低生活保障线",false));
        arrays.add(new ItemCheckBoxBean("当地政府有关部门认定的低收入家庭",false));
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
        arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, typeName));
        if (MultipleItem.ITEM_BUSINESS_SELECT == layoutType) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_SELECT,
                    new BusinessTextValueBean(typeName, null, String.format("%s%s", "请选择",
                            typeName), 0)));
        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_EDIT,
                    new BusinessTextValueBean(typeName, null,
                            String.format("%s%s", "请输入", typeName), 0)));
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
