package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.DeafBean;
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
    private boolean isDetail = false;//是否是详情模式

    public CheckBoxAdapter(int layoutResId, @Nullable List<ItemCheckBoxBean> data, boolean isSingleSelect,
                           boolean isDetail) {
        super(layoutResId, data);
        this.isSingleSelect = isSingleSelect;
        this.isDetail = isDetail;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemCheckBoxBean item) {


        CheckBox checkBox = helper.getView(R.id.business_checkboxes_cb);
        if (isDetail) {
            checkBox.setEnabled(false);
        }else {
            checkBox.setEnabled(true);
        }
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
                item.setIndex(helper.getAdapterPosition());
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新操作
                        notifyDataSetChanged();
                    }
                });
            }
        });

        EditText desEt =helper.getView(R.id.item_description_et);
        initEdittextFocuseStatus(desEt);
        desEt.setText(item.getDes());
        desEt.setTag(item);
        addTextChange(desEt);
    }
    private void initEdittextFocuseStatus(EditText editText) {
        if (isDetail) {
            editText.setClickable(false);
            editText.setFocusable(false);
        } else {
            editText.setClickable(true);
            editText.setFocusable(true);
        }
    }
    private void addTextChange(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ItemCheckBoxBean item = (ItemCheckBoxBean) editText.getTag();
                item.setDes(s.toString().trim());
            }
        });
    }
}
