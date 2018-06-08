package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.CheckStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FanpageStatistics.
 */
@Document(collection = "fanpage_statistics")
public class FanpageStatisticsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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
    private CheckStatusEnum status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public FanpageStatisticsEntity dayCount(Integer dayCount) {
        this.dayCount = dayCount;
        return this;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public FanpageStatisticsEntity monthCount(Integer monthCount) {
        this.monthCount = monthCount;
        return this;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Integer getYearCount() {
        return yearCount;
    }

    public FanpageStatisticsEntity yearCount(Integer yearCount) {
        this.yearCount = yearCount;
        return this;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public Integer getInDay() {
        return inDay;
    }

    public FanpageStatisticsEntity inDay(Integer inDay) {
        this.inDay = inDay;
        return this;
    }

    public void setInDay(Integer inDay) {
        this.inDay = inDay;
    }

    public Integer getInMonth() {
        return inMonth;
    }

    public FanpageStatisticsEntity inMonth(Integer inMonth) {
        this.inMonth = inMonth;
        return this;
    }

    public void setInMonth(Integer inMonth) {
        this.inMonth = inMonth;
    }

    public Integer getInYear() {
        return inYear;
    }

    public FanpageStatisticsEntity inYear(Integer inYear) {
        this.inYear = inYear;
        return this;
    }

    public void setInYear(Integer inYear) {
        this.inYear = inYear;
    }

    public CheckStatusEnum getStatus() {
        return status;
    }

    public FanpageStatisticsEntity status(CheckStatusEnum status) {
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
        FanpageStatisticsEntity fanpageStatistics = (FanpageStatisticsEntity) o;
        if (fanpageStatistics.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fanpageStatistics.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FanpageStatisticsEntity{" +
            "id=" + getId() +
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
