package com.juntai.disabled.federation.home_page.business.handlerBusiness.handlercard;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  残疾证办理
 * @date 2021/1/20 14:28
 */
public class HandlerCardActivity extends BaseBusinessActivity {

    private EditText mDisableNameEt;
    private EditText mGuardianNameEt;
    private ImageView mGuardianNameSignIv;
    private TextView mCommitmentTv;
    private TextView mCommitBusinessTv;


    @Override
    protected String getTitleName() {
        return "残疾证办理";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_disable_commiment, null);
        mDisableNameEt = view.findViewById(R.id.diable_name_et);
        mGuardianNameEt = view.findViewById(R.id.guardian__name_et);
        mGuardianNameSignIv = view.findViewById(R.id.guardian__name_sign_iv);
        mCommitmentTv = view.findViewById(R.id.commitment_tv);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        mGuardianNameSignIv.setOnClickListener(this);
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected void commit() {
        MultipartBody.Builder builder = getBuilderOfAdapterData();
        if (builder == null) {
            return;
        }
        if (TextUtils.isEmpty(getTextViewValue(mDisableNameEt))) {
            ToastUtils.toast(mContext, "请输入承诺书中残疾人姓名");
            return;
        }
        if (TextUtils.isEmpty(getTextViewValue(mGuardianNameEt))) {
            ToastUtils.toast(mContext, "请输入承诺书中残疾人监护人姓名");
            return;
        }
        String content = getTextViewValue(mCommitmentTv);
        if (content.contains("请选择残疾种类")) {
            ToastUtils.toast(mContext, "请选择残疾种类");
            return;
        }
        StringBuilder commitmentSb = new StringBuilder();
        commitmentSb.append("我是残疾人");
        commitmentSb.append(getTextViewValue(mDisableNameEt));
        commitmentSb.append(",残疾人监护人");
        commitmentSb.append(getTextViewValue(mGuardianNameEt) + ",");
        commitmentSb.append(getTextViewValue(mCommitmentTv));
        builder.addFormDataPart("commitment", commitmentSb.toString());
        mPresenter.addDisabilityCertificate(builder.build(), AppHttpPath.HANDLER_DISABLED_CARD);
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getHandlerIdCardAdapterData(null);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.guardian__name_sign_iv:
                showSignatureView();
                break;
            default:
                break;
        }
    }


    @Override
    public void initData() {
        //清除签名文件
        FileCacheUtils.clearImage(FileCacheUtils.getAppImagePath() + FileCacheUtils.SIGN_PIC_NAME);
        String content = getString(R.string.commitment);
        initCommitmentText(content);
    }

    private void initCommitmentText(String content) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString spannableString = new SpannableString(content);
        //点击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                CharSequence[] disabledTypes = getDisabledTypes();
                List<CharSequence> types = new ArrayList<>();
                new AlertDialog.Builder(mContext)
                        .setTitle("请选择残疾种类")
                        .setMultiChoiceItems(disabledTypes, new boolean[]{false, false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                        if (isChecked) {
                                            types.add(disabledTypes[which]);
                                        } else {
                                            types.remove(disabledTypes[which]);
                                        }
                                    }
                                }
                        ).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedStr = types.toString();
                        if (selectedStr != null) {
                            if (selectedStr.contains("[")) {
                                selectedStr = selectedStr.substring(1, selectedStr.length() - 1);
                            }
                        }
                        String str = content.replace(content.substring(content.indexOf("(") + 1,
                                content.indexOf(")")),
                                selectedStr);
                        initCommitmentText(str);
                    }
                }).show();
            }
        }, content.indexOf("(") + 1, content.indexOf(")"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //下划线
        spannableString.setSpan(new UnderlineSpan(), content.indexOf("(") + 1, content.indexOf(")"),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字颜色
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorAccent)),
                content.indexOf("(") + 1,
                content.indexOf(")"),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        mCommitmentTv.setText(builder);
        // 添加这一行之后，指定区域文字点击事件才会生效
        mCommitmentTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 获取残疾的种类
     *
     * @return
     */
    private CharSequence[] getDisabledTypes() {
        return new CharSequence[]{"视力", "听力", "言语", "肢体", "智力", "精神"};
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.HANDLER_DISABLED_CARD:
                ToastUtils.toast(mContext,"办理成功");
                finish();
                break;
            default:
                break;
        }

    }

}
