package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ChatMessages;

import com.hdmon.chatservice.repository.ChatMessagesRepository;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.Optional;

/**
 * REST controller for managing ChatMessages.
 */
@RestController
@RequestMapping("/api")
public class ChatMessagesResource {

    private final Logger log = LoggerFactory.getLogger(ChatMessagesResource.class);

    private static final String ENTITY_NAME = "chatMessages";

    private final ChatMessagesRepository chatMessagesRepository;

    public ChatMessagesResource(ChatMessagesRepository chatMessagesRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
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
    public ResponseEntity<ChatMessages> createChatMessages(@RequestBody ChatMessages chatMessages) throws URISyntaxException {
        log.debug("REST request to save ChatMessages : {}", chatMessages);
        if (chatMessages.getId() != null) {
            throw new BadRequestAlertException("A new chatMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatMessages result = chatMessagesRepository.save(chatMessages);
        return ResponseEntity.created(new URI("/api/chat-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
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
    public ResponseEntity<ChatMessages> updateChatMessages(@RequestBody ChatMessages chatMessages) throws URISyntaxException {
        log.debug("REST request to update ChatMessages : {}", chatMessages);
        if (chatMessages.getId() == null) {
            return createChatMessages(chatMessages);
        }
        ChatMessages result = chatMessagesRepository.save(chatMessages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatMessages.getId().toString()))
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
    public ResponseEntity<List<ChatMessages>> getAllChatMessages(Pageable pageable) {
        log.debug("REST request to get a page of ChatMessages");
        Page<ChatMessages> page = chatMessagesRepository.findAll(pageable);
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
    public ResponseEntity<ChatMessages> getChatMessages(@PathVariable String id) {
        log.debug("REST request to get ChatMessages : {}", id);
        ChatMessages chatMessages = chatMessagesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatMessages));
    }

    /**
     * DELETE  /chat-messages/:id : delete the "id" chatMessages.
     *
     * @param id the id of the chatMessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chat-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteChatMessages(@PathVariable String id) {
        log.debug("REST request to delete ChatMessages : {}", id);
        chatMessagesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
