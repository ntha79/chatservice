package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.PushNotificationsEntity;

import com.hdmon.chatservice.repository.PushNotificationsRepository;
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
 * REST controller for managing PushNotifications.
 */
@RestController
@RequestMapping("/api")
public class PushNotificationsResource {

    private final Logger log = LoggerFactory.getLogger(PushNotificationsResource.class);

    private static final String ENTITY_NAME = "pushNotifications";

    private final PushNotificationsRepository pushNotificationsRepository;

    public PushNotificationsResource(PushNotificationsRepository pushNotificationsRepository) {
        this.pushNotificationsRepository = pushNotificationsRepository;
    }

    /**
     * POST  /push-notifications : Create a new pushNotifications.
     *
     * @param pushNotifications the pushNotifications to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pushNotifications, or with status 400 (Bad Request) if the pushNotifications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/push-notifications")
    @Timed
    public ResponseEntity<PushNotificationsEntity> createPushNotifications(@RequestBody PushNotificationsEntity pushNotifications) throws URISyntaxException {
        log.debug("REST request to save PushNotifications : {}", pushNotifications);
        if (pushNotifications.getId() != null) {
            throw new BadRequestAlertException("A new pushNotifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PushNotificationsEntity result = pushNotificationsRepository.save(pushNotifications);
        return ResponseEntity.created(new URI("/api/push-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /push-notifications : Updates an existing pushNotifications.
     *
     * @param pushNotifications the pushNotifications to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pushNotifications,
     * or with status 400 (Bad Request) if the pushNotifications is not valid,
     * or with status 500 (Internal Server Error) if the pushNotifications couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/push-notifications")
    @Timed
    public ResponseEntity<PushNotificationsEntity> updatePushNotifications(@RequestBody PushNotificationsEntity pushNotifications) throws URISyntaxException {
        log.debug("REST request to update PushNotifications : {}", pushNotifications);
        if (pushNotifications.getId() == null) {
            return createPushNotifications(pushNotifications);
        }
        PushNotificationsEntity result = pushNotificationsRepository.save(pushNotifications);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pushNotifications.getId()))
            .body(result);
    }

    /**
     * GET  /push-notifications : get all the pushNotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pushNotifications in body
     */
    @GetMapping("/push-notifications")
    @Timed
    public ResponseEntity<List<PushNotificationsEntity>> getAllPushNotifications(Pageable pageable) {
        log.debug("REST request to get a page of PushNotifications");
        Page<PushNotificationsEntity> page = pushNotificationsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/push-notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /push-notifications/:id : get the "id" pushNotifications.
     *
     * @param id the id of the pushNotifications to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pushNotifications, or with status 404 (Not Found)
     */
    @GetMapping("/push-notifications/{id}")
    @Timed
    public ResponseEntity<PushNotificationsEntity> getPushNotifications(@PathVariable String id) {
        log.debug("REST request to get PushNotifications : {}", id);
        PushNotificationsEntity pushNotifications = pushNotificationsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pushNotifications));
    }

    /**
     * DELETE  /push-notifications/:id : delete the "id" pushNotifications.
     *
     * @param id the id of the pushNotifications to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/push-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deletePushNotifications(@PathVariable String id) {
        log.debug("REST request to delete PushNotifications : {}", id);
        pushNotificationsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
