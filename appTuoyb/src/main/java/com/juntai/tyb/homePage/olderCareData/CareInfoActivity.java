package com.juntai.tyb.homePage.olderCareData;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.bean.CareChildListNewestBean;
import com.juntai.disabled.video.img.ImageZoomActivity;
import com.juntai.tyb.AppHttpPath;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.GlideImageLoader;
import com.juntai.tyb.uitils.StringTools;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/21 15:48  更新 托养信息
 */
public class CareInfoActivity extends BaseAppActivity<CarePresent> implements CareContract.ICareView {
    private RecyclerView recyclerView;
    private GlideImageLoader imageLoader;
    private List<String> images;
    private ArrayList<String> photos = new ArrayList<>();
    public static String  CARE_ID = "care_id";//托养人id
    public static int REFRESH_CARE_LIST = 1001;//关闭的时候 刷新我的托养中的列表数据
    private FollowListAdapter followListAdapter;
    private Banner banner;
    private CareChildListNewestBean.DataBean dataBean;
    private List<String> paths;

    @Override
    public int getLayoutView() {
        return R.layout.activity_old_care_info;
    }

    @Override
    public void initView() {
        banner = findViewById(R.id.banner);
        banner.isAutoPlay(false);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(paths);
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item"
                        , position));

            }
        });
        imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
            @Override
            public void onFullScreen() {
                //                    if (mCase != null&& StringTools.isStringValueOk(mCase.getVideo())) {
                //                        Intent intent = new Intent(mContext, PlayerActivity.class);
                //                        intent.putExtra("playPath", AppHttpPath.BASE+ mCase.getVideo());
                //                        startActivity(intent);
                //                    }

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(REFRESH_CARE_LIST);
        super.onBackPressed();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (banner != null){
            banner.releaseBanner();
            banner.removeAllViews();
            banner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
            }


        }
        banner = null;
    }
    @Override
    public void initData() {
        setTitleName("托养信息");
        ((TextView) findViewById(R.id.daohang)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean != null) {
                    navigationLogic(new LatLng(dataBean.getLatitude(),
                            dataBean.getLongitude()), dataBean.getPlace());
                }
            }
        });
        int id = getIntent().getIntExtra(CARE_ID,0);
        mPresenter.getCareChildList(getPublishMultipartBody().addFormDataPart("id",String.valueOf(id)).build(), "");
    }





    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected CarePresent createPresenter() {
        return new CarePresent();
    }


    @Override
    public void onSuccess(String tag, Object o) {
        CareChildListNewestBean bean = (CareChildListNewestBean) o;
        dataBean = bean.getData();
        if (dataBean != null) {
            ((TextView) findViewById(R.id.firstCase_place)).append(dataBean.getPlace());
            ((TextView) findViewById(R.id.firstCase_type)).append(dataBean.getType());
            ((TextView) findViewById(R.id.firstCase_time)).append(dataBean.getCaseDate());
            ((TextView) findViewById(R.id.firstCase_detail)).append(dataBean.getName());
            recyclerView = findViewById(R.id.detail_rv2);
            followListAdapter = new FollowListAdapter(R.layout.old_care_item_layout,false);
            initRecyclerview(recyclerView, followListAdapter, LinearLayoutManager.VERTICAL);
            followListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    CareChildListNewestBean.DataBean.ChildCaseBean dataBean =
                            (CareChildListNewestBean.DataBean.ChildCaseBean) adapter.getData().get(position);
                    Intent mintent = new Intent(mContext, CareDetailActivity.class);
                    mintent.putExtra(CareDetailActivity.CARE_CHILD_ID, dataBean.getId());
                    startActivity(mintent);
                }
            });
            images = new ArrayList<>();
            if (StringTools.isStringValueOk(dataBean.getPhotoOne())) {
                images.add(dataBean.getPhotoOne());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoTwo())) {
                images.add(dataBean.getPhotoTwo());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoThree())) {
                images.add(dataBean.getPhotoThree());
            }
            paths = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                String path = AppHttpPath.BASE_IMAGE + images.get(i);
                paths.add(path);
            }
            banner.setImages(paths).setImageLoader(imageLoader).start();
          List<CareChildListNewestBean.DataBean.ChildCaseBean> arrays =   dataBean.getChildCase();
            if (arrays != null) {
                Collections.reverse(arrays);
                followListAdapter.setNewData(arrays);
            }
        }
    }
}