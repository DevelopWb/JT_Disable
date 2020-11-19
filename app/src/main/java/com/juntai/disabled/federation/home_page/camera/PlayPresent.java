package com.juntai.disabled.federation.home_page.camera;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.OpenLiveBean;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.base.BaseAppPresent;
import com.juntai.disabled.federation.bean.BaseDataBean;
import com.juntai.disabled.federation.bean.CommentListBean;
import com.juntai.disabled.federation.bean.stream.StreamCameraDetailBean;
import com.juntai.disabled.federation.utils.RxScheduler;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:49
 */
public class PlayPresent extends BaseAppPresent<IModel, PlayContract.IPlayView> implements PlayContract.IPlayPresent {
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(IView iView) {
        this.iView = iView;
    }


    @Override
    public void openStream(String channelid, String type, String videourltype,String tag) {
         AppNetModule.createrRetrofit()
                .openStream(channelid,type,videourltype)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OpenLiveBean>(null) {
                    @Override
                    public void onSuccess(OpenLiveBean o) {
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
    public void capturePic(String channelid, String type, String tag) {
        AppNetModule.createrRetrofit()
                .capturePic(channelid,type)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OpenLiveBean>(PlayContract.GET_STREAM_CAMERA_THUMBNAIL.equals(tag)?null:getView()) {
                    @Override
                    public void onSuccess(OpenLiveBean o) {
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
    public void getStreamCameraDetail(RequestBody requestBody,String tag) {
        AppNetModule.createrRetrofit()
                .getStreamCameraDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraDetailBean>(null) {
                    @Override
                    public void onSuccess(StreamCameraDetailBean o) {
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
    public void uploadStreamCameraCapturePic(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .uploadStreamCameraCapturePic(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
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
    public void uploadStreamCameraThumbPic(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .uploadStreamCameraThumbPic(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message);
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
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;

        AppNetModule.createrRetrofit()
                .getAllCommentCamera(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), commentedId, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(getView()) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getChildCommentCamera(MyApp.getAccount(), MyApp.getUserToken(), commentedId, unreadId, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(viewCallBack) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void like(int id, int userId, int isType, int typeId, int likeId, String tag) {
        IView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrCancleLikeCamera(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType,typeId,likeId)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消点赞");
                        }else {
                            ToastUtils.success(MyApp.app,"点赞成功");
                        }
                        LogUtil.e("更新点赞成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消点赞失败");
                        }else {
                            ToastUtils.success(MyApp.app,"点赞失败");
                        }
                        LogUtil.e("更新点赞失败");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

//    /**
//     * 开始计时
//     * @param tag
//     */
//    public void timeBegin(String tag) {
//        if (disposable == null) {
//            disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
//                    .take(6)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Long>() {
//                        @Override
//                        public void accept(Long aLong) throws Exception {
//                            ToastUtils.toast(CarLiveActivity.this,"计时"+aLong);
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            throwable.printStackTrace();
//                        }
//                    }, new Action() {
//                        @Override
//                        public void run() throws Exception {
//                            ToastUtils.toast(CarLiveActivity.this,"计时结束");
//                        }
//                    });
//        }
//
//    }
//
//    /**
//     * 结束计时
//     */
//    public void timeStop() {
//        if (disposable != null) {
//            disposable.dispose();
//        }
//    }

}
