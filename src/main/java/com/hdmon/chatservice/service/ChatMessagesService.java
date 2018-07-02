package com.hdmon.chatservice.service;

import com.hdmon.chatservice.config.ApplicationProperties;
import com.hdmon.chatservice.domain.ChatMessagesEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.ChatMessageStatusEnum;
import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.enumeration.UserFindTypeEnum;
import com.hdmon.chatservice.repository.ChatMessagesRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.service.util.UserHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.Messages.CreateNewMessageVM;
import com.hdmon.chatservice.web.rest.vm.Messages.EditMessageVM;
import com.hdmon.chatservice.web.rest.vm.Messages.OutputMessageVM;
import com.hdmon.chatservice.web.rest.vm.Messages.UpdateMessageVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    private final ChatMessagesRepository chatMessagesRepository;
    private final ChatMessageStatisticsService chatMessageStatisticsService;

    private final ApplicationProperties applicationProperties;
    private String gatewayUrl;

    public ChatMessagesService(ChatMessagesRepository chatMessagesRepository, ChatMessageStatisticsService chatMessageStatisticsService, ApplicationProperties applicationProperties) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.chatMessageStatisticsService = chatMessageStatisticsService;

        this.applicationProperties = applicationProperties;
        this.gatewayUrl = this.applicationProperties.getPortal().getGatewayUrl();
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
     * Thành viên thực hiện gửi tin nhắn mới lên.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @param viewModel: entity của tin nhắn gửi lên
     * @param outputEntity: entity trả kết quả về cho client
     */
    public ChatMessagesEntity createMessage(HttpServletRequest request, CreateNewMessageVM viewModel, IsoResponseEntity outputEntity)
    {
        ChatMessagesEntity dbSourceInfo = new ChatMessagesEntity();

        //Lấy thông tin tài khoản người tạo
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, viewModel.getFromUserName(), "");
        Long toUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, viewModel.getToUserName(), "");
        if(fromUserId > 0 && toUserId > 0) {
            dbSourceInfo.setMessageId(viewModel.getMsgId());
            dbSourceInfo.setGroupChatId(viewModel.getCode());
            dbSourceInfo.setGroupType(viewModel.getCodeType());                     //Client gửi lên cho đỡ tốn time
            dbSourceInfo.setMessageValue(viewModel.getMessage());
            dbSourceInfo.setMessageType(viewModel.getMsgType());
            dbSourceInfo.setMessageStatus(ChatMessageStatusEnum.RECEIVED);          //Xem lại chỗ này
            dbSourceInfo.setOwnerUserName(viewModel.getOwnerUserName());
            dbSourceInfo.setFromUserId(fromUserId);
            dbSourceInfo.setFromUserName(viewModel.getFromUserName());
            dbSourceInfo.setFromFullName(viewModel.getFromFullName());
            dbSourceInfo.setToUserName(viewModel.getToUserName());
            dbSourceInfo.setToUserId(toUserId);
            dbSourceInfo.setSendTime(viewModel.getSendTime());
            dbSourceInfo.setReadTime("");
            dbSourceInfo.setMaxSecondToAction(applicationProperties.getChatService().getChatmessageMaxSecondToAction());
            dbSourceInfo.setReferMessageId(viewModel.getReferMessageId());

            //Dữ liệu thống kê
            chatMessageStatisticsService.increaseStatistics();
            dbSourceInfo.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

            dbSourceInfo = chatMessagesRepository.save(dbSourceInfo);
        }
        else
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("chatmessages_create_notfound");
            outputEntity.setException("The user info is notfound!");
        }
        return dbSourceInfo;
    }

    /**
     * Người tạo thực hiện sửa tin nhắn (05/06/2018).
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @param viewModel: entity của tin nhắn gửi lêni
     * @param outResult: dùng để nhận một số lỗi trả về
     */
    public EditMessageVM editContentByMember(HttpServletRequest request, EditMessageVM viewModel, IsoResponseEntity outResult) {
        log.debug("Request to edit ChatMessages : {}", viewModel);

        //Lấy thông tin tài khoản người thao tác
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, viewModel.getActionUserName(), "");
        if(fromUserId > 0) {
            List<ChatMessagesEntity> dbSourceList = chatMessagesRepository.findAllByMessageId(viewModel.getMsgId());
            if (dbSourceList != null && dbSourceList.size() > 0) {
                for (ChatMessagesEntity msgItem : dbSourceList) {
                    msgItem.setMessageType(viewModel.getMsgType());
                    msgItem.setMessageValue(viewModel.getMessage());

                    msgItem.setLastModifiedBy(viewModel.getActionUserName());
                    msgItem.setLastModifiedTime(new Date().getTime());

                    chatMessagesRepository.save(msgItem);
                }
            } else {
                outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                     //notfound
                outResult.setMessage("chatmessages_editbyowner_notfound");
                outResult.setException("The message info is not found!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //rejected
            outResult.setMessage("chatmessages_editbyowner_rejected");
            outResult.setException("Request to update this message is rejected!");
        }

        return viewModel;
    }

    /**
     * Người tạo thực hiện sửa tin nhắn (05/06/2018).
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @param viewModel: entity của tin nhắn gửi lêni
     * @param outResult: dùng để nhận một số lỗi trả về
     */
    public UpdateMessageVM updateStatusByMember(HttpServletRequest request, UpdateMessageVM viewModel, IsoResponseEntity outResult) {
        log.debug("Request to update ChatMessages : {}", viewModel);

        //Lấy thông tin tài khoản người thao tác
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, viewModel.getActionUserName(), "");
        if(fromUserId > 0) {
            List<ChatMessagesEntity> dbSourceList = chatMessagesRepository.findAllByMessageId(viewModel.getMsgId());
            if (dbSourceList != null && dbSourceList.size() > 0) {
                for (ChatMessagesEntity msgItem : dbSourceList) {
                    ChatMessageStatusEnum newStatus = viewModel.getMsgStatus();
                    if(newStatus != ChatMessageStatusEnum.UNKNOW) {
                        msgItem.setMessageStatus(viewModel.getMsgStatus());

                        if (!viewModel.getChangeTime().isEmpty()) {
                            if(newStatus != ChatMessageStatusEnum.RECEIVED) {
                                msgItem.setSendTime(viewModel.getChangeTime());
                            }
                            else if(newStatus != ChatMessageStatusEnum.READED)
                            {
                                msgItem.setReadTime(viewModel.getChangeTime());
                            }
                        }
                    }

                    msgItem.setLastModifiedBy(viewModel.getActionUserName());
                    msgItem.setLastModifiedTime(new Date().getTime());
                    chatMessagesRepository.save(msgItem);
                }
            } else {
                outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                               //notfound
                outResult.setMessage("chatmessages_updateinfo_notfound");
                outResult.setException("The message info is not found!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //rejected
            outResult.setMessage("chatmessages_updateinfo_rejected");
            outResult.setException("Request to update this message is rejected!");
        }

        return viewModel;
    }

    /**
     * Thành viên thực hiện lệnh xóa tin nhắn.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-30
     * @param messageId: id của tin nhắn
     * @param actionUsername: username của thành viên thực hiện xóa
     */
    public boolean deleteByMember(HttpServletRequest request, boolean allowDeleteAll, String messageId, String actionUsername, String outErrorType, IsoResponseEntity outResult) {
        log.debug("Request to delete ChatMessages : {}", messageId);

        boolean blResult = false;
        //Lấy thông tin tài khoản người thao tác
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, actionUsername, "");
        if(fromUserId > 0) {
            List<ChatMessagesEntity> dbSourceList = chatMessagesRepository.findAllByMessageId(messageId);
            if (dbSourceList != null && dbSourceList.size() > 0) {
                for (ChatMessagesEntity msgItem : dbSourceList)
                {
                    if(allowDeleteAll) {
                        if(msgItem.getFromUserName().equals(actionUsername)) {
                            blResult = true;
                            chatMessagesRepository.delete(msgItem.getSeqId());
                        }
                    }
                    else
                    {
                        if (msgItem.getToUserName().equals(actionUsername)) {
                            blResult = true;
                            chatMessagesRepository.delete(msgItem.getSeqId());
                            break;
                        }
                    }
                }
            }

            //Return more value
            if (!blResult) {
                outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                //notfound
                outResult.setMessage((outErrorType + "_notfound"));
                outResult.setException("The message info is not found!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //rejected
            outResult.setMessage((outErrorType + "_rejected"));
            outResult.setException("Request to update this message is rejected!");
        }

        return blResult;
    }

    /**
     * Thành viên thực hiện lệnh thích tin nhắn.
     * (Hàm bổ sung)
     * Create Time: 2018-07-02
     * Update Time: 2018-07-02
     * @param messageId: id của tin nhắn
     * @param actionUsername: username của thành viên thực hiện thích
     */
    public Long likeByMember(HttpServletRequest request, String messageId, String actionUsername, IsoResponseEntity outResult) {
        log.debug("Request to like ChatMessages : {}", messageId);

        Long intLikeCount = 0L;
        boolean blResult = false;
        //Lấy thông tin tài khoản người thao tác
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, actionUsername, "");
        if(fromUserId > 0) {
            List<ChatMessagesEntity> dbSourceList = chatMessagesRepository.findAllByMessageId(messageId);
            if (dbSourceList != null && dbSourceList.size() > 0) {
                for (ChatMessagesEntity msgItem : dbSourceList)
                {
                    Long curLikeCount = msgItem.getLikeCount();
                    msgItem.setLikeCount((curLikeCount + 1));
                    chatMessagesRepository.save(msgItem);
                    blResult = true;

                    intLikeCount += (curLikeCount + 1);
                }
            }

            //Return more value
            if (!blResult) {
                outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                //notfound
                outResult.setMessage("chatmessages_likebymember_notfound");
                outResult.setException("The message info is not found!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //rejected
            outResult.setMessage("chatmessages_likebymember_rejected");
            outResult.setException("Request to update this message is rejected!");
        }

        return intLikeCount;
    }

    /**
     * Thành viên thực hiện lệnh thích tin nhắn.
     * (Hàm bổ sung)
     * Create Time: 2018-07-02
     * Update Time: 2018-07-02
     * @param messageId: id của tin nhắn
     * @param actionUsername: username của thành viên thực hiện thích
     */
    public Long dislikeByMember(HttpServletRequest request, String messageId, String actionUsername, IsoResponseEntity outResult) {
        log.debug("Request to unlike ChatMessages : {}", messageId);

        Long intLikeCount = 0L;
        boolean blResult = false;
        //Lấy thông tin tài khoản người thao tác
        Long fromUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, actionUsername, "");
        if(fromUserId > 0) {
            List<ChatMessagesEntity> dbSourceList = chatMessagesRepository.findAllByMessageId(messageId);
            if (dbSourceList != null && dbSourceList.size() > 0) {
                for (ChatMessagesEntity msgItem : dbSourceList)
                {
                    blResult = true;

                    Long curLikeCount = msgItem.getLikeCount();
                    if(curLikeCount > 0) {
                        msgItem.setLikeCount((curLikeCount - 1));
                        chatMessagesRepository.save(msgItem);

                        intLikeCount += (curLikeCount - 1);
                    }
                }
            }

            //Return more value
            if (!blResult) {
                outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                //notfound
                outResult.setMessage("chatmessages_dislikebymember_notfound");
                outResult.setException("The message info is not found!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //rejected
            outResult.setMessage("chatmessages_dislikebymember_rejected");
            outResult.setException("Request to update this message is rejected!");
        }

        return intLikeCount;
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-30
     * @param lastRequestTime: thời gian gần nhất đã lấy
     * @param receiverId: id của người nhận
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OutputMessageVM findAllByReportDayAndReceiverId(Long lastRequestTime, Long receiverId) {
        log.debug("Request to get ChatMessages (findAllByReportDayAndReceiverId) : {}-{}", lastRequestTime, receiverId);
        OutputMessageVM responseEntity = new OutputMessageVM();
        //Điều chỉnh dữ liệu cho hợp lệ
        if(lastRequestTime < 0)
            lastRequestTime = 0L;

        if(receiverId > 0) {
            Sort sortBy = new Sort(Sort.Direction.ASC, "lastModifiedTime");
            List<ChatMessagesEntity> dbSourceList  = chatMessagesRepository.findAllByCreatedTimeAfterAndToUserIdAndOrderByLastModified(lastRequestTime, receiverId, sortBy);
            if(dbSourceList != null && dbSourceList.size() > 0)
            {
                lastRequestTime = dbSourceList.get(dbSourceList.size() - 1).getCreatedTime();
                responseEntity.setLastRequestTime(lastRequestTime);
                responseEntity.setMessagesList(dbSourceList);
            }
        }
        return responseEntity;
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-30
     * @param receiverId: id của người nhận
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OutputMessageVM findAllByReceiverIdAndOrderByLastModifiedDesc(Long receiverId) {
        log.debug("Request to get ChatMessages (findAllByReceiverIdAndOrderByLastModifiedDesc) : {}", receiverId);
        OutputMessageVM responseEntity = new OutputMessageVM();
        if(receiverId > 0) {
            Sort sortBy = new Sort(Sort.Direction.ASC, "lastModifiedTime");
            List<ChatMessagesEntity> dbSourceList  = chatMessagesRepository.findAllByToUserIdAndOrderByLastModified(receiverId, sortBy);
            if(dbSourceList != null && dbSourceList.size() > 0)
            {
                Long lastRequestTime = dbSourceList.get(dbSourceList.size() - 1).getCreatedTime();
                responseEntity.setLastRequestTime(lastRequestTime);
                responseEntity.setMessagesList(dbSourceList);
            }
        }
        return responseEntity;
    }
}
