package com.juntai.disabled.federation.home_page.business;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.AssistToolsRequestActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardLevelChangeActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardLogoutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardMoveInActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardMoveOutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardReissueActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.EmploymentRegistActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HandlerCardActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.RenewalActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.DisabilityChildRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.StudentBursaryActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  业务办理
 * @date 2021/1/8 17:18
 */
public class HandlerBusinessFragment extends BaseMvpFragment<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private BusinessItemAdapter adapter;
    private SmartRefreshLayout mSmartrefreshlayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_handle_business;
    }

    @Override
    protected void initView() {
        TextView mTitleTv = (TextView) getView(R.id.status_top_title);
        mTitleTv.setText("业务办理");
        mRecyclerview = (RecyclerView) getView(R.id.recyclerview);
        adapter = new BusinessItemAdapter(null);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(adapter);
        mSmartrefreshlayout = (SmartRefreshLayout) getView(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
    }

    @Override
    protected void initData() {
        mPresenter.getAllBusinesses(null);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        AllBusinessBean allBusinessBean = (AllBusinessBean) o;
        if (allBusinessBean != null) {
           List<AllBusinessBean.DataBean> data =  allBusinessBean.getData();
            List<MultipleItem> arrays = new ArrayList<>();
            for (AllBusinessBean.DataBean datum : data) {
               arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_TITILE_BIG,datum.getTypeName()));
               arrays.add(new MultipleItem(MultipleItem.ITEM_BUSINESS_LIST,datum.getWorkMatterList()));
            }
            adapter.setNewData(arrays);
        }

    }
}
