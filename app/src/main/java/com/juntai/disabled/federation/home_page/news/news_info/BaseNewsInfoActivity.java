package com.juntai.disabled.federation.home_page.news.news_info;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.BaseDataBean;
import com.juntai.disabled.federation.bean.CommentListBean;
import com.juntai.disabled.federation.bean.news.NewsDetailBean;
import com.juntai.disabled.federation.home_page.InfoDetailContract;
import com.juntai.disabled.federation.home_page.InfoDetailPresent;
import com.juntai.disabled.federation.home_page.news.NewsContract;
import com.juntai.disabled.federation.home_page.news.NewsPresent;
import com.juntai.disabled.federation.home_page.news.news_comment.EditCommentDialog;
import com.juntai.disabled.federation.home_page.news.news_comment.CommentAdapter;
import com.juntai.disabled.federation.home_page.news.news_comment.CommentChildActivity;
import com.juntai.disabled.federation.home_page.news.news_personal_homepage.PersonalHomepageActivity;
import com.juntai.disabled.federation.mine.mycollect.MyCollectPresent;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.ToolShare;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Describe:资讯详情展示基类
 * Create by zhangzhenlong
 * 2020-8-13
 * email:954101549@qq.com
 */
public abstract class BaseNewsInfoActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.BaseINewsView, View.OnClickListener{
    protected ImageView mInfoUserImage;
    /**
     * 姓名
     */
    protected TextView mInfoUserName;
    /**
     * 218次播放\u30002019年5月19日发布
     */
    protected TextView mInfoTimeRead;
    /**info_comment_recycleview
     * 关注
     */
    protected TextView mInfoGuanzhuBtn;
    protected RecyclerView mInfoCommentRecycleview;
    protected SmartRefreshLayout mInfoSmartrefreshlayout;
    /**
     * 写评论···
     */
    protected TextView mCommentEdittext;
    protected ImageView mInfoCommentIv;
    protected ImageView mInfoCollectIv;
    protected ImageView mInfoLikeIv;
    protected ImageView mInfoShareIv;
    /**
     * 0
     */
    protected TextView mInfoCommentCount;
    /**
     * 6
     */
    protected TextView mInfoLikeCount;
    protected int newsId;//资讯id

    protected NewsDetailBean.DataBean newsDetailBean;
    //是否收藏，收藏操作是否执行中，是否点赞，点赞操作是否执行中,详情未加载出来不可以点赞收藏
    protected boolean isCollect = false,isCollectIng = true,isLike = false,isLikeIng = true;
    protected int likeId,collectId;//点赞收藏id
    protected EditCommentDialog editCommentDialog;
    protected String shareContent = "";
    protected int page = 1,pagesize = 10;
    protected int commentPositon;//点赞评论位置

    CommentAdapter commentAdapter;
    MyCollectPresent myCollectPresent;
    InfoDetailPresent infoDetailPresent;

