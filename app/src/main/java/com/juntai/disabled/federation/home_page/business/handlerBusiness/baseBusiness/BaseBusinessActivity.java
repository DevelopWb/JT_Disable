package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.RuleTools;
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
import com.juntai.disabled.federation.bean.business.DisabledBaseInfoBean;
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.bean.business.ToolInfoBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HeadCropActivity;
import com.juntai.disabled.federation.utils.DateUtil;
import com.juntai.disabled.federation.utils.StringTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
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
public abstract class BaseBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView, View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerBusinessAdapter adapter;
    private ImageView mHeadIv;
    private ImageView mFormPicIv;
    private int currentPosition;
    private GestureSignatureView gsv_signature;
    private BottomSheetDialog bottomSheetDialog;
    private String signPath = null;
    private String birthDay = null;
    private ImageView mSignIv = null;
    private ImageView mHandlerSignIv = null;//?????????????????????????????????
    private TextView mSelectTv;
    private int methodsId = 1;//????????????
    private int selectedIQId = 1;//???1<=25???2<=26-39???3<=40-54???4<=55-75???
    private int selectedBrainId = 1;//???1<=25???2<=26-39???3<=40-54???4<=55-75???
    private int selectedMarrayStatus = 0;//0?????????1??????(?????????)???2?????????3??????
    private int selectedRegMode = 1;//1 ?????????  2?????????
    private int selectedNation = 0;//??????
    private int selectedEducationLevel = 0;//????????????
    private int categoryId = 0;//????????????
    private int jobStatusId = 0;//????????????
    private int trainIntentId = 0;//??????????????????
    private int levelId = 0;//????????????
    private int toolId = 0;//??????id
    private BusinessTextValueBean selectBean;
    public static String BUSINESS_ID = "businessid";
    protected int businessId = -1;
    private ItemSignBean itemSignBean = null;
    private OnPicSelectedCallBack picSelectedCallBack;
    private BusinessPicBean businessPicBean;
    /**
     * ????????????
     */
    private TextView mToolNameTv;
    private ImageView mToolPicIv;
    /**
     * ??????:
     */
    private TextView mReserveTv;
    private WebView mToolInfoDesWb;
    private DisabledBaseInfoBean.DataBean mDisabledInfo = null;

    protected abstract String getTitleName();

    protected abstract View getFootView();

    protected abstract View getHeadView();

    protected abstract void commit();

    protected abstract List<MultipleItem> getAdapterData();

    /**
     * ????????????
     */
    protected void setSignIv(ImageView mHandlerSignIv) {
        this.mHandlerSignIv = mHandlerSignIv;
    }


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
        ////businessId=-1??????????????????
        businessId = getIntent().getIntExtra(BUSINESS_ID, -1);
        setTitleName(getTitleName());
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
        adapter = new HandlerBusinessAdapter(getAdapterData(), businessId == -1 ? false : true);
        adapter.setOnIdCardSearchCallBack(new OnIdCardSearchCallBack() {
            @Override
            public void searchDisabledInfoByIdCard(String idNo) {
                //???????????????????????????
                mPresenter.getDisabledBaseInfo(idNo, AppHttpPath.GET_DISABLED_BASE_INFO);
            }
        });
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

                switch (view.getId()) {
                    case R.id.form_pic_src_iv:
                        BusinessPicBean businessPicBean = (BusinessPicBean) multipleItem.getObject();
                        if (BusinessContract.TABLE_TITLE_DISABLE_PIC_FRONT_SAMPLE.equals(businessPicBean.getPicName())
                                || BusinessContract.TABLE_TITLE_DISABLE_PIC_BACK_SAMPLE.equals(businessPicBean.getPicName())) {
                            //????????????????????????

                        } else {
                            choseImage(0, BaseBusinessActivity.this, 1);
                        }
                        break;
                    case R.id.form_head_pic_iv:
                        choseImage(0, BaseBusinessActivity.this, 1);
                        break;

                    case R.id.tool_pic_iv:
                        //????????????
                        selectBean = (BusinessTextValueBean) multipleItem.getObject();
                        new AlertDialog.Builder(mContext).setView(getToolInfoView(selectBean.getDataBean())).show();
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
                            case BusinessContract.TABLE_TITLE_EDUCATION_LEVEL:
                                mPresenter.getDisabledEducation(BusinessContract.TABLE_TITLE_EDUCATION_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_KINDS:
                                //????????????
                                mPresenter.getDisabledCategory(AppHttpPath.GET_DISABLED_TYPE);
                                break;
                            case BusinessContract.TABLE_TITLE_DISABILITY_LEVEL:
                                //????????????
                                mPresenter.getDisabledLevel(AppHttpPath.GET_DISABLED_LEVEL);
                                break;
                            case BusinessContract.TABLE_TITLE_HOPE_TRAIN_TYPE:
                                //??????
                                mPresenter.getTrainingIntention(AppHttpPath.GET_TRAIN_INTENT_TYPES);
                                break;
                            case BusinessContract.TABLE_TITLE_JOB_STATUS:
                                //????????????
                                List<String> jobStatus = getWorkStatus();
                                PickerManager.getInstance().showOptionPicker(mContext, jobStatus,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                jobStatusId = options1;
                                                mSelectTv.setText(jobStatus.get(options1));
                                                selectBean.setValue(jobStatus.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL:
                                //????????????
                                if (0 == categoryId) {
                                    ToastUtils.toast(mContext, "????????????????????????");
                                    return;
                                }
                                mPresenter.getDisabledAIDS(categoryId, AppHttpPath.GET_DISABLED_AIDS);
                                break;
                            case BusinessContract.TABLE_TITLE_DELIVERY_METHOD:
                                //????????????
                                List<String> methods = getDeliveryMode();
                                PickerManager.getInstance().showOptionPicker(mContext, methods,
                                        new PickerManager.OnOptionPickerSelectedListener() {
                                            @Override
                                            public void onOptionsSelect(int options1, int option2, int options3,
                                                                        View v) {
                                                methodsId = options1 + 1;
                                                mSelectTv.setText(methods.get(options1));
                                                selectBean.setValue(methods.get(options1));
                                            }
                                        });
                                break;
                            case BusinessContract.TABLE_TITLE_CHILD_IQ:
                                //???????????????
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
                                //????????????
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
                        showSignatureView();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * ???????????????view
     *
     * @param dataBean
     * @return
     */
    private View getToolInfoView(ToolInfoBean.DataBean dataBean) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tool_info_view, null);
        mToolNameTv = (TextView) view.findViewById(R.id.tool_name_tv);
        mToolPicIv = (ImageView) view.findViewById(R.id.tool_info_pic_iv);
        ImageLoadUtil.loadImageNoCache(mContext, dataBean.getImg(), mToolPicIv);
        mReserveTv = (TextView) view.findViewById(R.id.reserve_tv);
        mToolInfoDesWb = (WebView) view.findViewById(R.id.tool_info_des_wb);
        mToolNameTv.setText(dataBean.getName());
        mReserveTv.append(String.valueOf(dataBean.getInventory()));
        initWebView(mToolInfoDesWb, dataBean.getContent());
        return view;
    }

    /**
     * @param webView
     * @param urlString
     */
    private void initWebView(WebView webView, String urlString) {
        //??????WebView?????????????????????????????????????????????????????????????????????????????????WebView??????
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);// ??????????????????????????????
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDisplayZoomControls(false);//??????webview????????????
        webView.clearCache(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClientDemo());
        webView.loadDataWithBaseURL("", urlString, "text/html",
                "utf-8", null);
    }

    private class WebViewClientDemo extends WebViewClient {
        @Override
        // ???WebView???????????????????????????????????????
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * ??????????????????1<=25???2<=26-39???3<=40-54???4<=55-75???
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
     * 1????????????2??????????????????3???????????????4????????????5?????????
     *
     * @return
     */
    protected List<String> getBrainBadTypes() {
        List<String> arrays = new ArrayList<>();
        arrays.add("?????????");
        arrays.add("???????????????");
        arrays.add("????????????");
        arrays.add("?????????");
        arrays.add("?????????");
        return arrays;
    }


    /**
     * ????????????
     * 0????????????1?????????
     *
     * @return
     */
    protected List<String> getWorkStatus() {
        List<String> arrays = new ArrayList<>();
        arrays.add("?????????");
        arrays.add("?????????");
        return arrays;
    }

    /**
     * ????????????
     * 1?????????2????????????
     *
     * @return
     */
    protected List<String> getDeliveryMode() {
        List<String> arrays = new ArrayList<>();
        arrays.add("??????");
        arrays.add("????????????");
        return arrays;
    }

    /**
     * ???????????? 0?????????1??????(?????????)???2?????????3??????
     *
     * @return
     */
    protected List<String> getMarrayStatus() {
        List<String> arrays = new ArrayList<>();
        arrays.add("??????");
        arrays.add("??????(?????????)");
        arrays.add("??????");
        arrays.add("??????");
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
        if (mToolInfoDesWb != null) {
            mToolInfoDesWb.destroy();
        }
        //??????????????????
        FileCacheUtils.clearImage(getSignPath(FileCacheUtils.SIGN_PIC_NAME));
        FileCacheUtils.clearImage(getSignPath(FileCacheUtils.HEAD_PIC));
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (picSelectedCallBack != null && adapter == null) {
            picSelectedCallBack.picSelected(icons);
            return;
        }
        if (icons.size() > 0) {
            String path = icons.get(0);
            businessPicBean = (BusinessPicBean) ((MultipleItem) adapter.getData().get(currentPosition)).getObject();

            if (BusinessContract.TABLE_TITLE_PIC.equals(businessPicBean.getPicName())) {
                //??????????????????????????????
                startActivityForResult(new Intent(this, HeadCropActivity.class).putExtra(HeadCropActivity.HEAD_PIC,
                        path), BASE_REQUEST_RESULT);
            } else {
                businessPicBean.setPicPath(path);
                adapter.notifyItemChanged(currentPosition);
            }

        }
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @Override
    protected String getSignPath(String picName) {
        return super.getSignPath(picName);
    }

    /**
     * ?????????????????????
     */
    protected void showSignatureView() {

        bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.signature_view_layout, null);
        view.findViewById(R.id.signature_view_save).setOnClickListener(this);
        view.findViewById(R.id.signature_view_rewrite).setOnClickListener(this);
        view.findViewById(R.id.signature_view_cancel).setOnClickListener(this);
        //????????????
        gsv_signature = view.findViewById(R.id.gsv_signature);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BASE_REQUEST_RESULT) {
            if (data != null) {
                String path = data.getStringExtra(HeadCropActivity.CROPED_HEAD_PIC);
                if (businessPicBean != null && adapter != null) {
                    businessPicBean.setPicPath(path);
                    adapter.notifyItemChanged(currentPosition);
                }

            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signature_view_save:
                if (gsv_signature.getTouched()) {
                    try {
                        signPath = FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME;
                        //???????????????
                        gsv_signature.save(signPath);
                        Bitmap bitmap1 =
                                FileCacheUtils.getLoacalBitmap(signPath); //??????????????????(???cdcard?????????)  //
                        if (mSignIv != null) {
                            mSignIv.setImageBitmap(bitmap1); //??????Bitmap
                        }
                        if (mHandlerSignIv != null) {
                            mHandlerSignIv.setImageBitmap(bitmap1); //??????Bitmap
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
                    ToastUtils.toast(mContext, "????????????");
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
                //??????
                commit();
                break;
            default:
                break;
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
                            if (!TextUtils.isEmpty(textValueEditBean.getValue())) {
                                if (!RuleTools.isDisabledIdNO(mContext, textValueEditBean.getValue())) {
                                    ToastUtils.toast(mContext, "???????????????????????????");
                                    return null;
                                }
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
                        case BusinessContract.TABLE_TITLE_TRAIN_INTENT:
                            //????????????
                            formKey = "trainingIntention";
                            break;
                        case BusinessContract.TABLE_TITLE_WORK_AREA:
                            //????????????
                            formKey = "areaIntention";
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
                        case BusinessContract.TABLE_TITLE_SCHOOL_SYSTEM:
                            //??????
                            formKey = "system";
                            break;
                        case BusinessContract.TABLE_TITLE_HOUSE_PHONE:
                            //????????????
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "telephone";
                            break;
                        case BusinessContract.TABLE_TITLE_MOBILE_NUM:
                            //????????????
                            if (!RuleTools.isMobileNO(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "phone";
                            break;
                        case BusinessContract.TABLE_TITLE_GUARDIAN_ID_CARD:
                            //?????????????????????
                            if (!RuleTools.isIdNO(mContext, textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "???????????????????????????");
                                return null;
                            }
                            formKey = "guardianId";
                            break;
                        case BusinessContract.TABLE_TITLE_COMMUNICATION_ADDR:
                            //????????????
                            formKey = "postalAddress";
                            break;

                        default:
                            break;
                    }
                    if (StringTools.isStringValueOk(textValueEditBean.getValue()) && formKey != null) {
                        builder.addFormDataPart(formKey, textValueEditBean.getValue());
                    }

                    break;
                case MultipleItem.ITEM_BUSINESS_EDIT2:
                    BusinessTextValueBean textValueEditBean2 = (BusinessTextValueBean) array
                            .getObject();
                    if (textValueEditBean2.isImportant() && TextUtils.isEmpty(textValueEditBean2.getValue())) {
                        String key = textValueEditBean2.getKey();
                        ToastUtils.toast(mContext, "?????????" + key);
                        return null;
                    }
                    String titleKey = null;
                    switch (textValueEditBean2.getKey()) {
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_F:
                            //???????????????????????????
                            titleKey = "geneticHistoryRelationship";
                            break;
                        case BusinessContract.TABLE_TITLE_RELATION_TO_CHILD_C:
                            //?????????????????????
                            titleKey = "accompanyRelationship";
                            break;
                        case BusinessContract.TABLE_TITLE_DISCOVER_DISABILITY_YEAR:
                            //??????????????????
                            titleKey = "findTime";
                            break;

                        default:
                            break;
                    }
                    if (StringTools.isStringValueOk(textValueEditBean2.getValue()) && titleKey != null) {
                        builder.addFormDataPart(titleKey, textValueEditBean2.getValue());
                    }

                    break;
                case MultipleItem.ITEM_BUSINESS_RADIO:
                    //???????????? ?????????????????? ???????????? ???????????????????????????????????????1????????? ??????????????????????????????+1  ????????? ?????????????????????????????? ?????????-1???
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
                            builder.addFormDataPart("accountType",
                                    String.valueOf(radioBean.getDefaultSelectedIndex() + 1));
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
                        //                            //????????????
                        //                            builder.addFormDataPart("grand", String.valueOf(radioBean
                        //                            .getDefaultSelectedIndex() + 1));
                        //                            break;
                        case BusinessContract.TABLE_TITLE_IS_POOR_FAMILY:
                            builder.addFormDataPart("alleviation", String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_FAMILY_HAS_DISABILITY:
                            //????????????????????????
                            builder.addFormDataPart("geneticHistory",
                                    String.valueOf(radioBean.getDefaultSelectedIndex()));
                            break;
                        case BusinessContract.TABLE_TITLE_HAS_CARE_WORKER:
                            //????????????????????????????????????????????????
                            builder.addFormDataPart("accompany", String.valueOf(radioBean.getDefaultSelectedIndex()));
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
                        case BusinessContract.TABLE_TITLE_CHILD_IQ:
                            //???????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("iq", String.valueOf(selectedIQId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_DELIVERY_METHOD:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("deliveryWay ", String.valueOf(methodsId));
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
                        case BusinessContract.TABLE_TITLE_SELECT_ASSIST_TOOL:
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("aidsId", String.valueOf(toolId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_JOB_STATUS:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("jobSituation", String.valueOf(jobStatusId));
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_HOPE_TRAIN_TYPE:
                            //????????????
                            if (StringTools.isStringValueOk(textValueSelectBean.getValue())) {
                                builder.addFormDataPart("trains", String.valueOf(trainIntentId));
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
                            case BusinessContract.TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????????????????");
                                    return null;
                                }
                                //???????????????????????????
                                builder.addFormDataPart("riPictureFile", "riPictureFile",
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
                                builder.addFormDataPart("householdRegisterPictureFile",
                                        "householdRegisterPictureFile",
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
                            case BusinessContract.TABLE_TITLE_GUARDIAN_HUJI_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "????????????????????????????????????");
                                    return null;
                                }
                                //???????????????????????????
                                builder.addFormDataPart("guardianRegisterPictureFile", "guardianRegisterPictureFile",
                                        RequestBody.create(MediaType.parse("file"),
                                                new File(picBean.getPicPath())));
                                break;
                            case BusinessContract.TABLE_TITLE_LIFE_PIC:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "?????????????????????");
                                    return null;
                                }
                                //???????????????
                                builder.addFormDataPart("lifePictureFile", "lifePictureFile",
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
                            case BusinessContract.TABLE_TITLE_LIFE_PIC_HOUSE:
                                if (!StringTools.isStringValueOk(picBean.getPicPath())) {
                                    ToastUtils.toast(mContext, "???????????????????????????");
                                    return null;
                                }
                                //??????????????????
                                builder.addFormDataPart("housePictureFile", "housePictureFile",
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
                                    "file"), new File(getSignPath(FileCacheUtils.SIGN_PIC_NAME))));
                    break;
                case MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW:
                    RecycleBean recycleBean = (RecycleBean) array.getObject();
                    List<ItemCheckBoxBean> data = recycleBean.getData();
                    String selectedData = getSelectedItems(data);
                    ItemCheckBoxBean selectedItem = getSelectedItem(data);
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
                                builder.addFormDataPart("familyEconomy",
                                        String.valueOf(selectedItem.getIndex() + 1));
                            }

                            break;
                        case BusinessContract.TABLE_TITLE_POOR_FAMILY:
                            //????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("poorFamily", String.valueOf(selectedItem.getIndex() + 1));
                                if (4 == selectedItem.getIndex()) {
                                    //????????????  ????????????????????????
                                    builder.addFormDataPart("poorFamilyExplain", selectedItem.getDes());
                                }
                            }
                            break;
                        case BusinessContract.TABLE_TITLE_MEDICALSAFE:
                            //????????????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("medicalInsurance",
                                        String.valueOf(selectedItem.getIndex() + 1));
                            }

                            break;
                        case BusinessContract.TABLE_TITLE_CURRENT_RECOVERY:
                            //??????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("recovery", String.valueOf(selectedItem.getIndex() + 1));
                            }
                            break;

                        case BusinessContract.TABLE_TITLE_REQUEST_RECOVERY:
                            //??????????????????
                            if (StringTools.isStringValueOk(selectedData)) {
                                builder.addFormDataPart("recoveryProject",
                                        String.valueOf(selectedItem.getIndex() + 1));
                            }

                            break;
                        default:
                            break;
                    }


                    break;
                case MultipleItem.ITEM_BUSINESS_DEAF_TABLE:
                    DeafBean deafBean = (DeafBean) array.getObject();
                    if (!StringTools.isStringValueOk(deafBean.getLeftEar())) {
                        ToastUtils.toast(mContext, "????????????????????????");
                        return null;
                    } else {
                        builder.addFormDataPart("leftEar", deafBean.getLeftEar());
                    }
                    if (!StringTools.isStringValueOk(deafBean.getRightEar())) {
                        ToastUtils.toast(mContext, "????????????????????????");
                        return null;
                    } else {
                        builder.addFormDataPart("rightEar", deafBean.getRightEar());
                    }
                    int isWear = deafBean.getWear();
                    builder.addFormDataPart("wear", String.valueOf(isWear));
                    if (0 == isWear) {
                        //??????????????????
                        if (!StringTools.isStringValueOk(deafBean.getWearTimeYear())) {
                            ToastUtils.toast(mContext, "?????????????????????--???");
                            return null;
                        }
                        if (!StringTools.isStringValueOk(deafBean.getWearTimeMonth())) {
                            ToastUtils.toast(mContext, "?????????????????????--??????");
                            return null;
                        }
                        StringBuilder wearTimeSb = new StringBuilder();
                        wearTimeSb.append(deafBean.getWearTimeYear());
                        wearTimeSb.append("???");
                        wearTimeSb.append(deafBean.getWearTimeMonth());
                        wearTimeSb.append("??????");
                        builder.addFormDataPart("wearTime", wearTimeSb.toString().trim());
                        builder.addFormDataPart("wearEar", String.valueOf(deafBean.getWearEar()));
                    }
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

    /**
     * ??????????????????  ?????? ??????????????????
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
                    if (TextUtils.isEmpty(textValueEditBean.getValue())) {
                        return "";
                    }
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_STREET:
                            //??????
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_STREET_FAMILY:
                            //??????
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE_FAMILY:
                            //??????
                            sb.append(textValueEditBean.getValue());
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE:
                            //??????
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
     * ?????????????????????
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
                            //??????
                            sb.append(textValueEditBean.getValue() + ",");
                            break;
                        case BusinessContract.TABLE_TITLE_VILLAGE_PERSIONAL:
                            //??????
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
     * ?????????????????????
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
     * ????????????????????????  ??????
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
                || AppHttpPath.GET_DISABLED_AIDS.equals(tag) || AppHttpPath.GET_TRAIN_INTENT_TYPES.equals(tag)) {
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
                                        //?????????????????????????????????
                                        mPresenter.getDisabledAIDSInfo(toolId, AppHttpPath.REQUEST_TOOL_INFO);
                                        break;
                                    case AppHttpPath.GET_TRAIN_INTENT_TYPES:
                                        trainIntentId = dataBean.getId();
                                        break;
                                    default:
                                        break;
                                }

                            }
                        });
            }

        } else if (AppHttpPath.REQUEST_TOOL_INFO.equals(tag)) {
            //??????????????????
            ToolInfoBean toolInfoBean = (ToolInfoBean) o;
            if (toolInfoBean != null) {
                selectBean.setDataBean(toolInfoBean.getData());
                adapter.notifyItemChanged(currentPosition);
            }

        } else if (AppHttpPath.GET_DISABLED_BASE_INFO.equals(tag)) {
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

        } else {
            BaseResult baseResult = (BaseResult) o;
            ToastUtils.toast(mContext, baseResult.message);
            finish();
        }
    }


    /**
     * ???????????????????????????
     */
    public interface OnPicSelectedCallBack {
        void picSelected(List<String> icons);
    }


    public void setOnPicSelectedCallBack(OnPicSelectedCallBack picSelectedCallBack) {
        this.picSelectedCallBack = picSelectedCallBack;
    }


    /**
     * ????????????
     *
     * @return
     */
    private String getBirthDay() {
        String value = null;
        List<MultipleItem> arrays = adapter.getData();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {
                case MultipleItem.ITEM_BUSINESS_EDIT:
                    BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array
                            .getObject();
                    switch (textValueEditBean.getKey()) {
                        case BusinessContract.TABLE_TITLE_IDCARD:
                            //????????????
                            value = textValueEditBean.getValue();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return value;
    }


    //?????????????????????????????????????????????
    interface OnIdCardSearchCallBack {


        void searchDisabledInfoByIdCard(String idNo);
    }
}
