package com.juntai.disabled.federation.home_page.collectInfos;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.search.BaseSearchFragment;
import com.juntai.disabled.federation.base.search.SearchContract;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.collect.CollectSearchResultBean;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  无障碍采集  搜索
 * @CreateDate: 2021/3/17 16:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/17 16:46
 */
public class DisabledPeopleInfoTakeFragment extends BaseSearchFragment implements CollectInfoContract.TakeInfoView,
        SelectPhotosFragment.OnPhotoItemClick {


    private DisabledPeoplesAdapter adapter;

    @Override
    protected BaseQuickAdapter getSearchResultAdapter() {
        adapter = new DisabledPeoplesAdapter(R.layout.disabled_peoples_item);

        return adapter;
    }



    @Override
    protected void requestData() {
        currentPage = 1;
        mPresenter.collectDisabledSearch(getRequestBody(), AppHttpPath.COLLECT_DISABLED_SEARCH);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.COLLECT_DISABLED_SEARCH:
                CollectSearchResultBean searchResultBean = (CollectSearchResultBean) o;
                if (searchResultBean != null) {
                    CollectSearchResultBean.DataBean dataBean = searchResultBean.getData();
                    List<CollectSearchResultBean.DataBean.DatasBean> arrays = dataBean.getDatas();
                    if (arrays != null) {
                        if (currentPage == 1) {
                            adapter.setNewData(arrays);
                        } else {
                            adapter.addData(arrays);
                        }
                        if (arrays.size() < limit) {
                            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                        } else {
                            mSmartrefreshlayout.setNoMoreData(false);
                        }
                        searchedAmount.setText(String.format("%s%s%s%s%s","当前",adapter.getData().size(),"条数据,共",
                                dataBean.getTotal(),"条数据"));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        mFilterIv.setVisibility(View.GONE);
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            mPresenter.collectDisabledSearch(getRequestBody(), AppHttpPath.COLLECT_DISABLED_SEARCH);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            mPresenter.collectDisabledSearch(getRequestBody(), AppHttpPath.COLLECT_DISABLED_SEARCH);
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectSearchResultBean.DataBean.DatasBean datasBean = (CollectSearchResultBean.DataBean.DatasBean) adapter.getData().get(position);
                startActivity(new Intent(mContext, DisabledDetailActivity.class)
                        .putExtra(DisabledDetailActivity.DISABLED_ID_NUM,datasBean.getIdno())
                        .putExtra(DisabledDetailActivity.DISABLED_ID,datasBean.getId())
                .putExtra(DisabledDetailActivity.DISABLED_NAME,datasBean.getName()));
            }
        });
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }
}
