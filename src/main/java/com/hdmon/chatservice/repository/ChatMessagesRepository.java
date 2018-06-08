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
    @Query(value="{'reportDay': {'$gt': ?0},'receiverLists.receiverId': ?1}")
    List<ChatMessagesEntity> findAllByReportDayAndReceiverId(int reportDay, Long receiverId, Sort sort);

    @Query(value="{'receiverLists.receiverId': ?0}")
    List<ChatMessagesEntity> findAllByReceiverIdAndOrderByLastModifiedDesc(Long receiverId, Sort sort);
}
