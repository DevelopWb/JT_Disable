package com.juntai.tyb.homePage.careTaker.addCareTaker;


import android.content.Intent;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.CalendarUtil;
import com.juntai.tyb.bean.careTaker.SearchedPeopleBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.base.BaseSearchFragment;
import com.juntai.tyb.homePage.search.SearchContract;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  查找托养人 所有的残疾人
 * @date 2020/7/6 16:08
 */
public class SearchCareTakerFragment extends BaseSearchFragment implements
        View.OnClickListener {


    private SearchedCareTakerAdapter mSearchResultAdapter;


    @Override
    protected void initData() {
        mSearchResultAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_record),
                R.mipmap.none_record));
        setSearchType(0);
        getBaseActivity().setMargin(mSearchTopCl, 0, 35, 0, 0);
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            mPresenter.searchAllDisabledPeoples(getRequestBody(), SearchContract.ALL_DISABLED_PEOPLE);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            mPresenter.searchAllDisabledPeoples(getRequestBody(), SearchContract.ALL_DISABLED_PEOPLE);
        });

        mSearchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchedPeopleBean.DataBean.DatasBean datasBean =
                        (SearchedPeopleBean.DataBean.DatasBean) adapter.getData().get(position);
                if (CalendarUtil.isCareble(Integer.parseInt(datasBean.getYear()))) {

                    if (0 != datasBean.getStatusX()) {
                        startActivityForResult(new Intent(mContext, AddCareTakerActivity.class).putExtra(AddCareTakerActivity.CARE_TAKER_ID_NO
                                , datasBean.getIdNo()).putExtra(
                                AddCareTakerActivity.CARE_TAKER_YEAR, datasBean.getYear()
                        ).putExtra(AddCareTakerActivity.CARE_TAKER_TYPE, 0), AddCareTakerActivity.ADD_CARE_TAKER_TAG);
                    }
                }
            }
        });
        requestData();
    }


    @Override
    protected BaseQuickAdapter getSearchResultAdapter() {
        mSearchResultAdapter = new SearchedCareTakerAdapter(R.layout.care_item_layout);
        mSearchResultAdapter.addFooterView(getFootView());
        return mSearchResultAdapter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void requestData() {
        currentPage = 1;
        mPresenter.searchAllDisabledPeoples(getRequestBody(), SearchContract.ALL_DISABLED_PEOPLE);
    }

    @Override
    public void onSuccess(String tag, Object o) {

        switch (tag) {
            case SearchContract.ALL_DISABLED_PEOPLE:

                SearchedPeopleBean searchedPeopleBean = (SearchedPeopleBean) o;
                SearchedPeopleBean.DataBean dataBean = searchedPeopleBean.getData();
                List<SearchedPeopleBean.DataBean.DatasBean> arrays = dataBean.getDatas();
                if (arrays != null) {
                    if (currentPage == 1) {
                        mSearchResultAdapter.setNewData(arrays);
                    } else {
                        mSearchResultAdapter.addData(arrays);
                    }
                    if (arrays.size() < limit) {
                        mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                    } else {
                        mSmartrefreshlayout.setNoMoreData(false);
                    }
                    searchedAmount.setText(String.format("%s%s%s%s%s", "当前", mSearchResultAdapter.getData().size(),
                            "条数据,共",
                            dataBean.getTotal(), "条数据"));
                }


                break;
            default:
                break;
        }

        super.onSuccess(tag, o);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getBaseActivity().getViewFocus(mRootView);
        if (AddCareTakerActivity.ADD_CARE_TAKER_TAG == resultCode) {
            requestData();
        }
    }
}
