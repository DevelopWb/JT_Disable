package com.juntai.disabled.basecomponent.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.utils.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.DividerItemDecoration;
import com.juntai.disabled.basecomponent.utils.EventManager;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.LoadingDialog;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ScreenUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

import static com.juntai.disabled.basecomponent.app.BaseApplication.app;


public abstract class BaseActivity extends RxAppCompatActivity implements Toolbar.OnMenuItemClickListener {
    public abstract int getLayoutView();

    public abstract void initView();

    public abstract void initData();


    public Context mContext;
    public Toast toast;
    private Toolbar toolbar;
    protected CoordinatorLayout mBaseRootCol;
    private boolean title_menu_first = true;
    private TextView mBackTv;
    public ImmersionBar mImmersionBar;
    private TextView titleName, titleRightTv;
    private boolean autoHideKeyboard = true;
    public FrameLayout frameLayout;
    public static int ActivityResult = 1001;//activity?????????
    public static int BASE_REQUEST_RESULT = 10086;//???????????????
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.getEventBus().register(this);//??????
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ????????????
        mContext = this;
        mImmersionBar = ImmersionBar.with(this);
        initWidows();
        setContentView(R.layout.activity_base);
        frameLayout = findViewById(R.id.base_content);
        if (0 != getLayoutView()) {
            frameLayout.addView(View.inflate(this, getLayoutView(), null));
        }
        toolbar = findViewById(R.id.base_toolbar);
        mBaseRootCol = findViewById(R.id.base_col);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBackTv = findViewById(R.id.back_tv);
        titleName = findViewById(R.id.title_name);
        titleRightTv = findViewById(R.id.title_rightTv);
        initToolbarAndStatusBar(true);
        initLeftBackTv(true);
        initView();
        initData();
        ActivityManagerTool.getInstance().addActivity(this);
    }

    /**
     * ????????? ????????????????????? ?????????toolbar????????????
     */
    protected void initToolbarAndStatusBar(boolean visible) {
        if (visible) {
            getToolbar().setVisibility(View.VISIBLE);
            getToolbar().setNavigationIcon(null);
            getToolbar().setBackgroundResource(R.drawable.bg_white_only_bottom_gray_shape_1px);
            //???????????????
            mBaseRootCol.setFitsSystemWindows(true);
            mImmersionBar.statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .init();
        }else{
            getToolbar().setVisibility(View.GONE);
            //???????????????
            mBaseRootCol.setFitsSystemWindows(false);
            mImmersionBar.reset().transparentStatusBar().init();
        }

    }

    /**
     * ????????????????????? ???????????????
     *
     * @param isShow ????????????
     */
    protected void initLeftBackTv(boolean isShow) {
        if (isShow) {
            mBackTv.setVisibility(View.VISIBLE);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.app_back);
            // ?????????????????????,??????????????????.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBackTv.setCompoundDrawables(drawable, null, null, null);
//            mBackTv.setText("??????");
            mBackTv.setCompoundDrawablePadding(-DisplayUtil.dp2px(this, 3));
            mBackTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            mBackTv.setVisibility(View.GONE);
        }

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTitleRightTv() {
        titleRightTv.setVisibility(View.VISIBLE);
        return titleRightTv;
    }

    /**
     * ??????????????????
     */
    public void showLoadingDialog(Context context) {
        LoadingDialog.getInstance().showProgress(context);
    }

    /**
     * ???????????????
     *
     * @return
     */
    public TextView getTitleLeftTv() {
        mBackTv.setVisibility(View.VISIBLE);
        return mBackTv;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ??????
     *
     * @param title
     */
    public void setTitleName(String title) {
        titleName.setText(title);
        titleName.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    /**
     * title??????:?????????
     */
    private void setRightRes() {
        //??????menu
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //????????????
        toolbar.setOnMenuItemClickListener(this);
    }

    /**
     * ??????????????????
     *
     * @param itemId
     */
    public void showTitleRes(int... itemId) {
        if (title_menu_first) {
            setRightRes();
            title_menu_first = false;
        }
        for (int item : itemId) {
            //??????
            toolbar.getMenu().findItem(item).setVisible(true);//??????id??????,????????????setIcon()????????????
            //            toolBar.getMenu().getItem(0).setVisible(true);//??????????????????
        }
    }

    /**
     * ??????title??????
     *
     * @param itemId :?????????????????????id
     */
    public void hindTitleRes(int... itemId) {
        //        if (titleBack != null)
        //            titleBack.setVisibility(View.GONE);
        for (int item : itemId) {
            //??????
            toolbar.getMenu().findItem(item).setVisible(false);
        }
    }

    /**
     * toolbar????????????---???activity??????onMenuItemClick()
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    /**
     * ??????webview
     *
     * @param webView
     */
    public void closeWebView(WebView webView) {
        if (webView != null) {
            //            ViewGroup parent = webView.getParent();
            //            if (parent != null) {
            //                parent.re(webView);
            //            }
            webView.removeAllViews();
            webView.destroy();
        }
    }
    //    @Override
    //    public void showLoadingFileDialog() {
    //        showFileDialog();
    //    }
    //
    //    @Override
    //    public void hideLoadingFileDialog() {
    //        hideFileDialog();
    //    }

    //    @Override
    //    public void onProgress(long totalSize, long downSize) {
    //        if (dialog != null) {
    //            dialog.setProgress((int) (downSize * 100 / totalSize));
    //        }
    //    }

    /**
     * ????????????????????????
     *
     * @param event
     * @param view
     * @param activity
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof TextView) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // ???????????????????????????????????????????????????????????????????????????????????????
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // ????????????
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????  view ????????????????????????view ???????????????edittext
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * ??????????????????????????????- - - ??????
     *
     * @param autoHideKeyboard:false - ???????????????
     */
    public void setAutoHideKeyboard(boolean autoHideKeyboard) {
        this.autoHideKeyboard = autoHideKeyboard;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                if (autoHideKeyboard) {
                    hideKeyboard(ev, view, BaseActivity.this);//??????????????????????????????????????????
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        ToastUtils.info(mContext,"??????");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ActivityManagerTool.getInstance().removeActivity(this);
        EventManager.getEventBus().unregister(this);//??????
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //??????????????????????????????????????????????????????????????????????????????bar???????????????????????????app????????????????????????????????????????????????????????????bar???????????????
            mImmersionBar = null;
        }
        this.mContext = null;
        stopLoadingDialog();
    }

    /**
     * ??????????????????
     */
    public void stopLoadingDialog() {
        LoadingDialog.getInstance().dismissProgress();
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public List<String> getTestData() {
        return Arrays.asList(new String[]{ "test2", "test3", "test4", "test5", "???????????????????????????????????????????????????????????????XXXXXXXXXXXXX", "???????????????????????????????????????????????????????????????"});
    }

    /**
     * ??????TextView??????
     *
     * @param textView
     * @return
     */
    public String getTextViewValue(TextView textView) {
        return textView.getText().toString().trim();
    }



    /**
     * ??????????????? ?????????????????????????????????
     */
    protected void initWidows() {
        //?????????????????? 360??????????????????px/2
        ScreenUtils screenUtils = ScreenUtils.getInstance(getApplicationContext());
        if (screenUtils.isPortrait()) {
            screenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            screenUtils.adaptScreen4HorizontalSlide(this, 360);
        }

    }

    /**
     * ?????????recyclerview LinearLayoutManager
     */
    public void initRecyclerview(RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter, @RecyclerView.Orientation int orientation) {
        LinearLayoutManager managere = new LinearLayoutManager(this, orientation, false);
        //        baseQuickAdapter.setEmptyView(getAdapterEmptyView("?????????????????????",0));
        recyclerView.setLayoutManager(managere);
        recyclerView.setAdapter(baseQuickAdapter);
    }
    /**
     * ???????????????
     *
     * @param recyclerView
     * @param haveTopLine
     * @param isHorizontalDivider ???????????????
     * @param haveEndLine         ????????????item???????????????
     */
    public void addDivider(boolean isHorizontalDivider, RecyclerView recyclerView, boolean haveTopLine, boolean haveEndLine) {
        DividerItemDecoration dividerItemDecoration;
        if (isHorizontalDivider) {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_hor_line_sp);
        } else {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
        }
        if (haveTopLine) {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.ALL);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.START);
            }
        } else {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.END);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.INSIDE);

            }
        }
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * ???????????????
     *
     * @param text
     * @return
     */
    public View getAdapterEmptyView(String text, int imageId) {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        TextView noticeTv = view.findViewById(R.id.none_tv);
        noticeTv.setText(text);
        ImageView imageView = view.findViewById(R.id.none_image);
        if (-1==imageId) {
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setImageResource(imageId);
        }
        return view;
    }

    /**
     * ??????imageview????????????
     *
     * @param imageView
     */
    public void recycleImageView(ImageView imageView) {
        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bm.recycle();
        bm = null;
    }
    //????????????
    public  void singleLogin(){}
    @Subscribe(threadMode = ThreadMode.MAIN) //???ui????????????
    public void receiveBaseStringMsg(String msg) {
        switch (msg) {
            case EventManager.SINGLE_LOGIN:
                //????????????
                singleLogin();
                break;
            default:
                break;
        }
    }
    /**
     * ??????view???margin??????
     */
    public void setMargin(View view, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(this, left);
        top = DisplayUtil.dp2px(this, top);
        right = DisplayUtil.dp2px(this, right);
        bottom = DisplayUtil.dp2px(this, bottom);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }
    /**
     * ???????????????  view ????????????????????????view ???????????????edittext
     */
    public void hideKeyboardFromView(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * view????????????
     */
    public  void getViewFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }
    /**
     * ???????????????
     *
     * @param view
     */
    public void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

    }

    /**
     * ????????????
     */
    public void makeAPhoneCall(String telNum) {
        View view = getLayoutInflater().inflate(R.layout.call_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.show();
        final TextView phone = view.findViewById(R.id.property_phone_no_tv);
        phone.setText(telNum);
        view.findViewById(R.id.call_property_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RxPermissions(BaseActivity.this)
                        .request(new String[]{
                                Manifest.permission.CALL_PHONE})
                        .delay(1, TimeUnit.SECONDS)
                        .compose(bindUntilEvent(ActivityEvent.DESTROY))
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {

                                } else {
                                    //????????????????????????
                                }
                                //??????????????????
                                alertDialog.dismiss();
                                PubUtil.callPhone(BaseActivity.this, phone.getText().toString().trim());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                            }
                        });
            }
        });
        view.findViewById(R.id.cancel_call_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    /**
     * ????????????
     * @param path  ????????????
     * @param saveDirName  ???????????????????????????
     * @param onImageCompressedPath
     * @param saveFileName  ?????????????????????
     */
    public void  compressImage(String path, String saveDirName,
                               String saveFileName,OnImageCompressedPath onImageCompressedPath) {
        //        showLoadingDialog(mContext);
        Luban.with(mContext).load(path).ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath(saveDirName))
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                return TextUtils.isEmpty(saveFileName)||saveFileName==null?System.currentTimeMillis()+".jpg":
                        saveFileName+".jpg";
            }
        })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  ???????????????????????????????????????????????? loading UI

                    }

                    @Override
                    public void onSuccess(File file) {
                        //  ??????????????????????????????????????????????????????
                        if (onImageCompressedPath != null) {
                            onImageCompressedPath.compressedImagePath(file);
                        }
                        stopLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("push-??????????????????");
                        stopLoadingDialog();
                    }
                }).launch();
    }

    /**
     * ????????????????????????
     */
    public interface OnImageCompressedPath {
        void  compressedImagePath(File file);
    }

    /**
     * ??????????????????
     * @param textView
     * @param drawableId
     */
    public void initViewLeftDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//????????? 0 ?????????????????????????????? 0 ?????????????????????40 ???????????????
        textView.setCompoundDrawables(drawable, null, null, null);//?????????
    }

    /**
     * ??????????????????
     * @param textView
     * @param drawableId
     */
    public void initViewTopDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//????????? 0 ?????????????????????????????? 0 ?????????????????????40 ???????????????
        textView.setCompoundDrawables(null, drawable, null, null);//?????????
    }

    /**
     * ??????????????????
     *
     * @param textView
     * @param drawableId
     */
    public void initViewRightDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//????????? 0 ?????????????????????????????? 0 ?????????????????????40 ???????????????
        textView.setCompoundDrawables(null, null, drawable, null);//????????????
    }


}
