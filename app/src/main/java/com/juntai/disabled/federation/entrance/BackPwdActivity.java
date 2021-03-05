package com.juntai.disabled.federation.entrance;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.entrance.regist.RegistContract;
import com.juntai.disabled.federation.entrance.regist.RegistPresent;
import com.juntai.disabled.federation.entrance.sendcode.SmsCheckCodeActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

import cn.smssdk.SMSSDK;

/**
 * @aouther tobato
 * @description 描述  找回密码/修改手机号
 * @date 2020/3/10 9:33
 */
public class BackPwdActivity extends SmsCheckCodeActivity<RegistPresent> implements RegistContract.IRegistView,
        View.OnClickListener {

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
    private LinearLayout mCheckPhoneLl;
    /**
     * 请设置6-20位新的登录密码
     */
    private EditText mRegistSetPwdEt;
    /**
     * 请再次输入新的登录密码
     */
    private EditText mRegistCheckPwdEt;
    private LinearLayout mSetNewPwdLl, mSetPwdLl;
    /**
     * 提交
     */
    private TextView mRegistTv;
    /**
     * 1.验证手机号码
     */
    private TextView mCheckPhoneTagTv;
    /**
     * 2.设置新密码
     */
    private TextView mSetPwdTagTv;
    private LinearLayout topTagLayout;

    private int pageType;//0找回密码，1修改手机号,2 修改密码,

    @Override
    protected RegistPresent createPresenter() {
        return new RegistPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_back_pwd;
    }

    @Override
    public void initView() {
        pageType = getIntent().getIntExtra("pageType", 0);
        topTagLayout = findViewById(R.id.top_tag_layout);
        mRegistPhoneEt = (EditText) findViewById(R.id.regist_phone_et);
        mRegistCheckCodeEt = (EditText) findViewById(R.id.regist_check_code_et);
        mRegistSendCheckCodeTv = (TextView) findViewById(R.id.regist_send_check_code_tv);
        mRegistSendCheckCodeTv.setOnClickListener(this);
        mCheckPhoneLl = (LinearLayout) findViewById(R.id.check_phone_ll);
        mRegistSetPwdEt = (EditText) findViewById(R.id.regist_set_pwd_et);
        mRegistCheckPwdEt = (EditText) findViewById(R.id.regist_check_pwd_et);
        mSetNewPwdLl = (LinearLayout) findViewById(R.id.set_new_pwd_ll);
        mSetPwdLl = (LinearLayout) findViewById(R.id.set_pwd_ll);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setOnClickListener(this);
        mCheckPhoneTagTv = (TextView) findViewById(R.id.check_phone_tag_tv);
        mSetPwdTagTv = (TextView) findViewById(R.id.set_pwd_tag_tv);
        //0找回密码，1修改手机号,2 修改密码,
        mRegistTv.setText("下一步");
        topTagLayout.setVisibility(View.VISIBLE);
        mRegistPhoneEt.setText(UserInfoManager.getPhoneNumber());

        switch (pageType) {
            case 0:
                setTitleName("找回密码");
                mRegistPhoneEt.setFocusable(false);
                mRegistPhoneEt.setClickable(false);
                mRegistPhoneEt.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
                break;
            case 1:
                mRegistPhoneEt.setText("");
                mRegistPhoneEt.setFocusable(true);
                mRegistPhoneEt.setClickable(true);
                mRegistPhoneEt.setTextColor(ContextCompat.getColor(mContext,R.color.black));
                mRegistTv.setText("确认");
                setTitleName("修改手机号");
                topTagLayout.setVisibility(View.GONE);
                mRegistPhoneEt.setHint("请输入新手机号码");
                mSetNewPwdLl.setVisibility(View.VISIBLE);
                mSetPwdLl.setVisibility(View.GONE);
                mRegistCheckPwdEt.setHint("请输入登录密码");
                break;
            case 2:
                setTitleName("修改密码");
                mRegistPhoneEt.setFocusable(false);
                mRegistPhoneEt.setClickable(false);
                mRegistPhoneEt.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
                break;
            default:
                break;
        }
    }


    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (getTextViewValue(mRegistTv).equals("下一步")) {
            verify = false;
            mCheckPhoneLl.setVisibility(View.GONE);
            mSetNewPwdLl.setVisibility(View.VISIBLE);
            mRegistTv.setText("提交");
            mSetPwdTagTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            mCheckPhoneTagTv.setTextColor(ContextCompat.getColor(this, R.color.gray_deeper));
        } else if (getTextViewValue(mRegistTv).equals("确认")) {
            mPresenter.updateAccount(RegistContract.SET_PHONE,
                    UserInfoManager.getPhoneNumber(), getTextViewValue(mRegistPhoneEt),
                    MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt),
                            getTextViewValue(mRegistCheckPwdEt))),
                    MD5.md5(String.format("%s#%s", UserInfoManager.getPhoneNumber(), getTextViewValue(mRegistCheckPwdEt))));
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {

        ToastUtils.success(mContext, "修改成功！");
        //        SPTools.saveString(mContext,"login","");
        Hawk.delete(AppUtils.SP_KEY_USER);
        //        MyApp.app.clearActivitys();//清理掉之前的所有activity
        finish();
        switch (tag) {
            case RegistContract.SET_PWD:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case RegistContract.SET_PHONE:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case RegistContract.MODIFY_PWD:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
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
            case R.id.regist_send_check_code_tv:
                mPresenter.sendCheckCode(getTextViewValue(mRegistPhoneEt), SMS_TEMP_CODE);
                break;
            case R.id.regist_tv:
                if (getTextViewValue(mRegistTv).equals("下一步")) {
                    //用于设置密码和修改密码
                    //校验手机号和验证码
                    if (!mPresenter.checkMobile(getTextViewValue(mRegistPhoneEt))) {
                        return;
                    }
                    if (!StringTools.isStringValueOk(getTextViewValue(mRegistCheckCodeEt))) {
                        checkFormatError("验证码不能为空");
                        return;
                    }
                    if (!verify) {
                        SMSSDK.submitVerificationCode("+86", getTextViewValue(mRegistPhoneEt),
                                getTextViewValue(mRegistCheckCodeEt));
                    } else {
                        mCheckPhoneLl.setVisibility(View.GONE);
                        mSetNewPwdLl.setVisibility(View.VISIBLE);
                        mRegistTv.setText("提交");
                        mSetPwdTagTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                        mCheckPhoneTagTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
                    }

                } else if (getTextViewValue(mRegistTv).equals("确认")) {
                    //用于修改手机号
                    if (!mPresenter.checkMobile(getTextViewValue(mRegistPhoneEt))) {
                        return;
                    }
                    if (!StringTools.isStringValueOk(getTextViewValue(mRegistCheckCodeEt))) {
                        checkFormatError("验证码不能为空");
                        return;
                    }
                    if (!StringTools.isStringValueOk(getTextViewValue(mRegistCheckPwdEt))) {
                        checkFormatError("登录密码不能为空");
                        return;
                    }
                    if (!verify) {
                        SMSSDK.submitVerificationCode("+86", getTextViewValue(mRegistPhoneEt),
                                getTextViewValue(mRegistCheckCodeEt));
                    } else {
                        mPresenter.updateAccount(RegistContract.SET_PHONE, UserInfoManager.getPhoneNumber(),
                                getTextViewValue(mRegistPhoneEt),
                                MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt),
                                        getTextViewValue(mRegistCheckPwdEt))),
                                MD5.md5(String.format("%s#%s", UserInfoManager.getPhoneNumber(),
                                        getTextViewValue(mRegistCheckPwdEt))));
                    }


                } else if (getTextViewValue(mRegistTv).equals("提交")) {
                    //用于设置密码和修改密码
                    //校验密码
                    String pwd = getTextViewValue(mRegistSetPwdEt);
                    if (!StringTools.isStringValueOk(pwd)) {
                        checkFormatError("登录密码不能为空");
                        return;
                    } else {
                        if (!PubUtil.checkPwdMark(pwd)) {
                            checkFormatError("密码仅支持最少6位(字母数字组合)");
                            return;
                        } else {
                            //查看确认密码
                            if (!pwd.equals(getTextViewValue(mRegistCheckPwdEt))) {
                                checkFormatError("两次输入的密码不一致");
                                return;
                            }
                        }
                    }
                    //找回密码
                    mPresenter.retrievePwd(RegistContract.SET_PWD, getTextViewValue(mRegistPhoneEt),
                            MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt),
                                    getTextViewValue(mRegistSetPwdEt))));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (pageType == 1) {
            super.onBackPressed();
        } else {
            if (getTextViewValue(mRegistTv).equals("下一步")) {
                super.onBackPressed();
            } else {
                mCheckPhoneLl.setVisibility(View.VISIBLE);
                mSetNewPwdLl.setVisibility(View.GONE);
                mRegistTv.setText("下一步");
                mCheckPhoneTagTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                mSetPwdTagTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
            }
        }
    }

    @Override
    public void updateSendCheckCodeViewStatus(long second) {
        if (second > 0) {
            mRegistSendCheckCodeTv.setText("重新发送 " + second + "s");
            mRegistSendCheckCodeTv.setClickable(false);
            mRegistSendCheckCodeTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
        } else {
            mRegistSendCheckCodeTv.setText("获取验证码");
            mRegistSendCheckCodeTv.setClickable(true);
            mRegistSendCheckCodeTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        }
    }

    @Override
    public void checkFormatError(String error) {
        ToastUtils.warning(mContext, error);
    }

    //    private void showWarnDialog(){
    //        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    //        builder.setMessage("修改手机号成功后需要重新登录，现在修改吗？");
    //        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialog, int which) {
    //                mPresenter.updateAccount(RegistContract.SET_PHONE, getTextViewValue(mRegistPhoneEt),
    //                        MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt), getTextViewValue
    //                        (mRegistCheckPwdEt))),
    //                        MD5.md5(String.format("%s#%s", MyApp.getAccount(), getTextViewValue(mRegistCheckPwdEt))));
    //            }
    //        });
    //        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialog, int which) {
    //            }
    //        });
    //        builder.show();
    //    }

}
