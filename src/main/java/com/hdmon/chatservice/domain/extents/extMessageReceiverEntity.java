package com.hdmon.chatservice.domain.extents;

import java.util.Date;

public class extMessageReceiverEntity {
    private Long receiverId;
    private String receiverLogin;
    private Long updateUnixTime = new Date().getTime();

    public extMessageReceiverEntity()
    {
        super();
    }

    public extMessageReceiverEntity(Long receiverId, String receiverLogin, Long updateUnixTime)
    {
        this.receiverId = receiverId;
        this.receiverLogin = receiverLogin;
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
            + "UpdateUnixTime=" + getUpdateUnixTime() + ","
            + "}";
    }
}
