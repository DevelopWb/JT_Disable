package com.juntai.disabled.federation.home_page.business.business_common;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MyMenuBean;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.home_page.business.BusinessContract;
import com.juntai.disabled.federation.home_page.business.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.disableCard.HandlerDisableCardActivity;
import com.juntai.disabled.federation.home_page.business.more_business.MoreBusinessActivity;
import com.juntai.disabled.federation.home_page.business.transact_business.TransactBusinessActivity;
import com.juntai.disabled.federation.home_page.call_to_police.VerifiedActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @aouther tobato
 * @description 描述  业务办理  常用
 * @date 2020/5/20 9:21
 */
public class BusinessActivity extends BaseMvpActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private BusinessItemAdapter adapter;
    private SmartRefreshLayout mSmartrefreshlayout;

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
        setTitleName("业务办理");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new BusinessItemAdapter(R.layout.business_item);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(adapter);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
    }

    @Override
    public void initData() {
        adapter.setNewData(mPresenter.getBusinessList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, HandlerDisableCardActivity.class));
            }
        });
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
