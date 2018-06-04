package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.PushNotificationStatus;
import com.hdmon.chatservice.domain.extents.extPushNotificationToMemberLists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A PushNotifications.
 */
@Document(collection = "push_notifications")
public class PushNotifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();;

    @Field("name")
    private String name;

    @Field("message")
    private String message;

    @Field("link_detail")
    private String linkDetail;

    @Field("status")
    private PushNotificationStatus status;

    @Field("to_member_list")
    private List<extPushNotificationToMemberLists> toMemberList;

    @Field("created_by")
    private String createdBy;

    @Field("created_date")
    private Instant createdDate;

    @Field("created_unix_time")
    private Long createdUnixTime;

    @Field("last_modified_by")
    private String lastModifiedBy;

    @Field("last_modified_date")
    private Instant lastModifiedDate;

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

    public PushNotifications seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getName() {
        return name;
    }

    public PushNotifications name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public PushNotifications message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkDetail() {
        return linkDetail;
    }

    public PushNotifications linkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
        return this;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }

    public PushNotificationStatus getStatus() {
        return status;
    }

    public PushNotifications status(PushNotificationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PushNotificationStatus status) {
        this.status = status;
    }

    public List<extPushNotificationToMemberLists> getToMemberList() {
        return toMemberList;
    }

    public PushNotifications toMemberList(List<extPushNotificationToMemberLists> toMemberList) {
        this.toMemberList = toMemberList;
        return this;
    }

    public void setToMemberList(List<extPushNotificationToMemberLists> toMemberList) {
        this.toMemberList = toMemberList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PushNotifications createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PushNotifications createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public PushNotifications createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PushNotifications lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PushNotifications lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public PushNotifications lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public PushNotifications reportDay(Integer reportDay) {
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
        PushNotifications pushNotifications = (PushNotifications) o;
        if (pushNotifications.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pushNotifications.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PushNotifications{" +
            "id=" + getId() +
            ", seqId='" + getSeqId() + "'" +
            ", name='" + getName() + "'" +
            ", message='" + getMessage() + "'" +
            ", linkDetail='" + getLinkDetail() + "'" +
            ", status='" + getStatus() + "'" +
            ", toMemberList='" + getToMemberList() + "'" +
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
