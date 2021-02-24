package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
    private int selectedMarrayStatus = 0;//0未婚；1已婚(有配偶)；2丧偶；3离婚
    private int selectedRegMode = 1;//1 是现场  2是来电
    private int selectedNation = 0;//民族
    private int selectedEducationLevel = 0;//学历登记
    private int categoryId = 0;//残疾类别
    private int levelId = 0;//残疾等级
    private BusinessTextValueBean selectBean;
    public static String BUSINESS_ID = "businessid";
    protected int businessId = -1;

    protected abstract String getTitleName();

    protected abstract View getFootView();

    protected abstract View getHeadView();

    protected abstract List<MultipleItem> getAdapterData();

    /**
     * @return
     */
    protected abstract ImageView getSignIv();

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
        businessId = getIntent().getIntExtra(BUSINESS_ID,-1);
        setTitleName(getTitleName());
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
        adapter = new HandlerBusinessAdapter(getAdapterData(),businessId==-1?false:true);
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
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
                                                selectedRegMode = options1;
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
                            default:
                                break;
                        }
                        break;
                    case R.id.sign_name_iv:
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
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
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
                        if (getSignIv() != null) {
                            getSignIv().setImageBitmap(bitmap1); //设置Bitmap
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
                        ToastUtils.toast(mContext, "请输入" + textValueEditBean.getKey());
                        return null;
                    }
                    String formKey = null;
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_NAME:
                            //姓名
                            formKey = "name";
                            break;
                        case BusinessContract.TABLE_TITLE_BIRTH:
                            //出生年月
                            formKey = "birth";
                            break;
                        case BusinessContract.TABLE_TITLE_HOMETOWN:
                            //籍贯
                            formKey = "nativePlace";
                            break;
                        case BusinessContract.TABLE_TITLE_IDCARD:
                            formKey = "idNumber";
                            break;
                        case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                            formKey = "disabilityCertificate";
                            break;
                        case BusinessContract.TABLE_TITLE_ADDR:
                            formKey = "address";
                            break;
                        case BusinessContract.TABLE_TITLE_ZIP_CODE:
                            formKey = "postCode";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN:
                            formKey = "guardian";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_RELATION:
                            formKey = "relationship";
                            break;
                        case BusinessContract.TABLE_TITLE_PHONE:
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_CONTACT_MODE:
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER:
                            formKey = "workingUnit";
                            break;
                        case BusinessContract.TABLE_TITLE_WORKER_TYPE:
                            formKey = "profession";
                            break;
                        case BusinessContract.TABLE_TITLE_UNIT_NATURE:
                            formKey = "unitNature";
                            break;
                        case BusinessContract.TABLE_TITLE_CARD_TYPE:
                            formKey = "type";
                            break;
                        case BusinessContract.TABLE_TITLE_SPECIAL:
                            //特长
                            formKey = "specialty";
                            break;
                        case BusinessContract.TABLE_TITLE_HOME_ADDR:
                            //户籍所在地
                            formKey = "residenceAddress";
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

                        default:
                            break;
                    }
                    if (formKey != null) {
                        builder.addFormDataPart(formKey, textValueEditBean.getValue());
                    }

                    break;
                case MultipleItem.ITEM_BUSINESS_RADIO:
                    BusinessRadioBean radioBean = (BusinessRadioBean) array.getObject();
                    switch (radioBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_SEX:
                            builder.addFormDataPart("sex", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_HUKOU:
                            builder.addFormDataPart("accountType", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_IS_WEEL_COMPANY:
                            builder.addFormDataPart("unitWelfare", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_HEAR:
                            builder.addFormDataPart("hearingDisability", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_DISABILITY_LIMB:
                            builder.addFormDataPart("physicalDisability", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        default:
                            break;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_SELECT:
                    BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) array.getObject();
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
                            builder.addFormDataPart("type", String.valueOf(selectedCardType));
                            break;
                        case AppHttpPath.GET_DISABLED_TYPE:
                            builder.addFormDataPart("category", String.valueOf(categoryId));
                            break;
                        case AppHttpPath.GET_DISABLED_LEVEL:
                            builder.addFormDataPart("level", String.valueOf(levelId));
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
                                    ToastUtils.toast(mContext,"请选择残疾证照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("pictureFile", "pictureFile", RequestBody.create(MediaType.parse("file"),
                                        new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_MATERIAL_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext,"请选择病例材料照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("casePictureFile", "casePictureFile", RequestBody.create(MediaType.parse("file"),
                                        new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext,"请选择生活照片");
                                    return null;
                                }
                                //残疾证照片
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile", RequestBody.create(MediaType.parse("file"),
                                        new File(picBean.getPicPath())));
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW:
                    RecycleBean recycleBean = (RecycleBean) array.getObject();
                    List<ItemCheckBoxBean> data = recycleBean.getData();
                    if (!isCheckBoxSelected(data)) {
                        ToastUtils.toast(mContext, "请选择" + recycleBean.getTitleKey());
                        return null;
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
                    break;
                default:
                    break;
            }
        }
        return builder;
    }

    /**
     * 查看有没有选中的box
     *
     * @param data
     * @return
     */
    private boolean isCheckBoxSelected(List<ItemCheckBoxBean> data) {
        for (ItemCheckBoxBean datum : data) {
            if (datum.isSelecte()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        BusinessPropertyBean propertyNationBean = null;
        List<BusinessPropertyBean.DataBean> data = null;
        if (BusinessContract.TABLE_TITLE_NATION.equals(tag) || BusinessContract.TABLE_TITLE_EDUCATION_LEVEL.equals(tag)
                || AppHttpPath.GET_DISABLED_TYPE.equals(tag)|| AppHttpPath.GET_DISABLED_LEVEL.equals(tag)) {
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

        }

    }
}
