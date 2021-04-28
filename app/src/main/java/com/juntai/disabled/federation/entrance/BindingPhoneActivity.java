package com.juntai.disabled.federation.entrance;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.EventManager;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MainActivity;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.entrance.regist.RegistContract;
import com.juntai.disabled.federation.entrance.sendcode.SmsCheckCodeActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

import cn.smssdk.SMSSDK;
import okhttp3.MultipartBody;

/**
 * @description 绑定手机号
 * @aouther ZhangZhenlong
 * @date 2020-10-16
 */
public class BindingPhoneActivity extends SmsCheckCodeActivity implements RegistContract.IRegistView,
        View.OnClickListener {
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
    private EditText mRegistCheckPwdEt;

    @Override
    public int getLayoutView() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initView() {
        setTitleName("绑定手机号");
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mCheckCodeEt = (EditText) findViewById(R.id.check_code_et);
        mSendCheckCodeTv = (TextView) findViewById(R.id.send_check_code_tv);
        mSendCheckCodeTv.setOnClickListener(this);
        mBindingTv = (TextView) findViewById(R.id.binding_tv);
        mBindingTv.setOnClickListener(this);
        entrancePresent = new EntrancePresent();
        entrancePresent.setCallBack(this);
        mRegistCheckPwdEt = (EditText) findViewById(R.id.regist_check_pwd_et);
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
        mSendCheckCodeTv.setText("获取验证码");
        mSendCheckCodeTv.setClickable(true);
        mSendCheckCodeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag,o);
        switch (tag){
            default:
                break;
            case EntranceContract.BIND_PHONE:
                ToastUtils.success(mContext, "绑定成功");
                UserBean userBean = MyApp.getUser();
                if (userBean != null) {
                    userBean.getData().setPhoneNumber(getTextViewValue(mPhoneEt));
                    Hawk.put(AppUtils.SP_KEY_USER,userBean);
                    onBackPressed();
                }
                break;
            case EntranceContract.BIND_QQ_OR_WECHAT:
                BaseResult baseResult = (BaseResult) o;
                if (baseResult != null){
                    if (baseResult.status == 200){
                        ToastUtils.success(mContext, "绑定成功");
                        entrancePresent.login(null, null, UserInfoManager.WECHAT_ID, UserInfoManager.QQ_ID,
                                EntranceContract.LOGIN_TAG);
                    }else if (baseResult.status == 401){
                        ToastUtils.warning(mContext, "该账号未注册，请先完成注册");
                        //                        startActivity(new Intent(mContext, RegistActivity.class));
                        //                        onBackPressed();
                    }else {
                        ToastUtils.error(mContext, baseResult.message == null?PubUtil.ERROR_NOTICE : baseResult.message);
                    }
                }else {
                    ToastUtils.error(mContext, PubUtil.ERROR_NOTICE);
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
                        EventManager.sendStringMsg(ActionConfig.BROAD_LOGIN_AFTER);
                        startActivity(new Intent(mContext, MainActivity.class));
                        onBackPressed();
                        LogUtil.d("token=" + MyApp.getUserToken());
                    } else {
                        ToastUtils.error(mContext, loginBean.message == null?PubUtil.ERROR_NOTICE : loginBean.message);
                    }
                }else {
                    ToastUtils.error(mContext, PubUtil.ERROR_NOTICE);
                }
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
       super.onError(tag,o);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.send_check_code_tv:
                mCheckCodeEt.setText("");
                mPresenter.sendCheckCode(getTextViewValue(mPhoneEt), GET_CODE_TAG);
                break;
            case R.id.binding_tv:
                if (!mPresenter.checkMobile(getTextViewValue(mPhoneEt))) {
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mCheckCodeEt))) {
                    checkFormatError("验证码不能为空");
                    return;
                }
                //校验密码
                String pwd = getTextViewValue(mRegistCheckPwdEt);
                if (!StringTools.isStringValueOk(pwd)) {
                    checkFormatError("登录密码不能为空");
                    return;
                } else {
                    if (!PubUtil.checkPwdMark(pwd)) {
                        checkFormatError("密码仅支持最少6位(字母数字组合)");
                        return;
                    }
                }
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("account", MyApp.getAccount())
                        .addFormDataPart("id", String.valueOf(MyApp.getUid()))
                        .addFormDataPart("phoneNumber", getTextViewValue(mPhoneEt))
                        .addFormDataPart("password",  MD5.md5(String.format("%s#%s", getTextViewValue(mPhoneEt),
                                getTextViewValue(mRegistCheckPwdEt))))
                        .addFormDataPart("code",getTextViewValue(mCheckCodeEt))
                        .addFormDataPart("token", MyApp.getUserToken());
                mPresenter.bindPhoneNum(builder.build(), EntranceContract.BIND_PHONE);
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
            mSendCheckCodeTv.setText("获取验证码");
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
