package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.RuleTools;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.customview.GestureSignatureView;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessPropertyBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.DeafBean;
import com.juntai.disabled.federation.bean.business.DisabledBaseInfoBean;
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HeadCropActivity;
import com.juntai.disabled.federation.utils.DateUtil;
import com.juntai.disabled.federation.utils.StringTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: ????????????
 * @CreateDate: 2021/1/19 9:36
 * @UpdateUser: ?????????
 * @UpdateDate: 2021/1/19 9:36
 */
public abstract class BaseBusinessFragment extends BaseSelectPhotoFragment<BusinessPresent> implements BusinessContract.IBusinessView,
        View.OnClickListener, BaseBusinessActivity.OnPicSelectedCallBack {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerBusinessAdapter adapter;
    private GestureSignatureView gsv_signature;
    private BottomSheetDialog bottomSheetDialog;
    private String signPath;
    private DisabledBaseInfoBean.DataBean mDisabledInfo = null;

    private int currentPosition;
    private String birthDay = null;
    private BusinessPicBean businessPicBean;
    private ImageView mSignIv = null;
    private TextView mSelectTv;
    private int admissionTimeId = 1;//????????????
    private int educationId = 1;//??????
    private int selectedIQId = 1;//???1<=25???2<=26-39???3<=40-54???4<=55-75???
    private int selectedBrainId = 1;//???1<=25???2<=26-39???3<=40-54???4<=55-75???
    private int selectedMarrayStatus = 0;//0?????????1??????(?????????)???2?????????3??????
    private int selectedRegMode = 1;//1 ?????????  2?????????
    private int selectedNation = 0;//??????
    private int selectedEducationLevel = 0;//????????????
    private int categoryId = 0;//????????????
    private int levelId = 0;//????????????
    private BusinessTextValueBean selectBean;
    public static String BUSINESS_ID = "businessid";
    private ItemSignBean itemSignBean = null;

    public static final String STUDENT_BURSARY_FIRST = "studentfirst";
    public static final String STUDENT_BURSARY_SECOND = "studentsecond";
    public static final String FAMILY_STUDENT_BURSARY_FIRST = "familystudentfirst";
    public static final String FAMILY_STUDENT_BURSARY_SECOND = "familystudentsecond";


    protected abstract View getFootView();

    protected abstract View getHeadView();


    protected abstract List<MultipleItem> getAdapterData();


    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        mRecyclerview = (RecyclerView) getView(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) getView(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
        adapter = new HandlerBusinessAdapter(getAdapterData(), getBaseFragmentActivity().businessId == -1 ? false :
                true);
        adapter.setOnIdCardSearchCallBack(new BaseBusinessActivity.OnIdCardSearchCallBack() {
            @Override
            public void searchDisabledInfoByIdCard(String idNo) {
                //???????????????????????????
                mPresenter.getDisabledBaseInfo(idNo, AppHttpPath.GET_DISABLED_BASE_INFO);
            }
        });
        getBaseFragmentActivity().initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        if (getFootView() != null) {
            adapter.setFooterView(getFootView());
        }
        if (getHeadView() != null) {
            adapter.setHeaderView(getHeadView());
        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                MultipleItem multipleItem = (MultipleItem) adapter.getData().get(position);
                //                mHeadIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id
                //                .form_head_pic_iv);
                //                mFormPicIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position,
                //                        R.id.form_pic_src_iv);

                switch (view.getId()) {
                    case R.id.form_pic_src_iv:
                        if (BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE.equals(businessPicBean.getPicName())
                                ||BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE.equals(businessPicBean.getPicName())) {
                            //????????????????????????

                        }else {
                            choseImageFromFragment(0, BaseBusinessFragment.this, 1, SELECT_PIC_RESULT);
                        }

                        break;
                    case R.id.form_head_pic_iv:
                        choseImageFromFragment(0, BaseBusinessFragment.this, 1, SELECT_PIC_RESULT);
                        break;
                    case R.id.select_value_tv:
                        mSelectTv = (TextView) adapter.getViewByPosition(mRecyclerview, position,
                                R.id.select_value_tv);
                        selectBean = (BusinessTextValueBean) multipleItem.getObject();
                        switch (selectBean.getKey()) {
                            case BusinessContract.TABLE_TITLE_NATION:
                                mPresenter.getDisabledNation(BusinessContract.TABLE_TITLE_NATION);
                                break;
                            case BusinessContract.TABLE_TITLE_MARRIAGE:
                                List<String> marrayStatus = getBaseFragmentActivity().getMarrayStatus();
                                PickerManager.getInstance().showOptionPicker(mContext, marrayStatus,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                selectedMarrayStatus = options1;
                                                mSelectTv.setText(marrayStatus.get(options1));
                                                selectBean.setValue(marrayStatus.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                                //????????????
                                mPresenter.getDisabledEducation(BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_EDUCATION:
                                //??????
                                List<String> educations = getEducations();
                                PickerManager.getInstance().showOptionPicker(mContext, educations,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                educationId = options1 + 1;
                                                mSelectTv.setText(educations.get(options1));
                                                selectBean.setValue(educations.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                                //????????????
                                mPresenter.getDisabledCategory(AppHttpPath.GET_DISABLED_TYPE);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                                //????????????
                                mPresenter.getDisabledLevel(AppHttpPath.GET_DISABLED_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_ADMISSION_TIME:
                                //????????????
                                List<String> admissionTimes = getAdmissionTime();
                                PickerManager.getInstance().showOptionPicker(mContext, admissionTimes,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                admissionTimeId = options1 + 1;
                                                mSelectTv.setText(admissionTimes.get(options1));
                                                selectBean.setValue(admissionTimes.get(options1));
                                            }
                                        });

                                break;
                            case BusinessContract.TABLE_TITLE_CHILD_IQ:
                                //???????????????
                                List<String> iQs = getBaseFragmentActivity().getChildIQs();
                                PickerManager.getInstance().showOptionPicker(mContext, iQs,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                selectedIQId = options1 + 1;
                                                mSelectTv.setText(iQs.get(options1));
                                                selectBean.setValue(iQs.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE:
                                //????????????
                                List<String> brainBadTypes = getBaseFragmentActivity().getBrainBadTypes();
                                PickerManager.getInstance().showOptionPicker(mContext, brainBadTypes,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                selectedBrainId = options1 + 1;
                                                mSelectTv.setText(brainBadTypes.get(options1));
                                                selectBean.setValue(brainBadTypes.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_BIRTH:
                                //????????????
                                PickerManager.getInstance().showTimePickerView(mContext, null, "????????????",
                                        new PickerManager.OnTimePickerTimeSelectedListener() {
                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        birthDay = DateUtil.getDateString(date, "yyyy???MM???dd???");
                                        mSelectTv.setText(birthDay);
                                        selectBean.setValue(birthDay);
                                    }
                                });


                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.sign_name_iv:
                        itemSignBean = (ItemSignBean) multipleItem.getObject();
                        //??????
                        mSignIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id
                                .sign_name_iv);
                        getBaseFragmentActivity().showSignatureView();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * ?????????1?????????2??????????????????3?????????4?????????
     *
     * @return
     */
    protected List<String> getEducations() {
        List<String> arrays = new ArrayList<>();
        arrays.add("??????");
        arrays.add("???????????????");
        arrays.add("??????");
        arrays.add("??????");
        return arrays;
    }
    /**
     * ????????????
     *
     * @return
     */
    protected List<String> getAdmissionTime() {

        List<String> arrays = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            int year = 2015;
            year+=i;
            arrays.add(String.valueOf(year));
        }
        return arrays;
    }

    /**
     * ??????activity
     *
     * @return
     */
    protected BaseBusinessActivity getBaseFragmentActivity() {
        return (BaseBusinessActivity) getActivity();
    }

    @Override
    protected void initData() {
        getBaseFragmentActivity().setOnPicSelectedCallBack(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.removeAllFooterView();
        adapter.removeAllHeaderView();
        if (bottomSheetDialog != null) {
            if (bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }
            bottomSheetDialog = null;
        }
        //??????????????????
        FileCacheUtils.clearImage(FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME);
        FileCacheUtils.clearImage(FileCacheUtils.getAppImagePath() + FileCacheUtils.HEAD_PIC);
    }


    @Override
    public void picSelected(List<String> icons) {

    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            businessPicBean = (BusinessPicBean) ((MultipleItem) adapter.getData().get(currentPosition)).getObject();

            if (BusinessContract.TABLE_TITLE_PIC.equals(businessPicBean.getPicName())) {
                //??????????????????????????????
                startActivityForResult(new Intent(mContext, HeadCropActivity.class).putExtra(HeadCropActivity.HEAD_PIC,
                        path),BaseActivity.BASE_REQUEST_RESULT);
            }else {
                businessPicBean.setPicPath(path);
                adapter.notifyItemChanged(currentPosition);
            }

        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==BaseActivity.BASE_REQUEST_RESULT) {
            if (data != null) {
                String path = data.getStringExtra(HeadCropActivity.CROPED_HEAD_PIC);
                if (businessPicBean != null&&adapter!=null) {
                    businessPicBean.setPicPath(path);
                    adapter.notifyItemChanged(currentPosition);
                }

            }

        }

    }

    /**
     * ??????adapter????????????
     *
     * @return
     */
    protected MultipartBody.Builder getBuilderOfAdapterData() {
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        List<MultipleItem> arrays = adapter.getData();
        StringBuilder sb = new StringBuilder();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {

                case MultipleItem.ITEM_BUSINESS_HEAD_PIC:
                    BusinessPicBean headPicBean = (BusinessPicBean) array.getObject();
                    if (TextUtils.isEmpty(headPicBean.getPicPath())) {
                        ToastUtils.toast(mContext, "????????????????????????");
                        return null;
                    }
                    builder.addFormDataPart("photoFile", "photoFile", RequestBody.create(MediaType.parse("file"),
                            new File(headPicBean.getPicPath())));
                    break;
                case MultipleItem.ITEM_BUSINESS_EDIT:
                    BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                            .getObject();
                    if (textValueEditBean.isImportant() && TextUtils.isEmpty(textValueEditBean.getValue())) {
                        String key = textValueEditBean.getKey();
                        if (key.contains(mPresenter.FAMILY_TAG)) {
                            key = "?????????" + key.substring(1, key.length());
                        } else if (key.contains(mPresenter.PERSIONAL_TAG)) {
                            key = "?????????" + key.substring(1, key.length());
                        }
                        ToastUtils.toast(mContext, "?????????" + key);
                        return null;
                    }
                    String formKey = null;
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NAME:
                            //??????
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_NAME_FAMILY:
                            //???????????????  ?????????????????????
                            formKey = "guardianName";
                            break;
                        case BusinessContract.TABLE_TITLE_NAME_PERSIONAL:
                            //???????????????   ?????????????????????
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_NAME:
                            //??????
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_AGE_FAMILY:
                            //??????
                            formKey = "guardianAge";
                            break;
                        case BusinessContract.TABLE_TITLE_AGE_PERSIONAL:
                            //??????
                            formKey = "age";
                            break;
                        case BusinessContract.TABLE_TITLE_HOMETOWN:
                            //??????
                            formKey = "nativePlace";
                            break;
                        case BusinessContract.TABLE_TITLE_IDCARD:
                            //????????????
                            if (!RuleTools.isIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IDCARD:
                            //??????????????????
                            if (!RuleTools.isIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                            //????????????
                            if (!RuleTools.isDisabledIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "disabilityCertificate";
                            break;
                        case BusinessContract.TABLE_TITLE_ADDR:
                            //?????????
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDR2:
                            //????????????
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_PRESENT_NAME:
                            //????????????
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN:
                            //?????????
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_RELATION:
                            //??????????????????
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD:
                            //???????????????
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_PHONE:
                            //????????????
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACTER:
                            //?????????
                            formKey = "contacts";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACT_MODE:
                            //????????????
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CURRENT_LIVE_ADDR:
                            //???????????????
                            formKey = "residentialAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER:
                            //????????????
                            formKey = "workingUnit";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER_TYPE:
                            //????????????
                            formKey = "profession";
                            break;
                        case BusinessContract.TABLE_TITLE_UNIT_NATURE:
                            //????????????
                            formKey = "unitNature";
                            break;
                        case BusinessContract.TABLE_TITLE_SPECIAL:
                            //??????
                            formKey = "specialty";
                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDR:
                            //???????????????
                            formKey = "residenceAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_STREET:
                            //??????
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE:
                            //??????
                            break;
                        case BusinessContract.TABLE_TITLE_ADDR_LIVE_NOW:
                            //????????????
                            formKey = "residentialAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME:
                            //????????????
                            formKey = "mineResume";
                            break;
                        case BusinessContract.TABLE_TITLE_WANTED_POST:
                            //????????????
                            formKey = "postIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_WORK_AREA:
                            //????????????
                            formKey = "areaIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_TRAIN_INTENT:
                            //????????????
                            formKey = "trainingIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_REMARK:
                            //??????
                            formKey = "remark";
                            break;
                        case BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY:
                            //????????????
                            formKey = "diagnoseAgency";
                            break;
                        case BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT:
                            //????????????
                            formKey = "diagnoseResult";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_NAME:
                            //???????????????
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_HEALTH_AGENCY:
                            //?????????????????????????????????
                            formKey = "recoveryInstitution";
                            break;
                        case BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST:
                            //???????????????
                            formKey = "guardianApply";
                            break;
                        case BusinessContract.TABLE_TITLE_ASSIST_TOOL_AMOUNT:
                            //????????????
                            formKey = "quantity";
                            break;
                        //                        case BusinessContract.TABLE_TITLE_JOB_STATUS:
                        //                            //????????????
                        //                            formKey = "jobSituation";
                        //                            break;
                        case BusinessContract.TABLE_TITLE_JOB:
                            //??????
                            formKey = "profession";
                            break;
                        case BusinessContract.TABLE_TITLE_TRAIN_TYPE:
                            //????????????
                            formKey = "trains";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME_WORK:
                            //????????????
                            formKey = "workingResume";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME_TRAIN:
                            //????????????
                            formKey = "trainingResume";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_RELATION:
                            //??????????????????
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE:
                            //????????????
                            formKey = "college";
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL:
                            //????????????
                            formKey = "major";
                            break;
                        //                        case BusinessContract.TABLE_TITLE_FATHER_NAME:
                        //                            //email
                        //                            formKey = "fatherName";
                        //                            break;
                        //                        case BusinessContract.TABLE_TITLE_MATHER_NAME:
                        //                            //email
                        //                            formKey = "motherName";
                        //                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDRESS:
                            //email
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_ACCOUNT_NAME:
                            formKey = "accountName";
                            break;
                        case BusinessContract.TABLE_TITLE_ACCOUNT_BANK:
                            formKey = "bankName";
                            break;
                        case BusinessContract.TABLE_TITLE_CARD_NUM:
                            formKey = "cardNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_NAME:
                            formKey = "severelyDisabledName";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_STUDENT_RELATION:
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM:
                            //??????
                            formKey = "system";
                            break;
                        case BusinessContract.TABLE_TITLE_TOWN_STREET:
                            //??????
                            formKey = "townStreet";
                            break;
                        case BusinessContract.TABLE_TITLE_WCHAT_PHONE:
                            //???????????????
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "??????????????????????????????");
                                return null;
                            }
                            formKey = "wechatPhone";
                            break;
                        default:
                            break;
                    }
                    if (StringTools.isStringValueOk(textValueEditBean.getValue()) && formKey != null) {
                        builder.addFormDataPart(formKey, textValueEditBean.getValue());
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_RADIO:
                    BusinessRadioBean radioBean = (BusinessRadioBean) array.getObject();
                    switch (radioBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_SEX:
                            builder.addFormDataPart("sex", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_SEX_FAMILY:
                            builder.addFormDataPart("guardianSex", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_SEX_PERSIONAL:
                            builder.addFormDataPart("sex", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_HUKOU:
                            //????????????
                            builder.addFormDataPart("accountType", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_IS_WEEL_COMPANY:
                            builder.addFormDataPart("unitWelfare", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_HEAR:
                            builder.addFormDataPart("hearingDisability",
                                    String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS:
                            //??????????????????
                            builder.addFormDataPart("familyEconomy",
                                    String.valueOf(radioBean.getDefaultSelectedIndex() + 1));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_LIMB:
                            builder.addFormDataPart("physicalDisability",
                                    String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
//                        case BusinessContract.TABLE_TITLE_PROJECT_LEVEL:
//                            builder.addFormDataPart("grand", String.valueOf(radioBean.getDefaultSelectedIndex() + 1));
//                            break;
                        case BusinessContract.TABLE_TITLE_IS_POOR_FAMILY:
                            builder.addFormDataPart("alleviation", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        default:
                            break;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_SELECT:
                    BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) array.getObject();
                    if (textValueSelectBean.isImportant() && !StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                        ToastUtils.toast(mContext, "?????????" + textValueSelectBean.getKey());
                        return null;
                    }
                    switch (textValueSelectBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NATION:
                            //??????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("nation", String.valueOf(selectedNation));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MARRIAGE:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("marriage", String.valueOf(selectedMarrayStatus));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("education", String.valueOf(selectedEducationLevel));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_EDUCATION:
                            //??????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("education", String.valueOf(educationId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IQ:
                            //???????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("iq", String.valueOf(selectedIQId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_TIME:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("startSchoolTime ", String.valueOf(admissionTimeId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("type", String.valueOf(selectedBrainId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_BIRTH:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("birth", String.valueOf(birthDay));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("category", String.valueOf(categoryId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("level", String.valueOf(levelId));
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_PIC:
                    BusinessPicBean picBean = (BusinessPicBean) array.getObject();
                    String picKey = picBean.getPicName();
                    if (picKey != null) {
                        switch (picKey) {
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_ALL:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PHOTO_ALL:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("backPictureFile", "backPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_GUARDIAN_ID_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "?????????????????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("guardianIdPictureFile", "guardianIdPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_HUKOU_RELATION_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("householdRegisterPictureFile", "householdRegisterPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_MATERIAL_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "???????????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("casePictureFile", "casePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_STUDENT_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("studentIdPictureFile", "studentIdPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_GROUP_PHOTO:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????????????????");
                                    return null;
                                }
                                //?????????????????????????????????
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_PRESENT_DISBILITY_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????????????????");
                                    return null;
                                }
                                //???????????????????????????
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_ACCOUNT_BOOK:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????????????????????????????");
                                    return null;
                                }
                                //?????????????????????????????????????????????
                                builder.addFormDataPart("householdRegisterPictureFile", "householdRegisterPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "?????????????????????");
                                    return null;
                                }
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_ADMISSION_NOTICE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????");
                                    return null;
                                }
                                //?????????????????????
                                builder.addFormDataPart("noticePictureFile", "noticePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("studentCertificatePictureFile",
                                        "studentCertificatePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_TUITION_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "??????????????????????????????????????????");
                                    return null;
                                }
                                //?????????????????????????????????
                                builder.addFormDataPart("payPictureFile", "payPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "?????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_PIC_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("idPictureFile", "idPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            default:
                                break;
                        }
                    }
                    break;

                case MultipleItem.ITEM_BUSINESS_SIGN:
                    //??????
                    ItemSignBean signBean = (ItemSignBean) array.getObject();
                    if (!StringTools.isStringValueOk(signBean.getSignPicPath())) {
                        ToastUtils.toast(mContext, "?????????");
                        return null;
                    }
                    builder.addFormDataPart("applicantSignFile", "applicantSignFile",
                            RequestBody.create(MediaType.parse(
                                    "file"),
                                    new File(getBaseFragmentActivity().getSignPath(FileCacheUtils.SIGN_PIC_NAME))));
                    break;
                case MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW:
                    RecycleBean recycleBean = (RecycleBean) array.getObject();
                    List<ItemCheckBoxBean> data = recycleBean.getData();
                    String selectedData = getBaseFragmentActivity().getSelectedItems(data);
                    ItemCheckBoxBean selectedItem = getBaseFragmentActivity().getSelectedItem(data);
                    if (recycleBean.isImportant() && !StringTools.isStringValueOk(selectedData)) {
                        ToastUtils.toast(mContext, "?????????" + recycleBean.getTitleKey());
                        return null;
                    }
                    switch (recycleBean.getTitleKey()) {
                        case BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY:
                            //????????????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("otherDisabled", selectedData);
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS:
                            //??????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("familyEconomy", String.valueOf(selectedItem.getIndex() + 1));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_POOR_FAMILY:
                            //????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("poorFamily", String.valueOf(selectedItem.getIndex()));
                                if (5 == selectedItem.getIndex()) {
                                    //????????????  ????????????????????????
                                    builder.addFormDataPart("poorFamilyExplain", selectedItem.getDes());
                                }
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MEDICALSAFE:
                            //????????????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("medicalInsurance", String.valueOf(selectedItem.getIndex()));
                            }
                            break;
                        default:
                            break;
                    }


                    break;
                case MultipleItem.ITEM_BUSINESS_DEAF_TABLE:
                    DeafBean deafBean = (DeafBean) array.getObject();
                    sb.append(deafBean.toString());
                    break;
                case MultipleItem.ITEM_BUSINESS_YEAR:
                    BusinessTextValueBean yearBean = (BusinessTextValueBean) array.getObject();
                    if (TextUtils.isEmpty(yearBean.getValue())) {
                        ToastUtils.toast(mContext, "?????????" + yearBean.getKey());
                        return null;
                    }
                    builder.addFormDataPart("year", yearBean.getValue());
                    break;
                default:
                    break;
            }
        }
        return builder;
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onSuccess(String tag, Object o) {
        BusinessPropertyBean propertyNationBean = null;
        List<BusinessPropertyBean.DataBean> data = null;
        if (BusinessContract.TABLE_TITLE_NATION.equals(tag) || BusinessContract.TABLE_TITLE_EDUCATION_LEVEL.equals(tag)
                || AppHttpPath.GET_DISABLED_TYPE.equals(tag) || AppHttpPath.GET_DISABLED_LEVEL.equals(tag)) {
            propertyNationBean = (BusinessPropertyBean) o;
            data = propertyNationBean.getData();
            if (propertyNationBean != null && data.size() > 0) {
                List<BusinessPropertyBean.DataBean> finalData = data;
                PickerManager.getInstance().showOptionPicker(mContext, finalData,
                        new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                BusinessPropertyBean.DataBean dataBean = finalData.get(options1);
                                selectBean.setValue(dataBean.getName());
                                mSelectTv.setText(dataBean.getName());
                                switch (tag) {
                                    case BusinessContract.TABLE_TITLE_NATION:
                                        selectedNation = dataBean.getId();
                                        break;
                                    case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                                        selectedEducationLevel = dataBean.getId();
                                        break;
                                    case AppHttpPath.GET_DISABLED_TYPE:
                                        categoryId = dataBean.getId();
                                        break;
                                    case AppHttpPath.GET_DISABLED_LEVEL:
                                        levelId = dataBean.getId();
                                        break;
                                    default:
                                        break;
                                }

                            }
                        });
            }

        } else if (AppHttpPath.DISABLED_CHILD_BURSARY.equals(tag) || AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY.equals(tag)) {
            BaseResult baseResult = (BaseResult) o;
            ToastUtils.toast(mContext, baseResult.message);
            getBaseFragmentActivity().finish();

        }else if (AppHttpPath.GET_DISABLED_BASE_INFO.equals(tag)) {
            //?????????????????????
            DisabledBaseInfoBean disabledBaseInfoBean = (DisabledBaseInfoBean) o;
            if (disabledBaseInfoBean != null) {
                mDisabledInfo = disabledBaseInfoBean.getData();
                if (mDisabledInfo != null) {
                    //??????????????????
                    List<MultipleItem> arrays = adapter.getData();
                    for (MultipleItem array : arrays) {
                        switch (array.getItemType()) {

                            case MultipleItem.ITEM_BUSINESS_EDIT:
                                BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                                        .getObject();
                                switch (textValueEditBean.getKey()) {
                                    case BusinessContract.TABLE_TITLE_HOMETOWN:
                                        //??????
                                        textValueEditBean.setValue(mDisabledInfo.getPermanentAddress());
                                        break;
                                    case BusinessContract.TABLE_TITLE_HOME_ADDR:
                                        //???????????????
                                        textValueEditBean.setValue(mDisabledInfo.getPermanentAddress());
                                        break;
                                    case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                                        //????????????
                                        textValueEditBean.setValue(mDisabledInfo.getCertificateNo());
                                        break;

                                    default:
                                        break;
                                }

                                break;
                            case MultipleItem.ITEM_BUSINESS_RADIO:
                                BusinessRadioBean radioBean = (BusinessRadioBean) array.getObject();
                                switch (radioBean.getKey()) {
                                    case BusinessContract.TABLE_TITLE_SEX:
                                        radioBean.setDefaultSelectedIndex(mDisabledInfo.getSex());
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case MultipleItem.ITEM_BUSINESS_SELECT:
                                BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) array.getObject();
                                switch (textValueSelectBean.getKey()) {
                                    case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                                        categoryId = mDisabledInfo.getCategory();
                                        textValueSelectBean.setValue(mDisabledInfo.getCategoryName());
                                        break;
                                    case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                                        levelId = mDisabledInfo.getLevel();
                                        textValueSelectBean.setValue(mDisabledInfo.getLevelName());
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    //????????????????????????????????????
                    mDisabledInfo = null;

                }
            }

        }

    }
}
