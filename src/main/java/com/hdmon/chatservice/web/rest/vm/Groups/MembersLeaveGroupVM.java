package com.hdmon.chatservice.web.rest.vm.Groups;

/**
 * Created by UserName on 6/7/2018.
 */
public class MembersLeaveGroupVM {
    private Long memberId;
    private String groupId;

    public MembersLeaveGroupVM(MembersLeaveGroupVM membersVM) {
        this.memberId = membersVM.getMemberId();
    }

    public MembersLeaveGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "MembersLeaveGroupVM{" +
            "MemberId='" + getMemberId() + '\'' +
            "GroupId='" + getGroupId() + '\'' +
            '}';
    }
}
