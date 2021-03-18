package com.juntai.disabled.federation.home_page.takeInfos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppFragment;

/**
 * @aouther tobato
 * @description 描述  信息采集
 * @date 2021/1/8 17:18
 */
public class TakeInfosFragment extends BaseAppFragment {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_take_info;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction  transaction = manager.beginTransaction();
        transaction.add(R.id.navigation_third_menu_fl,new DisabledPeopleInfoTakeFragment());
        transaction.commit();
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
