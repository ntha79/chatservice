package com.hdmon.chatservice.web.rest.vm.Groups;

/**
 * Created by UserName on 6/7/2018.
 */
public class MembersActionGroupVM {
    private Long memberId;

    public MembersActionGroupVM(MembersActionGroupVM membersVM) {
        this.memberId = membersVM.getMemberId();
    }

    public MembersActionGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "MembersActionGroupVM{" +
            "MemberId='" + getMemberId() + '\'' +
            '}';
    }
}
