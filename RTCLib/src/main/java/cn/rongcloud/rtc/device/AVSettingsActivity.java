package cn.rongcloud.rtc.device;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.rtc.CenterManager;
import cn.rongcloud.rtc.LoadDialog;
import cn.rongcloud.rtc.R;
import cn.rongcloud.rtc.config.RongCenterConfig;
import cn.rongcloud.rtc.device.adapter.AVSettingsDataSource;
import cn.rongcloud.rtc.device.adapter.AVSettingsParameterAdapter;
import cn.rongcloud.rtc.device.adapter.ItemDecoration;
import cn.rongcloud.rtc.device.entity.AVConfigInfo;
import cn.rongcloud.rtc.device.entity.CodecInfo;
import cn.rongcloud.rtc.device.entity.EventBusInfo;
import cn.rongcloud.rtc.device.entity.MediaType;
import cn.rongcloud.rtc.device.utils.FileUtils;
import cn.rongcloud.rtc.device.utils.OnItemClickListener;
import cn.rongcloud.rtc.stream.local.RongRTCLocalSourceManager;
import cn.rongcloud.rtc.util.SessionManager;
import cn.rongcloud.rtc.util.Utils;
import cn.rongcloud.rtc.utils.BuildVersion;
import cn.rongcloud.rtc.utils.FinLog;
import cn.rongcloud.rtc.utils.RongRTCSessionManager;
import cn.rongcloud.rtc.utils.debug.RTCCodecInfo;

import static cn.rongcloud.rtc.device.utils.Consts.*;

