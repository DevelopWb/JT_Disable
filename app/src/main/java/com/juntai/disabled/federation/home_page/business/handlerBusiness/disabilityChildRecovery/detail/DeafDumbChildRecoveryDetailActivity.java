package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail;

import android.os.Bundle;

/**
 * @aouther tobato
 * @description 描述  聋儿童康复详情
 * @date 2021/3/1 10:54
 */
public class DeafDumbChildRecoveryDetailActivity extends BaseRecoveryDetailActivity {

    @Override
    public int getChildId() {
        return 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.getDisabledChildrenDeafInfo(businessId,RECOVERY_DETAIL);
    }
}
