package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.ReceiverTypeEnum;
import com.hdmon.chatservice.domain.extents.extMessageReceiverEntity;

import java.util.List;

/**
 * Created by UserName on 6/5/2018.
 */
public class ChatMessagesVM {
    private String id;              //For delete/edit
    private Long memberId;          //For delete/edit

    private String messageValue;            //For edit
    private ChatMessageTypeEnum messageType;    //For edit

    private String groupChatId;                             //For create
    private Long senderId;                                  //For create
    private String senderLogin;                             //For create
    private ReceiverTypeEnum receiverType;                      //For create
    private String referMessageId;                          //For create
    private List<extMessageReceiverEntity> receiverLists;    //For create

    public ChatMessagesVM(ChatMessagesVM chatMessagesVM) {
        this.id = chatMessagesVM.getId();
        this.memberId = chatMessagesVM.getMemberId();
        this.messageValue = chatMessagesVM.getMessageValue();
        this.messageType = chatMessagesVM.getMessageType();

        this.groupChatId = chatMessagesVM.getGroupChatId();
        this.senderId = chatMessagesVM.getSenderId();
        this.senderLogin = chatMessagesVM.getSenderLogin();
        this.receiverType = chatMessagesVM.getReceiverType();
        this.referMessageId = chatMessagesVM.getReferMessageId();
        this.receiverLists = chatMessagesVM.getReceiverLists();
    }

    public ChatMessagesVM() {
        // Empty public constructor used by Jackson.
    }

    public String getId() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    public ChatMessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public String getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(String groupChatId) {
        this.groupChatId = groupChatId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public ReceiverTypeEnum getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(ReceiverTypeEnum receiverType) {
        this.receiverType = receiverType;
    }

    public String getReferMessageId() {
        return referMessageId;
    }

    public void setReferMessageId(String referMessageId) {
        this.referMessageId = referMessageId;
    }

    public List<extMessageReceiverEntity> getReceiverLists() {
        return receiverLists;
    }

    public void setReceiverLists(List<extMessageReceiverEntity> receiverLists) {
        this.receiverLists = receiverLists;
    }

    @Override
    public String toString() {
        return "ChatMessagesVM{" +
            "id='" + id + '\'' +
            ", memberId='" + memberId + '\'' +
            ", messageValue='" + messageValue + '\'' +
            ", messageType='" + messageType + '\'' +
            ", groupChatId='" + groupChatId + '\'' +
            ", senderId='" + senderId + '\'' +
            ", senderLogin='" + senderLogin + '\'' +
            ", receiverType='" + receiverType + '\'' +
            ", referMessageId='" + referMessageId + '\'' +
            ", receiverLists='" + receiverLists + '\'' +
            '}';
    }
}
