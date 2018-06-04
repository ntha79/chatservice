package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extServiceSettingValues;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ServiceSettings.
 */
@Document(collection = "service_settings")
public class ServiceSettings  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();

    @Field("code")
    private String code;

    @Field("values")
    private extServiceSettingValues values;

    @Field("created_unix_time")
    private Long createdUnixTime;

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime;

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

    public ServiceSettings seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getCode() {
        return code;
    }

    public ServiceSettings code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public extServiceSettingValues getValues() {
        return values;
    }

    public ServiceSettings values(extServiceSettingValues values) {
        this.values = values;
        return this;
    }

    public void setValues(extServiceSettingValues values) {
        this.values = values;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public ServiceSettings createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public ServiceSettings lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
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
        ServiceSettings serviceSettings = (ServiceSettings) o;
        if (serviceSettings.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceSettings.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceSettings{" +
            "id=" + getId() +
            ", seqId='" + getSeqId() + "'" +
            ", code='" + getCode() + "'" +
            ", values='" + getValues() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUnixTime=" + getCreatedUnixTime() +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedUnixTime=" + getLastModifiedUnixTime() +
            "}";
    }
}
