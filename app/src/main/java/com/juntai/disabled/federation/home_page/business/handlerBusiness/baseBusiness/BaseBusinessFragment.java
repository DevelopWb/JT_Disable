package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.app.Activity;
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
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
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
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.BaseStudentBursaryActivity;
import com.juntai.disabled.federation.utils.HawkProperty;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/19 9:36
 * @UpdateUser: 更新者
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
    private BusinessTextValueBean selectBean;
    public static String BUSINESS_ID = "businessid";
    private ItemSignBean itemSignBean = null;

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
        adapter = new HandlerBusinessAdapter(null, getBaseFragmentActivity().businessId == -1 ? false :
                true);
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
                Hawk.put(HawkProperty.BUSINESS_ITEM_POSITION, position);
                MultipleItem multipleItem = (MultipleItem) adapter.getData().get(position);
                //                mHeadIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id
                //                .form_head_pic_iv);
                //                mFormPicIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position,
                //                        R.id.form_pic_src_iv);

                switch (view.getId()) {
                    case R.id.form_pic_src_iv:
                       choseImage(0,BaseBusinessFragment.this, 1);
                        break;
                    case R.id.form_head_pic_iv:
                       choseImage(0, BaseBusinessFragment.this, 1);
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
                            case BusinessContract.TABLE_TITLE_REG_MODE:
                                List<String> regModes = getBaseFragmentActivity().getRegistMode();
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
                            case BusinessContract.TABLE_TITLE_CARD_TYPE:
                                List<String> cards = getBaseFragmentActivity().getCardTypes();
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
    }


    @Override
    public void picSelected(List<String> icons) {

    }
    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            int currentPosition = Hawk.get(HawkProperty.BUSINESS_ITEM_POSITION, 0);
            MultipleItem multipleItem = adapter.getData().get(currentPosition);
            BusinessPicBean businessPicBean =
                    (BusinessPicBean) multipleItem.getObject();
            businessPicBean.setPicPath(path);
            adapter.notifyItemChanged(currentPosition);
        }

    }
    @Override
    public void onClick(View v) {
    }


    /**
     * 获取adapter中的数据
     *
     * @return
     */
    protected StringBuilder getStringBuilderOfAdapterData() {
        List<MultipleItem> arrays = adapter.getData();
        StringBuilder sb = new StringBuilder();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {
                case MultipleItem.ITEM_BUSINESS_HEAD_PIC:
                    BusinessPicBean headPicBean = (BusinessPicBean) array.getObject();
                    sb.append(headPicBean.getPicName());
                    sb.append(".....\n");
                    sb.append(headPicBean.getPicPath());
                    sb.append(".....\n");
                    if (TextUtils.isEmpty(headPicBean.getPicPath())) {
                        ToastUtils.toast(mContext, "请选择申请人照片");
                        return null;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_EDIT:
                    BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array.getObject();
                    sb.append(textValueEditBean.getKey());
                    sb.append(".....\n");
                    sb.append(textValueEditBean.getValue());
                    sb.append(".....\n");
                    if (TextUtils.isEmpty(textValueEditBean.getValue())) {
                        ToastUtils.toast(mContext, "请输入你的" + textValueEditBean.getKey());
                        return null;
                    }
                    break;
                case MultipleItem.ITEM_BUSINESS_PIC:
                    BusinessPicBean picBean = (BusinessPicBean) array.getObject();
                    sb.append(picBean.getPicName());
                    sb.append(".....\n");
                    sb.append(picBean.getPicPath());
                    if (TextUtils.isEmpty(picBean.getPicPath())) {
                        ToastUtils.toast(mContext, "请上传资料图片");
                        return null;
                    }
                    break;
                default:
                    break;
            }
        }
        return sb;
    }

    @Override
    protected void lazyLoad() {
        adapter.setNewData(getAdapterData());
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

        }

    }
}
