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
 * @Description: 作用描述
 * @CreateDate: 2021/1/19 9:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/19 9:36
 */
public abstract class BaseBusinessFragment extends BaseSelectPhotoFragment<BusinessPresent> implements BusinessContract.BaseIBusinessView,
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
    private int admissionTimeId = 1;//开学日期
    private int educationId = 1;//学历
    private int selectedIQId = 1;//（1<=25；2<=26-39；3<=40-54；4<=55-75）
    private int selectedBrainId = 1;//（1<=25；2<=26-39；3<=40-54；4<=55-75）
    private int selectedMarrayStatus = 0;//0未婚；1已婚(有配偶)；2丧偶；3离婚
    private int selectedRegMode = 1;//1 是现场  2是来电
    private int selectedNation = 0;//民族
    private int selectedEducationLevel = 0;//学历登记
    private int categoryId = 0;//残疾类别
    private int levelId = 0;//残疾等级
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
                //获取残疾人基本信息
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
                            //示例图片不可点击

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
                                //文化程度
                                mPresenter.getDisabledEducation(BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_EDUCATION:
                                //学历
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
                                //残疾类别
                                mPresenter.getDisabledCategory(AppHttpPath.GET_DISABLED_TYPE);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                                //残疾等级
                                mPresenter.getDisabledLevel(AppHttpPath.GET_DISABLED_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_ADMISSION_TIME:
                                //入学时间
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
                                //儿童发育商
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
                                //脑瘫类型
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
                                //出生年月
                                PickerManager.getInstance().showTimePickerView(mContext, null, "出生年月",
                                        new PickerManager.OnTimePickerTimeSelectedListener() {
                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        birthDay = DateUtil.getDateString(date, "yyyy年MM月dd日");
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
                        //签名
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
     * 学历（1博士；2硕士研究生；3本科；4大专）
     *
     * @return
     */
    protected List<String> getEducations() {
        List<String> arrays = new ArrayList<>();
        arrays.add("博士");
        arrays.add("硕士研究生");
        arrays.add("本科");
        arrays.add("大专");
        return arrays;
    }
    /**
     * 入学时间
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
     * 获取activity
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
        //清除签名文件
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
                //跳转到裁剪头像的界面
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
     * 获取adapter中的数据
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
                        ToastUtils.toast(mContext, "请选择申请人照片");
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
                            key = "监护人" + key.substring(1, key.length());
                        } else if (key.contains(mPresenter.PERSIONAL_TAG)) {
                            key = "残疾人" + key.substring(1, key.length());
                        }
                        ToastUtils.toast(mContext, "请输入" + key);
                        return null;
                    }
                    String formKey = null;
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NAME:
                            //姓名
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_NAME_FAMILY:
                            //监护人姓名  居家托养。。。
                            formKey = "guardianName";
                            break;
                        case BusinessContract.TABLE_TITLE_NAME_PERSIONAL:
                            //残疾人姓名   居家托养。。。
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_NAME:
                            //姓名
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_AGE_FAMILY:
                            //年龄
                            formKey = "guardianAge";
                            break;
                        case BusinessContract.TABLE_TITLE_AGE_PERSIONAL:
                            //年龄
                            formKey = "age";
                            break;
                        case BusinessContract.TABLE_TITLE_HOMETOWN:
                            //籍贯
                            formKey = "nativePlace";
                            break;
                        case BusinessContract.TABLE_TITLE_IDCARD:
                            //身份证号
                            if (!RuleTools.isIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "身份证号格式不正确");
                                return null;
                            }
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IDCARD:
                            //儿童身份证号
                            if (!RuleTools.isIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "身份证号格式不正确");
                                return null;
                            }
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                            //残疾证号
                            if (!RuleTools.isDisabledIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "残疾证号格式不正确");
                                return null;
                            }
                            formKey = "disabilityCertificate";
                            break;
                        case BusinessContract.TABLE_TITLE_ADDR:
                            //现住址
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDR2:
                            //家庭住址
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_PRESENT_NAME:
                            //家长姓名
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN:
                            //监护人
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_RELATION:
                            //与监护人关系
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD:
                            //与儿童关系
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_PHONE:
                            //联系电话
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "联系电话格式不正确");
                                return null;
                            }
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACTER:
                            //联系人
                            formKey = "contacts";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACT_MODE:
                            //联系方式
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "联系电话格式不正确");
                                return null;
                            }
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CURRENT_LIVE_ADDR:
                            //目前居住地
                            formKey = "residentialAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER:
                            //工作单位
                            formKey = "workingUnit";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER_TYPE:
                            //职业工种
                            formKey = "profession";
                            break;
                        case BusinessContract.TABLE_TITLE_UNIT_NATURE:
                            //单位性质
                            formKey = "unitNature";
                            break;
                        case BusinessContract.TABLE_TITLE_SPECIAL:
                            //特长
                            formKey = "specialty";
                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDR:
                            //户籍所在地
                            formKey = "residenceAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_STREET:
                            //街道
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE:
                            //社区
                            break;
                        case BusinessContract.TABLE_TITLE_ADDR_LIVE_NOW:
                            //现居住地
                            formKey = "residentialAddress";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME:
                            //本人简历
                            formKey = "mineResume";
                            break;
                        case BusinessContract.TABLE_TITLE_WANTED_POST:
                            //意向岗位
                            formKey = "postIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_WORK_AREA:
                            //择业地区
                            formKey = "areaIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_TRAIN_INTENT:
                            //培训意向
                            formKey = "trainingIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_REMARK:
                            //备注
                            formKey = "remark";
                            break;
                        case BusinessContract.TABLE_TITLE_DIAGNOSIS_AGENCY:
                            //诊断机构
                            formKey = "diagnoseAgency";
                            break;
                        case BusinessContract.TABLE_TITLE_DIAGNOSIS_RESULT:
                            //诊断结果
                            formKey = "diagnoseResult";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_NAME:
                            //监护人姓名
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_HEALTH_AGENCY:
                            //申请的定点康复机构名称
                            formKey = "recoveryInstitution";
                            break;
                        case BusinessContract.TABLE_TITLE_GUAIDIAN_REQUEST:
                            //监护人申请
                            formKey = "guardianApply";
                            break;
                        case BusinessContract.TABLE_TITLE_ASSIST_TOOL_AMOUNT:
                            //器具数量
                            formKey = "quantity";
                            break;
                        //                        case BusinessContract.TABLE_TITLE_JOB_STATUS:
                        //                            //就业状况
                        //                            formKey = "jobSituation";
                        //                            break;
                        case BusinessContract.TABLE_TITLE_JOB:
                            //职业
                            formKey = "profession";
                            break;
                        case BusinessContract.TABLE_TITLE_TRAIN_TYPE:
                            //培训种类
                            formKey = "trains";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME_WORK:
                            //工作简历
                            formKey = "workingResume";
                            break;
                        case BusinessContract.TABLE_TITLE_RESUME_TRAIN:
                            //培训简历
                            formKey = "trainingResume";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_PEOPLE_RELATION:
                            //与残疾人关系
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_COLLEGE:
                            //录取院校
                            formKey = "college";
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_PERSIONAL:
                            //录取专业
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
                            //学制
                            formKey = "system";
                            break;
                        case BusinessContract.TABLE_TITLE_TOWN_STREET:
                            //镇街
                            formKey = "townStreet";
                            break;
                        case BusinessContract.TABLE_TITLE_WCHAT_PHONE:
                            //微信手机号
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "微信手机号格式不正确");
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
                            //户口类别
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
                            //家庭经济状况
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
                        ToastUtils.toast(mContext, "请选择" + textValueSelectBean.getKey());
                        return null;
                    }
                    switch (textValueSelectBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NATION:
                            //民族
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("nation", String.valueOf(selectedNation));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MARRIAGE:
                            //婚姻状况
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("marriage", String.valueOf(selectedMarrayStatus));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                            //文化程度
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("education", String.valueOf(selectedEducationLevel));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_EDUCATION:
                            //学历
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("education", String.valueOf(educationId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IQ:
                            //儿童发育商
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("iq", String.valueOf(selectedIQId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_ADMISSION_TIME:
                            //入学时间
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("startSchoolTime ", String.valueOf(admissionTimeId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE:
                            //脑瘫类型
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("type", String.valueOf(selectedBrainId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_BIRTH:
                            //出生年月
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
                                    ToastUtils.toast(mContext, "请选择残疾证正面照片");
                                    return null;
                                }
                                //残疾证正面照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_ALL:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择残疾证正面照片");
                                    return null;
                                }
                                //残疾证正面照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PHOTO_ALL:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择残疾证正面照片");
                                    return null;
                                }
                                //残疾证正面照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择残疾证反面照片");
                                    return null;
                                }
                                //残疾证反面照片
                                builder.addFormDataPart("backPictureFile", "backPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_GUARDIAN_ID_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择监护人身份证照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("guardianIdPictureFile", "guardianIdPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_HUKOU_RELATION_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择户口本照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("householdRegisterPictureFile", "householdRegisterPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_MATERIAL_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择病例材料照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("casePictureFile", "casePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_STUDENT_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择学生身份证照片");
                                    return null;
                                }
                                //学生身份证照片
                                builder.addFormDataPart("studentIdPictureFile", "studentIdPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_GROUP_PHOTO:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择学生与家长生活合影照片");
                                    return null;
                                }
                                //学生与家长生活合影照片
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_PRESENT_DISBILITY_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择家长重度残疾证照片");
                                    return null;
                                }
                                //家长重度残疾证照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_ACCOUNT_BOOK:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择户口本拍照能说明家庭关系的照片");
                                    return null;
                                }
                                //户口本拍照能说明家庭关系的照片
                                builder.addFormDataPart("householdRegisterPictureFile", "householdRegisterPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择生活照片");
                                    return null;
                                }
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_ADMISSION_NOTICE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择入学通知书照片");
                                    return null;
                                }
                                //入学通知书照片
                                builder.addFormDataPart("noticePictureFile", "noticePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_STUDENT_CARD_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择学生证照片");
                                    return null;
                                }
                                //学生证照片
                                builder.addFormDataPart("studentCertificatePictureFile",
                                        "studentCertificatePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_TUITION_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择缴费凭证或缴费凭证照片");
                                    return null;
                                }
                                //缴费凭证或缴费凭证照片
                                builder.addFormDataPart("payPictureFile", "payPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC_MYSELF:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择生活照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_PIC_IDCARD:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择身份证照片");
                                    return null;
                                }
                                //身份证照片
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
                    //签名
                    ItemSignBean signBean = (ItemSignBean) array.getObject();
                    if (!StringTools.isStringValueOk(signBean.getSignPicPath())) {
                        ToastUtils.toast(mContext, "请签名");
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
                        ToastUtils.toast(mContext, "请选择" + recycleBean.getTitleKey());
                        return null;
                    }
                    switch (recycleBean.getTitleKey()) {
                        case BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY:
                            //是否伴有其他残疾
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("otherDisabled", selectedData);
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS:
                            //家庭经济状况
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("familyEconomy", String.valueOf(selectedItem.getIndex() + 1));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_POOR_FAMILY:
                            //贫困家庭
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("poorFamily", String.valueOf(selectedItem.getIndex()));
                                if (5 == selectedItem.getIndex()) {
                                    //其他困难  需要上传描述字段
                                    builder.addFormDataPart("poorFamilyExplain", selectedItem.getDes());
                                }
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MEDICALSAFE:
                            //享受医疗保险情况
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
                        ToastUtils.toast(mContext, "请输入" + yearBean.getKey());
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
            //残疾人基本信息
            DisabledBaseInfoBean disabledBaseInfoBean = (DisabledBaseInfoBean) o;
            if (disabledBaseInfoBean != null) {
                mDisabledInfo = disabledBaseInfoBean.getData();
                if (mDisabledInfo != null) {
                    //给适配器赋值
                    List<MultipleItem> arrays = adapter.getData();
                    for (MultipleItem array : arrays) {
                        switch (array.getItemType()) {

                            case MultipleItem.ITEM_BUSINESS_EDIT:
                                BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                                        .getObject();
                                switch (textValueEditBean.getKey()) {
                                    case BusinessContract.TABLE_TITLE_HOMETOWN:
                                        //籍贯
                                        textValueEditBean.setValue(mDisabledInfo.getPermanentAddress());
                                        break;
                                    case BusinessContract.TABLE_TITLE_HOME_ADDR:
                                        //户籍所在地
                                        textValueEditBean.setValue(mDisabledInfo.getPermanentAddress());
                                        break;
                                    case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                                        //残疾证号
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
                    //没有查到相应残疾人的信息
                    mDisabledInfo = null;

                }
            }

        }

    }
}
