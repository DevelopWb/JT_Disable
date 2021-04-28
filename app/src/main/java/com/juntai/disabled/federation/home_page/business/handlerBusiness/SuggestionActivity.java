package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.entrance.regist.RegistContract;
import com.juntai.disabled.federation.entrance.sendcode.SmsCheckCodeActivity;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;

import java.io.File;
import java.util.List;

import cn.smssdk.SMSSDK;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  意见建议
 * @date 2021/3/2 8:33
 */
public class SuggestionActivity extends SmsCheckCodeActivity implements RegistContract.IRegistView,
        View.OnClickListener {


    private EditText mEditNameValueEt;
    private EditText mIdcardValueEt;
    private EditText mPhoneValueEt;
    private EditText mCodeValueEt;
    /**
     * 获取验证码
     */
    private TextView mSendCheckCodeTv;
    private RadioGroup mItemRadioG;
    /**
     *
     */
    private TextView mImportantTagTv;
    /**
     * 详细内容
     */
    private TextView mItemBusinessSmallTitleTv;
    private EditText mContentValueEt;
    /**
     * 提交
     */
    private TextView mCommitTv;

    @Override
    public int getLayoutView() {
        return R.layout.suggestion_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        super.initView();
        setTitleName("意见建议");
        mEditNameValueEt = (EditText) findViewById(R.id.edit_name_value_et);
        mIdcardValueEt = (EditText) findViewById(R.id.idcard_value_et);
        mPhoneValueEt = (EditText) findViewById(R.id.phone_value_et);
        mCodeValueEt = (EditText) findViewById(R.id.code_value_et);
        mSendCheckCodeTv = (TextView) findViewById(R.id.send_check_code_tv);
        mSendCheckCodeTv.setOnClickListener(this);
        mItemRadioG = (RadioGroup) findViewById(R.id.item_radio_g);
        mImportantTagTv = (TextView) findViewById(R.id.important_tag_tv);
        mItemBusinessSmallTitleTv = (TextView) findViewById(R.id.item_business_small_title_tv);
        mContentValueEt = (EditText) findViewById(R.id.content_value_et);
        mCommitTv = (TextView) findViewById(R.id.commit_tv);
        mCommitTv.setOnClickListener(this);
        UserBean userBean = UserInfoManager.getUser();
        mEditNameValueEt.setText(userBean.getData().getRealName());
        mIdcardValueEt.setText(userBean.getData().getIdNumber());
        mPhoneValueEt.setText(userBean.getData().getPhoneNumber());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void recordVedio() {
        mPresenter.recordVideo(this);
    }

    @Override
    protected SelectPhotosFragment getFragment() {
        return SelectPhotosFragment.newInstance().setPhotoTitle("")
                .setPhotoSpace(60)
                .setMaxCount(3);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag,o);
        ToastUtils.toast(mContext, ((BaseResult)o).message);
        finish();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            default:
                break;
            case R.id.send_check_code_tv:
                mPresenter.sendCheckCode(getTextViewValue(mPhoneValueEt), GET_CODE_TAG);
                break;
            case R.id.commit_tv:
                if (!StringTools.isStringValueOk(getTextViewValue(mCodeValueEt))) {
                    ToastUtils.warning(mContext, "请输入验证码");
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mContentValueEt))) {
                    ToastUtils.warning(mContext, "请输入详细内容");
                    return;
                }
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (pics.size() < 1) {
                    ToastUtils.warning(mContext, "请选择图片");
                    return;
                }
                if (!StringTools.isStringValueOk(videoPath)) {
                    ToastUtils.warning(mContext, "请选择视频");
                    return;
                }
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("account", MyApp.getAccount())
                        .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                        .addFormDataPart("token", MyApp.getUserToken())
                        .addFormDataPart("phoneNumber", UserInfoManager.getPhoneNumber())
                        .addFormDataPart("code",getTextViewValue(mCodeValueEt));
                //（0意见；1建议）
                builder.addFormDataPart("typeId", R.id.radio_zero_rb == mItemRadioG.getCheckedRadioButtonId() ? "0" : "1");
                builder.addFormDataPart("content", getTextViewValue(mContentValueEt));

                if (pics.size() > 0) {
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        builder.addFormDataPart("pictureFile", "pictureFile", RequestBody.create(MediaType.parse("file"), new
                                File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {
                    builder.addFormDataPart("videoFile", "videoFile", RequestBody.create(MediaType.parse("file"),
                            new File(videoPath)));
                }
                mPresenter.addOpinionsAndSuggestions(builder.build(), "");


                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, (String) o);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

}
