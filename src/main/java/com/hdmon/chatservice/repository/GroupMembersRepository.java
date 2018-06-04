package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.GroupMembers;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the GroupMembers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupMembersRepository extends MongoRepository<GroupMembers, String> {

}
