package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ChatMessageStatistics;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ChatMessageStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatMessageStatisticsRepository extends MongoRepository<ChatMessageStatistics, String> {

}
