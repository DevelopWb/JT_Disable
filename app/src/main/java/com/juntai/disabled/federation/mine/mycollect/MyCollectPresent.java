package com.juntai.disabled.federation.mine.mycollect;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.bean.BaseDataBean;
import com.juntai.disabled.federation.bean.CollectListBean;
import com.juntai.disabled.federation.mine.MyCenterContract;
import com.juntai.disabled.federation.utils.RxScheduler;

import java.util.List;

/**
 * Describe:收藏、分享
 * Create by zhangzhenlong
 * 2020-3-12
 * email:954101549@qq.com
 */
public class MyCollectPresent extends BasePresenter<IModel,MyCenterContract.ICollectView> implements MyCenterContract.ICollectPresent {
    private BaseIView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(BaseIView iView) {
        this.iView = iView;
    }

    @Override
    public void getCollectListCamera(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserCollectCamera(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getCollectListNews(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserCollectNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getShareListNews(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserShareNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteCollecListNews(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids, String tag) {
        BaseIView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        BaseIView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrDeleteCollectsNews(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType, typeId, collectId, ids)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏成功");
                        }
                        LogUtil.e("添加收藏成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏失败");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏失败");
                        }
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteCollecListCamera(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids, String tag) {
        BaseIView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        BaseIView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrDeleteCollectsCamera(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType, typeId, collectId, ids)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏成功");
                        }
                        LogUtil.e("添加收藏成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏失败");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏失败");
                        }
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteShareList(List<Integer> ids,String tag) {
        AppNetModule.createrRetrofit()
                .addOrDeleteShares(MyApp.getAccount(), MyApp.getUserToken(),null,1, null, null, ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }
}
