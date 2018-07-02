package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ChatGroupStatisticsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ChatGroupStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatGroupStatisticsRepository extends MongoRepository<ChatGroupStatisticsEntity, String> {
    ChatGroupStatisticsEntity findByInDayEqualsAndInMonthEqualsAndInYearEquals(int inDay, int inMonth, int inYear);
}
