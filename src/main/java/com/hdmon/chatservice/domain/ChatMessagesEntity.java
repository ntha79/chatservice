package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.enumeration.ReceiverTypeEnum;
import com.hdmon.chatservice.domain.extents.extMessageReceiverEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A ChatMessages.
 */
@Document(collection = "chat_messages")
public class ChatMessagesEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("message_id")
    private String messageId = UUID.randomUUID().toString();

    @Field("group_chat_id")
    private String groupChatId;

    @Field("group_type")
    private GroupTypeEnum groupType;

    @Field("message_value")
    private String messageValue;

    @Field("message_type")
    private ChatMessageTypeEnum messageType;

    @Field("sender_id")
    private Long senderId;

    @Field("sender_login")
    private String senderLogin;

    @Field("receiver_lists")
    private List<extMessageReceiverEntity> receiverLists;

    @Field("receiver_type")
    private ReceiverTypeEnum receiverType;

    @Field("receiver_text")
    private String receiverText;

    @Field("created_unix_time")
    private Long createdUnixTime = new Date().getTime();

    @Field("last_modified_unix_time")
    private Long lastModifiedUnixTime = new Date().getTime();

    @Field("report_day")
    private Integer reportDay;

    @Field("max_time_to_action")
    private Integer maxTimeToAction = 300;

    @Field("refer_message_id")
    private String referMessageId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public ChatMessagesEntity messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getGroupChatId() {
        return groupChatId;
    }

    public ChatMessagesEntity groupChatId(String groupChatId) {
        this.groupChatId = groupChatId;
        return this;
    }

    public void setGroupChatId(String groupChatId) {
        this.groupChatId = groupChatId;
    }

    public GroupTypeEnum getGroupType() {
        return groupType;
    }

    public ChatMessagesEntity groupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupTypeEnum groupType) {
        this.groupType = groupType;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public ChatMessagesEntity messageValue(String messageValue) {
        this.messageValue = messageValue;
        return this;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    public ChatMessageTypeEnum getMessageType() {
        return messageType;
    }

    public ChatMessagesEntity messageType(ChatMessageTypeEnum messageType) {
        this.messageType = messageType;
        return this;
    }

    public void setMessageType(ChatMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public Long getSenderId() {
        return senderId;
    }

    public ChatMessagesEntity senderId(Long senderId) {
        this.senderId = senderId;
        return this;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public ChatMessagesEntity senderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
        return this;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public List<extMessageReceiverEntity> getReceiverLists() {
        return receiverLists;
    }

    public ChatMessagesEntity receiverLists(List<extMessageReceiverEntity> receiverLists) {
        this.receiverLists = receiverLists;
        return this;
    }

    public void setReceiverLists(List<extMessageReceiverEntity> receiverLists) {
        this.receiverLists = receiverLists;
    }

    public ReceiverTypeEnum getReceiverType() {
        return receiverType;
    }

    public ChatMessagesEntity receiverType(ReceiverTypeEnum receiverType) {
        this.receiverType = receiverType;
        return this;
    }

    public void setReceiverType(ReceiverTypeEnum receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverText() {
        return receiverText;
    }

    public ChatMessagesEntity receiverText(String receiverText) {
        this.receiverText = receiverText;
        return this;
    }

    public void setReceiverText(String receiverText) {
        this.receiverText = receiverText;
    }

    public Long getCreatedUnixTime() {
        return createdUnixTime;
    }

    public ChatMessagesEntity createdUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
        return this;
    }

    public void setCreatedUnixTime(Long createdUnixTime) {
        this.createdUnixTime = createdUnixTime;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public ChatMessagesEntity lastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        return this;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public ChatMessagesEntity reportDay(Integer reportDay) {
        this.reportDay = reportDay;
        return this;
    }

    public void setReportDay(Integer reportDay) {
        this.reportDay = reportDay;
    }

    public Integer getMaxTimeToAction() {
        return maxTimeToAction;
    }

    public ChatMessagesEntity maxTimeToAction(Integer maxTimeToAction) {
        this.maxTimeToAction = maxTimeToAction;
        return this;
    }

    public void setMaxTimeToAction(Integer maxTimeToAction) {
        this.maxTimeToAction = maxTimeToAction;
    }

    public String getReferMessageId() {
        return referMessageId;
    }

    public ChatMessagesEntity referMessageId(String referMessageId) {
        this.referMessageId = referMessageId;
        return this;
    }

    public void setReferMessageId(String referMessageId) {
        this.referMessageId = referMessageId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatMessagesEntity chatMessages = (ChatMessagesEntity) o;
        if (chatMessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chatMessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChatMessagesEntity{" +
            "id=" + getId() +
            ", messageId='" + getMessageId() + "'" +
            ", groupChatId='" + getGroupChatId() + "'" +
            ", groupType='" + getGroupType() + "'" +
            ", messageValue='" + getMessageValue() + "'" +
            ", messageType='" + getMessageType() + "'" +
            ", senderId=" + getSenderId() +
            ", senderLogin='" + getSenderLogin() + "'" +
            ", receiverLists='" + getReceiverLists() + "'" +
            ", receiverType='" + getReceiverType() + "'" +
            ", receiverText='" + getReceiverText() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUnixTime=" + getCreatedUnixTime() +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedUnixTime=" + getLastModifiedUnixTime() +
            ", reportDay=" + getReportDay() +
            ", maxTimeToAction=" + getMaxTimeToAction() +
            ", referMessageId=" + getReferMessageId() +
            "}";
    }
}
