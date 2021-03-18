package com.juntai.disabled.federation.home_page.takeInfos;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.SearchBean;
import com.juntai.disabled.federation.bean.takeinfo.SearchedDisabledBean;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  无障碍采集 残疾人信息
 * @date 2021/3/17 17:06
 */
public class DisabledPeoplesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DisabledPeoplesAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper,String item) {
//        helper.setText(R.id.item_search_name,"姓名:" + item.getName());
//        helper.setText(R.id.item_search_zheng,"残疾证号:" + item.getCertificateno());
//        helper.setText(R.id.item_search_sex,"性别:" + item.getSex());
//        helper.setText(R.id.item_search_place,"住址:" + item.getPresentaddress());
//        //低保贫困
//        helper.getView(R.id.item_search_pin).setVisibility(View.GONE);
//        helper.getView(R.id.item_search_di).setVisibility(View.GONE);
//        helper.getView(R.id.item_search_wu).setVisibility(View.GONE);
//        if (item.getAlleviation().equals("是")){
//            helper.getView(R.id.item_search_pin).setVisibility(View.VISIBLE);
//        }
//        if (item.getSecurity().equals("是")){
//            helper.getView(R.id.item_search_di).setVisibility(View.VISIBLE);
//        }
//        if (item.getRemould().equals("是")){
//            helper.getView(R.id.item_search_wu).setVisibility(View.VISIBLE);
//        }
    }
}