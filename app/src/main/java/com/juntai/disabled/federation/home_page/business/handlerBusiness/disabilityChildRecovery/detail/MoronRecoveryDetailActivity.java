package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail;

import android.os.Bundle;

/**
 * @aouther tobato
 * @description 描述  智力残疾康复详情
 * @date 2021/2/25 15:23
 */
public class MoronRecoveryDetailActivity extends BaseRecoveryDetailActivity {


    @Override
    public int getChildId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getDisabledChildrenIntellectualInfo(businessId,"");
    }



    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

    }
}
