package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.customview.GestureSignatureView;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.BaseStudentBursaryActivity;
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
public abstract class BaseBusinessFragment extends BaseMvpFragment<BusinessPresent> implements BusinessContract.IBusinessView,
        View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerBusinessAdapter adapter;
    private GestureSignatureView gsv_signature;
    private BottomSheetDialog bottomSheetDialog;
    private String signPath;


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
        adapter = new HandlerBusinessAdapter(getAdapterData(),((BaseStudentBursaryActivity)getActivity()).businessId==-1?false:true);
        getBaseActivity().initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        if (getFootView() != null) {
            adapter.setFooterView(getFootView());
        }
        if (getHeadView() != null) {
            adapter.setHeaderView(getHeadView());
        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //                mHeadIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id
                //                .form_head_pic_iv);
                //                mFormPicIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position,
                //                        R.id.form_pic_src_iv);
                //                TextView selectTv = (TextView) adapter.getViewByPosition(mRecyclerview, position, R
                //                .id.select_value_tv);
                switch (view.getId()) {
                    case R.id.form_pic_src_iv:
                        ((BaseAppActivity)getActivity()).choseImage(0, BaseBusinessFragment.this, 1);
                        break;
                    case R.id.form_head_pic_iv:
                        ((BaseAppActivity)getActivity()).choseImage(0, BaseBusinessFragment.this, 1);
                        break;
                    case R.id.select_value_tv:
                        ToastUtils.toast(mContext, "select");
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    protected void initData() {

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

//    @Override
//    protected void selectedPicsAndEmpressed(List<String> icons) {
//        if (icons.size() > 0) {
//            String path = icons.get(0);
//            BusinessPicBean businessPicBean =
//                    (BusinessPicBean) ((MultipleItem) adapter.getData().get(currentPosition)).getObject();
//            businessPicBean.setPicPath(path);
//            adapter.notifyItemChanged(currentPosition);
//        }
//    }



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

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
