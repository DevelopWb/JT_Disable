package cn.rongcloud.rtc;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.herewhite.sdk.AbstractRoomCallbacks;
import com.herewhite.sdk.Room;
import com.herewhite.sdk.RoomParams;
import com.herewhite.sdk.WhiteBroadView;
import com.herewhite.sdk.WhiteSdk;
import com.herewhite.sdk.WhiteSdkConfiguration;
import com.herewhite.sdk.domain.Appliance;
import com.herewhite.sdk.domain.DeviceType;
import com.herewhite.sdk.domain.MemberState;
import com.herewhite.sdk.domain.PptPage;
import com.herewhite.sdk.domain.Promise;
import com.herewhite.sdk.domain.RoomPhase;
import com.herewhite.sdk.domain.RoomState;
import com.herewhite.sdk.domain.SDKError;
import com.herewhite.sdk.domain.Scene;
import com.herewhite.sdk.domain.SceneState;
import com.herewhite.sdk.domain.UrlInterrupter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.rongcloud.rtc.base.RongRTCBaseActivity;
import cn.rongcloud.rtc.callSettingsFragment.CallSettingsFragment;
import cn.rongcloud.rtc.callback.RongRTCDataResultCallBack;
import cn.rongcloud.rtc.callback.RongRTCResultUICallBack;
import cn.rongcloud.rtc.config.RongCenterConfig;
import cn.rongcloud.rtc.core.CameraVideoCapturer;
import cn.rongcloud.rtc.core.CreateEglContextException;
import cn.rongcloud.rtc.core.rongRTC.DevicesUtils;
import cn.rongcloud.rtc.custom.MediaMode;
import cn.rongcloud.rtc.custom.OnSendListener;
import cn.rongcloud.rtc.device.utils.Consts;
import cn.rongcloud.rtc.engine.report.StatusBean;
import cn.rongcloud.rtc.engine.report.StatusReport;
import cn.rongcloud.rtc.engine.view.RongRTCVideoView;
import cn.rongcloud.rtc.entity.KickEvent;
import cn.rongcloud.rtc.entity.KickedOfflineEvent;
import cn.rongcloud.rtc.entity.ResolutionInfo;
import cn.rongcloud.rtc.entity.RongRTCDeviceType;
import cn.rongcloud.rtc.entity.UserInfo;
import cn.rongcloud.rtc.events.ILocalAudioPCMBufferListener;
import cn.rongcloud.rtc.events.ILocalVideoFrameListener;
import cn.rongcloud.rtc.events.IRemoteAudioPCMBufferListener;
import cn.rongcloud.rtc.events.RTCAudioFrame;
import cn.rongcloud.rtc.events.RTCVideoFrame;
import cn.rongcloud.rtc.events.RongRTCEglEventListener;
import cn.rongcloud.rtc.events.RongRTCEventsListener;
import cn.rongcloud.rtc.events.RongRTCStatusReportListener;
import cn.rongcloud.rtc.media.http.HttpClient;
import cn.rongcloud.rtc.message.RoomInfoMessage;
import cn.rongcloud.rtc.message.RoomKickOffMessage;
import cn.rongcloud.rtc.message.WhiteBoardInfoMessage;
import cn.rongcloud.rtc.room.RongRTCLiveInfo;
import cn.rongcloud.rtc.room.RongRTCRoom;
import cn.rongcloud.rtc.stream.ResourceState;
import cn.rongcloud.rtc.stream.local.RongRTCAVOutputStream;
import cn.rongcloud.rtc.stream.local.RongRTCCapture;
import cn.rongcloud.rtc.stream.local.RongRTCLocalSourceManager;
import cn.rongcloud.rtc.stream.remote.RongRTCAVInputStream;
import cn.rongcloud.rtc.user.RongRTCLocalUser;
import cn.rongcloud.rtc.user.RongRTCRemoteUser;
import cn.rongcloud.rtc.util.AssetsFilesUtil;
import cn.rongcloud.rtc.util.BluetoothUtil;
import cn.rongcloud.rtc.util.ButtentSolp;
import cn.rongcloud.rtc.util.HeadsetPlugReceiver;
import cn.rongcloud.rtc.util.OnHeadsetPlugListener;
import cn.rongcloud.rtc.util.PromptDialog;
import cn.rongcloud.rtc.util.RongRTCPopupWindow;
import cn.rongcloud.rtc.util.RongRTCTalkTypeUtil;
import cn.rongcloud.rtc.util.SessionManager;
import cn.rongcloud.rtc.util.Utils;
import cn.rongcloud.rtc.utils.AudioBufferStream;
import cn.rongcloud.rtc.utils.FinLog;
import cn.rongcloud.rtc.watersign.TextureHelper;
import cn.rongcloud.rtc.watersign.WaterMarkFilter;
import cn.rongcloud.rtc.whiteboard.PencilColorPopupWindow;
import cn.rongcloud.rtc.whiteboard.WhiteBoardApi;
import cn.rongcloud.rtc.whiteboard.WhiteBoardRoomInfo;
import io.rong.common.RLog;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;

import static cn.rongcloud.rtc.SettingActivity.IS_GPUIMAGEFILTER;
import static cn.rongcloud.rtc.util.Utils.parseTimeSeconds;
import static io.rong.imlib.RongIMClient.ConnectionStatusListener.ConnectionStatus.NETWORK_UNAVAILABLE;

/**
 * Activity for peer connection call setup, call waiting
 * and call view.
 */
public class CallActivity extends RongRTCBaseActivity implements View.OnClickListener, OnHeadsetPlugListener,
        RongRTCEventsListener ,RongRTCStatusReportListener,ILocalVideoFrameListener,
        RongRTCEglEventListener,ILocalAudioPCMBufferListener,IRemoteAudioPCMBufferListener {
    private static String TAG = "CallActivity";
    private boolean isShowAutoTest;
    private AlertDialog ConfirmDialog = null;
    private String deviceId = "";

    public static final String EXTRA_ROOMID = "blinktalk.io.ROOMID";
    public static final String EXTRA_USER_NAME = "blinktalk.io.USER_NAME";
    public static final String EXTRA_CAMERA = "blinktalk.io.EXTRA_CAMERA";
    public static final String EXTRA_OBSERVER = "blinktalk.io.EXTRA_OBSERVER";
    public static final String EXTRA_ONLY_PUBLISH_AUDIO = "ONLY_PUBLISH_AUDIO";
    public static final String EXTRA_AUTO_TEST = "EXTRA_AUTO_TEST";
    public static final String EXTRA_WATER = "EXTRA_WATER";
    public static final String EXTRA_IS_MASTER = "EXTRA_IS_MASTER";
    public static final String EXTRA_IS_LIVE = "EXTRA_IS_LIVE";
    private static String Path = Environment.getExternalStorageDirectory().toString() + File.separator;

    // List of mandatory application unGrantedPermissions.
    private static final String[] MANDATORY_PERMISSIONS = {
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.CAMERA",
            "android.permission.READ_PHONE_STATE",
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            "android.permission.BLUETOOTH_ADMIN",
            "android.permission.BLUETOOTH"
    };
    private boolean isInRoom;

    private AppRTCAudioManager audioManager = null;
    private boolean isVideoMute;
    private boolean isObserver;
    private boolean canOnlyPublishAudio;
    Handler networkSpeedHandler;
    // Controls
    private String roomId = "", iUserName = "";
    private VideoViewManager renderViewManager;
    private boolean isConnected = true;

    private TextView textViewRoomNumber;
    private TextView textViewTime;
    private TextView textViewNetSpeed;
    private Button buttonHangUp;
    //    private CheckBox btnRotateScreen;
    //    ?????????????????????layout
    //    private LinearLayout moreContainer;
    private LinearLayout waitingTips;
    private LinearLayout layoutNetworkStatusInfo;
    private TextView txtViewNetworkStatusInfo;
    private LinearLayout titleContainer;
    private RelativeLayout mcall_more_container;
    private boolean isGPUImageFliter = false;
    private Handler handler = new Handler();
    private DebugInfoAdapter debugInfoAdapter;
    private ListView debugInfoListView;
    private TextView biteRateSendView;
    private TextView biteRateRcvView;
    private TextView rttSendView;
    private RongRTCPopupWindow popupWindow;
    private LinearLayout call_reder_container;
    private int sideBarWidth = 0;
    private AppCompatCheckBox btnSwitchCamera;
    private AppCompatCheckBox btnMuteSpeaker;
    private AppCompatCheckBox btnCloseCamera;
    private AppCompatCheckBox btnMuteMic;
    private AppCompatCheckBox btnSwitchSpeechMusic;
    private AppCompatCheckBox btnRaiseHand;
    private AppCompatCheckBox btnChangeResolution_up;
    private AppCompatCheckBox btnChangeResolution_down;
    private ImageButton btnMembers;
    private ImageView iv_modeSelect;
    private List<MembersDialog.ItemModel> mMembers = new ArrayList<>();
    private Map<String, UserInfo> mMembersMap = new HashMap<>();
    private AppCompatCheckBox btnCustomStream;
    private AppCompatCheckBox btnCustomAudioStream;
    private AppCompatCheckBox btnCustomAudioVolume;
    private AppCompatButton btnMenuSettings;

    /**
     * UpgradeToNormal?????????????????????,??????????????????????????????=0, ?????????:1 ?????????:2
     **/
    Map<Integer, ActionState> stateMap = new LinkedHashMap<>();
    /**
     * ??????????????????????????????
     **/
    private HashMap<String, Boolean> sharingMap = new HashMap<>();

    /**
     * true  ???????????????,false ???????????????
     */
    private boolean muteMicrophone = false;

    /**
     * true ?????????????????? false ???????????????
     */
    private boolean muteSpeaker = false;
    /**
     * true  ????????????,false ????????????
     */
    private boolean isMusic = false;

    private ScrollView scrollView;
    private HorizontalScrollView horizontalScrollView;
    private RelativeLayout rel_sv;//sv?????????
    GPUImageBeautyFilter beautyFilter;
    private String myUserId;
    //?????????uerId,????????????????????????????????????????????????
    private String adminUserId;
    private boolean kicked;

    private RongRTCRoom rongRTCRoom;
    private RongRTCLocalUser localUser;

    private HeadsetPlugReceiver headsetPlugReceiver = null;
    private boolean HeadsetPlugReceiverState = false;//false?????????????????????????????????????????????
    private boolean mShowWaterMark;
    private WaterMarkFilter mWaterFilter;

    //??????????????????
    private WhiteBroadView whiteboardView;
    private ProgressDialog progressDialog;
    private WhiteBoardRoomInfo whiteBoardRoomInfo;
    private View whiteBoardAction;
    private PencilColorPopupWindow pencilColorPopupWindow;
    private Scene[] whiteBoardScenes;
    private String currentSceneName;
    private int currentSceneIndex;
    private Room whiteBoardRoom;
    private Button whiteBoardPagesPrevious;
    private Button whiteBoardPagesNext;
    private Button whiteBoardClose;
    private AppCompatCheckBox btnWhiteBoard;
    private boolean customVideoEnabled = true;

    private List<StatusReport> statusReportList = new ArrayList<>();
    private int lossRateSum = 0;
    private SoundPool mSoundPool;
    private boolean playSound = true;
    private Timer networkObserverTimer = null;
    private int timerPeriod = 5 * 1000;
    private boolean mIsLive;
    /**
     * ???????????????????????????5
     */
    private int volumeIndex = 5 ;

    /**
     * ????????????????????????????????????pcm??????????????????????????????????????????,?????????????????????sdcard/webrtc/
     * 1.????????? writePcmFileForDebug ?????????true ??????
     * 2. ???????????????????????????????????????????????????????????????????????????
     */
    private boolean writePcmFileForDebug = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HeadsetPlugReceiver.setOnHeadsetPlugListener(this);
        if (BluetoothUtil.isSupportBluetooth()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
            headsetPlugReceiver = new HeadsetPlugReceiver();
            registerReceiver(headsetPlugReceiver, intentFilter);
        }

        sideBarWidth = dip2px(CallActivity.this, 40) + 75;

        // Set window styles for fullscreen-window size. Needs to be done before
        // adding content.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_call);

        // Get Intent parameters.
        final Intent intent = getIntent();
        roomId = intent.getStringExtra(EXTRA_ROOMID);
        iUserName = intent.getStringExtra(EXTRA_USER_NAME);
        isVideoMute = intent.getBooleanExtra(EXTRA_CAMERA, false);
        isObserver = intent.getBooleanExtra(EXTRA_OBSERVER, false);
        isShowAutoTest= intent.getBooleanExtra(EXTRA_AUTO_TEST,false);
        canOnlyPublishAudio = intent.getBooleanExtra(EXTRA_ONLY_PUBLISH_AUDIO, false);
        mShowWaterMark = intent.getBooleanExtra(EXTRA_WATER,false);
        mIsLive = intent.getBooleanExtra(EXTRA_IS_LIVE,false);
        //??????????????????????????????
        isGPUImageFliter = SessionManager.getInstance().getBoolean(IS_GPUIMAGEFILTER);
        if (TextUtils.isEmpty(roomId)) {
            Log.e(TAG, "Incorrect room ID in intent!");
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        myUserId = RongIMClient.getInstance().getCurrentUserId();
        boolean admin = intent.getBooleanExtra(EXTRA_IS_MASTER, false);
        if (admin) {
            adminUserId = myUserId;
        }
        initAudioManager();
        initViews(intent);
        checkPermissions();
        initBottomBtn();
        initRemoteScrollView();
        if (rongRTCRoom == null) {
            return;
        }
        rongRTCRoom.getRoomAttributes(null, new RongRTCDataResultCallBack<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data) {
                try {
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        JSONObject jsonObject = new JSONObject(entry.getValue());
                        if (entry.getKey().equals(WhiteBoardApi.WHITE_BOARD_KEY)) {
                            whiteBoardRoomInfo = new WhiteBoardRoomInfo(jsonObject.getString("uuid"),
                                    jsonObject.getString("roomToken"));
                            continue;
                        }
                        UserInfo userInfo = new UserInfo();
                        userInfo.userName = jsonObject.getString("userName");
                        userInfo.joinMode = jsonObject.getInt("joinMode");
                        userInfo.userId = jsonObject.getString("userId");
                        userInfo.timestamp = jsonObject.getLong("joinTime");
                        boolean master = jsonObject.optInt("master") == 1;
                        if (master) {
                            adminUserId = userInfo.userId;
                        }
                        if (rongRTCRoom.getRemoteUser(userInfo.userId) == null
                                && !TextUtils.equals(myUserId, userInfo.userId)) {
                            continue;
                        }
                        if (mMembersMap.containsKey(entry.getKey())) {
                            continue;
                        }
                        mMembersMap.put(entry.getKey(), userInfo);

                        MembersDialog.ItemModel model = new MembersDialog.ItemModel();
                        model.mode = mapMode(userInfo.joinMode);
                        model.name = userInfo.userName;
                        model.userId = userInfo.userId;
                        model.joinTime = userInfo.timestamp;
                        mMembers.add(model);

                        List<VideoViewManager.RenderHolder> holders = renderViewManager.getViewHolderByUserId(entry.getKey());
                        for (VideoViewManager.RenderHolder holder : holders) {
                            if (TextUtils.equals(entry.getKey(), myUserId)) {
                                holder.updateUserInfo(getResources().getString(R.string.room_actor_me));
                            } else {
                                holder.updateUserInfo(model.name);
                            }
                        }
                        setWaitingTipsVisiable(mMembers.size() <= 1);
                    }
                    sortRoomMembers();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(RTCErrorCode errorCode) {

            }
        });

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (writePcmFileForDebug) {
            createDebugPcmFile();
        }
    }

    private void sortRoomMembers() {
        Collections.sort(mMembers, new Comparator<MembersDialog.ItemModel>() {
            @Override
            public int compare(MembersDialog.ItemModel o1, MembersDialog.ItemModel o2) {
                return (int) (o1.joinTime - o2.joinTime);
            }
        });
        //??????????????????????????????????????????????????????????????????????????????
        if (mMembers.size() > 0 && !mMembers.get(0).userId.equals(adminUserId)) {
            MembersDialog.ItemModel adminItem = null;
            for (MembersDialog.ItemModel model : mMembers) {
                if (model.userId.equals(adminUserId)) {
                    adminItem = model;
                    break;
                }
            }
            if (adminItem != null) {
                mMembers.remove(adminItem);
                mMembers.add(0, adminItem);
            }
        }
    }

    private void initAudioManager() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Create and audio manager that will take care of audio routing,
        // audio modes, audio device enumeration etc.
        audioManager = AppRTCAudioManager.create(this, new Runnable() {
                    // This method will be called each time the audio state (number and
                    // type of devices) has been changed.
                    @Override
                    public void run() {
                        onAudioManagerChangedState();
                    }
                }
        );
        // Store existing audio settings and change audio mode to
        // MODE_IN_COMMUNICATION for best possible VoIP performance.
        Log.d(TAG, "Initializing the audio manager...");
        audioManager.init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            horizontalScreenViewInit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            verticalScreenViewInit();
        }
        if (renderViewManager != null && null != unGrantedPermissions && unGrantedPermissions.size() == 0) {
            renderViewManager.rotateView();
        }
        if (mWaterFilter != null)
            mWaterFilter.angleChange(RongRTCCapture.getInstance().isFrontCamera());
    }

    /**
     * ????????????????????? ????????????
     **/
    private void initBottomBtn() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) btnCloseCamera.getLayoutParams();
        layoutParams.setMargins(dip2px(CallActivity.this, 50), 0, 0, dip2px(CallActivity.this, 16));
        btnCloseCamera.setLayoutParams(layoutParams);

        ViewGroup.MarginLayoutParams mutelayoutParams = (ViewGroup.MarginLayoutParams) btnMuteMic.getLayoutParams();
        mutelayoutParams.setMargins(0, 0, dip2px(CallActivity.this, 50), dip2px(CallActivity.this, 16));
        btnMuteMic.setLayoutParams(mutelayoutParams);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initViews(Intent intent) {
        findViewById(R.id.btn_mcu).setVisibility(mIsLive ? View.VISIBLE : View.GONE);
        findViewById(R.id.btn_mcu).setOnClickListener(this);
        mcall_more_container = (RelativeLayout) findViewById(R.id.call_more_container);
        iv_modeSelect = (ImageView) findViewById(R.id.btn_modeSelect);
        btnRaiseHand = (AppCompatCheckBox) findViewById(R.id.menu_request_to_normal);
        btnSwitchCamera = (AppCompatCheckBox) findViewById(R.id.menu_switch);
        btnMuteSpeaker = (AppCompatCheckBox) findViewById(R.id.menu_mute_speaker);
        btnSwitchSpeechMusic = (AppCompatCheckBox) findViewById(R.id.menu_switch_speech_music);
        btnWhiteBoard = (AppCompatCheckBox) findViewById(R.id.menu_whiteboard);
        btnChangeResolution_up = (AppCompatCheckBox) findViewById(R.id.menu_up);
        btnChangeResolution_down = (AppCompatCheckBox) findViewById(R.id.menu_down);
        titleContainer = (LinearLayout) findViewById(R.id.call_layout_title);
        call_reder_container = (LinearLayout) findViewById(R.id.call_reder_container);
        biteRateSendView = (TextView) findViewById(R.id.debug_info_bitrate_send);
        biteRateRcvView = (TextView) findViewById(R.id.debug_info_bitrate_rcv);
        rttSendView = (TextView) findViewById(R.id.debug_info_rtt_send);
        debugInfoListView = (ListView) findViewById(R.id.debug_info_list);
        textViewRoomNumber = (TextView) findViewById(R.id.call_room_number);
        textViewTime = (TextView) findViewById(R.id.call_time);
        textViewNetSpeed = (TextView) findViewById(R.id.call_net_speed);
        buttonHangUp = (Button) findViewById(R.id.call_btn_hangup);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        btnCloseCamera = (AppCompatCheckBox) findViewById(R.id.menu_close);
        btnMuteMic = (AppCompatCheckBox) findViewById(R.id.menu_mute_mic);
        waitingTips = (LinearLayout) findViewById(R.id.call_waiting_tips);
        layoutNetworkStatusInfo = (LinearLayout) findViewById(R.id.layout_network_status_tips);
        txtViewNetworkStatusInfo = (TextView) findViewById(R.id.textView_network_status_tips);
        whiteboardView = (WhiteBroadView) findViewById(R.id.call_whiteboard);
        whiteBoardAction = findViewById(R.id.white_board_action);
        btnMembers = (ImageButton) findViewById(R.id.menu_members);
        whiteBoardPagesPrevious = (Button) findViewById(R.id.white_board_pages_previous);
        whiteBoardPagesNext = (Button) findViewById(R.id.white_board_pages_next);
        whiteBoardClose = (Button)findViewById(R.id.white_board_close);
        btnCustomStream = (AppCompatCheckBox) findViewById(R.id.menu_custom_stream);
        btnCustomAudioStream = (AppCompatCheckBox) findViewById(R.id.menu_custom_audio);
        btnCustomAudioVolume = (AppCompatCheckBox) findViewById(R.id.menu_custom_audio_volume);
        btnMenuSettings = (AppCompatButton) findViewById(R.id.menu_btn_call_menu_settings);
        if (BuildConfig.DEBUG && null != btnChangeResolution_up) {
            btnChangeResolution_up.setVisibility(View.GONE);
        } else {
            btnChangeResolution_up.setVisibility(View.GONE);
        }
        if (BuildConfig.DEBUG && null != btnChangeResolution_down) {
            btnChangeResolution_down.setVisibility(View.GONE);
        } else {
            btnChangeResolution_down.setVisibility(View.GONE);
        }

        debugInfoAdapter = new DebugInfoAdapter(this);
        debugInfoListView.setAdapter(debugInfoAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.white_board_loading));

        rel_sv = (RelativeLayout) findViewById(R.id.rel_sv);

        iv_modeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ButtentSolp.check(view.getId(), 500)) {
                    return;
                }
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                } else {
                    showPopupWindow();
                }
            }
        });
        toggleCameraMicViewStatus();

        renderViewManager = new VideoViewManager();
        renderViewManager.setActivity(this);
        if (BuildConfig.DEBUG) {
            textViewNetSpeed.setVisibility(View.VISIBLE);
        } else {
            textViewNetSpeed.setVisibility(View.GONE);
        }

        textViewRoomNumber.setText(getText(R.string.room_number) + intent.getStringExtra(CallActivity.EXTRA_ROOMID));
        buttonHangUp.setOnClickListener(this);
        btnSwitchCamera.setOnClickListener(this);
        btnCloseCamera.setOnClickListener(this);
        btnMuteMic.setOnClickListener(this);
        btnSwitchSpeechMusic.setOnClickListener(this);
        btnMuteSpeaker.setOnClickListener(this);
