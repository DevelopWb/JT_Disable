package com.juntai.disabled.federation.home_page.news.news_common;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.news.NewsListBean;
import com.juntai.disabled.federation.home_page.news.NewsContract;
import com.juntai.disabled.federation.home_page.news.NewsPresent;
import com.juntai.disabled.federation.home_page.news.news_publish.PublishNewsActivity;
import com.juntai.disabled.federation.home_page.news.news_search.NewsSearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 资讯列表
 * @aouther ZhangZhenlong
 * @date 2020-7-29
 */
public class NewsListFragment extends BaseMvpFragment<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {
    private LinearLayout mSearchLl;
    /**
     * 发布
     */
    private ImageView mPublishBtn;
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    int page = 1, pagesize = 20;
    private List<NewsListBean.DataBean.DatasBean> newsList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    PopupWindow popupWindow;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        mSearchLl = getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
        mPublishBtn = getView(R.id.publish_btn);
        mPublishBtn.setOnClickListener(this);
        mRecyclerview = getView(R.id.recyclerview);
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mSearchLl.getLayoutParams();
        layoutParams.setMargins(DisplayUtil.dp2px(mContext, 15), MyApp.statusBarH + DisplayUtil.dp2px(mContext, 10),
                DisplayUtil.dp2px(mContext, 10),DisplayUtil.dp2px(mContext, 8));
        mSearchLl.setLayoutParams(layoutParams);

        newsListAdapter = new NewsListAdapter(newsList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        newsListAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.load_more_no_data),R.mipmap.none_publish));
        mRecyclerview.setAdapter(newsListAdapter);
        newsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApp.gotoNewsInfo(newsListAdapter.getItem(position).getTypeId(), newsListAdapter.getItem(position).getId(), mContext);
                newsListAdapter.getItem(position).setLooked(true);
                newsListAdapter.notifyItemChanged(position);
            }
        });

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });
    }

    @Override
    protected void initData() {}

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    protected void lazyLoad() {
        if (newsList == null || newsList.isEmpty()){
            //加载数据
            page = 1;
            getData(true);
        }
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        mPresenter.getNewsList(page,pagesize, "", showProgress);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_ll:
                //资讯搜索
                startActivity(new Intent(mContext, NewsSearchActivity.class));
                break;
            case R.id.publish_btn:
                //发布
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                showPopType(mPublishBtn);
                break;
        }
    }

    public void showPopType(View view) {
        View viewPop = LayoutInflater.from(mContext).inflate(R.layout.pop_publish_item, null);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(view, 0, 5);//显示在控件下面
        viewPop.findViewById(R.id.item_one).setOnClickListener(v -> {
            startActivity(new Intent(mContext, PublishNewsActivity.class).putExtra("type",0));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.item_two).setOnClickListener(v -> {
            startActivity(new Intent(mContext, PublishNewsActivity.class).putExtra("type",1));
            popupWindow.dismiss();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UPDATE_NEWS_LIST.equals(test)){
            //刷新
            page = 1;
            getData(false);
        }
    }
}
