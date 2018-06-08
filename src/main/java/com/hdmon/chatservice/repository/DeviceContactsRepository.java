package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.DeviceContactsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Contacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceContactsRepository extends MongoRepository<DeviceContactsEntity, String> {
    DeviceContactsEntity findOneByownerId(Long ownerId);
}
