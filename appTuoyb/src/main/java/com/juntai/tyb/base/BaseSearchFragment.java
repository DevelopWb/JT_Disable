package com.juntai.tyb.base;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.tyb.base.customView.flowlayout.FlowLayout;
import com.juntai.tyb.base.customView.flowlayout.TagAdapter;
import com.juntai.tyb.base.customView.flowlayout.TagFlowLayout;
import com.juntai.tyb.bean.careTaker.StreetBean;
import com.juntai.tyb.bean.careTaker.YearsBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.search.SearchContract;
import com.juntai.tyb.homePage.search.SearchPresent;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UserInfoManager;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述   搜索控件
 * @CreateDate: 2020/3/15 9:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/15 9:18
 */
public abstract class BaseSearchFragment extends BaseAppFragment<SearchPresent> implements View.OnClickListener,
        SearchContract.ISearchView {

    public static String SEARCH_HIS_KEY = "search_his_key";//保存本地的历史记录的key

    /**
     * 返回
     */
    private TextView mBackTv;
    private SearchView mSearchContentSv;
    /**
     * 搜索
     */
    private TextView mSearchTv;
    private ImageView mDeleteHistoryIv;
    private TagFlowLayout tagFlowLayout;
    private TagAdapter mRecordsAdapter;
    private ImageView mShowMoreArrowIv;
    private LinearLayout mHisRecordLl;

    private int type = 0;//0是查找托养人  1是查找托养记录
    protected ImageView mFilterIv;
    /**
     * 请选择地区
     */
    private TextView mSelectAreaTv;
    /**
     * 请选择年度
     */
    private TextView mSelectYearTv;
    /**
     * 开始搜索
     */
    private TextView mStartSearchByConditionTv;
    private ConstraintLayout mSearchByConditionCl;

    private boolean showFilterView = false;//展示筛选控件
    private RecyclerView mResultRv;
    protected ConstraintLayout mSearchTopCl;

    private String currentStreet, currentVillage, currentYear;
    protected int currentPage = 1;
    protected int limit = 10;
    protected SmartRefreshLayout mSmartrefreshlayout;
    protected TextView searchedAmount;


    /**
     * 获取搜索结果的适配器
     *
     * @return
     */
    protected abstract BaseQuickAdapter getSearchResultAdapter();

    /**
     * 配置搜索类型
     *
     * @param type
     */
    public void setSearchType(int type) {
        this.type = type;
        if (0 == type) {
            //隐藏返回键
            mBackTv.setVisibility(View.GONE);
        } else {
            mBackTv.setVisibility(View.VISIBLE);

        }
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected SearchPresent createPresenter() {
        return new SearchPresent();
    }

    @Override
    protected void initView() {
        mBackTv = (TextView) getView(R.id.back_tv);
        mSearchTopCl = getView(R.id.search_top_cl);
        mBackTv.setOnClickListener(this);
        mSearchContentSv = (SearchView) getView(R.id.search_content_sv);
        mSearchTv = (TextView) getView(R.id.search_tv);
        mSearchTv.setOnClickListener(this);
        mDeleteHistoryIv = (ImageView) getView(R.id.delete_history_iv);
        mDeleteHistoryIv.setOnClickListener(this);
        tagFlowLayout = (TagFlowLayout) getView(R.id.history_item_tfl);
        mShowMoreArrowIv = (ImageView) getView(R.id.show_more_arrow_iv);
        mHisRecordLl = (LinearLayout) getView(R.id.his_record_ll);
        mFilterIv = (ImageView) getView(R.id.filter_iv);
        mFilterIv.setOnClickListener(this);
        mSelectAreaTv = (TextView) getView(R.id.select_area_tv);
        mSelectAreaTv.setOnClickListener(this);
        mSelectYearTv = (TextView) getView(R.id.select_year_tv);
        mResultRv = getView(R.id.recyclerview);
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);
        getBaseActivity().initRecyclerview(mResultRv, getSearchResultAdapter(), LinearLayoutManager.VERTICAL);
        mSelectYearTv.setOnClickListener(this);
        mStartSearchByConditionTv = (TextView) getView(R.id.start_search_by_condition_tv);
        mStartSearchByConditionTv.setOnClickListener(this);
        mSearchByConditionCl = (ConstraintLayout) getView(R.id.search_by_condition_cl);
        initFlowLayoutData();
    }


    /**
     * 初始化流式布局数据
     */
    private void initFlowLayoutData() {
        List<String> recordList = Hawk.get(SEARCH_HIS_KEY);
        if (recordList != null && !recordList.isEmpty()) {
            mHisRecordLl.setVisibility(View.VISIBLE);
        } else {
            mHisRecordLl.setVisibility(View.GONE);
        }
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(recordList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.search_item_textview,
                        tagFlowLayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };


        tagFlowLayout.setAdapter(mRecordsAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                List<String> his_data = Hawk.get(SEARCH_HIS_KEY);
                mSearchContentSv.setQuery(his_data.get(position), true);
                mSearchTv.performClick();
            }
        });
        //删除某个条目
        tagFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                showDialog("确定要删除该条历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除某一条记录
                        deletHisItem(position);
                    }
                });
            }
        });

        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    mShowMoreArrowIv.setVisibility(View.VISIBLE);
                } else {
                    mShowMoreArrowIv.setVisibility(View.GONE);
                }
            }
        });

        mShowMoreArrowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
                mHisRecordLl.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 删除条目的对话框
     *
     * @param dialogTitle
     * @param onClickListener
     */
    private void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_tv) {
            getActivity().onBackPressed();
        } else if (id == R.id.search_tv) {
            clearData();
            showFilterView = true;
            mStartSearchByConditionTv.performClick();
        } else if (id == R.id.delete_history_iv) {//删除所有的记录
            showDialog("确定要删除所有历史记录？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //删除所有记录
                    Hawk.delete(SEARCH_HIS_KEY);
                    initFlowLayoutData();
                }
            });
        } else if (id == R.id.filter_iv) {
            showFilterView = !showFilterView;
            mSearchByConditionCl.setVisibility(showFilterView ? View.VISIBLE : View.GONE);
            List<String> recordList = Hawk.get(SEARCH_HIS_KEY);
            if (recordList != null && !recordList.isEmpty()) {
                mHisRecordLl.setVisibility(showFilterView ? View.GONE : View.VISIBLE);
            }

            mSearchTv.setVisibility(showFilterView ? View.GONE : View.VISIBLE);
        } else if (id == R.id.select_area_tv) {
            mPresenter.getStreets(SearchContract.STREETS);
        } else if (id == R.id.select_year_tv) {
            mPresenter.getAllYears(SearchContract.YEARS);
        } else if (id == R.id.start_search_by_condition_tv) {
            mFilterIv.performClick();
            String content = mSearchContentSv.getQuery().toString();
            if (StringTools.isStringValueOk(content)) {
                initSearchClick(content);
            }
            requestData();
        }
    }

    /**
     * 初始化数据
     */
    protected void clearData() {
        currentStreet = "";
        currentVillage = "";
        currentYear = "";
        mSelectAreaTv.setText("");
        mSelectYearTv.setText("");
    }

    /**
     * 请求数据
     */
    protected abstract void requestData();


    private void initSearchClick(String content) {
        //将搜索的内容添加到本地
        List<String> his_data = null;
        if (Hawk.contains(SEARCH_HIS_KEY)) {
            his_data = Hawk.get(SEARCH_HIS_KEY);
        } else {
            his_data = new ArrayList<>();
        }
        if (!his_data.contains(content)) {
            his_data.add(0, content);
            Hawk.put(SEARCH_HIS_KEY, his_data);
            initFlowLayoutData();
        }
    }

    /**
     * 删除历史条目
     *
     * @param position
     */
    private void deletHisItem(int position) {
        List<String> his_data = Hawk.get(SEARCH_HIS_KEY);
        his_data.remove(position);
        Hawk.put(SEARCH_HIS_KEY, his_data);
        initFlowLayoutData();
    }

    @Override
    protected void lazyLoad() {
        clearData();
        requestData();
    }


    /**
     * 搜索的回调
     */
    public interface OnSearchCallBack {
        void onSearch(String textContent);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        getBaseActivity().getViewFocus(mSmartrefreshlayout);
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        switch (tag) {
            case SearchContract.STREETS:
                //获取街道
                StreetBean streetBean = (StreetBean) o;
                if (streetBean != null) {
                    List<StreetBean.DataBean> dataBeans = streetBean.getData();
                    List<List<StreetBean.DataBean.ChildBean>> childs = new ArrayList<>();
                    if (dataBeans != null && dataBeans.size() > 0) {
                        for (StreetBean.DataBean dataBean : dataBeans) {
                            List<StreetBean.DataBean.ChildBean> childBeans = dataBean.getCommunityVos();
                            if (childBeans != null && childBeans.size() > 0) {
                                StreetBean.DataBean.ChildBean allChild = new StreetBean.DataBean.ChildBean("全部");
                                childBeans.add(0, allChild);
                                childs.add(childBeans);
                            } else {
                                List<StreetBean.DataBean.ChildBean> arr = new ArrayList<>();
                                arr.add(new StreetBean.DataBean.ChildBean("全部"));
                                childs.add(arr);
                            }
                        }
                        PickerManager.getInstance().showOptionPicker(mContext, dataBeans, childs,
                                new PickerManager.OnOptionPickerSelectedListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                        currentStreet = dataBeans.get(options1).getStreet();
                                        currentVillage = childs.get(options1).get(option2).getCommunity();
                                        String area =
                                                String.format("%s%s%s", currentStreet, "    ", currentVillage);
                                        mSelectAreaTv.setText(area);
                                    }
                                });
                    }
                }
                break;

            case SearchContract.YEARS:

                YearsBean yearsBean = (YearsBean) o;
                if (yearsBean != null) {
                    List<YearsBean.DataBean> years = yearsBean.getData();
                    if (years != null && years.size() > 0) {
                        PickerManager.getInstance().showOptionPicker(mContext, years,
                                new PickerManager.OnOptionPickerSelectedListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                        currentYear = years.get(options1).getPickerViewText();
                                        mSelectYearTv.setText(currentYear);
                                    }
                                });
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        getBaseActivity().getViewFocus(mSmartrefreshlayout);
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    protected RequestBody getRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account", UserInfoManager.getUserAccount());
        builder.add("token", UserInfoManager.getUserToken());
        if (StringTools.isStringValueOk(currentStreet)) {
            builder.add("streetAddress", currentStreet);
        }
        if (StringTools.isStringValueOk(currentVillage) && !"全部".equals(currentVillage)) {
            builder.add("communityAddress", currentVillage);
        }
        String content = mSearchContentSv.getQuery().toString();
        if (StringTools.isStringValueOk(content)) {
            builder.add("name", content);
        }
        if (StringTools.isStringValueOk(currentYear)) {
            builder.add("year", currentYear);
        }

        builder.add("page", String.valueOf(currentPage));
        builder.add("limit", String.valueOf(limit));
        return builder.build();
    }

    /**
     * 获取底部布局
     * @return
     */
    protected View getFootView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_result_foot_view,null);
        searchedAmount = view.findViewById(R.id.search_result_foot_tv);
        return view;
    }
}
