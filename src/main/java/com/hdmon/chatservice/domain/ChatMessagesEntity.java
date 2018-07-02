package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.domain.enumeration.*;
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
public class ChatMessagesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String seqId;

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

    @Field("message_status")
    private ChatMessageStatusEnum messageStatus;

    @Field("owner_user_name")
    private String ownerUserName;

    @Field("from_user_id")
    private Long fromUserId;

    @Field("from_user_name")
    private String fromUserName;

    @Field("from_full_name")
    private String fromFullName;

    @Field("to_user_id")
    private Long toUserId;

    @Field("to_user_name")
    private String toUserName;

    @Field("send_time")
    private String sendTime;

    @Field("read_time")
    private String readTime;

    @Field("created_time")
    private Long createdTime = new Date().getTime();

    @Field("last_modified_by")
    private String lastModifiedBy;

    @Field("last_modified_time")
    private Long lastModifiedTime = new Date().getTime();

    @Field("max_second_to_action")
    private Integer maxSecondToAction = 300;

    @Field("report_day")
    private Integer reportDay;

    @Field("refer_message_id")
    private String referMessageId;

    @Field("like_count")
    private Long likeCount  = 0L;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
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


    public ChatMessageStatusEnum getMessageStatus() {
        return messageStatus;
    }

    public ChatMessagesEntity messageStatus(ChatMessageStatusEnum messageStatus) {
        this.messageStatus = messageStatus;
        return this;
    }

    public void setMessageStatus(ChatMessageStatusEnum messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public ChatMessagesEntity ownerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
        return this;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public ChatMessagesEntity fromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public ChatMessagesEntity fromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
        return this;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromFullName() {
        return fromFullName;
    }

    public ChatMessagesEntity fromFullName(String fromFullName) {
        this.fromFullName = fromFullName;
        return this;
    }

    public void setFromFullName(String fromFullName) {
        this.fromFullName = fromFullName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public ChatMessagesEntity toUserName(String toUserName) {
        this.toUserName = toUserName;
        return this;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public ChatMessagesEntity toUserId(Long toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public ChatMessagesEntity sendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public ChatMessagesEntity readTime(String readTime) {
        this.readTime = readTime;
        return this;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public ChatMessagesEntity createdTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ChatMessagesEntity lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public ChatMessagesEntity lastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
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

    public Integer getMaxSecondToAction() {
        return maxSecondToAction;
    }

    public ChatMessagesEntity maxSecondToAction(Integer maxSecondToAction) {
        this.maxSecondToAction = maxSecondToAction;
        return this;
    }

    public void setMaxSecondToAction(Integer maxSecondToAction) {
        this.maxSecondToAction = maxSecondToAction;
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

    public Long getLikeCount() {
        return likeCount;
    }

    public ChatMessagesEntity likeCount(Long likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
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
        if (chatMessages.getSeqId() == null || getSeqId() == null) {
            return false;
        }
        return Objects.equals(getSeqId(), chatMessages.getSeqId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSeqId());
    }

    @Override
    public String toString() {
        return "ChatMessagesEntity{" +
            "SeqId=" + getSeqId() +
            ", MessageId='" + getMessageId() + "'" +
            ", GroupChatId='" + getGroupChatId() + "'" +
            ", GroupType='" + getGroupType() + "'" +
            ", MessageValue='" + getMessageValue() + "'" +
            ", MessageType='" + getMessageType() + "'" +
            ", MessageStatus='" + getMessageStatus() + "'" +
            ", OwnerUserName=" + getOwnerUserName() +
            ", FromUserId=" + getFromUserId() +
            ", FromUserName=" + getFromUserName() +
            ", FromFullName='" + getFromFullName() + "'" +
            ", ToUserName='" + getToUserName() + "'" +
            ", ToUserId='" + getToUserId() + "'" +
            ", SendTime='" + getSendTime() + "'" +
            ", ReadTime='" + getReadTime() + "'" +
            ", CreatedTime='" + getCreatedTime() + "'" +
            ", LastModifiedBy='" + getLastModifiedBy() + "'" +
            ", LastModifiedTime=" + getLastModifiedTime() +
            ", MaxSecondToAction='" + getMaxSecondToAction() + "'" +
            ", ReferMessageId='" + getReferMessageId() + "'" +
            ", ReportDay=" + getReportDay() +
            ", LikeCount=" + getLikeCount() +
            "}";
    }
}
