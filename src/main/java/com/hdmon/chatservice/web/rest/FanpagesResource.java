package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.Fanpages;

import com.hdmon.chatservice.repository.FanpagesRepository;
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
 * REST controller for managing Fanpages.
 */
@RestController
@RequestMapping("/api")
public class FanpagesResource {

    private final Logger log = LoggerFactory.getLogger(FanpagesResource.class);

    private static final String ENTITY_NAME = "fanpages";

    private final FanpagesRepository fanpagesRepository;

    public FanpagesResource(FanpagesRepository fanpagesRepository) {
        this.fanpagesRepository = fanpagesRepository;
    }

    /**
     * POST  /fanpages : Create a new fanpages.
     *
     * @param fanpages the fanpages to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fanpages, or with status 400 (Bad Request) if the fanpages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fanpages")
    @Timed
    public ResponseEntity<Fanpages> createFanpages(@RequestBody Fanpages fanpages) throws URISyntaxException {
        log.debug("REST request to save Fanpages : {}", fanpages);
        if (fanpages.getId() != null) {
            throw new BadRequestAlertException("A new fanpages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fanpages result = fanpagesRepository.save(fanpages);
        return ResponseEntity.created(new URI("/api/fanpages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fanpages : Updates an existing fanpages.
     *
     * @param fanpages the fanpages to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fanpages,
     * or with status 400 (Bad Request) if the fanpages is not valid,
     * or with status 500 (Internal Server Error) if the fanpages couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fanpages")
    @Timed
    public ResponseEntity<Fanpages> updateFanpages(@RequestBody Fanpages fanpages) throws URISyntaxException {
        log.debug("REST request to update Fanpages : {}", fanpages);
        if (fanpages.getId() == null) {
            return createFanpages(fanpages);
        }
        Fanpages result = fanpagesRepository.save(fanpages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fanpages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fanpages : get all the fanpages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fanpages in body
     */
    @GetMapping("/fanpages")
    @Timed
    public ResponseEntity<List<Fanpages>> getAllFanpages(Pageable pageable) {
        log.debug("REST request to get a page of Fanpages");
        Page<Fanpages> page = fanpagesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fanpages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fanpages/:id : get the "id" fanpages.
     *
     * @param id the id of the fanpages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fanpages, or with status 404 (Not Found)
     */
    @GetMapping("/fanpages/{id}")
    @Timed
    public ResponseEntity<Fanpages> getFanpages(@PathVariable String id) {
        log.debug("REST request to get Fanpages : {}", id);
        Fanpages fanpages = fanpagesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fanpages));
    }

    /**
     * DELETE  /fanpages/:id : delete the "id" fanpages.
     *
     * @param id the id of the fanpages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fanpages/{id}")
    @Timed
    public ResponseEntity<Void> deleteFanpages(@PathVariable String id) {
        log.debug("REST request to delete Fanpages : {}", id);
        fanpagesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
