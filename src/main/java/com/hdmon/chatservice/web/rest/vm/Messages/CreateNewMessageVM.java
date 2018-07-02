package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.enumeration.ChatMessageStatusEnum;
import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;

/**
 * Created by UserName on 6/30/2018.
 */
public class CreateNewMessageVM {
    private String msgId;
    private String ownerUserName;
    private String fromUserName;
    private String fromFullName;
    private String toUserName;
    private ChatMessageTypeEnum msgType;
    private String message;
    private String sendTime;
    private String code;
    private GroupTypeEnum codeType;
    private String referMessageId;

    public CreateNewMessageVM()
    {
        super();
    }

    public CreateNewMessageVM(CreateNewMessageVM entity)
    {
        this.msgId = entity.getMsgId();
        this.ownerUserName = entity.getOwnerUserName();
        this.fromFullName = entity.getFromFullName();
        this.fromFullName = entity.getFromFullName();
        this.toUserName = entity.getToUserName();
        this.msgType = entity.getMsgType();
        this.message = entity.getMessage();
        this.sendTime = entity.getSendTime();
        this.code = entity.getCode();
        this.codeType = entity.getCodeType();
        this.referMessageId = entity.getReferMessageId();
    }

    public CreateNewMessageVM(String msgId, String ownerUserName, String fromUserName, String fromFullName, String toUserName, ChatMessageTypeEnum msgType, String message, String sendTime, String code, GroupTypeEnum codeType, String referMessageId)
    {
        this.msgId = msgId;
        this.ownerUserName = ownerUserName;
        this.fromFullName = fromUserName;
        this.fromFullName = fromFullName;
        this.toUserName = toUserName;
        this.msgType = msgType;
        this.message = message;
        this.sendTime = sendTime;
        this.code = code;
        this.codeType = codeType;
        this.referMessageId = referMessageId;
    }

    public String getMsgId() {
        return msgId;
    }

    public CreateNewMessageVM msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public CreateNewMessageVM ownerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
        return this;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public CreateNewMessageVM fromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromFullName() {
        return fromFullName;
    }

    public CreateNewMessageVM fromFullName(String fromFullName) {
        this.fromFullName = fromFullName;
        return this;
    }

    public void setFromFullName(String fromFullName) {
        this.fromFullName = fromFullName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public CreateNewMessageVM toUserName(String toUserName) {
        this.toUserName = toUserName;
        return this;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public ChatMessageTypeEnum getMsgType() {
        return msgType;
    }

    public CreateNewMessageVM msgType(ChatMessageTypeEnum msgType) {
        this.msgType = msgType;
        return this;
    }

    public void setMsgType(ChatMessageTypeEnum msgType) {
        this.msgType = msgType;
    }

    public String getMessage() {
        return message;
    }

    public CreateNewMessageVM message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendTime() {
        return sendTime;
    }

    public CreateNewMessageVM sendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getCode() {
        return code;
    }

    public CreateNewMessageVM code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GroupTypeEnum getCodeType() {
        return codeType;
    }

    public CreateNewMessageVM codeType(GroupTypeEnum codeType) {
        this.codeType = codeType;
        return this;
    }

    public void setCodeType(GroupTypeEnum codeType) {
        this.codeType = codeType;
    }

    public String getReferMessageId() {
        return referMessageId;
    }

    public CreateNewMessageVM referMessageId(String referMessageId) {
        this.referMessageId = referMessageId;
        return this;
    }

    public void setReferMessageId(String referMessageId) {
        this.referMessageId = referMessageId;
    }

    @Override
    public String toString() {
        return "CreateNewMessageVM{" +
            "MsgId='" + getMsgId() + '\'' +
            ", OwnerUserName='" + getOwnerUserName() + '\'' +
            ", FromUserName='" + getFromUserName() + '\'' +
            ", FromFullName='" + getFromFullName() + '\'' +
            ", ToUserName='" + getToUserName() + '\'' +
            ", MsgType='" + getMsgType() + '\'' +
            ", Message='" + getMessage() + '\'' +
            ", SendTime='" + getSendTime() + '\'' +
            ", Code='" + getCode() + '\'' +
            ", ReferMessageId='" + getReferMessageId() + '\'' +
            '}';
    }
}
