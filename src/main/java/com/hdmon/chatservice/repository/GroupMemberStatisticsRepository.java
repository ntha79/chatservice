package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.GroupMemberStatistics;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the GroupMemberStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupMemberStatisticsRepository extends MongoRepository<GroupMemberStatistics, String> {

}
