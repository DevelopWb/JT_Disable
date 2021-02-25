package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.bean.MultipleItem;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  孤独症儿童康复
 * @date 2021/1/31 10:31
 */
public class LonelyChildRecoveryActivity extends BaseRecoveryActivity {



    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getLonelyChildRecoveryData(null);
    }

    @Override
    public int getChildId() {
        return 1;
    }
}
