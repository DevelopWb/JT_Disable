package com.juntai.disabled.federation;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.juntai.disabled.basecomponent.app.BaseApplication;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.bean.MapMenuButton;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.bean.message.UnReadCountBean;
import com.juntai.disabled.federation.entrance.LoginActivity;
import com.juntai.disabled.federation.entrance.complete_info.CompleteInfoActivity;
import com.juntai.disabled.federation.greenDao.DaoMaster;
import com.juntai.disabled.federation.greenDao.DaoSession;
import com.juntai.disabled.federation.home_page.call_to_police.VerifiedActivity;
import com.juntai.disabled.federation.home_page.news.news_info.NewsNormalInfoActivity;
import com.juntai.disabled.federation.home_page.news.news_info.NewsVideoInfoActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.im.ModuleIm_Init;
import com.juntai.disabled.im.UserIM;
import com.juntai.disabled.video.ModuleVideo_Init;
import com.mob.MobSDK;
import com.orhanobut.hawk.Hawk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.rongcloud.rtc.initRoom.IMRoom_Init;

import static com.juntai.disabled.basecomponent.app.BaseApplication.app;

/**
 * @aouther Ma
 * @date 2019/3/12
 */
public class MyApp extends BaseApplication {
    public static MyApp app;
    public static int CHECK_UPDATE_TYPE = 1;//类型id（1：警小宝）（2：巡小管）（3：邻小帮）
    public boolean isFinish = false;
    private String BUGLY_APPID = "77648a6776";//bugly appid警小宝
    public LatLng myLocation;
    public BDLocation bdLocation;
    public static long lastClickTime;//上次点击按钮时间
    public static int timeLimit = 1000;
    private static DaoSession daoSession;
    private static final String DATA_BASE_NAME = "db_dgjxb";//数据库名称

    public static int BASE_REQUESR = 10086;
    public static int BASE_RESULT = 10087;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Hawk.init(this).build();
        MobSDK.init(this);
        //Im模块初始化
        ModuleIm_Init.init(this);
        IMRoom_Init.init(this);
        //Video模块初始化
        ModuleVideo_Init.init();
        //百度地图初始化
        SDKInitializer.initialize(this);
        //        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //创建压缩图片存放目录
        FileCacheUtils.creatFile(FileCacheUtils.getAppImagePath());
//        initBugly();
        initGreenDao();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    /**
//     * 初始化bugly
//     */
//    private void initBugly() {
//        //        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
//        //        Bugly.init(this, BUGLY_APPID, false);
//        //
//        //bug上传
//        // 获取当前包名
//        String packageName = getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(this, BUGLY_APPID, false);
//
//    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DATA_BASE_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static UserBean getUser() {
        return Hawk.get(AppUtils.SP_KEY_USER);
    }

    public static void setUser(UserBean userBean) {
        Hawk.put(AppUtils.SP_KEY_USER, userBean);
    }

    /**
     * 获取usertoken
     *
     * @return
     */
    public static String getUserToken() {
        return Hawk.get(AppUtils.SP_KEY_TOKEN);
    }

    /**
     * 获取融云token
     *
     * @return
     */
    public static String getUserRongYunToken() {
        return Hawk.get(AppUtils.SP_RONGYUN_TOKEN);
    }



    /**
     * 获取账号
     *
     * @return
     */
    public static String getAccount() {
        UserBean userBean = getUser();
        return userBean== null ? "" : userBean.getData().getAccount();
    }

    /**
     * 获取id
     *
     * @return
     */
    public static int getUid() {
        return getUser() == null ? -1 : getUser().getData().getUserId();
    }

    /**
     * 获取头像
     *
     * @return
     */
    public static String getUserHeadImg() {
        return getUser() == null ? "" : getUser().getData().getHeadPortrait();
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public static UnReadCountBean.DataBean getUnReadCountBean() {
        if (isLogin()){
            return Hawk.get(AppUtils.SP_KEY_UNREAD_COUNT) == null? new UnReadCountBean.DataBean() : Hawk.get(AppUtils.SP_KEY_UNREAD_COUNT);
        }else {
            return new UnReadCountBean.DataBean();
        }
    }

    /**
     * 缓存未读消息数
     *
     * @param unReadCountBean
     */
    public static void setUnReadCountBean(UnReadCountBean.DataBean unReadCountBean) {
        Hawk.put(AppUtils.SP_KEY_UNREAD_COUNT, unReadCountBean);
    }

    /**
     * 获取本地地图菜单
     *
     * @return
     */
    public static MapMenuButton getMapMenuButton() {
        return Hawk.get(AppUtils.SP_KEY_MAP_MENU) == null? new MapMenuButton() : Hawk.get(AppUtils.SP_KEY_MAP_MENU);
    }

    /**
     * 缓存线上地图菜单
     *
     * @param mapMenuButton
     */
    public static void setMapMenuButton(MapMenuButton mapMenuButton) {
        Hawk.put(AppUtils.SP_KEY_MAP_MENU, mapMenuButton);
    }

    /**
     * 获取当前定位
     *
     * @return
     */
    public LatLng getMyLocation() {
        if (myLocation == null) {
            myLocation = new LatLng(0, 0);
        }
        return myLocation;
    }

    public void setMyLocation(LatLng myLocation) {
        this.myLocation = myLocation;
    }


    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    @Override
    public void appBackground(boolean isBackground, Activity activity) {
        if (isBackground && !isFinish) {
            //            NitoficationTool.sendNotif(activity,
            //                    11,
            //                    "挂起",
            //                    BaseAppUtils.getAppName() + "服务正在运行",
            //                    R.mipmap.app_icon,
            //                    true,
            //                    new Intent(activity, MainActivity.class));
        } else {
            //变为前台
            //            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //            manager.cancelAll();
        }
    }

    //    @Override
    //    public String getTinkerId() {
    //        return BUGLY_APPID;
    //    }


    /**
     * 上传成功后删除压缩图片和视频缓存
     */
    public static void delCache() {
        FileCacheUtils.clearImage();
        FileCacheUtils.clearVideo();
    }

    /**
     * 更新im用户信息
     *
     * @param id
     * @param name
     * @param image
     */
    public static void addImUserInfo(String id, String name, String image) {
        ModuleIm_Init.setUser(new UserIM(id, name, image));
    }

    /**
     * 退出登录清理缓存配置
     */
    public void clearUserData() {
        Hawk.delete(AppUtils.SP_KEY_USER);
        Hawk.delete(AppUtils.SP_KEY_TOKEN);
        Hawk.delete(AppUtils.SP_RONGYUN_TOKEN);
        //        Hawk.delete(AppUtils.SP_NEWS_SAVE_DRAFTS);
        Hawk.delete(AppUtils.SP_KEY_UNREAD_COUNT);
        //        clearActivitys();
    }


    /**
     * 防止暴力点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < timeLimit) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 跳转资讯详情
     */
    public static void gotoNewsInfo(int type, int newsId, Context context) {
        Intent newsIntent = null;
        if (type == 1) {//视频
            newsIntent = new Intent(context, NewsVideoInfoActivity.class).putExtra(AppUtils.ID_KEY, newsId);
        } else {
            newsIntent = new Intent(context, NewsNormalInfoActivity.class).putExtra(AppUtils.ID_KEY, newsId);
        }
        context.startActivity(newsIntent);
    }

    /**
     * 跳转登录
     */
    public static void goLogin() {
        app.getNowActivity().startActivity(new Intent(app, LoginActivity.class));
    }
    public static boolean isLogin() {
        if (getUser() == null) {
            return false;
        } else {
            return true;
        }
    }

}
