package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.PushNotificationStatusEnum;
import com.hdmon.chatservice.domain.extents.extPushNotificationToMemberEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A PushNotifications.
 */
@Document(collection = "push_notifications")
public class PushNotificationsEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();

    @Field("name")
    private String name;

    @Field("message")
    private String message;

    @Field("link_detail")
    private String linkDetail;

    @Field("status")
    private PushNotificationStatusEnum status = PushNotificationStatusEnum.INIT;

    @Field("to_member_list")
    private List<extPushNotificationToMemberEntity> toMemberList;

    @Field("created_unix_time")
    private Long createdUnixTime;

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

    public PushNotificationsEntity seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getName() {
        return name;
    }

    public PushNotificationsEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public PushNotificationsEntity message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkDetail() {
        return linkDetail;
    }

    public PushNotificationsEntity linkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
        return this;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }

    public PushNotificationStatusEnum getStatus() {
        return status;
    }

    public PushNotificationsEntity status(PushNotificationStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(PushNotificationStatusEnum status) {
        this.status = status;
    }

    public List<extPushNotificationToMemberEntity> getToMemberList() {
        return toMemberList;
    }

    public PushNotificationsEntity toMemberList(List<extPushNotificationToMemberEntity> toMemberList) {
        this.toMemberList = toMemberList;
        return this;
    }

    public void setToMemberList(List<extPushNotificationToMemberEntity> toMemberList) {
        this.toMemberList = toMemberList;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public PushNotificationsEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public PushNotificationsEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public PushNotificationsEntity reportDay(Integer reportDay) {
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
        PushNotificationsEntity pushNotifications = (PushNotificationsEntity) o;
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
        return "PushNotificationsEntity{" +
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