//        btnWhiteBoard.setOnClickListener(this);
        btnMembers.setOnClickListener(this);
        btnRaiseHand.setOnClickListener(this);
        waitingTips.setOnClickListener(this);
        btnChangeResolution_up.setOnClickListener(this);
        btnChangeResolution_down.setOnClickListener(this);
//        btnCustomStream.setOnClickListener(this);
//        btnCustomAudioStream.setOnClickListener(this);
//        btnCustomAudioVolume.setOnClickListener(this);
        btnMenuSettings.setOnClickListener(this);
        renderViewManager.setOnLocalVideoViewClickedListener(new VideoViewManager.OnLocalVideoViewClickedListener() {
            @Override
            public void onClick() {
                toggleActionButtons(buttonHangUp.getVisibility() == View.VISIBLE);
            }
        });

        if (isObserver){
            btnMuteMic.setChecked(true);
            btnMuteMic.setEnabled(false);
            btnCloseCamera.setChecked(true);
            btnCloseCamera.setEnabled(false);
            btnCustomStream.setEnabled(false);
            btnCustomAudioStream.setEnabled(false);
        }
        if (isVideoMute) {
            btnCloseCamera.setChecked(true);
            btnCloseCamera.setEnabled(false);
        }

        //setCallIdel();
    }

    /**
     * ????????????????????????
     *
     * @param initiative ?????????????????????false??????????????????
     */
    private void intendToLeave(boolean initiative) {
        FinLog.i(TAG,"intendToLeave()-> "+ initiative);
        unpublishLiveData();
        if (null != sharingMap) {
            sharingMap.clear();
        }
        if (initiative) {
            selectAdmin();
        } else {
            kicked = true;
        }
        CenterManager.getInstance().unPublishMediaStream(null);
        RongRTCAudioMixer.getInstance().stop();
        //???????????????????????? ??? ??????????????????????????????????????????????????????
        disconnect();
    }

    private void unpublishLiveData() {
        if (liveRoom != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(LiveDataOperator.ROOM_ID, liveRoom.getRoomId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LiveDataOperator.getInstance().unpublish(jsonObject.toString(), null);
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    private void toggleActionButtons(boolean isHidden) {
        if (isHidden) {
            buttonHangUp.setVisibility(View.GONE);
            mcall_more_container.setVisibility(View.GONE);
            titleContainer.setVisibility(View.GONE);
            btnCloseCamera.setVisibility(View.GONE);
            btnMuteMic.setVisibility(View.GONE);
        } else {
            btnCloseCamera.setVisibility(View.VISIBLE);
            btnMuteMic.setVisibility(View.VISIBLE);
            buttonHangUp.setVisibility(View.VISIBLE);
            mcall_more_container.setVisibility(View.VISIBLE);
            titleContainer.setVisibility(View.VISIBLE);
            startTenSecondsTimer();
        }
    }

    private Timer tenSecondsTimer;

    /**
     * ??????????????????10?????????????????????????????????????????????????????????
     */
    private void startTenSecondsTimer() {
//        tenSecondsTimer = new Timer();
//        tenSecondsTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        toggleActionButtons(true);
//                        tenSecondsTimer = null;
//                    }
//                });
//            }
//        }, 10 * 1000);
    }

    private void setCallIdel() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                if (state == TelephonyManager.CALL_STATE_OFFHOOK){
                    if (RongRTCEngine.getInstance() != null) {
//                            RongRTCEngine.getInstance().muteMicrophone(true);
                    }
                }else if (state == TelephonyManager.CALL_STATE_IDLE){
                    if (RongRTCEngine.getInstance() != null) {
//                            RongRTCEngine.getInstance().muteMicrophone(false);
                    }
                }else if (state == TelephonyManager.CALL_STATE_RINGING){
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void setWaitingTipsVisiable(boolean visiable) {
//        FinLog.v(TAG,"setWaitingTipsVisiable() visiable = "+visiable);
        if (visiable) {
            visiable = !(mMembers != null && mMembers.size() > 1);
        }
        int tmp = waitingTips.getVisibility();
        if (visiable) {
            if (tmp != View.VISIBLE){
                handler.removeCallbacks(timeRun);
            }
            waitingTips.setVisibility(View.VISIBLE);
            initUIForWaitingStatus();
        } else {
            waitingTips.setVisibility(View.GONE);
            if (tmp == View.VISIBLE) {
                handler.postDelayed(timeRun, 1000);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearState();
    }

    private void destroyPopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    protected void onDestroy() {
        destroyPopupWindow();
        if (networkObserverTimer != null) {
            networkObserverTimer.cancel();
            networkObserverTimer = null;
        }
        HeadsetPlugReceiver.setOnHeadsetPlugListener(null);
        if (headsetPlugReceiver != null) {
            unregisterReceiver(headsetPlugReceiver);
            headsetPlugReceiver = null;
        }
        HeadsetPlugReceiverState = false;
        if(rongRTCRoom!=null){
            rongRTCRoom.unregisterEventsListener(this);
            rongRTCRoom.unregisterStatusReportListener(this);
            rongRTCRoom.unregisterEglEventsListener(this);
        }
        if (isConnected) {
            CenterManager.getInstance().unPublishMediaStream(null);
            RongRTCAudioMixer.getInstance().stop();
            if (RongRTCEngine.getInstance() != null)
                if (rongRTCRoom != null) {
                    rongRTCRoom.deleteRoomAttributes(Arrays.asList(myUserId), null, null);
                    deleteRTCWhiteBoardAttribute();
                }
            RongRTCEngine.getInstance().quitRoom(roomId, new RongRTCResultUICallBack() {
                @Override
                public void onUiSuccess() {
                    isInRoom = false;
                }

                @Override
                public void onUiFailed(RTCErrorCode errorCode) {
                }
            });
            if (renderViewManager != null)
//                renderViewManager.destroyViews();
                if (audioManager != null) {
                    audioManager.close();
                    audioManager = null;
                }
        }
        if (handler != null) {
            handler.removeCallbacks(memoryRunnable);
            handler.removeCallbacks(timeRun);
        }
        handler = null;
        super.onDestroy();
        if (null != ConfirmDialog && ConfirmDialog.isShowing()) {
            ConfirmDialog.dismiss();
            ConfirmDialog = null;
        }
        if (null != sharingMap) {
            sharingMap.clear();
        }
        destroyWebView(whiteboardView);
        if (beautyFilter != null) {
            beautyFilter.destroy();
            beautyFilter = null;
        }

        if (mWaterFilter != null){
            mWaterFilter.release();
        }
        RongRTCCapture.getInstance().setLocalAudioPCMBufferListener(null);
        RongRTCCapture.getInstance().setRemoteAudioPCMBufferListener(null);
        RongRTCCapture.getInstance().setLocalVideoFrameListener(true,null);
        if (mSoundPool != null){
            mSoundPool.release();
        }
        mSoundPool = null;
        localSurface = null;

        EventBus.getDefault().unregister(this);
        if (writePcmFileForDebug) {
            closePcmFile(localFileChanel, localFileStream);
            closePcmFile(remoteFileChanel, remoteFileStream);
        }
    }


    public void onCameraSwitch() {
//        RongRTCEngine.getInstance().switchCamera();
    }

    /**
     * ???????????????
     *
     * @param closed true  ???????????????
     *               false ???????????????
     * @return
     * @isActive true?????????
     */
    public boolean onCameraClose(boolean closed) {
        Log.i(TAG, "onCameraClose closed = " + closed);
        this.isVideoMute = closed;
        RongRTCCapture.getInstance().muteLocalVideo(closed);
        if (renderViewManager != null) {
            renderViewManager.updateTalkType(myUserId, RongRTCCapture.getInstance().getTag(), closed ? RongRTCTalkTypeUtil.C_CAMERA : RongRTCTalkTypeUtil.O_CAMERA);
        }
        toggleCameraMicViewStatus();
        return isVideoMute;
    }

    public void onToggleMic(boolean mute) {
        muteMicrophone = mute;
        RongRTCCapture.getInstance().muteMicrophone(muteMicrophone);
    }

    public void onToggleSwitchSpeechMusic(boolean isMusic) {
        this.isMusic = isMusic;
        DevicesUtils.setPlayMode(this.isMusic ? DevicesUtils.AudioPlayMode.MUSIC :
                DevicesUtils.AudioPlayMode.SPEEK);
        int mode = DevicesUtils.getAudioMode() == AudioManager.STREAM_MUSIC ? AudioManager.MODE_NORMAL
                : AudioManager.MODE_IN_COMMUNICATION;

        if (audioManager != null && audioManager.getAudioManager() != null) {
            Log.d(TAG, "second setMode =" + mode);
            audioManager.getAudioManager().setMode(mode);
        }
    }

    public boolean onToggleSpeaker(boolean mute) {
        try {
            this.muteSpeaker = mute;
            audioManager.onToggleSpeaker(mute);
        } catch (Exception e) {
            e.printStackTrace();
            FinLog.v(TAG, "message=" + e.getMessage());
        }
        return mute;
    }

    private void rotateScreen(boolean isToLandscape) {
        if (isToLandscape)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    List<String> unGrantedPermissions;

    private void checkPermissions() {
        unGrantedPermissions = new ArrayList();
        for (String permission : MANDATORY_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                unGrantedPermissions.add(permission);
            }
        }
        if (unGrantedPermissions.size() == 0) {//???????????????????????????????????????????????????
            startCall();
        } else {//????????????????????????????????????????????????
            String[] array = new String[unGrantedPermissions.size()];
            ActivityCompat.requestPermissions(this, unGrantedPermissions.toArray(array), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        unGrantedPermissions.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                unGrantedPermissions.add(permissions[i]);
        }
        for (String permission : unGrantedPermissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showToastLengthLong(getString(R.string.PermissionStr) + permission + getString(R.string.plsopenit));
                finish();
            } else ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
        if (unGrantedPermissions.size() == 0) {
            AssetsFilesUtil.putAssetsToSDCard(getApplicationContext(), assetsFile, encryptFilePath);
            startCall();
        }
    }

    private String assetsFile = "EncryptData/00000001.bin";
    private String encryptFilePath = new StringBuffer().append(Environment.getExternalStorageDirectory().toString() + File.separator).append("Blink").append(File.separator).append("EncryptData").toString();
    RongRTCVideoView localSurface;

    private void startCall() {
        try {
            renderViewManager.initViews(this, isObserver);
            if (!isObserver) {
                localSurface = RongRTCEngine.getInstance().createVideoView(getApplicationContext());
                renderViewManager.userJoin(myUserId, RongRTCCapture.getInstance().getTag(), iUserName, isVideoMute ? RongRTCTalkTypeUtil.C_CAMERA : RongRTCTalkTypeUtil.O_CAMERA);
                renderViewManager.setVideoView(true, myUserId, RongRTCCapture.getInstance().getTag(), iUserName, localSurface, isVideoMute ? RongRTCTalkTypeUtil.C_CAMERA : RongRTCTalkTypeUtil.O_CAMERA);
            }

            rongRTCRoom = CenterManager.getInstance().getRongRTCRoom();
            rongRTCRoom.registerEventsListener(CallActivity.this);
            rongRTCRoom.registerStatusReportListener(CallActivity.this);
            rongRTCRoom.registerEglEventsListener(this);
            localUser = rongRTCRoom.getLocalUser();
            renderViewManager.setRongRTCRoom(rongRTCRoom);
            RongRTCCapture.getInstance().setRongRTCVideoView(localSurface);//????????????view
            RongRTCCapture.getInstance().muteLocalVideo(isVideoMute);

            boolean acquisitionMode = SessionManager.getInstance().getBoolean(Consts.ACQUISITION_MODE_KEY, true);
            RongRTCCapture.getInstance().setLocalVideoFrameListener(acquisitionMode,this);

            RongRTCCapture.getInstance().setLocalAudioPCMBufferListener(this);
            RongRTCCapture.getInstance().setRemoteAudioPCMBufferListener(this);

            RongRTCCapture.getInstance().setEnableSpeakerphone(true);
            RongRTCCapture.getInstance().startCameraCapture();
            publishResource();//????????????
            addAllVideoView();
            subscribeAll();

            if (!HeadsetPlugReceiverState) {
                int type = -1;
                if (BluetoothUtil.hasBluetoothA2dpConnected()) {
                    type = 0;
                } else if (BluetoothUtil.isWiredHeadsetOn(CallActivity.this)) {
                    type = 1;
                }
                if (type != -1) {
                    onNotifyHeadsetState(true, type);
                }
            }
            isInRoom = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAllVideoView() {
        Map<String, RongRTCRemoteUser> map = rongRTCRoom.getRemoteUsers();
        if (map != null) {
            for (RongRTCRemoteUser remoteUser : map.values()) {
                addNewRemoteView(remoteUser);//??????view
            }
        }
    }

    private RongRTCLiveInfo liveRoom;
    private void publishResource() {
        if (isObserver) {
            return;
        }
        if (localUser == null) {
            Toast.makeText(CallActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if (RongIMClient.getInstance().getCurrentConnectionStatus() == NETWORK_UNAVAILABLE) {
            Toast.makeText(CallActivity.this, getResources().getString(R.string.Thecurrentnetworkisnotavailable), Toast.LENGTH_SHORT).show();
            return;
        }

        List<RongRTCAVOutputStream> localAvStreams = localUser.getLocalAVStreams();
        if (isVideoMute) {
            for (RongRTCAVOutputStream stream : localAvStreams) {
                if (stream.getMediaType() == cn.rongcloud.rtc.stream.MediaType.VIDEO) {
                    stream.setResourceState(ResourceState.DISABLED);
                }

            }
        }
        if (!mIsLive) {
            localUser.publishDefaultAVStream(new RongRTCResultUICallBack() {
                @Override
                public void onUiSuccess() {
                    FinLog.v(TAG, "publish success()");
                }

                @Override
                public void onUiFailed(RTCErrorCode errorCode) {
                    FinLog.e(TAG, "publish publish Failed()");
                }
            });
            return;
        }

        localUser.publishDefaultLiveAVStream(new RongRTCDataResultCallBack<RongRTCLiveInfo>() {
            @Override
            public void onSuccess(RongRTCLiveInfo room) {
                liveRoom = room;
                //TODO URL??????????????????
                FinLog.d(TAG, "liveUrl::" + room.getLiveUrl());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(LiveDataOperator.ROOM_ID, room.getRoomId());
                    jsonObject.put(LiveDataOperator.ROOM_NAME, room.getUserId());
                    jsonObject.put(LiveDataOperator.LIVE_URL, room.getLiveUrl());
                    jsonObject.put(LiveDataOperator.PUB_ID, rongRTCRoom.getLocalUser().getUserId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LiveDataOperator.getInstance().publish(jsonObject.toString(), new LiveDataOperator.OnResultCallBack() {
                    @Override
                    public void onSuccess(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("???????????????????????????" + result);
                            }
                        });
                    }

                    @Override
                    public void onFailed(final String error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("???????????????????????????" + error);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailed(final RTCErrorCode errorCode) {
                FinLog.e(TAG,"publish publish Failed()");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CallActivity.this, "?????????????????? ???"+errorCode, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    private int getMaxBitRate() {
        int bitRate = 500;
        String maxBitRate = SessionManager.getInstance().getString(SettingActivity.BIT_RATE_MAX, getResources().getString(R.string.def_min_bitrate));
        if (!TextUtils.isEmpty(maxBitRate) && maxBitRate.length() > 4) {
            bitRate = Integer.valueOf(maxBitRate.substring(0, maxBitRate.length() - 4));
            FinLog.v(TAG, "BIT_RATE_MAX=" + bitRate);
        }
        return bitRate;
    }

    private int getMinBitRate() {
        int bitRate = 100;
        String minBitRate = SessionManager.getInstance().getString(SettingActivity.BIT_RATE_MIN, getResources().getString(R.string.def_min_bitrate));
        if (!TextUtils.isEmpty(minBitRate) && minBitRate.length() > 4) {
            bitRate = Integer.valueOf(minBitRate.substring(0, minBitRate.length() - 4));
            FinLog.v(TAG, "BIT_RATE_MIN=" + bitRate);
        }
        return bitRate;
    }

    private RongRTCConfig.RongRTCVideoCodecs getVideoCodec() {
        //set codecs
        String codec = SessionManager.getInstance().getString(SettingActivity.CODECS);
        if (!TextUtils.isEmpty(codec)) {
            if ("VP8".equals(codec)) {
                return RongRTCConfig.RongRTCVideoCodecs.VP8;
            }
        }
        return RongRTCConfig.RongRTCVideoCodecs.H264;
    }

    private String getAudioRecordFilePath() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/blink/audio_recording/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path + System.currentTimeMillis() + ".wav";
    }

    private void onAudioManagerChangedState() {
        // TODO(henrika): disable video if AppRTCAudioManager.AudioDevice.EARPIECE
        // is active.
    }

    // Disconnect from remote resources, dispose of local resources, and exit.

    private void startCalculateNetSpeed() {
        if (networkSpeedHandler == null)
            networkSpeedHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        textViewNetSpeed.setText(getResources().getString(R.string.network_traffic_receive) + msg.getData().getLong("rcv") + "Kbps  " +
                                getResources().getString(R.string.network_traffic_send) + msg.getData().getLong("send") + "Kbps");
                    }
                    super.handleMessage(msg);
                }
            };
//        startTenSecondsTimer();
    }


    private Runnable timeRun = new Runnable() {
        @Override
        public void run() {
            if (waitingTips != null && waitingTips.getVisibility() != View.VISIBLE) {
                updateTimer();
                handler.postDelayed(timeRun, 1000);
            }
        }
    };

    private int time = 0;

    private void updateTimer() {
        time++;
        textViewTime.setText(parseTimeSeconds(time));
    }

    private String getFormatTime(int time) {
        if (time < 10)
            return "00:0" + time;
        else if (time < 60)
            return "00:" + time;
        else if (time % 60 < 10) {
            if (time / 60 < 10) {
                return "0" + time / 60 + ":0" + time % 60;
            } else {
                return time / 60 + ":0" + time % 60;
            }
        } else {
            if (time / 60 < 10) {
                return "0" + time / 60 + ":" + time % 60;
            } else {
                return time / 60 + ":" + time % 60;
            }
        }
    }

    @Override
    public void onRemoteUserPublishResource(RongRTCRemoteUser remoteUser, List<RongRTCAVInputStream> publishResource) {
        FinLog.d("BUGTAGS", "--- onRemoteUserPublishResource ----- remoteUser: " + remoteUser);
        if (remoteUser == null) return;
        alertRemotePublished(remoteUser);
        updateResourceVideoView(remoteUser);


        rongRTCRoom.getRoomAttributes(null, new RongRTCDataResultCallBack<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data) {
                try {
                    //????????????????????????1??????????????????????????????????????????????????????????????????????????????
                    if (mMembersMap == null || mMembersMap.size() == data.size()) {
                        return;
                    }
                    mMembersMap.clear();
                    mMembers.clear();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        JSONObject jsonObject = new JSONObject(entry.getValue());
                        if (entry.getKey().equals(WhiteBoardApi.WHITE_BOARD_KEY)) {
                            whiteBoardRoomInfo = new WhiteBoardRoomInfo(jsonObject.getString("uuid"),
                                    jsonObject.getString("roomToken"));
                            continue;
                        }
                        UserInfo userInfo = new UserInfo();
                        userInfo.userName = jsonObject.getString("userName");
                        userInfo.joinMode = jsonObject.getInt("joinMode");
                        userInfo.userId = jsonObject.getString("userId");
                        userInfo.timestamp = jsonObject.getLong("joinTime");
                        boolean master = jsonObject.optInt("master") == 1;
                        if (master) {
                            adminUserId = userInfo.userId;
                        }

                        if (rongRTCRoom.getRemoteUser(userInfo.userId) == null
                                && !TextUtils.equals(myUserId, userInfo.userId)) {
                            continue;
                        }
                        mMembersMap.put(entry.getKey(), userInfo);

                        MembersDialog.ItemModel model = new MembersDialog.ItemModel();
                        model.mode = mapMode(userInfo.joinMode);
                        model.name = userInfo.userName;
                        model.userId = userInfo.userId;
                        model.joinTime = userInfo.timestamp;
                        mMembers.add(model);

                        List<VideoViewManager.RenderHolder> holders = renderViewManager.getViewHolderByUserId(entry.getKey());
                        for (VideoViewManager.RenderHolder holder : holders) {
                            if (TextUtils.equals(entry.getKey(), myUserId)) {
                                holder.updateUserInfo(getResources().getString(R.string.room_actor_me));
                            } else {
                                holder.updateUserInfo(model.name);
                            }
                        }
                        setWaitingTipsVisiable(mMembers.size() <= 1);
                    }
                    sortRoomMembers();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(RTCErrorCode errorCode) {

            }
        });
    }

    @Override
    public void onRemoteUserAudioStreamMute(RongRTCRemoteUser remoteUser, RongRTCAVInputStream avInputStream, boolean mute) {
        FinLog.d(TAG, "onRemoteUserAudioStreamMute remoteUser: " + remoteUser +" ,  mute :"+mute);
    }

    @Override
    public void onRemoteUserVideoStreamEnabled(RongRTCRemoteUser remoteUser, RongRTCAVInputStream avInputStream, boolean enable) {
        FinLog.d(TAG, "onRemoteUserVideoStreamEnabled remoteUser: " + remoteUser+"  , enable :"+enable);
        if (remoteUser == null || avInputStream == null) {
            return;
        }
        updateVideoView(remoteUser, avInputStream, enable);
    }

    @Override
    public void onRemoteUserUnpublishResource(RongRTCRemoteUser remoteUser, List<RongRTCAVInputStream> unPublishResource) {
        FinLog.d(TAG, "onRemoteUserUnpublishResource remoteUser: " + remoteUser);
        if (unPublishResource != null) {
            for (RongRTCAVInputStream stream : unPublishResource) {
                if (stream.getMediaType().equals(cn.rongcloud.rtc.stream.MediaType.VIDEO)) {
                    renderViewManager.removeVideoView(false, stream.getUserId(), stream.getTag());
                    if (!TextUtils.equals(stream.getTag(), CenterManager.RONG_TAG)) {
                        customVideoEnabled = true;
                    }
                }
            }
        }
    }

    @Override
    public void onUserJoined(RongRTCRemoteUser remoteUser) {
        FinLog.d(TAG,"onUserJoined  remoteUser :"+remoteUser.getUserId());
        //Toast.makeText(CallActivity.this, remoteUser.getUserId() + " " + getResources().getString(R.string.rtc_join_room), Toast.LENGTH_SHORT).show();

//        if (!mMembersMap.containsKey(remoteUser.getUserId())) {//?????????2.0??????????????????????????????room info?????????????????????????????????ItemModel????????????
//            MembersDialog.ItemModel itemModel = new MembersDialog.ItemModel();
//            itemModel.name = "";
//            itemModel.mode = "0";
//            itemModel.userId = remoteUser.getUserId();
//            mMembers.add(0, itemModel);
//        }

        if (mMembers.size() > 1) {
            setWaitingTipsVisiable(false);
        }
        //renderViewManager.userJoin(remoteUser.getUserId(), remoteUser.getUserId(), RongRTCTalkTypeUtil.O_CAMERA);
    }

    @Override
    public void onUserLeft(RongRTCRemoteUser remoteUser) {
        Toast.makeText(CallActivity.this, getUserName(remoteUser.getUserId()) + " " + getResources().getString(R.string.rtc_quit_room), Toast.LENGTH_SHORT).show();
        exitRoom(remoteUser.getUserId());
        clearWhiteBoardInfoIfNeeded();
        if (mMembers.size() <= 1) {
            setWaitingTipsVisiable(true);
        }
    }

    @Override
    public void onUserOffline(RongRTCRemoteUser remoteUser) {
        Toast.makeText(CallActivity.this, getUserName(remoteUser.getUserId()) + " " + getResources().getString(R.string.rtc_user_offline), Toast.LENGTH_SHORT).show();
        exitRoom(remoteUser.getUserId());
        clearWhiteBoardInfoIfNeeded();
        if (remoteUser.getUserId().equals(adminUserId)) {
            adminUserId = null;
        }
        if (mMembers.size() <= 1) {
            setWaitingTipsVisiable(true);
        }
    }

    private String getUserName(String userId) {
        if (TextUtils.isEmpty(userId)) return userId;
        UserInfo userInfo = mMembersMap.get(userId);
        if (userInfo == null) return userId;
        return userInfo.userName;
    }

    @Override
    public void onVideoTrackAdd(String userId, String tag) {
        Log.i(TAG, "onVideoTrackAdd() userId: " + userId + " ,tag = " + tag);
        if(isShowAutoTest){ //???????????????????????????
            renderViewManager.onTrackadd(userId, tag);
        }
        if (!TextUtils.equals(tag, CenterManager.RONG_TAG)) {
            customVideoEnabled = false;
        }
    }

    @Override
    public void onFirstFrameDraw(String userId, String tag) {
        Log.i(TAG, "onFirstFrameDraw() userId: " + userId + " ,tag = " + tag);
        if(isShowAutoTest){
            renderViewManager.onFirstFrameDraw(userId, tag);
        }
    }

    @Override
    public void onLeaveRoom() {
        final PromptDialog dialog = PromptDialog.newInstance(this, getString(R.string.rtc_status_im_error));
        dialog.setPromptButtonClickedListener(new PromptDialog.OnPromptButtonClickedListener() {
            @Override
            public void onPositiveButtonClicked() {
                finish();
            }

            @Override
            public void onNegativeButtonClicked() {

            }
        });
        dialog.disableCancel();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onReceiveMessage(io.rong.imlib.model.Message message) {
        MessageContent messageContent = message.getContent();
        FinLog.i(TAG,"onReceiveMessage()->"+ messageContent);
        if (messageContent instanceof RoomInfoMessage) {
            RoomInfoMessage roomInfoMessage = (RoomInfoMessage) messageContent;
            MembersDialog.ItemModel itemModel = new MembersDialog.ItemModel();
            itemModel.name = roomInfoMessage.getUserName();
            itemModel.mode = mapMode(roomInfoMessage.getJoinMode());
            itemModel.userId = roomInfoMessage.getUserId();
            itemModel.joinTime = roomInfoMessage.getTimeStamp();
            if (!mMembersMap.containsKey(itemModel.userId)) {
                Toast.makeText(CallActivity.this, itemModel.name + " " + getResources().getString(R.string.rtc_join_room), Toast.LENGTH_SHORT).show();
                mMembers.add(0, itemModel);
                sortRoomMembers();
            } else {
                for (MembersDialog.ItemModel member : mMembers) {
                    if (TextUtils.equals(member.userId,itemModel.userId)){
                        member.mode = itemModel.mode;
                        break;
                    }
                }
                if (roomInfoMessage.isMaster() && !itemModel.userId.equals(adminUserId)) {
                    adminUserId = itemModel.userId;
                    if (itemModel.userId.equals(myUserId)) {
                        Toast.makeText(CallActivity.this, getResources().getString(R.string.member_operate_admin_me), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CallActivity.this, itemModel.name + " " + getResources().getString(R.string.member_operate_admin_new), Toast.LENGTH_SHORT).show();
                    }
                    updateMembersDialog();
                }
//                return;
            }

            UserInfo userInfo = new UserInfo();
            userInfo.userId = roomInfoMessage.getUserId();
            userInfo.userName = roomInfoMessage.getUserName();
            userInfo.joinMode = roomInfoMessage.getJoinMode();
            userInfo.timestamp = roomInfoMessage.getTimeStamp();
            mMembersMap.put(roomInfoMessage.getUserId(), userInfo);

            List<VideoViewManager.RenderHolder> holders = renderViewManager.getViewHolderByUserId(roomInfoMessage.getUserId());
            for (VideoViewManager.RenderHolder holder : holders) {
                holder.updateUserInfo(roomInfoMessage.getUserName());
                if (roomInfoMessage.getJoinMode() == RoomInfoMessage.JoinMode.AUDIO){
                    holder.CameraSwitch(RongRTCTalkTypeUtil.C_CAMERA);
                }else if (roomInfoMessage.getJoinMode() == RoomInfoMessage.JoinMode.AUDIO_VIDEO){
                    holder.CameraSwitch(RongRTCTalkTypeUtil.O_CAMERA);
                }else if (roomInfoMessage.getJoinMode() == RoomInfoMessage.JoinMode.OBSERVER){
                    renderViewManager.removeVideoView(roomInfoMessage.getUserId());
                }
            }

            updateMembersDialog();
            if (mMembers.size() > 1) {
                setWaitingTipsVisiable(false);
            }
        } else if (messageContent instanceof WhiteBoardInfoMessage) {
            WhiteBoardInfoMessage whiteBoardInfoMessage = (WhiteBoardInfoMessage) messageContent;
            whiteBoardRoomInfo = new WhiteBoardRoomInfo(whiteBoardInfoMessage.getUuid(), whiteBoardInfoMessage.getRoomToken());
        } else if (messageContent instanceof RoomKickOffMessage) {
            RoomKickOffMessage kickOffMessage = (RoomKickOffMessage) messageContent;
            if (myUserId.equals(kickOffMessage.getUserId())) {
                FinLog.i(TAG,"kickOffMessage-intendToLeave");
                intendToLeave(false);
                EventBus.getDefault().post(new KickEvent(roomId));
            }
        }
    }

    private String mapMode(int mode) {
        if (mode == RoomInfoMessage.JoinMode.AUDIO) {
            return getString(R.string.mode_audio);
        } else if (mode == RoomInfoMessage.JoinMode.AUDIO_VIDEO) {
            return getString(R.string.mode_audio_video);
        } else if (mode == RoomInfoMessage.JoinMode.OBSERVER) {
            return getString(R.string.mode_observer);
        }
        return "";
    }

    @Override
    public void onAudioReceivedLevel(HashMap<String, String> audioLevel) {
        try {
            Iterator iter = audioLevel.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = "";
                int val = 0;
                if (null != entry.getKey()) {
                    key = entry.getKey().toString();
                }
                if (null != entry.getValue()) {
                    val = Integer.valueOf(entry.getValue().toString());
                }
                audiolevel(val, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAudioInputLevel(String audioLevel) {
        if (localUser == null)
            return;
        int val = 0;
        try {
            val = TextUtils.isEmpty(audioLevel) ? 0 : Integer.valueOf(audioLevel);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        audiolevel(val, localUser.getDefaultAudioStream().getMediaId());
    }

    @Override
    public void onConnectionStats(final StatusReport statusReport) {
        if (mMembers != null && mMembers.size() > 1) {
            updateNetworkSpeedInfo(statusReport);
        } else {
            initUIForWaitingStatus();
        }

        unstableNetworkToast(statusReport);

        //??????Debug???????????????????????????????????????
        if (renderViewManager == null || !BuildConfig.DEBUG)
            return;
        parseToList(statusReport);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateDebugInfo(statusReport);
            }
        });
    }

    /**
     * ????????????????????????????????????
     * @param statusReport
     */
    private void unstableNetworkToast(StatusReport statusReport) {
        if (statusReportList != null && statusReportList.size() < 10) {
            statusReportList.add(statusReport);
            return;
        }

        Map<String, Map<String, Integer>> userStreamLostRate = new HashMap<>();

        for (StatusReport item : statusReportList) {
            // ????????????????????????????????????
            for (Map.Entry<String, StatusBean> entry : item.statusAudioSends.entrySet()) {
                String localUid = entry.getValue().uid;
                String streamId = entry.getValue().id;
                if (!userStreamLostRate.containsKey(localUid)) {
                    userStreamLostRate.put(localUid, new HashMap<String, Integer>());
                }
                if (entry.getValue().packetLostRate > 30) {
                    if (!userStreamLostRate.get(localUid).containsKey(streamId)) {
                        userStreamLostRate.get(localUid).put(streamId, 0);
                    }
                    userStreamLostRate.get(localUid).put(streamId, userStreamLostRate.get(localUid).get(streamId) + 1);
                }
            }

            // ????????????????????????????????????
            for (Map.Entry<String, StatusBean> entry : item.statusVideoSends.entrySet()) {
                String localUid = entry.getValue().uid;
                String streamId = entry.getValue().id;
                if (!userStreamLostRate.containsKey(localUid)) {
                    userStreamLostRate.put(localUid, new HashMap<String, Integer>());
                }
                if (entry.getValue().packetLostRate > 15) {
                    if (!userStreamLostRate.get(localUid).containsKey(streamId)) {
                        userStreamLostRate.get(localUid).put(streamId, 0);
                    }
                    userStreamLostRate.get(localUid).put(streamId, userStreamLostRate.get(localUid).get(streamId) + 1);
                }
            }

            // ??????????????????????????????
            for (Map.Entry<String, StatusBean> entry : item.statusAudioRcvs.entrySet()) {
                String remoteUid = entry.getValue().uid;
                String streamId = entry.getValue().id;
                if (!userStreamLostRate.containsKey(remoteUid)) {
                    userStreamLostRate.put(remoteUid, new HashMap<String, Integer>());
                }
                if (entry.getValue().packetLostRate > 30) {
                    if (!userStreamLostRate.get(remoteUid).containsKey(streamId)) {
                        userStreamLostRate.get(remoteUid).put(streamId, 0);
                    }
                    userStreamLostRate.get(remoteUid).put(streamId, userStreamLostRate.get(remoteUid).get(streamId) + 1);
                }
            }

            // ??????????????????????????????
            for (Map.Entry<String, StatusBean> entry : item.statusVideoRcvs.entrySet()) {
                String remoteUid = entry.getValue().uid;
                String streamId = entry.getValue().id;
                if (!userStreamLostRate.containsKey(remoteUid)) {
                    userStreamLostRate.put(remoteUid, new HashMap<String, Integer>());
                }
                if (entry.getValue().packetLostRate > 15) {
                    if (!userStreamLostRate.get(remoteUid).containsKey(streamId)) {
                        userStreamLostRate.get(remoteUid).put(streamId, 0);
                    }
                    userStreamLostRate.get(remoteUid).put(streamId, userStreamLostRate.get(remoteUid).get(streamId) + 1);
                }
            }
        }
        statusReportList.clear();

        String networkToast = "";
        boolean shouldToast = false;
        for (Map.Entry<String, Map<String, Integer>> entry : userStreamLostRate.entrySet()) {
            String userId = entry.getKey();
            for (Map.Entry<String, Integer> streamEntry : entry.getValue().entrySet()) {
                if (streamEntry.getValue() > 5) {
                    if (mMembersMap != null && mMembersMap.containsKey(userId) && !networkToast.contains(mMembersMap.get(userId).userName)) {
                        if (shouldToast) {
                            networkToast += ", ";
                        }
                        networkToast += mMembersMap.get(userId).userName;
                        shouldToast = true;
                    }
                }
            }
        }

        networkToast = String.format(getString(R.string.network_tip), networkToast);

        final boolean finalShouldToast = shouldToast;
        final String finalNetworkToast = networkToast;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (finalShouldToast) {
                    layoutNetworkStatusInfo.setVisibility(View.VISIBLE);
                    txtViewNetworkStatusInfo.setText(finalNetworkToast);
                    txtViewNetworkStatusInfo.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutNetworkStatusInfo.setVisibility(View.GONE);
                            txtViewNetworkStatusInfo.setVisibility(View.GONE);
                        }
                    }, 3000);
                } else {
                    layoutNetworkStatusInfo.setVisibility(View.GONE);
                    txtViewNetworkStatusInfo.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * ??????>15%????????????????????? {@link #timerPeriod}???????????????????????????
     * @param lossRate
     */
    private void networkObserverTimer(final float lossRate) {
        if (lossRate < 15) {
            if (networkObserverTimer != null) {
                networkObserverTimer.cancel();
                networkObserverTimer = null;
            }
        } else {
            if (networkObserverTimer == null) {
                networkObserverTimer = new Timer("NetWorkObserverTimer");
                networkObserverTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        playTipsSound(lossRate);
                    }
                }, 0, timerPeriod);
            }
        }
    }

    private void playTipsSound(final float lossRate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (lossRate >= 15) {
                    FinLog.v(TAG,"loss rate > 15, play Sound !");
                    if (mSoundPool != null) {
                        mSoundPool.release();
                    }
                    if (playSound) {
                        playSound = false;
                        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                        mSoundPool.load(CallActivity.this, R.raw.voip_network_error_sound, 0);
                        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                soundPool.play(sampleId, 1F, 1F, 0, 0, 1F);
                            }
                        });
                        String toastInfo = CallActivity.this.getString(R.string.rtc_unstable_call_connection);
                        Toast.makeText(CallActivity.this, toastInfo, Toast.LENGTH_SHORT).show();
                        playSound = true;
                    }
                }
            }
        });
    }

    private void updateResourceVideoView(RongRTCRemoteUser remoteUser) {
        for (RongRTCAVInputStream rongRTCAVOutputStream : remoteUser.getRemoteAVStreams()) {
            ResourceState state = rongRTCAVOutputStream.getResourceState();
            if (rongRTCAVOutputStream.getMediaType() == cn.rongcloud.rtc.stream.MediaType.VIDEO && renderViewManager != null) {
                FinLog.v(TAG, "updateResourceVideoView userId = " + remoteUser.getUserId() + " state = " + state);
                renderViewManager.updateTalkType(remoteUser.getUserId(),rongRTCAVOutputStream.getTag(), state == ResourceState.DISABLED ? RongRTCTalkTypeUtil.C_CAMERA : RongRTCTalkTypeUtil.O_CAMERA);
                rongRTCAVOutputStream.setVideoDisplayRenderer(rongRTCAVOutputStream.getResourceState() == ResourceState.NORMAL);
            }
        }
    }

    private void updateVideoView(RongRTCRemoteUser remoteUser, RongRTCAVInputStream rongRTCAVInputStream, boolean enable) {
        if (renderViewManager != null) {
            FinLog.v(TAG, "updateVideoView userId = " + remoteUser.getUserId() + " state = " + enable);
            renderViewManager.updateTalkType(remoteUser.getUserId(), rongRTCAVInputStream.getTag(), enable ? RongRTCTalkTypeUtil.O_CAMERA : RongRTCTalkTypeUtil.C_CAMERA);
            rongRTCAVInputStream.setVideoDisplayRenderer(enable);
        }
    }

    private void alertRemotePublished(final RongRTCRemoteUser remoteUser) {
        Log.i(TAG, "alertRemotePublished() start");
        addNewRemoteView(remoteUser);
        remoteUser.subscribeAVStream(remoteUser.getRemoteAVStreams(), new RongRTCResultUICallBack() {
            @Override
            public void onUiSuccess() {

            }

            @Override
            public void onUiFailed(RTCErrorCode errorCode) {

            }
        });

    }

    private void subscribeAll() {
        if (rongRTCRoom == null || rongRTCRoom.getRemoteUsers() == null) {
            return;
        }

        for (final RongRTCRemoteUser remoteUser : rongRTCRoom.getRemoteUsers().values()) {
            if (remoteUser.getRemoteAVStreams() != null) {
                remoteUser.subscribeAVStream(remoteUser.getRemoteAVStreams(), new RongRTCResultUICallBack() {
                    @Override
                    public void onUiSuccess() {
                        updateResourceVideoView(remoteUser);
                    }

                    @Override
                    public void onUiFailed(RTCErrorCode errorCode) {

                    }
                });
            }

        }
    }

    private void addNewRemoteView(RongRTCRemoteUser remoteUser) {
        List<RongRTCAVInputStream> videoStreamList = new ArrayList<>();
        List<RongRTCAVInputStream> remoteAVStreams = remoteUser.getRemoteAVStreams();
        RongRTCAVInputStream audioStream = null;
        // ?????????????????????????????????????????????
        boolean cameraOpened = false;
        for (RongRTCAVInputStream inputStream : remoteAVStreams) {
            if (inputStream.getMediaType() == cn.rongcloud.rtc.stream.MediaType.VIDEO) {
                videoStreamList.add(inputStream);
                if (TextUtils.equals(inputStream.getTag(), CenterManager.RONG_TAG)) {
                    cameraOpened = true;
                }
            }else if (inputStream.getMediaType() == cn.rongcloud.rtc.stream.MediaType.AUDIO){
                if (inputStream.getRongRTCVideoView() != null && cameraOpened){
                    renderViewManager.removeVideoView(remoteUser.getUserId());
                }
                if (CenterManager.RONG_TAG.equals(inputStream.getTag())){//???????????????????????????????????????????????????????????????UI??????
                    audioStream = inputStream;
                }
            }
        }
        //????????????????????????????????????????????????
        if (videoStreamList.isEmpty() && audioStream != null){
            videoStreamList.add(audioStream);
        }

        if (videoStreamList.size() > 0) {
            for (RongRTCAVInputStream videoStream : videoStreamList) {
                if (videoStream != null && videoStream.getRongRTCVideoView() == null) {
                    FinLog.v(TAG, "addNewRemoteView");
                    if (!renderViewManager.hasConnectedUser()) {
                        startCalculateNetSpeed();
                    }
                    UserInfo userInfo = mMembersMap.get(remoteUser.getUserId());
                    String userName = "";
                    if (userInfo != null) {
                        userName = userInfo.userName;
                    }
                    String talkType = videoStream.getMediaType() == cn.rongcloud.rtc.stream.MediaType.AUDIO ? RongRTCTalkTypeUtil.C_CAMERA : RongRTCTalkTypeUtil.O_CAMERA;
                    renderViewManager.userJoin(remoteUser.getUserId(), videoStream.getTag(), userName, talkType);
                    RongRTCVideoView remoteView = RongRTCEngine.getInstance().createVideoView(CallActivity.this.getApplicationContext());
                    renderViewManager.setVideoView(false, videoStream.getUserId(), videoStream.getTag(), remoteUser.getUserId(), remoteView, talkType);
                    videoStream.setRongRTCVideoView(remoteView);
                }
            }
        }
    }

    private void updateNetworkSpeedInfo(StatusReport statusReport) {
        if (networkSpeedHandler != null) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = 1;
            bundle.putLong("send", statusReport.bitRateSend);
            bundle.putLong("rcv", statusReport.bitRateRcv);
            message.setData(bundle);
            networkSpeedHandler.sendMessage(message);
        }
    }

    private void updateDebugInfo(StatusReport statusReport) {
        biteRateSendView.setText(statusReport.bitRateSend + "");
        biteRateRcvView.setText(statusReport.bitRateRcv + "");
        rttSendView.setText(statusReport.rtt + "");
        debugInfoAdapter.setStatusBeanList(statusBeanList);
        debugInfoAdapter.notifyDataSetChanged();
    }

    List<StatusBean> statusBeanList = new ArrayList<>();

    private void parseToList(StatusReport statusReport) {
        statusBeanList.clear();
        for (Map.Entry<String, StatusBean> entry : statusReport.statusVideoRcvs.entrySet()) {
            statusBeanList.add(entry.getValue());
        }
        for (Map.Entry<String, StatusBean> entry : statusReport.statusVideoSends.entrySet()) {
            statusBeanList.add(entry.getValue());
        }

        for (Map.Entry<String, StatusBean> entry : statusReport.statusAudioSends.entrySet()) {
            statusBeanList.add(entry.getValue());
        }

        for (Map.Entry<String, StatusBean> entry : statusReport.statusAudioRcvs.entrySet()) {
            statusBeanList.add(entry.getValue());
        }
    }

    /**
     * Initialize the UI to "waiting user join" IMConnectionStatus
     */
    private void initUIForWaitingStatus() {
        if (time != 0) {
            textViewTime.setText(getResources().getText(R.string.connection_duration));
            textViewNetSpeed.setText(getResources().getText(R.string.network_traffic));
        }
        time = 0;
    }

    private void disconnect() {
        isConnected = false;
        LoadDialog.show(CallActivity.this);
        if (rongRTCRoom != null) {
            rongRTCRoom.deleteRoomAttributes(Arrays.asList(myUserId), null, null);
            deleteRTCWhiteBoardAttribute();
        }
        RongRTCEngine.getInstance().quitRoom(roomId, new RongRTCResultUICallBack() {
            @Override
            public void onUiSuccess() {
                FinLog.i(TAG, "quitRoom()->onUiSuccess");
                isInRoom = false;
                if (!kicked) {
                    Toast.makeText(CallActivity.this, getResources().getString(R.string.quit_room_success), Toast.LENGTH_SHORT).show();
                }

                if (audioManager != null) {
                    audioManager.close();
                    audioManager = null;
                }
                LoadDialog.dismiss(CallActivity.this);
                finish();
            }

            @Override
            public void onUiFailed(RTCErrorCode errorCode) {
                FinLog.i(TAG,"quitRoom()->onUiFailed : "+errorCode);
                if (audioManager != null) {
                    audioManager.close();
                    audioManager = null;
                }
                LoadDialog.dismiss(CallActivity.this);
                finish();
            }
        });
    }

    private Runnable memoryRunnable = new Runnable() {
        @Override
        public void run() {
            getSystemMemory();
            if (handler != null)
                handler.postDelayed(memoryRunnable, 1000);
        }
    };

    /**
     * @param type true:?????????  false?????????
     * @return
     */
    private boolean addActionState(int type, String hostUid, String userid) {
        if (null == stateMap) {
            stateMap = new LinkedHashMap<>();
        }
        boolean state = false;
        if (stateMap.containsKey(type)) {
            state = true;
        } else {
            ActionState bean = null;
            if (stateMap.size() > 0) {//??????????????? ??????key ???????????????
                bean = new ActionState(type, hostUid, userid);
                stateMap.put(type, bean);
                state = true;
            } else {  //?????????????????? ?????? ?????????????????????????????????
                bean = new ActionState(type, hostUid, userid);
                stateMap.put(type, bean);
                state = false;
            }
        }
        return state;
    }

    private void clearState() {
        if (null != stateMap && stateMap.size() > 0) {
            stateMap.clear();
        }
    }

    private void getSystemMemory() {
        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        FinLog.e(TAG, "max Memory:" + Long.toString(maxMemory / (1024 * 1024)));
        FinLog.e(TAG, "free Memory:" + rt.freeMemory() / (1024 * 1024) + "m");
        FinLog.e(TAG, "total Memory:" + rt.totalMemory() / (1024 * 1024) + "m");
        FinLog.e(TAG, "?????????????????????Memory?????????" + info.lowMemory);
        FinLog.e(TAG, "???????????????Memory??????" + (info.threshold >> 10) / 1024 + "m???????????????????????????");
    }

    public void destroyWebView(WebView mWebView) {
        if (mWebView != null) {
            try {
                ViewParent parent = mWebView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mWebView);
                }
                mWebView.stopLoading();
                mWebView.getSettings().setJavaScriptEnabled(false);
                mWebView.clearHistory();
                mWebView.clearView();
                mWebView.removeAllViews();

                mWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String blinkTalkType(boolean isOpen, RongRTCDeviceType dType) {
        String talkType = "";
        if (isOpen) {
            if (dType == RongRTCDeviceType.Camera) {
                talkType = RongRTCTalkTypeUtil.O_CAMERA;
            } else if (dType == RongRTCDeviceType.Microphone) {
                talkType = RongRTCTalkTypeUtil.O_MICROPHONE;
            } else if (dType == RongRTCDeviceType.CameraAndMicrophone) {
                talkType = RongRTCTalkTypeUtil.O_CM;
            }
        } else {//
            if (dType == RongRTCDeviceType.Camera) {
                talkType = RongRTCTalkTypeUtil.C_CAMERA;
            } else if (dType == RongRTCDeviceType.Microphone) {
                talkType = RongRTCTalkTypeUtil.C_MICROPHONE;
            } else if (dType == RongRTCDeviceType.CameraAndMicrophone) {
                talkType = RongRTCTalkTypeUtil.C_CM;
            }
        }
        return talkType;
    }

    private void showToastLengthLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * ???????????????
     *
     * @param config return true:??????
     */
    private boolean screenCofig(Configuration config) {
        boolean screen = false;//????????????
        try {
            Configuration configuration = null;
            if (config == null) {
                configuration = this.getResources().getConfiguration();
            } else {
                configuration = config;
            }
            int ori = configuration.orientation;
            if (ori == configuration.ORIENTATION_LANDSCAPE) {
                screen = true;
            } else if (ori == configuration.ORIENTATION_PORTRAIT) {
                screen = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screen;
    }

    public Boolean isSharing(String userid) {
        if (sharingMap.size() == 0) {
            return false;
        }
        if (sharingMap.containsKey(userid)) {
            return sharingMap.get(userid);
        } else {
            return false;
        }
    }

    private void exitRoom(String userId) {
        sharingMap.remove(userId);
        renderViewManager.delSelect(userId);
        //
        renderViewManager.removeVideoView(userId);
        if (!renderViewManager.hasConnectedUser()) {//????????????,?????????
            initUIForWaitingStatus();
        }
        mMembersMap.remove(userId);
        for (int i = mMembers.size() - 1; i >= 0; --i) {
            MembersDialog.ItemModel model = mMembers.get(i);
            if (TextUtils.equals(model.userId, userId)) {
                mMembers.remove(i);
                break;
            }
        }
        updateMembersDialog();
    }

    /*--------------------------------------------------------------------------???????????????---------------------------------------------------------------------------*/

    private Map<String, ResolutionInfo> changeResolutionMap = null;
    private String[] resolution;

//    private void setChangeResolutionMap() {
//        ResolutionInfo info = null;
//        changeResolutionMap = new HashMap<>();
//        String key = "";
//        resolution = new String[]{CR_144x256, CR_240x320, CR_368x480, CR_368x640, CR_480x640, CR_480x720, CR_720x1280, CR_1080x1920};
//        try {
//            for (int v = 0; v < resolution.length; v++) {
//                key = resolution[v];
//                info = new ResolutionInfo(key, v);
//                changeResolutionMap.put(key, info);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // String fpsStr = SessionManager.getInstance(this).getString(SettingActivity.FPS);

//    private void changeVideoSize(String action) {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(RongRTCEngine.getInstance().getRTCConfig().getVideoWidth());
//        stringBuffer.append("x").append(RongRTCEngine.getInstance().getRTCConfig().getVideoHeight());
//        String resolutionStr = stringBuffer.toString();
//        int index = -1;
//
//        try {
//            if (changeResolutionMap.containsKey(resolutionStr)) {
//                index = changeResolutionMap.get(resolutionStr).getIndex();
//            }
//            if (action.equals("down")) {
//                if (index != 0) {
//                    String str = resolution[index - 1];
//                    RongRTCConfig.RongRTCVideoProfile profile = selectiveResolution(str, "15");
//                    RongRTCEngine.getInstance().changeVideoSize(profile);
//                } else {
//                    Toast.makeText(CallActivity.this, R.string.resolutionmunimum, Toast.LENGTH_SHORT).show();
//                }
//            } else if (action.equals("up")) {
//                if (index != 7) {
//                    String str = resolution[index + 1];
//                    RongRTCConfig.RongRTCVideoProfile profile = selectiveResolution(str, "30");
//                    RongRTCEngine.getInstance().changeVideoSize(profile);
//                } else {
//                    Toast.makeText(CallActivity.this, R.string.resolutionhighest, Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            FinLog.v(TAG, "error???" + e.getMessage());
//        }
//    }

    /*--------------------------------------------------------------------------AudioLevel---------------------------------------------------------------------------*/

    private void audiolevel(final int val, final String key) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != renderViewManager && null != renderViewManager.connetedRemoteRenders &&
                        renderViewManager.connetedRemoteRenders.containsKey(key)) {
                    if (val > 0) {
                        if (key.equals(RongIMClient.getInstance().getCurrentUserId()) && muteMicrophone) {
                            renderViewManager.connetedRemoteRenders.get(key).coverView.closeAudioLevel();
                        } else {
                            renderViewManager.connetedRemoteRenders.get(key).coverView.showAudioLevel();
                        }
                    } else {
                        renderViewManager.connetedRemoteRenders.get(key).coverView.closeAudioLevel();
                    }
                }
            }
        });
    }

    private void showPopupWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            return;
        }
        boolean screenConfig = screenCofig(null);
        WindowManager wm = (WindowManager) this.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int screenHeight=wm.getDefaultDisplay().getHeight();
        int xoff = screenWidth - sideBarWidth - dip2px(CallActivity.this, 80);
        int yoff = 0;
//        int base = screenHeight < screenWidth ? screenHeight : screenWidth;

        View view = LayoutInflater.from(CallActivity.this).inflate(R.layout.layout_viewing_pattern, null);
        final TextView tv_smooth = (TextView) view.findViewById(R.id.tv_smooth);
        final TextView tv_highresolution = (TextView) view.findViewById(R.id.tv_highresolution);
        if (SessionManager.getInstance().contains("VideoModeKey")) {
            String videoMode = SessionManager.getInstance().getString("VideoModeKey");
            if (!TextUtils.isEmpty(videoMode)) {
                if (videoMode.equals("smooth")) {
                    tv_smooth.setTextColor(getResources().getColor(R.color.blink_yellow));
                    tv_highresolution.setTextColor(Color.WHITE);
//                    sideBar.setVideoModeBtnText("??????");
                } else if (videoMode.equals("highresolution")) {
                    tv_smooth.setTextColor(Color.WHITE);
//                    sideBar.setVideoModeBtnText("??????");
                    tv_highresolution.setTextColor(getResources().getColor(R.color.blink_yellow));
                }
            }
        }
        LinearLayout linear_smooth = (LinearLayout) view.findViewById(R.id.linear_smooth);
        LinearLayout linear_highresolution = (LinearLayout) view.findViewById(R.id.linear_highresolution);
        linear_smooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RongRTCEngine.getInstance().setVideoMode(TEnumVideoMode.VideoModeSmooth);
                SessionManager.getInstance().put("VideoModeKey", "smooth");
                tv_smooth.setTextColor(getResources().getColor(R.color.blink_yellow));
                tv_highresolution.setTextColor(Color.WHITE);
//                changeVideoSize("down");
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        linear_highresolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RongRTCEngine.getInstance().setVideoMode(TEnumVideoMode.VideoModeHighresolution);
                SessionManager.getInstance().put("VideoModeKey", "highresolution");
                tv_smooth.setTextColor(Color.WHITE);
                tv_highresolution.setTextColor(getResources().getColor(R.color.blink_yellow));
//                changeVideoSize("up");
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        if (popupWindow == null) {
            popupWindow = new RongRTCPopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        yoff = dip2px(CallActivity.this, 92);//36+16+view.getH
        if (screenConfig) {
            xoff = sideBarWidth;
            popupWindow.showAtLocation(scrollView, Gravity.RIGHT, xoff, -yoff);
        } else {
            popupWindow.showAtLocation(iv_modeSelect, Gravity.LEFT, xoff, -yoff);
        }
    }

    /**
     * ???????????????????????????????????????????????????
     */
    private void initRemoteScrollView() {
        if (screenCofig(null)) {
            horizontalScreenViewInit();
        } else {
            verticalScreenViewInit();
        }
    }

    /**
     * ??????View??????
     */
    private void horizontalScreenViewInit() {
        try {
            RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) rel_sv.getLayoutParams();
            lp3.addRule(RelativeLayout.BELOW, 0);

            WindowManager wm = (WindowManager) this.getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            int screenHeight = wm.getDefaultDisplay().getHeight();
            int width = (screenHeight < screenWidth ? screenHeight : screenWidth) / 3;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) btnCloseCamera.getLayoutParams();
            layoutParams.setMargins(width, 0, 0, dip2px(CallActivity.this, 16));
            btnCloseCamera.setLayoutParams(layoutParams);
            ViewGroup.MarginLayoutParams mutelayoutParams = (ViewGroup.MarginLayoutParams) btnMuteMic.getLayoutParams();
            mutelayoutParams.setMargins(0, 0, width, dip2px(CallActivity.this, 16));
            btnMuteMic.setLayoutParams(mutelayoutParams);
            //
            if (null != horizontalScrollView) {
                if (horizontalScrollView.getChildCount() > 0) {
                    horizontalScrollView.removeAllViews();
                }
                horizontalScrollView.setVisibility(View.GONE);
            }
            if (null != scrollView) {
                if (scrollView.getChildCount() > 0) {
                    scrollView.removeAllViews();
                }
                scrollView.setVisibility(View.VISIBLE);
                call_reder_container.setOrientation(LinearLayout.VERTICAL);
                scrollView.addView(call_reder_container);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????View??????
     */
    private void verticalScreenViewInit() {
        initBottomBtn();
        //
        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) rel_sv.getLayoutParams();
        lp3.addRule(RelativeLayout.BELOW, titleContainer.getId());

        if (null != scrollView) {
            if (scrollView.getChildCount() > 0) {
                scrollView.removeAllViews();
            }
            scrollView.setVisibility(View.GONE);
        }
        if (null != horizontalScrollView) {
            if (horizontalScrollView.getChildCount() > 0) {
                horizontalScrollView.removeAllViews();
            }
            horizontalScrollView.addView(call_reder_container);
            horizontalScrollView.setVisibility(View.VISIBLE);
            call_reder_container.setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox;
        if (v.getId() == R.id.btn_mcu){
            if (liveRoom != null){
                McuConfigDialog.showDialog(this,liveRoom.getConfigUrl());
            }else {
                showToast("liveRoom is Null");
            }
        }else if (v.getId() == R.id.call_btn_hangup){
            intendToLeave(true);
        }else if (v.getId() == R.id.menu_switch){
            RongRTCCapture.getInstance().switchCamera(new CameraVideoCapturer.CameraSwitchHandler() {
                @Override
                public void onCameraSwitchDone(boolean isFrontCamera) {
                    if (mWaterFilter != null){
                        mWaterFilter.angleChange(isFrontCamera);
                    }
                }

                @Override
                public void onCameraSwitchError(String errorDescription) {
                }
            });
        }else if (v.getId() == R.id.menu_close){
            checkBox = (CheckBox) v;
            if (canOnlyPublishAudio || isObserver) {
                checkBox.setChecked(true);
            } else {
                onCameraClose(checkBox.isChecked());
            }
        }else if (v.getId() == R.id.menu_mute_mic){
            checkBox = (CheckBox) v;
            FinLog.i(TAG,"isMute : " +checkBox.isChecked());
            onToggleMic(checkBox.isChecked());
        }else if (v.getId() == R.id.menu_switch_speech_music){
            checkBox = (CheckBox) v;
            Log.d(TAG, "setMode check " + checkBox.isChecked());
            onToggleSwitchSpeechMusic(checkBox.isChecked());
        }else if (v.getId() == R.id.menu_mute_speaker){
            //????????????????????????????????????????????????????????????????????????
            if (Utils.isFastDoubleClick()) {
                showToast(R.string.rtc_processing);
                return;
            }
            destroyPopupWindow();
            checkBox = (CheckBox) v;
            RongRTCCapture.getInstance().setEnableSpeakerphone(!checkBox.isChecked());
            onToggleSpeaker(checkBox.isChecked());
        }else if (v.getId() == R.id.menu_request_to_normal){
            destroyPopupWindow();
        }else if (v.getId() == R.id.call_waiting_tips){
            toggleActionButtons(buttonHangUp.getVisibility() == View.VISIBLE);
        }else if (v.getId() == R.id.menu_up){
            destroyPopupWindow();
//                changeVideoSize("up");
        }else if (v.getId() == R.id.menu_down){
            destroyPopupWindow();
//                changeVideoSize("down");
        }else if (v.getId() == R.id.menu_members){
            showMembersDialog();
        }else if (v.getId() == R.id.menu_btn_call_menu_settings){
            showSettingsDialog();
        }
//            case R.id.menu_whiteboard:
//                if (RongIMClient.getInstance().getCurrentConnectionStatus() == NETWORK_UNAVAILABLE) {
//                    Toast.makeText(CallActivity.this, getResources().getString(R.string.Thecurrentnetworkisnotavailable), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                destroyPopupWindow();
//                checkBox = (CheckBox) v;
//                if (checkBox.isChecked()) {
//                    if (whiteBoardRoomInfo != null) {
//                        progressDialog.show();
//                        updateCallActionsWithWhiteBoard(true);
//                        joinWhiteBoardRoom(whiteBoardRoomInfo.getUuid(), whiteBoardRoomInfo.getRoomToken());
//                    } else {
//                        if (isObserver) {
//                            showToast(getString(R.string.white_board_room_not_exist));
//                            return;
//                        }
//                        progressDialog.show();
//                        updateCallActionsWithWhiteBoard(true);
//                        WhiteBoardApi.createRoom(roomId, 100, new HttpClient.ResultCallback() {
//                            @Override
//                            public void onResponse(String result) {
//                                if (TextUtils.isEmpty(result)) {
//                                    showToast("json is Null");
//                                    return;
//                                }
//                                JSONObject msg;
//                                try {
//                                    msg = new JSONObject(result);
//                                    JSONObject room = msg.getJSONObject("msg").getJSONObject("room");
//                                    final String uuid = room.getString("uuid");
//                                    String roomToken = msg.getJSONObject("msg").getString("roomToken");
//                                    setRoomWhiteBoardInfo(uuid, roomToken);
//                                    joinWhiteBoardRoom(uuid, roomToken);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    return;
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int errorCode) {
//                                RLog.d(TAG, "here white create room errorCode:" + errorCode);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        updateCallActionsWithWhiteBoard(false);
//                                        progressDialog.dismiss();
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onError(IOException exception) {
//                                RLog.d(TAG, "here white create room error :" + exception.getMessage());
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        updateCallActionsWithWhiteBoard(false);
//                                        progressDialog.dismiss();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }
//                break;
//            case R.id.menu_custom_stream:
//                if (Utils.isFastDoubleClick()) {
//                    showToast(R.string.rtc_processing);
//                    return;
//                }
//                checkBox = (CheckBox) v;
//                if (checkBox.isSelected()) {
//                    unPublishCustomStream(checkBox);
//                } else {
//                    showVideoListMenu(v);
//                }
//                break;
//            case R.id.menu_custom_audio:
//                checkBox = (CheckBox) v;
//                if (checkBox.isSelected()) {
//                    RongRTCAudioMixer.getInstance().stop();
//                    checkBox.setSelected(false);
//                    btnCustomAudioVolume.setVisibility(View.GONE);
//                    volumeIndex = 5;
//                    dismissVolumeWindow();
//                } else {
//                    showAudioListMenu(v);
//                }
//                break;
//            case R.id.menu_custom_audio_volume:
//                checkBox = (CheckBox) v;
//                if (checkBox.isSelected()){
//                    checkBox.setSelected(false);
//                    dismissVolumeWindow();
//                }else {
//                    checkBox.setSelected(true);
//                    showVolumeWindow(v);
//                }
//                break;

    }

    private void showSettingsDialog(){
        CallSettingsFragment callSettingsFragment = new CallSettingsFragment();
        callSettingsFragment.setListener(new MyCallSettingFragmentListener(this));
        callSettingsFragment.show(getFragmentManager(), "menuSettings");

    }

    private void unPublishCustomStream(final CheckBox checkBox) {
        CenterManager.getInstance().unPublishMediaStream(new RongRTCDataResultCallBack<RongRTCAVOutputStream>() {
            @Override
            public void onSuccess(RongRTCAVOutputStream stream) {
                renderViewManager.removeVideoView(true,myUserId, stream.getTag());
                checkBox.setSelected(false);
            }

            @Override
            public void onFailed(RTCErrorCode errorCode) {
                Toast.makeText(CallActivity.this, "?????????????????????????????????:"+errorCode, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private PopupWindow volumePopupWindow;
    private void showVolumeWindow(View view) {
        volumePopupWindow = new PopupWindow();
        View contentView = getLayoutInflater().inflate(R.layout.layout_audio_volume, null);
        final TextView volumeIndexView = contentView.findViewById(R.id.menu_custom_audio_volume_index);
        volumeIndexView.setText(String.valueOf(volumeIndex));

        contentView.findViewById(R.id.menu_custom_audio_volume_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVolume(true);
                volumeIndexView.setText(String.valueOf(volumeIndex));
            }
        });

        contentView.findViewById(R.id.menu_custom_audio_volume_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVolume(false);
                volumeIndexView.setText(String.valueOf(volumeIndex));
            }
        });

        volumePopupWindow.setContentView(contentView);
        volumePopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        volumePopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        volumePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void dismissVolumeWindow(){
        if (volumePopupWindow != null){
            volumePopupWindow.dismiss();
        }
    }

    private void updateVolume(boolean isUp){
        if (isUp && volumeIndex < 10){
            volumeIndex ++;
        }else if (!isUp && volumeIndex > 0){
            volumeIndex --;
        }
        RongRTCAudioMixer.getInstance().setVolume(volumeIndex);
    }

//    private void showAudioListMenu(View view) {
//        final PopupWindow popupWindow = new PopupWindow();
//        View contentView = getLayoutInflater().inflate(R.layout.layout_audio_list, null);
//        View audio = contentView.findViewById(R.id.audio);
//        audio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (RongIMClient.getInstance().getCurrentConnectionStatus() == NETWORK_UNAVAILABLE) {
//                    Toast.makeText(CallActivity.this, getResources().getString(R.string.Thecurrentnetworkisnotavailable), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                CenterManager.getInstance().unPublishMediaStream(null);
//                btnCustomAudioStream.setSelected(true);
//                final String mp3Path = "file:///android_asset/music.mp3";
//                RongRTCAudioMixer.getInstance()
//                        .startMix(CallActivity.this, mp3Path, AudioBufferStream.MODE_MIX, true);
//                RongRTCAudioMixer.getInstance().setVolume(volumeIndex);
//                popupWindow.dismiss();
//                btnCustomAudioVolume.setVisibility(View.VISIBLE);
//            }
//        });
//        popupWindow.setContentView(contentView);
//        showPopupWindowList(popupWindow, view);
//    }
//
//    private void showVideoListMenu(View view) {
//        final PopupWindow popupWindow = new PopupWindow();
//        View contentView = getLayoutInflater().inflate(R.layout.layout_video_list, null);
//        View video1 = contentView.findViewById(R.id.video_1);
//        View video2 = contentView.findViewById(R.id.video_2);
//        video1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                publishCustomStream("file:///android_asset/video_1.mp4");
//                popupWindow.dismiss();
//            }
//        });
//        video2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                publishCustomStream("file:///android_asset/video_2.mp4");
//                popupWindow.dismiss();
//            }
//        });
//        contentView.findViewById(R.id.video_3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                publishUSBCamera();
//                popupWindow.dismiss();
//            }
//        });
//        popupWindow.setContentView(contentView);
//        showPopupWindowList(popupWindow, view);
//    }

    private void showPopupWindowList(PopupWindow popupWindow, View view) {
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(getResources().getDimensionPixelSize(R.dimen.popup_width));
        popupWindow.setHeight(getResources().getDimensionPixelSize(R.dimen.popup_width));
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0] - popupWindow.getWidth();
        int y = location[1] + view.getHeight() / 2 - popupWindow.getHeight() / 2;
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
    }

    private void publishCustomStream(String filePath) {
        if (!customVideoEnabled) {
            String toastInfo = getResources().getString(R.string.publish_disabled);
            Toast.makeText(this, toastInfo, Toast.LENGTH_SHORT).show();
            return;
        }
        btnCustomStream.setSelected(true);
        RongRTCAudioMixer.getInstance().stop();
        btnCustomAudioStream.setSelected(false);
        CenterManager.getInstance().publishMediaStream(filePath, MediaMode.VIDEO, new OnSendListener() {
            @Override
            public void onSendStart(RongRTCAVOutputStream stream) {
                RongRTCVideoView videoView = RongRTCEngine.getInstance().createVideoView(CallActivity.this);
                stream.setRongRTCVideoView(videoView);
                stream.setRongRTCVideoTrack(RongRTCLocalSourceManager.getInstance().getCustomVideoTrack(stream));
                renderViewManager.userJoin(myUserId, stream.getTag(), iUserName, RongRTCTalkTypeUtil.O_CAMERA);
                renderViewManager.setVideoView(true, myUserId, stream.getTag(), iUserName, videoView, RongRTCTalkTypeUtil.O_CAMERA);
            }

            @Override
            public void onSendComplete(final RongRTCAVOutputStream stream) {
                CenterManager.getInstance().unPublishMediaStream(null);
                btnCustomStream.post(new Runnable() {
                    @Override
                    public void run() {
                        btnCustomStream.setSelected(false);
                        renderViewManager.removeVideoView(true, myUserId, stream.getTag());
                    }
                });
            }

            @Override
            public void onSendFailed() {
                String toastInfo = getResources().getString(R.string.publish_failed);
                Toast.makeText(CallActivity.this, toastInfo, Toast.LENGTH_SHORT).show();
                btnCustomStream.post(new Runnable() {
                    @Override
                    public void run() {
                        btnCustomStream.setSelected(false);
                    }
                });
            }
        });
    }

    private void showMembersDialog() {
        MembersDialog dialog = null;
        Fragment fragment = getFragmentManager().findFragmentByTag("MembersDialog");
        if (fragment == null) {
            dialog = new MembersDialog();
            dialog.setKickUserListener(new MembersDialog.onKickUserListener() {
                @Override
                public void onKick(String userId,String name) {
                    kickMember(userId,name);
                }
            });
        } else {
            dialog = (MembersDialog) fragment;
        }
        dialog.update(mMembers, adminUserId);
        dialog.show(getFragmentManager(), "MembersDialog");
    }

    private void kickMember(final String userId, final String name) {
        PromptDialog dialog = PromptDialog.newInstance(this, "", String.format(getString(R.string.member_operate_kick), name));
        dialog.setPromptButtonClickedListener(new PromptDialog.OnPromptButtonClickedListener() {
            @Override
            public void onPositiveButtonClicked() {
                if (isFinishing()) return;
                RoomKickOffMessage kickOffMessage = new RoomKickOffMessage(userId);
                rongRTCRoom.sendMessage(roomId, kickOffMessage, null);
            }

            @Override
            public void onNegativeButtonClicked() {
            }
        });
        dialog.show();
    }

    private void selectAdmin() {
        if (!myUserId.equals(adminUserId) || mMembersMap.size() <= 1) return;
        UserInfo userInfo = mMembersMap.get(mMembers.get(1).userId);
        if (userInfo == null) return;
        RoomInfoMessage roomInfoMessage = new RoomInfoMessage(userInfo.userId, userInfo.userName, userInfo.joinMode, userInfo.timestamp, true);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userInfo.userId);
            jsonObject.put("userName", userInfo.userName);
            jsonObject.put("joinMode", userInfo.joinMode);
            jsonObject.put("joinTime", userInfo.timestamp);
            jsonObject.put("master", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rongRTCRoom.setRoomAttributeValue(jsonObject.toString(), userInfo.userId, roomInfoMessage, null);
    }


    private void updateMembersDialog() {
        Fragment fragment = getFragmentManager().findFragmentByTag("MembersDialog");
        if (fragment != null) {
            sortRoomMembers();
            MembersDialog dialog = (MembersDialog) fragment;
            dialog.update(mMembers, adminUserId);
        }
    }

    private void toggleCameraMicViewStatus() {
        Log.i(TAG, "toggleCameraMicViewStatus() isObserver = " + isObserver + " isVideoMute = " + isVideoMute);
        iv_modeSelect.setVisibility(View.GONE);
        if (isObserver) {

            btnSwitchCamera.setVisibility(View.GONE);
            btnMuteSpeaker.setVisibility(View.VISIBLE);
            btnCloseCamera.setVisibility(View.GONE);
            btnMuteMic.setVisibility(View.GONE);
        } else {
            if (isVideoMute) {
                btnSwitchCamera.setEnabled(false);
                btnMuteSpeaker.setVisibility(View.VISIBLE);
                btnCloseCamera.setVisibility(View.VISIBLE);
                btnMuteSpeaker.setVisibility(View.VISIBLE);
            } else {
                btnSwitchCamera.setEnabled(true);
                btnSwitchCamera.setVisibility(View.VISIBLE);
                btnMuteSpeaker.setVisibility(View.VISIBLE);
                btnCloseCamera.setVisibility(View.VISIBLE);
                btnMuteSpeaker.setVisibility(View.VISIBLE);
            }
            btnCloseCamera.setChecked(isVideoMute);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInRoom) {
//            RongRTCCapture.getInstance().startCameraCapture();
            RongRTCLocalSourceManager.getInstance().startCapture();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isInRoom) {
            RongRTCCapture.getInstance().stopCameraCapture();
        }
    }

    @Override
    public void onNotifyHeadsetState(boolean connected, int type) {
        try {
            if (connected) {
                HeadsetPlugReceiverState = true;
                if (type == 0) {
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    if (am != null) {
                        if (am.getMode() != AudioManager.MODE_IN_COMMUNICATION) {
                            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                        }
                        am.startBluetoothSco();
                        am.setBluetoothScoOn(true);
                        am.setSpeakerphoneOn(false);
                    }
                }
                if (null != btnMuteSpeaker) {
                    btnMuteSpeaker.setBackgroundResource(R.drawable.img_capture_gray);
                    btnMuteSpeaker.setSelected(false);
                    btnMuteSpeaker.setEnabled(false);
                    btnMuteSpeaker.setClickable(false);
                }
            } else {
                if (type == 1 &&
                        BluetoothUtil.hasBluetoothA2dpConnected()) {
                    return;
                }
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (am != null) {
                    if (am.getMode() != AudioManager.MODE_IN_COMMUNICATION) {
                        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    }
                    if (type == 0) {
                        am.stopBluetoothSco();
                        am.setBluetoothScoOn(false);
                        am.setSpeakerphoneOn(!muteSpeaker);
                    }
                }
                if (null != btnMuteSpeaker) {
                    btnMuteSpeaker.setBackgroundResource(R.drawable.selector_checkbox_capture);
                    btnMuteSpeaker.setSelected(false);
                    btnMuteSpeaker.setEnabled(true);
                    btnMuteSpeaker.setClickable(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RTCVideoFrame processVideoFrame(RTCVideoFrame rtcVideoFrame) {
        if (mShowWaterMark)
            rtcVideoFrame.setTextureId(onDrawWater(rtcVideoFrame.getWidth(), rtcVideoFrame.getHeight(), rtcVideoFrame.getTextureId()));
        if (isGPUImageFliter) {
            if (beautyFilter == null) {
                beautyFilter = new GPUImageBeautyFilter();
            }
            if (rtcVideoFrame.getCurrentCaptureDataType()) {
                rtcVideoFrame.setTextureId(beautyFilter.draw(rtcVideoFrame.getWidth(), rtcVideoFrame.getHeight(), rtcVideoFrame.getTextureId()));
            } else {
                //yuv
            }
        }
        return rtcVideoFrame;
    }

    @Override
    public byte[] onLocalBuffer(RTCAudioFrame rtcAudioFrame) {
        if (writePcmFileForDebug) {
            byte[] bytes = rtcAudioFrame.getBytes().clone();
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            writePcmBuffer(byteBuffer, localFileChanel);
        }
        return rtcAudioFrame.getBytes();
    }

    @Override
    public byte[] onRemoteBuffer(RTCAudioFrame rtcAudioFrame) {
        if (writePcmFileForDebug) {
            byte[] bytes = rtcAudioFrame.getBytes().clone();
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            writePcmBuffer(byteBuffer, remoteFileChanel);
        }
        return rtcAudioFrame.getBytes();
    }

    private @Nullable
    FileOutputStream localFileStream;
    private @Nullable
    FileChannel localFileChanel;
    private @Nullable
    FileOutputStream remoteFileStream;
    private @Nullable
    FileChannel remoteFileChanel;

    private void createDebugPcmFile() {
        try {
            String roomId = "";
            if (CenterManager.getInstance().getRongRTCRoom() != null) {
                roomId = CenterManager.getInstance().getRongRTCRoom().getRoomId();
            }
            String localName = RongIMClient.getInstance().getCurrentUserId() + "_" + roomId + "_local.pcm";
            String remoteName = RongIMClient.getInstance().getCurrentUserId() + "_" + roomId + "_remote.pcm";
            File parent_path = Environment.getExternalStorageDirectory();

            File dir = new File(parent_path.getAbsoluteFile(), "webrtc");
            dir.mkdir();

            File file = new File(dir.getAbsoluteFile(), localName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            localFileStream = new FileOutputStream(file);
            localFileChanel = localFileStream.getChannel();
            RLog.d(TAG, "create file success " + file.getAbsolutePath());
            file = new File(dir.getAbsoluteFile(), remoteName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            remoteFileStream = new FileOutputStream(file);
            remoteFileChanel = remoteFileStream.getChannel();
            RLog.d(TAG, "create file success " + file.getAbsolutePath());
        } catch (IOException e) {
            RLog.d(TAG, "create file failed");

        }
    }

    private void writePcmBuffer(ByteBuffer buffer, FileChannel channel) {
        try {
            if (channel != null) {
                channel.write(buffer);
                Log.d(TAG, "write buffer success.");
            }
        } catch (IOException e) {
            RLog.d(TAG, "write file failed");
        }
    }


    private void closePcmFile(FileChannel channel, FileOutputStream fos) {
        try {
            if (channel != null)
                channel.close();
            if (fos != null) {
                fos.flush();
                fos.close();
            }
            channel = null;
            fos = null;
        } catch (IOException e) {
            RLog.d(TAG, "close file failed");
        }
    }

    @Override
    public void onCreateEglFailed(String userId,String tag, Exception e) {
        if(isShowAutoTest){
            if (e instanceof CreateEglContextException && ((CreateEglContextException) e).getErrorCode() == CreateEglContextException.CODE_EGLBADALLOC)
                renderViewManager.onCreateEglFailed(userId,tag);
        }
        showToast("onCreateEglFailed:"+e.getMessage());
    }

    private int onDrawWater(int width, int height, int textureID) {
        if (mWaterFilter == null){
            mWaterFilter = new WaterMarkFilter(this,RongRTCCapture.getInstance().isFrontCamera(), TextureHelper.loadBitmap(this, R.drawable.logo));
        }
        mWaterFilter.drawFrame(width,height,textureID,RongRTCCapture.getInstance().isFrontCamera());

        return mWaterFilter.getTextureID();
    }



    /*------------------------------------???????????????????????? here white ?????? start --------------------------*/
    private void joinWhiteBoardRoom(final String uuid, final String roomToken) {
        RLog.d(TAG, "here white uuid = " + uuid + " , roomToken = " + roomToken);
        WhiteSdk whiteSdk = new WhiteSdk(
                whiteboardView,
                CallActivity.this,
                new WhiteSdkConfiguration(DeviceType.touch, 10, 0.1)
                // ??????????????????????????? URL ,?????????????????????, ?????????????????????????????????????????? URL,?????? cache ??????????????????????????????????????????(??? ???????????? Token)
                , new UrlInterrupter() {
            @Override
            public String urlInterrupter(String s) {
                // ???????????? URL
                return s;
            }
        }
        );
        whiteSdk.joinRoom(new RoomParams(uuid, roomToken), new AbstractRoomCallbacks() {
            @Override
            public void onPhaseChanged(RoomPhase phase) {
                RLog.d(TAG, "here white AbstractRoomCallbacks onPhaseChanged");
            }

            @Override
            public void onBeingAbleToCommitChange(boolean isAbleToCommit) {
                RLog.d(TAG, "here white AbstractRoomCallbacks onPhaseChanged");
            }

            @Override
            public void onRoomStateChanged(final RoomState modifyState) {
                RLog.d(TAG, "here white AbstractRoomCallbacks onRoomStateChanged");
                if (modifyState != null && modifyState.getSceneState() != null) {
                    updateSceneState(modifyState.getSceneState());
                }
                if (modifyState != null && modifyState.getZoomScale() != null) {
                    RLog.d(TAG, "here white AbstractRoomCallbacks zoom scale changed = " + modifyState.getZoomScale());
                }
            }

            @Override
            public void onCatchErrorWhenAppendFrame(long userId, Exception error) {
                RLog.d(TAG, "here white AbstractRoomCallbacks onCatchErrorWhenAppendFrame userId = "+userId+" ???error = "+error.getMessage());
            }

            @Override
            public void onDisconnectWithError(Exception e) {
                RLog.d(TAG, "here white joinRoom onDisconnectWithError = "+e.getMessage());
            }

            @Override
            public void onKickedWithReason(String reason) {
                RLog.d(TAG, "here white joinRoom onKickedWithReason reason = " + reason);
            }
        }, new Promise<Room>() {
            @Override
            public void then(final Room room) {
                progressDialog.dismiss();
                RLog.d(TAG, "here white joinRoom Promise  Room");
                room.zoomChange(0.3);
                if (isObserver) {
                    room.disableOperations(true);
                }
                whiteBoardRoom = room;
                initPencilColor();
                bindWhiteBoardActions(room);
                room.getSceneState(new Promise<SceneState>() {
                    @Override
                    public void then(SceneState sceneState) {
                        RLog.d(TAG, "here white joinRoom and then getSceneState");
                        if (sceneState != null) {
                            if (sceneState.getScenePath().equals(WhiteBoardApi.WHITE_BOARD_INIT_SCENE_PATH)) {
                                currentSceneName = generateSceneName();
                                whiteBoardScenes = new Scene[]{
                                        new Scene(currentSceneName, new PptPage("", 0d, 0d))
                                };
                                room.putScenes(WhiteBoardApi.WHITE_BOARD_SCENE_PATH, whiteBoardScenes, 0);
                                room.setScenePath(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + currentSceneName);
                                whiteboardView.setVisibility(View.VISIBLE);
                                currentSceneIndex = 0;
                            } else {
                                updateSceneState(sceneState);
                                room.setScenePath(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + currentSceneName);
                            }
                        }
                    }

                    @Override
                    public void catchEx(SDKError sdkError) {
                        RLog.d(TAG, "here white joinRoom and then getSceneState error = " + sdkError.getMessage());
                    }
                });
            }

            @Override
            public void catchEx(SDKError t) {
                RLog.d(TAG, "here white joinRoom Promise  catchEx = " + t.toString());
                progressDialog.dismiss();
                updateCallActionsWithWhiteBoard(false);
                hidePencilColorPopupWindow();
                showToast(R.string.white_board_service_error);
            }
        });
    }

    private void updateSceneState(SceneState sceneState) {
        currentSceneIndex = sceneState.getIndex();
        whiteBoardScenes = sceneState.getScenes();
        String scenePath = sceneState.getScenePath();
        currentSceneName = scenePath.substring(scenePath.lastIndexOf("/") + 1);
        updateWhiteBoardPagesView();
    }

    private void bindWhiteBoardActions(final Room room) {
        //????????????
        whiteBoardAction.findViewById(R.id.white_action_pencil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePencilColorPopupWindow();
                pencilColorPopupWindow = new PencilColorPopupWindow(CallActivity.this,room);
                pencilColorPopupWindow.show(v);

            }
        });
        //????????????
        whiteBoardAction.findViewById(R.id.white_action_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberState memberState = new MemberState();
                memberState.setCurrentApplianceName(Appliance.TEXT);
                room.setMemberState(memberState);
            }
        });
        //?????????
        whiteBoardAction.findViewById(R.id.white_action_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberState memberState = new MemberState();
                memberState.setCurrentApplianceName(Appliance.ERASER);
                room.setMemberState(memberState);
            }
        });
        //???????????????
        whiteBoardAction.findViewById(R.id.white_action_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room != null) {
                    room.cleanScene(true);
                }
            }
        });
        //???????????????
        whiteBoardAction.findViewById(R.id.white_action_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sceneName = generateSceneName();
                room.putScenes(WhiteBoardApi.WHITE_BOARD_SCENE_PATH, new Scene[]{
                        new Scene(sceneName, new PptPage("", 0d, 0d)),
                }, Integer.MAX_VALUE);
                room.setScenePath(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + sceneName);
                currentSceneName = sceneName;
            }
        });
        //?????????????????????
        whiteBoardAction.findViewById(R.id.white_action_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whiteBoardScenes.length == 1) {
                    room.cleanScene(true);
                } else {
                    room.removeScenes(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + currentSceneName);
                }
            }
        });
        //?????????
        whiteBoardPagesPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSceneIndex > 0) {
                    String sceneName = whiteBoardScenes[--currentSceneIndex].getName();
                    room.setScenePath(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + sceneName);
                }
                updateWhiteBoardPagesView();
            }
        });
        //?????????
        whiteBoardPagesNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSceneIndex < whiteBoardScenes.length - 1) {
                    String sceneName = whiteBoardScenes[++currentSceneIndex].getName();
                    room.setScenePath(WhiteBoardApi.WHITE_BOARD_SCENE_PATH + "/" + sceneName);
                }
                updateWhiteBoardPagesView();
            }
        });
        //????????????????????????
        whiteBoardClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCallActionsWithWhiteBoard(false);
                hidePencilColorPopupWindow();
                if (whiteBoardRoom != null) {
                    whiteBoardRoom.disconnect();
                    whiteBoardRoom = null;
                }
            }
        });
    }

    private void initPencilColor() {
        int color = getResources().getColor(R.color.white_board_pencil_color_red);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        MemberState memberState = new MemberState();
        memberState.setStrokeColor(new int[]{red, green, blue});
        memberState.setCurrentApplianceName(Appliance.PENCIL);
        whiteBoardRoom.setMemberState(memberState);
    }

    private void updateWhiteBoardPagesView() {
        if (currentSceneIndex == 0) {
            whiteBoardPagesPrevious.setEnabled(false);
        } else {
            whiteBoardPagesPrevious.setEnabled(true);
        }
        if (currentSceneIndex == whiteBoardScenes.length - 1) {
            whiteBoardPagesNext.setEnabled(false);
        } else {
            whiteBoardPagesNext.setEnabled(true);
        }
    }

    private String generateSceneName() {
        String userId = RongIMClient.getInstance().getCurrentUserId();
        return shortMD5(userId, System.currentTimeMillis() + "");
    }

    private void setRoomWhiteBoardInfo(final String uuid, final String roomToken) {
        whiteBoardRoomInfo = new WhiteBoardRoomInfo(uuid,roomToken);
        WhiteBoardInfoMessage whiteBoardInfoMessage = new WhiteBoardInfoMessage(uuid, roomToken);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", uuid);
            jsonObject.put("roomToken", roomToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rongRTCRoom.setRoomAttributeValue(jsonObject.toString(), WhiteBoardApi.WHITE_BOARD_KEY, whiteBoardInfoMessage, new RongRTCResultUICallBack() {
            @Override
            public void onUiSuccess() {
                RLog.d(TAG, "here white setRoomWhiteBoardInfo success");
            }

            @Override
            public void onUiFailed(RTCErrorCode errorCode) {
                RLog.d(TAG, "here white setRoomWhiteBoardInfo error = " + errorCode.getValue());
            }
        });
    }

    private void hidePencilColorPopupWindow(){
        if (pencilColorPopupWindow != null) {
            pencilColorPopupWindow.dismiss();
        }
    }

    private void updateCallActionsWithWhiteBoard(boolean hideCallAction) {
        if (hideCallAction) {
            buttonHangUp.setVisibility(View.GONE);
            mcall_more_container.setVisibility(View.GONE);
            titleContainer.setVisibility(View.GONE);
            btnCloseCamera.setVisibility(View.GONE);
            btnMuteMic.setVisibility(View.GONE);
            whiteboardView.setVisibility(View.VISIBLE);
            if (!isObserver) {
                whiteBoardAction.setVisibility(View.VISIBLE);
                whiteBoardPagesPrevious.setVisibility(View.VISIBLE);
                whiteBoardPagesNext.setVisibility(View.VISIBLE);
            } else {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) whiteboardView.getLayoutParams();
                layoutParams.bottomMargin = 0;
                whiteboardView.setLayoutParams(layoutParams);
            }
            whiteBoardClose.setVisibility(View.VISIBLE);
        } else {
            btnCloseCamera.setVisibility(View.VISIBLE);
            btnMuteMic.setVisibility(View.VISIBLE);
            buttonHangUp.setVisibility(View.VISIBLE);
            mcall_more_container.setVisibility(View.VISIBLE);
            titleContainer.setVisibility(View.VISIBLE);
            whiteboardView.setVisibility(View.GONE);
            whiteBoardAction.setVisibility(View.GONE);
            whiteBoardPagesPrevious.setVisibility(View.GONE);
            whiteBoardPagesNext.setVisibility(View.GONE);
            whiteBoardClose.setVisibility(View.GONE);
        }
    }

    private void deleteRTCWhiteBoardAttribute() {
        if (rongRTCRoom != null) {
            //rtc room???????????????????????????????????????
            if (whiteBoardRoomInfo != null &&
                    (rongRTCRoom.getRemoteUsers() == null || rongRTCRoom.getRemoteUsers().size() == 0||
                            allObserver())) {
                deleteWhiteBoardRoom();
                rongRTCRoom.deleteRoomAttributes(Arrays.asList(WhiteBoardApi.WHITE_BOARD_KEY), null, new RongRTCResultUICallBack() {
                    @Override
                    public void onUiSuccess() {
                        RLog.d(TAG, "here white delete rtc room attributes success ");
                    }

                    @Override
                    public void onUiFailed(RTCErrorCode errorCode) {
                        RLog.d(TAG, "here white delete rtc room attributes error =  " + errorCode.getValue());
                    }
                });
            }
        }
    }

    private boolean allObserver() {
        if (mMembersMap != null) {
            for (UserInfo userInfo : mMembersMap.values()) {
                if (userInfo.joinMode != RoomInfoMessage.JoinMode.OBSERVER) {
                    return false;
                }
            }
        }
        return true;
    }

    private void clearWhiteBoardInfoIfNeeded() {
        if (isObserver && rongRTCRoom != null) {
            if (rongRTCRoom.getRemoteUsers() == null || rongRTCRoom.getRemoteUsers().size() == 0 ||
                    allObserver()) {
                whiteBoardRoomInfo = null;
            }
        }
    }

    private void deleteWhiteBoardRoom() {
        if (whiteBoardRoomInfo != null) {
            WhiteBoardApi.deleteRoom(whiteBoardRoomInfo.getUuid(), new HttpClient.ResultCallback() {
                @Override
                public void onResponse(String result) {
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                    try {
                        JSONObject msg = new JSONObject(result);
                        int resultCode = msg.getInt("code");
                        if (resultCode == 200) {
                            RLog.d(TAG, "here white deleteRoom success ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }

                @Override
                public void onFailure(int errorCode) {
                    RLog.d(TAG, "here white deleteRoom errorCode = " + errorCode);
                }

                @Override
                public void onError(IOException exception) {
                    RLog.d(TAG, "here white deleteRoom exception = " + exception.getMessage());
                }
            });
        }
    }

    private String shortMD5(String... args) {
        try {
            StringBuilder builder = new StringBuilder();

            for (String arg : args) {
                builder.append(arg);
            }

            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(builder.toString().getBytes());
            byte[] mds = mdInst.digest();
            mds = Base64.encode(mds, Base64.NO_WRAP);
            String result = new String(mds);
            result = result.replace("=", "").replace("+", "-").replace("/", "_").replace("\n", "");
            return result;
        } catch (Exception e) {
            RLog.e(TAG, "shortMD5", e);
        }
        return "";
    }
    /*------------------------------------???????????????????????? here white ?????? end --------------------------*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKickedOfflineEvent(KickedOfflineEvent offlineEvent){
        Log.i(TAG,"kicked offline ");
        this.finish();
    }

    static class MyCallSettingFragmentListener implements CallSettingsFragment.CallSettingFragmentListener{
        CallActivity activity;

        public MyCallSettingFragmentListener(CallActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onSwitchAudioOptions(boolean isOn) {
            SessionManager.getInstance().put(SettingActivity.IS_AUDIO_MUSIC, isOn);
            RongRTCCapture.getInstance().changeAudioScenario(isOn? RongRTCConfig.AudioScenario.MUSIC : RongRTCConfig.AudioScenario.MUSIC);
        }

        @Override
        public void onUploadClickEvents() {
//            FwLog.upload();
//            activity.showToast("???????????????");
        }
    }
}
