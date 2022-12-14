package com.juntai.disabled.federation;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.juntai.disabled.basecomponent.app.BaseApplication;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.NotificationTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.BaseAppFragment;
import com.juntai.disabled.federation.base.MainPageContract;
import com.juntai.disabled.federation.base.MainPagePresent;
import com.juntai.disabled.federation.base.MainPagerAdapter;
import com.juntai.disabled.federation.base.customview.CustomViewPager;
import com.juntai.disabled.federation.base.update.UpdateActivity;
import com.juntai.disabled.federation.bean.IMUsersBean;
import com.juntai.disabled.federation.bean.history_track.LocationBean;
import com.juntai.disabled.federation.entrance.BindingPhoneActivity;
import com.juntai.disabled.federation.entrance.LoginActivity;
import com.juntai.disabled.federation.home_page.MyMapFragment;
import com.juntai.disabled.federation.home_page.business.HandlerBusinessFragment;
import com.juntai.disabled.federation.home_page.inspection.InspectionDetailActivity;
import com.juntai.disabled.federation.home_page.inspection.PublishInspectionActivity;
import com.juntai.disabled.federation.home_page.law_case.PublishCaseActivity;
import com.juntai.disabled.federation.home_page.news.news_publish.PublishNewsActivity;
import com.juntai.disabled.federation.home_page.takeInfos.TakeInfosFragment;
import com.juntai.disabled.federation.mine.MyCenterFragment;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;
import com.juntai.disabled.federation.utils.ViewUtil;
import com.juntai.disabled.im.ModuleIm_Init;
import com.juntai.disabled.im.UserIM;
import com.mob.MobSDK;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends BaseAppActivity<MainPagePresent> implements ViewPager.OnPageChangeListener,
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;
    final static Handler mHandler = new Handler();
    List<LocationBean> cacheDatas = new ArrayList<>();//

    private TabLayout mainTablayout;
    private String[] title = new String[]{"??????", "????????????", "????????????", "??????"};
    private int[] tabDrawables = new int[]{R.drawable.home_index, R.drawable.handler_business,
            R.drawable.take_msg, R.drawable.home_msg};
    private SparseArray<Fragment> mFragments = new SparseArray<>();
    //
    CGBroadcastReceiver broadcastReceiver = new CGBroadcastReceiver();

    PopupWindow popupWindow;


    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainViewpager = findViewById(R.id.main_viewpager);
        mainTablayout = findViewById(R.id.main_tablayout);
        mainLayout = findViewById(R.id.main_layout);
        mainViewpager.setScanScroll(false);
        mFragments.append(0, new MyMapFragment());//??????
        mFragments.append(1, new HandlerBusinessFragment());//
        mFragments.append(2, new TakeInfosFragment());//
        mFragments.append(3, new MyCenterFragment());//??????
        //
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        mainViewpager.setOffscreenPageLimit(4);
        initTab();
        //????????????
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConfig.BROAD_LOGIN);
        intentFilter.addAction(ActionConfig.BROAD_CASE_DETAILS);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void initData() {
        update(false);
        if (MyApp.isLogin()) {
            initForLogin();
        }
        /**?????????????????? true*/
        MobSDK.submitPolicyGrantResult(true, null);
    }

    /**
     * ????????????????????????????????????????????????????????????????????????
     */
    private void initForLogin() {
        getIMUsers();
        /**??????IM*/
        ModuleIm_Init.connectIM(MyApp.getUserRongYunToken());
        if (MyApp.getUser().getData().getSettleStatus() == 2) {
            //?????????????????????
            mHandler.postDelayed(runnable, 1000 * 1);//??????1???
        }
    }

    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, tabDrawables,
                mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(title.length);
        /*viewpager???????????????????????????????????????*/
        mainViewpager.addOnPageChangeListener(this);
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = mainTablayout.newTab();
            if (tab != null) {
                if (i == title.length - 1) {
                    tab.setCustomView(adapter.getTabView(i, true));
                } else {
                    tab.setCustomView(adapter.getTabView(i, false));
                }
                mainTablayout.addTab(tab);
            }
        }

        mainTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewpager.setCurrentItem(tab.getPosition(), false);
