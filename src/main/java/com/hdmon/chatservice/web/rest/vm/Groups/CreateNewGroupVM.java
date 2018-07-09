package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;

import java.util.List;

/**
 * Created by UserName on 6/9/2018.
 */
public class CreateNewGroupVM {
    private String ownerUsername;
    private String groupId;
    private GroupTypeEnum groupType;
    private String groupName;
    private String groupIcon = "";
    private String groupBackground = "";
    private String groupSlogan = "";
    private String groupSumary = "";
    private List<String> memberList;

    public CreateNewGroupVM(CreateNewGroupVM createNewGroupVM) {
        this.ownerUsername = createNewGroupVM.getGroupIcon();
        this.groupType = createNewGroupVM.getGroupType();
        this.groupName = createNewGroupVM.getGroupName();
        this.groupIcon = createNewGroupVM.getGroupIcon();
        this.groupBackground = createNewGroupVM.getGroupBackground();
        this.groupSlogan = createNewGroupVM.getGroupSlogan();
        this.groupSumary = createNewGroupVM.getGroupSumary();
        this.memberList = createNewGroupVM.getMemberList();
    }

    public CreateNewGroupVM(String ownerUsername, String groupId, GroupTypeEnum groupType, String groupName, String groupIcon, String groupBackground, String groupSlogan, String groupSumary, List<String> memberList)
    {
        this.ownerUsername = ownerUsername;
        this.groupId = groupId;
        this.groupType = groupType;
        this.groupName = groupName;
        this.groupIcon = groupIcon;
        this.groupBackground = groupBackground;
        this.groupSlogan = groupSlogan;
        this.groupSumary = groupSumary;
        this.memberList = memberList;
    }

    public CreateNewGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public CreateNewGroupVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getGroupId() {
        return groupId;
    }

    public CreateNewGroupVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
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

    public CreateNewGroupVM groupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public CreateNewGroupVM groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public CreateNewGroupVM groupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupBackground() {
        return groupBackground;
    }

    public CreateNewGroupVM groupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
        return this;
    }

    public void setGroupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public CreateNewGroupVM groupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
        return this;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public String getGroupSumary() {
        return groupSumary;
    }

    public CreateNewGroupVM groupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
        return this;
    }

    public void setGroupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public CreateNewGroupVM memberList(List<String> memberList) {
        this.memberList = memberList;
        return this;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "GroupMembersVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", GroupType='" + getGroupType() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", GroupIcon='" + getGroupIcon() + '\'' +
            ", GroupBackground='" + getGroupBackground() + '\'' +
            ", GroupSlogan='" + getGroupSlogan() + '\'' +
            ", GroupSumary='" + getGroupSumary() + '\'' +
            ", MemberList='" + getMemberList() + '\'' +
            '}';
    }
}
