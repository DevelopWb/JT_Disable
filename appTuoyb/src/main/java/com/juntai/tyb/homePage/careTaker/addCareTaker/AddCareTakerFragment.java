package com.juntai.tyb.homePage.careTaker.addCareTaker;

import android.view.View;


import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.HomePageContract;
import com.juntai.tyb.homePage.HomePagePresent;

/**
 * @aouther tobato
 * @description 描述  添加托养人
 * @date 2020/7/6 16:23
 */

public class AddCareTakerFragment extends BaseMvpFragment<HomePagePresent> implements View.OnClickListener,
        HomePageContract.IHomePageView {


    @Override
    protected int getLayoutRes() {
        return R.layout.add_care_taker;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onError(String tag, Object o) {

    }


}
