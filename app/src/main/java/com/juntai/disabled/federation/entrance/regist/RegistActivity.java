package com.juntai.disabled.federation.entrance.regist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.juntai.disabled.basecomponent.base.OnMultiClickListener;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.utils.BaiDuLocationUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.entrance.LoginActivity;
import com.juntai.disabled.federation.entrance.sendcode.SmsCheckCodeActivity;
import com.juntai.disabled.federation.mine.UserAgreementActivity;
import com.juntai.disabled.federation.utils.StringTools;

import cn.smssdk.SMSSDK;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  注册
 * @date 2020/3/8 14:27
 */
public class RegistActivity extends SmsCheckCodeActivity<RegistPresent> implements RegistContract.IRegistView,
        View.OnClickListener {
    private BaiDuLocationUtils baiDuLocationUtils = null;

    /**
     * 请输入注册手机号码
     */
    private EditText mRegistPhoneEt;
    /**
     * 请输入短信验证码
     */
    private EditText mRegistCheckCodeEt;
    /**
     * 发送验证码
     */
    private TextView mRegistSendCheckCodeTv;
    /**
     * 请设置6-20位登录密码
     */
    private EditText mRegistSetPwdEt;
    /**
     * 请再次确认登录密码
     */
    private EditText mRegistCheckPwdEt;
    /**
     * 请输入昵称
     */
    private EditText mRegistNameEt;

    private RadioButton mRegistAgreeProtocalRb;
    /**
     * 注册
     */
    private TextView mRegistTv;
    /**
     * 我已阅读并同意《隐私协议》
     */
    private TextView mRegistProtocalSecrecyTv;
    /**
     * 和《用户协议》
     */
    private TextView mRegistProtocaUserTv;
    //是否同意协议
    private boolean isAgreeProtocal = true;
    public Double lat = 0.0;
    public Double lng = 0.0;
    private RequestBody requestBody;
    private ImageView mCloseIv;
    /**
     * 登录
     */
    private TextView mLoginTv;

    public static  String REGIST_PHONE = "regist_phone";

    @Override
    protected RegistPresent createPresenter() {
        return new RegistPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (baiDuLocationUtils == null) {
            baiDuLocationUtils = new BaiDuLocationUtils(getApplicationContext());
        }
        baiDuLocationUtils.start(listener);
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() != 161 && bdLocation.getLocType() != 61) {
                return;
            }
            lat = bdLocation.getLatitude();
            lng = bdLocation.getLongitude();
            baiDuLocationUtils.stop(listener);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (baiDuLocationUtils != null) {
            baiDuLocationUtils.stop(listener);
            baiDuLocationUtils = null;
        }
    }



    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        mRegistNameEt = (EditText) findViewById(R.id.regist_name_et);
        mRegistPhoneEt = (EditText) findViewById(R.id.regist_phone_et);
        mRegistCheckCodeEt = (EditText) findViewById(R.id.regist_check_code_et);
        mRegistSendCheckCodeTv = (TextView) findViewById(R.id.regist_send_check_code_tv);
        mRegistSendCheckCodeTv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mPresenter.sendCheckCode(getTextViewValue(mRegistPhoneEt), SMS_TEMP_CODE);
            }
        });
        mRegistSetPwdEt = (EditText) findViewById(R.id.regist_set_pwd_et);
        mRegistCheckPwdEt = (EditText) findViewById(R.id.regist_check_pwd_et);
        mRegistAgreeProtocalRb = (RadioButton) findViewById(R.id.regist_agree_protocal_rb);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setOnClickListener(this);
        mRegistProtocalSecrecyTv = (TextView) findViewById(R.id.regist_protocal_secrecy_tv);
        mRegistProtocalSecrecyTv.setOnClickListener(this);
        mRegistProtocaUserTv = (TextView) findViewById(R.id.regist_protoca_user_tv);
        mRegistProtocaUserTv.setOnClickListener(this);
        mRegistAgreeProtocalRb.setOnClickListener(this);

        mRegistNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edit = mRegistNameEt.getText().toString();
                String str = StringTools.stringFilter(edit);
                if (!edit.equals(str)) {
                    mRegistNameEt.setText(str);
                    //设置新的光标所在位置
                    mRegistNameEt.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mCloseIv = (ImageView) findViewById(R.id.close_iv);
        mCloseIv.setOnClickListener(this);
        mLoginTv = (TextView) findViewById(R.id.login_tv);
        mLoginTv.setOnClickListener(this);
    }


    @Override
    public void initData() {
        String content = getString(R.string.protocal_notice1);
        StringTools.setTextPartColor(mRegistProtocalSecrecyTv, content, content.lastIndexOf("《"), content.length(),
                "#FB7D06");
        String content_user = getString(R.string.protocal_notice2);
        StringTools.setTextPartColor(mRegistProtocaUserTv, content_user, content_user.lastIndexOf("《"),
                content_user.length(), "#FB7D06");
    }

    @Override
    protected void initGetTestCodeButtonStatusStart() {
        mPresenter.initGetTestCodeButtonStatus();
    }

    @Override
    protected void initGetTestCodeButtonStatusStop() {
        mPresenter.receivedCheckCodeAndDispose();
        mRegistSendCheckCodeTv.setText("获取验证码");
        mRegistSendCheckCodeTv.setClickable(true);
        mRegistSendCheckCodeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    protected void checkCodeSuccessed() {
        mPresenter.regist(RegistContract.REGIST, requestBody);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case RegistContract.REGIST:
                ToastUtils.success(mContext, "注册成功");
                startActivity(new Intent(this, LoginActivity.class).putExtra(REGIST_PHONE,
                        getTextViewValue(mRegistPhoneEt)));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //隐私协议
            case R.id.regist_protocal_secrecy_tv:
                startActivity(new Intent(mContext, UserAgreementActivity.class).putExtra("url",
                        getString(R.string.secret_xieyi_url)));
                break;
            //注册
            case R.id.regist_tv:
                if (!StringTools.isStringValueOk(getTextViewValue(mRegistNameEt))) {
                    ToastUtils.warning(mContext, "请输入昵称");
                    return;
                }
                String account = getTextViewValue(mRegistPhoneEt);
                if (!mPresenter.checkMobile(account)) {
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mRegistCheckCodeEt))) {
                    ToastUtils.warning(mContext, "验证码不能为空");
                    return;
                }
                String pwd = getTextViewValue(mRegistSetPwdEt);
                if (!StringTools.isStringValueOk(pwd)) {
                    ToastUtils.warning(mContext, "登录密码不能为空");
                    return;
                } else {
                    if (!PubUtil.checkPwdMark(pwd)) {
                        ToastUtils.warning(mContext, "登录密码仅支持最少6位(字母数字下划线）");
                        return;
                    } else {
                        //查看确认密码
                        if (!pwd.equals(getTextViewValue(mRegistCheckPwdEt))) {
                            ToastUtils.warning(mContext, "两次输入的密码不一致");
                            return;
                        }
                    }
                }

                FormBody.Builder builder = new FormBody.Builder();
                builder.add("phoneNumber", account);
                builder.add("password", MD5.md5(String.format("%s#%s", account, getTextViewValue(mRegistCheckPwdEt))));
                builder.add("nickname", getTextViewValue(mRegistNameEt));
                builder.add("userSource", "10");//（1警小宝；2巡小管；3邻小帮   注册来源（10河东残联APP））
                requestBody = builder.build();
                if (!isAgreeProtocal) {
                    ToastUtils.toast(mContext, "请阅读并同意《隐私协议》和《用户协议》");
                    return;
                }
                if (!verify) {
                    SMSSDK.submitVerificationCode("+86", account, getTextViewValue(mRegistCheckCodeEt));
                } else {
                    mPresenter.regist(RegistContract.REGIST, requestBody);
                }
                break;
            //用户协议
            case R.id.regist_protoca_user_tv:
                startActivity(new Intent(mContext, UserAgreementActivity.class).putExtra("url",
                        getString(R.string.user_xieyi_url)));
                break;
            case R.id.regist_agree_protocal_rb:
                if (isAgreeProtocal) {
                    mRegistAgreeProtocalRb.setChecked(false);
                    isAgreeProtocal = false;
                } else {
                    mRegistAgreeProtocalRb.setChecked(true);
                    isAgreeProtocal = true;
                }
                break;
            case R.id.close_iv:
                finish();
                break;
            case R.id.login_tv:
                //已有账户 立即登录
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void updateSendCheckCodeViewStatus(long second) {
        if (second > 0) {
            mRegistSendCheckCodeTv.setText("重新发送 " + second + "s");
            mRegistSendCheckCodeTv.setClickable(false);
            mRegistSendCheckCodeTv.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else {
            mRegistSendCheckCodeTv.setText("获取验证码");
            mRegistSendCheckCodeTv.setClickable(true);
            mRegistSendCheckCodeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        }
    }

    @Override
    public void checkFormatError(String error) {
        ToastUtils.warning(mContext, error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onError(String tag, Object o) {
        switch (tag) {
            case RegistContract.REGIST:
                verify = false;
                break;
            default:
                break;
        }
        super.onError(tag, o);
    }

}
