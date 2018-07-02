package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ChatGroupStatisticsEntity;

import com.hdmon.chatservice.repository.ChatGroupStatisticsRepository;
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
 * REST controller for managing GroupMemberStatistics.
 */
@RestController
@RequestMapping("/api")
public class ChatGroupStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(ChatGroupStatisticsResource.class);

    private static final String ENTITY_NAME = "chatGroupStatistics";

    private final ChatGroupStatisticsRepository chatGroupStatisticsRepository;

    public ChatGroupStatisticsResource(ChatGroupStatisticsRepository chatGroupStatisticsRepository) {
        this.chatGroupStatisticsRepository = chatGroupStatisticsRepository;
    }

    /**
     * POST  /group-member-statistics : Create a new chatGroupStatistics.
     *
     * @param chatGroupStatistics the chatGroupStatistics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chatGroupStatistics, or with status 400 (Bad Request) if the chatGroupStatistics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroupstatistics")
    @Timed
    public ResponseEntity<ChatGroupStatisticsEntity> createGroupMemberStatistics(@RequestBody ChatGroupStatisticsEntity chatGroupStatistics) throws URISyntaxException {
        log.debug("REST request to save ChatGroupStatistics : {}", chatGroupStatistics);
        if (chatGroupStatistics.getSeqId() != null) {
            throw new BadRequestAlertException("A new chatGroupStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatGroupStatisticsEntity result = chatGroupStatisticsRepository.save(chatGroupStatistics);
        return ResponseEntity.created(new URI("/api/chatgroupstatistics/" + result.getSeqId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSeqId()))
            .body(result);
    }

    /**
     * PUT  /group-member-statistics : Updates an existing chatGroupStatistics.
     *
     * @param chatGroupStatistics the chatGroupStatistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chatGroupStatistics,
     * or with status 400 (Bad Request) if the chatGroupStatistics is not valid,
     * or with status 500 (Internal Server Error) if the chatGroupStatistics couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chatgroupstatistics")
    @Timed
    public ResponseEntity<ChatGroupStatisticsEntity> updateGroupMemberStatistics(@RequestBody ChatGroupStatisticsEntity chatGroupStatistics) throws URISyntaxException {
        log.debug("REST request to update ChatGroupStatistics : {}", chatGroupStatistics);
        if (chatGroupStatistics.getSeqId() == null) {
            return createGroupMemberStatistics(chatGroupStatistics);
        }
        ChatGroupStatisticsEntity result = chatGroupStatisticsRepository.save(chatGroupStatistics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chatGroupStatistics.getSeqId()))
            .body(result);
    }

    /**
     * GET  /group-member-statistics : get all the chatGroupStatistics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatGroupStatistics in body
     */
    @GetMapping("/chatgroupstatistics")
    @Timed
    public ResponseEntity<List<ChatGroupStatisticsEntity>> getAllGroupMemberStatistics(Pageable pageable) {
        log.debug("REST request to get a page of ChatGroupStatistics");
        Page<ChatGroupStatisticsEntity> page = chatGroupStatisticsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chatgroupstatistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-member-statistics/:id : get the "id" chatGroupStatistics.
     *
     * @param id the id of the groupMemberStatistics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatGroupStatistics, or with status 404 (Not Found)
     */
    @GetMapping("/chatgroupstatistics/{id}")
    @Timed
    public ResponseEntity<ChatGroupStatisticsEntity> getGroupMemberStatistics(@PathVariable String id) {
        log.debug("REST request to get ChatGroupStatistics : {}", id);
        ChatGroupStatisticsEntity chatGroupStatistics = chatGroupStatisticsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chatGroupStatistics));
    }

    /**
     * DELETE  /group-member-statistics/:id : delete the "id" chatGroupStatistics.
     *
     * @param id the id of the chatGroupStatistics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chatgroupstatistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMemberStatistics(@PathVariable String id) {
        log.debug("REST request to delete ChatGroupStatistics : {}", id);
        chatGroupStatisticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
