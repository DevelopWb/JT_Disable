package com.juntai.disabled.federation.home_page.business.handlerBusiness.businessdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessDetailActivity;

public class DisabledCardReissueDetailActivity extends BaseDisabledCardBusinessDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return BaseDisabledCardBusinessDetailActivity.BUSINESS_NAME_REISSUE;
    }
}