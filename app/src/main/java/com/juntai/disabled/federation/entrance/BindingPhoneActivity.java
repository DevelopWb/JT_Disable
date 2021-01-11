package com.juntai.disabled.federation.entrance;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.MainActivity;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.entrance.regist.RegistContract;
import com.juntai.disabled.federation.entrance.regist.RegistPresent;
import com.juntai.disabled.federation.entrance.sendcode.SmsCheckCodeActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;
import com.orhanobut.hawk.Hawk;

import cn.smssdk.SMSSDK;

/**
 * @description 绑定手机号
 * @aouther ZhangZhenlong
 * @date 2020-10-16
 */
public class BindingPhoneActivity extends SmsCheckCodeActivity<RegistPresent> implements RegistContract.IRegistView, View.OnClickListener {
    public String QQId, QQName, WeChatId, WeChatName;
    public static String QQID = "QQ_Id";
    public static String QQNAME = "QQ_Name";
    public static String WECHATID = "WeChat_Id";
    public static String WECHATNAME = "WeChat_Name";
    /**
     * 请输入注册手机号码
     */
    private EditText mPhoneEt;
    /**
     * 请输入短信验证码
     */
    private EditText mCheckCodeEt;
    /**
     * 发送验证码
     */
    private TextView mSendCheckCodeTv;
    /**
     * 绑定
     */
    private TextView mBindingTv;

    private EntrancePresent entrancePresent;

    @Override
    protected RegistPresent createPresenter() {
        return new RegistPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initView() {
        setTitleName("绑定账号");
        QQId = getIntent().getStringExtra(QQID);
        QQName = getIntent().getStringExtra(QQNAME);
        WeChatId = getIntent().getStringExtra(WECHATID);
        WeChatName = getIntent().getStringExtra(WECHATNAME);
        if (QQId == null && WeChatId == null){
            ToastUtils.warning(mContext, "无法获取第三方账号信息");
            onBackPressed();
        }
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mCheckCodeEt = (EditText) findViewById(R.id.check_code_et);
        mSendCheckCodeTv = (TextView) findViewById(R.id.send_check_code_tv);
        mSendCheckCodeTv.setOnClickListener(this);
        mBindingTv = (TextView) findViewById(R.id.binding_tv);
        mBindingTv.setOnClickListener(this);
        entrancePresent = new EntrancePresent();
        entrancePresent.setCallBack(this);
    }

    @Override
    public void initData() {
    }

    @Override
    protected void initGetTestCodeButtonStatusStart() {
        mPresenter.initGetTestCodeButtonStatus();
    }

    @Override
    protected void initGetTestCodeButtonStatusStop() {
        mPresenter.receivedCheckCodeAndDispose();
        mSendCheckCodeTv.setText("发送验证码");
        mSendCheckCodeTv.setClickable(true);
        mSendCheckCodeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    protected void checkCodeSuccessed() {
        entrancePresent.bindQQOrWeChat(getTextViewValue(mPhoneEt), WeChatId, WeChatName, QQId, QQName, EntranceContract.BIND_QQ_OR_WECHAT);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            default:
                break;
            case EntranceContract.BIND_QQ_OR_WECHAT:
                BaseResult baseResult = (BaseResult) o;
                if (baseResult != null){
                    if (baseResult.status == 200){
                        ToastUtils.success(mContext, "绑定成功");
                        entrancePresent.login(null, null, WeChatId, QQId, EntranceContract.LOGIN_TAG);
                    }else if (baseResult.status == 401){
                        ToastUtils.warning(mContext, "该账号未注册，请先完成注册");
//                        startActivity(new Intent(mContext, RegistActivity.class));
//                        onBackPressed();
                    }else {
                        ToastUtils.error(mContext, "服务器开小差了");
                    }
                }else {
                    ToastUtils.error(mContext, "服务器开小差了");
                }
                break;
            case EntranceContract.LOGIN_TAG:
                UserBean loginBean = (UserBean) o;
                if (loginBean != null) {
                    if (loginBean.status == 200) {
//                        ToastUtils.success(mContext, "登录成功");
                        MyApp.isReLoadWarn = true;
                        Hawk.put(AppUtils.SP_KEY_USER,loginBean);
                        Hawk.put(AppUtils.SP_KEY_TOKEN,loginBean.getData().getToken());
                        Hawk.put(AppUtils.SP_RONGYUN_TOKEN,loginBean.getData().getrOngYunToken());
                        startActivity(new Intent(mContext, MainActivity.class));
                        onBackPressed();
                        LogUtil.d("token=" + MyApp.getUserToken());
                    } else {
                        verify = false;
                        ToastUtils.error(mContext, loginBean.message == null? "服务器开小差了" : loginBean.message);
                    }
                }else {
                    verify = false;
                    ToastUtils.error(mContext, "服务器开小差了");
                }
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        verify = false;
        ToastUtils.error(mContext, (String) o);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.send_check_code_tv:
                mPresenter.sendCheckCode(getTextViewValue(mPhoneEt),SMS_TEMP_CODE);
                break;
            case R.id.binding_tv:
                if (!mPresenter.checkMobile(getTextViewValue(mPhoneEt))) {
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mCheckCodeEt))) {
                    checkFormatError("验证码不能为空");
                    return;
                }
                if (!verify) {
                    SMSSDK.submitVerificationCode("+86",getTextViewValue(mPhoneEt),getTextViewValue(mCheckCodeEt));
                }
                break;
        }
    }

    @Override
    public void updateSendCheckCodeViewStatus(long second) {
        if (second > 0) {
            mSendCheckCodeTv.setText("重新发送 " + second + "s");
            mSendCheckCodeTv.setClickable(false);
            mSendCheckCodeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        } else {
            mSendCheckCodeTv.setText("发送验证码");
            mSendCheckCodeTv.setClickable(true);
            mSendCheckCodeTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }
    }

    @Override
    public void checkFormatError(String error) {
        ToastUtils.warning(mContext, error);
    }

    @Override
    protected void onDestroy() {
        entrancePresent.setCallBack(null);
        super.onDestroy();
    }
}
