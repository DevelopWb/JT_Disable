package com.juntai.disabled.federation.home_page.news.news_info;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.customview.WarnDialog;
import com.juntai.disabled.federation.bean.news.NewsDetailBean;
import com.juntai.disabled.federation.home_page.news.NewsContract;
import com.juntai.disabled.federation.home_page.news.NewsPresent;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.video.player.PlayerActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

/**
 * @description 视频资讯详情
 * @aouther ZhangZhenlong
 * @date 2020-8-13
 */
public class NewsVideoInfoActivity extends BaseNewsInfoActivity {

    private StandardGSYVideoPlayer mInfoVideo;
    private View mPartingLine;
    private LinearLayout mContentTag;
    /**
     * 内容
     */
    private TextView mInfoContent;

    OrientationUtils orientationUtils;
    boolean isPlay,isPause;
    private NestedScrollView mInfoScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止系统截屏、录屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_news_video_info;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        getToolbar().setVisibility(View.GONE);
        mInfoVideo = (StandardGSYVideoPlayer) findViewById(R.id.info_video);
        mPartingLine = (View) findViewById(R.id.parting_line);
        mInfoContent = (TextView) findViewById(R.id.info_content);
        mInfoScrollView = findViewById(R.id.info_scroll_view);
        mContentTag = findViewById(R.id.content_tag);
        mInfoScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 > 10){
                    mPartingLine.setVisibility(View.VISIBLE);
                }else {
                    mPartingLine.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getNewsInfo(newsId, NewsContract.GET_NEWS_INFO);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.info_guanzhu_btn:
                //关注
                if (newsDetailBean.getIsFollow() > 0 ){
                    //删除
                    mPresenter.addFollowOrDelete(2, newsDetailBean.getUserId(), NewsContract.DELETE_FOLLOW);
                }else {
                    //添加
                    mPresenter.addFollowOrDelete(1, newsDetailBean.getUserId(), NewsContract.ADD_FOLLOW);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag){
            case NewsContract.GET_NEWS_INFO:
                NewsDetailBean news = (NewsDetailBean) o;
                newsDetailBean = news.getData();
                if (newsDetailBean == null || newsDetailBean.getId() == 0){
                    ToastUtils.warning(this,"数据已删除！");
                    finish();
                }else {
                    viewSetData();
                    mInfoTimeRead.setText(newsDetailBean.getBrowseNum() + "次播放\u3000" + newsDetailBean.getGmtCreate() + "发布");
                    if (StringTools.isStringValueOk(newsDetailBean.getVideoUrl())){
                        initVideo(newsDetailBean.getVideoUrl(),newsDetailBean.getTitle(), newsDetailBean.getCoverUrl());
                        startVideo();
                    }
                    if (StringTools.isStringValueOk(newsDetailBean.getContent())){
                        mContentTag.setVisibility(View.VISIBLE);
                        mInfoContent.setText(newsDetailBean.getContent());
                        mInfoContent.setVisibility(View.VISIBLE);
                    }else {
                        mContentTag.setVisibility(View.GONE);
                        mInfoContent.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    /**
     * 开始播放
     */
    private void startVideo(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            WarnDialog warnDialog = new WarnDialog(mContext).builder().setTextStyle();
            warnDialog.getContentTextView().setMovementMethod(LinkMovementMethod.getInstance());
            warnDialog.setCanceledOnTouchOutside(false)
                    .setTitle("提示")
                    .setContent(getResources().getString(R.string.warn_not_wifi))
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    })
                    .setOkButton("继续", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mInfoVideo.startPlayLogic();
                        }
                    }).show();
        } else if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            mInfoVideo.startPlayLogic();
        } else {
            ToastUtils.warning(mContext,getString(R.string.bad_network));
        }
    }

    /**
     * 初始化播放
     * @param path
     */
    private void initVideo(String path, String title, String coverUrl) {
        //RTMP播放需切换至exo播放
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        mInfoVideo.getLayoutParams().height = MyApp.H / 3;
        mInfoVideo.setUp(path, false, "");
        //增加封面
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext.getApplicationContext()).asBitmap().load(coverUrl).into(imageView);
        mInfoVideo.setThumbImageView(imageView);
        //增加title
        mInfoVideo.getTitleTextView().setVisibility(View.VISIBLE);
        mInfoVideo.setIsTouchWiget(true);//非全屏滑动触摸有效
        //设置返回键
        mInfoVideo.getBackButton().setVisibility(View.VISIBLE);
        mInfoVideo.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        //设置旋转
//        orientationUtils = new OrientationUtils(this, mInfoVideo);
//        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        mInfoVideo.getFullscreenButton().setOnClickListener(new ViewBase.OnClickListener() {
//            @Override
//            public void onClick(ViewBase v) {
//                orientationUtils.resolveByClick();
//            }
//        });
        //是否可以滑动调整
//        mInfoVideo.setIsTouchWiget(false);
        //设置返回按键功能
//        mInfoVideo.getBackButton().setVisibility(ViewBase.GONE);
        //设置全屏按键功能
        mInfoVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("playPath", path);
                startActivity(intent);
//                mInfoVideo.startWindowFullscreen(mContext, false, true);
            }
        });
        mInfoVideo.setVideoAllCallBack(new GSYSampleCallBack(){
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                isPlay = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        mInfoVideo.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInfoVideo.getCurrentPlayer().onVideoResume(false);
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        if (isPlay) {
            mInfoVideo.getCurrentPlayer().release();
            mInfoVideo.release();
        }
        mInfoVideo.setVideoAllCallBack(null);
        if (orientationUtils != null){
            orientationUtils.releaseListener();
        }
        mInfoVideo = null;
        super.onDestroy();
    }
}
