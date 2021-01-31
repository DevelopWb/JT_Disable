package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  智力残疾儿童康复
 * @date 2021/1/27 17:35
 */
public abstract class BaseRecoveryActivity extends BaseBusinessActivity {
    private TextView mCommitBusinessTv;
    public static String RECOVERY_NAME="recoveryname";

    @Override
    public void initData() {

    }
    @Override
    protected String getTitleName() {
        if (getIntent() != null) {
            return getIntent().getStringExtra(RECOVERY_NAME);
        }
        return "";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_commit, null);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }


    @Override
    protected ImageView getSignIv() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

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
                break;
            default:
                break;
        }
    }
}
