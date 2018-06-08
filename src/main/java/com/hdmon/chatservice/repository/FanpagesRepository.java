package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.FanpagesEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Fanpages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FanpagesRepository extends MongoRepository<FanpagesEntity, String> {
    List<FanpagesEntity> findAllByownerId(Long ownerId);
}
