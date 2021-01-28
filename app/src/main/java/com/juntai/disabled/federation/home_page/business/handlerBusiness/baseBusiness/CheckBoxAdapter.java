package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.federation.R;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/28 11:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28 11:14
 */
public class CheckBoxAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CheckBoxAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        CheckBox checkBox = helper.getView(R.id.business_checkboxes_cb);
        checkBox.setText(item);
        if ("其他困难".equals(item)) {
            helper.setGone(R.id.item_description_ll, true);
        } else {
            helper.setGone(R.id.item_description_ll, false);
        }

    }
}
