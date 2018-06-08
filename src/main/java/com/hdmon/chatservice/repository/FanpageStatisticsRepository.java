package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.FanpageStatisticsEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FanpageStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FanpageStatisticsRepository extends MongoRepository<FanpageStatisticsEntity, String> {
    FanpageStatisticsEntity findByInDayEqualsAndInMonthEqualsAndInYearEquals(int inDay, int inMonth, int inYear);
}
