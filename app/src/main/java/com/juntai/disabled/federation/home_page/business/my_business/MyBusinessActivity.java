package com.juntai.disabled.federation.home_page.business.my_business;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  我的业务
 * @date 2020/5/22 10:13
 */
public class MyBusinessActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView,
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
                ToastUtils.toast(mContext,baseResult.message);
                getTitleRightTv().setText("编辑");
                adapter.setEdit(false);
                mDeleteTv.setVisibility(View.GONE);
                break;
            default:
                MyBusinessBean myBusinessBean = (MyBusinessBean) o;
                if (currentPage == 1) {
                    adapter.getData().clear();
                }
                if (myBusinessBean.getData().getDatas().size() < limit) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                adapter.addData(myBusinessBean.getData().getDatas());
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
                List<Integer> ids =   getSelectedBusiness();
                if (ids.isEmpty()) {
                    ToastUtils.toast(mContext,"请选择要删除的业务");
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
