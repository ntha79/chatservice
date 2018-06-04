package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extFriendMemberLists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A Friends.
 */
@Document(collection = "friends")
public class Friends extends AbstractAuditingEntity implements Serializable {

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
    private List<extFriendMemberLists> friendLists;

    @Field("friend_count")
    private Integer friendCount;

    @Field("created_unix_time")
    private Long createdUnixTime;

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime;

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

    public Friends seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Friends ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public Friends ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<extFriendMemberLists> getFriendLists() {
        return friendLists;
    }

    public Friends friendLists(List<extFriendMemberLists> friendLists) {
        this.friendLists = friendLists;
        return this;
    }

    public void setFriendLists(List<extFriendMemberLists> friendLists) {
        this.friendLists = friendLists;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public Friends friendCount(Integer friendCount) {
        this.friendCount = friendCount;
        return this;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public Friends createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public Friends lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public Friends reportDay(Integer reportDay) {
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
        Friends friends = (Friends) o;
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
        return "Friends{" +
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
