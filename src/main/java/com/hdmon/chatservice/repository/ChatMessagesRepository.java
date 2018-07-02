package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ChatMessagesEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the ChatMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatMessagesRepository extends MongoRepository<ChatMessagesEntity, String> {
    List<ChatMessagesEntity> findAllByMessageId(String messageId);

    @Query(value="{'createdTime': {'$gt': ?0},'toUserId': ?1}")
    List<ChatMessagesEntity> findAllByCreatedTimeAfterAndToUserIdAndOrderByLastModified(Long createdTime, Long receiverId, Sort sort);

    @Query(value="{'toUserId': ?0}")
    List<ChatMessagesEntity> findAllByToUserIdAndOrderByLastModified(Long receiverId, Sort sort);
}
