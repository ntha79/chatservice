package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.MessageReceiverStatus;

public class extMessageReceiverLists {
    private Long receiverId;
    private String receiverLogin;
    private MessageReceiverStatus status;
    private Long updateUnixTime;

    public extMessageReceiverLists()
    {
        super();
    }

    public extMessageReceiverLists(Long receiverId, String receiverLogin, MessageReceiverStatus status, Long updateUnixTime)
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

    public MessageReceiverStatus getStatus() {
        return status;
    }

    public void setStatus(MessageReceiverStatus status) {
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
        return "extMessageReceiverLists{"
            + "ReceiverId=" + receiverId + ","
            + "ReceiverLogin=" + receiverLogin + ","
            + "Status=" + status + ","
            + "UpdateUnixTime=" + updateUnixTime + ","
            + "}";
    }
}
