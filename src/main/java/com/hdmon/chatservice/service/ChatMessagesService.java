package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.ChatMessagesEntity;
import com.hdmon.chatservice.domain.GroupMembersEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.MessageReceiverStatusEnum;
import com.hdmon.chatservice.domain.enumeration.ReceiverTypeEnum;
import com.hdmon.chatservice.domain.extents.extMessageReceiverEntity;
import com.hdmon.chatservice.repository.*;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing ChatMessages.
 */
@Service
@Transactional
public class ChatMessagesService {
    private final Logger log = LoggerFactory.getLogger(ChatMessagesService.class);

    private final SequencesRepository sequencesRepository;
    private final ChatMessagesRepository chatMessagesRepository;
    private final GroupMembersRepository groupMembersRepository;
    private final GroupMemberStatisticsRepository groupMemberStatisticsRepository;
    private final ChatMessageStatisticsRepository chatMessageStatisticsRepository;
    private final ContactsRepository contactsRepository;

    public ChatMessagesService(SequencesRepository sequencesRepository, ChatMessagesRepository chatMessagesRepository, GroupMembersRepository groupMembersRepository, GroupMemberStatisticsRepository groupMemberStatisticsRepository, ChatMessageStatisticsRepository chatMessageStatisticsRepository, ContactsRepository contactsRepository) {
        this.sequencesRepository = sequencesRepository;
        this.chatMessagesRepository = chatMessagesRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.groupMemberStatisticsRepository = groupMemberStatisticsRepository;
        this.chatMessageStatisticsRepository = chatMessageStatisticsRepository;
        this.contactsRepository = contactsRepository;
    }

    /**
     * Save a chatMessages.
     *
     * @param chatMessages the entity to save
     * @return the persisted entity
     */
    public ChatMessagesEntity save(ChatMessagesEntity chatMessages) {
        log.debug("Request to save ChatMessages : {}", chatMessages);
        return chatMessagesRepository.save(chatMessages);
    }

