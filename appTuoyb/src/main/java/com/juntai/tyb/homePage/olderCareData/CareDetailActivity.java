package com.juntai.tyb.homePage.olderCareData;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.juntai.disabled.basecomponent.bean.CareChildListNewestBean;
import com.juntai.disabled.video.img.ImageZoomActivity;
import com.juntai.tyb.AppHttpPath;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.base.TextListAdapter;
import com.juntai.tyb.bean.TextListBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.GlideImageLoader;
import com.juntai.tyb.uitils.StringTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/23 11:48  更新  托养信息
 */
public class CareDetailActivity extends BaseAppActivity<CarePresent> implements CareContract.ICareView {

    public static final String CARE_CHILD_ID = "id";


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
        textListAdapter = new TextListAdapter(R.layout.item_text_layout);
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
            int id = getIntent().getIntExtra(CARE_CHILD_ID, 0);
            mPresenter.getCareChildList(getPublishMultipartBody().addFormDataPart("id", String.valueOf(id)).build(),
                    "");
        }

    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();

        CareChildListNewestBean bean = (CareChildListNewestBean) o;
        CareChildListNewestBean.DataBean dataBean = bean.getData();
        if (dataBean != null) {
            textListAdapter.setNewData(null);
            textListAdapter.addData(new TextListBean("详细信息", dataBean.getName()));
            textListAdapter.addData(new TextListBean("服务类型", dataBean.getType()));
            textListAdapter.addData(new TextListBean("上报地点", dataBean.getPlace()));
            textListAdapter.addData(new TextListBean("上报时间", dataBean.getCaseDate()));
            images = new ArrayList<>();
            if (StringTools.isStringValueOk(dataBean.getPhotoOne())) {
                images.add(AppHttpPath.BASE_IMAGE + dataBean.getPhotoOne());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoTwo())) {
                images.add(AppHttpPath.BASE_IMAGE + dataBean.getPhotoTwo());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoThree())) {
                images.add(AppHttpPath.BASE_IMAGE + dataBean.getPhotoThree());
            }
            banner.setImages(images).setImageLoader(imageLoader).start();
        }
    }

}
