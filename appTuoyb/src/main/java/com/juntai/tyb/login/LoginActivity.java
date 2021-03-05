package com.juntai.tyb.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.tyb.MainActivity;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.bean.LoginBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.uitils.HawkProperty;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

/**
 * @aouther tobato
 * @description 描述  登录
 * @date 2020/7/9 11:57
 */
public class LoginActivity extends BaseAppActivity<LoginPresent> implements View.OnClickListener,LoginContract.ILoginView {

    private EditText mLonginAccountEt;
    private EditText mLonginPwdEt;
    /**
     * 登录
     */
    private Button mLoginBt;

    @Override
    public int getLayoutView() {
        return R.layout.activity_login1;
    }

    @Override
    public void initView() {
        mImmersionBar.reset().statusBarColor(R.color.longin_bg_color).init();
        getToolbar().setVisibility(View.GONE);
        mLonginAccountEt = (EditText) findViewById(R.id.longin_account_et);
        mLonginPwdEt = (EditText) findViewById(R.id.longin_pwd_et);
        mLonginPwdEt.setText(UserInfoManager.getUserPhone());
        mLoginBt = (Button) findViewById(R.id.login_bt);
        mLoginBt.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onBackPressed() {
        ActivityManagerTool.getInstance().finishApp();
        super.onBackPressed();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_bt) {
            String account = getTextViewValue(mLonginAccountEt);
            String pwd = getTextViewValue(mLonginPwdEt);

            if (!StringTools.isStringValueOk(account)) {
                ToastUtils.toast(mContext, "请输入用户名");
                return;
            }
            if (!StringTools.isStringValueOk(pwd)) {
                ToastUtils.toast(mContext, "请输入密码");
                return;
            }

            mPresenter.login(account, encryptPwd(account, pwd), null);
        }
    }

    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            LoginBean.UserBean userBean = loginBean.getData();
            Hawk.put(HawkProperty.USER_INFO,userBean);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}

