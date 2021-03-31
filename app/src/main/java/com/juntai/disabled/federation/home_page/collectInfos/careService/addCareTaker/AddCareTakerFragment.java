package com.juntai.disabled.federation.home_page.collectInfos.careService.addCareTaker;

import android.view.View;

import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.home_page.HomePageContract;
import com.juntai.disabled.federation.home_page.HomePagePresent;

/**
 * @aouther tobato
 * @description 描述  添加托养人
 * @date 2020/7/6 16:23
 */

public class AddCareTakerFragment extends BaseMvpFragment<HomePagePresent> implements View.OnClickListener {


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
