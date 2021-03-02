package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.customview.GestureSignatureView;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessPropertyBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.DeafBean;
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.utils.StringTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public abstract class BaseBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView, View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerBusinessAdapter adapter;
    private ImageView mHeadIv;
    private ImageView mFormPicIv;
    private int currentPosition;
    private GestureSignatureView gsv_signature;
    private BottomSheetDialog bottomSheetDialog;
    private String signPath;
    private ImageView mSignIv = null;
    private TextView mSelectTv;
    private int selectedCardType = 1;//1新申请；2换领申请；3补办申请
    private int selectedIQId = 1;//（1<=25；2<=26-39；3<=40-54；4<=55-75）
    private int selectedBrainId = 1;//（1<=25；2<=26-39；3<=40-54；4<=55-75）
    private int selectedMarrayStatus = 0;//0未婚；1已婚(有配偶)；2丧偶；3离婚
    private int selectedRegMode = 1;//1 是现场  2是来电
    private int selectedNation = 0;//民族
    private int selectedEducationLevel = 0;//学历登记
    private int categoryId = 0;//残疾类别
    private int levelId = 0;//残疾等级
    private int toolId = 0;//辅具id
    private BusinessTextValueBean selectBean;
    public static String BUSINESS_ID = "businessid";
    protected int businessId = -1;
    private ItemSignBean itemSignBean = null;
    private OnPicSelectedCallBack picSelectedCallBack;

    protected abstract String getTitleName();

    protected abstract View getFootView();

    protected abstract View getHeadView();

    protected abstract void commit();

    protected abstract List<MultipleItem> getAdapterData();

    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        ////businessId=-1进入详情模式
        businessId = getIntent().getIntExtra(BUSINESS_ID, -1);
        setTitleName(getTitleName());
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
        adapter = new HandlerBusinessAdapter(getAdapterData(), businessId == -1 ? false : true);
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        if (getFootView() != null) {
            adapter.setFooterView(getFootView());
        }
        if (getHeadView() != null) {
            adapter.setHeaderView(getHeadView());
        }
        setAdapterClick();
    }

    private void setAdapterClick() {
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
                        choseImage(0, BaseBusinessActivity.this, 1);
                        break;
                    case R.id.form_head_pic_iv:
                        choseImage(0, BaseBusinessActivity.this, 1);
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
                                List<String> marrayStatus = getMarrayStatus();
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
                            case BusinessContract.TABLE_TITLE_REG_MODE:
                                List<String> regModes = getRegistMode();
                                PickerManager.getInstance().showOptionPicker(mContext, regModes,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                selectedRegMode = options1 + 1;
                                                mSelectTv.setText(regModes.get(options1));
                                                selectBean.setValue(regModes.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                                mPresenter.getDisabledEducation(BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                                //残疾类别
                                mPresenter.getDisabledCategory(AppHttpPath.GET_DISABLED_TYPE);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                                //残疾等级
                                mPresenter.getDisabledLevel(AppHttpPath.GET_DISABLED_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL:
                                //辅具
                                if (0 == categoryId) {
                                    ToastUtils.toast(mContext, "请先选择残疾类别");
                                    return;
                                }
                                mPresenter.getDisabledAIDS(categoryId, AppHttpPath.GET_DISABLED_AIDS);
                                break;
                            case BusinessContract.TABLE_TITLE_CARD_TYPE:
                                List<String> cards = getCardTypes();
                                PickerManager.getInstance().showOptionPicker(mContext, cards,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                selectedCardType = options1 + 1;
                                                mSelectTv.setText(cards.get(options1));
                                                selectBean.setValue(cards.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_CHILD_IQ:
                                //儿童发育商
                                List<String> iQs = getChildIQs();
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
                                List<String> brainBadTypes = getBrainBadTypes();
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
                            default:
                                break;
                        }
                        break;
                    case R.id.sign_name_iv:
                        itemSignBean = (ItemSignBean) multipleItem.getObject();
                        //签名
                        mSignIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id
                                .sign_name_iv);
                        showSignatureView();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * 儿童发育商（1<=25；2<=26-39；3<=40-54；4<=55-75）
     *
     * @return
     */
    protected List<String> getChildIQs() {
        List<String> arrays = new ArrayList<>();
        arrays.add("<=25");
        arrays.add("<=26~39");
        arrays.add("<=40~54");
        arrays.add("<=55~75");
        return arrays;
    }

    /**
     * 1痉挛型；2手足徐动型；3共济失调；4弛缓型；5混合型
     *
     * @return
     */
    protected List<String> getBrainBadTypes() {
        List<String> arrays = new ArrayList<>();
        arrays.add("痉挛型");
        arrays.add("手足徐动型");
        arrays.add("共济失调");
        arrays.add("弛缓型");
        arrays.add("混合型");
        return arrays;
    }

    /**
     * 获取证件类型
     *
     * @return
     */
    protected List<String> getCardTypes() {
        List<String> arrays = new ArrayList<>();
        arrays.add("新申请");
        arrays.add("换领申请");
        arrays.add("补办申请");
        return arrays;
    }

    /**
     * 婚姻状况 0未婚；1已婚(有配偶)；2丧偶；3离婚
     *
     * @return
     */
    protected List<String> getMarrayStatus() {
        List<String> arrays = new ArrayList<>();
        arrays.add("未婚");
        arrays.add("已婚(有配偶)");
        arrays.add("丧偶");
        arrays.add("离婚");
        return arrays;
    }

    /**
     * 登记方式  1 现场 2 来电
     *
     * @return
     */
    protected List<String> getRegistMode() {
        List<String> arrays = new ArrayList<>();
        arrays.add("现场");
        arrays.add("来电");
        return arrays;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.removeAllFooterView();
            adapter.removeAllHeaderView();
        }
        if (bottomSheetDialog != null) {
            if (bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }
            bottomSheetDialog = null;
        }
        //清除签名文件
        FileCacheUtils.clearImage(FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME);
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (picSelectedCallBack != null && adapter == null) {
            picSelectedCallBack.picSelected(icons);
            return;
        }
        if (icons.size() > 0) {
            String path = icons.get(0);
            BusinessPicBean businessPicBean =
                    (BusinessPicBean) ((MultipleItem) adapter.getData().get(currentPosition)).getObject();
            businessPicBean.setPicPath(path);
            adapter.notifyItemChanged(currentPosition);
        }
    }

    /**
     * 获取签名图片的路径
     *
     * @return
     */
    protected String getSignPath() {
        File file = new File(FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME);
        return file.exists() ? FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME : null;
    }

    /**
     * 展示签名的画板
     */
    protected void showSignatureView() {

        bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.signature_view_layout, null);
        view.findViewById(R.id.signature_view_save).setOnClickListener(this);
        view.findViewById(R.id.signature_view_rewrite).setOnClickListener(this);
        view.findViewById(R.id.signature_view_cancel).setOnClickListener(this);
        //签名画板
        gsv_signature = view.findViewById(R.id.gsv_signature);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signature_view_save:
                if (gsv_signature.getTouched()) {
                    try {
                        signPath = FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME;
                        //保存到本地
                        gsv_signature.save(signPath);
                        Bitmap bitmap1 =
                                FileCacheUtils.getLoacalBitmap(signPath); //从本地取图片(在cdcard中获取)  //
                        if (mSignIv != null) {
                            mSignIv.setImageBitmap(bitmap1); //设置Bitmap
                        }
                        if (itemSignBean != null) {
                            itemSignBean.setSignPicPath(signPath);
                        }
                        //                        SINGE_STATE = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //                    mSignNameTagIv.setVisibility(View.GONE);
                    //                    mSignNameNoticeTv.setVisibility(View.GONE);
                    //                    mSignRedactImg.setVisibility(View.VISIBLE);
                    bottomSheetDialog.dismiss();
                    //                    mSignResign.getRightTextView().setVisibility(View.VISIBLE);
                } else {
                    ToastUtils.toast(mContext, "请签名！");
                }

                break;

            case R.id.signature_view_rewrite:
                gsv_signature.clear();
                break;
            case R.id.signature_view_cancel:
                gsv_signature.clear();
                bottomSheetDialog.dismiss();
                break;
            case R.id.commit_business_form_tv:
                //提交
                commit();
                break;
            default:
                break;
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
                    if (TextUtils.isEmpty(textValueEditBean.getValue())) {
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
                        case BusinessContract.TABLE_TITLE_BIRTH:
                            //出生年月
                            formKey = "birth";
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
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IDCARD:
                            //儿童身份证号
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                            //残疾证号
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
                        case BusinessContract.TABLE_TITLE_ZIP_CODE:
                            //邮政编码
                            formKey = "postCode";
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
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACTER:
                            //联系人
                            formKey = "contacts";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACT_MODE:
                            //联系方式
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
                        case BusinessContract.TABLE_TITLE_SALARY:
                            //月薪要求
                            formKey = "monthlySalaryIntention";
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
                        case BusinessContract.TABLE_TITLE_JOB_STATUS:
                            //就业状况
                            formKey = "jobSituation";
                            break;
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
                        case BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM:
                            //学制
                            formKey = "system";
                            break;
                        case BusinessContract.TABLE_TITLE_HOUSE_PHONE:
                            //住宅电话
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_MOBILE_NUM:
                            //手机号码
                            formKey = "phone";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_ID_CARD:
                            //监护人身份证号
                            formKey = "guardianId";
                            break;
                        case BusinessContract.TABLE_TITLE_COMMUNICATION_ADDR:
                            //通讯地址
                            formKey = "postalAddress";
                            break;

                        default:
                            break;
                    }
                    if (formKey != null) {
                        builder.addFormDataPart(formKey, textValueEditBean.getValue());
                    }

                    break;
                case MultipleItem.ITEM_BUSINESS_EDIT2:
                    BusinessTextValueBean textValueEditBean2 = (BusinessTextValueBean) array
                            .getObject();
                    String titleKey = null;
                    switch (textValueEditBean2.getKey()) {
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_F:
                            //家族遗传与儿童关系
                            titleKey = "geneticHistoryRelationship";
                            break;
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_C:
                            //护工与儿童关系
                            titleKey = "accompanyRelationship";
                            break;
                        case BusinessContract.TABLE_TITLE_DISCOVER_DISABILITY_YEAR:
                            //发现耳聋月龄
                            titleKey = "findTime";
                            break;

                        default:
                            break;
                    }
                    if (titleKey != null) {
                        builder.addFormDataPart(titleKey, textValueEditBean2.getValue());
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
                        case BusinessContract.TABLE_TITLE_PROJECT_LEVEL:
                            builder.addFormDataPart("grand", String.valueOf(radioBean.getDefaultSelectedIndex() + 1));
                            break;
                        case BusinessContract.TABLE_TITLE_IS_POOR_FAMILY:
                            builder.addFormDataPart("alleviation", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_HAS_DISABILITY:
                            //是否有家族耳聋史
                            builder.addFormDataPart("geneticHistory", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_HAS_CARE_WORKER:
                            //接受救助后家庭中有无专人陪伴康复
                            builder.addFormDataPart("accompany",String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        default:
                            break;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_SELECT:
                    BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) array.getObject();
                    if (!StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                        ToastUtils.toast(mContext, "请选择" + textValueSelectBean.getKey());
                        return null;
                    }
                    switch (textValueSelectBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NATION:
                            //民族
                            builder.addFormDataPart("nation", String.valueOf(selectedNation));
                            break;
                        case BusinessContract.TABLE_TITLE_MARRIAGE:
                            //婚姻状况
                            builder.addFormDataPart("marriage", String.valueOf(selectedMarrayStatus));
                            break;
                        case BusinessContract.TABLE_TITLE_REG_MODE:
                            //登记方式
                            builder.addFormDataPart("way", String.valueOf(selectedRegMode));
                            break;
                        case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                            //文化程度
                            builder.addFormDataPart("education", String.valueOf(selectedEducationLevel));
                            break;
                        case BusinessContract.TABLE_TITLE_CARD_TYPE:
                            //证件申请类型
                            builder.addFormDataPart("type", String.valueOf(selectedCardType));
                            break;
                        case BusinessContract.TABLE_TITLE_CHILD_IQ:
                            //儿童发育商
                            builder.addFormDataPart("iq", String.valueOf(selectedIQId));
                            break;
                        case BusinessContract.TABLE_TITLE_BRAIN_PALSY_STYLE:
                            //脑瘫类型
                            builder.addFormDataPart("type", String.valueOf(selectedBrainId));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                            builder.addFormDataPart("category", String.valueOf(categoryId));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                            builder.addFormDataPart("level", String.valueOf(levelId));
                            break;
                        case BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL:
                            builder.addFormDataPart("aidsId", String.valueOf(toolId));
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
                            case BusinessContract.TABLE_TITLE_DISABLE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择残疾证照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_DISABLE_PHOTO:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择残疾证照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("pictureFile", "pictureFile",
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
                            case BusinessContract.TABLE_TITLE_LIFE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "请选择生活照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
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
                                    "file"), new File(getSignPath())));
                    break;
                case MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW:
                    RecycleBean recycleBean = (RecycleBean) array.getObject();
                    List<ItemCheckBoxBean> data = recycleBean.getData();
                    String selectedData = getSelectedItems(data);
                    ItemCheckBoxBean selectedItem = getSelectedItem(data);
                    if (!StringTools.isStringValueOk(selectedData)) {
                        ToastUtils.toast(mContext, "请选择" + recycleBean.getTitleKey());
                        return null;
                    }
                    switch (recycleBean.getTitleKey()) {
                        case BusinessContract.TABLE_TITLE_WITH_OTHER_DISABILITY:
                            //是否伴有其他残疾
                            builder.addFormDataPart("otherDisabled", selectedData);
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS:
                            //家庭经济状况
                            builder.addFormDataPart("familyEconomy", String.valueOf(selectedItem.getIndex() + 1));
                            break;
                        case BusinessContract.TABLE_TITLE_POOR_FAMILY:
                            //贫困家庭
                            builder.addFormDataPart("poorFamily", String.valueOf(selectedItem.getIndex()));
                            if (5 == selectedItem.getIndex()) {
                                //其他困难  需要上传描述字段
                                builder.addFormDataPart("poorFamilyExplain", selectedItem.getDes());
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MEDICALSAFE:
                            //享受医疗保险情况
                            builder.addFormDataPart("medicalInsurance", String.valueOf(selectedItem.getIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_CURRENT_RECOVERY:
                            //目前康复状态
                            builder.addFormDataPart("recovery", String.valueOf(selectedItem.getIndex()));
                            break;

                        case BusinessContract.TABLE_TITLE_REQUEST_RECOVERY:
                            //康复需求项目
                            builder.addFormDataPart("recoveryProject", String.valueOf(selectedItem.getIndex()));
                            break;
                        default:
                            break;
                    }


                    break;
                case MultipleItem.ITEM_BUSINESS_DEAF_TABLE:
                    DeafBean deafBean = (DeafBean) array.getObject();
                    if (!StringTools.isStringValueOk(deafBean.getLeftEar())) {
                        ToastUtils.toast(mContext, "请输入左耳听力值");
                        return null;
                    } else {
                        builder.addFormDataPart("leftEar", deafBean.getLeftEar());
                    }
                    if (!StringTools.isStringValueOk(deafBean.getRightEar())) {
                        ToastUtils.toast(mContext, "请输入右耳听力值");
                        return null;
                    } else {
                        builder.addFormDataPart("rightEar", deafBean.getRightEar());
                    }
                    int isWear = deafBean.getWear();
                    builder.addFormDataPart("wear", String.valueOf(isWear));
                    if (0 == isWear) {
                        //佩戴助听器了
                        if (!StringTools.isStringValueOk(deafBean.getWearTimeYear())) {
                            ToastUtils.toast(mContext, "请输入佩戴时间--岁");
                            return null;
                        }
                        if (!StringTools.isStringValueOk(deafBean.getWearTimeMonth())) {
                            ToastUtils.toast(mContext, "请输入佩戴时间--个月");
                            return null;
                        }
                        StringBuilder wearTimeSb = new StringBuilder();
                        wearTimeSb.append(deafBean.getWearTimeYear());
                        wearTimeSb.append("年");
                        wearTimeSb.append(deafBean.getWearTimeMonth());
                        wearTimeSb.append("个月");
                        builder.addFormDataPart("wearTime", wearTimeSb.toString().trim());
                        builder.addFormDataPart("wearEar", String.valueOf(deafBean.getWearEar()));
                    }
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

    /**
     * 获取户口信息  或者 监护人的地址
     *
     * @return
     */
    protected String getHukouInfoOrFamilyAddrAdapterData() {
        List<MultipleItem> arrays = adapter.getData();
        StringBuilder sb = new StringBuilder();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {

                case MultipleItem.ITEM_BUSINESS_EDIT:
                    BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                            .getObject();
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_STREET:
                            //街道
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_STREET_FAMILY:
                            //街道
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE_FAMILY:
                            //社区
                            sb.append(textValueEditBean.getValue());
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE:
                            //社区
                            sb.append(textValueEditBean.getValue());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;

            }
        }
        return sb.toString().trim();
    }

    /**
     * 获取残疾人住址
     *
     * @return
     */
    protected String getDisabledAddrAdapterData() {
        List<MultipleItem> arrays = adapter.getData();
        StringBuilder sb = new StringBuilder();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {

                case MultipleItem.ITEM_BUSINESS_EDIT:
                    BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                            .getObject();
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_STREET_PERSIONAL:
                            //街道
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE_PERSIONAL:
                            //社区
                            sb.append(textValueEditBean.getValue());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;

            }
        }
        return sb.toString().trim();
    }

    /**
     * 获取选中的项目
     *
     * @param data
     * @return
     */
    protected String getSelectedItems(List<ItemCheckBoxBean> data) {
        List<String> sb = new ArrayList<>();
        for (ItemCheckBoxBean datum : data) {
            if (datum.isSelecte()) {
                sb.add(datum.getKey());
            }
        }
        return sb.toString();
    }

    /**
     * 获取选中的项目的  单选
     *
     * @param data
     * @return
     */
    protected ItemCheckBoxBean getSelectedItem(List<ItemCheckBoxBean> data) {
        for (ItemCheckBoxBean datum : data) {
            if (datum.isSelecte()) {
                return datum;
            }
        }
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        BusinessPropertyBean propertyNationBean = null;
        List<BusinessPropertyBean.DataBean> data = null;
        if (BusinessContract.TABLE_TITLE_NATION.equals(tag) || BusinessContract.TABLE_TITLE_EDUCATION_LEVEL.equals(tag)
                || AppHttpPath.GET_DISABLED_TYPE.equals(tag) || AppHttpPath.GET_DISABLED_LEVEL.equals(tag)
                || AppHttpPath.GET_DISABLED_AIDS.equals(tag)) {
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
                                    case AppHttpPath.GET_DISABLED_AIDS:
                                        toolId = dataBean.getId();
                                        break;
                                    default:
                                        break;
                                }

                            }
                        });
            }

        } else {
            BaseResult baseResult = (BaseResult) o;
            ToastUtils.toast(mContext, baseResult.message);
            finish();
        }
    }

    /**
     * 图片被选中后的回调
     */
    public interface OnPicSelectedCallBack {
        void picSelected(List<String> icons);
    }


    public void setOnPicSelectedCallBack(OnPicSelectedCallBack picSelectedCallBack) {
        this.picSelectedCallBack = picSelectedCallBack;
    }
}
