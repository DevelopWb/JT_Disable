package com.juntai.disabled.federation.entrance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.juntai.disabled.basecomponent.utils.SPTools;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.entrance.guide.GuideActivity;
import com.juntai.disabled.federation.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @aouther Ma
 * @date 2019/3/13
 */
public class StartActivity extends RxAppCompatActivity {
    String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RxPermissions(this)
                .request(permissions)
                .delay(1, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //所有权限通过
                        } else {
                            //有一个权限没通过
                        }
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
//                        if (SPTools.getBoolean(StartActivity.this, "first_start", true)) {
//                            startActivity(new Intent(StartActivity.this, GuideActivity.class));
//                            finish();
//                        } else {
//                            startActivity(new Intent(StartActivity.this, MainActivity.class));
//                            finish();
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}
