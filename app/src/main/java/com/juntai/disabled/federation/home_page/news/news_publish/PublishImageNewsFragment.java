package com.juntai.disabled.federation.home_page.news.news_publish;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.EventManager;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.LocationSeltionActivity;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.news.NewsDraftsBean;
import com.juntai.disabled.federation.bean.news.NewsUploadPhotoBean;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.federation.home_page.news.NewsContract;
import com.juntai.disabled.federation.home_page.news.NewsPresent;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.DateUtil;
import com.juntai.disabled.federation.utils.StringTools;
import com.orhanobut.hawk.Hawk;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.qzb.richeditor.RE;
import cn.qzb.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description ??????????????????
 * @aouther ZhangZhenlong
 * @date 2020-7-30
 */
public class PublishImageNewsFragment extends BaseMvpFragment<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {
    /**
     * ????????????????????????????????????????????????
     */
    private EditText mTitleEt;
    /**
     * ?????????????????????
     */
    private TextView mAddressTv;//??????
    private ImageView mActionImg;//????????????
    private ImageView mActionBold;//??????
    private ImageView mActionItalic;//??????
    private ImageView mActionUnderline;//?????????
    private LinearLayout mBottom;//
    private RichEditor mEditor;//
    public RE re;
    private FrameLayout mWebContainer;

    //??????
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;

