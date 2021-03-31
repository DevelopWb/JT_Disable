package com.juntai.disabled.federation.home_page.collectInfos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppFragment;
import com.juntai.disabled.federation.home_page.collectInfos.careService.addCareTaker.SearchCareTakerFragment;
import com.juntai.disabled.federation.home_page.collectInfos.disabledInfoCollect.DisabledPeopleInfoTakeFragment;

/**
 * @aouther tobato
 * @description 描述  信息采集
 * @date 2021/1/8 17:18
 */
public class CollectInfosFragment extends BaseAppFragment {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_take_info;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    /**
     * 0代表托养服务 1代表残疾人信息采集
     * @param type
     */
    public  void initFragment(int type ) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (0==type) {
            transaction.replace(R.id.navigation_third_menu_fl,new SearchCareTakerFragment());
        }else {
            transaction.replace(R.id.navigation_third_menu_fl,new DisabledPeopleInfoTakeFragment());
        }
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