    /**
     * Get all the chatMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChatMessagesEntity> findAll(Pageable pageable) {
        log.debug("Request to get all ChatMessages");
        return chatMessagesRepository.findAll(pageable);
    }

    /**
     * Get one chatMessages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ChatMessagesEntity findOne(String id) {
        log.debug("Request to get ChatMessages : {}", id);
        return chatMessagesRepository.findOne(id);
    }

    /**
     * Delete the chatMessages by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ChatMessages : {}", id);
        chatMessagesRepository.delete(id);
    }

    /**
     * Thành viên thực hiện gửi tin nhắn mới lên (06/06/2018).
     * (Hàm bổ sung)
     * @param inputChatMessages: entity của tin nhắn gửi lên
     * @param outResult: id của thành viên thực hiện xóa
     */
    public ChatMessagesEntity create(ChatMessagesEntity inputChatMessages, IsoResponseEntity outResult)
    {
        //Gửi cho những ai
        boolean isInvalidData = false;
        String receiverText = "";
        List<extMessageReceiverEntity> inputReceiverLists = inputChatMessages.getReceiverLists();
        ReceiverTypeEnum inputReceiverType = inputChatMessages.getReceiverType();
        if(inputReceiverType.equals(ReceiverTypeEnum.GROUP))
        {
            //GROUP: Lập danh sách tất cả thành viên
            GroupMembersEntity dbInfoGroup = new GroupMembersEntity();
            GroupMembersService groupService = new GroupMembersService(groupMembersRepository, groupMemberStatisticsRepository, contactsRepository);
            inputReceiverLists = groupService.createMessageReceiverLists(inputChatMessages.getGroupChatId(), dbInfoGroup);
            if(inputReceiverLists == null || inputReceiverLists.size() <= 0)
            {
                isInvalidData = true;
            }
            else
            {
                inputChatMessages.setGroupType(dbInfoGroup.getGroupType());
            }
        }
        else if(inputReceiverType.equals(ReceiverTypeEnum.FRIEND))
        {
            //FRIEND: chỉnh sửa danh sách
            if(inputReceiverLists != null && inputReceiverLists.size() > 0)
            {
                for (extMessageReceiverEntity receiver : inputReceiverLists) {
                    receiver.setUpdateUnixTime(new Date().getTime());
                    receiver.setStatus(MessageReceiverStatusEnum.NEW);

                    receiverText+= "," + receiver.getReceiverId();
                }

                //Bổ sung người nhận là người gửi luôn
                extMessageReceiverEntity ownerItem = new extMessageReceiverEntity();
                ownerItem.setReceiverId(inputChatMessages.getSenderId());
                ownerItem.setReceiverLogin(inputChatMessages.getSenderLogin());
                ownerItem.setUpdateUnixTime(new Date().getTime());
                ownerItem.setStatus(MessageReceiverStatusEnum.NEW);
                inputReceiverLists.add(ownerItem);

                receiverText = inputChatMessages.getSenderId() + receiverText;
            }
            else
            {
                isInvalidData = true;
            }
        }
        else
        {
            //FANPAGE: Lập danh sách tất cả thành viên
        }

        if(isInvalidData)
        {
            outResult.setError(ResponseErrorCode.NOMEMBER.getValue());                 //no-member
            outResult.setMessage("chat_messages_create_error");
            outResult.setException("The recipient must be specified!");

            return  null;
        }
        else {
            //Tạo sequence id
            //SequencesService sequencesService = new SequencesService(sequencesRepository);
            //Long newSequenceId = sequencesService.getNextSequenceId("ChatMessages");
            //inputChatMessages.setGroupChatId(newSequenceId.toString());

            //Danh sách nhận
            inputChatMessages.setReceiverLists(inputReceiverLists);
            inputChatMessages.setReceiverText(receiverText);

            //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
            inputChatMessages.setLastModifiedBy(inputChatMessages.getSenderId().toString());
            inputChatMessages.setLastModifiedDate(Calendar.getInstance().toInstant());
            inputChatMessages.setLastModifiedBy(inputChatMessages.getSenderId().toString());
            inputChatMessages.setLastModifiedDate(Calendar.getInstance().toInstant());

            //Dữ liệu thống kê
            ChatMessageStatisticsService msgStatisticsService = new ChatMessageStatisticsService(chatMessageStatisticsRepository);
            msgStatisticsService.increaseStatistics();
            inputChatMessages.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

            return  chatMessagesRepository.save(inputChatMessages);
        }
    }

    /**
     * Thành viên thực hiện lệnh xóa tin nhắn (05/06/2018).
     * (Hàm bổ sung)
     * @param id: id của tin nhắn
     * @param memberId: id của thành viên thực hiện xóa
     */
    public boolean deleteInfoByMember(String id, Long memberId, IsoResponseEntity outResult) {
        log.debug("Request to delete ChatMessages : {}", memberId);

        boolean isNotFound = true;
        boolean blResult = true;
        ChatMessagesEntity dbInfo = chatMessagesRepository.findOne(id);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            Long lngNowValue = new Date().getTime();
            List<extMessageReceiverEntity> dbReceiverLists = dbInfo.getReceiverLists();
            if(dbReceiverLists != null && dbReceiverLists.size() > 0)
            {
                for (extMessageReceiverEntity msgItem : dbReceiverLists) {
                    if(msgItem.getReceiverId().equals(memberId))
                    {
                        isNotFound = false;
                        msgItem.setStatus(MessageReceiverStatusEnum.DELETED);
                        msgItem.setUpdateUnixTime(lngNowValue);
                        break;
                    }
                }

                //if it is exists
                if(!isNotFound) {
                    //Cập nhật trường LastModifiedUnixTime
                    dbInfo.setLastModifiedUnixTime(lngNowValue);
                    //Người sửa, ngày sửa (log-history)
                    dbInfo.setLastModifiedBy(memberId.toString());
                    dbInfo.setLastModifiedDate(Calendar.getInstance().toInstant());

                    chatMessagesRepository.save(dbInfo);
                }
                else
                {
                    blResult = false;
                }
            }
        }
        else{
            blResult = false;
        }

        //Return more value
        if(isNotFound) {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                 //notfound
            outResult.setMessage("chatmessages_notfound_error");
            outResult.setException("The record is not found!");
        }

