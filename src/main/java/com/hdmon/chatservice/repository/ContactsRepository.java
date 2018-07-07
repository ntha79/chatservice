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
public interface ContactsRepository extends MongoRepository<ContactsEntity, Long> {
    ContactsEntity findOneByOwnerUsername(String ownerUsername);

    ContactsEntity findOneByOwnerUserid(Long ownerUserid);

    @Query(value="{'ownerUsername': {'$eq': ?0}, 'friendLists.username': {'$in': ?1}}")
    List<ContactsEntity> findAllByFriendUserNameAndOrderByFriendUserNameAsc(String ownerUsername, String friendUsername, Sort sort);

    @Query(value="{'friendLists.username': {'$in': ?0}}")
    List<ContactsEntity> findAllByFriendUserNameAndOrderByFriendUserNameAsc(String friendUsername, Sort sort);
}
