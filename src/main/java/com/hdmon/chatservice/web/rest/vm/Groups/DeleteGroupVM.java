package com.hdmon.chatservice.web.rest.vm.Groups;

/**
 * Created by UserName on 6/9/2018.
 */
public class DeleteGroupVM {
    private String ownerUsername;
    private String groupId;

    public DeleteGroupVM(DeleteGroupVM updateGroupVM) {
        this.ownerUsername = updateGroupVM.getOwnerUsername();
        this.groupId = updateGroupVM.getGroupId();
    }

    public DeleteGroupVM(String ownerUsername, String groupId) {
        this.ownerUsername = ownerUsername;
        this.groupId = groupId;
    }

    public DeleteGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public DeleteGroupVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getGroupId() {
        return groupId;
    }

    public DeleteGroupVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "DeleteGroupVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            '}';
    }
}
