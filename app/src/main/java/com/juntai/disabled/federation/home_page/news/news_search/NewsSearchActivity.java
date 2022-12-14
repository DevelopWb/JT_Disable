package com.juntai.disabled.federation.home_page.news.news_search;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.base.search.BaseSearchListActivity;
import com.juntai.disabled.federation.bean.news.NewsListBean;
import com.juntai.disabled.federation.home_page.news.NewsContract;
import com.juntai.disabled.federation.home_page.news.NewsPresent;
import com.juntai.disabled.federation.home_page.news.news_common.NewsListAdapter;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 资讯搜索
 * @aouther ZhangZhenlong
 * @date 2020-8-8
 */
public class NewsSearchActivity extends BaseSearchListActivity<NewsPresent> implements NewsContract.INewsView {
    private List<NewsListBean.DataBean.DatasBean> newsList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;

    private String curKeyWord = "";

    @Override
    public void gotoSearch(String content) {
        if (StringTools.isStringValueOk(content)){
            curKeyWord = content;
            mPresenter.searchNewsList(content, page, pagesize,NewsContract.SEARCH_NEWS_LIST);
        }
    }

    @Override
    public void initView() {
        super.initView();
        SEARCH_HIS_KEY = "news_search_his_key";
        newsListAdapter = new NewsListAdapter(newsList);
        mSearchInfoRv.setLayoutManager(new LinearLayoutManager(mContext));
        newsListAdapter.setEmptyView(getAdapterEmptyView("暂无相关资讯",-1));
        mSearchInfoRv.setAdapter(newsListAdapter);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            gotoSearch(curKeyWord);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            gotoSearch(curKeyWord);
        });

        newsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApp.gotoNewsInfo(newsListAdapter.getItem(position).getTypeId(), newsListAdapter.getItem(position).getId(), mContext);
            }
        });
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();

        NewsListBean newsListBean = (NewsListBean)o;
        if (page == 1){
            newsListAdapter.getData().clear();
        }
        if (newsListBean.getData().getDatas().size() < pagesize){
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        }else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        newsListAdapter.addData(newsListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        ToastUtils.error(mContext, String.valueOf(o));
    }
}
