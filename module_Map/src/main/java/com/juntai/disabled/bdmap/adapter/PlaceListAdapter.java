package com.juntai.disabled.bdmap.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.bdmap.R;
import com.juntai.disabled.bdmap.act.LocationSeltionActivity;

import java.util.List;

/**
 * 融云 - 位置定位选择
 * @aouther Ma
 * @date 2019/3/17
 */
public class PlaceListAdapter extends BaseQuickAdapter<LocationSeltionActivity.Address, BaseViewHolder> {

    public PlaceListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationSeltionActivity.Address item) {
//        helper.setText(R.id.mapname,item.name);
        helper.setText(R.id.mapaddress,item.getAddress());
        //根据重新加载的时候第position条item是否是当前所选择的，选择加载不同的图片
        if(item.ischecked){
//            helper.setTextColor(R.id.mapname,Color.parseColor("#ff6501"));
            helper.setTextColor(R.id.mapaddress,Color.parseColor("#ff6501"));
        }
        else {
//            helper.setTextColor(R.id.mapname,Color.parseColor("#000000"));
            helper.setTextColor(R.id.mapaddress,Color.parseColor("#8f605f5f"));
        }
    }
}