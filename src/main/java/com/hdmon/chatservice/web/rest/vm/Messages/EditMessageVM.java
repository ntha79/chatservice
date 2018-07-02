package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;

/**
 * Created by UserName on 6/30/2018.
 */
public class EditMessageVM {
    private String msgId;
    private String actionUserName;
    private ChatMessageTypeEnum msgType;
    private String message;
    private String sendTime;
    private String code;

    public EditMessageVM()
    {
        super();
    }

    public EditMessageVM(EditMessageVM entity)
    {
        this.msgId = entity.getMsgId();
        this.actionUserName = entity.getActionUserName();
        this.msgType = entity.getMsgType();
        this.message = entity.getMessage();
        this.sendTime = entity.getSendTime();
        this.code = entity.getCode();
    }

    public EditMessageVM(String msgId, String actionUserName, ChatMessageTypeEnum msgType, String message, String sendTime, String code)
    {
        this.msgId = msgId;
        this.actionUserName = actionUserName;
        this.msgType = msgType;
        this.message = message;
        this.sendTime = sendTime;
        this.code = code;
    }

    public String getMsgId() {
        return msgId;
    }

    public EditMessageVM msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public EditMessageVM actionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
        return this;
    }

    public ChatMessageTypeEnum getMsgType() {
        return msgType;
    }

    public EditMessageVM msgType(ChatMessageTypeEnum msgType) {
        this.msgType = msgType;
        return this;
    }

    public void setMsgType(ChatMessageTypeEnum msgType) {
        this.msgType = msgType;
    }

    public String getMessage() {
        return message;
    }

    public EditMessageVM message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendTime() {
        return sendTime;
    }

    public EditMessageVM sendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getCode() {
        return code;
    }

    public EditMessageVM code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "EditMessageVM{" +
            "MsgId='" + getMsgId() + '\'' +
            ", ActionUserName='" + getActionUserName() + '\'' +
            ", MsgType='" + getMsgType() + '\'' +
            ", Message='" + getMessage() + '\'' +
            ", SendTime='" + getSendTime() + '\'' +
            ", Code='" + getCode() + '\'' +
            '}';
    }
}
