package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.GroupMemberRoleEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;

import java.io.Serializable;
import java.time.Instant;

public class extGroupMemberEntity implements Serializable {
    private Long memberId;
    private String memberUsername;
    private GroupMemberRoleEnum memberRole;
    private GroupMemberStatusEnum memberStatus;
    private Instant joinTime;
    private Instant leaveTime;
    private String actionNote;

    public extGroupMemberEntity(){
        super();
    }

    public extGroupMemberEntity(long memberId, String memberUsername, GroupMemberRoleEnum memberRole, GroupMemberStatusEnum memberStatus, Instant joinTime, Instant leaveTime, String actionNote)
    {
        this.memberId = memberId;
        this.memberUsername = memberUsername;
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
        this.joinTime = joinTime;
        this.leaveTime = leaveTime;
        this.actionNote = actionNote;
    }

    public Long getMemberId() {
        return memberId;
    }

    public extGroupMemberEntity memberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public extGroupMemberEntity memberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
        return this;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
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

    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }

    @Override
    public String toString() {
        return "extGroupMemberEntity{"
            + "MemberId=" + getMemberId() + ","
            + "MemberUsername=" + getMemberUsername() + ","
            + "MemberRole=" + getMemberRole() + ","
            + "MemberStatus=" + getMemberStatus() + ","
            + "JoinTime=" + getJoinTime() + ","
            + "LeaveTime=" + getLeaveTime() + ","
            + "ActionNote=" + getActionNote() + ","
            + "}";
    }
}

