package com.hdmon.chatservice.repository;

import com.hdmon.chatservice.domain.SequencesEntity;
import com.hdmon.chatservice.exception.SequencesException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Sequences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequencesRepository extends MongoRepository<SequencesEntity, String> {
}
