package com.juntai.disabled.im.ry.listener;

import com.juntai.disabled.basecomponent.utils.LogUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * 发送消息监听
 * @aouther Ma
 * @date 2019/4/7
 */
public class MySendMessageListener implements RongIM.OnSendMessageListener {

    private static final String TAG = "RongYun 发送消息监听 ";

    /**
     * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
     *
     * @param message 发送的消息实例。
     * @return 处理后的消息实例。
     */
    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        return message;
    }

    /**
     * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
     *
     * @param message              消息实例。
     * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
     * @return true 表示走自己的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

        if(message.getSentStatus()== Message.SentStatus.FAILED){
            if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_CHATROOM){
                LogUtil.d("NOT_IN_CHATROOM");
                //不在聊天室
            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION){
                //不在讨论组
                LogUtil.d("NOT_IN_DISCUSSION");

            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_GROUP){
                //不在群组
                LogUtil.d("NOT_IN_GROUP");

            }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST){
                //你在他的黑名单中
                LogUtil.d("REJECTED_BY_BLACKLIST");
            }else {
                LogUtil.d("发送失败-->"+sentMessageErrorCode);
            }
        }

        MessageContent messageContent = message.getContent();

        if (messageContent instanceof TextMessage) {//文本消息
            TextMessage textMessage = (TextMessage) messageContent;
            LogUtil.d(TAG+"onSent-TextMessage:" + textMessage.getContent());
        } else if (messageContent instanceof ImageMessage) {//图片消息
            ImageMessage imageMessage = (ImageMessage) messageContent;
            LogUtil.d(TAG+"onSent-ImageMessage:" + imageMessage.getRemoteUri());
        } else if (messageContent instanceof VoiceMessage) {//语音消息
            VoiceMessage voiceMessage = (VoiceMessage) messageContent;
            LogUtil.d(TAG+"onSent-voiceMessage:" + voiceMessage.getUri().toString());
        } else if (messageContent instanceof RichContentMessage) {//图文消息
            RichContentMessage richContentMessage = (RichContentMessage) messageContent;
            LogUtil.d(TAG+"onSent-RichContentMessage:" + richContentMessage.getContent());
        } else {
            LogUtil.d(TAG+"onSent-其他消息，自己来判断处理");
        }

        return false;
    }
}