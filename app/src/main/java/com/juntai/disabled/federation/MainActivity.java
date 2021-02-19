package com.juntai.disabled.federation;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
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
import com.juntai.disabled.basecomponent.app.BaseApplication;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.NotificationTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.federation.base.MainPageContract;
import com.juntai.disabled.federation.base.MainPagePresent;
import com.juntai.disabled.federation.base.MainPagerAdapter;
import com.juntai.disabled.federation.base.customview.CustomViewPager;
import com.juntai.disabled.federation.base.update.UpdateActivity;
import com.juntai.disabled.federation.bean.IMUsersBean;
import com.juntai.disabled.federation.bean.news.NewsDraftsBean;
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
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends UpdateActivity<MainPagePresent> implements ViewPager.OnPageChangeListener,
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;
    private TabLayout mainTablayout;
    private String[] title = new String[]{"首页", "业务办理", "信息采集", "我的"};
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
        mFragments.append(0, new MyMapFragment());//地图
        mFragments.append(1, new HandlerBusinessFragment());//
        mFragments.append(2, new TakeInfosFragment());//
        mFragments.append(3, new MyCenterFragment());//资讯
        //
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        mainViewpager.setOffscreenPageLimit(4);
        initTab();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConfig.BROAD_LOGIN);
        intentFilter.addAction(ActionConfig.BROAD_CASE_DETAILS);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void initData() {
        //        if (MyApp.isLogin()){
        //            getIMUsers();
        //            /**登录IM*/
        //            ModuleIm_Init.connectIM(MyApp.getUserRongYunToken());
        //        }
        update(false);
        /**分享隐私授权 true*/
        MobSDK.submitPolicyGrantResult(true, null);
    }

    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, tabDrawables,
                mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //        mainTablayout.newTab();
        /*viewpager切换默认第一个*/
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
    }


    AlertDialog alertDialog;
    int id22;
    String content;
    int type;//推送类型，1案件指派、3巡检退回


    /**
     * 登录被顶
     */
    public class CGBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActionConfig.BROAD_LOGIN.equals(intent.getAction())) {
                //重新登录
                ModuleIm_Init.logout();
                //登录信息设置为空
                String error = intent.getStringExtra("error");
                ToastUtils.info(MyApp.app, error);
                //                SPTools.saveString(mContext, "login", "");
                MyApp.app.clearUserData();//清理数据
                ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
                startActivity(new Intent(mContext, LoginActivity.class));
                //重置界面
                //                EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
            } else if (ActionConfig.BROAD_CASE_DETAILS.equals(intent.getAction())) {
                LogUtil.d("RongYun-消息监听", "MainActivity-----------");
                //案件指派
                id22 = intent.getIntExtra("id", 0);
                //推送类型：1案件指派，3巡检退回，7任务指派，8任务退回，9巡检完成推送（巡检记录审核通过）
                type = intent.getIntExtra("type", 0);
                if (type == 1 || type == 7 || type == 8) {
                    return;
                }
                content = intent.getStringExtra("content");
                if (BaseApplication.app.isRun) {
                    LogUtil.d("777777", "-----------前台");
                    //前台发广播
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    } else {
                        alertDialog =
                                new AlertDialog.Builder(MyApp.app.getNowActivity()).setPositiveButton("查看",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(setNotificationIntent(type));
                                            }
                                        }).setNegativeButton("取消", null).create();
                    }

                    alertDialog.setTitle(setNotificationTile(type));
                    if (StringTools.isStringValueOk(content)) {
                        alertDialog.setMessage(type == 1 ? "要求：" + content : "说明：" + content);
                    }
                    alertDialog.show();
                } else {
                    LogUtil.d("888888", "-----------后台===发送通知");
                    //后台发通知
                    Notification notification =
                            NotificationTool.sendNotifMessage(BaseApplication.app, new Random().nextInt(10000),
                                    setNotificationTile(type), content, R.mipmap.app_jing_icon,
                                    false, setNotificationIntent(type));

                }
            }
        }
    }

    /**
     * 设置标题
     *
     * @param titleType
     * @return
     */
    public String setNotificationTile(int titleType) {
        String title = "通知消息";
        switch (titleType) {
            case 3:
                title = "巡检退回";
                break;
            case 9:
                title = "巡检完成";
                break;
        }
        return title;
    }

    /**
     * 设置跳转Intent
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
     * 发布的popupwindow
     *
     * @param view
     */
    public void initPopTypePublish(View view) {
        if (!MyApp.isLogin()) {
            MyApp.goLogin();
            return;
        }
        View viewPop = LayoutInflater.from(mContext).inflate(R.layout.publish_menu_layout, null);
        //背景颜色
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
        //显示（自定义位置）
        popupWindow.showAtLocation(mainTablayout, Gravity.TOP, 0, 0);
        if (popupWindow.isShowing()) {
            mImmersionBar.statusBarColor(R.color.gray_light).statusBarDarkFont(true).init();
        }
        viewPop.findViewById(R.id.anjian_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //没有绑定手机号
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishCaseActivity.class));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.zixun_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //没有绑定手机号
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishNewsActivity.class));
            popupWindow.dismiss();
        });
    }

    /**
     * 获取im - - users
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
        //主线程中调用：
        //            mHandler.postDelayed(runnable, 1000 * 62);//延时60秒
        //        }
    }

    @Override
    protected void onDestroy() {
        Log.e("EEEEEEEEEE", " = MainActivity  onDestroy");
        stopService(new Intent(MainActivity.this, LocateAndUpload.class));
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage("请选择退出方式")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApp.app.isFinish = true;
                        ModuleIm_Init.logout();
                        finish();
                    }
                })
                .setNegativeButton("挂起", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //模拟home键,发送广播
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
     * drawerlayout 开启
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(NewsDraftsBean newsDraftsBean) {
        if (newsDraftsBean != null && newsDraftsBean.getDraftsId() != null) {
            mPresenter.deleteNewsDrafts(newsDraftsBean.getDraftsId(), MainPageContract.DELETE_NEWS_DRAFTS);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UN_READ_MESSAG_TAG.equals(test)) {
            //刷新未读标记
            adapter.setUnReadMsg(MyApp.getUnReadCountBean().getMessageCount() + MyApp.getUnReadCountBean().getImCount());
        } else if (ActionConfig.BROAD_LOGIN_AFTER.equals(test)) {
//            getIMUsers();
//            /**登录IM*/
//            ModuleIm_Init.connectIM(MyApp.getUserRongYunToken());
        }
    }
}
