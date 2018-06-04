package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.Friends;

import com.hdmon.chatservice.repository.FriendsRepository;
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
 * REST controller for managing Friends.
 */
@RestController
@RequestMapping("/api")
public class FriendsResource {

    private final Logger log = LoggerFactory.getLogger(FriendsResource.class);

    private static final String ENTITY_NAME = "friends";

    private final FriendsRepository friendsRepository;

    public FriendsResource(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    /**
     * POST  /friends : Create a new friends.
     *
     * @param friends the friends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/friends")
    @Timed
    public ResponseEntity<Friends> createFriends(@RequestBody Friends friends) throws URISyntaxException {
        log.debug("REST request to save Friends : {}", friends);
        if (friends.getId() != null) {
            throw new BadRequestAlertException("A new friends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Friends result = friendsRepository.save(friends);
        return ResponseEntity.created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /friends : Updates an existing friends.
     *
     * @param friends the friends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friends,
     * or with status 400 (Bad Request) if the friends is not valid,
     * or with status 500 (Internal Server Error) if the friends couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friends")
    @Timed
    public ResponseEntity<Friends> updateFriends(@RequestBody Friends friends) throws URISyntaxException {
        log.debug("REST request to update Friends : {}", friends);
        if (friends.getId() == null) {
            return createFriends(friends);
        }
        Friends result = friendsRepository.save(friends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friends.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friends : get all the friends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of friends in body
     */
    @GetMapping("/friends")
    @Timed
    public ResponseEntity<List<Friends>> getAllFriends(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<Friends> page = friendsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/friends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /friends/:id : get the "id" friends.
     *
     * @param id the id of the friends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the friends, or with status 404 (Not Found)
     */
    @GetMapping("/friends/{id}")
    @Timed
    public ResponseEntity<Friends> getFriends(@PathVariable String id) {
        log.debug("REST request to get Friends : {}", id);
        Friends friends = friendsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(friends));
    }

    /**
     * DELETE  /friends/:id : delete the "id" friends.
     *
     * @param id the id of the friends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/friends/{id}")
    @Timed
    public ResponseEntity<Void> deleteFriends(@PathVariable String id) {
        log.debug("REST request to delete Friends : {}", id);
        friendsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
