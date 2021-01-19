package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.BusinessPicBean;
import com.juntai.disabled.federation.bean.business.BusinessRadioBean;
import com.juntai.disabled.federation.bean.business.BusinessTextValueBean;
import com.juntai.disabled.federation.home_page.business.BaseHandlerBusinessActivity;
import com.juntai.disabled.federation.home_page.business.BusinessContract;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  期满换证
 * @date 2021/1/19 9:53
 */
public class RenewalActivity extends BaseHandlerBusinessActivity {

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾证期满换证";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_renewal, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        ImageView mGuardianNameSignIv = view.findViewById(R.id.guardian__name_sign_iv);
        mCommitBusinessTv.setOnClickListener(this);
        mGuardianNameSignIv.setOnClickListener(this);
        return view;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getRenewalAdapterData();
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                List<MultipleItem> arrays = adapter.getData();
                StringBuilder sb = new StringBuilder();
                for (MultipleItem array : arrays) {
                    switch (array.getItemType()) {
                        case MultipleItem.ITEM_BUSINESS_HEAD_PIC:
                            BusinessPicBean headPicBean = (BusinessPicBean) array.getObject();
                            sb.append(headPicBean.getPicName());
                            sb.append(".....\n");
                            sb.append(headPicBean.getPicPath());
                            sb.append(".....\n");
                            if (TextUtils.isEmpty(headPicBean.getPicPath())) {
                                ToastUtils.toast(mContext, "请选择申请人照片");
                                return;
                            }
                            break;
                        case MultipleItem.ITEM_BUSINESS_EDIT:
                            BusinessTextValueBean textValueEditBean = (BusinessTextValueBean) array.getObject();
                            sb.append(textValueEditBean.getKey());
                            sb.append(".....\n");
                            sb.append(textValueEditBean.getValue());
                            sb.append(".....\n");
                            if (TextUtils.isEmpty(textValueEditBean.getValue())) {
                                ToastUtils.toast(mContext, "请输入你的"+textValueEditBean.getKey());
                                return;
                            }
                            break;
                        case MultipleItem.ITEM_BUSINESS_PIC:
                            BusinessPicBean picBean = (BusinessPicBean) array.getObject();
                            sb.append(picBean.getPicName());
                            sb.append(".....\n");
                            sb.append(picBean.getPicPath());
                            if (TextUtils.isEmpty(picBean.getPicPath())) {
                                ToastUtils.toast(mContext, "请上传资料图片");
                                return;
                            }
                            break;
                        default:
                            break;
                    }
                }
                LogUtil.e(sb.toString());
                ToastUtils.toast(mContext, sb.toString());
                break;
            case R.id.guardian__name_sign_iv:
                break;
            default:
                ToastUtils.toast(mContext, "dfadfasd ");
                break;
        }
    }
}
