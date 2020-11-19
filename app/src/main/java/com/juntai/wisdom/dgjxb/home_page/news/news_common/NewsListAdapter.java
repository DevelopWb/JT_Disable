package com.juntai.wisdom.dgjxb.home_page.news.news_common;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.news.NewsListBean;

import java.util.List;

/**
 * Describe:资讯列表
 * Create by zhangzhenlong
 * 2020-7-29
 * email:954101549@qq.com
 */
public class NewsListAdapter extends BaseQuickAdapter<NewsListBean.DataBean.DatasBean, BaseViewHolder> {
    public NewsListAdapter(List<NewsListBean.DataBean.DatasBean> data) {
        super(data);
        //设置布局
        setMultiTypeDelegate(new MultiTypeDelegate<NewsListBean.DataBean.DatasBean>() {
            @Override
            protected int getItemType(NewsListBean.DataBean.DatasBean beanDate) {
                return beanDate.getTypeId();
            }
        });
//
//      //添加布局
        getMultiTypeDelegate()
                .registerItemType(NewsListBean.TYPE_ZERO, R.layout.item_news_video_type)
                .registerItemType(NewsListBean.TYPE_ONE, R.layout.item_news_video_type)
                .registerItemType(NewsListBean.TYPE_TWO, R.layout.item_news_photo_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsListBean.DataBean.DatasBean item) {
        //获取视图
        int itemViewType = helper.getItemViewType();
        helper.setText(R.id.item_title, item.getTitle());
        if (item.isLooked()){
            helper.setAlpha(R.id.item_title, 0.4f);
        }else {
            helper.setAlpha(R.id.item_title, 1f);
        }
        helper.setText(R.id.item_des, item.getNickname() + "\u3000" + item.getCommentCount() + "评论\u3000" + item.getReleaseTime());
        //判断视图
        switch (itemViewType){
            case NewsListBean.TYPE_ZERO:
                break;
            case NewsListBean.TYPE_ONE:
                //视频
                ImageLoadUtil.loadImageForList(mContext, item.getCoverUrl(), helper.getView(R.id.item_image),1, 350, 200, 7, R.drawable.nopicture_video, R.drawable.nopicture_video);
                break;
            case NewsListBean.TYPE_TWO:
                //图文
                if (item.getFileList().size() > 0 && item.getFileList().size() < 2){
//                    helper.setGone(R.id.item_photo_layout,false);
                    helper.setVisible(R.id.item_image4,true);
                    helper.setGone(R.id.item_photo_layout,false);
                    ImageLoadUtil.loadRoundCornerImg(mContext, item.getFileList().get(0).getFileUrl(), helper.getView(R.id.item_image4),0,2);
                }else if (item.getFileList().size() > 1){
                    helper.setVisible(R.id.item_photo_layout,true);
                    helper.setGone(R.id.item_image4,false);
                    helper.setVisible(R.id.item_image1,false);
                    helper.setVisible(R.id.item_image2,false);
                    helper.setVisible(R.id.item_image3,false);
                    for (int i = 0; i < item.getFileList().size(); i++){
                        switch (i){
                            case 0:
                                helper.setVisible(R.id.item_image1,true);
                                ImageLoadUtil.loadRoundCornerImg(mContext, item.getFileList().get(0).getFileUrl(), helper.getView(R.id.item_image1),0,2);
                                break;
                            case 1:
                                helper.setVisible(R.id.item_image2,true);
                                ImageLoadUtil.loadRoundCornerImg(mContext, item.getFileList().get(1).getFileUrl(), helper.getView(R.id.item_image2),0,2);
                                break;
                            case 2:
                                helper.setVisible(R.id.item_image3,true);
                                ImageLoadUtil.loadRoundCornerImg(mContext, item.getFileList().get(2).getFileUrl(), helper.getView(R.id.item_image3),0,2);
                                break;
                        }
                    }
                } else {
                    helper.setGone(R.id.item_photo_layout,false);
                    helper.setGone(R.id.item_image4,false);
                }
                break;
        }
    }
}
