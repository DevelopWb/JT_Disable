package com.juntai.disabled.federation.mine.myinfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.BaseAppUtils;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.GlideEngine4;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.mine.MyCenterContract;
import com.juntai.disabled.federation.utils.RxScheduler;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/10
 * email:954101549@qq.com
 */
public class MyInfoPresent extends BasePresenter<IModel, MyCenterContract.IMyInfoView> implements MyCenterContract.IMyInfoPresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    @SuppressLint("CheckResult")
    @Override
    public void imageChoose() {
        new RxPermissions((MyInformationActivity)getView())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from((MyInformationActivity)getView())
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    .countable(true)
                                    .maxSelectable(1)                     // ??????????????????
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7.0?????? ????????????
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(MyInformationActivity.REQUEST_CODE_CHOOSE);
                        } else {
                            Toasty.info((MyInformationActivity) getView(), "?????????????????????").show();
                        }
                    }
                });
    }

    @Override
    public void postHead(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("token", MyApp.getUserToken())
                .addFormDataPart("id", MyApp.getUid()+"")
                .addFormDataPart("file", "file",
                        RequestBody.create(MediaType.parse("file"),
                                file));
        RequestBody requestBody = builder.build();
        AppNetModule.createrRetrofit()
                .modifyHeadPortrait(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        if (getView() != null){
                            getView().onSuccess(MyCenterContract.UPDATE_HEAD_TAG,baseResult);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(MyCenterContract.UPDATE_HEAD_TAG,msg);
                        }
                    }
                });
    }

    @Override
    public void yasuo(String path) {
        Luban.with((MyInformationActivity)getView())
                .load(path)
                .ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  ???????????????????????????????????????????????? loading UI
//                        getView().showLoading();
                    }

                    @Override
                    public void onSuccess(File file) {
                        //  ??????????????????????????????????????????????????????
                        getView().onSuccess(MyCenterContract.YASUO_HEAD_TAG,file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  ????????????????????????????????????
                        getView().onError("yasuo","??????????????????");
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
//                        getView().hideLoading();
                    }
                }).launch();
    }

    @Override
    public void getUserData(String tag) {
        AppNetModule.createrRetrofit()
                .getUserData(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), 1)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(getView()) {
                    @Override
                    public void onSuccess(UserBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
}
