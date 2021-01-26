package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.MainPagerAdapter;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessPresent;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.DisabilityStudentBursaryFragment;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.DisabilityStudentBursaryNextYearFragment;

/**
 * @aouther tobato
 * @description 描述  大学生助学金
 * @date 2021/1/26 9:45
 */
public abstract  class BaseStudentBursaryActivity extends BaseAppActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private String[] tabs = {"首次申请", "第2年以后申请"};
    private TabLayout mBursaryTab;
    private ViewPager mBursaryVp;
    protected abstract SparseArray<Fragment> getFragments();
    protected abstract String getTitleName();
    @Override
    public int getLayoutView() {
        return R.layout.activity_disability_bursary;
    }

    @Override
    public void initView() {

        setTitleName(getTitleName());
        mBursaryTab = (TabLayout) findViewById(R.id.bursary_tab);
        mBursaryVp = (ViewPager) findViewById(R.id.bursary_vp);
        initViewPageWithTabLayout();
    }



    @Override
    public void initData() {

    }


    /**
     * viewpage和tablayout绑定数据
     */
    private void initViewPageWithTabLayout() {

        FragmentManager fragmentManager = getSupportFragmentManager();//外面如果还有一层fragment时，使用这个方法
        //        FragmentManager fragmentManager = getSupportFragmentManager();//外面不是fragment包裹时，使用这个方法
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(fragmentManager,
                mContext, tabs, getFragments());
        mBursaryVp.setAdapter(pagerAdapter);
        mBursaryTab.setupWithViewPager(mBursaryVp);

        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = mBursaryTab.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));//自定义View

            }
        }
        //tab的字体选择器,默认黑色,选择时红色
        mBursaryTab.setTabTextColors(ContextCompat.getColor(mContext, R.color.black),
                ContextCompat.getColor(mContext,
                        R.color.blue));
        //tab的下划线颜色,默认是粉红色
        mBursaryTab.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        //        //        //更改tab下划线的宽度
                PubUtil.setIndicator(mBursaryTab, 20, 20);
        //        将tab下划线隐藏，也可以在xml里面配置高的值为0dp来实现隐藏
//        mBursaryTab.setSelectedTabIndicatorHeight(0);

    }






    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    protected BusinessPresent createPresenter() {
        return null;
    }

}
