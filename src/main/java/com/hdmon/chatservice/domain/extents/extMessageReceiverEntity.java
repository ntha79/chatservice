package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.MessageReceiverStatusEnum;

import java.util.Date;

public class extMessageReceiverEntity {
    private Long receiverId;
    private String receiverLogin;
    private MessageReceiverStatusEnum status = MessageReceiverStatusEnum.NEW;
    private Long updateUnixTime = new Date().getTime();

    public extMessageReceiverEntity()
    {
        super();
    }

    public extMessageReceiverEntity(Long receiverId, String receiverLogin, MessageReceiverStatusEnum status, Long updateUnixTime)
    {
        this.receiverId = receiverId;
        this.receiverLogin = receiverLogin;
        this.status = status;
        this.updateUnixTime = updateUnixTime;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    public MessageReceiverStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MessageReceiverStatusEnum status) {
        this.status = status;
    }

    public Long getUpdateUnixTime() {
        return updateUnixTime;
    }

    public void setUpdateUnixTime(Long updateUnixTime) {
        this.updateUnixTime = updateUnixTime;
    }

    @Override
    public String toString() {
        return "extMessageReceiverEntity{"
            + "ReceiverId=" + getReceiverId() + ","
            + "ReceiverLogin=" + getReceiverLogin() + ","
            + "Status=" + getStatus() + ","
            + "UpdateUnixTime=" + getUpdateUnixTime() + ","
            + "}";
    }
}