    private int iconSelectColor = Color.BLACK;
    private int iconDefaultColor = Color.parseColor("#CDCDCD");
    private String informationId;
    private ImageView mActionAlignLeft;
    private ImageView mActionAlignCenter;
    private ImageView mActionAlignRight;
    private ImageView mActionUndo;
    private ImageView mActionRedo;
    /**
     * ??????
     */
    private TextView mYulanBtn;
    /**
     * ??????
     */
    private TextView mFabuBtn;
    private LinearLayout mOperationLayout;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_publish_image_news;
    }

    @Override
    protected void initView() {
        mTitleEt = getView(R.id.title_et);
        mAddressTv = getView(R.id.address_tv);
        mAddressTv.setOnClickListener(this);
        mActionImg = getView(R.id.action_img);
        mActionImg.setOnClickListener(this);
        mActionBold = getView(R.id.action_bold);
        mActionBold.setOnClickListener(this);
        mActionItalic = getView(R.id.action_italic);
        mActionItalic.setOnClickListener(this);
        mActionUnderline = getView(R.id.action_underline);
        mActionUnderline.setOnClickListener(this);
        mActionAlignLeft = getView(R.id.action_align_left);
        mActionAlignLeft.setOnClickListener(this);
        mActionAlignCenter = getView(R.id.action_align_center);
        mActionAlignCenter.setOnClickListener(this);
        mActionAlignRight = getView(R.id.action_align_right);
        mActionAlignRight.setOnClickListener(this);
        mActionUndo = getView(R.id.action_undo);
        mActionUndo.setOnClickListener(this);
        mActionRedo = getView(R.id.action_redo);
        mActionRedo.setOnClickListener(this);
        mBottom = getView(R.id.bottom);
        mYulanBtn = getView(R.id.yulan_btn);
        mYulanBtn.setOnClickListener(this);
        mFabuBtn = getView(R.id.fabu_btn);
        mFabuBtn.setOnClickListener(this);
        mOperationLayout = getView(R.id.operation_layout);
        mOperationLayout.setVisibility(View.VISIBLE);

        mWebContainer = getView(R.id.web_container);
        mEditor = new RichEditor(mContext.getApplicationContext());
        mWebContainer.addView(mEditor);

        re = RE.getInstance(mEditor);
        re.setPlaceHolder(getString(R.string.news_publish_article_hint));
        re.setPadding(20, 20, 20, 20);

        //        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //            @Override
        //            public void onFocusChange(View v, boolean hasFocus) {
        //                if (hasFocus) {
        //                    mBottom.setVisibility(View.VISIBLE);
        //                } else {
        //                    mBottom.setVisibility(View.GONE);
        //                }
        //            }
        //        });
    }

    @Override
    protected void initData() {
        informationId = DateUtil.getCurrentTime("yyyyMMddHHmmss");
        changeIconColor(mActionBold, iconDefaultColor);
        changeIconColor(mActionItalic, iconDefaultColor);
        changeIconColor(mActionUnderline, iconDefaultColor);
        changeIconColor(mActionAlignLeft, iconDefaultColor);
        changeIconColor(mActionAlignCenter, iconDefaultColor);
        changeIconColor(mActionAlignRight, iconDefaultColor);
        getBaseActivity().initViewRightDrawable(mAddressTv, R.mipmap.ic_push_location, 23, 23);
        //        re.moveToEndEdit();
        setDefaultData();
    }

    @Override
    protected void lazyLoad() {
        NewsDraftsBean drafts = Hawk.get(AppUtils.SP_NEWS_SAVE_DRAFTS + MyApp.getAccount());
        if (drafts != null) {
            new AlertDialog.Builder(mContext)
                    .setCancelable(false)
                    .setMessage("?????????????????????????????????????????????????????????")
                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            re.setHtml(drafts.getHtmlContent());
                            re.moveToEndEdit();
                            mTitleEt.setText(drafts.getTitle());
                            informationId = drafts.getDraftsId();
                            Hawk.delete(AppUtils.SP_NEWS_SAVE_DRAFTS + MyApp.getAccount());
                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Hawk.delete(AppUtils.SP_NEWS_SAVE_DRAFTS + MyApp.getAccount());
                            EventManager.getEventBus().post(drafts);
                        }
                    }).show();
        }
    }

    /**
     * ??????????????????
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ? bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mAddressTv.setText(locAddress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            re.reFreshState();
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            default:
                break;
            case NewsContract.YASUO_PHOTO_TAG:
                List<String> photos = (List<String>) o;
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("informationId", informationId);
                if (photos != null && photos.size() > 0) {
                    for (String photoPath : photos) {
                        if (StringTools.isStringValueOk(photoPath)) {
                            builder.addFormDataPart("file", "file", RequestBody.create(MediaType.parse("file"), new File(photoPath)));
                        }
                    }
                } else {
                    return;
                }
                mPresenter.uploadNewsPhoto(NewsContract.UPLOAD_NEWS_PHOTO, builder.build());
                break;
            case NewsContract.UPLOAD_NEWS_PHOTO:
                NewsUploadPhotoBean photoBean = (NewsUploadPhotoBean) o;
                List<String> photoList = photoBean.getData();
                if (photoList != null && photoList.size() > 0) {
                    for (String path : photoList) {
                        // ????????????
                        re.insertImage(path, "image");
                    }
                }
                break;
            case NewsContract.PUBLISH_IMAGE_TEXT_NEWS:
                ToastUtils.success(mContext, (String) o);
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                Objects.requireNonNull(getActivity()).finish();
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.warning(mContext, String.valueOf(o));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.address_tv:
                //????????????
                Intent intent = new Intent(mContext, LocationSeltionActivity.class);
                startActivityForResult(intent, PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.action_img:
                //????????????
                mPresenter.imageChoose(this);
                break;
            case R.id.action_bold:
                re.setBold();
                if (re.isBold()) {
                    changeIconColor(mActionBold, iconSelectColor);
                } else {
                    changeIconColor(mActionBold, iconDefaultColor);
                }
                break;
            case R.id.action_italic:
                re.setItalic();
                if (re.isItalic()) {
                    changeIconColor(mActionItalic, iconSelectColor);
                } else {
                    changeIconColor(mActionItalic, iconDefaultColor);
                }
                break;
            case R.id.action_underline:
                re.setUnderLine();
                if (re.isUnderline()) {
                    changeIconColor(mActionUnderline, iconSelectColor);
                } else {
                    changeIconColor(mActionUnderline, iconDefaultColor);
                }
                break;
            case R.id.action_align_left:
                if (re.getTextAlign() == 1) {
                    return;
                }
                re.setAlignLeft();
                changeIconColor(mActionAlignLeft, iconSelectColor);
                changeIconColor(mActionAlignCenter, iconDefaultColor);
                changeIconColor(mActionAlignRight, iconDefaultColor);
                break;
            case R.id.action_align_center:
                if (re.getTextAlign() == 2) {
                    return;
                }
                re.setAlignCenter();
                changeIconColor(mActionAlignLeft, iconDefaultColor);
                changeIconColor(mActionAlignCenter, iconSelectColor);
                changeIconColor(mActionAlignRight, iconDefaultColor);
                break;
            case R.id.action_align_right:
                if (re.getTextAlign() == 3) {
                    return;
                }
                re.setAlignRight();
                changeIconColor(mActionAlignLeft, iconDefaultColor);
                changeIconColor(mActionAlignCenter, iconDefaultColor);
                changeIconColor(mActionAlignRight, iconSelectColor);
                break;
            case R.id.action_undo:
                re.undo();
                break;
            case R.id.action_redo:
                re.redo();
                break;
            case R.id.yulan_btn:
                submitData(1);
                break;
            case R.id.fabu_btn:
                submitData(0);
                break;
        }
    }

    /**
     * ??????
     * @param type 1?????????0??????
     */
    public void submitData(int type) {
        if (MyApp.isFastClick()) {
            ToastUtils.warning(mContext, "??????????????????");
            return;
        }
        if (!StringTools.isStringValueOk(getBaseActivity().getTextViewValue(mTitleEt))) {
            ToastUtils.warning(mContext, "???????????????");
            return;
        }
        if (!StringTools.isStringValueOk(locAddress)) {
            ToastUtils.warning(mContext, "?????????????????????");
            return;
        }
        if (!StringTools.isStringValueOk(re.getHtml())) {
            ToastUtils.warning(mContext, "?????????????????????");
            return;
        }
        if (type == 0){
            //        //??????
            MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
            builder.addFormDataPart("userid", String.valueOf(MyApp.getUid()))
                    .addFormDataPart("longitude", String.valueOf(locLon))
                    .addFormDataPart("latitude", String.valueOf(locLat))
                    .addFormDataPart("typeId", "2")
                    .addFormDataPart("informationId", informationId)
                    .addFormDataPart("title", getBaseActivity().getTextViewValue(mTitleEt))
                    .addFormDataPart("content", re.getHtml().replaceAll("\n", "</br>"))
                    .addFormDataPart("address", locAddress);
            mPresenter.publishNews(NewsContract.PUBLISH_IMAGE_TEXT_NEWS, builder.build());
        }else {
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("longitude", String.valueOf(locLon));
            bodyMap.put("latitude", String.valueOf(locLat));
            bodyMap.put("typeId", "2");
            bodyMap.put("informationId", informationId);
            bodyMap.put("title", getBaseActivity().getTextViewValue(mTitleEt));
            bodyMap.put("content", re.getHtml().replaceAll("\n", "</br>"));
            bodyMap.put("address", locAddress);
//            startActivity(new Intent(mContext, ImageTextPreActivity.class).putExtra(ImageTextPreActivity.NEWS_PART_BODY, (Serializable) bodyMap));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == getActivity().RESULT_OK) {
//            //????????????
//            locLat = data.getDoubleExtra("lat", 0.0);
//            locLon = data.getDoubleExtra("lng", 0.0);
//            locAddress = data.getStringExtra("address");
//            mAddressTv.setText(locAddress);
//            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
//        } else if (requestCode == NewsContract.REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
//            mPresenter.imageCompress(Matisse.obtainPathResult(data), (BaseMvpFragment) this);
//        }
    }

    // ????????????????????????
    private void changeIconColor(ImageView imageView, int color) {
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color));
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //???ui????????????
    public void receiveMsg(String test) {
//        if (ActionConfig.PUBLISH_NEWS_SAVE_DRAFTS.equals(test)) {
//            if (StringTools.isStringValueOk(re.getHtml())) {
//                NewsDraftsBean newsDraftsBean = new NewsDraftsBean(informationId, re.getHtml(), getBaseActivity().getTextViewValue(mTitleEt));
//                new AlertDialog.Builder(mContext)
//                        .setCancelable(true)
//                        .setMessage("??????????????????")
//                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Hawk.put(AppUtils.SP_NEWS_SAVE_DRAFTS + MyApp.getAccount(), newsDraftsBean);
//                                getActivity().finish();
//                            }
//                        })
//                        .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //????????????
//                                EventManager.getLibraryEvent().post(newsDraftsBean);
//                                getActivity().finish();
//                            }
//                        }).show();
//            } else {
//                getActivity().finish();
//            }
//        }
    }

    @Override
    public void onDestroyView() {
        destroyWebView();
        super.onDestroyView();
    }

    public void destroyWebView() {
        if (mEditor != null) {
            ViewParent parent = mEditor.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mEditor);
            }
            mEditor.stopLoading();
            mEditor.getSettings().setJavaScriptEnabled(false);
            mEditor.clearHistory();
            mEditor.removeAllViews();
            mEditor.destroy();
        }
    }
}
