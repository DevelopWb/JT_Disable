package com.juntai.wisdom.dgjxb.home_page.camera.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.ijkplayer.listener.OnReplayListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadActivity;
import com.juntai.wisdom.basecomponent.bean.OpenLiveBean;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ScreenUtils;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.stream.StreamCameraDetailBean;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayContract;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayPresent;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述   车辆直播
 * @date 2020/7/25 16:44
 */
public class CarLiveActivity extends BaseDownLoadActivity<PlayPresent> implements PlayContract.IPlayView,
        View.OnClickListener, BaseDownLoadActivity.OnFileDownloaded {

    private PlayerView player;
    //    private String url放大 = "rtmp://juntaikeji.net:1935/video/37130201561327011011";
    private PowerManager.WakeLock wakeLock;
    private Intent intent;
    public static String STREAM_CAMERA_URL = "stream_url";
    public static String STREAM_CAMERA_NAME = "stream_url_name";
    private String playUrl;
    private Disposable disposable;

    @Override
    protected PlayPresent createPresenter() {
        return new PlayPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.car_stream;
    }

    @Override
    public void initView() {
        timeBegin();
        String name = getIntent().getStringExtra(STREAM_CAMERA_NAME);
        TextView titleTv = findViewById(R.id.car_title_tv);
        titleTv.setText(name);
        setFileDownLoadCallBack(this);
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (ScreenUtils.getInstance(getApplicationContext()).getScreenWidth() / 10) * 9;
        wlp.height = DisplayUtil.dp2px(mContext, 245);
        window.setAttributes(wlp);
    }


    @Override
    public void initData() {

    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, mBaseRootCol)
                .setTitle("")
                //隐藏顶部布局
                .hideHideTopBar(true)
                //分辨率
                .hideSteam(true)
                //旋转
                .hideRotation(true)
                //隐藏全屏按钮，true隐藏，false为显示
                .hideFullscreen(false)
                .hideCenterPlayer(false)
                .forbidTouch(false)
                .hideBottonBar(true)
                .setForbidDoulbeUp(true)
                .setScaleType(PlayStateParams.wrapcontent);
        //                .showThumbnail(new OnShowThumbnailListener() {
        //                    @Override
        //                    public void onShowThumbnail(ImageView ivThumbnail) {
        //                        ImageLoadUtil.loadImageNoCrash(mContext.getApplicationContext(),
        //                                UrlFormatUtil.formatStreamCapturePicUrl(mCameraNum), ivThumbnail);
        //                    }
        //                });
        player.setOnReplayListener(new OnReplayListener() {
            @Override
            public void onReplay() {
                timeBegin();
            }
        });
        findViewById(R.id.car_fullscreen_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isLiving()) {
                    ToastUtils.toast(mContext,"获取不到直播源");
                    return;
                }
                if (!TextUtils.isEmpty(playUrl)) {
                    startActivity(new Intent(mContext, CarStreamCameraFullScreenActivity.class)
                            .putExtra(CarStreamCameraFullScreenActivity.STREAM_CAMERA_TITLE,
                                    "")
                            .putExtra(CarStreamCameraFullScreenActivity.STREAM_CAMERA_URL, playUrl)
                    );
                } else {
                    ToastUtils.toast(mContext, "无法获取流地址");
                }
            }
        });

        playUrl = getIntent().getStringExtra(STREAM_CAMERA_URL);

        player.setPlaySource(playUrl).startPlay();
    }

    @Override
    protected String getTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (wakeLock != null) {
            wakeLock.release();
        }
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();

    }

    @Override
    public void onSuccess(String tag, Object o) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        if (intent != null) {
            stopService(intent);
        }
        if (disposable != null) {
            disposable.dispose();
        }
        setFileDownLoadCallBack(null);
    }

    @Override
    public void onFileDownloaded(String filePath) {

    }
    /**
     * 开始计时
     */
    public void timeBegin() {
        if (disposable == null) {
            disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .take(6)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            disposable = null;
                            if (!player.isLiving()) {
                                player.statusChange(PlayStateParams.MEDIA_INFO_VIDEO_INTERRUPT);
                            }

                        }
                    });
        }

    }


}
