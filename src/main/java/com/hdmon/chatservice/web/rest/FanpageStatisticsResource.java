package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.FanpageStatistics;

import com.hdmon.chatservice.repository.FanpageStatisticsRepository;
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
 * REST controller for managing FanpageStatistics.
 */
@RestController
@RequestMapping("/api")
public class FanpageStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(FanpageStatisticsResource.class);

    private static final String ENTITY_NAME = "fanpageStatistics";

    private final FanpageStatisticsRepository fanpageStatisticsRepository;

    public FanpageStatisticsResource(FanpageStatisticsRepository fanpageStatisticsRepository) {
        this.fanpageStatisticsRepository = fanpageStatisticsRepository;
    }

    /**
     * POST  /fanpage-statistics : Create a new fanpageStatistics.
     *
     * @param fanpageStatistics the fanpageStatistics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fanpageStatistics, or with status 400 (Bad Request) if the fanpageStatistics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fanpage-statistics")
    @Timed
    public ResponseEntity<FanpageStatistics> createFanpageStatistics(@RequestBody FanpageStatistics fanpageStatistics) throws URISyntaxException {
        log.debug("REST request to save FanpageStatistics : {}", fanpageStatistics);
        if (fanpageStatistics.getId() != null) {
            throw new BadRequestAlertException("A new fanpageStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FanpageStatistics result = fanpageStatisticsRepository.save(fanpageStatistics);
        return ResponseEntity.created(new URI("/api/fanpage-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fanpage-statistics : Updates an existing fanpageStatistics.
     *
     * @param fanpageStatistics the fanpageStatistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fanpageStatistics,
     * or with status 400 (Bad Request) if the fanpageStatistics is not valid,
     * or with status 500 (Internal Server Error) if the fanpageStatistics couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fanpage-statistics")
    @Timed
    public ResponseEntity<FanpageStatistics> updateFanpageStatistics(@RequestBody FanpageStatistics fanpageStatistics) throws URISyntaxException {
        log.debug("REST request to update FanpageStatistics : {}", fanpageStatistics);
        if (fanpageStatistics.getId() == null) {
            return createFanpageStatistics(fanpageStatistics);
        }
        FanpageStatistics result = fanpageStatisticsRepository.save(fanpageStatistics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fanpageStatistics.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fanpage-statistics : get all the fanpageStatistics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fanpageStatistics in body
     */
    @GetMapping("/fanpage-statistics")
    @Timed
    public ResponseEntity<List<FanpageStatistics>> getAllFanpageStatistics(Pageable pageable) {
        log.debug("REST request to get a page of FanpageStatistics");
        Page<FanpageStatistics> page = fanpageStatisticsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fanpage-statistics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fanpage-statistics/:id : get the "id" fanpageStatistics.
     *
     * @param id the id of the fanpageStatistics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fanpageStatistics, or with status 404 (Not Found)
     */
    @GetMapping("/fanpage-statistics/{id}")
    @Timed
    public ResponseEntity<FanpageStatistics> getFanpageStatistics(@PathVariable String id) {
        log.debug("REST request to get FanpageStatistics : {}", id);
        FanpageStatistics fanpageStatistics = fanpageStatisticsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fanpageStatistics));
    }

    /**
     * DELETE  /fanpage-statistics/:id : delete the "id" fanpageStatistics.
     *
     * @param id the id of the fanpageStatistics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fanpage-statistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteFanpageStatistics(@PathVariable String id) {
        log.debug("REST request to delete FanpageStatistics : {}", id);
        fanpageStatisticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
