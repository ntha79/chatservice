package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extUserPrivateSettings;
import com.hdmon.chatservice.domain.extents.extUserSocialSettings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A UserSettings.
 */
@Document(collection = "user_settings")
public class UserSettings  extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("seq_id")
    private String seqId = UUID.randomUUID().toString();;

    @Field("owner_id")
    private Long ownerId;

    @Field("owner_login")
    private String ownerLogin;

    @Field("priavate_settings")
    private extUserPrivateSettings privateSettings;

    @Field("social_settings")
    private extUserSocialSettings socialSettings;

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

    public UserSettings seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public UserSettings ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public UserSettings ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public extUserPrivateSettings getPriavateSettings() {
        return privateSettings;
    }

    public UserSettings priavateSettings(extUserPrivateSettings priavateSettings) {
        this.privateSettings = priavateSettings;
        return this;
    }

    public void setPriavateSettings(extUserPrivateSettings priavateSettings) {
        this.privateSettings = priavateSettings;
    }

    public extUserSocialSettings getSocialSettings() {
        return socialSettings;
    }

    public UserSettings socialSettings(extUserSocialSettings socialSettings) {
        this.socialSettings = socialSettings;
        return this;
    }

    public void setSocialSettings(extUserSocialSettings socialSettings) {
        this.socialSettings = socialSettings;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public UserSettings createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public UserSettings lastModifiedUnixTime(Long lastModifiedUnixTime) {
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
        UserSettings userSettings = (UserSettings) o;
        if (userSettings.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userSettings.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + getId() +
            ", seqId='" + getSeqId() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            ", priavateSettings='" + getPriavateSettings() + "'" +
            ", socialSettings='" + getSocialSettings() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUnixTime=" + getCreatedUnixTime() +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedUnixTime=" + getLastModifiedUnixTime() +
            "}";
    }
}
