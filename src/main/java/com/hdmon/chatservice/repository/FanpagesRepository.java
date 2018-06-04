package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.Fanpages;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Fanpages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FanpagesRepository extends MongoRepository<Fanpages, String> {

}
