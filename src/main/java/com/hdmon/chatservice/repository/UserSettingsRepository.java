package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.UserSettings;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSettingsRepository extends MongoRepository<UserSettings, String> {

}
