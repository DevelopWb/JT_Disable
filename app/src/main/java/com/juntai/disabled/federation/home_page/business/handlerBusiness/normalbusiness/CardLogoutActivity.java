package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 残疾证注销
 * @date 2021/1/20 14:22
 */
public class CardLogoutActivity extends BaseDisabledCardBusinessActivity {

    @Override
    protected String getTitleName() {
        return BUSINESS_NAME_LOGOUT;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }


}
