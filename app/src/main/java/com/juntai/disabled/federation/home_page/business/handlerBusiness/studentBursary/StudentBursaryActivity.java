package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.home_page.business.BusinessContract;
import com.juntai.disabled.federation.home_page.business.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.ChildRecoveryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * @aouther tobato  残疾大学生助学金业务
 * @description 描述
 * @date 2021/1/23 16:22
 */
public class StudentBursaryActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView {

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
        setTitleName("残疾学生助学金业务列表");
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
                switch (position) {
                    case 0:
                        break;
                    case 1:
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
        arrays.add("残疾人大学生");
        arrays.add("重度残疾人家庭大学生");
        return arrays;
    }

    @Override
    public void initData() {

    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
