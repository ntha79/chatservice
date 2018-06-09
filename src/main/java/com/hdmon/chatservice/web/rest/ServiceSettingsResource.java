package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ServiceSettingsEntity;

import com.hdmon.chatservice.repository.ServiceSettingsRepository;
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
 * REST controller for managing ServiceSettings.
 */
@RestController
@RequestMapping("/api")
public class ServiceSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSettingsResource.class);

    private static final String ENTITY_NAME = "serviceSettings";

    private final ServiceSettingsRepository serviceSettingsRepository;

    public ServiceSettingsResource(ServiceSettingsRepository serviceSettingsRepository) {
        this.serviceSettingsRepository = serviceSettingsRepository;
    }

    /**
     * POST  /service-settings : Create a new serviceSettings.
     *
     * @param serviceSettings the serviceSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceSettings, or with status 400 (Bad Request) if the serviceSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-settings")
    @Timed
    public ResponseEntity<ServiceSettingsEntity> createServiceSettings(@RequestBody ServiceSettingsEntity serviceSettings) throws URISyntaxException {
        log.debug("REST request to save ServiceSettings : {}", serviceSettings);
        if (serviceSettings.getId() != null) {
            throw new BadRequestAlertException("A new serviceSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSettingsEntity result = serviceSettingsRepository.save(serviceSettings);
        return ResponseEntity.created(new URI("/api/service-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /service-settings : Updates an existing serviceSettings.
     *
     * @param serviceSettings the serviceSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceSettings,
     * or with status 400 (Bad Request) if the serviceSettings is not valid,
     * or with status 500 (Internal Server Error) if the serviceSettings couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-settings")
    @Timed
    public ResponseEntity<ServiceSettingsEntity> updateServiceSettings(@RequestBody ServiceSettingsEntity serviceSettings) throws URISyntaxException {
        log.debug("REST request to update ServiceSettings : {}", serviceSettings);
        if (serviceSettings.getId() == null) {
            return createServiceSettings(serviceSettings);
        }
        ServiceSettingsEntity result = serviceSettingsRepository.save(serviceSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceSettings.getId()))
            .body(result);
    }

    /**
     * GET  /service-settings : get all the serviceSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceSettings in body
     */
    @GetMapping("/service-settings")
    @Timed
    public ResponseEntity<List<ServiceSettingsEntity>> getAllServiceSettings(Pageable pageable) {
        log.debug("REST request to get a page of ServiceSettings");
        Page<ServiceSettingsEntity> page = serviceSettingsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-settings/:id : get the "id" serviceSettings.
     *
     * @param id the id of the serviceSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceSettings, or with status 404 (Not Found)
     */
    @GetMapping("/service-settings/{id}")
    @Timed
    public ResponseEntity<ServiceSettingsEntity> getServiceSettings(@PathVariable String id) {
        log.debug("REST request to get ServiceSettings : {}", id);
        ServiceSettingsEntity serviceSettings = serviceSettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceSettings));
    }

    /**
     * DELETE  /service-settings/:id : delete the "id" serviceSettings.
     *
     * @param id the id of the serviceSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceSettings(@PathVariable String id) {
        log.debug("REST request to delete ServiceSettings : {}", id);
        serviceSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
