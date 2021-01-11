package com.juntai.tyb.mine;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.login.LoginActivity;
import com.juntai.tyb.uitils.HawkProperty;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

/**
 * @aouther tobato
 * @description 描述  修改密码
 * @date 2020/7/26 11:07
 */
public class ModifyPwdActivity extends BaseAppActivity<MinePresent> implements MineContract.IMineView,
        View.OnClickListener {

    /**
     * 请输入原密码
     */
    private EditText mOlderPwdEt;
    /**
     * 请输入新密码
     */
    private EditText mNewestPwdEt;
    /**
     * 再输入新密码
     */
    private EditText mNewestPwdAgainEt;
    /**
     * 提交
     */
    private TextView mCommitModifyPwdTv;

    @Override
    protected MinePresent createPresenter() {
        return new MinePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void initView() {
        setTitleName("修改密码");
        mOlderPwdEt = (EditText) findViewById(R.id.older_pwd_et);
        mNewestPwdEt = (EditText) findViewById(R.id.newest_pwd_et);
        mNewestPwdAgainEt = (EditText) findViewById(R.id.newest_pwd_again_et);
        mCommitModifyPwdTv = (TextView) findViewById(R.id.commit_modify_pwd_tv);
        mCommitModifyPwdTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onSuccess(String tag, Object o) {

        ToastUtils.success(mContext,"修改成功");
        //跳转到登录页面重新登录
        Hawk.delete(HawkProperty.USER_INFO);
        ActivityManagerTool.getInstance().finishApp();
        startActivity(new Intent(mContext, LoginActivity.class));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commit_modify_pwd_tv) {
            String olderPwd = getTextViewValue(mOlderPwdEt);
            String newPwd = getTextViewValue(mNewestPwdEt);
            String newPwdAgain = getTextViewValue(mNewestPwdAgainEt);
            if (!StringTools.isStringValueOk(olderPwd)) {
                ToastUtils.toast(mContext, "请输入原密码");
                return;
            } else {
                if (!encryptPwd(UserInfoManager.getUserAccount(), olderPwd).equals(UserInfoManager.getUserPwd())) {
                    //原密码相同
                    ToastUtils.toast(mContext, "原密码输入有误");
                    return;
                }
            }
            if (!StringTools.isStringValueOk(newPwd)) {
                ToastUtils.toast(mContext, "请输入新密码");
                return;
            }
            if (!StringTools.isStringValueOk(newPwdAgain)) {
                ToastUtils.toast(mContext, "请再次输入新密码");
                return;
            } else {
                //比较两次的新密码是否相同
                if (!newPwd.equals(newPwdAgain)) {
                    ToastUtils.toast(mContext, "新密码两次输入不一致");
                    return;
                }
            }

            mPresenter.modifyPwd(getBaseBuilder().add("password", encryptPwd(UserInfoManager.getUserAccount(),
                    newPwd)).build(), "");
        }
    }
}
