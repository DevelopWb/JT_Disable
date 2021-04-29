package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.business.ChildBusinessesBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.BaseRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.ChildRecoveryAdapter;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.DisabilityChildRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilityFamilyStudent.DisabilityFamilyStudentActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.DisabilityStudentBursaryActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChildBusinessesBean.DataBean bean = (ChildBusinessesBean.DataBean) adapter.getData().get(position);
                String name = bean.getName();
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, DisabilityStudentBursaryActivity.class).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, DisabilityFamilyStudentActivity.class).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void initData() {
        if (getIntent() != null) {
            int matterId = getIntent().getIntExtra(DisabilityChildRecoveryActivity.BUSINESS_ID,0);
            mPresenter.getChildBusinesses(matterId,null);
        }

    }


    @Override
    public void onSuccess(String tag, Object o) {
        ChildBusinessesBean childBusinessesBean = (ChildBusinessesBean) o;
        if (childBusinessesBean != null) {
            List<ChildBusinessesBean.DataBean> arrays =   childBusinessesBean.getData();
            adapter.setNewData(arrays);
        }
    }
}
