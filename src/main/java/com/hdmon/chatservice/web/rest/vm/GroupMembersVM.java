package com.hdmon.chatservice.web.rest.vm;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 6/6/2018.
 */
public class GroupMembersVM {
    private Long ownerId;
    private String ownerLogin;

    private String id;
    private GroupTypeEnum groupType;
    private String groupName;
    private String groupIcon = "";
    private String groupSlogan = "";
    private String groupAbout = "";
    private List<MembersVM> listMembers = new ArrayList<>();


    public GroupMembersVM(GroupMembersVM groupMembersVM) {
        this.ownerId = groupMembersVM.getOwnerId();
        this.ownerLogin = groupMembersVM.getOwnerLogin();
        this.id = groupMembersVM.getId();
        this.groupType = groupMembersVM.getGroupType();
        this.groupName = groupMembersVM.getGroupName();
        this.groupIcon = groupMembersVM.getGroupIcon();
        this.groupSlogan = groupMembersVM.getGroupSlogan();
        this.groupAbout = groupMembersVM.getGroupAbout();
        this.listMembers = groupMembersVM.getListMembers();
    }

    public GroupMembersVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin)
    {
        this.ownerLogin = ownerLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<MembersVM> getListMembers() {
        return listMembers;
    }

    public void setListMembers(List<MembersVM> listMembers) {
        this.listMembers = listMembers;
    }

    @Override
    public String toString() {
        return "GroupMembersVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", OwnerLogin='" + getOwnerLogin() + '\'' +
            ", Id='" + getId() + '\'' +
            ", GroupType='" + getGroupType() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", GroupIcon='" + getGroupIcon() + '\'' +
            ", GroupSlogan='" + getGroupSlogan() + '\'' +
            ", GroupAbout='" + getGroupAbout() + '\'' +
            ", ListMembers='" + getListMembers() + '\'' +
            '}';
    }
}
