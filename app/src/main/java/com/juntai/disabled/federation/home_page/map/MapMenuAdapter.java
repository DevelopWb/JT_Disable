package com.juntai.disabled.federation.home_page.map;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MapMenuButton;

import java.util.List;

/**
 * author:wong
 * Date: 2019/3/19
 * Description:
 */
public class MapMenuAdapter extends BaseQuickAdapter<MapMenuButton.DataBean, BaseViewHolder> {

    public MapMenuAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapMenuButton.DataBean item) {
        // 加载网络图片
        if (!item.isSelected()) {
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.getUncheckedUrl(),
                    helper.getView(R.id.menu_button_iv));
        }else {
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.getCheckedUrl(),
                    helper.getView(R.id.menu_button_iv));
        }
    }
}