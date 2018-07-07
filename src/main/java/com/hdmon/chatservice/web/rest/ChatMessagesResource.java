package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.config.ApplicationProperties;
import com.hdmon.chatservice.domain.ChatGroupsEntity;
import com.hdmon.chatservice.domain.ChatMessagesEntity;

import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.service.ChatMessagesService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.BusinessUtil;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;

import com.hdmon.chatservice.web.rest.vm.Messages.*;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChatMessages.
 */
@RestController
@RequestMapping("/api")
public class ChatMessagesResource {

    private final Logger log = LoggerFactory.getLogger(ChatMessagesResource.class);

    private static final String ENTITY_NAME = "chatMessages";

    private final ChatMessagesService chatMessagesService;

    public ChatMessagesResource(ChatMessagesService chatMessagesService) {
        this.chatMessagesService = chatMessagesService;
    }

    /**
     * POST  /chat-messages : Create a new chatMessages.
     *
     * @param chatMessages the chatMessages to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chatMessages, or with status 400 (Bad Request) if the chatMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatmessages")
    @Timed
    public ResponseEntity<ChatMessagesEntity> createChatMessages(@RequestBody ChatMessagesEntity chatMessages) throws URISyntaxException {
        log.debug("REST request to save ChatMessages : {}", chatMessages);
        if (chatMessages.getSeqId() != null) {
            throw new BadRequestAlertException("A new chatMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatMessagesEntity result = chatMessagesService.save(chatMessages);
        return ResponseEntity.created(new URI("/api/chatmessages/" + result.getSeqId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSeqId()))
            .body(result);
    }

    /**
     * PUT  /chat-messages : Updates an existing chatMessages.
     *
     * @param chatMessages the chatMessages to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chatMessages,
     * or with status 400 (Bad Request) if the chatMessages is not valid,
     * or with status 500 (Internal Server Error) if the chatMessages couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chatmessages")
    @Timed
    public ResponseEntity<ChatMessagesEntity> updateChatMessages(@RequestBody ChatMessagesEntity chatMessages) throws URISyntaxException {
        log.debug("REST request to update ChatMessages : {}", chatMessages);
        if (chatMessages.getSeqId() == null) {
            return createChatMessages(chatMessages);
        }
        ChatMessagesEntity result = chatMessagesService.save(chatMessages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatMessages.getSeqId()))
            .body(result);
    }

    /**
     * GET  /chat-messages : get all the chatMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatMessages in body
     */
    @GetMapping("/chatmessages")
    @Timed
    public ResponseEntity<List<ChatMessagesEntity>> getAllChatMessages(Pageable pageable) {
        log.debug("REST request to get a page of ChatMessages");
        Page<ChatMessagesEntity> page = chatMessagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chatmessages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chatmessages/:id : get the "id" chatmessages.
     *
     * @param id the id of the chatmessages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatmessages, or with status 404 (Not Found)
     */
    @GetMapping("/chatmessages/{id}")
    @Timed
    public ResponseEntity<ChatMessagesEntity> getContacts(@PathVariable String id) {
        log.debug("REST request to get ChatMessages : {}", id);
        ChatMessagesEntity chatmessages = chatMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatmessages));
    }

    /**
     * DELETE  /chatmessages/:id : delete the "id" chatMessages.
     *
     * @param id the id of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chatmessages/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMembers(@PathVariable String id) {
        log.debug("REST request to delete ChatGroups : {}", id);
        chatMessagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*==================================================================================================================
    * ================CAC HAM BO SUNG===================================================================================
    * ================================================================================================================*/

    /**
     * DELETE  /chatmessages/deleteonebymember : delete for chatMessage item.
     *
     * @param viewModel the viewModel of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/deleteonebymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteOneByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody ActionMessageVM viewModel) {
        log.debug("REST request to delete ChatMessages by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_deleteonebymember_invalid");
                    responseEntity.setException("The fields MsgId, ActionUserName are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_deleteonebymember_invalid", "The fields MsgId, ActionUserName are not allowed NULL!");
                } else {
                    boolean blResult = chatMessagesService.deleteByMember(request, false, viewModel.getMsgId(), viewModel.getActionUserName(), "chatmessages_deleteonebymember", responseEntity);
                    if (blResult) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(blResult);
                        responseEntity.setMessage("chatmessages_deleteonebymember_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(blResult));
                    } else if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.DELETEFAIL.getValue());                 //Leave fail
                        responseEntity.setMessage("chatmessages_deleteonebymember_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_deleteonebymember_fail", "Delete message on server fail, please try again!");
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * DELETE  /chatmessages/deleteallbymember : delete chatMessage item.
     *
     * @param viewModel the viewModel of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/deleteallbymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteAllByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody ActionMessageVM viewModel) {
        log.debug("REST request to delete ChatMessages by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_deleteallbymember_invalid");
                    responseEntity.setException("The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_deleteallbymember_invalid", "The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                } else {
                    boolean blResult = chatMessagesService.deleteByMember(request, true,  viewModel.getMsgId(), viewModel.getActionUserName(), "chatmessages_deleteallbymember", responseEntity);
                    if (blResult) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(blResult);
                        responseEntity.setMessage("chatmessages_deleteallbymember_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(blResult));
                    } else if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.DELETEFAIL.getValue());                 //Leave fail
                        responseEntity.setMessage("chatmessages_deleteallbymember_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_deleteallbymember_fail", "Delete message on server fail, please try again!");
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * EDIT  /chatmessages/editbymember : edit value for chatMessage item.
     *
     * @param viewModel the viewModel of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/editcontentbymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> editContentChatMessagesByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody EditMessageVM viewModel) {
        log.debug("REST request to edit ChatMessages by owner: {}", viewModel);

        IsoResponseEntity<EditMessageVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null  || viewModel.getMessage() == null ) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_editbyowner_invalid");
                    responseEntity.setException("The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_editbyowner_invalid", "The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                } else {
                    EditMessageVM dbResult = chatMessagesService.editContentByMember(request, viewModel, responseEntity);
                    if (dbResult != null) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(dbResult);
                        responseEntity.setMessage("chatmessages_editbyowner_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResult));
                    } else if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                 //Leave fail
                        responseEntity.setMessage("chatmessages_editbyowner_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_editbyowner_fail", "Edit message on server fail, please try again!");
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * EDIT  /chatmessages/updateinfo : update status for chatMessage item.
     *
     * @param viewModel the viewModel of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/updatestatus")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateStatusChatMessagesByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdateMessageVM viewModel) {
        log.debug("REST request to update ChatMessages by owner: {}", viewModel);

        IsoResponseEntity<UpdateMessageVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null ) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_updateinfo_invalid");
                    responseEntity.setException("The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_updateinfo_invalid", "The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                } else {
                    UpdateMessageVM dbResult = chatMessagesService.updateStatusByMember(request, viewModel, responseEntity);
                    if (dbResult != null) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(dbResult);
                        responseEntity.setMessage("chatmessages_updateinfo_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResult));
                    } else if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                 //Leave fail
                        responseEntity.setMessage("chatmessages_updateinfo_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_updateinfo_fail", "Update message on server fail, please try again!");
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Hàm gửi tin nhắn lên server để lưu vào Database
     *
     * @param viewModel: nội dung tin nhắn cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/create")
    @Timed
    public ResponseEntity<IsoResponseEntity> createNewChatMessages(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateNewMessageVM viewModel) {
        log.debug("REST request to create ChatMessages: {}", viewModel);

        IsoResponseEntity<ChatMessagesEntity> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getFromUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getFromUserName() == null  || viewModel.getToUserName() == null  || viewModel.getMessage() == null ) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_create_invalid");
                    responseEntity.setException("The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_create_invalid", "The fields MsgId, FromUserName, ToUserName, Message are not allowed NULL!");
                } else {
                    ChatMessagesEntity dbResult = chatMessagesService.createMessage(request, viewModel, responseEntity);
                    if (dbResult != null) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(dbResult);
                        responseEntity.setMessage("chatmessages_create_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResult));
                    } else if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.CREATEFAIL.getValue());                 //Leave fail
                        responseEntity.setMessage("chatmessages_create_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_create_fail", "Send message to server fail, please try again!");
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * LIKE  /chatmessages/likebymember : like chatMessage item.
     *
     * @param viewModel the ActionMessageVM of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/likebymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> likeByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody ActionMessageVM viewModel) {
        log.debug("REST request to like ChatMessages by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_likebymember_invalid");
                    responseEntity.setException("The fields MsgId, ActionUserName are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_likebymember_invalid", "The fields MsgId, ActionUserName are not allowed NULL!");
                } else {
                    Long lngResult = chatMessagesService.likeByMember(request, viewModel.getMsgId(), viewModel.getActionUserName(), responseEntity);
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());              //Success
                        responseEntity.setData(lngResult);
                        responseEntity.setMessage("chatmessages_likebymember_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(lngResult));
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * LIKE  /chatmessages/dislikebymember : unlike chatMessage item.
     *
     * @param viewModel the viewModel of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatmessages/dislikebymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> dislikeByMember(HttpServletRequest request, HttpServletResponse response, @RequestBody ActionMessageVM viewModel) {
        log.debug("REST request to unlike ChatMessages by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getActionUserName())) {
                if (viewModel.getMsgId() == null || viewModel.getActionUserName() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatmessages_dislikebymember_invalid");
                    responseEntity.setException("The fields MsgId, ActionUserName are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_dislikebymember_invalid", "The fields MsgId, ActionUserName are not allowed NULL!");
                } else {
                    Long lngResult = chatMessagesService.dislikeByMember(request, viewModel.getMsgId(), viewModel.getActionUserName(), responseEntity);
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());              //Success
                        responseEntity.setData(lngResult);
                        responseEntity.setMessage("chatmessages_dislikebymember_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(lngResult));
                    } else {
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatmessages_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     *
     * @param receiverId: id của người nhận
     * @param lastRequestTime: thời gian gần nhất đã lấy (theo unix time)
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chatmessages/getlistbyreceiver/{receiverId}/{lastRequestTime}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessagesByReceiver(@PathVariable Long receiverId, @PathVariable Long lastRequestTime) {
        log.debug("REST request to get ChatMessages (getlistbyreceiver): {}/{}", receiverId, lastRequestTime);

        IsoResponseEntity<OutputMessageVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if (receiverId <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("chatmessages_getlistbyreceiver_invalid");
                responseEntity.setException("The field ReceiverId is not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_getlistbyreceiver_invalid", "The field ReceiverId is not allowed NULL!");
            } else {
                OutputMessageVM dbResults = chatMessagesService.findAllByReportDayAndReceiverId(lastRequestTime, receiverId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("chatmessages_getlistbyreceiver_successfull");

                String urlRequest = String.format("/chatmessages/getlistbyreceiver/%s/%s", receiverId, lastRequestTime);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     *
     * @param receiverId: id của người nhận
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chatmessages/getlistbyreceiver/{receiverId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessagesByReceiver(@PathVariable Long receiverId) {
        log.debug("REST request to get ChatMessages (getlistbyreceiver): {}", receiverId);

        IsoResponseEntity<OutputMessageVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if (receiverId <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("chatmessages_getlistbyreceiver_invalid");
                responseEntity.setException("The field ReceiverId is not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_getlistbyreceiver_invalid", "The field ReceiverId is not allowed NULL!");
            } else {
                OutputMessageVM dbResults = chatMessagesService.findAllByReceiverIdAndOrderByLastModifiedDesc(receiverId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("chatmessages_getlistbyreceiver_successfull");

                String urlRequest = String.format("/chatmessages/getlistbyreceiver/%s", receiverId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Lấy chi tiết tin nhắn thông qua id (02/07/2018).
     *
     * @param seqId: id của tin nhắn
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chatmessages/getinfobyseqid/{seqId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessagesInfo(@PathVariable String seqId) {
        log.debug("REST request to get ChatMessages (getinfobyseqid): {}", seqId);

        IsoResponseEntity<ChatMessagesEntity> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if (seqId.isEmpty()) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("chatmessages_getinfobyseqid_invalid");
                responseEntity.setException("The field SeqId is not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_getinfobyseqid_invalid", "The field SeqId is not allowed NULL!");
            } else {
                ChatMessagesEntity dbResults = chatMessagesService.findOne(seqId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("chatmessages_getinfobyseqid_successfull");

                String urlRequest = String.format("/chatmessages/getinfobyseqid/%s", seqId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatmessages_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatmessages_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
