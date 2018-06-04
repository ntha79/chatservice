package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.Contacts;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Contacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactsRepository extends MongoRepository<Contacts, String> {

}
