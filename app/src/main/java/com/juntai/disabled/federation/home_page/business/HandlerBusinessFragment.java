package com.juntai.disabled.federation.home_page.business;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseLazyFragment;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MyMenuBean;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.home_page.business.business_common.BusinessItemAdapter;
import com.juntai.disabled.federation.home_page.business.disableCard.HandlerDisableCardActivity;
import com.juntai.disabled.federation.home_page.business.more_business.MoreBusinessActivity;
import com.juntai.disabled.federation.home_page.business.transact_business.TransactBusinessActivity;
import com.juntai.disabled.federation.home_page.call_to_police.VerifiedActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.orhanobut.hawk.Hawk;
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


                startActivity(new Intent(mContext, HandlerDisableCardActivity.class));
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
