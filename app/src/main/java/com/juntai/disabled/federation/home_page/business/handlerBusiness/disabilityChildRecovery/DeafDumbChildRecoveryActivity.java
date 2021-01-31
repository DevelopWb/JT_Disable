package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.bean.MultipleItem;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  聋哑儿童康复
 * @date 2021/1/31 11:04
 */
public class DeafDumbChildRecoveryActivity extends BaseRecoveryActivity {


    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getDeafDumbChildRecoveryData();
    }
}
