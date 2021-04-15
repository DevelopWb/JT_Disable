package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail;

import android.os.Bundle;

public class BrainPalsyRecoveryDetailActivity extends BaseRecoveryDetailActivity {

    @Override
    public int getChildId() {
        return 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getDisabledChildrenCerebralPalsyInfo(businessId,RECOVERY_DETAIL);

    }
}
