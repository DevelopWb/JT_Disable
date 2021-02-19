package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.business.ChildBusinessesBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/23 9:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/23 9:22
 */
public class ChildRecoveryAdapter extends BaseQuickAdapter<ChildBusinessesBean.DataBean, BaseViewHolder> {
    public ChildRecoveryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildBusinessesBean.DataBean item) {
        helper.setText(R.id.item_business_title_tv, item.getName());
    }

}
