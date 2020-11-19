package com.juntai.wisdom.dgjxb.home_page.news.news_comment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseEditText;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.utils.Reflex;
import com.juntai.wisdom.dgjxb.utils.StringTools;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * 文章评论
 * Created by Ma
 * on 2019/7/24
 */
public class EditCommentDialog extends DialogFragment {
    BaseEditText editText;
    boolean isCommentIng = false;
    RefreshListener refreshListener;
//    View view;
    BaseMvpActivity baseActivity;

    int typeId;  //评论类型id 8资讯
    int commentedId;// 被评论的内容id（资讯id）
    int fId;//评论子id（回复评论时传主评论的id，默认不传）
    int commentedUserId;//被评论人id
    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_comment,container,false);
        editText = view.findViewById(R.id.edittext_comment);
        view.findViewById(R.id.send_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editComment();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSoftInputListener();
    }

    /**
     * 点击非输入框区域时，自动收起键盘
     */
    private void initSoftInputListener() {
        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        InputMethodManager manager = (InputMethodManager)getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (getDialog().getCurrentFocus() != null
                                    && getDialog().getCurrentFocus().getWindowToken() != null) {
                                manager.hideSoftInputFromWindow(
                                        getDialog().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onDestroy() {
        refreshListener = null;
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        //显示软键盘
                        InputMethodManager inputManager =
                               (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(editText, 0);
                    }
                },100);
            }
        });
    }

    /**
     * 发布评论
     */
    public void editComment(){
        //正在提交或者为空
        if (isCommentIng || !StringTools.isStringValueOk(baseActivity.getTextViewValue(editText))) {
            return;
        }
        isCommentIng = true;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken())
                .addFormDataPart("typeId", String.valueOf(typeId))
                .addFormDataPart("commentedId", String.valueOf(commentedId))
                .addFormDataPart("content", baseActivity.getTextViewValue(editText));
        if (fId > 0 && commentedUserId >0){
            builder.addFormDataPart("fId", String.valueOf(fId))
                    .addFormDataPart("commentedUserId", String.valueOf(commentedUserId));
        }

        Observable<BaseResult> observable = null;
        if (typeId == 0){//监控
            observable = AppNetModule.createrRetrofit()
                    .addCommentCamera(builder.build());
        }else {//资讯
            observable = AppNetModule.createrRetrofit()
                    .addCommentNews(builder.build());
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(baseActivity) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        isCommentIng = false;
                        editText.setText("");
                        ToastUtils.success(baseActivity, "评论成功");
                        if(refreshListener != null){
                            refreshListener.refresh();
                        }
                        dismiss();
                    }
                    @Override
                    public void onError(String msg) {
                        baseActivity.onError("",msg);
                        isCommentIng = false;
                    }
                });
    }


    /**
     * 显示评论弹窗
     * @param manager
     * @param tag
     * @param typeId 评论类型id 8资讯 0监控
     * @param commentedId 被评论的内容id（资讯id）
     * @param fId 评论子id（回复评论时传主评论的id，默认不传）
     * @param commentedUserId  被评论人id
     * @param listener  回复成功回调
     * @param baseActivity
     */
    public void show(FragmentManager manager, String tag, int typeId, int commentedId, int fId, int commentedUserId, RefreshListener listener, BaseMvpActivity baseActivity) {
        show(manager,tag);
        this.typeId = typeId;
        this.commentedId = commentedId;
        this.fId = fId;
        this.commentedUserId = commentedUserId;
        refreshListener = listener;
        WeakReference<BaseMvpActivity> weakReference = new WeakReference<>(baseActivity);
        this.baseActivity = weakReference.get();
    }

    public void show(FragmentTransaction transaction, String tag, int typeId, int commentedId, int fId, int commentedUserId, RefreshListener listener) {
        show(transaction,tag);
        this.typeId = typeId;
        this.commentedId = commentedId;
        this.fId = fId;
        this.commentedUserId = commentedUserId;
        refreshListener = listener;
    }

    public interface RefreshListener{
        void refresh();
    }

    @Override
    public void onDestroyView() {
        Handler mListenersHandler = (Handler)Reflex.getFieldObject(Dialog.class,getDialog(),"mListenersHandler");
        if (mListenersHandler != null){
            mListenersHandler.removeCallbacksAndMessages(null);
        }
        refreshListener = null;
        baseActivity = null;
        super.onDestroyView();
    }
}
