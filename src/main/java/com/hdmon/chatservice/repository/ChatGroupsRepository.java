package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ChatGroupsEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ChatGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatGroupsRepository extends MongoRepository<ChatGroupsEntity, String> {

}
