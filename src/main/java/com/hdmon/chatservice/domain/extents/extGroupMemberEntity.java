package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.GroupMemberRoleEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;

import java.io.Serializable;
import java.time.Instant;

public class extGroupMemberEntity implements Serializable {
    private Long memberId;
    private GroupMemberRoleEnum memberRole;
    private GroupMemberStatusEnum memberStatus;
    private Instant joinTime;
    private Instant leaveTime;

    public extGroupMemberEntity(){
        super();
    }

    public extGroupMemberEntity(long memberId, GroupMemberRoleEnum memberRole, GroupMemberStatusEnum memberStatus, Instant joinTime, Instant leaveTime)
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

    public GroupMemberRoleEnum getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(GroupMemberRoleEnum memberRole) {
        this.memberRole = memberRole;
    }

    public GroupMemberStatusEnum getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(GroupMemberStatusEnum memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Instant getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Instant joinTime) {
        this.joinTime = joinTime;
    }

    public Instant getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Instant leaveTime) {
        this.leaveTime = leaveTime;
    }

    @Override
    public String toString() {
        return "extGroupMemberEntity{"
            + "MemberId=" + getMemberId() + ","
            + "MemberRole=" + getMemberRole() + ","
            + "MemberStatus=" + getMemberStatus() + ","
            + "JoinTime=" + getJoinTime() + ","
            + "LeaveTime=" + getLeaveTime() + ","
            + "}";
    }
}

