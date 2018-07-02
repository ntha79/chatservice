package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.CheckStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ChatGroupStatistics.
 */
@Document(collection = "chat_group_statistics")
public class ChatGroupStatisticsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String seqId;

    @Field("day_count")
    private Integer dayCount;

    @Field("month_count")
    private Integer monthCount;

    @Field("year_count")
    private Integer yearCount;

    @Field("in_day")
    private Integer inDay;

    @Field("in_month")
    private Integer inMonth;

    @Field("in_year")
    private Integer inYear;

    @Field("status")
    private CheckStatusEnum status = CheckStatusEnum.NOTCHECK;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public ChatGroupStatisticsEntity dayCount(Integer dayCount) {
        this.dayCount = dayCount;
        return this;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public ChatGroupStatisticsEntity monthCount(Integer monthCount) {
        this.monthCount = monthCount;
        return this;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Integer getYearCount() {
        return yearCount;
    }

    public ChatGroupStatisticsEntity yearCount(Integer yearCount) {
        this.yearCount = yearCount;
        return this;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public Integer getInDay() {
        return inDay;
    }

    public ChatGroupStatisticsEntity inDay(Integer inDay) {
        this.inDay = inDay;
        return this;
    }

    public void setInDay(Integer inDay) {
        this.inDay = inDay;
    }

    public Integer getInMonth() {
        return inMonth;
    }

    public ChatGroupStatisticsEntity inMonth(Integer inMonth) {
        this.inMonth = inMonth;
        return this;
    }

    public void setInMonth(Integer inMonth) {
        this.inMonth = inMonth;
    }

    public Integer getInYear() {
        return inYear;
    }

    public ChatGroupStatisticsEntity inYear(Integer inYear) {
        this.inYear = inYear;
        return this;
    }

    public void setInYear(Integer inYear) {
        this.inYear = inYear;
    }

    public CheckStatusEnum getStatus() {
        return status;
    }

    public ChatGroupStatisticsEntity status(CheckStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(CheckStatusEnum status) {
        this.status = status;
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
        ChatGroupStatisticsEntity groupMemberStatistics = (ChatGroupStatisticsEntity) o;
        if (groupMemberStatistics.getSeqId() == null || getSeqId() == null) {
            return false;
        }
        return Objects.equals(getSeqId(), groupMemberStatistics.getSeqId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSeqId());
    }

    @Override
    public String toString() {
        return "ChatGroupStatisticsEntity{" +
            "seqId=" + getSeqId() +
            ", dayCount=" + getDayCount() +
            ", monthCount=" + getMonthCount() +
            ", yearCount=" + getYearCount() +
            ", inDay=" + getInDay() +
            ", inMonth=" + getInMonth() +
            ", inYear=" + getInYear() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
