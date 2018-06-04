package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.FanpageStatistics;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FanpageStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FanpageStatisticsRepository extends MongoRepository<FanpageStatistics, String> {

}
