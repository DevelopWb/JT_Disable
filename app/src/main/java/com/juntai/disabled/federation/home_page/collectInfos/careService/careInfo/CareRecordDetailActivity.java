package com.juntai.disabled.federation.home_page.collectInfos.careService.careInfo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.TextListBean;
import com.juntai.disabled.federation.bean.careTaker.CareRecordDetailBean;
import com.juntai.disabled.federation.home_page.baseInfo.TextListAdapter;
import com.juntai.disabled.federation.home_page.collectInfos.careService.CareContract;
import com.juntai.disabled.federation.home_page.collectInfos.careService.CarePresent;
import com.juntai.disabled.federation.utils.GlideImageLoader;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UrlFormatUtil;
import com.juntai.disabled.video.img.ImageZoomActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  托养记录详情
 * @date 2020/7/13 9:10
 */
public class CareRecordDetailActivity extends BaseAppActivity<CarePresent> implements CareContract.ICareView {


    private Banner mBanner;
    private RecyclerView mCareRecordInfoRv;
    private Banner banner;
    private GlideImageLoader imageLoader;
    private List<String> images;
    public static String CARE_RECORD_TAKER_ID = "care_record_taker_id";
    private TextListAdapter textListAdapter;
    private SmartRefreshLayout mSmartrefreshlayout;

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("托养记录详情");
        mBanner = (Banner) findViewById(R.id.banner);
        mCareRecordInfoRv = (RecyclerView) findViewById(R.id.recyclerview);
        textListAdapter = new TextListAdapter(R.layout.item_text_layout,null);
        textListAdapter.setHidePresentBg(true);
        textListAdapter.setHeaderView(getHeadView());
        textListAdapter.setHeaderAndEmpty(true);
        initRecyclerview(mCareRecordInfoRv, textListAdapter, LinearLayoutManager.VERTICAL);

        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(true);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    /**
     * 获取头布局
     *
     * @return
     */
    private View getHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_care_record_head, null);
        /*anner*/
        banner = view.findViewById(R.id.banner);
        banner.isAutoPlay(true);
        ArrayList<String> photos = new ArrayList<>();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(images);
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item"
                        , position));

            }
        });
        imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
            @Override
            public void onFullScreen() {
            }
        });
        return view;
    }

    @Override
    protected CarePresent createPresenter() {
        return new CarePresent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.releaseBanner();
            banner.removeAllViews();
            banner.setOnBannerListener(null);

        }
        banner = null;
    }

    @Override
    public void initData() {
        //获取托养人员的id
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(CARE_RECORD_TAKER_ID, 0);
            mPresenter.getCareRecord(getBaseBuilder().add("id", String.valueOf(id)).build(), "");
        }

    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        CareRecordDetailBean careRecordDetailBean = (CareRecordDetailBean) o;
        if (careRecordDetailBean != null) {
            CareRecordDetailBean.DataBean dataBean = careRecordDetailBean.getData();
            images = new ArrayList<>();
            if (dataBean != null) {
                textListAdapter.setNewData(null);
                textListAdapter.addData(new TextListBean("服务项目", dataBean.getCateName()));
                textListAdapter.addData(new TextListBean("服务人员", dataBean.getServicer()));
                textListAdapter.addData(new TextListBean("服务时长", dataBean.getServiceLength()));
                textListAdapter.addData(new TextListBean("服务时间", String.format("%s%s%s", dataBean.getStartTime(), "至",
                        dataBean.getEndTime())));
//                textListAdapter.addData(new TextListBean("上传时间", dataBean.getGmtCreat()));
                textListAdapter.addData(new TextListBean("备注信息", dataBean.getDescribe()));

                List<CareRecordDetailBean.DataBean.ServiceFileVosBean> services = dataBean.getServiceFileVos();
                if (services != null) {
                    for (CareRecordDetailBean.DataBean.ServiceFileVosBean service : services) {
                        if (StringTools.isStringValueOk(service.getPath())) {
                            images.add(UrlFormatUtil.formatPicUrl(service.getPath()));
                        }
                    }
                    banner.setImages(images).setImageLoader(imageLoader).start();
                }
            }
        }
    }

}
