package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.GroupMemberStatisticsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GroupMemberStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupMemberStatisticsRepository extends MongoRepository<GroupMemberStatisticsEntity, String> {
    GroupMemberStatisticsEntity findByInDayEqualsAndInMonthEqualsAndInYearEquals(int inDay, int inMonth, int inYear);
}
