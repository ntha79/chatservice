package com.hdmon.chatservice.web.rest.vm.Groups;

/**
 * Created by UserName on 6/7/2018.
 */
public class MembersLeaveGroupVM {
    private String actionUsername;
    private String groupId;

    public MembersLeaveGroupVM(MembersLeaveGroupVM membersVM) {
        this.actionUsername = membersVM.getActionUsername();
    }

    public MembersLeaveGroupVM(String actionUsername) {
        this.actionUsername = actionUsername;
    }

    public MembersLeaveGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getActionUsername() {
        return actionUsername;
    }

    public MembersLeaveGroupVM actionUsername(String actionUsername) {
        this.actionUsername = actionUsername;
        return this;
    }

    public void setActionUsername(String actionUsername) {
        this.actionUsername = actionUsername;
    }

    public String getGroupId() {
        return groupId;
    }

    public MembersLeaveGroupVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "MembersLeaveGroupVM{" +
            "ActionUsername='" + getActionUsername() + '\'' +
            "GroupId='" + getGroupId() + '\'' +
            '}';
    }
}
