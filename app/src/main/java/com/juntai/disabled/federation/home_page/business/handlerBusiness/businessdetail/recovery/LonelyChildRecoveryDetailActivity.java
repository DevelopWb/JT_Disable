package com.juntai.disabled.federation.home_page.business.handlerBusiness.businessdetail.recovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juntai.disabled.federation.bean.MultipleItem;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 精准康复孤独儿童康复救助详情
 * @date 2021/2/25 16:19
 */
public class LonelyChildRecoveryDetailActivity extends BaseRecoveryDetailActivity {


    @Override
    public int getChildId() {
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getDisabledChildrenAutismInfo(businessId,"");
    }

}
