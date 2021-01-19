package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.home_page.business.BusinessContract;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述   办理残疾人证
 * @date 2021/1/16 11:22
 */
public class HandlerDisableCardAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HandlerDisableCardAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_BUSINESS_HEAD_PIC, R.layout.item_layout_type_head_pic);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_BIG, R.layout.item_layout_type_title_big);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, R.layout.item_layout_type_title_small);
        addItemType(MultipleItem.ITEM_BUSINESS_EDIT, R.layout.item_layout_type_edit);
        addItemType(MultipleItem.ITEM_BUSINESS_SELECT, R.layout.item_layout_type_select);
        addItemType(MultipleItem.ITEM_BUSINESS_RADIO, R.layout.item_layout_type_radio);
        addItemType(MultipleItem.ITEM_BUSINESS_PIC, R.layout.item_layout_type_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.ITEM_BUSINESS_HEAD_PIC:
                helper.addOnClickListener(R.id.form_head_pic_iv);
                BusinessPicBean headPicBean = (BusinessPicBean) item.getObject();
                ImageView headIv = helper.getView(R.id.form_head_pic_iv);
                String headPicPath = headPicBean.getPicPath();
                if (!TextUtils.isEmpty(headPicPath)) {
                    ImageLoadUtil.loadImage(mContext, headPicPath, headIv);
                } else {
                    ImageLoadUtil.loadImage(mContext, R.mipmap.item_head_pic, headIv);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_TITILE_BIG:
                helper.setText(R.id.item_business_big_title_tv, (String) item.getObject());
                break;
            case MultipleItem.ITEM_BUSINESS_TITILE_SMALL:
                helper.setText(R.id.item_business_small_title_tv, (String) item.getObject());
                break;
            case MultipleItem.ITEM_BUSINESS_EDIT:
                BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) item.getObject();
                EditText editText = helper.getView(R.id.edit_value_et);
                editText.setTag(textValueEditBean);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        BusinessTextValueBean editBean = (BusinessTextValueBean) editText.getTag();
                        editBean.setValue(s.toString().trim());
                    }
                });
                editText.setHint(textValueEditBean.getHint());
                editText.setText(textValueEditBean.getValue());
                String editKey = textValueEditBean.getKey();
                if (BusinessContract.TABLE_TITLE_CONTACT_MODE.equals(editKey) || BusinessContract.TABLE_TITLE_PHONE.equals(editKey)) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_SELECT:
                BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) item.getObject();
                TextView textViewTv = helper.getView(R.id.select_value_tv);
                helper.addOnClickListener(R.id.select_value_tv);
                textViewTv.setHint(textValueSelectBean.getHint());
                textViewTv.setText(textValueSelectBean.getValue());
                break;
            case MultipleItem.ITEM_BUSINESS_RADIO:
                BusinessRadioBean radioBean = (BusinessRadioBean) item.getObject();
                String radioBeanKey = radioBean.getKey();
                RadioGroup radioGroup = helper.getView(R.id.item_radio_g);
                radioGroup.setTag(radioBean);
                RadioButton radioButton0 = helper.getView(R.id.radio_zero_rb);
                RadioButton radioButton1 = helper.getView(R.id.radio_first_rb);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        BusinessRadioBean radioBean = (BusinessRadioBean) group.getTag();
                        switch (checkedId) {
                            case R.id.radio_zero_rb:
                                radioBean.setDefaultSelectedIndex(0);
                                break;
                            case R.id.radio_first_rb:
                                radioBean.setDefaultSelectedIndex(1);
                                break;
                            default:
                                break;
                        }
                    }
                });
                if (BusinessContract.TABLE_TITLE_SEX.equals(radioBeanKey)) {
                    radioButton0.setText("男");
                    radioButton1.setText("女");
                } else {
                    radioButton0.setText("是");
                    radioButton1.setText("否");
                }
                if (0 == radioBean.getDefaultSelectedIndex()) {
                    radioButton0.setChecked(true);
                    radioButton1.setChecked(false);
                } else {
                    radioButton1.setChecked(true);
                    radioButton0.setChecked(false);
                }

                break;
            case MultipleItem.ITEM_BUSINESS_PIC:
                BusinessPicBean businessPicBean = (BusinessPicBean) item.getObject();
                helper.setText(R.id.form_pic_title_tv, businessPicBean.getPicName());
                ImageView picIv = helper.getView(R.id.form_pic_src_iv);
                helper.addOnClickListener(R.id.form_pic_src_iv);
                String picPath = businessPicBean.getPicPath();
                if (!TextUtils.isEmpty(picPath)) {
                    ImageLoadUtil.loadImage(mContext, picPath, picIv);
                } else {
                    ImageLoadUtil.loadImage(mContext, R.mipmap.item_add_pic, picIv);
                }
                break;
            default:
                break;
        }
    }
}
