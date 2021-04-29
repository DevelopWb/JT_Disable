package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  残疾证等级变更
 * @date 2021/1/20 14:18
 */
public class CardLevelChangeActivity extends BaseDisabledCardBusinessActivity {

    @Override
    protected String getTitleName() {
        return BUSINESS_NAME_LEVEL_CHANGE;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
       return mPresenter.getChangedLevelAdapterData(null);
    }


}
