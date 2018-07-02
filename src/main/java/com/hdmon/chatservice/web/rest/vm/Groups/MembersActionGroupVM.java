package com.hdmon.chatservice.web.rest.vm.Groups;

/**
 * Created by UserName on 6/7/2018.
 */
public class MembersActionGroupVM {
    private String memberUsername;

    public MembersActionGroupVM(MembersActionGroupVM membersVM) {
        this.memberUsername = membersVM.getMemberUsername();
    }

    public MembersActionGroupVM(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public MembersActionGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public MembersActionGroupVM memberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
        return this;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    @Override
    public String toString() {
        return "MembersActionGroupVM{" +
            "MemberUsername='" + getMemberUsername() + '\'' +
            '}';
    }
}
