package com.juntai.disabled.federation.home_page.business;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器  子条目
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class BusinessItemChildAdapter extends BaseQuickAdapter<AllBusinessBean.DataBean.WorkMatterListBean,
        BaseViewHolder> {
    public BusinessItemChildAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllBusinessBean.DataBean.WorkMatterListBean item) {
        helper.setText(R.id.business_tv, item.getName());
        ImageLoadUtil.loadImage(mContext,item.getIcon(),helper.getView(R.id.business_iv));
    }
}
