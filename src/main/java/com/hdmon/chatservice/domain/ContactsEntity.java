package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A Contacts.
 */
@Document(collection = "contacts")
public class ContactsEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();

    @Field("owner_id")
    private Long ownerId;

    @Field("owner_login")
    private String ownerLogin;

    @Field("friend_lists")
    private List<extFriendMemberEntity> friendLists;

    @Field("friend_count")
    private Integer friendCount = 0;

    @Field("group_lists")
    private List<extContactGroupEntity> groupLists;

    @Field("group_count")
    private Integer groupCount = 0;

    @Field("created_unix_time")
    private Long createdUnixTime = new Date().getTime();

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime = new Date().getTime();

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

    public ContactsEntity seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public ContactsEntity ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public ContactsEntity ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<extFriendMemberEntity> getFriendLists() {
        return friendLists;
    }

    public ContactsEntity friendLists(List<extFriendMemberEntity> friendLists) {
        this.friendLists = friendLists;
        return this;
    }

    public void setFriendLists(List<extFriendMemberEntity> friendLists) {
        this.friendLists = friendLists;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public ContactsEntity friendCount(Integer friendCount) {
        this.friendCount = friendCount;
        return this;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public List<extContactGroupEntity> getGroupLists() {
        return groupLists;
    }

    public ContactsEntity groupLists(List<extContactGroupEntity> groupLists) {
        this.groupLists = groupLists;
        return this;
    }

    public void setGroupLists(List<extContactGroupEntity> groupLists) {
        this.groupLists = groupLists;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public ContactsEntity groupCount(Integer groupCount) {
        this.groupCount = groupCount;
        return this;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public ContactsEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public ContactsEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public ContactsEntity reportDay(Integer reportDay) {
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
        ContactsEntity friends = (ContactsEntity) o;
        if (friends.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friends.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactsEntity{" +
            "id=" + getId() +
            ", seqId='" + getSeqId() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            ", friendLists='" + getFriendLists() + "'" +
            ", friendCount=" + getFriendCount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUnixTime=" + getCreatedUnixTime() +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedUnixTime=" + getLastModifiedUnixTime() +
            ", reportDay=" + getReportDay() +
            "}";
    }
}
