package com.juntai.disabled.federation.home_page.business.my_business;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.assistTool.AssistToolDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.DisabledCardLevelChangeDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.DisabledCardLogoutDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.DisabledCardMoveInDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.DisabledCardMoveOutDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.DisabledCardReissueDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.employmentRegist.EmploymentRegistDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.handlercard.HandlerCardDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.RenewalDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail.BrainPalsyRecoveryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail.DeafDumbChildRecoveryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail.LonelyChildRecoveryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail.MoronRecoveryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.BaseRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.homecare.HomeCareDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.detail.DisabledFamilyStudentBursaryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.detail.DisabledStudentBursaryDetailActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.trainRequest.TrainRequestDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  我的业务
 * @date 2020/5/22 10:13
 */
public class MyBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.BaseIBusinessView,
        View.OnClickListener {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private MyBusinessAdapter adapter;
    private int currentPage = 1;//当前页数
    private int limit = 15;//默认一次请求15条记录
    private TextView mDeleteTv;

    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.my_business_layout;
    }

    @Override
    public void initView() {
        setTitleName("我的业务");
        getTitleRightTv().setText("编辑");
        mDeleteTv = findViewById(R.id.delete_tv);
        mDeleteTv.setOnClickListener(this);
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("编辑".equals(getTextViewValue(getTitleRightTv()))) {
                    getTitleRightTv().setText("完成");
                    //进入编辑状态
                    adapter.setEdit(true);
                    mDeleteTv.setVisibility(View.VISIBLE);
                } else {
                    getTitleRightTv().setText("编辑");
                    adapter.setEdit(false);
                    mDeleteTv.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        adapter = new MyBusinessAdapter(R.layout.item_mybusiness);
        adapter.setEmptyView(getAdapterEmptyView("一条业务记录都没有", -1));
        initRecyclerview(mRecyclerview, adapter, LinearLayout.VERTICAL);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage = 1;
                initData();
            }
        });
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                currentPage++;
                initData();
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyBusinessBean.DataBean.DatasBean bean =
                        (MyBusinessBean.DataBean.DatasBean) adapter.getData().get(position);
                int matterId = bean.getMatterId();
                int businessId = bean.getBusinessId();
                int checkStatus = bean.getStatusX();
                int businessItemId = bean.getId();
                String name = bean.getMatterName();
                Intent intent = new Intent();
                intent.putExtra(BaseBusinessActivity.BUSINESS_ITEM_ID, businessItemId)
                        .putExtra(BaseRecoveryActivity.RECOVERY_NAME, name)
                        .putExtra(BaseRecoveryActivity.CHECK_STATUS, checkStatus)
                        .putExtra(BaseBusinessActivity.BUSINESS_ID, businessId);
                switch (matterId) {
                    case 1:
                        //残疾人证业务详情
                        intent.setClass(mContext, HandlerCardDetailActivity.class);
                        break;
                    case 2:
                        //期满换证
                        intent.setClass(mContext, RenewalDetailActivity.class);
                        break;
                    case 3:
                        //等级变更
                        intent.setClass(mContext, DisabledCardLevelChangeDetailActivity.class);
                        break;
                    case 4:
                        //补办
                        intent.setClass(mContext, DisabledCardReissueDetailActivity.class);
                        break;
                    case 5:
                        //迁入
                        intent.setClass(mContext, DisabledCardMoveInDetailActivity.class);
                        break;
                    case 6:
                        //迁出
                        intent.setClass(mContext, DisabledCardMoveOutDetailActivity.class);
                        break;
                    case 7:
                        //注销
                        intent.setClass(mContext, DisabledCardLogoutDetailActivity.class);
                        break;
                    case 8:
                        //就业登记
                        intent.setClass(mContext, EmploymentRegistDetailActivity.class);
                        break;
                    case 12:
                        //辅助用品申请
                        intent.setClass(mContext, AssistToolDetailActivity.class);
                        break;
                    case 13:
                        //培训申请
                        intent.setClass(mContext, TrainRequestDetailActivity.class);
                        break;
                    case 14:
                        //居家托养
                        intent.setClass(mContext, HomeCareDetailActivity.class);
                        break;
                    case 16:
                        //精准康复智力残疾儿童康复救助
                        intent.setClass(mContext, MoronRecoveryDetailActivity.class);
                        break;
                    case 17:
                        //精准康复孤独儿童康复救助
                        intent.setClass(mContext, LonelyChildRecoveryDetailActivity.class);
                        break;
                    case 18:
                        //精准康复聋儿童康复救助
                        intent.setClass(mContext, DeafDumbChildRecoveryDetailActivity.class);
                        break;
                    case 19:
                        //精准康复孤独儿童康复救助
                        intent.setClass(mContext, BrainPalsyRecoveryDetailActivity.class);
                        break;
                    case 20:
                        //残疾人大学生助学金
                        intent.setClass(mContext, DisabledStudentBursaryDetailActivity.class);
                        break;
                    case 21:
                        //重残家庭大学生助学金
                        intent.setClass(mContext, DisabledFamilyStudentBursaryDetailActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);

            }
        });


    }


    @Override
    public void initData() {
        mPresenter.businessProgress(mPresenter.getPublishMultipartBody().addFormDataPart("pageSize", limit + "").addFormDataPart("currentPage", currentPage + "").build(), "");
    }


    @Override
    public void onSuccess(String tag, Object o) {

        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        switch (tag) {
            case AppHttpPath.DELETE_MY_BUSINESS:
                BaseResult baseResult = (BaseResult) o;
                ToastUtils.toast(mContext, baseResult.message);
                List<MyBusinessBean.DataBean.DatasBean> arrays = adapter.getData();
                Iterator iterator = arrays.iterator();
                while (iterator.hasNext()) {
                    MyBusinessBean.DataBean.DatasBean datasBean = (MyBusinessBean.DataBean.DatasBean) iterator.next();
                    if (datasBean.isChecked()) {
                        iterator.remove();
                    }
                }
                if (arrays.size()==0) {
                    getTitleRightTv().setText("");
                }else {
                    getTitleRightTv().setText("编辑");
                }

                adapter.setEdit(false);
                adapter.setNewData(arrays);
                mDeleteTv.setVisibility(View.GONE);
                break;
            default:
                MyBusinessBean myBusinessBean = (MyBusinessBean) o;
                List<MyBusinessBean.DataBean.DatasBean> data = myBusinessBean.getData().getDatas();
                if (currentPage == 1) {
                    adapter.setNewData(null);
                    if (data.size() == 0) {
                        getTitleRightTv().setText("");
                    }
                }
                if (myBusinessBean.getData().getDatas().size() < limit) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }

                adapter.addData(data);
                break;
        }

    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag, o);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_tv:
                //删除 我的业务
                List<Integer> ids = getSelectedBusiness();
                if (ids.isEmpty()) {
                    ToastUtils.toast(mContext, "请选择要删除的业务");
                    return;
                }
                mPresenter.deleteUserBusiness(ids, AppHttpPath.DELETE_MY_BUSINESS);
                break;
            default:
                break;
        }
    }

    /**
     * 获取选中的业务
     *
     * @return
     */
    private List<Integer> getSelectedBusiness() {
        List<Integer> ids = new ArrayList<>();
        List<MyBusinessBean.DataBean.DatasBean> arrays = adapter.getData();
        for (MyBusinessBean.DataBean.DatasBean array : arrays) {
            if (array.isChecked()) {
                ids.add(array.getId());
            }
        }
        return ids;
    }
}
