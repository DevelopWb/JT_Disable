package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.bean.MultipleItem;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  脑瘫恢复
 * @date 2021/1/31 17:29
 */
public class BrainPalsyRecoveryActivity extends BaseRecoveryActivity {

    @Override
    public int getChildId() {
        return 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getBrainPalsyRecoveryData(null);
    }
}
