package com.juntai.disabled.federation.home_page.business;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HandlerDisableCardAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/19 9:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/19 9:36
 */
public abstract class BaseHandlerBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView, View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected HandlerDisableCardAdapter adapter;
    private ImageView mHeadIv;
    private ImageView mFormPicIv;
    private int currentPosition;

    protected abstract String getTitleName();

    protected abstract View getFootView();

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
                        choseImage(0, BaseHandlerBusinessActivity.this, 1);
                        break;
                    case R.id.form_head_pic_iv:
                        choseImage(0, BaseHandlerBusinessActivity.this, 1);
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
}
