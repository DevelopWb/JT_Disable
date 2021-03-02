package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseSelectPicsAndVedioActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;
import com.juntai.disabled.federation.utils.StringTools;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  意见建议
 * @date 2021/3/2 8:33
 */
public class SuggestionActivity extends BaseBusinessActivity implements SelectPhotosFragment.OnPhotoItemClick,
        SelectPhotosFragment.OnPicCalculateed, View.OnClickListener {

    protected SelectPhotosFragment selectPhotosFragment;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;
    protected String videoPath = null;
    private ImageView mItemVideoPic, mDeleteVedio, mItemVideoTag;

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 动态获取到fragment中图片的宽高后 改变mItemVideoPic的宽高
     *
     * @param width
     */
    @Override
    public void picCalculateed(int width) {
        ViewGroup.LayoutParams params = mItemVideoPic.getLayoutParams();
        params.width = DisplayUtil.dp2px(mContext, width);
        params.height = DisplayUtil.dp2px(mContext, width);
        mItemVideoPic.setLayoutParams(params);
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    /**
     * 视频录制成功回调广播play_button
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedio.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String getTitleName() {
        return "意见建议";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_suggestion, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        mItemVideoPic = (ImageView)  view.findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView)  view.findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView)  view.findViewById(R.id.push_case_delete_vedio_iv);
        mDeleteVedio.setOnClickListener(this);
        mItemVideoPic.setOnClickListener(this);
        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);
//        FrameLayout frameLayout = view.findViewById(R.id.picture_fragment);
//        selectPhotosFragment = SelectPhotosFragment.newInstance().setPhotoTitle("")
//                .setPhotoSpace(60)
//                .setMaxCount(3).setPicCalculateCallBack(this);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        //开启事务
//        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//        beginTransaction.replace(frameLayout.getId(), selectPhotosFragment);
//        //最后一步 记得commit
//        beginTransaction.commit();
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected void commit() {
        List<String> pics = selectPhotosFragment.getPhotosPath();
        if (pics.size() < 3) {
            ToastUtils.warning(mContext, "请选择3张图片");
            return;
        }
        if (!StringTools.isStringValueOk(videoPath)) {
            ToastUtils.warning(mContext, "请选择视频");
            return;
        }

        if (pics.size() > 0) {
            for (int i = 0; i < pics.size(); i++) {
                String path = pics.get(i);
                String key = String.format("%s%d", "photo", i + 1);
                //                builder.addFormDataPart(key, key, RequestBody.create(MediaType.parse("file"), new
                //                File(path)));
            }
        }
        if (StringTools.isStringValueOk(videoPath)) {
            //            builder.addFormDataPart("caseVideo", "caseVideo", RequestBody.create(MediaType.parse("file"),
            //                    new File(videoPath)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_video_pic:
                //选择视频
                mPresenter.recordVideo(this);
                break;
            case R.id.push_case_delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedio.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getSuggestionAdapterData();
    }

}
