package com.juntai.disabled.federation;


import com.juntai.disabled.basecomponent.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModule {
    static com.juntai.disabled.federation.AppServer appServer ;
    public static com.juntai.disabled.federation.AppServer createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(com.juntai.disabled.federation.AppServer.class);
        }
        return appServer;
    }
}
