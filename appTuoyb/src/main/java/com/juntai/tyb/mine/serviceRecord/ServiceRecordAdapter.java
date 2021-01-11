package com.juntai.tyb.mine.serviceRecord;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.tyb.bean.ServiceRecordBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  搜索到的托养人
 * @CreateDate: 2020/7/7 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 9:53
 */
public class ServiceRecordAdapter extends BaseQuickAdapter<ServiceRecordBean.DataBean, BaseViewHolder> {
    public ServiceRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceRecordBean.DataBean item) {

        String street =item.getStreetAddress();
        String village = item.getCommunityAddress();
        if (StringTools.isStringValueOk(street)) {
            if (street.length()>5) {
                street = street.substring(0,5);
            }
        }
        if (StringTools.isStringValueOk(village)) {
            if (village.length()>5) {
                village = village.substring(0,5);
            }
        }

        String title = String.format("%s%s%s%s%s", street," ",village, " ",
                item.getName());
        helper.setText(R.id.care_title_tv, title);
        helper.setText(R.id.care_content_tv, item.getIdNo());
        helper.setText(R.id.year_tag_tv, item.getYear() + "年度");
        ImageLoadUtil.loadImage(mContext, UrlFormatUtil.formatPicUrl(item.getPersonImg()),
                helper.getView(R.id.care_item_iv));
        helper.addOnClickListener(R.id.care_navigation_tv);
        helper.setText(R.id.care_navigation_tv, "导航");
    }
}
