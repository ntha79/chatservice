package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.ChatMessagesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 7/2/2018.
 */
public class OutputMessageVM {
    private Long lastRequestTime;
    private List<ChatMessagesEntity> messagesList;

    public OutputMessageVM(OutputMessageVM entity)
    {
        //this.lastRequestTime =
    }

    public OutputMessageVM(Long lastRequestTime, List<ChatMessagesEntity> messagesList)
    {
        this.lastRequestTime = lastRequestTime;
        this.messagesList = messagesList;
    }

    public OutputMessageVM()
    {
        super();

        lastRequestTime = 0L;
        messagesList = new ArrayList<>();
    }

    public Long getLastRequestTime() {
        return lastRequestTime;
    }

    public OutputMessageVM lastRequestTime(Long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
        return this;
    }

    public void setLastRequestTime(Long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public List<ChatMessagesEntity> getMessagesList() {
        return messagesList;
    }

    public OutputMessageVM messagesList(List<ChatMessagesEntity> messagesList) {
        this.messagesList = messagesList;
        return this;
    }

    public void setMessagesList(List<ChatMessagesEntity> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public String toString() {
        return "OutputMessageVM{" +
            "LastRequestTime='" + getLastRequestTime() + '\'' +
            ", MessagesList='" + getMessagesList() + '\'' +
            '}';
    }
}
