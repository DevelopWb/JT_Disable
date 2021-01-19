package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.BaseHandlerBusinessActivity;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.List;

public class HandlerDisableCardActivity extends BaseHandlerBusinessActivity {

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
      switch (v.getId()) {
                    case R.id.commit_business_form_tv:
                        break;
                    case R.id.guardian__name_sign_iv:
                        break;
                    default:
                        ToastUtils.toast(mContext,"dfadfasd ");
                        break;
        }
    }

    @Override
    public void initData() {
        String content = getString(R.string.commitment);
        StringTools.setTextPartColor2(mCommitmentTv, content, content.indexOf("(")+1, content.indexOf(")"),
                "#FB7D06");
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }


}
