package com.hdmon.chatservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by UserName on 6/6/2018.
 */
@Document(collection = "sequences")
public class SequencesEntity implements Serializable {
    @Id
    private String id;

    private long seq;

    public SequencesEntity()
    {
        super();
    }

    public SequencesEntity(String id, long seq)
    {
        this.id = id;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Sequences{" +
            "id=" + getId() +
            ", seq='" + getSeq() + "'" +
            "}";
    }
}
