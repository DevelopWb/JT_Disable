package com.juntai.disabled.federation.home_page.collectInfos.careService.addCareTaker;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.careTaker.CareTakerPicBean;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  托养人 图片相关信息
 * @CreateDate: 2020/7/7 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 9:53
 */
public class CareTakerPicsAdapter extends BaseQuickAdapter<CareTakerPicBean, BaseViewHolder> {
    public CareTakerPicsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CareTakerPicBean item) {
        if ("".equals(item.getPicPath())) {
            helper.setGone(R.id.delete_pushed_news_iv,false);
            helper.setImageResource(R.id.select_pic_icon_iv, R.mipmap.add_icons);
        } else {
            helper.setGone(R.id.delete_pushed_news_iv,true);
            String  picPath = item.getPicPath();
            if (StringTools.isStringValueOk(picPath)) {
                if (picPath.contains("托养宝")) {
                    ImageLoadUtil.loadImage(mContext, item.getPicPath(), helper.getView(R.id.select_pic_icon_iv));
                }else {
                    ImageLoadUtil.loadImage(mContext, UrlFormatUtil.formatPicUrl(item.getPicPath()), helper.getView(R.id.select_pic_icon_iv));
                }
            }

        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_pushed_news_iv);
        helper.setText(R.id.select_pic_des_tv, item.getTitle());
        if (item.isImportant()) {
            helper.setVisible(R.id.important_tag_tv,true);
        }else {
            helper.setVisible(R.id.important_tag_tv,false);

        }
    }
}
