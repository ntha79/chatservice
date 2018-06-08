package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.SequencesEntity;
import com.hdmon.chatservice.exception.SequencesException;
import com.hdmon.chatservice.repository.ChatMessageStatisticsRepository;
import com.hdmon.chatservice.repository.ChatMessagesRepository;
import com.hdmon.chatservice.repository.GroupMembersRepository;
import com.hdmon.chatservice.repository.SequencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by UserName on 6/6/2018.
 */
@Service
@Transactional
public class SequencesService {
    private final SequencesRepository sequencesRepository;

    public SequencesService(SequencesRepository sequencesRepository) {
        this.sequencesRepository = sequencesRepository;
    }

    public long getNextSequenceId(String key) throws SequencesException {
        SequencesEntity dbInfo = sequencesRepository.findOne(key);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            dbInfo.setSeq(dbInfo.getSeq() + 1);
        }
        else
        {
            dbInfo = new SequencesEntity(key, 1);
        }

        SequencesEntity seqId = sequencesRepository.save(dbInfo);
        if (seqId == null) {
            throw new SequencesException("Unable to get sequence id for key : " + key);
        }
        return seqId.getSeq();
    }
}
