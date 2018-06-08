package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.PushNotificationToMemberStatusEnum;

import java.io.Serializable;

public class extPushNotificationToMemberEntity implements Serializable {
    private Long memberId;
    private String memberLogin;
    private PushNotificationToMemberStatusEnum status;

    public extPushNotificationToMemberEntity()
    {
        super();
    }

    public extPushNotificationToMemberEntity(Long memberId, String memberLogin, PushNotificationToMemberStatusEnum status)
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

    public PushNotificationToMemberStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PushNotificationToMemberStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "extPushNotificationToMemberEntity{"
            + "MemberId=" + getMemberId() + ","
            + "MemberLogin=" + getMemberLogin() + ","
            + "Status=" + getStatus() + ","
            + "}";
    }
}
