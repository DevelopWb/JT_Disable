package com.juntai.disabled.federation.home_page.news.news_comment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.CommentBean;

import java.util.List;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-3-19
 * email:954101549@qq.com
 */
public class CommentChildAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {
    public CommentChildAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.setText(R.id.item_comment_user_name,item.getNickname());
        helper.setText(R.id.item_comment_content,item.getContent());
        helper.setText(R.id.item_comment_time,item.getGmtCreate());
        //
        ImageLoadUtil.loadCircularImage(mContext, item.getHeadPortrait(),
                R.mipmap.my_hint_head,R.mipmap.my_hint_head,helper.getView(R.id.item_comment_user_image));
    }
}
