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
 * A GroupMembers.
 */
@Document(collection = "group_members")
public class GroupMembersEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();

    @Field("group_type")
    private GroupTypeEnum groupType;

    @Field("group_name")
    private String groupName;

    @Field("group_icon")
    private String groupIcon;

    @Field("group_slogan")
    private String groupSlogan;

    @Field("group_about")
    private String groupAbout;

    @Field("group_status")
    private GroupMemberStatusEnum groupStatus = GroupMemberStatusEnum.NORMAL;

    @Field("owner_id")
    private Long ownerId;

    @Field("member_lists")
    private List<extGroupMemberEntity> memberLists;

    @Field("max_member")
    private Integer maxMember = 5000;

    @Field("created_unix_time")
    private Long createdUnixTime = new Date().getTime();

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime = new Date().getTime();

    @Field("last_chat_unix_time")
    private Long lastChatUnixTime = new Date().getTime();

    @Field("report_day")
    private Integer reportDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeqId() {
        return seqId;
    }

    public GroupMembersEntity seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public GroupTypeEnum getGroupType() {
        return groupType;
    }

    public GroupMembersEntity groupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupMembersEntity groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public GroupMembersEntity groupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
        return this;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupSlogan() {
        return groupSlogan;
    }

    public GroupMembersEntity groupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
        return this;
    }

    public void setGroupSlogan(String groupSlogan) {
        this.groupSlogan = groupSlogan;
    }

    public GroupMemberStatusEnum getGroupStatus() {
        return groupStatus;
    }

    public GroupMembersEntity groupStatus(GroupMemberStatusEnum groupStatus) {
        this.groupStatus = groupStatus;
        return this;
    }

    public void setGroupStatus(GroupMemberStatusEnum groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getGroupAbout() {
        return groupAbout;
    }

    public GroupMembersEntity groupAbout(String groupAbout) {
        this.groupAbout = groupAbout;
        return this;
    }

    public void setGroupAbout(String groupAbout) {
        this.groupAbout = groupAbout;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public GroupMembersEntity ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<extGroupMemberEntity> getMemberLists() {
        return memberLists;
    }

    public GroupMembersEntity memberLists(List<extGroupMemberEntity> memberLists) {
        this.memberLists = memberLists;
        return this;
    }

    public void setMemberLists(List<extGroupMemberEntity> memberLists) {
        this.memberLists = memberLists;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public GroupMembersEntity maxMember(Integer maxMember) {
        this.maxMember = maxMember;
        return this;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public GroupMembersEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public GroupMembersEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Long getLastChatUnixTime() {
        return lastChatUnixTime;
    }

    public GroupMembersEntity lastChatUnixTime(Long lastChatUnixTime) {
        this.lastChatUnixTime = lastChatUnixTime;
        return this;
    }

    public void setLastChatUnixTime(Long lastChatUnixTime) {
        this.lastChatUnixTime = lastChatUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public GroupMembersEntity reportDay(Integer reportDay) {
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
        GroupMembersEntity groupMembers = (GroupMembersEntity) o;
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
        return "GroupMembersEntity{" +
            "id=" + getId() +
            ", groupType='" + getGroupType() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", groupIcon='" + getGroupIcon() + "'" +
            ", groupSlogan='" + getGroupSlogan() + "'" +
            ", groupAbout='" + getGroupAbout() + "'" +
            ", groupStatus=" + getGroupStatus() +
            ", ownerId=" + getOwnerId() +
            ", memberLists='" + getMemberLists() + "'" +
            ", maxMember=" + getMaxMember() +
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
