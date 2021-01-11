package com.juntai.tyb;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.tyb.base.customView.CustomViewPager;
import com.juntai.tyb.base.update.UpdateActivity;
import com.juntai.tyb.bean.mine.UnReadMsgBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.HomePageFragment;
import com.juntai.tyb.homePage.MainPagerAdapter;
import com.juntai.tyb.homePage.careTaker.addCareTaker.SearchCareTakerFragment;
import com.juntai.tyb.homePage.search.SearchFragment;
import com.juntai.tyb.mine.MineContract;
import com.juntai.tyb.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  首页
 * @date 2020/7/29 16:55
 */
public class MainActivity extends UpdateActivity<MainPresent> implements SearchFragment.OnSearchCallBack,
        ViewPager.OnPageChangeListener, BaseIView {
    private long mExitTime;
    private CustomViewPager mMainViewpager;
    private TabLayout mMainTablayout;
    List<Fragment> mFragments = new ArrayList<>();
    private MainPagerAdapter adapter;
    String[] titles = {"首页", "添加", "我的"};
    int[] tabDrawables = {R.drawable.bottom_button_home_press, R.drawable.bottom_button_post_press,
            R.drawable.bottom_button_user_press};
    private SearchCareTakerFragment searchCareTakerFragment;
    private HomePageFragment mHomePageFragment;
    private MineFragment mMineFragment;


    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        mImmersionBar.reset().statusBarDarkFont(true).init();
        mMainViewpager = (CustomViewPager) findViewById(R.id.main_viewpager);
        mMainTablayout = (TabLayout) findViewById(R.id.main_tablayout);
        searchCareTakerFragment = new SearchCareTakerFragment();
        mHomePageFragment = new HomePageFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mHomePageFragment);
        mFragments.add(searchCareTakerFragment);
        mFragments.add(mMineFragment);
        initTab();
//        update(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.unReadMsg(getPublishMultipartBody().build(), MineContract.UNREAD_MSG);
    }

    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), mContext, titles, tabDrawables, mFragments);
        mMainViewpager.setAdapter(adapter);
        mMainViewpager.setOffscreenPageLimit(titles.length);
        //        /*viewpager切换监听，包含滑动点击两种*/
        mMainViewpager.addOnPageChangeListener(this);
        //设置不可左右滑动
        mMainViewpager.setScanScroll(false);
        //TabLayout
        //        mMainTablayout.addTab(mMainTablayout.newTab().setText("index").setIcon(R.mipmap.point_focus));
        mMainTablayout.setupWithViewPager(mMainViewpager);
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < mMainTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mMainTablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        /*viewpager切换默认第一个*/
        mMainViewpager.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        //未读消息
        UnReadMsgBean unReadMsgBean = (UnReadMsgBean) o;
        int size = unReadMsgBean.getData();
        mMineFragment.setUnReadMsg(unReadMsgBean);
        TabLayout.Tab tab = mMainTablayout.getTabAt(2);
        TextView unReadTv =   tab.getCustomView().findViewById(R.id.unread_tv);
        if (size>0) {
            unReadTv.setVisibility(View.VISIBLE);
        }else {
            unReadTv.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public void onSearch(String textContent) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onBackPressed() {
        exit();
    }


    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再次点击返回退出托养宝", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
//            ModuleIm_Init.logout();
            finish();
            System.exit(0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected MainPresent createPresenter() {
        return new MainPresent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, LocateAndUpload.class));
    }


}