public class AVSettingsActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "AVSettingsActivity";
    private TabLayout mTableLayout;
    private LinearLayout mLinear_audioLayout, mLinear_videoLayout;
    private RecyclerView mRecyclerView;
    private List<AVConfigInfo> avConfigInfoList = new ArrayList<>();
    private AVSettingsParameterAdapter mAdapter;
    private static final String TABLAYOUT_TEXT_VIDEO = "??????";
    private static final String TABLAYOUT_TEXT_AUDIO = "??????";
    private String mCurrentTabLayout = TABLAYOUT_TEXT_VIDEO;
    private ArrayList<CodecInfo> mEncoderInfoList = new ArrayList<>();
    private ArrayList<CodecInfo> mDecoderInfoList = new ArrayList<>();

    private final int REQUEST_CODE_SETTING_PREVIEW = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avsettings);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
            super.setSupportActionBar(toolbar);
        }

        LoadDialog.show(AVSettingsActivity.this);
        initView();
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTabLayout.equals(tab.getText())) {
                    return;
                }
                mCurrentTabLayout = tab.getText().toString();
                boolean isCurrentVideoTab = mCurrentTabLayout.equals(TABLAYOUT_TEXT_VIDEO);
                showTabLayout(isCurrentVideoTab);
                if (isCurrentVideoTab) {
                    initEncoderDataList();
                } else {
                    initAudioCaptureDataList();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LoadDialog.dismiss(AVSettingsActivity.this);

        SessionManager.getInstance().put(getResources().getString(R.string.key_use_av_setting), true);      // ??????setting???????????????????????????
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_av_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting_menu_reset){
            AVSettingsDataSource.getInstance().resetAudioConfig();
            AVSettingsDataSource.getInstance().resetVideoConfig();

            avConfigInfoList.clear();
            avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getCurrentConfig());
            mAdapter.notifyDataSetChanged();

            Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.setting_menu_preview){
            Intent intent = new Intent(this, AVSettingsPreviewActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTING_PREVIEW);
        }else if (item.getItemId() == R.id.setting_menu_export){
            exportAVSettings();
        }else if (item.getItemId() == android.R.id.home){

        }else{
            AVSettingsDataSource.getInstance().reloadConfig();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportAVSettings() {
        if (null == CenterManager.getInstance().getCenterConfig()) {
            return;
        }
        String avParameters = CenterManager.getInstance().getCenterConfig().toJsonString();

        String platformString = null;
        try {
            JSONObject platformJson = new JSONObject();
            platformJson.put("deviceModel", Build.MODEL);
            platformJson.put("deviceVersion", Build.VERSION.SDK_INT);
            platformJson.put("sdkVersion", BuildVersion.SDK_VERSION);
            platformString = platformJson.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileUtils.saveText(this.getApplicationContext(), "", "platform.json", platformString);
        FileUtils.saveText(this.getApplicationContext(), "", "AVParameters.json", avParameters);
        Toast.makeText(this, "???????????????????????? /sdcard/SealRTC/", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mLinear_audioLayout = (LinearLayout) findViewById(R.id.linear_audioLayout);
        mLinear_videoLayout = (LinearLayout) findViewById(R.id.linear_videoLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mTableLayout = (TabLayout) findViewById(R.id.tablayout);
        mTableLayout.addTab(mTableLayout.newTab().setText(TABLAYOUT_TEXT_VIDEO));
        mTableLayout.addTab(mTableLayout.newTab().setText(TABLAYOUT_TEXT_AUDIO));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AVSettingsParameterAdapter(avConfigInfoList);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration(this, 1));
        initEncoderDataList();
        showTabLayout(true);
        mAdapter.setOnItemClickListener(this);

        String codecInfoJson = RTCCodecInfo.getInstance().getMediaCodecInfo();

        if (!TextUtils.isEmpty(codecInfoJson)) {
            Gson gson = new Gson();
            List<CodecInfo> codecInfoList = gson.fromJson(codecInfoJson, new TypeToken<List<CodecInfo>>() {
            }.getType());
            for (int i = 0; i < codecInfoList.size(); i++) {
                if (codecInfoList.get(i).getCodecName().indexOf("decoder") != -1) {
                    mDecoderInfoList.add(codecInfoList.get(i));
                } else {
                    mEncoderInfoList.add(codecInfoList.get(i));
                }
            }
        }

        TextView textViewSysInfo = (TextView) findViewById(R.id.textView_sys_info);
        textViewSysInfo.setText("model-" + Build.MODEL + " sdk-" + Build.VERSION.SDK_INT + " rtcLib-" + BuildVersion.SDK_VERSION);
    }

    public void click(View view) {
        if (view.getId() == R.id.btn_encoder){
            initEncoderDataList();
        }else if (view.getId() == R.id.btn_decoder){
            initDecoderDataList();
        }else if (view.getId() == R.id.btn_capture){
            initCaptureDataList();
        }else if (view.getId() == R.id.btn_audio_capture){
            initAudioCaptureDataList();
        }else if (view.getId() == R.id.btn_audio_agc){
            initAudioAgcDataList();
        }else if (view.getId() == R.id.btn_audio_noise_suppression){
            initAudioNSDataList();
        }else if (view.getId() == R.id.btn_audio_echo_cancel){
            initAudioECDataList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_CODE_SETTING_PREVIEW) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private void initEncoderDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getVideoEncoderConfig());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * ??????????????????????????????
     */
    private void initDecoderDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getVideoDecoderConfig());
        mAdapter.notifyDataSetChanged();
    }

    private void initCaptureDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getVideoCameraConfig());
        mAdapter.notifyDataSetChanged();
    }

    private void initAudioCaptureDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getAudioCaptureConfig());
        mAdapter.notifyDataSetChanged();
    }

    private void initAudioAgcDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getAudioAgcConfig());
        mAdapter.notifyDataSetChanged();
    }

    private void initAudioNSDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getAudioNsConfig());
        mAdapter.notifyDataSetChanged();
    }

    private void initAudioECDataList() {
        avConfigInfoList.clear();
        avConfigInfoList.addAll(AVSettingsDataSource.getInstance().getAudioEcConfig());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadDialog.dismiss(AVSettingsActivity.this);
        EventBus.getDefault().removeStickyEvent(decoder_colorFormat_eventBus);
        EventBus.getDefault().removeStickyEvent(encoder_colorFormat_eventBus);
        EventBus.getDefault().unregister(this);
    }

    /**
     * ?????????/???????????? ???????????????????????????????????? ????????????
     *
     * @param codecInfos
     * @param codecName
     */
    private ArrayList<MediaType> getMediaTypes(ArrayList<CodecInfo> codecInfos, String codecName) {
        ArrayList<MediaType> tmpMediaTypes = new ArrayList<>();
        for (int i = 0; i < codecInfos.size(); i++) {
            if (codecInfos.get(i).getCodecName().equals(codecName)) {
                tmpMediaTypes.addAll(codecInfos.get(i).getMediaTypes());
                break;
            }
        }
        return tmpMediaTypes;
    }

    /**
     * ???????????? ?????? ??????
     *
     * @param video true?????????
     */
    private void showTabLayout(boolean video) {
        mLinear_audioLayout.setVisibility((!video) ? View.VISIBLE : View.GONE);
        mLinear_videoLayout.setVisibility(video ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(int position) {
        if (avConfigInfoList == null || avConfigInfoList.size() == 0) {
            FinLog.e(TAG, "avConfigInfoList = null.");
            return;
        }
        AVConfigInfo info = avConfigInfoList.get(position);
        if (info == null) {
            FinLog.e(TAG, "AVConfigInfo = null !");
            return;
        }

        if (info.getRequestCode() == REQUEST_CODE_CAMERA_DISPLAY_ORIENTATION ||
                info.getRequestCode() == REQUEST_CODE_FRAME_ORIENTATION ||
                info.getRequestCode() == REQUEST_CODE_AUDIO_SAMPLE_RATE ||
                info.getRequestCode() == REQUEST_AUDIO_TRANSPORT_BIT_RATE ||
                info.getRequestCode() == REQUEST_AUDIO_AGC_TARGET_DBOV ||
                info.getRequestCode() == REQUEST_AUDIO_AGC_COMPRESSION_LEVEL ||
                info.getRequestCode() == REQUEST_AUDIO_PRE_AMPLIFIER_LEVEL) {//????????????????????????????????????????????????
            SettingInputActivity.startActivity(AVSettingsActivity.this, info.getRequestCode());
            return;
        }

        //???????????????????????????
        if (info.getRequestCode() == REQUEST_CODE_ENCODER_NAME && !AVSettingsDataSource.getInstance().isEncoderHardMode()) {
            Toast.makeText(AVSettingsActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if (info.getRequestCode() == REQUEST_CODE_ENCODER_COLOR_FORMAT && !AVSettingsDataSource.getInstance().isEncoderHardMode()) {
            Toast.makeText(AVSettingsActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        //???????????????????????????
        if (info.getRequestCode() == REQUEST_CODE_DECODER_NAME && !AVSettingsDataSource.getInstance().isDecoderHardMode()) {
            Toast.makeText(AVSettingsActivity.this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (info.getRequestCode() == REQUEST_CODE_DECODER_COLOR_FORMAT && !AVSettingsDataSource.getInstance().isDecoderHardMode()) {
            Toast.makeText(AVSettingsActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        String enCodecName = AVSettingsDataSource.getInstance().getItemConfig(AVSettingsDataSource.SettingCategory.VideoEncoder, REQUEST_CODE_ENCODER_NAME);
        if (info.getRequestCode() == REQUEST_CODE_ENCODER_COLOR_FORMAT && TextUtils.isEmpty(enCodecName)) {
            Toast.makeText(AVSettingsActivity.this, "?????????????????????!", Toast.LENGTH_SHORT).show();
            return;
        }
        String deCodecName = AVSettingsDataSource.getInstance().getItemConfig(AVSettingsDataSource.SettingCategory.VideoDecoder, REQUEST_CODE_DECODER_NAME);
        if (info.getRequestCode() == REQUEST_CODE_DECODER_COLOR_FORMAT && TextUtils.isEmpty(deCodecName)) {
            Toast.makeText(AVSettingsActivity.this, "?????????????????????!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (info.getRequestCode() == REQUEST_CODE_ENCODER_COLOR_FORMAT ||
                info.getRequestCode() == REQUEST_CODE_DECODER_COLOR_FORMAT) {
            //??????????????????????????????????????????
            //???????????????????????? ???CodecType.equals("1")?"???????????????":"???????????????"
            String codecType = "";
            String codecName = "";
            ArrayList<MediaType> mediaTypeArrayList = null;
            if (info.getRequestCode() == REQUEST_CODE_ENCODER_COLOR_FORMAT) {
                mediaTypeArrayList = getMediaTypes(mEncoderInfoList, enCodecName);
                codecType = "0";
                codecName = enCodecName;
            } else if (info.getRequestCode() == REQUEST_CODE_DECODER_COLOR_FORMAT) {
                mediaTypeArrayList = getMediaTypes(mDecoderInfoList, deCodecName);
                codecType = "1";
                codecName = deCodecName;
            }
            CodecColorFormatActivity.startActivity(AVSettingsActivity.this, mediaTypeArrayList, codecType, codecName);
            return;
        }
        if(info.getRequestCode() == REQUEST_CODE_AUDIO_SOURCE){
            AudioSourceSelectActivity.startActivity(this, info.getItemRealValue());
        }
    }

    /**
     * ??????????????????????????? ??? ?????????????????? ?????????adapter
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBusComplaint(EventBusInfo info) {
        if (info == null) {
            return;
        }
        String content = info.getContent();
        int requestCode = info.getRequestCode();

        AVConfigInfo findConfigItem = getAvConfigInfo(requestCode);

        if (findConfigItem != null && !TextUtils.equals(findConfigItem.getItemValue(), info.getContent())) {

            findConfigItem.setItemValue(content);
            findConfigItem.setItemRealValue(info.getRealyValue());
            mAdapter.notifyItemChanged(avConfigInfoList.indexOf(findConfigItem));

            String sw_encoder = Utils.getContext().getResources().getString(R.string.soft_encoder_str);
            String sw_decoder = Utils.getContext().getResources().getString(R.string.soft_decoder_str);
            if (requestCode == REQUEST_CODE_ENCODER_TYPE && TextUtils.equals(sw_encoder, content)) {
                resetConfigItem(REQUEST_CODE_ENCODER_COLOR_FORMAT);
            }
            if (requestCode == REQUEST_CODE_DECODER_TYPE && TextUtils.equals(sw_decoder, content)) {
                resetConfigItem(REQUEST_CODE_DECODER_COLOR_FORMAT);
            }

            // ??????????????? ??????-??????????????????????????????
            if (requestCode == REQUEST_CODE_ENCODER_NAME && TextUtils.equals(mCurrentTabLayout, TABLAYOUT_TEXT_VIDEO)) {
                resetConfigItem(REQUEST_CODE_ENCODER_COLOR_FORMAT);
            }
        } else {
            FinLog.e(TAG, "position = -1");
        }
    }

    private void resetConfigItem(int requestCodeEncoderName) {
        AVConfigInfo configItem = getAvConfigInfo(requestCodeEncoderName);
        if (configItem != null) {
            configItem.setItemValue(null);
            int idx = avConfigInfoList.indexOf(configItem);
            if (idx > 0 && idx < avConfigInfoList.size()) {
                mAdapter.notifyItemChanged(idx);
            }
        }
    }

    private AVConfigInfo getAvConfigInfo(int requestCode) {
        AVConfigInfo findConfigItem = null;
        for (int i = 0; i < avConfigInfoList.size(); i++) {
            if (avConfigInfoList.get(i).getRequestCode() == requestCode) {
                findConfigItem = avConfigInfoList.get(i);
                break;
            }
        }
        return findConfigItem;
    }
}