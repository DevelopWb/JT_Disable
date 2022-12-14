package com.juntai.tyb.base;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.BaseRequestLocationActivity;
import com.juntai.disabled.bdmap.utils.NagivationUtils;
import com.juntai.tyb.login.LoginActivity;
import com.juntai.tyb.uitils.HawkProperty;
import com.juntai.tyb.uitils.MD5;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppActivity<P extends BasePresenter> extends BaseRequestLocationActivity<P> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 导航
     * @param endLatlng  目的地
     * @param endName  目的地名称
     */
    public void navigationLogic(LatLng endLatlng,String endName) {
        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        final String item_list[] = {"腾讯地图", "高德地图", "百度地图"};
        build.setItems(item_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (item_list[which]) {
                    case "腾讯地图":
                        NagivationUtils.getInstant().openTencent(mContext,endLatlng.latitude,endLatlng.longitude,endName);
                        break;
                    case "高德地图":
                        NagivationUtils.getInstant().openGaoDeMap(mContext,endLatlng.latitude,endLatlng.longitude,endName);
                        break;
                    case "百度地图":
                        NagivationUtils.getInstant().openBaiduMap(mContext,endLatlng.latitude,endLatlng.longitude,endName);
                        break;
                    default:
                        break;
                }
            }
        });
        build.setTitle("请选择导航方式");
        AlertDialog alertDialog = build.create();
        alertDialog.show();
    }



    /**
     * 发送更新头像的广播
     */
    public void broadcasetRefreshHead() {
        Intent intent = new Intent();
        intent.setAction("action.refreshHead");
        sendBroadcast(intent);
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account", UserInfoManager.getUserAccount());
        builder.add("token", UserInfoManager.getUserToken());
        return builder;
    }
    /**
     * 获取requestBody
     *
     * @return
     */
    public RequestBody getRequestBody(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
    }
    /**
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", UserInfoManager.getUserAccount())
                .addFormDataPart("token", UserInfoManager.getUserToken())
                .addFormDataPart("uid", String.valueOf(UserInfoManager.getUserId()));
    }

    /**
     * 是否是内部账号
     *
     * @return
     */
    public boolean isInnerAccount() {
        return UserInfoManager.isTest();
    }

    @Override
    public void singleLogin() {
        ToastUtils.toast(mContext, "登录过期,请重新登录");
        Hawk.delete(HawkProperty.USER_INFO);
        ActivityManagerTool.getInstance().finishApp();
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }

    /**
     * 将list中的数据转成字符串  并以逗号隔开
     *
     * @return
     */
    public String getListToString(List<String> arrays) {
        if (arrays == null || arrays.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(arrays.size());
        for (String selectedServicePeople : arrays) {
            sb.append(selectedServicePeople + ",");
        }
        String people = sb.toString();
        if (StringTools.isStringValueOk(people)) {
            people = people.substring(0, people.length() - 1);
        }
        return people;
    }

    /**
     * 复制电话号码
     */
    public void copyTelephoneNum(String text) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("simple text", text);
        //传入clipdata对象.
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public boolean requestLocation() {
        return false;
    }


    /**
     * 加密密码
     * @param pwd
     * @return
     */
    protected  String  encryptPwd(String account,String pwd){
        return MD5.md5(String.format("%s#%s", account, pwd));
    }

}
