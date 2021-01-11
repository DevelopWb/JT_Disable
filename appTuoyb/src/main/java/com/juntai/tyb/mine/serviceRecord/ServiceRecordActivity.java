package com.juntai.tyb.mine.serviceRecord;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.bean.ServiceRecordBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.careTaker.careInfo.CareTakerInfoActivity;
import com.juntai.tyb.mine.MineContract;
import com.juntai.tyb.mine.MinePresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  我的服务记录
 * @date 2020/7/16 17:56
 */
public class ServiceRecordActivity extends BaseAppActivity<MinePresent> implements MineContract.IMineView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private ServiceRecordAdapter mRecordAdapter;
    protected int currentPage = 1;
    protected int limit = 10;

    @Override
    protected MinePresent createPresenter() {
        return new MinePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_service_record;
    }

    @Override
    public void initView() {
        setTitleName("服务记录");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mRecordAdapter = new ServiceRecordAdapter(R.layout.care_item_layout);
        mRecordAdapter.setEmptyView(getAdapterEmptyView(getString(R.string.none_record),R.mipmap.none_record));
        initRecyclerview(mRecyclerview, mRecordAdapter, LinearLayoutManager.VERTICAL);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                currentPage++;
                mPresenter.getServiceRecord(getPublishMultipartBody().addFormDataPart("page", String.valueOf(currentPage))
                        .addFormDataPart("limit", String.valueOf(limit)).build(), MineContract.SERVICE_RECORD);
            }
        });
        mRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id = ((ServiceRecordBean.DataBean)adapter.getData().get(position)).getId();
                startActivity(new Intent(mContext, CareTakerInfoActivity.class).putExtra(CareTakerInfoActivity.CARE_TAKER_ID,id));
            }
        });
        mRecordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceRecordBean.DataBean dataBean = (ServiceRecordBean.DataBean) adapter.getData().get(position);
                navigationLogic(new LatLng(dataBean.getLatitude(),dataBean.getLongitude()),dataBean.getPlace());
            }
        });
    }

    @Override
    public void initData() {
        currentPage = 1;
        mPresenter.getServiceRecord(getPublishMultipartBody().addFormDataPart("page", String.valueOf(currentPage))
                .addFormDataPart("limit", String.valueOf(limit)).build(), MineContract.SERVICE_RECORD);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        ServiceRecordBean serviceRecordBean = (ServiceRecordBean) o;
        if (serviceRecordBean != null) {
            List<ServiceRecordBean.DataBean> recordList = serviceRecordBean.getData();
            if (recordList != null) {
                if (currentPage == 1) {
                    mRecordAdapter.setNewData(null);
                }
                if (recordList.size() < limit) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                mRecordAdapter.addData(recordList);
            }
        }
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag, o);
    }
}
