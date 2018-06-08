package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.ServiceSettingsEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ServiceSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSettingsRepository extends MongoRepository<ServiceSettingsEntity, String> {

}
