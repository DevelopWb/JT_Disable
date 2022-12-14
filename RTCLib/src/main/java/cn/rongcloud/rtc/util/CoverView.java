package cn.rongcloud.rtc.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.rongcloud.rtc.CenterManager;
import cn.rongcloud.rtc.VideoViewManager;
import cn.rongcloud.rtc.utils.FinLog;
import cn.rongcloud.rtc.engine.view.RongRTCVideoView;

import cn.rongcloud.rtc.R;

/**
 * @Author DengXuDong.
 * @Time 2018/2/7.
 * @Description:
 */
public class CoverView extends RelativeLayout {

    private static final String TAG = "CoverView";

    public RelativeLayout mRl_Container;
    public TextView tv_userName;
    private GradientDrawable mGroupDrawable;
    public ImageView iv_Header, iv_Audiolevel,ivAudioCover;
    private Context mContext;
    //    private TextPaint textPaint;
    private String UserId = "", UserName = "RongRTC";
    public ProgressBar progressBar;
    public RongRTCVideoView rongRTCVideoView = null;
    public VideoViewManager.RenderHolder mRenderHolder;
    private View trackTest;
    private View firstFrameTest;
    private View testLayout;
    private View eglTest;
    public CoverView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        try {
            LayoutInflater.from(mContext).inflate(R.layout.layout_cover, this, true);
            trackTest = findViewById(R.id.auto_test);
            testLayout = findViewById(R.id.testLayout);
            eglTest = findViewById(R.id.auto_test3);
            ivAudioCover = (ImageView) findViewById(R.id.iv_audiocover);
            firstFrameTest = findViewById(R.id.auto_test2);
            mRl_Container = (RelativeLayout) findViewById(R.id.relative_cover);

            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            tv_userName = (TextView) findViewById(R.id.tv_UserName);
            tv_userName.setTextColor(Color.WHITE);
            tv_userName.clearFocus();

//            textPaint=tv_userName.getPaint();
//            textPaint.setFakeBoldText(true);
            iv_Header = (ImageView) findViewById(R.id.iv_bg);
            iv_Audiolevel = (ImageView) findViewById(R.id.iv_audiolevel);
            Glide.with(mContext).asGif().load(R.drawable.sound).into(iv_Audiolevel);

            mGroupDrawable = (GradientDrawable) iv_Header.getBackground();

            int Height = SessionManager.getInstance().getInt(Utils.KEY_screeHeight);
            int width = SessionManager.getInstance().getInt(Utils.KEY_screeWidth);
            ViewGroup.LayoutParams para;
            para = iv_Header.getLayoutParams();
            para.height = Height;
            para.width = width;

            iv_Header.setLayoutParams(para);
            iv_Header.setOnClickListener(clickListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAudioLevel() {
        if (null == iv_Audiolevel) {
            return;
        }
        if (iv_Audiolevel.getVisibility() != VISIBLE) {
            iv_Audiolevel.setVisibility(VISIBLE);
        }
        iv_Audiolevel.bringToFront();
        tv_userName.bringToFront();
    }

    public void closeAudioLevel() {
        if (null == iv_Audiolevel) {
            return;
        }
        if (iv_Audiolevel.getVisibility() != INVISIBLE) {
            iv_Audiolevel.setVisibility(INVISIBLE);
        }
    }

    public void setUserInfo(String name, String id,String tag) {

        if (null != tv_userName && !TextUtils.isEmpty(name)) {
            tv_userName.setText(name.length() > 4 ? name.substring(0, 4) : name);
        }
        if (!TextUtils.isEmpty(tag) && !tag.equals(CenterManager.RONG_TAG))
        tv_userName.setText(name + "-" + mContext.getResources().getString(R.string.user_video_custom));

        if (!TextUtils.isEmpty(id)) {
            this.UserId = id;
        }
        setUserType();
    }

    /**
     * ??????????????????
     */
    private void setCoverTransoarent() {
        try {
            iv_Header.setVisibility(INVISIBLE);
//            tv_userName.setVisibility(INVISIBLE);
            closeLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????
     *
     * @param videoView
     */
    public void setRongRTCVideoView(RongRTCVideoView videoView) {
        this.rongRTCVideoView = videoView;
        if (null == mRl_Container) {
            return;
        }
        try {
            for (int i = 0; i < mRl_Container.getChildCount(); i++) {
                if (mRl_Container.getChildAt(i) instanceof RongRTCVideoView) {
                    mRl_Container.removeView(mRl_Container.getChildAt(i));
                }
            }
            LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            p.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRl_Container.addView(rongRTCVideoView, p);
            tv_userName.bringToFront();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBlinkVideoView() {
        for (int i = 0; i < mRl_Container.getChildCount(); i++) {
            if (mRl_Container.getChildAt(i) instanceof RongRTCVideoView) {
                mRl_Container.getChildAt(i).setVisibility(VISIBLE);
            }
        }
        setCoverTransoarent();
        ivAudioCover.setVisibility(INVISIBLE);
    }

    public void showUserHeader() {
        try {
            iv_Header.setVisibility(VISIBLE);
            tv_userName.setVisibility(VISIBLE);
            for (int i = 0; i < mRl_Container.getChildCount(); i++) {
                if (mRl_Container.getChildAt(i) instanceof RongRTCVideoView) {
                    RongRTCVideoView videoView = (RongRTCVideoView) mRl_Container.getChildAt(i);
                    if (videoView.getVisibility() == VISIBLE) {
                        closeLoading();
                    }
                    videoView.setVisibility(INVISIBLE);
                }
            }
            ivAudioCover.setVisibility(VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            FinLog.v(TAG, "coverView Error:" + e.getMessage());
        }
    }

    public void showLoading() {
        if (null == progressBar) {
            return;
        }
        progressBar.setVisibility(VISIBLE);
    }

    public void closeLoading() {
        if (null == progressBar) {
            return;
        }
        progressBar.setVisibility(GONE);
    }

    /**
     * ?????????????????? ????????????????????????
     */
    private void setUserType() {
//        String colorStr = " ";
//        colorStr = SessionManager.getInstance(Utils.getContext()).getString("color" + UserId);
//        if (!TextUtils.isEmpty(colorStr)) {
//            mGroupDrawable.setColor(Color.parseColor(colorStr));
//            return;
//        }
        if (mGroupDrawable != null) {
//            switch (new Random().nextInt(6)) {
//                case 0:
//                    colorStr = "#0066CC";
//                    break;
//                case 1:
//                    colorStr = "#009900";
//                    break;
//                case 2:
//                    colorStr = "#CC3333";
//                    break;
//                case 3:
//                    colorStr = "#CC9966";
//                    break;
//                case 4:
//                    colorStr = "#FF9900";
//                    break;
//                case 5:
//                    colorStr = "#CC33CC";
//                    break;
//            }
            mGroupDrawable.setColor(Color.BLACK);
        }
    }

    public RongRTCVideoView getRongRTCVideoView() {
        return rongRTCVideoView;
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if (rongRTCVideoView != null) {
                    mGroupDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                    rongRTCVideoView.performClick();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //todo delete
    public void setTrackisAdded() {
        Log.i(TAG,"setTrackisAdded");
        trackTest.setVisibility(VISIBLE);
        testLayout.bringToFront();
    }

    //todo delete
    public void setFirstDraw() {
        Log.i(TAG,"setTrackisAdded");
        firstFrameTest.setVisibility(VISIBLE);
        testLayout.bringToFront();
    }

    public void onCreateEglFailed() {
        Log.i(TAG,"onCreateEglFailed");
        eglTest.setVisibility(VISIBLE);
        testLayout.bringToFront();
    }
}
