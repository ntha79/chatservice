package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;

/**
 * Created by UserName on 6/9/2018.
 */
public class DeleteGroupVM {
    private Long ownerId;

    private String groupId;

    public DeleteGroupVM(DeleteGroupVM updateGroupVM) {
        this.ownerId = updateGroupVM.getOwnerId();
        this.groupId = updateGroupVM.getGroupId();
    }

    public DeleteGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "DeleteGroupVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            '}';
    }
}
