package com.juntai.disabled.federation.entrance;

import com.juntai.disabled.basecomponent.mvp.BaseIView;

/**
 * @Author: tobato
 * @Description: 作用描述  APP入口
 * @CreateDate: 2020/3/5 15:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:53
 */
public interface EntranceContract {
    String BIND_QQ_OR_WECHAT = "bindQQOrWeChat";
    String LOGIN_TAG = "login";//登录的标识

    interface IEntranceView extends BaseIView {
    }

    interface IEntrancePresent {
        void login(String account, String password, String weChatId, String qqId, String tag);

        void bindQQOrWeChat(String account, String weChatId, String weChatName, String qqId, String qqName, String tag);
    }
}
