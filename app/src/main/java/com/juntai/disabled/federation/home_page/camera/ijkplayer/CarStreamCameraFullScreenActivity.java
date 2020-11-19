package com.juntai.disabled.federation.home_page.camera.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.RelativeLayout;

import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.juntai.disabled.federation.R;

/**
 * @aouther tobato  车辆流数据 全屏播放
 * @description 描述
 * @date 2020/6/1 16:49
 */

public class CarStreamCameraFullScreenActivity extends AppCompatActivity {
    private PlayerView player;
    //    private String url = "rtmp://juntaikeji.net:1935/video/37130201561327011011";
    private PowerManager.WakeLock wakeLock;
    public static String STREAM_CAMERA_URL = "stream_num_rul";
    public static String STREAM_CAMERA_TITLE = "stream_num_rul_title";
    private String url;
    private RelativeLayout mRootRL;


    public void initView() {
        url = getIntent().getStringExtra(STREAM_CAMERA_URL);
        mRootRL = findViewById(R.id.app_video_box);
    }


    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_player_view_player_full);
        initView();
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, mRootRL)
                .setTitle("")
                //分辨率
                .hideSteam(true)
                //旋转
                .hideRotation(false)
                //底部功能键
                .hideControlPanl(true)
                .setScaleType(PlayStateParams.wrapcontent);
        if (!TextUtils.isEmpty(url)) {
            player.setPlaySource(url).startPlay();
        }
        player.hideHideTopBar(false)
                .forbidTouch(false)
                .setForbidHideControlPanl(false)
                .hideMenu(true)
        ;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        MediaUtils.muteAudioFocus(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        MediaUtils.muteAudioFocus(this, false);
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
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }

    }


}
