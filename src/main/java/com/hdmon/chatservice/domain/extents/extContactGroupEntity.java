package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.ContactStatusEnum;

/**
 * Created by UserName on 6/7/2018.
 */
public class extContactGroupEntity {
    private String groupId;
    private String groupName;

    public extContactGroupEntity(){
        super();
    }

    public extContactGroupEntity(String groupId, String groupName)
    {
        this.groupId = groupId;
        this.groupName = groupName;
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

    @Override
    public String toString() {
        return "extContactGroupEntity{"
            + "GroupId=" + getGroupId() + ","
            + "GroupName=" + getGroupName() + ","
            + "}";
    }
}
