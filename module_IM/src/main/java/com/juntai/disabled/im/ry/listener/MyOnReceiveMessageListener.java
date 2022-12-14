package com.juntai.disabled.im.ry.listener;

import android.content.Intent;

import com.juntai.disabled.basecomponent.app.BaseApplication;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.im.CustomCaseMessage;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 消息监听
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyOnReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {
        LogUtil.d("RongYun-消息监听---i ==" + i + "    " + message.toString());
        if(message.getConversationType() == Conversation.ConversationType.SYSTEM){
            if (message.getObjectName().equals("app:custom")){
                LogUtil.d("RongYun-消息监听---i =案件="+ ((CustomCaseMessage) message.getContent()).toString());
                CustomCaseMessage caseMessage = (CustomCaseMessage) message.getContent();
                Intent intent = new Intent();
                intent.setAction(ActionConfig.BROAD_CASE_DETAILS);
                intent.putExtra("id",caseMessage.getId().intValue());
                intent.putExtra("type",caseMessage.getGenre());
                intent.putExtra("content",caseMessage.getContent());
                BaseApplication.app.sendBroadcast(intent);
            }
        }
        return false;
    }
}
