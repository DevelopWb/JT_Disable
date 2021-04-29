package com.juntai.disabled.federation.home_page.business.handlerBusiness.handlercard;

import android.support.constraint.Group;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.HandlerCardDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  残疾证办理详情
 * @date 2021/2/23 15:45
 */
public class HandlerCardDetailActivity extends BaseBusinessActivity {
    private EditText mDisableNameEt;
    private EditText mGuardianNameEt;
    private ImageView mGuardianNameSignIv;
    private TextView mCommitmentTv;
    private TextView mCommitBusinessTv;
    @Override
    public void initData() {
        mPresenter.getDisabilityCertificateInfo(businessId, AppHttpPath.DISABLED_ID_CARD_DETAIL);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾证办理详情";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_disable_commiment, null);
        mDisableNameEt = view.findViewById(R.id.diable_name_et);
        mGuardianNameEt = view.findViewById(R.id.guardian__name_et);
        mGuardianNameSignIv = view.findViewById(R.id.guardian__name_sign_iv);
        mCommitmentTv = view.findViewById(R.id.commitment_tv);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setVisibility(View.GONE);
        Group commitmentG = view.findViewById(R.id.commitment_g);
        commitmentG.setVisibility(View.GONE);
        return view;
    }
    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected void commit() {

    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        if (AppHttpPath.DISABLED_ID_CARD_DETAIL.equals(tag)) {
            HandlerCardDetailBean handlerCardDetailBean = (HandlerCardDetailBean) o;
            if (handlerCardDetailBean != null) {
                HandlerCardDetailBean.DataBean dataBean =   handlerCardDetailBean.getData();
                if (1==dataBean.getEstatus()&&checkStatusId==1) {
                    //未评价
                    showScoreDialog(businessItemId);
                }
                adapter.setNewData(mPresenter.getHandlerIdCardAdapterData(dataBean));
                ImageLoadUtil.loadImage(mContext,dataBean.getApplicantSign(),mGuardianNameSignIv);
                mCommitmentTv.setText(dataBean.getCommitment());
            }
        }else {
            super.onSuccess(tag,o);
        }

    }
}
