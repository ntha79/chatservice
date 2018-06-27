package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;
import com.hdmon.chatservice.web.rest.vm.MembersVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 6/9/2018.
 */
public class CreateNewGroupVM {
    private Long ownerId;

    private GroupTypeEnum groupType;
    private String groupName;
    private String groupIcon = "";
    private String groupSlogan = "";
    private String groupAbout = "";

    public CreateNewGroupVM(GroupMembersVM groupMembersVM) {
        this.ownerId = groupMembersVM.getOwnerId();
        this.groupType = groupMembersVM.getGroupType();
        this.groupName = groupMembersVM.getGroupName();
        this.groupIcon = groupMembersVM.getGroupIcon();
        this.groupSlogan = groupMembersVM.getGroupSlogan();
        this.groupAbout = groupMembersVM.getGroupAbout();
    }

    public CreateNewGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
            ", GroupType='" + getGroupType() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", GroupIcon='" + getGroupIcon() + '\'' +
            ", GroupSlogan='" + getGroupSlogan() + '\'' +
            ", GroupAbout='" + getGroupAbout() + '\'' +
            '}';
    }
}
