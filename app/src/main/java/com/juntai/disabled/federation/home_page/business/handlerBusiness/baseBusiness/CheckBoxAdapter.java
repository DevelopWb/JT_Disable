package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.business.ItemCheckBoxBean;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/28 11:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28 11:14
 */
public class CheckBoxAdapter extends BaseQuickAdapter<ItemCheckBoxBean, BaseViewHolder> {

    private boolean isSingleSelect = false;// 默认不是单选

    public CheckBoxAdapter(int layoutResId, @Nullable List<ItemCheckBoxBean> data, boolean isSingleSelect) {
        super(layoutResId, data);
        this.isSingleSelect = isSingleSelect;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemCheckBoxBean item) {

        CheckBox checkBox = helper.getView(R.id.business_checkboxes_cb);
        checkBox.setText(item.getKey());
        if ("其他困难".equals(item.getKey())) {
            helper.setGone(R.id.item_description_ll, true);
        } else {
            helper.setGone(R.id.item_description_ll, false);
        }
        if (item.isSelecte()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingleSelect) {
                    List<ItemCheckBoxBean> datas = getData();
                    for (ItemCheckBoxBean data : datas) {
                        if (data.isSelecte()) {
                            data.setSelecte(false);
                        }
                    }
                }
                if (item.isSelecte()) {
                    item.setSelecte(false);
                } else {
                    item.setSelecte(true);
                }

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新操作
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
}