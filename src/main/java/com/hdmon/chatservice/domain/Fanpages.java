package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.FanpageStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A Fanpages.
 */
@Document(collection = "fanpages")
public class Fanpages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("fanpage_id")
    private String fanpageId = UUID.randomUUID().toString();;

    @Field("fan_name")
    private String fanName;

    @Field("fan_url")
    private String fanUrl;

    @Field("fan_about")
    private String fanAbout;

    @Field("fan_icon")
    private String fanIcon;

    @Field("fan_thumbnail")
    private String fanThumbnail;

    @Field("fan_status")
    private FanpageStatus fanStatus;

    @Field("member_list")
    private String memberList;

    @Field("member_count")
    private Integer memberCount;

    @Field("owner_id")
    private Long ownerId;

    @Field("owner_login")
    private String ownerLogin;

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

    public String getFanpageId() {
        return fanpageId;
    }

    public Fanpages fanpageId(String fanpageId) {
        this.fanpageId = fanpageId;
        return this;
    }

    public void setFanpageId(String fanpageId) {
        this.fanpageId = fanpageId;
    }

    public String getFanName() {
        return fanName;
    }

    public Fanpages fanName(String fanName) {
        this.fanName = fanName;
        return this;
    }

    public void setFanName(String fanName) {
        this.fanName = fanName;
    }

    public String getFanUrl() {
        return fanUrl;
    }

    public Fanpages fanUrl(String fanUrl) {
        this.fanUrl = fanUrl;
        return this;
    }

    public void setFanUrl(String fanUrl) {
        this.fanUrl = fanUrl;
    }

    public String getFanAbout() {
        return fanAbout;
    }

    public Fanpages fanAbout(String fanAbout) {
        this.fanAbout = fanAbout;
        return this;
    }

    public void setFanAbout(String fanAbout) {
        this.fanAbout = fanAbout;
    }

    public String getFanIcon() {
        return fanIcon;
    }

    public Fanpages fanIcon(String fanIcon) {
        this.fanIcon = fanIcon;
        return this;
    }

    public void setFanIcon(String fanIcon) {
        this.fanIcon = fanIcon;
    }

    public String getFanThumbnail() {
        return fanThumbnail;
    }

    public Fanpages fanThumbnail(String fanThumbnail) {
        this.fanThumbnail = fanThumbnail;
        return this;
    }

    public void setFanThumbnail(String fanThumbnail) {
        this.fanThumbnail = fanThumbnail;
    }

    public FanpageStatus getFanStatus() {
        return fanStatus;
    }

    public Fanpages fanStatus(FanpageStatus fanStatus) {
        this.fanStatus = fanStatus;
        return this;
    }

    public void setFanStatus(FanpageStatus fanStatus) {
        this.fanStatus = fanStatus;
    }

    public String getMemberList() {
        return memberList;
    }

    public Fanpages memberList(String memberList) {
        this.memberList = memberList;
        return this;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public Fanpages memberCount(Integer memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Fanpages ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public Fanpages ownerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
        return this;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Fanpages createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Fanpages createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public Fanpages createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Fanpages lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Fanpages lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public Fanpages lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public Fanpages reportDay(Integer reportDay) {
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
        Fanpages fanpages = (Fanpages) o;
        if (fanpages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fanpages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fanpages{" +
            "id=" + getId() +
            ", fanpageId='" + getFanpageId() + "'" +
            ", fanName='" + getFanName() + "'" +
            ", fanUrl='" + getFanUrl() + "'" +
            ", fanAbout='" + getFanAbout() + "'" +
            ", fanIcon='" + getFanIcon() + "'" +
            ", fanThumbnail='" + getFanThumbnail() + "'" +
            ", fanStatus='" + getFanStatus() + "'" +
            ", memberList='" + getMemberList() + "'" +
            ", memberCount=" + getMemberCount() +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
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
