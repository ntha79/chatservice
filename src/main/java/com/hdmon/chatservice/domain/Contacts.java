package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extContactLists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A Contacts.
 */
@Document(collection = "contacts")
public class Contacts extends AbstractAuditingEntity implements Serializable {

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
    private List<extContactLists> contactLists;

    @Field("contact_count")
    private Integer contactCount;

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

    public Contacts seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Contacts ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public Contacts ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<extContactLists> getContactLists() {
        return contactLists;
    }

    public Contacts contactLists(List<extContactLists> contactLists) {
        this.contactLists = contactLists;
        return this;
    }

    public void setContactLists(List<extContactLists> contactLists) {
        this.contactLists = contactLists;
    }

    public Integer getContactCount() {
        return contactCount;
    }

    public Contacts contactCount(Integer contactCount) {
        this.contactCount = contactCount;
        return this;
    }

    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public Contacts createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public Contacts lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public Contacts reportDay(Integer reportDay) {
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
        Contacts contacts = (Contacts) o;
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
        return "Contacts{" +
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
