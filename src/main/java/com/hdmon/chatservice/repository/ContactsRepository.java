package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ContactsEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Contacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactsRepository extends MongoRepository<ContactsEntity, String> {
    ContactsEntity findOneByownerId(Long ownerId);

    @Async
    @Query(value="{'ownerId': {'$eq': ?0}, 'friendLists.friendLogin': {'$in': ?1}}")
    List<ContactsEntity> findAllByLoginNameAndOrderByLoginNameAsc(Long ownerId, String loginName, Sort sort);

    @Async
    @Query(value="{'friendLists.friendLogin': {'$in': ?0}}")
    List<ContactsEntity> findAllByLoginNameAndOrderByLoginNameAsc(String loginName, Sort sort);
}
