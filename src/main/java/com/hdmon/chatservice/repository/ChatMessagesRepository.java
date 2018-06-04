package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ChatMessages;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ChatMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatMessagesRepository extends MongoRepository<ChatMessages, String> {

}