    @Override
    public void initView() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        newsId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        mInfoUserImage = (ImageView) findViewById(R.id.info_user_image);
        mInfoUserImage.setOnClickListener(this);
        mInfoUserName = (TextView) findViewById(R.id.info_user_name);
        mInfoTimeRead = (TextView) findViewById(R.id.info_time_read);
        mInfoGuanzhuBtn = (TextView) findViewById(R.id.info_guanzhu_btn);
        mInfoGuanzhuBtn.setOnClickListener(this);
        mInfoCommentRecycleview = (RecyclerView) findViewById(R.id.info_comment_recycleview);
        mInfoSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.info_smartrefreshlayout);
        mCommentEdittext = (TextView) findViewById(R.id.comment_edittext);
        mCommentEdittext.setOnClickListener(this);
        mInfoCommentIv = (ImageView) findViewById(R.id.info_comment_iv);
        mInfoCommentIv.setOnClickListener(this);
        mInfoCollectIv = (ImageView) findViewById(R.id.info_collect_iv);
        mInfoCollectIv.setOnClickListener(this);
        mInfoLikeIv = (ImageView) findViewById(R.id.info_like_iv);
        mInfoLikeIv.setOnClickListener(this);
        mInfoShareIv = (ImageView) findViewById(R.id.info_share_iv);
        mInfoShareIv.setOnClickListener(this);
        mInfoCommentCount = (TextView) findViewById(R.id.info_comment_count);
        mInfoLikeCount = (TextView) findViewById(R.id.info_like_count);

        infoDetailPresent = new InfoDetailPresent();
        infoDetailPresent.setCallBack(this);
        myCollectPresent = new MyCollectPresent();
        myCollectPresent.setCallBack(this);
        editCommentDialog = new EditCommentDialog();

        //评论
        mInfoCommentRecycleview = (RecyclerView) findViewById(R.id.info_comment_recycleview);
        mInfoCommentRecycleview.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(R.layout.item_comment_news, new ArrayList());
        commentAdapter.setEmptyView(getAdapterEmptyView("还没有评论呢", -1));
        mInfoCommentRecycleview.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_comment_huifu){
                    //回复
                    editCommentDialog.show(getSupportFragmentManager(),
                            "commentDialog",
                            8,
                            newsId,
                            commentAdapter.getData().get(position).getId(),
                            commentAdapter.getData().get(position).getUserId(),
                            refreshListener, BaseNewsInfoActivity.this);
                }else if (view.getId() == R.id.item_comment_like){
                    //评论点赞
                    commentPositon = position;
                    if (commentAdapter.getData().get(position).getIsLike() > 0){//取消
                        mPresenter.like(-1,-1,1,0,commentAdapter.getData().get(position).getIsLike(),InfoDetailContract.LIKE_COMMENT_TAG);
                    }else {
                        mPresenter.like(commentAdapter.getData().get(position).getIsLike(), MyApp.getUid(),0,2,commentAdapter.getData().get(position).getId(),InfoDetailContract.LIKE_COMMENT_TAG);
                    }
                }else if (view.getId() == R.id.item_comment_user_more){
                    //更多--子评论
                    startActivity(new Intent(mContext, CommentChildActivity.class)
                            .putExtra("id",commentAdapter.getData().get(position).getId())
                            .putExtra("type", 8));
                }
            }
        });
        //刷新
        mInfoSmartrefreshlayout.setEnableRefresh(false);
        //加载更多
        mInfoSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page ++;
                mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
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
            newsDetailBean.setCommentCount(newsDetailBean.getCommentCount() + 1);
            mInfoCommentCount.setText(String.valueOf(newsDetailBean.getCommentCount()));
            mInfoCommentCount.setVisibility(View.VISIBLE);
            mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
        }
    };

    @Override
    public void initData() {
        mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
    }

    /**
     * 根据数据内容初始化view
     */
    public void viewSetData(){
        isCollectIng = false;
        isLikeIng = false;
        likeId = newsDetailBean.getIsLike();
        collectId = newsDetailBean.getIsCollect();

        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), newsDetailBean.getHeadPortrait(),
                R.mipmap.my_hint_head,R.mipmap.my_hint_head,mInfoUserImage);
        mInfoUserName.setText(newsDetailBean.getNickname());

        //是否点赞收藏
        isCollect = newsDetailBean.getIsCollect() != 0;
        isLike = newsDetailBean.getIsLike() != 0;
        mInfoCollectIv.setSelected(isCollect);
        mInfoLikeIv.setSelected(isLike);
        if (newsDetailBean.getCommentCount() == 0){
            mInfoCommentCount.setVisibility(View.GONE);
        }else {
            mInfoCommentCount.setVisibility(View.VISIBLE);
            mInfoCommentCount.setText(String.valueOf(newsDetailBean.getCommentCount()));
        }

        if (newsDetailBean.getLikeCount() == 0){
            mInfoLikeCount.setVisibility(View.GONE);
        }else {
            mInfoLikeCount.setVisibility(View.VISIBLE);
            mInfoLikeCount.setText(String.valueOf(newsDetailBean.getLikeCount()));
        }


        if (newsDetailBean.getUserId() == MyApp.getUid()){
            mInfoGuanzhuBtn.setVisibility(View.INVISIBLE);
        }else {
            mInfoGuanzhuBtn.setVisibility(View.VISIBLE);
            if (newsDetailBean.getIsFollow() > 0){
                mInfoGuanzhuBtn.setText("已关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
            }else {
                mInfoGuanzhuBtn.setText("关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_blue);
            }
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case InfoDetailContract.GET_COMMENT_LIST:
                CommentListBean commentListBean = (CommentListBean) o;
                if (page == 1){
                    commentAdapter.getData().clear();
                }
                if (commentListBean.getData().getDatas().size() < pagesize){
                    mInfoSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mInfoSmartrefreshlayout.setNoMoreData(false);
                }
                mInfoSmartrefreshlayout.finishRefresh();
                mInfoSmartrefreshlayout.finishLoadMore();
                commentAdapter.addData(commentListBean.getData().getDatas());
                break;
            case InfoDetailContract.GET_COMMENT_CHILD_LIST:
                break;
            case InfoDetailContract.LIKE_TAG:
                newsDetailBean.setLikeCount(isLike? newsDetailBean.getLikeCount() -1 : newsDetailBean.getLikeCount() + 1);
                if (newsDetailBean.getLikeCount() == 0){
                    mInfoLikeCount.setVisibility(View.GONE);
                }else {
                    mInfoLikeCount.setVisibility(View.VISIBLE);
                    mInfoLikeCount.setText(String.valueOf(newsDetailBean.getLikeCount()));
                }
                isLike = !isLike;
                likeId = ((BaseDataBean) o).getData();
                break;
            case InfoDetailContract.LIKE_COMMENT_TAG:
                if (commentAdapter.getData().get(commentPositon).getIsLike() > 0){//取消
                    commentAdapter.getData().get(commentPositon).setIsLike(0);
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() - 1);
                }else {
                    commentAdapter.getData().get(commentPositon).setIsLike(((BaseDataBean) o).getData());
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() + 1);
                }
                commentAdapter.notifyDataSetChanged();
                break;
            case InfoDetailContract.COLLECT_TAG:
                isCollect = !isCollect;
                collectId = ((BaseDataBean) o).getData();
                break;
            case NewsContract.DELETE_FOLLOW:
                newsDetailBean.setIsFollow(0);
                mInfoGuanzhuBtn.setText("关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_blue);
                break;
            case NewsContract.ADD_FOLLOW://添加成功
                newsDetailBean.setIsFollow(1);
                mInfoGuanzhuBtn.setText("已关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag){
            case InfoDetailContract.GET_NEWS_DETAIL:
                finish();
                break;
            case InfoDetailContract.GET_COMMENT_LIST:
                mInfoSmartrefreshlayout.finishRefresh();
                mInfoSmartrefreshlayout.finishLoadMore();
                break;
            case InfoDetailContract.LIKE_TAG:
                //点赞失败
                mInfoLikeIv.setSelected(isLike);
                break;
            case InfoDetailContract.COLLECT_TAG:
                //收藏失败
                mInfoCollectIv.setSelected(isCollect);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (MyApp.isFastClick()){
            ToastUtils.warning(this,"操作过于频繁！");
            return;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.info_user_image:
                //作者头像
                startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY, newsDetailBean.getUserId()));
                break;
            case R.id.comment_edittext:
                //弹窗评论窗
                editCommentDialog.show(getSupportFragmentManager(),"commentDialog",8,
                        newsId,-1,-1,refreshListener,BaseNewsInfoActivity.this);
                break;
            case R.id.info_comment_iv:
                //
                break;
            case R.id.info_collect_iv:
                //收藏
                if (isCollect){
                    List<Integer> ids = new ArrayList<>();
                    ids.add(collectId);
                    myCollectPresent.deleteCollecListNews(-1,-1,1, -1, -1, ids, InfoDetailContract.COLLECT_TAG);
                }else {
                    myCollectPresent.deleteCollecListNews(collectId, MyApp.getUid(),0, 8, newsId, null, InfoDetailContract.COLLECT_TAG);
                }
                mInfoCollectIv.setSelected(!isCollect);
                break;
            case R.id.info_like_iv:
                //点赞
                if (isLike){//取消
                    mPresenter.like(-1,-1,1,0,likeId,InfoDetailContract.LIKE_TAG);
                }else {
                    mPresenter.like(likeId, MyApp.getUid(),0,1,newsId,InfoDetailContract.LIKE_TAG);
                }
                mInfoLikeIv.setSelected(!isLike);
                break;
            case R.id.info_share_iv:
                //分享
                initShare();
                break;
        }
    }

    /**
     * 分享初始化
     */
    public void initShare() {
        if (newsDetailBean != null){
            shareContent = newsDetailBean.getTitle();
            if (newsDetailBean.getFileList().size()>0){
                String picPath = null;
                if (newsDetailBean.getFileList().size() > 0 ){
                    picPath = newsDetailBean.getFileList().get(0);
                }else {
                    picPath = getString(R.string.logo_url);
                }
                ToolShare.shareForMob(mContext,
                        newsDetailBean.getTitle(),
                        newsDetailBean.getShareUrl(),
                        shareContent,
                        picPath,
                        callback);
            }else {
                ToolShare.shareForMob(mContext,
                        newsDetailBean.getTitle(),
                        newsDetailBean.getShareUrl(),
                        shareContent,
                        getString(R.string.logo_url),
                        callback);
            }
        }
    }

    /**
     * 分享外部回调
     */
    protected PlatformActionListener callback = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //  分享成功后的操作或者提示
            ToastUtils.success(mContext,"分享成功！");
            infoDetailPresent.addShare(0,8,newsId,InfoDetailContract.SHARE_TAG);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            //  失败，打印throwable为错误码
            ToastUtils.warning(mContext,"分享失败！");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            //  分享取消操作
            ToastUtils.warning(mContext,"分享已取消！");
        }
    };

    @Override
    protected void onDestroy() {
        try {
            callback = null;
            infoDetailPresent.setCallBack(null);
            myCollectPresent.setCallBack(null);
            commentAdapter.getData().clear();
        }catch (Exception e){
        }
        super.onDestroy();
    }
}
