package com.juntai.tyb.homePage.search;


import android.content.Intent;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.tyb.base.BaseSearchFragment;
import com.juntai.tyb.bean.careTaker.SearchedPeopleBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.careTaker.CareContract;
import com.juntai.tyb.homePage.careTaker.careInfo.CareTakerInfoActivity;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述   搜索控件
 * @CreateDate: 2020/3/15 9:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/15 9:18
 */
public class SearchFragment extends BaseSearchFragment implements View.OnClickListener,
        CareContract.ICareView {


    private SearchedAdapter mSearchedAdapter;

    @Override
    protected void initData() {
        mSearchedAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_record),
                R.mipmap.none_record));
        setSearchType(1);
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            mPresenter.searchAllCareTakers(getRequestBody(), SearchContract.ALL_CARE_TAKER);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            mPresenter.searchAllCareTakers(getRequestBody(), SearchContract.ALL_CARE_TAKER);
        });

        mSearchedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id = ((SearchedPeopleBean.DataBean.DatasBean) adapter.getData().get(position)).getId();
                startActivity(new Intent(mContext, CareTakerInfoActivity.class).putExtra(CareTakerInfoActivity.CARE_TAKER_ID, id));
            }
        });

        mSearchedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SearchedPeopleBean.DataBean.DatasBean datasBean =
                        (SearchedPeopleBean.DataBean.DatasBean) adapter.getData().get(position);
                getBaseAppActivity().navigationLogic(new LatLng(datasBean.getLatitude(), datasBean.getLongitude()),
                        datasBean.getPlace());
            }
        });

        requestData();
    }


    @Override
    protected BaseQuickAdapter getSearchResultAdapter() {
        mSearchedAdapter = new SearchedAdapter(R.layout.care_item_layout);
        mSearchedAdapter.addFooterView(getFootView());
        return mSearchedAdapter;
    }

    @Override
    protected void requestData() {
        currentPage = 1;
        mPresenter.searchAllCareTakers(getRequestBody(), SearchContract.ALL_CARE_TAKER);
    }



    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case SearchContract.ALL_CARE_TAKER:

                SearchedPeopleBean searchedPeopleBean = (SearchedPeopleBean) o;
                SearchedPeopleBean.DataBean dataBean = searchedPeopleBean.getData();
                List<SearchedPeopleBean.DataBean.DatasBean> arrays = dataBean.getDatas();
                if (arrays != null) {
                    if (currentPage == 1) {
                        mSearchedAdapter.setNewData(arrays);
                    } else {
                        mSearchedAdapter.addData(arrays);
                    }
                    if (arrays.size() < limit) {
                        mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                    } else {
                        mSmartrefreshlayout.setNoMoreData(false);
                    }
                    searchedAmount.setText(String.format("%s%s%s%s%s","当前",mSearchedAdapter.getData().size(),"条数据,共",
                            dataBean.getTotal(),"条数据"));
                }

                break;
            default:
                break;
        }

        super.onSuccess(tag, o);
    }
}
