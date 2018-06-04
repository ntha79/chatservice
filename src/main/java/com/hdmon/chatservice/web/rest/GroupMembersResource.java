package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.GroupMembers;

import com.hdmon.chatservice.repository.GroupMembersRepository;
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
 * REST controller for managing GroupMembers.
 */
@RestController
@RequestMapping("/api")
public class GroupMembersResource {

    private final Logger log = LoggerFactory.getLogger(GroupMembersResource.class);

    private static final String ENTITY_NAME = "groupMembers";

    private final GroupMembersRepository groupMembersRepository;

    public GroupMembersResource(GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * POST  /group-members : Create a new groupMembers.
     *
     * @param groupMembers the groupMembers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupMembers, or with status 400 (Bad Request) if the groupMembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-members")
    @Timed
    public ResponseEntity<GroupMembers> createGroupMembers(@RequestBody GroupMembers groupMembers) throws URISyntaxException {
        log.debug("REST request to save GroupMembers : {}", groupMembers);
        if (groupMembers.getId() != null) {
            throw new BadRequestAlertException("A new groupMembers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMembers result = groupMembersRepository.save(groupMembers);
        return ResponseEntity.created(new URI("/api/group-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-members : Updates an existing groupMembers.
     *
     * @param groupMembers the groupMembers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupMembers,
     * or with status 400 (Bad Request) if the groupMembers is not valid,
     * or with status 500 (Internal Server Error) if the groupMembers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-members")
    @Timed
    public ResponseEntity<GroupMembers> updateGroupMembers(@RequestBody GroupMembers groupMembers) throws URISyntaxException {
        log.debug("REST request to update GroupMembers : {}", groupMembers);
        if (groupMembers.getId() == null) {
            return createGroupMembers(groupMembers);
        }
        GroupMembers result = groupMembersRepository.save(groupMembers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupMembers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-members : get all the groupMembers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupMembers in body
     */
    @GetMapping("/group-members")
    @Timed
    public ResponseEntity<List<GroupMembers>> getAllGroupMembers(Pageable pageable) {
        log.debug("REST request to get a page of GroupMembers");
        Page<GroupMembers> page = groupMembersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-members/:id : get the "id" groupMembers.
     *
     * @param id the id of the groupMembers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupMembers, or with status 404 (Not Found)
     */
    @GetMapping("/group-members/{id}")
    @Timed
    public ResponseEntity<GroupMembers> getGroupMembers(@PathVariable String id) {
        log.debug("REST request to get GroupMembers : {}", id);
        GroupMembers groupMembers = groupMembersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupMembers));
    }

    /**
     * DELETE  /group-members/:id : delete the "id" groupMembers.
     *
     * @param id the id of the groupMembers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-members/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMembers(@PathVariable String id) {
        log.debug("REST request to delete GroupMembers : {}", id);
        groupMembersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
