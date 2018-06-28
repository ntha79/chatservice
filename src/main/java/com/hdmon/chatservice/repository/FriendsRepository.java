package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.FriendsEntity;
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
public interface FriendsRepository extends MongoRepository<FriendsEntity, String> {
    @Async
    FriendsEntity findOneByOwnerUsername(String ownerUsername);

    @Async
    @Query(value="{'ownerUsername': {'$eq': ?0}, 'friendLists.username': {'$in': ?1}}")
    List<FriendsEntity> findAllByFriendUserNameAndOrderByFriendUserNameAsc(String ownerUsername, String friendUsername, Sort sort);

    @Async
    @Query(value="{'friendLists.username': {'$in': ?0}}")
    List<FriendsEntity> findAllByFriendUserNameAndOrderByFriendUserNameAsc(String friendUsername, Sort sort);
}
