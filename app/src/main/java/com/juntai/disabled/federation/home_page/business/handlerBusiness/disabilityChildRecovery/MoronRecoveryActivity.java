package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  智力残疾儿童康复
 * @date 2021/1/27 17:35
 */
public class MoronRecoveryActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {

        return "精准康复智力残疾儿童康复救助";
    }

    @Override
    protected View getFootView() {
        return null;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getMoronRecoveryData();
    }

    @Override
    protected ImageView getSignIv() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
