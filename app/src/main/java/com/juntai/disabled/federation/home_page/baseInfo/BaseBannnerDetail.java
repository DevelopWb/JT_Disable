package com.juntai.disabled.federation.home_page.baseInfo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.TextListBean;
import com.juntai.disabled.federation.utils.GlideImageLoader;
import com.juntai.disabled.video.img.ImageZoomActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:带有轮播图的详情基类
 * Create by zhangzhenlong
 * 2020-7-4
 * email:954101549@qq.com
 */
public abstract class BaseBannnerDetail<P extends BasePresenter> extends BaseMvpActivity<P> {
    protected Banner mBanner;
    protected RecyclerView mInterviewDetailTexts;
    protected List<TextListBean> textListBeans = new ArrayList<>();
    protected TextListAdapter textListAdapter;
    /**
     * 描述
     */
    protected TextView mDescriptionTv;
    protected int dataId;

    protected GlideImageLoader imageLoader;
    protected ArrayList<String> images = new ArrayList<>();
    protected boolean hasVideo = false;//是否有视频文件
    protected SmartRefreshLayout mPageDetailSrl;

    @Override
    public int getLayoutView() {
        return R.layout.activity_interview_detail;
    }

    @Override
    public void initView() {
        setTitleName(getTitleContent());
        mBanner = (Banner) findViewById(R.id.banner);
        mInterviewDetailTexts = (RecyclerView) findViewById(R.id.interview_detail_texts);
        mDescriptionTv = (TextView) findViewById(R.id.description_tv);
        mInterviewDetailTexts.setLayoutManager(new LinearLayoutManager(this));
        textListAdapter = new TextListAdapter(R.layout.item_inspection_detail, textListBeans);
        mInterviewDetailTexts.setAdapter(textListAdapter);
        mPageDetailSrl = (SmartRefreshLayout) findViewById(R.id.page_detail_srl);

        mPageDetailSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        mPageDetailSrl.setEnableLoadMore(false);

        mBanner.isAutoPlay(false);
        ArrayList<String> photos = new ArrayList<>();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(images);
                if (hasVideo) {
                    photos.remove(0);
                }
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item", hasVideo ? position - 1 : position));

            }
        });
    }

    @Override
    public void onError(String tag, Object o) {
        mPageDetailSrl.finishRefresh();
        if (String.valueOf(o).equals("error")) {
            ToastUtils.error(mContext, "无法获取数据或已删除");
            onBackPressed();
        } else {
            super.onError(tag, o);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.releaseBanner();
            mBanner.removeAllViews();
            mBanner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
                imageLoader.release();
            }

        }
        mBanner = null;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (imageLoader != null) {
            imageLoader.pause();
        }
    }

    protected abstract String getTitleContent();
}
