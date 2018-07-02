package com.hdmon.chatservice.web.rest.vm.Contacts;

/**
 * Created by UserName on 6/29/2018.
 */
public class GroupInfoVM {
    private String groupId;
    private String groupName;
    private Integer memberCount;
    private String imageUrl;

    public GroupInfoVM()
    {
        super();
    }

    public GroupInfoVM(GroupInfoVM groupInfoVM)
    {
        this.groupId = groupInfoVM.getGroupId();
        this.groupName = groupInfoVM.getGroupName();
        this.memberCount = groupInfoVM.getMemberCount();
        this.imageUrl = groupInfoVM.getImageUrl();
    }

    public GroupInfoVM(String groupId, String groupName, Integer memberCount, String imageUrl)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.memberCount = memberCount;
        this.imageUrl = imageUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public GroupInfoVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupInfoVM groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public GroupInfoVM memberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public GroupInfoVM imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "GroupInfoVM{" +
            "GroupId='" + getGroupId() + '\'' +
            ", GroupName='" + getGroupName() + '\'' +
            ", MemberCount='" + getMemberCount() + '\'' +
            ", ImageUrl='" + getImageUrl() + '\'' +
            '}';
    }
}
