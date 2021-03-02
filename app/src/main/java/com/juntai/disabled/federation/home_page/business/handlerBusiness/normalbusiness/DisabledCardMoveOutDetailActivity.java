package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessDetailActivity;

public class DisabledCardMoveOutDetailActivity extends BaseDisabledCardBusinessDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return BaseDisabledCardBusinessDetailActivity.BUSINESS_NAME_MOVE_OUT;
    }
}