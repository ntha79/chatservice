package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.PushNotificationToMemberStatus;

import java.io.Serializable;

public class extPushNotificationToMemberLists implements Serializable {
    private Long memberId;
    private String memberLogin;
    private PushNotificationToMemberStatus status;

    public extPushNotificationToMemberLists()
    {
        super();
    }

    public extPushNotificationToMemberLists(Long memberId, String memberLogin, PushNotificationToMemberStatus status)
    {
        this.memberId = memberId;
        this.memberLogin = memberLogin;
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberLogin() {
        return memberLogin;
    }

    public void setMemberLogin(String memberLogin) {
        this.memberLogin = memberLogin;
    }

    public PushNotificationToMemberStatus getStatus() {
        return status;
    }

    public void setStatus(PushNotificationToMemberStatus status) {
        this.status = status;
    }
}
