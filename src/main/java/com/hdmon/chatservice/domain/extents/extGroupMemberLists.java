package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.GroupMemberRole;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatus;

import java.io.Serializable;
import java.time.Instant;

public class extGroupMemberLists implements Serializable {
    private Long memberId;
    private GroupMemberRole memberRole;
    private GroupMemberStatus memberStatus;
    private Instant joinTime;
    private Instant leaveTime;

    public extGroupMemberLists(){
        super();
    }

    public extGroupMemberLists(long memberId, GroupMemberRole memberRole, GroupMemberStatus memberStatus, Instant joinTime, Instant leaveTime)
    {
        this.memberId = memberId;
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
        this.joinTime = joinTime;
        this.leaveTime = leaveTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public GroupMemberRole getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(GroupMemberRole memberRole) {
        this.memberRole = memberRole;
    }

    @Override
    public String toString() {
        return "extGroupMemberLists{"
            + "memberId=" + memberId + ","
            + "memberRole=" + memberRole + ","
            + "memberStatus=" + memberStatus + ","
            + "joinTime=" + joinTime + ","
            + "leaveTime=" + leaveTime
            + "}";
    }
}

