package com.juntai.disabled.federation.home_page.camera.ijkplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.federation.AppNetModule;
import com.juntai.disabled.federation.bean.stream.OpenLiveBean;

public class KeepAliveService extends Service {

    private String sessionId;
    private Thread thread;
    private boolean isDeatrory = false;

    public KeepAliveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("444444", "流媒体保活服务启动");
        sessionId = intent.getStringExtra("sessionId");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AppNetModule.createrRetrofit()
                        .keepAlive(sessionId)
//                        .compose(RxScheduler.ObsIoMain(null))
                        .subscribe(new BaseObserver<OpenLiveBean>(null) {
                            @Override
                            public void onSuccess(OpenLiveBean o) {
                                Log.d("444444", o.getErrcode() + "流媒体保活服务");
                                if (!isDeatrory) {
                                    run();
                                }

                            }

                            @Override
                            public void onError(String msg) {

                            }
                        });
            }

        });
        thread.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("444444", "流媒体保活服务杀死");
        isDeatrory = true;
        thread.interrupt();
    }
}
