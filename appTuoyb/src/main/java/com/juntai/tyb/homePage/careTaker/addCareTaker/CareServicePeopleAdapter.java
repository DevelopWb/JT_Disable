package com.juntai.tyb.homePage.careTaker.addCareTaker;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.tyb.bean.careTaker.ServicePeoplesBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  托养服务人员适配器
 * @CreateDate: 2020/7/7 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 9:53
 */
public class CareServicePeopleAdapter extends BaseQuickAdapter<ServicePeoplesBean.DataBean.ServicerVOsVosBean, BaseViewHolder> {
    public CareServicePeopleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServicePeoplesBean.DataBean.ServicerVOsVosBean item) {
        ImageLoadUtil.loadCirImgNoCrash(mContext, UrlFormatUtil.formatPicUrl(item.getHead()),helper.getView(R.id.service_people_pic_iv),
                R.mipmap.default_head_icon,R.mipmap.default_head_icon);
        helper.setText(R.id.select_people_name_iv,item.getName());
        if (item.isSelected()) {
            helper.setImageResource(R.id.select_people_iv,R.mipmap.check_true_icon);
        }else {
            helper.setImageResource(R.id.select_people_iv,R.mipmap.check_false_icon);
        }
    }
}
