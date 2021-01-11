package com.juntai.tyb.mine.myMsg;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.bean.mine.MyMsgBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.careTaker.careInfo.CareTakerInfoActivity;
import com.juntai.tyb.mine.MineContract;
import com.juntai.tyb.mine.MinePresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  我的消息
 * @date 2020/7/28 11:50
 */
public class MyMsgActivity extends BaseAppActivity<MinePresent> implements MineContract.IMineView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private MyMsgAdapter adapter;
    protected int currentPage = 1;
    protected int limit = 10;
    private MyMsgBean.DataBean.DatasBean currentBean;
    private int currentPosition ;

    public static int  IS_READ_RESULT = 1004;//已读
    @Override
    protected MinePresent createPresenter() {
        return new MinePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("我的消息");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        adapter = new MyMsgAdapter(R.layout.my_msg_item);
        adapter.setEmptyView(getAdapterEmptyView(getString(R.string.none_message),R.mipmap.none_message));
        initRecyclerview(mRecyclerview, adapter, LinearLayoutManager.VERTICAL);
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            initData();
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            initData();
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {



            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentBean = (MyMsgBean.DataBean.DatasBean) adapter.getData().get(position);
                currentPosition =position;
                if (0== currentBean.getIsRead()) {
                    //未读
                    mPresenter.readMsg(getPublishMultipartBody().addFormDataPart("id",String.valueOf(currentBean.getId())).build(),
                            MineContract.READ_MSG);
                }else {
                    startActivity(new Intent(mContext, CareTakerInfoActivity.class).putExtra(CareTakerInfoActivity.CARE_TAKER_ID, currentBean.getCaregiversId()));

                }


            }
        });
    }

    @Override
    public void initData() {
        mPresenter.myNotice(getPublishMultipartBody().addFormDataPart("page", String.valueOf(currentPage))
                .addFormDataPart("limit", String.valueOf(limit)).build(), MineContract.MY_NOTICE);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        switch (tag) {
            case MineContract.MY_NOTICE:
                MyMsgBean myMsgBean = (MyMsgBean) o;
                if (myMsgBean != null) {
                    MyMsgBean.DataBean dataBean = myMsgBean.getData();
                    if (dataBean != null) {
                        List<MyMsgBean.DataBean.DatasBean> arrays = dataBean.getDatas();
                        if (arrays != null && arrays.size() > 0) {
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
                        }
                    }

                }
                break;
            case MineContract.READ_MSG:
                setResult(IS_READ_RESULT);
                //消息已读  更新适配器
                if (currentBean != null) {
                    currentBean.setIsRead(1);
                    adapter.notifyItemChanged(currentPosition);
                    //跳转到托养信息界面
                    startActivity(new Intent(mContext, CareTakerInfoActivity.class).putExtra(CareTakerInfoActivity.CARE_TAKER_ID, currentBean.getCaregiversId()));
                }

                break;
            default:
                break;
        }
    }
}
