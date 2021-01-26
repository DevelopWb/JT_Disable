package com.juntai.disabled.federation.home_page.business;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.federation.R;
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
        adapter = new BusinessItemAdapter(R.layout.business_item);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(adapter);
        mSmartrefreshlayout = (SmartRefreshLayout) getView(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
    }

    @Override
    protected void initData() {
        adapter.setNewData(mPresenter.getBusinessList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, HandlerCardActivity.class));
                        break;
                    case 1:
                        //期满换证
                        startActivity(new Intent(mContext, RenewalActivity.class));
                        break;
                    case 2:
                        //期满换证
                        startActivity(new Intent(mContext, CardLevelChangeActivity.class));
                        break;
                    case 3:
                        //期满换证
                        startActivity(new Intent(mContext, CardReissueActivity.class));
                        break;
                    case 4:
                        //期满换证
                        startActivity(new Intent(mContext, CardMoveInActivity.class));
                        break;
                    case 5:
                        //期满换证
                        startActivity(new Intent(mContext, CardMoveOutActivity.class));
                        break;
                    case 6:
                        //期满换证
                        startActivity(new Intent(mContext, CardLogoutActivity.class));
                        break;
                    case 7:
                        //就业登记
                        startActivity(new Intent(mContext, EmploymentRegistActivity.class));
                        break;
                    case 8:
                        //就业登记
                        startActivity(new Intent(mContext, DisabilityChildRecoveryActivity.class));
                        break;
                    case 9:
                        //就业登记
                        startActivity(new Intent(mContext, StudentBursaryActivity.class));
                        break;
                    default:
                        break;
                }

            }
        });
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

    }
}
