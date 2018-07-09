package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A ChatGroups.
 */
@Document(collection = "chat_groups")
public class ChatGroupsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Field("group_id")
    private String groupId;

    @Field("group_type")
    private GroupTypeEnum groupType;

    @Field("group_name")
    private String groupName;

    @Field("group_icon")
    private String groupIcon;

    @Field("group_background")
    private String groupBackground;

    @Field("group_slogan")
    private String groupSlogan;

    @Field("group_sumary")
    private String groupSumary;

    @Field("group_status")
    private GroupMemberStatusEnum groupStatus = GroupMemberStatusEnum.NORMAL;

    @Field("member_lists")
    private List<extGroupMemberEntity> memberLists;

    @Field("max_member")
    private Integer maxMember = 5000;

    @Field("owner_userid")
    private Long ownerUserid;

    @Field("owner_username")
    private String ownerUsername;

    @Field("created_time")
    private Long createdTime = new Date().getTime();

    @Field("last_modified_by")
    private String lastModifiedBy;

    @Field("last_modified_time")
    private Long lastModifiedTime = new Date().getTime();

    @Field("report_day")
    private Integer reportDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupTypeEnum getGroupType() {
        return groupType;
    }

    public ChatGroupsEntity groupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public ChatGroupsEntity groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupBackground() {
        return groupBackground;
    }

    public ChatGroupsEntity groupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
        return this;
    }

    public void setGroupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public ChatGroupsEntity groupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public ChatGroupsEntity groupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
        return this;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public GroupMemberStatusEnum getGroupStatus() {
        return groupStatus;
    }

    public ChatGroupsEntity groupStatus(GroupMemberStatusEnum groupStatus) {
        this.groupStatus = groupStatus;
        return this;
    }

    public void setGroupStatus(GroupMemberStatusEnum groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getGroupSumary() {
        return groupSumary;
    }

    public ChatGroupsEntity groupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
        return this;
    }

    public void setGroupSumary(String groupSumary) {
        this.groupSumary = groupSumary;
    }

    public List<extGroupMemberEntity> getMemberLists() {
        return memberLists;
    }

    public ChatGroupsEntity memberLists(List<extGroupMemberEntity> memberLists) {
        this.memberLists = memberLists;
        return this;
    }

    public void setMemberLists(List<extGroupMemberEntity> memberLists) {
        this.memberLists = memberLists;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public ChatGroupsEntity maxMember(Integer maxMember) {
        this.maxMember = maxMember;
        return this;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public Long getOwnerUserid() {
        return ownerUserid;
    }

    public ChatGroupsEntity ownerUserid(Long ownerUserid) {
        this.ownerUserid = ownerUserid;
        return this;
    }

    public void setOwnerUserid(Long ownerUserid) {
        this.ownerUserid = ownerUserid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public ChatGroupsEntity ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public ChatGroupsEntity createdTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public ChatGroupsEntity lastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ChatGroupsEntity lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public ChatGroupsEntity reportDay(Integer reportDay) {
        this.reportDay = reportDay;
        return this;
    }

    public void setReportDay(Integer reportDay) {
        this.reportDay = reportDay;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatGroupsEntity groupMembers = (ChatGroupsEntity) o;
        if (groupMembers.getGroupId() == null || getGroupId() == null) {
            return false;
        }
        return Objects.equals(getGroupId(), groupMembers.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getGroupId());
    }

    @Override
    public String toString() {
        return "ChatGroupsEntity{" +
            "GroupId=" + getGroupId() +
            ", GroupType='" + getGroupType() + "'" +
            ", GroupName='" + getGroupName() + "'" +
            ", GroupIcon='" + getGroupIcon() + "'" +
            ", GroupBackground='" + getGroupBackground() + "'" +
            ", GroupSlogan='" + getGroupSlogan() + "'" +
            ", GroupSumary='" + getGroupSumary() + "'" +
            ", GroupStatus=" + getGroupStatus() +
            ", MemberLists='" + getMemberLists() + "'" +
            ", mMaxMember=" + getMaxMember() +
            ", OwnerUserid=" + getOwnerUserid() +
            ", OwnerUsername()='" + getOwnerUsername() + "'" +
            ", CreatedTime='" + getCreatedTime() + "'" +
            ", LastModifiedBy='" + getLastModifiedBy() + "'" +
            ", LastModifiedTime='" + getLastModifiedTime() + "'" +
            ", ReportDay=" + getReportDay() +
            "}";
    }
}
