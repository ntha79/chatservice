package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.extents.extUserPrivateSettingEntity;
import com.hdmon.chatservice.domain.extents.extUserSocialSettingEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A UserSettings.
 */
@Document(collection = "user_settings")
public class UserSettingsEntity  extends AbstractAuditingEntity implements Serializable {

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
    private extUserPrivateSettingEntity privateSettings;

    @Field("social_settings")
    private extUserSocialSettingEntity socialSettings;

    @Field("created_unix_time")
    private Long createdUnixTime = new Date().getTime();

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime = new Date().getTime();

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

    public UserSettingsEntity seqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public UserSettingsEntity ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public UserSettingsEntity ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public extUserPrivateSettingEntity getPriavateSettings() {
        return privateSettings;
    }

    public UserSettingsEntity priavateSettings(extUserPrivateSettingEntity priavateSettings) {
        this.privateSettings = priavateSettings;
        return this;
    }

    public void setPriavateSettings(extUserPrivateSettingEntity priavateSettings) {
        this.privateSettings = priavateSettings;
    }

    public extUserSocialSettingEntity getSocialSettings() {
        return socialSettings;
    }

    public UserSettingsEntity socialSettings(extUserSocialSettingEntity socialSettings) {
        this.socialSettings = socialSettings;
        return this;
    }

    public void setSocialSettings(extUserSocialSettingEntity socialSettings) {
        this.socialSettings = socialSettings;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public UserSettingsEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public UserSettingsEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
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
        UserSettingsEntity userSettings = (UserSettingsEntity) o;
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
        return "UserSettingsEntity{" +
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
