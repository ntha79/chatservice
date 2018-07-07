package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
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
@Document(collection = "contacts")
public class ContactsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Field("owner_userid")
    private Long ownerUserid;

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
    public Long getOwnerUserid() {
        return ownerUserid;
    }

    public ContactsEntity ownerUserid(Long ownerUserid) {
        this.ownerUserid = ownerUserid;
        return this;
    }

    public void setOwnerUserid(Long ownerUserid) {
        this.ownerUserid = ownerUserid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public ContactsEntity ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<extFriendContactEntity> getFriendLists() {
        return friendLists;
    }

    public ContactsEntity friendLists(List<extFriendContactEntity> friendLists) {
        this.friendLists = friendLists;
        return this;
    }

    public void setFriendLists(List<extFriendContactEntity> friendLists) {
        this.friendLists = friendLists;
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

    public Long getCreatedTime() {
        return createdTime;
    }

    public ContactsEntity createdUnixTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public ContactsEntity lastModifiedUnixTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
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
        if (friends.getOwnerUsername() == null || getOwnerUsername() == null) {
            return false;
        }
        return Objects.equals(getOwnerUsername(), friends.getOwnerUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOwnerUsername());
    }

    @Override
    public String toString() {
        return "ContactsEntity{" +
            ", ownerUserid='" + getOwnerUserid() + "'" +
            ", ownerUsername='" + getOwnerUsername() + "'" +
            ", friendLists='" + getFriendLists() + "'" +
            ", groupLists='" + getGroupLists() + "'" +
            ", createdTime=" + getCreatedTime() +
            ", lastModifiedTime=" + getLastModifiedTime() +
            ", reportDay='" + getReportDay() +
            "}";
    }
}
