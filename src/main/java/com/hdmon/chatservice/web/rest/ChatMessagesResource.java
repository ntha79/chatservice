package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ChatMessagesEntity;

import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.service.ChatMessagesService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;

import com.hdmon.chatservice.web.rest.vm.ChatMessagesVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

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
    @PostMapping("/chat-messages")
    @Timed
    public ResponseEntity<ChatMessagesEntity> createChatMessages(@RequestBody ChatMessagesEntity chatMessages) throws URISyntaxException {
        log.debug("REST request to save ChatMessages : {}", chatMessages);
        if (chatMessages.getId() != null) {
            throw new BadRequestAlertException("A new chatMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatMessagesEntity result = chatMessagesService.save(chatMessages);
        return ResponseEntity.created(new URI("/api/chat-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
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
    @PutMapping("/chat-messages")
    @Timed
    public ResponseEntity<ChatMessagesEntity> updateChatMessages(@RequestBody ChatMessagesEntity chatMessages) throws URISyntaxException {
        log.debug("REST request to update ChatMessages : {}", chatMessages);
        if (chatMessages.getId() == null) {
            return createChatMessages(chatMessages);
        }
        ChatMessagesEntity result = chatMessagesService.save(chatMessages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatMessages.getId()))
            .body(result);
    }

    /**
     * GET  /chat-messages : get all the chatMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatMessages in body
     */
    @GetMapping("/chat-messages")
    @Timed
    public ResponseEntity<List<ChatMessagesEntity>> getAllChatMessages(Pageable pageable) {
        log.debug("REST request to get a page of ChatMessages");
        Page<ChatMessagesEntity> page = chatMessagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chat-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chat-messages/:id : get the "id" chatMessages.
     *
     * @param id the id of the chatMessages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chat-messages/{id}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessages(@PathVariable String id) {
        log.debug("REST request to get ChatMessages : {}", id);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            ChatMessagesEntity dbResults = chatMessagesService.findOne(id);

            responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
            responseEntity.setData(dbResults);
            responseEntity.setMessage("successfull");

            httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, "/chat-messages");
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_info_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatMessages));
    }

    /**
     * DELETE  /chat-messages/:id : delete the "id" chatMessages.
     *
     * @param id the id of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chat-messages/{id}")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteChatMessages(@PathVariable String id) {
        log.debug("REST request to delete ChatMessages : {}", id);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            chatMessagesService.delete(id);

            responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
            responseEntity.setData(id);
            responseEntity.setMessage("successfull");

            httpHeaders = HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id);
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_delete_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
        //chatMessagesRepository.delete(id);
        //return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * DELETE  /chat-messages/deletebymember : update info for chatMessage item.
     *
     * @param chatMessagesVM the chatMessagesVM of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chat-messages/deletebymember")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteChatMessagesByMember(@RequestBody ChatMessagesVM chatMessagesVM) {
        log.debug("REST request to delete ChatMessages by member: {}", chatMessagesVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            Boolean blDelete = chatMessagesService.deleteInfoByMember(chatMessagesVM.getId(), chatMessagesVM.getMemberId(), responseEntity);
            if(blDelete) {
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(chatMessagesVM.getId());
                responseEntity.setMessage("successfull");

                httpHeaders = HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, chatMessagesVM.getId());
            }
            else
            {
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue())
                {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                         //UpdateFailt
                    responseEntity.setData(chatMessagesVM.getId());
                    responseEntity.setMessage("chat_messages_deletebymember_fail");

                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_update_error", "Delete message failed!");
                }
                else   {
                    httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, "chat_messages_not_found");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_deletebymember_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * EDIT  /chat-messages/editbyowner : update info for chatMessage item.
     *
     * @param chatMessagesVM the chatMessagesVM of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chat-messages/editbyowner")
    @Timed
    public ResponseEntity<IsoResponseEntity> editChatMessagesByOwner(@RequestBody ChatMessagesVM chatMessagesVM) {
        log.debug("REST request to edit ChatMessages by owner: {}", chatMessagesVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            Boolean blEdit = chatMessagesService.editInfoByOwner(chatMessagesVM.getId(), chatMessagesVM.getMemberId(), chatMessagesVM.getMessageValue(), chatMessagesVM.getMessageType(), responseEntity);
            if(blEdit) {
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(chatMessagesVM.getId());
                responseEntity.setMessage("successfull");

                httpHeaders = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatMessagesVM.getId());
            }
            else
            {
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue())
                {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                         //UpdateFailt
                    responseEntity.setData(chatMessagesVM.getId());
                    responseEntity.setMessage("chat_messages_editbyowner_fail");

                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_update_error", "Edit message failed!");
                }
                else   {
                    httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, "chat_messages_not_found");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_editbyowner_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Hàm gửi tin nhắn lên server để lưu vào Database
     *
     * @param chatMessagesVM: nội dung tin nhắn cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chat-messages/create")
    @Timed
    public ResponseEntity<IsoResponseEntity> createNewChatMessages(@RequestBody ChatMessagesVM chatMessagesVM) {
        log.debug("REST request to create ChatMessages: {}", chatMessagesVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            //Lấy dữ liệu đầu vào
            ChatMessagesEntity chatMessages = new ChatMessagesEntity();
            chatMessages.setMessageValue(chatMessagesVM.getMessageValue());
            chatMessages.setMessageType(chatMessagesVM.getMessageType());
            chatMessages.setReferMessageId(chatMessagesVM.getReferMessageId());
            chatMessages.setReceiverType(chatMessagesVM.getReceiverType());
            chatMessages.setSenderId(chatMessagesVM.getSenderId());
            chatMessages.setSenderLogin(chatMessagesVM.getSenderLogin());
            chatMessages.setReceiverLists(chatMessagesVM.getReceiverLists());

            ChatMessagesEntity dbResult = chatMessagesService.create(chatMessages, responseEntity);
            if(dbResult != null) {
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResult);
                responseEntity.setMessage("successfull");

                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResult.getId());
            }
            else
            {
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_create_invalid", "Invalid input, please check again!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_create_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     *
     * @param receiverId: id của người nhận
     * @param numberDay: số ngày cần lấy (ví dụ: 0 = today, 1=yesterday)
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chat-messages/getlistbyreceiver/{receiverId}/{numberDay}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessagesByReceiver(@PathVariable Long receiverId, @PathVariable int numberDay) {
        log.debug("REST request to get ChatMessages (getlistbyreceiver): {}/{}", receiverId, numberDay);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            List<ChatMessagesEntity> dbResults = chatMessagesService.findAllByReportDayAndReceiverId(numberDay, receiverId);

            responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
            responseEntity.setData(dbResults);
            responseEntity.setMessage("successfull");

            String urlRequest = String.format("/chat-messages/getlistbyreceiver/%s/%s", receiverId, numberDay);
            httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_getlistbyreceiver_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Lấy danh sách tin nhắn theo từng người nhận (05/06/2018).
     *
     * @param receiverId: id của người nhận
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessages, or with status 404 (Not Found)
     */
    @GetMapping("/chat-messages/getlistbyreceiver/{receiverId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatMessagesByReceiver(@PathVariable Long receiverId) {
        log.debug("REST request to get ChatMessages (getlistbyreceiver): {}", receiverId);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            List<ChatMessagesEntity> dbResults = chatMessagesService.findAllByReceiverIdAndOrderByLastModifiedDesc(receiverId);

            responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
            responseEntity.setData(dbResults);
            responseEntity.setMessage("successfull");

            String urlRequest = String.format("/chat-messages/getlistbyreceiver/%s", receiverId);
            httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chat_messages_getlistbyreceiver_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
