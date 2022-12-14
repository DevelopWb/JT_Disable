package io.rong.callkit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.rongcloud.rtc.RongRTCConfig;
import cn.rongcloud.rtc.utils.FinLog;
import io.rong.callkit.util.BluetoothUtil;
import io.rong.callkit.util.CallKitUtils;
import io.rong.callkit.util.HeadsetPlugReceiver;
import io.rong.callkit.util.RingingMode;
import io.rong.calllib.IRongCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.common.RLog;
import io.rong.imkit.RongContext;
import io.rong.imkit.manager.AudioPlayManager;
import io.rong.imkit.manager.AudioRecordManager;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imkit.utils.NotificationUtil;

import static io.rong.callkit.CallFloatBoxView.showFB;
import static io.rong.callkit.util.CallKitUtils.isDebug;
import static io.rong.callkit.util.CallKitUtils.isDial;

/**
 * Created by weiqinxiao on 16/3/9.
 */
public class BaseCallActivity extends BaseNoActionBarActivity implements IRongCallListener,
        PickupDetector.PickupDetectListener {

    private static final String TAG = "BaseCallActivity";
    private static final String MEDIAPLAYERTAG = "MEDIAPLAYERTAG";
    private final static long DELAY_TIME = 1000;
    static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 100;
    static final int REQUEST_CODE_ADD_MEMBER = 110;
    public final int REQUEST_CODE_ADD_MEMBER_NONE = 120;
    static final int VOIP_MAX_NORMAL_COUNT = 6;

    private MediaPlayer mMediaPlayer;
    private Vibrator mVibrator;
    private long time = 0;
    private Runnable updateTimeRunnable;

    private boolean shouldRestoreFloat;
    //????????????????????????????????????????????????
    private boolean checkingOverlaysPermission;
    protected Handler handler;
    /**
     * ????????????????????????
     */
    protected boolean isFinishing;

    protected PickupDetector pickupDetector;
    protected PowerManager powerManager;
    protected PowerManager.WakeLock wakeLock;
    protected PowerManager.WakeLock screenLock;

    static final String[] VIDEO_CALL_PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    static final String[] AUDIO_CALL_PERMISSIONS = {Manifest.permission.RECORD_AUDIO};

    public static final int CALL_NOTIFICATION_ID = 4000;
    private boolean isMuteCamera = false;

    /**
     * ???????????????????????????????????????
     */
    private boolean isIncoming;

    public void setShouldShowFloat(boolean ssf) {
        CallKitUtils.shouldShowFloat = ssf;
    }

    public void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void postRunnableDelay(Runnable runnable) {
        handler.postDelayed(runnable, DELAY_TIME);
    }

    /**
     * ?????????????????????Ringer Mode?????????????????????????????????????????????
     */
    protected final BroadcastReceiver mRingModeReceiver = new BroadcastReceiver() {
        boolean isFirstReceivedBroadcast = true;

        @Override
        public void onReceive(Context context, Intent intent) {
            // ??????????????? sticky ???????????????????????????????????????????????????????????????????????????????????????
            if (isFirstReceivedBroadcast) {
                isFirstReceivedBroadcast = false;
                return;
            }
            // ?????? isIncoming ????????????????????????????????????????????????????????????????????????????????????
            if (isIncoming && intent.getAction().equals(AudioManager.RINGER_MODE_CHANGED_ACTION) && !CallKitUtils.callConnected) {
                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                final int ringMode = am.getRingerMode();
                Log.i(TAG, "Ring mode Receiver mode=" + ringMode);
                switch (ringMode) {
                    case AudioManager.RINGER_MODE_NORMAL:
                        stopRing();
                        callRinging(RingingMode.Incoming);
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        stopRing();
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        stopRing();
                        startVibrator();
                        break;
                    default:
                }
            }
        }
    };

    private HeadsetPlugReceiver headsetPlugReceiver = null;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    public static final String EXTRA_BUNDLE_KEY_MUTECAMERA = "muteCamera";
    public static final String EXTRA_BUNDLE_KEY_MUTEMIC = "muteMIC";
    public static final String EXTRA_BUNDLE_KEY_LOCALVIEWUSERID = "localViewUserId";
    public static final String EXTRA_BUNDLE_KEY_CALLACTION = "callAction";
    public static final String EXTRA_BUNDLE_KEY_MEDIATYPE = "mediaType";
    public static final String EXTRA_BUNDLE_KEY_USER_TOP_NAME = "rc_voip_user_top_name";
    public static final String EXTRA_BUNDLE_KEY_USER_TOP_NAME_TAG = "rc_voip_user_top_name_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RLog.d(TAG, "BaseCallActivity onCreate");
        audioVideoConfig();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        shouldRestoreFloat = true;
        CallKitUtils.shouldShowFloat = false;

        createPowerManager();
        boolean isScreenOn = powerManager.isScreenOn();
        if (!isScreenOn) {
            wakeLock.acquire();
        }
        handler = new Handler();
        RongCallProxy.getInstance().setCallListener(this);

        AudioPlayManager.getInstance().stopPlay();
        AudioRecordManager.getInstance().destroyRecord();
        RongContext.getInstance().getEventBus().register(this);

        initMp();

        //?????? BroadcastReceiver ???????????????????????????
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
        registerReceiver(mRingModeReceiver, filter);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (am != null) {
            onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                }
            };
            am.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }

    private void initMp() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    try {
                        if (mp != null) {
                            mp.setLooping(true);
                            mp.start();
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        Log.i(MEDIAPLAYERTAG, "setOnPreparedListener Error!");
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("floatbox");
        if (shouldRestoreFloat && bundle != null) {
            onRestoreFloatBox(bundle);
        }
    }

    public void callRinging(RingingMode mode) {
        isIncoming = false;
        try {
            initMp();

            if (mode == RingingMode.Incoming) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mMediaPlayer.setDataSource(this, uri);
            } else if (mode == RingingMode.Incoming_Custom || mode == RingingMode.Outgoing) {
                int rawResId = mode == RingingMode.Outgoing ? R.raw.voip_outgoing_ring : R.raw.voip_incoming_ring;
                AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(rawResId);
                mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                        assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                assetFileDescriptor.close();
            }

            // ?????? MediaPlayer ?????????????????????
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .build();
                mMediaPlayer.setAudioAttributes(attributes);
            } else {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            }
            mMediaPlayer.prepareAsync();
            final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                am.setSpeakerphoneOn(mode == RingingMode.Incoming || mode == RingingMode.Incoming_Custom);
                // ?????????????????????????????????????????????
                am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                // ????????????????????????????????????
                am.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 5, AudioManager.STREAM_VOICE_CALL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            Log.i(MEDIAPLAYERTAG, "---onOutgoingCallRinging Error---" + e1.getMessage());
        }
    }

    public void onIncomingCallRinging() {
        isIncoming = true;
        int ringerMode = NotificationUtil.getRingerMode(this);
        if (ringerMode != AudioManager.RINGER_MODE_SILENT) {
            if (ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
                startVibrator();
            } else {
                if (isVibrateWhenRinging()) {
                    startVibrator();
                }
                callRinging(RingingMode.Incoming);
            }
        }
    }

    public void setupTime(final TextView timeView) {
        try {
            if (updateTimeRunnable != null) {
                handler.removeCallbacks(updateTimeRunnable);
            }
            timeView.setVisibility(View.VISIBLE);
            updateTimeRunnable = new UpdateTimeRunnable(timeView);
            handler.post(updateTimeRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelTime() {
        if (handler != null && updateTimeRunnable != null) {
            handler.removeCallbacks(updateTimeRunnable);
        }
    }

    public long getTime() {
        return time;
    }

    @SuppressLint("MissingPermission")
    protected void stopRing() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
            }
            if (mVibrator != null) {
                mVibrator.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(MEDIAPLAYERTAG, "mMediaPlayer stopRing error=" + ((e == null) ? "null" : e.getMessage()));
        }
    }

    protected void startVibrator() {
        if (mVibrator == null) {
            mVibrator = (Vibrator) RongContext.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        } else {
            mVibrator.cancel();
        }
        mVibrator.vibrate(new long[]{500, 1000}, 0);
    }

    @Override
    public void onCallOutgoing(RongCallSession callProfile, SurfaceView localVideo) {
        CallKitUtils.shouldShowFloat = true;
        CallKitUtils.isDial = true;
    }

    @Override
    public void onRemoteUserRinging(String userId) {

    }

    @Override
    public void onCallDisconnected(RongCallSession callProfile, RongCallCommon.CallDisconnectedReason reason) {

        if (RongCallKit.getCustomerHandlerListener() != null) {
            RongCallKit.getCustomerHandlerListener().onCallDisconnected(callProfile, reason);
        }
        CallKitUtils.callConnected = false;
        CallKitUtils.shouldShowFloat = false;

        toastDisconnectReason(reason);

        AudioPlayManager.getInstance().setInVoipMode(false);
        stopRing();
        NotificationUtil.clearNotification(this, BaseCallActivity.CALL_NOTIFICATION_ID);
        RongCallProxy.getInstance().setCallListener(null);
        BluetoothUtil.stopBlueToothSco(this);
    }

    @Override
    public void onRemoteUserJoined(String userId, RongCallCommon.CallMediaType mediaType, int userType,
                                   SurfaceView remoteVideo) {
        CallKitUtils.isDial = false;
    }

    @Override
    public void onRemoteUserInvited(String userId, RongCallCommon.CallMediaType mediaType) {
        if (RongCallKit.getCustomerHandlerListener() != null) {
            RongCallKit.getCustomerHandlerListener().onRemoteUserInvited(userId, mediaType);
        }
    }

    @Override
    public void onRemoteUserLeft(String userId, RongCallCommon.CallDisconnectedReason reason) {
        RLog.i(TAG, "onRemoteUserLeft userId :" + userId + ", CallDisconnectedReason :" + reason.name());
        toastDisconnectReason(reason);
    }

    @Override
    public void onMediaTypeChanged(String userId, RongCallCommon.CallMediaType mediaType, SurfaceView video) {

    }

    @Override
    public void onError(RongCallCommon.CallErrorCode errorCode) {
        AudioPlayManager.getInstance().setInVoipMode(false);
        if (RongCallCommon.CallErrorCode.ENGINE_NOT_FOUND.getValue() == errorCode.getValue() && isDebug(BaseCallActivity.this)) {
            Toast.makeText(this, getResources().getString(R.string.rc_voip_engine_notfound), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onCallConnected(RongCallSession callProfile, SurfaceView localVideo) {
        Log.d(TAG, "onCallConnected ?????????????????????????????? onCallConnected ???????????? call ??????????????????");

        if (RongCallKit.getCustomerHandlerListener() != null) {
            RongCallKit.getCustomerHandlerListener().onCallConnected(callProfile, localVideo);
        }
        CallKitUtils.callConnected = true;
        CallKitUtils.shouldShowFloat = true;
        CallKitUtils.isDial = false;
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        }
        AudioPlayManager.getInstance().setInVoipMode(true);
        AudioRecordManager.getInstance().destroyRecord();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RLog.d(TAG, "BaseCallActivity onStop");
        if (CallKitUtils.shouldShowFloat && !checkingOverlaysPermission) {
            Bundle bundle = new Bundle();
            String action = onSaveFloatBoxState(bundle);
            if (checkDrawOverlaysPermission(true)) {
                if (action != null) {
                    bundle.putString("action", action);
                    showFB(getApplicationContext(), bundle);
                    int mediaType = bundle.getInt("mediaType");
                    showOnGoingNotification(getString(R.string.rc_call_on_going),
                            mediaType == RongCallCommon.CallMediaType.AUDIO.getValue()
                                    ? getString(R.string.rc_audio_call_on_going) :
                                    getString(R.string.rc_video_call_on_going));
                    if (!isFinishing()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                        } else {
                            finish();
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.rc_voip_float_window_not_allowed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RLog.d(TAG, "BaseCallActivity onResume");
        try {
            RongCallSession session = RongCallClient.getInstance().getCallSession();
            if (session != null) {
                if (session.getMediaType() == RongCallCommon.CallMediaType.VIDEO && !isMuteCamera) {
                    RongCallClient.getInstance().startCapture();
                }
                RongCallProxy.getInstance().setCallListener(this);
                if (shouldRestoreFloat) {
                    CallFloatBoxView.hideFloatBox();
                    NotificationUtil.clearNotification(this, BaseCallActivity.CALL_NOTIFICATION_ID);
                }
                long activeTime = session != null ? session.getActiveTime() : 0;
                time = activeTime == 0 ? 0 : (System.currentTimeMillis() - activeTime) / 1000;
                shouldRestoreFloat = true;
                if (time > 0) {
                    CallKitUtils.shouldShowFloat = true;
                }
                if (checkingOverlaysPermission) {
                    checkDrawOverlaysPermission(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            RLog.d(TAG, "BaseCallActivity onResume Error : " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shouldRestoreFloat = false;
        if (RongCallKit.getCustomerHandlerListener() != null) {
            List<String> selectedUserIds = RongCallKit.getCustomerHandlerListener().handleActivityResult(requestCode,
                    resultCode, data);
            if (selectedUserIds != null && selectedUserIds.size() > 0)
                onAddMember(selectedUserIds);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            RLog.d(TAG, "BaseCallActivity onDestroy");
            RongContext.getInstance().getEventBus().unregister(this);
            handler.removeCallbacks(updateTimeRunnable);
            unregisterReceiver(mRingModeReceiver);
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                am.setMode(AudioManager.MODE_NORMAL);
                if (onAudioFocusChangeListener != null) {
                    am.abandonAudioFocus(onAudioFocusChangeListener);
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.i(MEDIAPLAYERTAG, "--- onDestroy IllegalStateException---");
        }
        super.onDestroy();
        unRegisterHeadsetplugReceiver();
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }

        if (screenLock != null && screenLock.isHeld()) {
            try {
                screenLock.setReferenceCounted(false);
                screenLock.release();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onRemoteCameraDisabled(String userId, boolean muted) {
        Log.d(TAG, "onRemoteCameraDisabled ??????????????? camera ?????????????????????????????? onRemoteCameraDisabled ?????????????????????");
    }

    @Override
    public void onRemoteMicrophoneDisabled(String userId, boolean disabled) {
        Log.d(TAG, "onRemoteMicrophoneDisabled");
    }

    @Override
    public void onNetworkReceiveLost(String userId, int lossRate) {

    }

    @Override
    public void onNetworkSendLost(int lossRate, int delay) {

    }

    @Override
    public void onFirstRemoteVideoFrame(String userId, int height, int width) {

    }

    @Override
    public void onAudioLevelSend(String audioLevel) {

    }

    @Override
    public void onAudioLevelReceive(HashMap<String, String> audioLevel) {

    }

    private void toastDisconnectReason(RongCallCommon.CallDisconnectedReason reason) {
        String text = null;
        switch (reason) {
            case CANCEL:
                text = getString(R.string.rc_voip_mo_cancel);
                break;
            case REJECT:
                text = getString(R.string.rc_voip_mo_reject);
                break;
            case NO_RESPONSE:
            case BUSY_LINE:
                text = getString(R.string.rc_voip_mo_no_response);
                break;
            case REMOTE_BUSY_LINE:
                text = getString(R.string.rc_voip_mt_busy);

                break;
            case REMOTE_CANCEL:
                text = getString(R.string.rc_voip_mt_cancel);
                break;
            case REMOTE_REJECT:
                text = getString(R.string.rc_voip_mt_reject);
                break;
            case REMOTE_NO_RESPONSE:
                text = getString(R.string.rc_voip_mt_no_response);
                break;
            case REMOTE_HANGUP:
            case HANGUP:
            case NETWORK_ERROR:
            case INIT_VIDEO_ERROR:
                text = getString(R.string.rc_voip_call_terminalted);
                break;
            case OTHER_DEVICE_HAD_ACCEPTED:
                text = getString(R.string.rc_voip_call_other);
                break;
            //                case CONN_USER_BLOCKED:
            //                text = getString(R.string.rc_voip_call_conn_user_blocked);
            //                break;
        }
        Log.d(TAG, "Ring mode Receiver mode=" + text);
        if (text != null) {
            showShortToast(text);
        }
    }

    public void onRemoteUserPublishVideoStream(String userId, String streamId, String tag, SurfaceView surfaceView) {
    }

    @Override
    public void onRemoteUserUnpublishVideoStream(String userId, String streamId, String tag) {

    }

    /**
     * onStart???????????????
     **/
    public void onRestoreFloatBox(Bundle bundle) {
        isMuteCamera = bundle.getBoolean(EXTRA_BUNDLE_KEY_MUTECAMERA);
    }

    protected void addMember(ArrayList<String> currentMemberIds) {
        // do your job to add more member
        // after got your new member, call onAddMember
        if (RongCallKit.getCustomerHandlerListener() != null) {
            RongCallKit.getCustomerHandlerListener().addMember(this, currentMemberIds);
        }
    }

    protected void onAddMember(List<String> newMemberIds) {
    }

    /**
     * onPause??????????????????????????????
     **/
    public String onSaveFloatBoxState(Bundle bundle) {
        return null;
    }

    public void showOnGoingNotification(String title, String content) {
        Intent intent = new Intent(getIntent().getAction());
        Bundle bundle = new Bundle();
        onSaveFloatBoxState(bundle);
        bundle.putBoolean("isDial", isDial);
        intent.putExtra("floatbox", bundle);
        intent.putExtra("callAction", RongCallAction.ACTION_RESUME_CALL.getName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtil.showNotification(this, title, content, pendingIntent, CALL_NOTIFICATION_ID,
                Notification.DEFAULT_LIGHTS);
    }

    @TargetApi(23)
    boolean requestCallPermissions(RongCallCommon.CallMediaType type, int requestCode) {
        String[] permissions = null;
        Log.i(TAG, "BaseActivty requestCallPermissions requestCode=" + requestCode);
        if (type.equals(RongCallCommon.CallMediaType.VIDEO) || type.equals(RongCallCommon.CallMediaType.AUDIO)) {
            permissions = CallKitUtils.getCallpermissions();
        }
        boolean result = false;
        if (permissions != null) {
            boolean granted = CallKitUtils.checkPermissions(this, permissions);
            Log.i(TAG, "BaseActivty requestCallPermissions granted=" + granted);
            if (granted) {
                result = true;
            } else {
                PermissionCheckUtil.requestPermissions(this, permissions, requestCode);
            }
        }
        return result;
    }

    private class UpdateTimeRunnable implements Runnable {
        private TextView timeView;

        public UpdateTimeRunnable(TextView timeView) {
            this.timeView = timeView;
        }

        @Override
        public void run() {
            time++;
            if (time >= 3600) {
                timeView.setText(String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60)));
            } else {
                timeView.setText(String.format("%02d:%02d", (time % 3600) / 60, (time % 60)));
            }
            handler.postDelayed(this, 1000);
        }
    }

    void onMinimizeClick(View view) {
        if (checkDrawOverlaysPermission(true)) {
            finish();
        } else {
            Toast.makeText(this, getString(R.string.rc_voip_float_window_not_allowed), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkDrawOverlaysPermission(boolean needOpenPermissionSetting) {
        if (Build.BRAND.toLowerCase().contains("xiaomi") || Build.VERSION.SDK_INT >= 23) {
            if (PermissionCheckUtil.canDrawOverlays(this, needOpenPermissionSetting)) {
                checkingOverlaysPermission = false;
                return true;
            } else {
                if (needOpenPermissionSetting && !Build.BRAND.toLowerCase().contains("xiaomi")) {
                    checkingOverlaysPermission = true;
                }
                return false;
            }
        } else {
            checkingOverlaysPermission = false;
            return true;
        }
    }

    private void createPowerManager() {
        if (powerManager == null) {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock =
                    powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,
                            TAG);
            wakeLock.setReferenceCounted(false);
            screenLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            screenLock.setReferenceCounted(false);
        }
    }

    protected void createPickupDetector() {
        if (pickupDetector == null) {
            pickupDetector = new PickupDetector(this);
        }
    }

    @Override
    public void onPickupDetected(boolean isPickingUp) {
        if (screenLock == null) {
            RLog.d(TAG, "No PROXIMITY_SCREEN_OFF_WAKE_LOCK");
            return;
        }
        if (isPickingUp && !screenLock.isHeld()) {
            screenLock.acquire();
        }
        if (!isPickingUp && screenLock.isHeld()) {
            try {
                screenLock.setReferenceCounted(false);
                screenLock.release();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (!PermissionCheckUtil.checkPermissions(this, permissions)) {
            PermissionCheckUtil.showRequestPermissionFailedAlter(this,
                    PermissionCheckUtil.getNotGrantedPermissionMsg(this, permissions, grantResults));
        }
    }

    /**
     * ??????????????????????????? ???????????????
     */
    private boolean isVibrateWhenRinging() {
        ContentResolver resolver = getApplicationContext().getContentResolver();
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            return Settings.System.getInt(resolver, "vibrate_in_normal", 0) == 1;
        } else if (Build.MANUFACTURER.equals("smartisan")) {
            return Settings.Global.getInt(resolver, "telephony_vibration_enabled", 0) == 1;
        } else {
            return Settings.System.getInt(resolver, "vibrate_when_ringing", 0) == 1;
        }
    }

    public void openSpeakerphoneNoWiredHeadsetOn() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager.isWiredHeadsetOn()) {
            RongCallClient.getInstance().setEnableSpeakerphone(false);
        } else {
            RongCallClient.getInstance().setEnableSpeakerphone(true);
        }
    }

    /**
     * outgoing ???initView???incoming?????????
     */
    public void regisHeadsetPlugReceiver() {
        if (BluetoothUtil.isSupportBluetooth()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
            headsetPlugReceiver = new HeadsetPlugReceiver();
            registerReceiver(headsetPlugReceiver, intentFilter);
        }
    }

    /**
     * onHangupBtnClick onDestory ?????????
     */
    public void unRegisterHeadsetplugReceiver() {
        if (headsetPlugReceiver != null) {
            unregisterReceiver(headsetPlugReceiver);
            headsetPlugReceiver = null;
        }
    }

    /**
     * ???????????????????????????????????????<br />
     * ?????????{@link RongCallClient#startCall} ??? {@link RongCallClient#acceptCall(String)}???????????? <br />
     */
    public void audioVideoConfig() {
        RongRTCConfig.Builder configBuilder = new RongRTCConfig.Builder();
        configBuilder.setVideoProfile(RongRTCConfig.RongRTCVideoProfile.RONGRTC_VIDEO_PROFILE_480P_15f_1);
        configBuilder.setMaxRate(1000);
        configBuilder.setMinRate(350);
        /*
         * ???????????? Https ???????????????????????????????????????
         * ????????????????????????????????????????????????????????????????????????????????????????????????
         */
        //configBuilder.enableHttpsSelfCertificate(true);
        RongCallClient.getInstance().setRTCConfig(configBuilder);
    }
}
