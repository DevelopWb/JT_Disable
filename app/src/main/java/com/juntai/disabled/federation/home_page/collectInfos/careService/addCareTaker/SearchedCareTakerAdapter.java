package com.juntai.disabled.federation.home_page.collectInfos.careService.addCareTaker;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.CalendarUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.careTaker.SearchedPeopleBean;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  搜索到的托养人   SearchedPeopleBean.DataBean.DatasBean
 * @CreateDate: 2020/7/7 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 9:53
 */
public class SearchedCareTakerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SearchedCareTakerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper,String item) {
//        String street =item.getStreetAddress();
//        String village = item.getCommunityAddress();
//        if (StringTools.isStringValueOk(street)) {
//            if (street.length()>5) {
//                street = street.substring(0,5);
//            }
//        }
//        if (StringTools.isStringValueOk(village)) {
//            if (village.length()>5) {
//                village = village.substring(0,5);
//            }
//        }
//
//        String title = String.format("%s%s%s%s%s", street," ",village, " ",
//                item.getName());
//        helper.setText(R.id.care_title_tv, title);
//        helper.setText(R.id.care_content_tv, item.getIdNo());
//        helper.setText(R.id.year_tag_tv, item.getYear() + "年度");
//        ImageLoadUtil.loadImageWithCache(mContext, UrlFormatUtil.formatPicUrl(item.getPersonImg()),
//                R.mipmap.tyb_replace_icon,helper.getView(R.id.care_item_iv));
//
//        if (CalendarUtil.isCareble(Integer.parseInt(item.getYear()))) {
//            if (0==item.getStatusX()) {
//                //已添加
//                helper.setText(R.id.care_navigation_tv, "已添加");
//                helper.setTextColor(R.id.care_navigation_tv, ContextCompat.getColor(mContext,R.color.black));
//                helper.setBackgroundRes(R.id.care_navigation_tv,R.drawable.sp_filled_gray);
//            }else {
//                helper.setText(R.id.care_navigation_tv, "添加");
//                helper.setTextColor(R.id.care_navigation_tv, ContextCompat.getColor(mContext,R.color.white));
//
//                helper.setBackgroundRes(R.id.care_navigation_tv,R.drawable.sp_blue_square_button);
//            }
//        }else {
//            helper.setText(R.id.care_navigation_tv, "添加");
//            helper.setTextColor(R.id.care_navigation_tv, ContextCompat.getColor(mContext,R.color.black));
//            helper.setBackgroundRes(R.id.care_navigation_tv,R.drawable.sp_filled_gray);
//        }
//
//
    }
}
