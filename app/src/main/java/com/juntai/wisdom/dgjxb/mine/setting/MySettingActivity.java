package com.juntai.wisdom.dgjxb.mine.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.DialogUtil;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.RxScheduler;
import com.juntai.wisdom.basecomponent.utils.RxTask;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MyMenuBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.entrance.BackPwdActivity;
import com.juntai.wisdom.dgjxb.entrance.EntranceContract;
import com.juntai.wisdom.dgjxb.entrance.EntrancePresent;
import com.juntai.wisdom.dgjxb.mine.AboutActivity;
import com.juntai.wisdom.dgjxb.mine.MyCenterContract;
import com.juntai.wisdom.dgjxb.base.update.UpdateActivity;
import com.juntai.wisdom.dgjxb.utils.StringTools;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人设置
 *
 * @aouther ZhangZhenlong
 * @date 2020/3/9
 */
public class MySettingActivity extends UpdateActivity<EntrancePresent> implements EntranceContract.IEntranceView {
    SettingMenuAdapter settingMenuAdapter;
    List<MyMenuBean> menuBeans = new ArrayList<>();

    private RecyclerView mMenuRecycler;
    PlatformDb platDB;
    public String QQId = "", QQName = "", WeChatId = "", WeChatName = "";

    MyHandler myHandler = new MyHandler(this);
    static class MyHandler extends Handler {
        WeakReference<Activity> mActivity;//弱引用
        MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MySettingActivity theActivity = (MySettingActivity) mActivity.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 1:
                    theActivity.updateBind();
                    break;
            }
        }
    }

    @Override
    protected EntrancePresent createPresenter() {
        return new EntrancePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void initView() {
        setTitleName("个人设置");
        mMenuRecycler = (RecyclerView) findViewById(R.id.menu_recycler);

        settingMenuAdapter = new SettingMenuAdapter(R.layout.my_center_menu_item, menuBeans);
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMenuRecycler.setAdapter(settingMenuAdapter);

        settingMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (settingMenuAdapter.getData().get(position).getTag()) {
                    case MyCenterContract.SET_UPDATE_TAG:
                        update(true);
                        break;
                    case MyCenterContract.SET_CLEAR_TAG:
                        DialogUtil.getMessageDialog(mContext, "将清理掉应用本地的图片和视频缓存文件", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RxScheduler.doTask(MySettingActivity.this, new RxTask<String>("清理成功") {
                                    @Override
                                    public void doOnIoThread() {
                                        FileCacheUtils.clearAll(mContext.getApplicationContext());
                                    }
                                    @Override
                                    public void doOnUIThread(String s) {
                                        ToastUtils.success(mContext.getApplicationContext(), s);
                                    }
                                });
                            }
                        }).show();
                        break;
                    case MyCenterContract.SET_UPDATE_TEL_TAG:
                        startActivity(new Intent(mContext, settingMenuAdapter.getData().get(position).getCls()).putExtra("pageType", 1));
                        break;
                    case MyCenterContract.SET_WEIXIN_TAG:
                        //微信
                        if (StringTools.isStringValueOk(MyApp.getUser().getData().getWeChatName())){
                            ToastUtils.info(mContext,"微信已绑定");
                        }else {
                            bindQQOrWeChat(Wechat.NAME);
                        }
                        break;
                    case MyCenterContract.SET_QQ_TAG:
                        //qq
                        if (StringTools.isStringValueOk(MyApp.getUser().getData().getQqName())){
                            ToastUtils.info(mContext,"QQ已绑定");
                        }else {
                            bindQQOrWeChat(QQ.NAME);
                        }
                        break;
                    case MyCenterContract.SET_UPDATE_PSD_TAG://修改密码
                        startActivity(new Intent(mContext, settingMenuAdapter.getData().get(position).getCls()).putExtra("pageType", 2));
                        break;
                    default:
                        if (settingMenuAdapter.getData().get(position).getCls() != null) {
                            startActivity(new Intent(mContext, settingMenuAdapter.getData().get(position).getCls()));
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        menuBeans.clear();
        menuBeans.add(new MyMenuBean("修改密码", 0, R.mipmap.set_psd, MyCenterContract.SET_UPDATE_PSD_TAG, BackPwdActivity.class));
        //        menuBeans.add(new MyMenuBean("收货地址", 0, R.mipmap.set_address, MyCenterContract.SET_ADDRESS_TAG, MySettingActivity.class));
        menuBeans.add(new MyMenuBean("修改手机号", 0, R.mipmap.set_tel, MyCenterContract.SET_UPDATE_TEL_TAG, BackPwdActivity.class));
        menuBeans.add(new MyMenuBean("清理内存", 0, R.mipmap.set_clear, MyCenterContract.SET_CLEAR_TAG, null));
        menuBeans.add(new MyMenuBean("检测更新", 0, R.mipmap.set_update, MyCenterContract.SET_UPDATE_TAG, null));
        menuBeans.add(new MyMenuBean("关于我们", -1, R.mipmap.set_about, MyCenterContract.SET_ABOUT_TAG, AboutActivity.class));
        menuBeans.add(new MyMenuBean("绑定微信", 0, R.mipmap.set_wexin, MyCenterContract.SET_WEIXIN_TAG, null));
        menuBeans.add(new MyMenuBean("绑定QQ", 0, R.mipmap.set_qq, MyCenterContract.SET_QQ_TAG, null));
        settingMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        BaseResult baseResult = (BaseResult) o;
        if (baseResult != null){
            if (baseResult.status == 200){
                UserBean userBean = MyApp.getUser();
                if (StringTools.isStringValueOk(WeChatId)){
                    ToastUtils.success(mContext,"微信绑定成功");
                    userBean.getData().setWeChatName(WeChatName);
                }
                if (StringTools.isStringValueOk(QQId)){
                    ToastUtils.success(mContext,"QQ绑定成功");
                    userBean.getData().setQqName(QQName);
                }
                MyApp.setUser(userBean);
            }else {
                ToastUtils.error(mContext, "服务器开小差了");
            }
        }else {
            ToastUtils.error(mContext, "服务器开小差了");
        }
    }

    /**
     * 绑定第三方数据
     * @param name
     */
    public void bindQQOrWeChat(String name) {
        WeChatName = null;
        QQName = null;
        QQId = null;
        WeChatId = null;

        Platform plat = ShareSDK.getPlatform(name);

        if (!plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
            if (name.equals(QQ.NAME)){
                ToastUtils.warning(mContext,"未安装QQ");
            }else {
                ToastUtils.warning(mContext,"未安装微信");
            }
        }

        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //数据
                LogUtil.d("userinfo=" + platform.getDb().exportData());
                LogUtil.d("userinfo=" + platform.getName());
                LogUtil.d("userinfo=" + hashMap.toString());
                //用户资源都保存到res
                //通过打印res数据看看有哪些数据是你想要的
                if (i == Platform.ACTION_USER_INFOR) {
                    platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    LogUtil.e("id=" + platDB.getUserId());
                    if (platform.getName().equals(QQ.NAME)) {
                        QQId = platDB.getUserId();
                        QQName = platDB.getUserName();
                    } else {
                        WeChatId = platDB.getUserId();
                        WeChatName = platDB.getUserName();
                    }
                    myHandler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e(throwable.toString());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

//        ShareSDK.setActivity(this);//抖音登录适配安卓9.0
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
        //获取账号为“3189087725”的资料
        //plat.showUser(“3189087725”);
    }

    /**
     * 提交绑定结果
     */
    public void updateBind(){
        mPresenter.bindQQOrWeChat(MyApp.getAccount(), WeChatId, WeChatName, QQId, QQName, EntranceContract.BIND_QQ_OR_WECHAT);
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
