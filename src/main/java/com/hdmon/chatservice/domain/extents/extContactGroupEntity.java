package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.ContactStatusEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;

/**
 * Created by UserName on 6/7/2018.
 */
public class extContactGroupEntity {
    private String groupId;
    private String groupName;
    private GroupMemberStatusEnum status;

    public extContactGroupEntity(){
        super();
    }

    public extContactGroupEntity(extContactGroupEntity entity)
    {
        this.groupId = entity.getGroupId();
        this.groupName = entity.getGroupName();
        this.status = entity.getStatus();
    }

    public extContactGroupEntity(String groupId, String groupName, GroupMemberStatusEnum status)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public GroupMemberStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GroupMemberStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "extContactGroupEntity{"
            + "GroupId=" + getGroupId() + ","
            + "GroupName=" + getGroupName() + ","
            + "Status=" + getStatus() + ","
            + "}";
    }
}