        return blResult;
    }

    /**
     * Người tạo thực hiện sửa tin nhắn (05/06/2018).
     * (Hàm bổ sung)
     * @param id: id của tin nhắn
     * @param memberId: id của người tạo
     * @param messageValue: nội dung của tin nhắn mới
     * @param messageType: phân loại nội dung mới
     * @param outResult: dùng để nhận một số lỗi trả về
     */
    public boolean editInfoByOwner(String id, Long memberId, String messageValue, ChatMessageTypeEnum messageType, IsoResponseEntity outResult) {
        log.debug("Request to edit ChatMessages : {}", memberId);

        boolean isNotFound = true;
        boolean blResult = false;
        ChatMessagesEntity dbInfo = chatMessagesRepository.findOne(id);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            if(dbInfo.getSenderId().equals(memberId)) {
                Long lngNowValue = new Date().getTime();
                //Kiểm tra và thực hiện đánh dấu sửa bản ghi
                List<extMessageReceiverEntity> dbReceiverLists = dbInfo.getReceiverLists();
                if (dbReceiverLists != null && dbReceiverLists.size() > 0) {
                    for (extMessageReceiverEntity msgItem : dbReceiverLists) {
                        if (msgItem.getReceiverId().equals(memberId)) {
                            isNotFound = false;
                            msgItem.setStatus(MessageReceiverStatusEnum.EDIT);
                            msgItem.setUpdateUnixTime(lngNowValue);
                            break;
                        }
                    }

                    //Trường hợp tồn tại
                    if (!isNotFound) {
                        blResult = true;

                        //Sửa nội dung chính
                        dbInfo.setLastModifiedUnixTime(lngNowValue);
                        dbInfo.setMessageValue(messageValue);
                        dbInfo.setMessageType(messageType);

                        //Người sửa, ngày sửa (log-history)
                        dbInfo.setLastModifiedBy(memberId.toString());
                        dbInfo.setLastModifiedDate(Calendar.getInstance().toInstant());

                        chatMessagesRepository.save(dbInfo);
                    }
                }
            }
            else
            {
                //Trả về lỗi không có quyền hiệu chỉnh
                outResult.setError(ResponseErrorCode.DENIED.getValue());                                 //denied
                outResult.setMessage("chatmessages_denied_error");
                outResult.setException("Request to edit the message is rejected!");
            }
        }

        //Trả về lỗi không tìm thấy thông tin
        if(isNotFound && outResult.getError() == ResponseErrorCode.SUCCESSFULL.getValue()) {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                     //notfound
            outResult.setMessage("chatmessages_notfound_error");
            outResult.setException("The record is not found!");
        }

        return blResult;
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     * (Hàm bổ sung)
     * @param subAmount: số ngày cần lấy
     * @param receiverId: id của người nhận
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<ChatMessagesEntity> findAllByReportDayAndReceiverId(int subAmount, Long receiverId) {
        log.debug("Request to get ChatMessages (findAllByReportDayAndReceiverId) : {}-{}", subAmount, receiverId);

        //Điều chỉnh dữ liệu cho hợp lệ
        if(subAmount < 0)
            subAmount = 0;

        int reportDayNeed = DataTypeHelper.SubDateTimeAndConvertToReportDay((subAmount + 1), Calendar.DATE);
        List<ChatMessagesEntity> dbList = new ArrayList<>();
        if(receiverId > 0) {
            Sort sortBy = new Sort(Sort.Direction.DESC, "lastModifiedUnixTime");
            dbList = chatMessagesRepository.findAllByReportDayAndReceiverId(reportDayNeed, receiverId, sortBy);
        }
        return dbList;
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     * (Hàm bổ sung)
     * @param receiverId: id của người nhận
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<ChatMessagesEntity> findAllByReceiverIdAndOrderByLastModifiedDesc(Long receiverId) {
        log.debug("Request to get ChatMessages (findAllByReceiverIdAndOrderByLastModifiedDesc) : {}", receiverId);
        List<ChatMessagesEntity> dbList = new ArrayList<>();
        if(receiverId > 0) {
            Sort sortBy = new Sort(Sort.Direction.DESC, "lastModifiedUnixTime");
            dbList = chatMessagesRepository.findAllByReceiverIdAndOrderByLastModifiedDesc(receiverId, sortBy);
        }
        return dbList;
    }
}
