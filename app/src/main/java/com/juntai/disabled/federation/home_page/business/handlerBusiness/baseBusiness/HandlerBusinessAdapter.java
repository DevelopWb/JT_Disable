package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
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
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.bean.business.DeafBean;
import com.juntai.disabled.federation.bean.business.ImportantTagBean;
import com.juntai.disabled.federation.bean.business.ItemSignBean;
import com.juntai.disabled.federation.bean.business.RecycleBean;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述   办理业务
 * @date 2021/1/16 11:22
 */
public class HandlerBusinessAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    private boolean isDetail = false;//是否是详情模式


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HandlerBusinessAdapter(List<MultipleItem> data, boolean isDetail) {
        super(data);
        addItemType(MultipleItem.ITEM_BUSINESS_HEAD_PIC, R.layout.item_layout_type_head_pic);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_BIG, R.layout.item_layout_type_title_big);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_SMALL, R.layout.item_layout_type_title_small);
        addItemType(MultipleItem.ITEM_BUSINESS_EDIT, R.layout.item_layout_type_edit);
        addItemType(MultipleItem.ITEM_BUSINESS_EDIT2, R.layout.item_layout_type_edit2);
        addItemType(MultipleItem.ITEM_BUSINESS_SELECT, R.layout.item_layout_type_select);
        addItemType(MultipleItem.ITEM_BUSINESS_RADIO, R.layout.item_layout_type_radio);
        addItemType(MultipleItem.ITEM_BUSINESS_PIC, R.layout.item_layout_type_pic);
        addItemType(MultipleItem.ITEM_BUSINESS_SIGN, R.layout.item_layout_type_sign);
        addItemType(MultipleItem.ITEM_BUSINESS_NOTICE, R.layout.item_layout_type_notice);
        addItemType(MultipleItem.ITEM_BUSINESS_YEAR, R.layout.item_layout_type_year);
        addItemType(MultipleItem.ITEM_BUSINESS_NORMAL_RECYCLEVIEW, R.layout.item_layout_type_recyclerview);
        addItemType(MultipleItem.ITEM_BUSINESS_DEAF_TABLE, R.layout.item_layout_type_deaf_table);
        this.isDetail = isDetail;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.ITEM_BUSINESS_HEAD_PIC:
                if (!isDetail) {
                    helper.addOnClickListener(R.id.form_head_pic_iv);
                }
                BusinessPicBean headPicBean = (BusinessPicBean) item.getObject();
                ImageView headIv = helper.getView(R.id.form_head_pic_iv);
                String headPicPath = headPicBean.getPicPath();
                if (!TextUtils.isEmpty(headPicPath)) {
                    ImageLoadUtil.loadImageNoCache(mContext, headPicPath, headIv);
                } else {
                    ImageLoadUtil.loadImage(mContext, R.mipmap.two_inch_pic, headIv);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_TITILE_BIG:
                helper.setText(R.id.item_business_big_title_tv, (String) item.getObject());
                break;
            case MultipleItem.ITEM_BUSINESS_TITILE_SMALL:
                ImportantTagBean importantTagBean = (ImportantTagBean) item.getObject();
                helper.setGone(R.id.important_tag_tv, importantTagBean.isImportant());
                helper.setText(R.id.item_business_small_title_tv, importantTagBean.getTitleName());
                break;
            case MultipleItem.ITEM_BUSINESS_EDIT:
                BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) item.getObject();
                if (BusinessContract.TABLE_TITLE_CHECK_CODE.equals(textValueEditBean.getKey())) {
                    helper.setGone(R.id.send_check_code_tv, true);
                } else {
                    helper.setGone(R.id.send_check_code_tv, false);
                }
                EditText editText = helper.getView(R.id.edit_value_et);
                initEdittextFocuseStatus(editText);
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
                //正则
                switch (editKey) {
                    case BusinessContract.TABLE_TITLE_CONTACT_MODE:
                        //联系方式
                        setMaxLength(editText, 11);
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case BusinessContract.TABLE_TITLE_PHONE:
                        //联系电话
                        setMaxLength(editText, 11);
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case BusinessContract.TABLE_TITLE_MOBILE_NUM:
                        //手机号码
                        setMaxLength(editText, 11);
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case BusinessContract.TABLE_TITLE_HOUSE_PHONE:
                        //住宅电话
                        setMaxLength(editText, 11);
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case BusinessContract.TABLE_TITLE_WCHAT_PHONE:
                        //微信手机号
                        setMaxLength(editText, 11);
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                        break;
                    case BusinessContract.TABLE_TITLE_CARD_NUM:
                        //卡号
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case BusinessContract.TABLE_TITLE_IDCARD:
                        //身份证号
                        setMaxLength(editText, 18);
                        break;
                    case BusinessContract.TABLE_TITLE_CHILD_IDCARD:
                        //儿童身份证号
                        setMaxLength(editText, 18);
                        break;
                    case BusinessContract.TABLE_TITLE_GUARDIAN_ID_CARD:
                        //监护人身份证号
                        setMaxLength(editText, 18);
                        break;
                    case BusinessContract.TABLE_TITLE_AGE_FAMILY:
                        //F年龄
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case BusinessContract.TABLE_TITLE_AGE_PERSIONAL:
                        //P年龄
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case BusinessContract.TABLE_TITLE_DISABLE_CARD_ID:
                        //残疾证号
                        setMaxLength(editText, 20);
                        break;
                    default:
                        //输入类型为普通文本
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        setMaxLength(editText, 1000);
                        break;
                }

                break;
            case MultipleItem.ITEM_BUSINESS_EDIT2:
                BusinessTextValueBean textValueEditBean2 = (BusinessTextValueBean) item.getObject();
                EditText editText2 = helper.getView(R.id.value_et);
                initEdittextFocuseStatus(editText2);
                TextView textView2 = helper.getView(R.id.key_tv);
                editText2.setTag(textValueEditBean2);
                addTextChange(editText2);
                editText2.setText(textValueEditBean2.getValue());
                String editKeyTv = textValueEditBean2.getKey();

                if (editKeyTv.contains("F") || editKeyTv.contains("C")) {
                    //主要涉及聋儿童康复 与残疾儿童关系的地方 用于区分
                    editKeyTv = editKeyTv.substring(1, editKeyTv.length());
                }
                textView2.setText(editKeyTv);
                if (BusinessContract.TABLE_TITLE_CONTACT_MODE.equals(editKeyTv) || BusinessContract.TABLE_TITLE_PHONE.equals(editKeyTv)) {
                    editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_YEAR:
                //年度
                BusinessTextValueBean yearBean = (BusinessTextValueBean) item.getObject();
                EditText yearEt = helper.getView(R.id.item_year_et);
                yearEt.setTag(yearBean);
                if (isDetail) {
                    yearEt.setClickable(false);
                    yearEt.setFocusable(false);
                    yearEt.setText(yearBean.getValue());
                } else {
                    yearEt.setClickable(true);
                    yearEt.setFocusable(true);
                    addTextChange(yearEt);
                }
                break;
            case MultipleItem.ITEM_BUSINESS_SELECT:
                BusinessTextValueBean textValueSelectBean = (BusinessTextValueBean) item.getObject();
                TextView textViewTv = helper.getView(R.id.select_value_tv);
                if (!isDetail) {
                    helper.addOnClickListener(R.id.select_value_tv);
                    helper.addOnClickListener(R.id.tool_pic_iv);
                }
                if (textValueSelectBean.getDataBean() != null && !TextUtils.isEmpty(textValueSelectBean.getDataBean().getImg())) {
                    helper.setGone(R.id.tool_pic_iv, true);
                    ImageLoadUtil.loadImageNoCache(mContext, textValueSelectBean.getDataBean().getImg(),
                            helper.getView(R.id.tool_pic_iv));
                } else {
                    helper.setGone(R.id.tool_pic_iv, false);
                }
                textViewTv.setTag(textValueSelectBean);
                BusinessTextValueBean selectBean = (BusinessTextValueBean) textViewTv.getTag();
                textViewTv.setHint(selectBean.getHint());
                textViewTv.setText(selectBean.getValue());
                break;
            case MultipleItem.ITEM_BUSINESS_RADIO:
                BusinessRadioBean radioBean = (BusinessRadioBean) item.getObject();
                RadioGroup radioGroup = helper.getView(R.id.item_radio_g);
                initRadioGroupStatus(radioGroup);
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
                            case R.id.radio_second_rb:
                                radioBean.setDefaultSelectedIndex(2);
                                break;
                            case R.id.radio_third_rb:
                                radioBean.setDefaultSelectedIndex(3);
                                break;
                            default:
                                break;
                        }
                    }
                });
                int defaultIndex = radioBean.getDefaultSelectedIndex();
                //                switch (radioBean.getKey()) {
                //                    case BusinessContract.TABLE_TITLE_FAMILY_EMONIC_STATUS:
                //                        defaultIndex -= 1;
                //                        break;
                //                    case BusinessContract.TABLE_TITLE_PROJECT_LEVEL:
                //                        defaultIndex -= 1;
                //                        break;
                //                    case BusinessContract.TABLE_TITLE_HUKOU:
                //                        defaultIndex -= 1;
                //                        break;
                //                    default:
                //                        break;
                //                }
                switch (defaultIndex) {
                    case 0:
                        radioButton0.setChecked(true);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        break;
                    case 1:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(true);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        break;
                    case 2:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(true);
                        radioButton3.setChecked(false);
                        break;
                    case 3:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(true);
                        break;
                    default:
                        break;
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
                if (!isDetail) {
                    helper.addOnClickListener(R.id.form_pic_src_iv);
                }

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
                        recycleBean.getData(), recycleBean.isSigleSelect(), isDetail);
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
                if (!isDetail) {
                    helper.addOnClickListener(R.id.sign_name_iv);
                }
                ItemSignBean signBean = (ItemSignBean) item.getObject();
                int gravity = signBean.getLayoutGravity();
                LinearLayout signLl = helper.getView(R.id.item_sign_ll);
                ImageView signIv = helper.getView(R.id.sign_name_iv);
                if (0 == gravity) {
                    helper.setGone(R.id.sign_tag, true);
                    signLl.setGravity(Gravity.LEFT);
                } else {
                    helper.setGone(R.id.sign_tag, false);
                    signLl.setGravity(Gravity.RIGHT);
                }
                helper.setText(R.id.sign_name_tv, signBean.getSignName());
                if (StringTools.isStringValueOk(signBean.getSignPicPath())) {
                    ImageLoadUtil.loadImage(mContext, signBean.getSignPicPath(), signIv);
                }
                break;

            case MultipleItem.ITEM_BUSINESS_DEAF_TABLE:

                DeafBean deafBean = (DeafBean) item.getObject();
                EditText leftEarLoseEt = helper.getView(R.id.left_ear_et);
                initEdittextFocuseStatus(leftEarLoseEt);
                leftEarLoseEt.setTag(deafBean);
                leftEarLoseEt.setText(deafBean.getLeftEar());
                EditText rightEarLoseEt = helper.getView(R.id.right_ear_et);
                initEdittextFocuseStatus(rightEarLoseEt);
                rightEarLoseEt.setTag(deafBean);
                rightEarLoseEt.setText(deafBean.getRightEar());
                EditText wearYearEt = helper.getView(R.id.wear_time_year_et);
                initEdittextFocuseStatus(wearYearEt);

                wearYearEt.setTag(deafBean);
                wearYearEt.setText(deafBean.getWearTimeYear());
                EditText wearMonthEt = helper.getView(R.id.wear_time_month_et);
                initEdittextFocuseStatus(wearMonthEt);

                wearMonthEt.setTag(deafBean);
                wearMonthEt.setText(deafBean.getWearTimeMonth());
                addTextChange(leftEarLoseEt);
                addTextChange(rightEarLoseEt);
                addTextChange(wearYearEt);
                addTextChange(wearMonthEt);
                LinearLayout wearTimeLl = helper.getView(R.id.wear_time_ll);
                RadioGroup wearAidRg = helper.getView(R.id.weared_aid_rg);
                int isWear = deafBean.getWear();
                if (0 == isWear) {
                    //佩戴
                    wearAidRg.check(R.id.wear_aid_rb);
                } else {
                    wearAidRg.check(R.id.not_wear_aid_rb);
                }

                RadioGroup whichEarWearRg = helper.getView(R.id.which_ear_wear_rg);
                int witchEar = deafBean.getWearEar();
                if (0 == witchEar) {
                    //左
                    whichEarWearRg.check(R.id.left_ear_rb);
                } else {
                    whichEarWearRg.check(R.id.right_ear_rb);
                }
                wearAidRg.setTag(deafBean);
                whichEarWearRg.setTag(deafBean);
                addRadioCheckedListener(wearAidRg);
                addRadioCheckedListener(whichEarWearRg);
                initRadioGroupStatus(wearAidRg);
                initRadioGroupStatus(whichEarWearRg);

                break;
            default:
                break;
        }
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

    private void initRadioGroupStatus(RadioGroup wearAidRg) {
        if (isDetail) {
            for (int i = 0; i < wearAidRg.getChildCount(); i++) {
                wearAidRg.getChildAt(i).setEnabled(false);
            }
        } else {
            for (int i = 0; i < wearAidRg.getChildCount(); i++) {
                wearAidRg.getChildAt(i).setEnabled(true);
            }
        }
    }

    /**
     * 配置最大长度
     *
     * @param editText
     * @param i2
     */
    private void setMaxLength(EditText editText, int i2) {
        //手动设置maxLength为18
        InputFilter[] filters = {new InputFilter.LengthFilter(i2)};
        editText.setFilters(filters);
    }

    private void addRadioCheckedListener(RadioGroup whichEarWearRg) {
        whichEarWearRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                DeafBean deafBean1 = (DeafBean) group.getTag();
                switch (checkedId) {
                    case R.id.left_ear_rb:
                        deafBean1.setWearEar(0);
                        break;
                    case R.id.right_ear_rb:
                        deafBean1.setWearEar(1);
                        break;
                    case R.id.wear_aid_rb:
                        deafBean1.setWear(0);
                        //                                wearTimeLl.setVisibility(ViewBase.VISIBLE);
                        //                                whichEarWearRg.setVisibility(ViewBase.VISIBLE);
                        break;
                    case R.id.not_wear_aid_rb:
                        deafBean1.setWear(1);
                        //                                wearTimeLl.setVisibility(ViewBase.GONE);
                        //                                whichEarWearRg.setVisibility(ViewBase.GONE);
                        break;

                    default:
                        break;
                }
            }
        });
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

                switch (editText.getId()) {
                    case R.id.left_ear_et:
                        DeafBean deafBeanleft = (DeafBean) editText.getTag();
                        deafBeanleft.setLeftEar(s.toString().trim());
                        break;
                    case R.id.right_ear_et:
                        DeafBean deafBeanRight = (DeafBean) editText.getTag();
                        deafBeanRight.setRightEar(s.toString().trim());
                        break;
                    case R.id.wear_time_year_et:
                        DeafBean wearYearBean = (DeafBean) editText.getTag();
                        wearYearBean.setWearTimeYear(s.toString().trim());
                        break;
                    case R.id.wear_time_month_et:
                        DeafBean wearMonthBean = (DeafBean) editText.getTag();
                        wearMonthBean.setWearTimeMonth(s.toString().trim());
                        break;
                    case R.id.value_et:
                        BusinessTextValueBean editBean = (BusinessTextValueBean) editText.getTag();
                        editBean.setValue(s.toString().trim());
                        break;
                    case R.id.item_year_et:
                        BusinessTextValueBean yearBean = (BusinessTextValueBean) editText.getTag();
                        yearBean.setValue(s.toString().trim());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
