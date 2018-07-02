package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.enumeration.ChatMessageStatusEnum;

/**
 * Created by UserName on 6/30/2018.
 */
public class UpdateMessageVM {
    private String msgId;
    private String actionUserName;
    private ChatMessageStatusEnum msgStatus;
    private String changeTime;

    public UpdateMessageVM()
    {
        super();
    }

    public UpdateMessageVM(UpdateMessageVM entity)
    {
        this.msgId = entity.getMsgId();
        this.actionUserName = entity.getActionUserName();
        this.msgStatus = entity.getMsgStatus();
        this.changeTime = entity.getChangeTime();
    }

    public UpdateMessageVM(String msgId, String actionUserName, ChatMessageStatusEnum msgStatus, String changeTime)
    {
        this.msgId = msgId;
        this.actionUserName = actionUserName;
        this.msgStatus = msgStatus;
        this.changeTime = changeTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public UpdateMessageVM msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public UpdateMessageVM actionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
        return this;
    }

    public ChatMessageStatusEnum getMsgStatus() {
        return msgStatus;
    }

    public UpdateMessageVM msgStatus(ChatMessageStatusEnum msgStatus) {
        this.msgStatus = msgStatus;
        return this;
    }

    public void setMsgType(ChatMessageStatusEnum msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public UpdateMessageVM changeTime(String changeTime) {
        this.changeTime = changeTime;
        return this;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    @Override
    public String toString() {
        return "UpdateMessageVM{" +
            "MsgId='" + getMsgId() + '\'' +
            ", ActionUserName='" + getActionUserName() + '\'' +
            ", MsgStatus='" + getMsgStatus() + '\'' +
            ", ChangeTime='" + getChangeTime() + '\'' +
            '}';
    }
}
