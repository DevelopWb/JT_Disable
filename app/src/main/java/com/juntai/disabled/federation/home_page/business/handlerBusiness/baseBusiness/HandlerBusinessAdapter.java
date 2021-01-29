package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.mob.tools.gui.PullToRequestAdatper;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述   办理业务
 * @date 2021/1/16 11:22
 */
public class HandlerBusinessAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HandlerBusinessAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_BUSINESS_HEAD_PIC, R.layout.item_layout_type_head_pic);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_BIG, R.layout.item_layout_type_title_big);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, R.layout.item_layout_type_title_small);
        addItemType(MultipleItem.ITEM_BUSINESS_EDIT, R.layout.item_layout_type_edit);
        addItemType(MultipleItem.ITEM_BUSINESS_SELECT, R.layout.item_layout_type_select);
        addItemType(MultipleItem.ITEM_BUSINESS_RADIO, R.layout.item_layout_type_radio);
        addItemType(MultipleItem.ITEM_BUSINESS_PIC, R.layout.item_layout_type_pic);
        addItemType(MultipleItem.ITEM_BUSINESS_SIGN, R.layout.item_layout_type_sign);
        addItemType(MultipleItem.ITEM_BUSINESS_NOTICE, R.layout.item_layout_type_notice);
        addItemType(MultipleItem.ITEM_BUSINESS_YEAR, R.layout.item_layout_type_year);
        addItemType(MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW, R.layout.item_layout_type_recyclerview);
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
                int editType = textValueEditBean.getType();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) editText.getLayoutParams();
                if (0 == editType) {
                    lp.height = DisplayUtil.dp2px(mContext, 32);
                    editText.setGravity(Gravity.CENTER_VERTICAL);
                    editText.setSingleLine(true);
                } else {
                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    editText.setMinimumHeight(DisplayUtil.dp2px(mContext, 150));
                    editText.setGravity(Gravity.TOP);
                    editText.setSingleLine(false);
                }
                editText.setLayoutParams(lp);
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
                RadioGroup radioGroup = helper.getView(R.id.item_radio_g);
                radioGroup.setTag(radioBean);
                RadioButton radioButton0 = helper.getView(R.id.radio_zero_rb);
                RadioButton radioButton1 = helper.getView(R.id.radio_first_rb);
                RadioButton radioButton2 = helper.getView(R.id.radio_second_rb);
                RadioButton radioButton3 = helper.getView(R.id.radio_third_rb);
                String[] values = radioBean.getValues();
                radioButton2.setVisibility(View.GONE);
                radioButton3.setVisibility(View.GONE);
                if (values != null) {
                    if (values.length > 1) {
                        radioButton0.setText(values[0]);
                        radioButton1.setText(values[1]);
                        if (values.length == 3) {
                            radioButton2.setVisibility(View.VISIBLE);
                            radioButton2.setText(values[2]);
                        }
                        if (values.length == 4) {
                            radioButton2.setVisibility(View.VISIBLE);
                            radioButton2.setText(values[2]);
                            radioButton3.setVisibility(View.VISIBLE);
                            radioButton3.setText(values[3]);
                        }
                    }

                } else {
                    radioButton0.setText("是");
                    radioButton1.setText("否");
                }
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
                int index = businessPicBean.getPicNameIndex();
                if (index > 0) {
                    helper.setText(R.id.form_pic_title_tv, String.format("%s%s%s", String.valueOf(index), ".",
                            businessPicBean.getPicName()));
                } else {
                    helper.setText(R.id.form_pic_title_tv, businessPicBean.getPicName());
                }
                ImageView picIv = helper.getView(R.id.form_pic_src_iv);
                helper.addOnClickListener(R.id.form_pic_src_iv);
                String picPath = businessPicBean.getPicPath();
                if (!TextUtils.isEmpty(picPath)) {
                    ImageLoadUtil.loadImage(mContext, picPath, picIv);
                } else {
                    ImageLoadUtil.loadImage(mContext, R.mipmap.item_add_pic, picIv);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_NOTICE:
                helper.setText(R.id.business_item_notice_tv, (String) item.getObject());
                break;

            case MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW:
                //recycleview
                RecycleBean recycleBean = (RecycleBean) item.getObject();
                RecyclerView recyclerView = helper.getView(R.id.item_normal_rv);
                int layoutType = recycleBean.getLayoutManagerType();
                CheckBoxAdapter checkBoxAdapter = new CheckBoxAdapter(R.layout.item_business_checkboxes,
                        recycleBean.getData(),recycleBean.isSigleSelect());
                LinearLayoutManager manager = null;
                switch (layoutType) {
                    case 0:
                        manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL
                                , false);
                        break;
                    case 1:
                        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL
                                , false);
                        break;
                    case 2:
                        manager = new GridLayoutManager(mContext, recycleBean.getSpanCount());
                        break;
                    default:
                        break;
                }
                recyclerView.setAdapter(checkBoxAdapter);
                recyclerView.setLayoutManager(manager);
                break;

            case MultipleItem.ITEM_BUSINESS_SIGN:
                ItemSignBean signBean = (ItemSignBean) item.getObject();
                int  gravity = signBean.getLayoutGravity();
                LinearLayout signLl = helper.getView(R.id.item_sign_ll);
                if (0==gravity) {
                    signLl.setGravity(Gravity.LEFT);
                }else {
                    signLl.setGravity(Gravity.RIGHT);
                }
                helper.setText(R.id.sign_name_tv,signBean.getSignName());
                break;
            default:
                break;
        }
    }
}
