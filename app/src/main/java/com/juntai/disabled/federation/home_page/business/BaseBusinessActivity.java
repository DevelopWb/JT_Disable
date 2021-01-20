package com.juntai.disabled.federation.home_page.business;

import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.customview.GestureSignatureView;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HandlerDisableCardAdapter;
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
public abstract class BaseBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView, View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerDisableCardAdapter adapter;
    private ImageView mHeadIv;
    private ImageView mFormPicIv;
    private int currentPosition;
    private GestureSignatureView gsv_signature;
    private BottomSheetDialog bottomSheetDialog;
    private String signPath;

    protected abstract String getTitleName();

    protected abstract View getFootView();

    protected abstract List<MultipleItem> getAdapterData();
    /**
     *
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
        setTitleName(getTitleName());
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
        adapter = new HandlerDisableCardAdapter(getAdapterData());
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        if (getFootView() != null) {
            adapter.setFooterView(getFootView());
        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
//                mHeadIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position, R.id.form_head_pic_iv);
//                mFormPicIv = (ImageView) adapter.getViewByPosition(mRecyclerview, position,
//                        R.id.form_pic_src_iv);
//                TextView selectTv = (TextView) adapter.getViewByPosition(mRecyclerview, position, R.id.select_value_tv);
                switch (view.getId()) {
                    case R.id.form_pic_src_iv:
                        choseImage(0, BaseBusinessActivity.this, 1);
                        break;
                    case R.id.form_head_pic_iv:
                        choseImage(0, BaseBusinessActivity.this, 1);
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
    protected void onDestroy() {
        super.onDestroy();
        adapter.removeAllFooterView();
        if (bottomSheetDialog != null) {
            if (bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }
            bottomSheetDialog=null;
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
     * @return
     */
    protected String getSignPath(){
        File file = new File(FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME);
        return file.exists()?FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME:null;
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
                    ToastUtils.toast(mContext,"请签名！");
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
                        ToastUtils.toast(mContext, "请输入你的"+textValueEditBean.getKey());
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
}
