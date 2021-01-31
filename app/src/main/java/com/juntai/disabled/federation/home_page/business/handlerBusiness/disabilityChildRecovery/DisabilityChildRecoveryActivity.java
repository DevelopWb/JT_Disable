package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 残疾儿童抢救性康复项目
 * @date 2021/1/23 9:13
 */
public class DisabilityChildRecoveryActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private ChildRecoveryAdapter adapter;

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
        setTitleName("残疾儿童抢救性康复项目");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableRefresh(false);
        mSmartrefreshlayout.setEnableLoadMore(false);
        adapter = new ChildRecoveryAdapter(R.layout.item_child_recovery);
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        adapter.setNewData(getAdapterData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = (String) adapter.getData().get(position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, MoronRecoveryActivity.class).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, LonelyChildRecoveryActivity.class).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 2:
                        //聋哑儿童  deaf dumb
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<String> getAdapterData() {
        List<String> arrays = new ArrayList<>();
        arrays.add("精准康复智力残疾儿童康复救助");
        arrays.add("精准康复孤独症儿童康复救助");
        arrays.add("精准康复聋儿康复救助");
        arrays.add("精准康复脑瘫儿童康复救助");
        return arrays;
    }

    @Override
    public void initData() {

    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
