package com.juntai.wisdom.dgjxb.home_page.camera.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadActivity;
import com.juntai.wisdom.basecomponent.bean.OpenLiveBean;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.AppHttpPath;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.BaseDataBean;
import com.juntai.wisdom.dgjxb.bean.CommentListBean;
import com.juntai.wisdom.dgjxb.bean.stream.StreamCameraDetailBean;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayContract;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayPresent;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailContract;
import com.juntai.wisdom.dgjxb.home_page.news.news_comment.EditCommentDialog;
import com.juntai.wisdom.dgjxb.home_page.news.news_comment.CommentAdapter;
import com.juntai.wisdom.dgjxb.home_page.news.news_comment.CommentChildActivity;
import com.juntai.wisdom.dgjxb.mine.mycollect.MyCollectPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 监控流媒体
 * @aouther ZhangZhenlong
 * @date 2020-10-23
 */
public class PlayerLiveActivity extends BaseDownLoadActivity<PlayPresent> implements PlayContract.IPlayView,
        View.OnClickListener, BaseDownLoadActivity.OnFileDownloaded {

    private PlayerView player;
    //    private String url = "rtmp://juntaikeji.net:1935/video/37130201561327011011";
    private PowerManager.WakeLock wakeLock;
    private Intent intent;
    public static String STREAM_CAMERA_ID = "stream";
    public static String STREAM_CAMERA_NUM = "stream_num";
    public static String STREAM_CAMERA_PIC = "camera_pic";
    //    public static String STREAM_CAMERA_URL = "stream_url";
    //    public static String STREAM_CAMERA_SESSION_ID = "sessionid";


    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    /**
     * 写评论···
     */
    private TextView mCommentEdittext;
    /**
     * 0
     */
    private TextView mInfoCommentCnt;
    /**
     * 0
     */
    private TextView mInfoLikeCnt;
    /**
     * 收藏
     */
    private TextView mInfoCollectCnt;
    /**
     * 分享
     */
    private TextView mInfoShareCnt;

    //是否收藏，收藏操作是否执行中，是否点赞，点赞操作是否执行中,详情未加载出来不可以点赞收藏
    boolean isCollect = false, isCollectIng = true, isLike = false, isLikeIng = true;
    int likeId, collectId;//点赞收藏id
    EditCommentDialog editCommentDialog;
    int page = 1, pagesize = 10;
    CommentAdapter commentAdapter;
    MyCollectPresent myCollectPresent;
    private int mCameraId;//
    private String mCameraNum;//
    private String mCameraPic;//
    private TextView tvstate;
    private StreamCameraDetailBean.DataBean mStreamCameraBean;//详情
    private TextView tvplace;
    private String playUrl;
    protected int commentPositon;//点赞评论位置


    @Override
    protected PlayPresent createPresenter() {
        return new PlayPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.simple_player_view_player;
    }

    @Override
    public void initView() {
        setFileDownLoadCallBack(this);
        //        Window window = getWindow();
        //        WindowManager.LayoutParams wlp = window.getAttributes();
        //        wlp.width = (MyApp.width / 10) * 9;
        //        wlp.height = (MyApp.H / 5) * 4;
        //        window.setAttributes(wlp);
        mCameraId = getIntent().getIntExtra(STREAM_CAMERA_ID, 0);
        mCameraNum = getIntent().getStringExtra(STREAM_CAMERA_NUM);
        mCameraPic = getIntent().getStringExtra(STREAM_CAMERA_PIC);

        myCollectPresent = new MyCollectPresent();
        myCollectPresent.setCallBack(this);
        editCommentDialog = new EditCommentDialog();
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

        mCommentEdittext = (TextView) findViewById(R.id.comment_edittext);
        mCommentEdittext.setOnClickListener(this);
        mInfoCommentCnt = (TextView) findViewById(R.id.info_comment_cnt);
        mInfoCommentCnt.setOnClickListener(this);
        mInfoLikeCnt = (TextView) findViewById(R.id.info_like_cnt);
        mInfoLikeCnt.setOnClickListener(this);
        mInfoCollectCnt = (TextView) findViewById(R.id.info_collect_cnt);
        mInfoCollectCnt.setOnClickListener(this);
        mInfoShareCnt = (TextView) findViewById(R.id.info_share_cnt);
        mInfoShareCnt.setOnClickListener(this);
        tvplace = findViewById(R.id.deviceplace);
        tvstate = findViewById(R.id.devicestate);
        initViewTopDrawable(mInfoCommentCnt, R.mipmap.info_liuyan, 15, 15);
        initViewTopDrawable(mInfoLikeCnt, R.drawable.info_like_bg_selector, 15, 15);
        initViewTopDrawable(mInfoCollectCnt, R.drawable.info_collect_bg_selector, 15, 15);
        initViewTopDrawable(mInfoShareCnt, R.mipmap.info_share, 15, 15);
        initViewLeftDrawable(mCommentEdittext, R.mipmap.edit_icon, 20, 20);
        //评论
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        commentAdapter = new CommentAdapter(R.layout.item_comment_news, new ArrayList());
        mRecyclerview.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_comment_huifu) {
                    //回复
                    editCommentDialog.show(getSupportFragmentManager(),
                            "commentDialog",
                            0,
                            mCameraId,
                            commentAdapter.getData().get(position).getId(),
                            commentAdapter.getData().get(position).getUserId(),
                            refreshListener, PlayerLiveActivity.this);
                } else if (view.getId() == R.id.item_comment_like) {
                    //评论点赞
                    commentPositon = position;
                    if (commentAdapter.getData().get(position).getIsLike() > 0) {//取消
                        mPresenter.like(-1, -1, 1, 0, commentAdapter.getData().get(position).getIsLike(),
                                InfoDetailContract.LIKE_COMMENT_TAG);
                    } else {
                        mPresenter.like(commentAdapter.getData().get(position).getIsLike(), MyApp.getUid(), 0,
                                2, commentAdapter.getData().get(position).getId(), InfoDetailContract.LIKE_COMMENT_TAG);
                    }
                } else if (view.getId() == R.id.item_comment_user_more) {
                    //更多--子评论
                    startActivity(new Intent(mContext, CommentChildActivity.class)
                            .putExtra("id", commentAdapter.getData().get(position).getId())
                            .putExtra("type", 0));
                }
            }
        });
        //刷新
        mSmartrefreshlayout.setEnableRefresh(false);
        //加载更多
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.getCommentList(mCameraId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
            }
        });
    }

    /**
     * 评论后刷新
     */
    EditCommentDialog.RefreshListener refreshListener = new EditCommentDialog.RefreshListener() {
        @Override
        public void refresh() {
            page = 1;
            mStreamCameraBean.setCommentCount(mStreamCameraBean.getCommentCount() + 1);
            mInfoCommentCnt.setText(String.valueOf(mStreamCameraBean.getCommentCount()));
            mPresenter.getCommentList(mCameraId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
        }
    };

    @Override
    public void initData() {
        //获取摄像头信息
        mPresenter.getStreamCameraDetail(mPresenter.getPublishMultipartBody()
                        .addFormDataPart("id", String.valueOf(mCameraId)).build(),
                PlayContract.GET_STREAM_CAMERA_DETAIL);
        mPresenter.getCommentList(mCameraId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);
        //常亮
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
                .hideBottonBar(false)
                .setForbidDoulbeUp(true)
                .setScaleType(PlayStateParams.fitxy).showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                                mCameraPic, ivThumbnail);
                    }
                });
        player.getBottonBarView().setVisibility(View.VISIBLE);
        player.getFullScreenView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(playUrl)) {
                    startActivity(new Intent(mContext, StreamCameraFullScreenActivity.class)
                            .putExtra(StreamCameraFullScreenActivity.STREAM_CAMERA_TITLE,
                                    mStreamCameraBean.getName() + "(" + mStreamCameraBean.getAddress() + ")")
                            .putExtra(StreamCameraFullScreenActivity.STREAM_CAMERA_URL, playUrl)
                            .putExtra(STREAM_CAMERA_NUM, mCameraNum)
                            .putExtra(STREAM_CAMERA_PIC, mCameraPic));
                } else {
                    ToastUtils.toast(mContext, "设备离线，暂无数据");
                }
            }
        });

        player.getCapturePicView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //截屏
                mPresenter.capturePic(mCameraNum, "1", PlayContract.GET_STREAM_CAMERA_CAPTURE);
            }
        });
        //打开流数据
        mPresenter.openStream(mCameraNum, "1", "rtmp", PlayContract.GET_URL_PATH);
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
        switch (tag) {
            case InfoDetailContract.GET_COMMENT_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                break;
            case InfoDetailContract.LIKE_TAG:
                //点赞失败
                mInfoLikeCnt.setSelected(isLike);
                break;
            case InfoDetailContract.COLLECT_TAG:
                //收藏失败
                mInfoCollectCnt.setSelected(isCollect);
                break;
            default:
                super.onError(tag, o);
                break;
        }
    }

    /**
     * 初始化评论view
     */
    public void viewSetData() {
        isCollectIng = false;
        isLikeIng = false;
        likeId = mStreamCameraBean.getIsLike();
        collectId = mStreamCameraBean.getIsCollect();
        //是否点赞收藏
        isCollect = mStreamCameraBean.getIsCollect() != 0;
        isLike = mStreamCameraBean.getIsLike() != 0;
        mInfoCollectCnt.setSelected(isCollect);
        mInfoLikeCnt.setSelected(isLike);
        mInfoCommentCnt.setText(String.valueOf(mStreamCameraBean.getCommentCount()));
        mInfoLikeCnt.setText(String.valueOf(mStreamCameraBean.getLikeCount()));
    }

    @Override
    public void onClick(View v) {
        if (MyApp.isFastClick()) {
            ToastUtils.warning(this, "操作过于频繁！");
            return;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.comment_edittext://评论
                editCommentDialog.show(getSupportFragmentManager(), "commentDialog", 0,
                        mStreamCameraBean.getId(), -1, -1, refreshListener, PlayerLiveActivity.this);
                break;
            case R.id.info_like_cnt://点赞
                if (isLike) {//取消
                    mPresenter.like(-1, -1, 1, 0, likeId, InfoDetailContract.LIKE_TAG);
                } else {
                    mPresenter.like(likeId, MyApp.getUid(), 0, 1, mStreamCameraBean.getId(),
                            InfoDetailContract.LIKE_TAG);
                }
                mInfoLikeCnt.setSelected(!isLike);
                break;
            case R.id.info_collect_cnt://收藏
                if (isCollect) {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(collectId);
                    myCollectPresent.deleteCollecListCamera(-1, -1, 1, -1, -1, ids, InfoDetailContract.COLLECT_TAG);
                } else {
                    myCollectPresent.deleteCollecListCamera(collectId, MyApp.getUid(), 0, 0, mStreamCameraBean.getId(), null,
                            InfoDetailContract.COLLECT_TAG);
                }
                mInfoCollectCnt.setSelected(!isCollect);
                break;
            case R.id.info_share_cnt://分享
                ToastUtils.warning(this, "敬请期待！");
                break;
        }
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
        switch (tag) {
            case PlayContract.GET_URL_PATH:
                OpenLiveBean openLiveBean = (OpenLiveBean) o;
                int errorCode = openLiveBean.getErrcode();
                if (errorCode < 0) {
                    player.isOffLine(true);
                    tvstate.append("离线");
                    ToastUtils.toast(mContext, "设备离线，无数据");
                    return;
                } else {
                    player.isOffLine(false);
                    tvstate.append("在线");
                    //调用截图的接口  然后 上传封面图
//                    mPresenter.capturePic(mCameraNum, "1", PlayContract.GET_STREAM_CAMERA_THUMBNAIL);
                }
                playUrl = openLiveBean.getVideourl();
                if (StringTools.isStringValueOk(playUrl)) {
                    if (playUrl.contains("//")) {
                        playUrl = playUrl.substring(playUrl.indexOf("//") + 2);
                        playUrl = playUrl.substring(playUrl.indexOf("/"));
                        playUrl = AppHttpPath.BASE_CAMERA_DNS + playUrl;
                    }
                }
                player.setPlaySource(playUrl).startPlay();
                intent = new Intent(this, KeepAliveService.class).putExtra("sessionId", openLiveBean.getStrsessionid());
                startService(intent);
                break;
            case PlayContract.GET_STREAM_CAMERA_THUMBNAIL:
                //截图  每次打开 更新缩略图
                OpenLiveBean captureBean = (OpenLiveBean) o;
                int errCode = captureBean.getErrcode();
                if (errCode < 0) {
                    //截图失败
                } else {
                    //截图成功 上传封面图（将图片路径上传后台）  http://60.213.43.241:8080/image/37131201561327001001.jpg
                    String imagePath = captureBean.getImageurl();
                    if (!TextUtils.isEmpty(imagePath)) {
                        if (imagePath.contains("image")) {
                            //截图成功 保存本地
                            downloadFileContentUnique(FileCacheUtils.STREAM_THUMBNAIL, imagePath);
                        }
                    }
                }
                break;
            case PlayContract.GET_STREAM_CAMERA_CAPTURE:
                //截图并保存本地
                OpenLiveBean captureBeanToLocal = (OpenLiveBean) o;
                int errMsg = captureBeanToLocal.getErrcode();
                if (errMsg < 0) {
                    //截图失败
                    ToastUtils.toast(mContext, "截图失败");
                } else {
                    //截图成功 保存本地
                    String imagePath = captureBeanToLocal.getImageurl();
                    if (!TextUtils.isEmpty(imagePath)) {
                        downloadFileContent(FileCacheUtils.STREAM_CAPTURE + mStreamCameraBean.getName() + "(" + mStreamCameraBean.getAddress() + ")", imagePath);
                    }
                }
                break;
            case PlayContract.UPLOAD_CAMERA_CAPTURE:
                //上传封面图
                break;
            case PlayContract.GET_STREAM_CAMERA_DETAIL:
                StreamCameraDetailBean detailBean = (StreamCameraDetailBean) o;
                if (detailBean != null) {
                    mStreamCameraBean = detailBean.getData();
                    tvplace.append(mStreamCameraBean.getName() + "(" + mStreamCameraBean.getAddress() + ")");
                }
                viewSetData();
                break;
            case InfoDetailContract.GET_COMMENT_LIST:
                //获取评论列表
                CommentListBean commentListBean = (CommentListBean) o;
                if (page == 1) {
                    commentAdapter.getData().clear();
                }
                if (commentListBean.getData().getDatas().size() < pagesize) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                commentAdapter.addData(commentListBean.getData().getDatas());
                break;
            case InfoDetailContract.LIKE_TAG:
                //点赞
                mStreamCameraBean.setLikeCount(isLike ? mStreamCameraBean.getLikeCount() - 1 :
                        mStreamCameraBean.getLikeCount() + 1);
                mInfoLikeCnt.setText(String.valueOf(mStreamCameraBean.getLikeCount()));
                isLike = !isLike;
                likeId = ((BaseDataBean) o).getData();
                break;
            case InfoDetailContract.LIKE_COMMENT_TAG:
                if (commentAdapter.getData().get(commentPositon).getIsLike() > 0) {//取消
                    commentAdapter.getData().get(commentPositon).setIsLike(0);
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() - 1);
                } else {
                    commentAdapter.getData().get(commentPositon).setIsLike(((BaseDataBean) o).getData());
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() + 1);
                }
                commentAdapter.notifyDataSetChanged();
                break;
            case InfoDetailContract.COLLECT_TAG:
                //收藏
                isCollect = !isCollect;
                collectId = ((BaseDataBean) o).getData();
                break;
            default:
                break;
        }
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
        setFileDownLoadCallBack(null);
    }

    @Override
    public void onFileDownloaded(String filePath) {
        if (!filePath.contains(FileCacheUtils.STREAM_CAPTURE)) {
            String fileName = filePath.substring(filePath.lastIndexOf(
                    "/") + 1, filePath.lastIndexOf("."));
            //压缩图片作为缩略图
            compressImage(filePath, FileCacheUtils.STREAM_THUMBNAIL, fileName, new OnImageCompressedPath() {
                @Override
                public void compressedImagePath(File file) {
                    if (mPresenter == null) {
                        return;
                    }
                    MultipartBody.Builder builder = mPresenter.getPublishMultipartBody()
                            .addFormDataPart("id", String.valueOf(mCameraId))
                            .addFormDataPart("number", String.valueOf(mCameraNum))
                            .addFormDataPart("file", "file", RequestBody.create(MediaType.parse("file"), file));
                    mPresenter.uploadStreamCameraThumbPic(builder.build(), PlayContract.UPLOAD_CAMERA_CAPTURE);
                }
            });
        }

    }
}
