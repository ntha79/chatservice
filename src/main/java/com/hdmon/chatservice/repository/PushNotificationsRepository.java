package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.PushNotifications;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PushNotifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PushNotificationsRepository extends MongoRepository<PushNotifications, String> {

}
