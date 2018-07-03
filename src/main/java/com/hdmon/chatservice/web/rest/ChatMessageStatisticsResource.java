package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ChatMessageStatisticsEntity;

import com.hdmon.chatservice.repository.ChatMessageStatisticsRepository;
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
 * REST controller for managing ChatMessageStatistics.
 */
@RestController
@RequestMapping("/api")
public class ChatMessageStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(ChatMessageStatisticsResource.class);

    private static final String ENTITY_NAME = "chatMessageStatistics";

    private final ChatMessageStatisticsRepository chatMessageStatisticsRepository;

    public ChatMessageStatisticsResource(ChatMessageStatisticsRepository chatMessageStatisticsRepository) {
        this.chatMessageStatisticsRepository = chatMessageStatisticsRepository;
    }

    /**
     * POST  /chat-message-statistics : Create a new chatMessageStatistics.
     *
     * @param chatMessageStatistics the chatMessageStatistics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chatMessageStatistics, or with status 400 (Bad Request) if the chatMessageStatistics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chat-message-statistics")
    @Timed
    public ResponseEntity<ChatMessageStatisticsEntity> createChatMessageStatistics(@RequestBody ChatMessageStatisticsEntity chatMessageStatistics) throws URISyntaxException {
        log.debug("REST request to save ChatMessageStatistics : {}", chatMessageStatistics);
        if (chatMessageStatistics.getSeqId() != null) {
            throw new BadRequestAlertException("A new chatMessageStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatMessageStatisticsEntity result = chatMessageStatisticsRepository.save(chatMessageStatistics);
        return ResponseEntity.created(new URI("/api/chat-message-statistics/" + result.getSeqId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSeqId()))
            .body(result);
    }

    /**
     * PUT  /chat-message-statistics : Updates an existing chatMessageStatistics.
     *
     * @param chatMessageStatistics the chatMessageStatistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chatMessageStatistics,
     * or with status 400 (Bad Request) if the chatMessageStatistics is not valid,
     * or with status 500 (Internal Server Error) if the chatMessageStatistics couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chat-message-statistics")
    @Timed
    public ResponseEntity<ChatMessageStatisticsEntity> updateChatMessageStatistics(@RequestBody ChatMessageStatisticsEntity chatMessageStatistics) throws URISyntaxException {
        log.debug("REST request to update ChatMessageStatistics : {}", chatMessageStatistics);
        if (chatMessageStatistics.getSeqId() == null) {
            return createChatMessageStatistics(chatMessageStatistics);
        }
        ChatMessageStatisticsEntity result = chatMessageStatisticsRepository.save(chatMessageStatistics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatMessageStatistics.getSeqId()))
            .body(result);
    }

    /**
     * GET  /chat-message-statistics : get all the chatMessageStatistics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatMessageStatistics in body
     */
    @GetMapping("/chat-message-statistics")
    @Timed
    public ResponseEntity<List<ChatMessageStatisticsEntity>> getAllChatMessageStatistics(Pageable pageable) {
        log.debug("REST request to get a page of ChatMessageStatistics");
        Page<ChatMessageStatisticsEntity> page = chatMessageStatisticsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chat-message-statistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chat-message-statistics/:id : get the "id" chatMessageStatistics.
     *
     * @param id the id of the chatMessageStatistics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatMessageStatistics, or with status 404 (Not Found)
     */
    @GetMapping("/chat-message-statistics/{id}")
    @Timed
    public ResponseEntity<ChatMessageStatisticsEntity> getChatMessageStatistics(@PathVariable String id) {
        log.debug("REST request to get ChatMessageStatistics : {}", id);
        ChatMessageStatisticsEntity chatMessageStatistics = chatMessageStatisticsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatMessageStatistics));
    }

    /**
     * DELETE  /chat-message-statistics/:id : delete the "id" chatMessageStatistics.
     *
     * @param id the id of the chatMessageStatistics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chat-message-statistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteChatMessageStatistics(@PathVariable String id) {
        log.debug("REST request to delete ChatMessageStatistics : {}", id);
        chatMessageStatisticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
