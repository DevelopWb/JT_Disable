package com.juntai.disabled.federation.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.video.record.VideoPreviewActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/3/27 16:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/27 16:26
 */
public abstract class BaseSelectVideoActivity<P extends BasePresenter> extends BaseAppActivity<P> implements View.OnClickListener {
    protected ImageView mItemVideoPic;
    private ImageView mItemVideoTag;
    private ImageView mItemDeleteVedioIv;
    //视频
    protected String videoScreenPath;//视频封面
    protected String videoPath;//视频地址
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;

    @Override
    public void initView() {
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoPic.setOnClickListener(this);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mItemDeleteVedioIv = (ImageView) findViewById(R.id.item_delete_vedio_iv);
        mItemDeleteVedioIv.setOnClickListener(this);
        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_video_pic:
                //选择视频
                recordVideo(this);
                break;
            case R.id.item_delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mItemDeleteVedioIv.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.SELECT_VIDEO_RESULT && resultCode == RESULT_OK) {
            List<String> paths = Matisse.obtainPathResult(data);
            if (paths != null && paths.size() > 0) {
                if (new File(paths.get(0)).length() / 1024 < 10000) {
                    videoPath = paths.get(0);
                    videoScreenPath = paths.get(0);
                    LogUtil.e("size-->" + new File(videoPath));
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreenPath, mItemVideoPic);
                    mItemDeleteVedioIv.setVisibility(View.VISIBLE);
                    mItemVideoTag.setVisibility(View.VISIBLE);
                } else {
                    ToastUtils.warning(this, "请选择小于10m的视频");
                }
            }
        }
    }

    /**
     * 视频录制成功回调广播
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            videoPath = intent.getStringExtra("videoUri");
            videoScreenPath = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreenPath, mItemVideoPic);
            mItemDeleteVedioIv.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
    }



    /**
     * 录制视频
     *
     * @param activity
     */
    public void recordVideo(FragmentActivity activity) {
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(this))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 录制
                            MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                                    .fullScreen(false)
                                    .smallVideoWidth(500)
                                    .smallVideoHeight(480)
                                    .recordTimeMax(10000)
                                    .recordTimeMin(2000)
                                    .videoBitrate(960 * 640)
                                    .captureThumbnailsTime(1)
                                    .build();
                            MediaRecorderActivity.goSmallVideoRecorder(activity, VideoPreviewActivity.class.getName()
                                    , config);
                        } else {
                            Toasty.info(activity, "请给与相应权限").show();
                        }
                    }
                });
    }

}
