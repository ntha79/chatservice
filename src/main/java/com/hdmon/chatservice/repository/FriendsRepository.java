package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.Friends;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Friends entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendsRepository extends MongoRepository<Friends, String> {

}
