package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extDeviceContactEntity;
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
@Document(collection = "device_contacts")
public class DeviceContactsEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();

    @Field("owner_id")
    private Long ownerId;

    @Field("owner_login")
    private String ownerLogin;

    @Field("contact_lists")
    private List<extDeviceContactEntity> contactLists;

    @Field("contact_count")
    private Integer contactCount = 0;

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

    public DeviceContactsEntity seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public DeviceContactsEntity ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public DeviceContactsEntity ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<extDeviceContactEntity> getContactLists() {
        return contactLists;
    }

    public DeviceContactsEntity contactLists(List<extDeviceContactEntity> contactLists) {
        this.contactLists = contactLists;
        return this;
    }

    public void setContactLists(List<extDeviceContactEntity> contactLists) {
        this.contactLists = contactLists;
    }

    public Integer getContactCount() {
        return contactCount;
    }

    public DeviceContactsEntity contactCount(Integer contactCount) {
        this.contactCount = contactCount;
        return this;
    }

    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public DeviceContactsEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public DeviceContactsEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public DeviceContactsEntity reportDay(Integer reportDay) {
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
        DeviceContactsEntity contacts = (DeviceContactsEntity) o;
        if (contacts.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contacts.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceContactsEntity{" +
            "id=" + getId() +
            ", seqId='" + getSeqId() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            ", contactLists='" + getContactLists() + "'" +
            ", contactCount=" + getContactCount() +
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
