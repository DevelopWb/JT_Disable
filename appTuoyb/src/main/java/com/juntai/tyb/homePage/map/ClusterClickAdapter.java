package com.juntai.tyb.homePage.map;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.tyb.bean.careTaker.CareRecordPositionBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UrlFormatUtil;

/**
 * author:wong  地图底部弹窗的适配器
 * Date: 2019/4/19
 * Description:
 */
public class ClusterClickAdapter extends BaseQuickAdapter<MapClusterItem, BaseViewHolder> {
    public ClusterClickAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapClusterItem item) {
        ImageView imageView = helper.getView(R.id.care_item_iv);
        helper.setGone(R.id.care_navigation_tv,false);
        helper.setBackgroundRes(R.id.year_tag_tv,0);
        helper.setTextColor(R.id.year_tag_tv,ContextCompat.getColor(mContext,R.color.text_default_color));
        helper.getView(R.id.year_tag_tv).setPadding(0,0,0,0);
        switch (item.getType()) {
            case MapClusterItem.STREAM_CAMERA:
//                //标识 区分硬盘录像机和独立摄像头
//                helper.setGone(R.id.care_navigation_tv,true);
//                if (0==item.streamCamera.getFlag()) {
//                    //网络视频录像机
//                    helper.setText(R.id.care_navigation_tv,"NVR");
//                    helper.setBackgroundColor(R.id.care_navigation_tv,
//                            ContextCompat.getColor(mContext.getApplicationContext(),R.color.blue));
//                }else{
//                    //网络摄像机IP
//                    helper.setText(R.id.care_navigation_tv,"IPC");
//                    helper.setBackgroundColor(R.id.care_navigation_tv,
//                            ContextCompat.getColor(mContext.getApplicationContext(),R.color.green));
//                }

                ImageLoadUtil.loadImageNoCrash(mContext.getApplicationContext(),
                        item.streamCamera.getEzopen(),imageView);
                helper.setText(R.id.care_title_tv, item.streamCamera.getPlace())
                        .setText(R.id.care_content_tv, "编号:" + item.streamCamera.getNumber())
                        .setText(R.id.year_tag_tv, item.streamCamera.getRemark());
                break;
            case MapClusterItem.PEOPLE:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageLoadUtil.loadCirImgNoCrash(mContext.getApplicationContext(),
                        UrlFormatUtil.formatPicUrl(item.peoplePosition.getHead()),
                        imageView,
                        R.mipmap.default_head_icon,
                        R.mipmap.default_head_icon);
                helper.setText(R.id.care_title_tv, "姓名:" + item.peoplePosition.getName())
                        .setText(R.id.care_content_tv, "街道:" + item.peoplePosition.getStreet())
//                        .setText(R.id.item_case_content, "状态:" + "离线")
                        .setText(R.id.year_tag_tv, "联系方式:" + item.peoplePosition.getPhone());
                break;
            case MapClusterItem.HEALTH_POSITION:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageLoadUtil.loadCirImgNoCrash(mContext.getApplicationContext(),
                        UrlFormatUtil.formatPicUrl(item.healthPosition.getImg()),
                        imageView,
                        R.mipmap.default_head_icon,
                        R.mipmap.default_head_icon);
                helper.setText(R.id.care_title_tv, "名称:" + item.healthPosition.getName())
                        .setText(R.id.care_content_tv, "地址:" + item.healthPosition.getAddress())
//                        .setText(R.id.item_case_content, "状态:" + "离线")
                        .setText(R.id.year_tag_tv, "联系方式:" + item.healthPosition.getPhone()
                        );
                helper.setGone(R.id.care_navigation_tv,true);
                helper.addOnClickListener(R.id.care_navigation_tv);
                helper.setText(R.id.care_navigation_tv, "导航");
                break;
            case MapClusterItem.CARE_POSITION:

                helper.setBackgroundRes(R.id.year_tag_tv,R.color.orange);
                helper.setTextColor(R.id.year_tag_tv,ContextCompat.getColor(mContext,R.color.white));
                helper.getView(R.id.year_tag_tv).setPadding(DisplayUtil.dp2px(mContext,5),0,DisplayUtil.dp2px(mContext,5),0);
                CareRecordPositionBean.DataBean.DatasBean  carePosition = item.carePosition;
                String street =carePosition.getStreetAddress();
                String village = carePosition.getCommunityAddress();
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

                String year = carePosition.getYear();
                String content = null;
                String title = null;
                if ("2019".equals(year)) {
                    title =carePosition.getName();
                    content = carePosition.getCaseDate();
                    helper.setGone(R.id.year_tag_tv,false);

                }else {
                     title = String.format("%s%s%s%s%s", street," ",village, " ",
                            carePosition.getName());
                     content = carePosition.getIdNo();
                     helper.setGone(R.id.year_tag_tv,true);
                    helper.setText(R.id.year_tag_tv,  year+ "年度");
                }
                helper.setText(R.id.care_title_tv, title);
                helper.setText(R.id.care_content_tv, content);

                ImageLoadUtil.loadImage(mContext, UrlFormatUtil.formatPicUrl(carePosition.getPersonImg()),
                        helper.getView(R.id.care_item_iv));
                helper.setGone(R.id.care_navigation_tv,true);
                helper.addOnClickListener(R.id.care_navigation_tv);
                helper.setText(R.id.care_navigation_tv, "导航");
                break;
        }
    }
}
