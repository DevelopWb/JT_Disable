package com.juntai.disabled.federation.entrance.sendcode;

import android.support.annotation.Nullable;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.base.BaseSelectPicsAndVedioActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragmentNormal;
import com.juntai.disabled.federation.entrance.regist.RegistPresent;

import java.util.List;

/**
 * @aouther tobato 短信验证码接收
 * @description 描述
 * @date 2020/3/25 8:46
 */
public abstract class SmsCheckCodeActivity extends BaseSelectPicsAndVedioActivity<RegistPresent> {
    public final static String GET_CODE_TAG = "getCodeTag";//获取短信验证码的标识
//    /**
//     * 验证成功
//     */
//    protected abstract void checkCodeSuccessed();

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }
    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }
    @Override
    protected RegistPresent createPresenter() {
        return new RegistPresent();
    }
    @Override
    public boolean requestLocation() {
        return false;
    }
    @Override
    protected void recordVedio() {

    }

    @Override
    protected SelectPhotosFragmentNormal getFragment() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 更改获取验证码按钮得状态
     */
    protected abstract void initGetTestCodeButtonStatusStart();

    protected abstract void initGetTestCodeButtonStatusStop();

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case GET_CODE_TAG:
                ToastUtils.success(SmsCheckCodeActivity.this, "已发送");
                initGetTestCodeButtonStatusStart();
                break;
            default:
                initGetTestCodeButtonStatusStop();
                break;
        }

    }


    @Override
    public void onError(String tag, Object o) {
        String msg = (String) o;
        if ("短信验证码错误".equals(msg)) {
            ToastUtils.error(SmsCheckCodeActivity.this, "验证码输入有误");
            initGetTestCodeButtonStatusStop();
        }
        super.onError(tag, o);
    }
}
