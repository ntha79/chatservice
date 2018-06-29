package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;

/**
 * Created by UserName on 6/9/2018.
 */
public class UpdateGroupVM {
    private String ownerUsername;

    private String groupId;
    private String groupName;
    private String groupIcon = "";
    private String groupBackground = "";
    private String groupSlogan = "";
    private String groupSumary = "";

    public UpdateGroupVM(UpdateGroupVM updateGroupVM) {
        this.ownerUsername = updateGroupVM.getOwnerUsername();
        this.groupId = updateGroupVM.getGroupId();
        this.groupName = updateGroupVM.getGroupName();
        this.groupIcon = updateGroupVM.getGroupIcon();
        this.groupBackground = updateGroupVM.getGroupBackground();
        this.groupSlogan = updateGroupVM.getGroupSlogan();
        this.groupSumary = updateGroupVM.getGroupSumary();
    }

    public UpdateGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public UpdateGroupVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getGroupId() {
        return groupId;
    }

    public UpdateGroupVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public UpdateGroupVM groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public UpdateGroupVM groupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupBackground() {
        return groupBackground;
    }

    public UpdateGroupVM groupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
        return this;
    }

    public void setGroupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public UpdateGroupVM groupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
        return this;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public String getGroupSumary() {
        return groupSumary;
    }

    public UpdateGroupVM groupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
        return this;
    }

    public void setGroupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
    }

    @Override
    public String toString() {
        return "GroupMembersVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", GroupIcon='" + getGroupIcon() + '\'' +
            ", GroupBackground='" + getGroupBackground() + '\'' +
            ", GroupSlogan='" + getGroupSlogan() + '\'' +
            ", GroupSumary='" + getGroupSumary() + '\'' +
            '}';
    }
}