//                if (tab.getPosition() == 1) {
//                    //????????????
//                    initPopType(mainTablayout);
//                } else {
//                    mainViewpager.setCurrentItem(tab.getPosition(), false);
//
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 1) {
//                    //????????????
//                    initPopType(mainTablayout);
//                }
            }
        });

        //        mainTablayout.newTab();
        /*viewpager?????????????????????*/
        mainViewpager.setCurrentItem(0);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shadow_tv:
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MainPageContract.UPLOAD_HISTORY:
                if (cacheDatas != null) {
                    for (LocationBean locationBean : cacheDatas) {
                        MyApp.getDaoSession().getLocationBeanDao().delete(locationBean);
                    }
                }
                break;
            default:
                break;
        }
    }

    AlertDialog alertDialog;
    int id22;
    String content;
    int type;//???????????????1???????????????3????????????


    /**
     * ????????????
     */
    public class CGBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActionConfig.BROAD_LOGIN.equals(intent.getAction())) {
                //????????????
                ModuleIm_Init.logout();
                //????????????????????????
                String error = intent.getStringExtra("error");
                ToastUtils.info(MyApp.app, error);
                //                SPTools.saveString(mContext, "login", "");
                MyApp.app.clearUserData();//????????????
                mHandler.removeCallbacks(runnable);
                mHandler.removeCallbacksAndMessages(null);
                ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
                startActivity(new Intent(mContext, LoginActivity.class));
                //????????????
                //                EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
            } else if (ActionConfig.BROAD_CASE_DETAILS.equals(intent.getAction())) {
                LogUtil.d("RongYun-????????????", "MainActivity-----------");
                //????????????
                id22 = intent.getIntExtra("id", 0);
                //???????????????1???????????????3???????????????7???????????????8???????????????9????????????????????????????????????????????????
                type = intent.getIntExtra("type", 0);
                if (type == 1 || type == 7 || type == 8) {
                    return;
                }
                content = intent.getStringExtra("content");
                if (BaseApplication.app.isRun) {
                    LogUtil.d("777777", "-----------??????");
                    //???????????????
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    } else {
                        alertDialog =
                                new AlertDialog.Builder(MyApp.app.getNowActivity()).setPositiveButton("??????",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(setNotificationIntent(type));
                                            }
                                        }).setNegativeButton("??????", null).create();
                    }

                    alertDialog.setTitle(setNotificationTile(type));
                    if (StringTools.isStringValueOk(content)) {
                        alertDialog.setMessage(type == 1 ? "?????????" + content : "?????????" + content);
                    }
                    alertDialog.show();
                } else {
                    LogUtil.d("888888", "-----------??????===????????????");
                    //???????????????
                    Notification notification =
                            NotificationTool.sendNotifMessage(BaseApplication.app, new Random().nextInt(10000),
                                    setNotificationTile(type), content, R.mipmap.app_icon,
                                    false, setNotificationIntent(type));

                }
            }
        }
    }

    /**
     * ????????????
     *
     * @param titleType
     * @return
     */
    public String setNotificationTile(int titleType) {
        String title = "????????????";
        switch (titleType) {
            case 3:
                title = "????????????";
                break;
            case 9:
                title = "????????????";
                break;
        }
        return title;
    }

    /**
     * ????????????Intent
     *
     * @param titleType
     * @return
     */
    public Intent setNotificationIntent(int titleType) {
        Intent intent;
        switch (titleType) {
            case 3:
                intent = new Intent(mContext, InspectionDetailActivity.class).putExtra("id", id22);
                break;
            case 9:
                intent = new Intent(mContext, InspectionDetailActivity.class).putExtra("id", id22);
                break;
            default:
                intent = new Intent(mContext, MainActivity.class);
                break;
        }
        return intent;
    }

    /**
     * popupwindow
     *
     * @param view
     */
    public void initPopType(View view) {
        if (!MyApp.isLogin()) {
            MyApp.goLogin();
            return;
        }
        View viewPop = LayoutInflater.from(mContext).inflate(R.layout.publish_menu_layout, null);
        //????????????
        view.setBackgroundColor(Color.WHITE);
        TextView shadowTv = viewPop.findViewById(R.id.shadow_tv);
        shadowTv.setOnClickListener(this);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewUtil.getScreenHeight(this) - mainTablayout.getLayoutParams().height - MyApp.statusBarH, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mImmersionBar.statusBarColor(R.color.white).statusBarDarkFont(true).init();
            }
        });
        //???????????????????????????
        popupWindow.showAtLocation(mainTablayout, Gravity.TOP, 0, 0);
        if (popupWindow.isShowing()) {
            mImmersionBar.statusBarColor(R.color.gray_light).statusBarDarkFont(true).init();
        }
        viewPop.findViewById(R.id.anjian_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //?????????????????????
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishCaseActivity.class));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.zixun_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //?????????????????????
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishNewsActivity.class));
            popupWindow.dismiss();
        });
    }

    /**
     * ??????im - - users
     */
    public void getIMUsers() {
        AppNetModule.createrRetrofit()
                .getIMUsers(MyApp.getUserToken(), MyApp.getAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<IMUsersBean>(null) {
                    @Override
                    public void onSuccess(IMUsersBean o) {
                        ArrayList<UserIM> arrayList = new ArrayList<>();
                        for (IMUsersBean.DataBean bean : o.getData()) {
                            arrayList.add(new UserIM(bean.getAccount(), bean.getRealName(), bean.getHeadPortrait()));
                        }
                        ModuleIm_Init.setUsers(arrayList);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.error(MyApp.app, msg);
                    }
                });
    }

    @Override
    protected MainPagePresent createPresenter() {
        return new MainPagePresent();
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }

    @Override
    public boolean requestLocation() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //        if (MyApp.getUser().getData().getSettleStatus() == 2){
        //?????????????????????
        //            mHandler.postDelayed(runnable, 1000 * 62);//??????60???
        //        }
    }

    @Override
    protected void onDestroy() {
        Log.e("EEEEEEEEEE", " = MainActivity  onDestroy");
        stopService(new Intent(MainActivity.this, LocateAndUpload.class));
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        mHandler.removeCallbacks(runnable);
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage("?????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApp.app.isFinish = true;
                        ModuleIm_Init.logout();
                        finish();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //??????home???,????????????
                        //sendBroadcast(new Intent().setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                        // .putExtra("reason","homekey"));
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AppUtils.QR_SCAN_NOMAL && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Intent intent = new Intent(this, PublishInspectionActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }

        } else if (requestCode == AppUtils.QR_SCAN_FOR_XUANJIAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Intent intent = new Intent(this, PublishInspectionActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * drawerlayout ??????
     *
     * @param open
     */
    public void drawerlayoutOpened(boolean open) {
        if (open) {
            mImmersionBar.reset().statusBarDarkFont(true).init();
        } else {
            mImmersionBar.reset().statusBarColor(R.color.white).statusBarDarkFont(true).init();
        }
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN) //???ui????????????
    //    public void receiveMsg(NewsDraftsBean newsDraftsBean) {
    //        if (newsDraftsBean != null && newsDraftsBean.getDraftsId() != null) {
    //            mPresenter.deleteNewsDrafts(newsDraftsBean.getDraftsId(), MainPageContract.DELETE_NEWS_DRAFTS);
    //        }
    //    }

    @Subscribe(threadMode = ThreadMode.MAIN) //???ui????????????
    public void receiveMsg(String test) {
        if (ActionConfig.UN_READ_MESSAG_TAG.equals(test)) {
            //??????????????????
            adapter.setUnReadMsg(MyApp.getUnReadCountBean().getMessageCount() + MyApp.getUnReadCountBean().getImCount());
        } else if (ActionConfig.BROAD_LOGIN_AFTER.equals(test)) {
            initForLogin();
        } else if (ActionConfig.BROAD_LOGIN_OUT.equals(test)) {
            //????????????
            mHandler.removeCallbacks(runnable);
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * ???????????????????????????
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //do something
            List<LocationBean> datas = null;
            try {
                datas = MyApp.getDaoSession().getLocationBeanDao().loadAll();
            } catch (Exception e) {
                e.printStackTrace();
                datas = new ArrayList<>();
            }
            if (datas.size() > 0 && datas.size() < 30) {
                cacheDatas.clear();
                cacheDatas.addAll(datas);
                mPresenter.uploadHistory(new Gson().toJson(datas), MainPageContract.UPLOAD_HISTORY);
            } else {
                MyApp.getDaoSession().getLocationBeanDao().deleteAll();
            }
            //??????62s????????????run??????
            mHandler.postDelayed(runnable, 1000 * 62);
        }
    };
}
