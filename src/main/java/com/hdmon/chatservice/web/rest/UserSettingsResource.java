package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.UserSettingsEntity;

import com.hdmon.chatservice.repository.UserSettingsRepository;
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
 * REST controller for managing UserSettings.
 */
@RestController
@RequestMapping("/api")
public class UserSettingsResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingsResource.class);

    private static final String ENTITY_NAME = "userSettings";

    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsResource(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    /**
     * POST  /user-settings : Create a new userSettings.
     *
     * @param userSettings the userSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSettings, or with status 400 (Bad Request) if the userSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettingsEntity> createUserSettings(@RequestBody UserSettingsEntity userSettings) throws URISyntaxException {
        log.debug("REST request to save UserSettings : {}", userSettings);
        if (userSettings.getId() != null) {
            throw new BadRequestAlertException("A new userSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSettingsEntity result = userSettingsRepository.save(userSettings);
        return ResponseEntity.created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /user-settings : Updates an existing userSettings.
     *
     * @param userSettings the userSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSettings,
     * or with status 400 (Bad Request) if the userSettings is not valid,
     * or with status 500 (Internal Server Error) if the userSettings couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettingsEntity> updateUserSettings(@RequestBody UserSettingsEntity userSettings) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}", userSettings);
        if (userSettings.getId() == null) {
            return createUserSettings(userSettings);
        }
        UserSettingsEntity result = userSettingsRepository.save(userSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userSettings.getId()))
            .body(result);
    }

    /**
     * GET  /user-settings : get all the userSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userSettings in body
     */
    @GetMapping("/user-settings")
    @Timed
    public ResponseEntity<List<UserSettingsEntity>> getAllUserSettings(Pageable pageable) {
        log.debug("REST request to get a page of UserSettings");
        Page<UserSettingsEntity> page = userSettingsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-settings/:id : get the "id" userSettings.
     *
     * @param id the id of the userSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSettings, or with status 404 (Not Found)
     */
    @GetMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<UserSettingsEntity> getUserSettings(@PathVariable String id) {
        log.debug("REST request to get UserSettings : {}", id);
        UserSettingsEntity userSettings = userSettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userSettings));
    }

    /**
     * DELETE  /user-settings/:id : delete the "id" userSettings.
     *
     * @param id the id of the userSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserSettings(@PathVariable String id) {
        log.debug("REST request to delete UserSettings : {}", id);
        userSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
