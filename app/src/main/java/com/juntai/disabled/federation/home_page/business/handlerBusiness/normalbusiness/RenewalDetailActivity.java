package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import android.os.Bundle;

import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessDetailActivity;

/**
 * @aouther tobato
 * @description 描述  期满换证
 * @date 2021/2/23 17:33
 */
public class RenewalDetailActivity extends BaseDisabledCardBusinessDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return BaseDisabledCardBusinessDetailActivity.BUSINESS_NAME_RENEWAL;
    }
}
