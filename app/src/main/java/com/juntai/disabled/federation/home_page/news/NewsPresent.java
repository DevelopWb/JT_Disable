package com.juntai.disabled.federation.home_page.news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseFragment;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.BaseAppUtils;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.GlideEngine4;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.BaseDataBean;
import com.juntai.disabled.federation.bean.CommentListBean;
import com.juntai.disabled.federation.bean.news.NewsDetailBean;
import com.juntai.disabled.federation.bean.news.NewsFansListBean;
import com.juntai.disabled.federation.bean.news.NewsListBean;
import com.juntai.disabled.federation.bean.news.NewsPersonalHomePageInfo;
import com.juntai.disabled.federation.bean.news.NewsUploadPhotoBean;
import com.juntai.disabled.federation.bean.user_equipment.ReportTypeBean;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.video.record.VideoPreviewActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Describe:??????present
 * Create by zhangzhenlong
 * 2020-7-29
 * email:954101549@qq.com
 */
public class NewsPresent extends BasePresenter<IModel, NewsContract.INewsView> implements NewsContract.INewsPresent {
    private int compressedSize = 0;//????????????????????????
    List<String> icons = new ArrayList<>();
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getNewsList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getNewsList(pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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

    @Override
    public void publishNews(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .publishNews(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message == null ? "" : o.message);
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

    @Override
    public void updateNews(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .updateNews(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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

    @Override
    public void uploadNewsPhoto(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .uploadNewsPhoto(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsUploadPhotoBean>(getView()) {
                    @Override
                    public void onSuccess(NewsUploadPhotoBean o) {
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

    @Override
    public void searchNewsList(String keyWord, int pageNum, int pageSize, String tag) {
        AppNetModule
                .createrRetrofit()
                .searchNewsList(keyWord, pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(getView()) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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

    @Override
    public void getNewsInfo(int newsId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getNewsInfo(MyApp.getUid(), newsId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsDetailBean>(getView()) {
                    @Override
                    public void onSuccess(NewsDetailBean o) {
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

    @Override
    public void getAuthorInfo(int authorId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getNewsAuthorInfo(authorId, MyApp.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsPersonalHomePageInfo>(null) {
                    @Override
                    public void onSuccess(NewsPersonalHomePageInfo o) {
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

    @Override
    public void getNewsListByAuthorId(int authorId, int typeId, int pageNum, int pageSize, String tag,
                                      boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getNewsListByAuthorId(authorId, typeId, pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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

    @Override
    public void getFansList(int followId, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getFansList(MyApp.getAccount(), MyApp.getUserToken(), followId, MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsFansListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsFansListBean o) {
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

    @Override
    public void getFollowList(int fansId, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getFollowList(MyApp.getAccount(), MyApp.getUserToken(), fansId, MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsFansListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsFansListBean o) {
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

    @Override
    public void addFollowOrDelete(int typeId, int followId, String tag) {
        AppNetModule
                .createrRetrofit()
                .addFollowOrDelete(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), typeId, followId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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

    @Override
    public void getCommentList(int commentedId, int pageSize, int currentPage, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;

        AppNetModule.createrRetrofit()
                .getAllCommentNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), commentedId, pageSize,
                        currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(getView()) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
    }

    @Override
    public void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getChildCommentNews(MyApp.getAccount(), MyApp.getUserToken(), commentedId, unreadId, pageSize,
                        currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(viewCallBack) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
    }

    @Override
    public void like(int id, int userId, int isType, int typeId, int likeId, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrCancleLikeNews(id, MyApp.getAccount(), MyApp.getUserToken(), userId, isType, typeId, likeId)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1) {//??????
                            ToastUtils.success(MyApp.app, "????????????");
                        } else {
                            ToastUtils.success(MyApp.app, "????????????");
                        }
                        LogUtil.e("??????????????????");
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (isType == 1) {//??????
                            ToastUtils.success(MyApp.app, "??????????????????");
                        } else {
                            ToastUtils.success(MyApp.app, "????????????");
                        }
                        LogUtil.e("??????????????????");
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
    }

    /**
     * ??????builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }

    @SuppressLint("CheckResult")
    public void imageChoose(BaseFragment context) {
        //        SoftReference<Activity> softReference = new SoftReference<>(baseActivity);
        new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(context)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    .countable(true)
                                    .maxSelectable(9)   // ??????????????????
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7
                                    // .0?????? ????????????
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(NewsContract.REQUEST_CODE_CHOOSE);
                        } else {
                            ToastUtils.info(context.getContext(), "?????????????????????");
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void imageChoose(BaseActivity context, int picCount) {
        //        SoftReference<Activity> softReference = new SoftReference<>(baseActivity);
        new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(context)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    .countable(true)
                                    .maxSelectable(picCount)   // ??????????????????
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7
                                    // .0?????? ????????????
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(NewsContract.REQUEST_CODE_CHOOSE);
                        } else {
                            ToastUtils.info(context, "?????????????????????");
                        }
                    }
                });
    }

    /**
     * ????????????
     */
    public void imageCompress(List<String> paths, BaseMvpFragment iView) {
        compressedSize = 0;
        icons.clear();
        iView.showLoading();
        Luban.with(iView.getContext()).load(paths).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  ???????????????????????????????????????????????? loading UI
            }

            @Override
            public void onSuccess(File file) {
                compressedSize++;
                //  ??????????????????????????????????????????????????????
                icons.add(file.getPath());
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                    iView.onSuccess(NewsContract.YASUO_PHOTO_TAG, icons);
                }
            }

            @Override
            public void onError(Throwable e) {
                //  ????????????????????????????????????
                compressedSize++;
                LogUtil.e("push-??????????????????");
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                }
            }
        }).launch();
    }

    /**
     * ????????????
     */
    public void imageCompress(List<String> paths, BaseMvpActivity iView) {
        compressedSize = 0;
        icons.clear();
        iView.showLoading();
        Luban.with(iView).load(paths).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  ???????????????????????????????????????????????? loading UI
            }

            @Override
            public void onSuccess(File file) {
                compressedSize++;
                //  ??????????????????????????????????????????????????????
                icons.add(file.getPath());
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                    iView.onSuccess(NewsContract.YASUO_PHOTO_TAG, icons);
                }
            }

            @Override
            public void onError(Throwable e) {
                //  ????????????????????????????????????
                compressedSize++;
                LogUtil.e("push-??????????????????");
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                }
            }
        }).launch();
    }

    /**
     * ????????????
     *
     * @param fragment
     */
    @SuppressLint("CheckResult")
    public void recordVideo(BaseFragment fragment) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(fragment)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // ??????
                            MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                                    .fullScreen(false)
                                    .smallVideoWidth(500)
                                    .smallVideoHeight(480)
                                    .recordTimeMax(10000)
                                    .recordTimeMin(2000)
                                    .videoBitrate(960 * 640)
                                    .captureThumbnailsTime(1)
                                    .build();
                            MediaRecorderActivity.goSmallVideoRecorder(fragment.getActivity(),
                                    VideoPreviewActivity.class.getName()
                                    , config);
                        } else {
                            Toasty.info(fragment.getContext(), "?????????????????????").show();
                        }
                    }
                });
    }

    //????????????
    @SuppressLint("CheckResult")
    public void videoChoose(BaseFragment fragment) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(fragment)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(fragment)
                                    .choose(MimeType.ofVideo())
                                    .showSingleMediaType(true)//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    .countable(true)
                                    .maxSelectable(1)                     // ??????????????????
                                    .capture(false)//??????????????????
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7
                                    // .0?????? ????????????
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(PublishContract.SELECT_VIDEO_RESULT);
                        } else {
                            Toasty.info(fragment.getContext(), "?????????????????????").show();
                        }
                    }
                });
    }

    public void getReportTypes(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .getReportTypes(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ReportTypeBean>(getView()) {
                    @Override
                    public void onSuccess(ReportTypeBean o) {
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

    public void report(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .report(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
