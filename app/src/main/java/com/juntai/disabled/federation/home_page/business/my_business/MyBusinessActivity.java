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
 * @description ??????  ????????????
 * @date 2020/5/22 10:13
 */
public class MyBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView,
        View.OnClickListener {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private MyBusinessAdapter adapter;
    private int currentPage = 1;//????????????
    private int limit = 15;//??????????????????15?????????
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
        setTitleName("????????????");
        getTitleRightTv().setText("??????");
        mDeleteTv = findViewById(R.id.delete_tv);
        mDeleteTv.setOnClickListener(this);
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("??????".equals(getTextViewValue(getTitleRightTv()))) {
                    getTitleRightTv().setText("??????");
                    //??????????????????
                    adapter.setEdit(true);
                    mDeleteTv.setVisibility(View.VISIBLE);
                } else {
                    getTitleRightTv().setText("??????");
                    adapter.setEdit(false);
                    mDeleteTv.setVisibility(View.GONE);
                }
            }
        });
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        adapter = new MyBusinessAdapter(R.layout.item_mybusiness);
        adapter.setEmptyView(getAdapterEmptyView("???????????????????????????", -1));
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
                String name = bean.getMatterName();
                switch (matterId) {
                    case 1:
                        //????????????????????????
                        startActivity(new Intent(mContext, HandlerCardDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID, businessId));
                        break;
                    case 2:
                        //????????????
                        startActivity(new Intent(mContext, RenewalDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID, businessId));
                        break;
                    case 3:
                        //????????????
                        startActivity(new Intent(mContext, DisabledCardLevelChangeDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 4:
                        //??????
                        startActivity(new Intent(mContext, DisabledCardReissueDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 5:
                        //??????
                        startActivity(new Intent(mContext, DisabledCardMoveInDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 6:
                        //??????
                        startActivity(new Intent(mContext, DisabledCardMoveOutDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 7:
                        //??????
                        startActivity(new Intent(mContext, DisabledCardLogoutDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 8:
                        //????????????
                        startActivity(new Intent(mContext, EmploymentRegistDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 12:
                        //??????????????????
                        startActivity(new Intent(mContext, AssistToolDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 13:
                        //????????????
                        startActivity(new Intent(mContext, TrainRequestDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 14:
                        //????????????
                        startActivity(new Intent(mContext, HomeCareDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId));
                        break;
                    case 16:
                        //??????????????????????????????????????????
                        startActivity(new Intent(mContext, MoronRecoveryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 17:
                        //????????????????????????????????????
                        startActivity(new Intent(mContext, LonelyChildRecoveryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 18:
                        //?????????????????????????????????
                        startActivity(new Intent(mContext, DeafDumbChildRecoveryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 19:
                        //????????????????????????????????????
                        startActivity(new Intent(mContext, BrainPalsyRecoveryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 20:
                        //???????????????????????????
                        startActivity(new Intent(mContext, DisabledStudentBursaryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    case 21:
                        //??????????????????????????????
                        startActivity(new Intent(mContext, DisabledFamilyStudentBursaryDetailActivity.class).putExtra(BaseBusinessActivity.BUSINESS_ID,
                                businessId).putExtra(BaseRecoveryActivity.RECOVERY_NAME, name));
                        break;
                    default:
                        break;
                }


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
                    getTitleRightTv().setText("??????");
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
                //?????? ????????????
                List<Integer> ids = getSelectedBusiness();
                if (ids.isEmpty()) {
                    ToastUtils.toast(mContext, "???????????????????????????");
                    return;
                }
                mPresenter.deleteUserBusiness(ids, AppHttpPath.DELETE_MY_BUSINESS);
                break;
            default:
                break;
        }
    }

    /**
     * ?????????????????????
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
