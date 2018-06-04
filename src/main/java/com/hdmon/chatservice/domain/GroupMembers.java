package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.GroupType;
import com.hdmon.chatservice.domain.extents.extGroupMemberLists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A GroupMembers.
 */
@Document(collection = "group_members")
public class GroupMembers extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("group_id")
    private String groupId = UUID.randomUUID().toString();

    @Field("group_type")
    private GroupType groupType;

    @Field("group_name")
    private String groupName;

    @Field("group_icon")
    private String groupIcon;

    @Field("group_slogan")
    private String groupSlogan;

    @Field("group_status")
    private Integer groupStatus;

    @Field("owner_id")
    private Long ownerId;

    @Field("owner_login")
    private String ownerLogin;

    @Field("member_lists")
    private List<extGroupMemberLists> memberLists;

    @Field("max_member")
    private Integer maxMember;

    @Field("member_count")
    private Integer memberCount;

    @Field("created_unix_time")
    private Long createdUnixTime;

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime;

    @Field("last_chat_unix_time")
    private Long lastChatUnixTime;

    @Field("report_day")
    private Integer reportDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public GroupMembers groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public GroupMembers groupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupMembers groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public GroupMembers groupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public GroupMembers groupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
        return this;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public GroupMembers groupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
        return this;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public GroupMembers ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public GroupMembers ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<extGroupMemberLists> getMemberLists() {
        return memberLists;
    }

    public GroupMembers memberLists(List<extGroupMemberLists> memberLists) {
        this.memberLists = memberLists;
        return this;
    }

    public void setMemberLists(List<extGroupMemberLists> memberLists) {
        this.memberLists = memberLists;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public GroupMembers maxMember(Integer maxMember) {
        this.maxMember = maxMember;
        return this;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public GroupMembers memberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public GroupMembers createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public GroupMembers lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Long getLastChatUnixTime() {
        return lastChatUnixTime;
    }

    public GroupMembers lastChatUnixTime(Long lastChatUnixTime) {
        this.lastChatUnixTime = lastChatUnixTime;
        return this;
    }

    public void setLastChatUnixTime(Long lastChatUnixTime) {
        this.lastChatUnixTime = lastChatUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public GroupMembers reportDay(Integer reportDay) {
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
        GroupMembers groupMembers = (GroupMembers) o;
        if (groupMembers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupMembers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupMembers{" +
            "id=" + getId() +
            ", groupId='" + getGroupId() + "'" +
            ", groupType='" + getGroupType() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", groupIcon='" + getGroupIcon() + "'" +
            ", groupSlogan='" + getGroupSlogan() + "'" +
            ", groupStatus=" + getGroupStatus() +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            ", memberLists='" + getMemberLists() + "'" +
            ", maxMember=" + getMaxMember() +
            ", memberCount=" + getMemberCount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUnixTime=" + getCreatedUnixTime() +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedUnixTime=" + getLastModifiedUnixTime() +
            ", lastChatUnixTime=" + getLastChatUnixTime() +
            ", reportDay=" + getReportDay() +
            "}";
    }
}
