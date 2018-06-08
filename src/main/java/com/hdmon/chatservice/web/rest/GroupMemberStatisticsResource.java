package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.GroupMemberStatisticsEntity;

import com.hdmon.chatservice.repository.GroupMemberStatisticsRepository;
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
public class GroupMemberStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(GroupMemberStatisticsResource.class);

    private static final String ENTITY_NAME = "groupMemberStatistics";

    private final GroupMemberStatisticsRepository groupMemberStatisticsRepository;

    public GroupMemberStatisticsResource(GroupMemberStatisticsRepository groupMemberStatisticsRepository) {
        this.groupMemberStatisticsRepository = groupMemberStatisticsRepository;
    }

    /**
     * POST  /group-member-statistics : Create a new groupMemberStatistics.
     *
     * @param groupMemberStatistics the groupMemberStatistics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupMemberStatistics, or with status 400 (Bad Request) if the groupMemberStatistics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-member-statistics")
    @Timed
    public ResponseEntity<GroupMemberStatisticsEntity> createGroupMemberStatistics(@RequestBody GroupMemberStatisticsEntity groupMemberStatistics) throws URISyntaxException {
        log.debug("REST request to save GroupMemberStatistics : {}", groupMemberStatistics);
        if (groupMemberStatistics.getId() != null) {
            throw new BadRequestAlertException("A new groupMemberStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMemberStatisticsEntity result = groupMemberStatisticsRepository.save(groupMemberStatistics);
        return ResponseEntity.created(new URI("/api/group-member-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-member-statistics : Updates an existing groupMemberStatistics.
     *
     * @param groupMemberStatistics the groupMemberStatistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupMemberStatistics,
     * or with status 400 (Bad Request) if the groupMemberStatistics is not valid,
     * or with status 500 (Internal Server Error) if the groupMemberStatistics couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-member-statistics")
    @Timed
    public ResponseEntity<GroupMemberStatisticsEntity> updateGroupMemberStatistics(@RequestBody GroupMemberStatisticsEntity groupMemberStatistics) throws URISyntaxException {
        log.debug("REST request to update GroupMemberStatistics : {}", groupMemberStatistics);
        if (groupMemberStatistics.getId() == null) {
            return createGroupMemberStatistics(groupMemberStatistics);
        }
        GroupMemberStatisticsEntity result = groupMemberStatisticsRepository.save(groupMemberStatistics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupMemberStatistics.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-member-statistics : get all the groupMemberStatistics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupMemberStatistics in body
     */
    @GetMapping("/group-member-statistics")
    @Timed
    public ResponseEntity<List<GroupMemberStatisticsEntity>> getAllGroupMemberStatistics(Pageable pageable) {
        log.debug("REST request to get a page of GroupMemberStatistics");
        Page<GroupMemberStatisticsEntity> page = groupMemberStatisticsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-member-statistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-member-statistics/:id : get the "id" groupMemberStatistics.
     *
     * @param id the id of the groupMemberStatistics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupMemberStatistics, or with status 404 (Not Found)
     */
    @GetMapping("/group-member-statistics/{id}")
    @Timed
    public ResponseEntity<GroupMemberStatisticsEntity> getGroupMemberStatistics(@PathVariable String id) {
        log.debug("REST request to get GroupMemberStatistics : {}", id);
        GroupMemberStatisticsEntity groupMemberStatistics = groupMemberStatisticsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupMemberStatistics));
    }

    /**
     * DELETE  /group-member-statistics/:id : delete the "id" groupMemberStatistics.
     *
     * @param id the id of the groupMemberStatistics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-member-statistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMemberStatistics(@PathVariable String id) {
        log.debug("REST request to delete GroupMemberStatistics : {}", id);
        groupMemberStatisticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
