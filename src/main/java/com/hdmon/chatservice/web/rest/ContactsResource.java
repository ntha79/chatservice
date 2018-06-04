package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.Contacts;

import com.hdmon.chatservice.repository.ContactsRepository;
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
 * REST controller for managing Contacts.
 */
@RestController
@RequestMapping("/api")
public class ContactsResource {

    private final Logger log = LoggerFactory.getLogger(ContactsResource.class);

    private static final String ENTITY_NAME = "contacts";

    private final ContactsRepository contactsRepository;

    public ContactsResource(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    /**
     * POST  /contacts : Create a new contacts.
     *
     * @param contacts the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<Contacts> createContacts(@RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", contacts);
        if (contacts.getId() != null) {
            throw new BadRequestAlertException("A new contacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contacts result = contactsRepository.save(contacts);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contacts.
     *
     * @param contacts the contacts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contacts,
     * or with status 400 (Bad Request) if the contacts is not valid,
     * or with status 500 (Internal Server Error) if the contacts couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts")
    @Timed
    public ResponseEntity<Contacts> updateContacts(@RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to update Contacts : {}", contacts);
        if (contacts.getId() == null) {
            return createContacts(contacts);
        }
        Contacts result = contactsRepository.save(contacts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contacts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts")
    @Timed
    public ResponseEntity<List<Contacts>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get a page of Contacts");
        Page<Contacts> page = contactsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contacts/:id : get the "id" contacts.
     *
     * @param id the id of the contacts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contacts, or with status 404 (Not Found)
     */
    @GetMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Contacts> getContacts(@PathVariable String id) {
        log.debug("REST request to get Contacts : {}", id);
        Contacts contacts = contactsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contacts));
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contacts.
     *
     * @param id the id of the contacts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContacts(@PathVariable String id) {
        log.debug("REST request to delete Contacts : {}", id);
        contactsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
