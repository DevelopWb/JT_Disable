package com.juntai.disabled.federation.home_page.business.handlerBusiness.businessdetail.recovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DeafDumbChildRecoveryDetailActivity extends BaseRecoveryDetailActivity {

    @Override
    public int getChildId() {
        return 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.getDisabledChildrenDeafInfo(businessId,"");
    }
}
