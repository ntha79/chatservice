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
    private String id;

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

    @Field("created_by_id")
    private Long createdById;

    @Field("created_by")
    private String createdBy;

    @Field("created_time")
    private Long createdTime = new Date().getTime();

    @Field("last_modified_by")
    private String lastModifiedBy;

    @Field("last_modified_time")
    private Long lastModifiedTime = new Date().getTime();

    @Field("report_day")
    private Integer reportDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getCreatedById() {
        return createdById;
    }

    public ChatGroupsEntity createdById(Long createdById) {
        this.createdById = createdById;
        return this;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ChatGroupsEntity createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        return "ChatGroupsEntity{" +
            "id=" + getId() +
            ", groupType='" + getGroupType() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", groupIcon='" + getGroupIcon() + "'" +
            ", groupBackground='" + getGroupBackground() + "'" +
            ", groupSlogan='" + getGroupSlogan() + "'" +
            ", groupSumary='" + getGroupSumary() + "'" +
            ", groupStatus=" + getGroupStatus() +
            ", memberLists='" + getMemberLists() + "'" +
            ", maxMember=" + getMaxMember() +
            ", createdById=" + getCreatedById() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedTime='" + getLastModifiedTime() + "'" +
            ", reportDay=" + getReportDay() +
            "}";
    }
}
