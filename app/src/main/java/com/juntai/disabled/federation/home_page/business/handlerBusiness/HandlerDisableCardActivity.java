package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.BaseBusinessActivity;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.List;

public class HandlerDisableCardActivity extends BaseBusinessActivity {

    private EditText mDisableNameEt;
    private EditText mGuardianNameEt;
    private ImageView mGuardianNameSignIv;
    private TextView mCommitmentTv;
    private TextView mCommitBusinessTv;


    @Override
    protected String getTitleName() {
        return "残疾人证办理";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_disable_commiment, null);
        mDisableNameEt = view.findViewById(R.id.diable_name_et);
        mGuardianNameEt = view.findViewById(R.id.guardian__name_et);
        mGuardianNameSignIv = view.findViewById(R.id.guardian__name_sign_iv);
        mCommitmentTv = view.findViewById(R.id.commitment_tv);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        mGuardianNameSignIv.setOnClickListener(this);
        return view;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getHandlerIdCardAdapterData();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                StringBuilder sb = getStringBuilderOfAdapterData();
                if (sb == null) {
                    return;
                }
                if (TextUtils.isEmpty(getTextViewValue(mDisableNameEt))) {
                    ToastUtils.toast(mContext,"请输入承诺书中残疾人姓名");
                    return;
                }
                if (TextUtils.isEmpty(getTextViewValue(mGuardianNameEt))) {
                    ToastUtils.toast(mContext,"请输入承诺书中残疾人监护人姓名");
                    return;
                }
                if (TextUtils.isEmpty(getSignPath())) {
                    ToastUtils.toast(mContext,"请签名");
                    return;
                }
                break;
            case R.id.guardian__name_sign_iv:
                showSignatureView();
                break;
            default:
                break;
        }
    }

    @Override
    protected ImageView getSignIv() {
        return mGuardianNameSignIv;
    }

    @Override
    public void initData() {
        String content = getString(R.string.commitment);
        StringTools.setTextPartColor2(mCommitmentTv, content, content.indexOf("(") + 1, content.indexOf(")"),
                "#FB7D06");
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }


}
