package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A Contacts.
 */
@Document(collection = "friends")
public class FriendsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("owner_username")
    private String ownerUsername;

    @Field("friend_lists")
    private List<extFriendContactEntity> friendLists;

    @Field("group_lists")
    private List<extContactGroupEntity> groupLists;

    @Field("created_time")
    private Long createdTime = new Date().getTime();

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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public FriendsEntity ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<extFriendContactEntity> getFriendLists() {
        return friendLists;
    }

    public FriendsEntity friendLists(List<extFriendContactEntity> friendLists) {
        this.friendLists = friendLists;
        return this;
    }

    public void setFriendLists(List<extFriendContactEntity> friendLists) {
        this.friendLists = friendLists;
    }

    public List<extContactGroupEntity> getGroupLists() {
        return groupLists;
    }

    public FriendsEntity groupLists(List<extContactGroupEntity> groupLists) {
        this.groupLists = groupLists;
        return this;
    }

    public void setGroupLists(List<extContactGroupEntity> groupLists) {
        this.groupLists = groupLists;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public FriendsEntity createdUnixTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public FriendsEntity lastModifiedUnixTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public FriendsEntity reportDay(Integer reportDay) {
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
        FriendsEntity friends = (FriendsEntity) o;
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
        return "FriendsEntity{" +
            "id=" + getId() +
            ", ownerUsername='" + getOwnerUsername() + "'" +
            ", friendLists='" + getFriendLists() + "'" +
            ", groupLists='" + getGroupLists() + "'" +
            ", createdTime=" + getCreatedTime() +
            ", lastModifiedTime=" + getLastModifiedTime() +
            ", reportDay='" + getReportDay() +
            "}";
    }
}
