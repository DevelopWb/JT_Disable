package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import android.os.Bundle;

import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessDetailActivity;
/**
 * @aouther tobato
 * @description 描述  等级变更详情
 * @date 2021/2/24 16:06
 */
public class DisabledCardLevelChangeDetailActivity extends BaseDisabledCardBusinessDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return BaseDisabledCardBusinessDetailActivity.BUSINESS_NAME_LEVEL_CHANGE;
    }
}
