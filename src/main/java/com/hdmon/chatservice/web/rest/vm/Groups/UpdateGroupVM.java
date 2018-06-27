package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;

/**
 * Created by UserName on 6/9/2018.
 */
public class UpdateGroupVM {
    private Long ownerId;

    private String groupId;
    private GroupTypeEnum groupType;
    private String groupName;
    private String groupIcon = "";
    private String groupSlogan = "";
    private String groupAbout = "";

    public UpdateGroupVM(UpdateGroupVM updateGroupVM) {
        this.ownerId = updateGroupVM.getOwnerId();
        this.groupId = updateGroupVM.getGroupId();
        this.groupType = updateGroupVM.getGroupType();
        this.groupName = updateGroupVM.getGroupName();
        this.groupIcon = updateGroupVM.getGroupIcon();
        this.groupSlogan = updateGroupVM.getGroupSlogan();
        this.groupAbout = updateGroupVM.getGroupAbout();
    }

    public UpdateGroupVM() {
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

    /**
     * PUBLIC, FANPAGE, SECRET, PEERTOPEER
     */
    public GroupTypeEnum getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public String getGroupAbout() {
        return groupAbout;
    }

    public void setGroupAbout(String groupAbout) {
        this.groupAbout = groupAbout;
    }

    @Override
    public String toString() {
        return "GroupMembersVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", GroupType='" + getGroupType() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", GroupIcon='" + getGroupIcon() + '\'' +
            ", GroupSlogan='" + getGroupSlogan() + '\'' +
            ", GroupAbout='" + getGroupAbout() + '\'' +
            '}';
    }
}
