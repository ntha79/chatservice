package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.DeviceContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.service.DeviceContactsService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.ContactsVM;
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
public class DeviceContactsResource {

    private final Logger log = LoggerFactory.getLogger(DeviceContactsResource.class);

    private static final String ENTITY_NAME = "device_contacts";

    //private final ContactsRepository contactsRepository;
    private final DeviceContactsService contactsService;

    public DeviceContactsResource(DeviceContactsService contactsService) {
        this.contactsService = contactsService;
    }

    /**
     * POST  /contacts : Create a new contacts.
     *
     * @param contacts the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devicecontacts")
    @Timed
    public ResponseEntity<DeviceContactsEntity> createContacts(@RequestBody DeviceContactsEntity contacts) throws URISyntaxException {
        log.debug("REST request to save DeviceContacts : {}", contacts);
        if (contacts.getId() != null) {
            throw new BadRequestAlertException("A new device contacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceContactsEntity result = contactsService.save(contacts);
        return ResponseEntity.created(new URI("/api/devicecontacts/" + result.getId()))
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
    @PutMapping("/devicecontacts")
    @Timed
    public ResponseEntity<DeviceContactsEntity> updateContacts(@RequestBody DeviceContactsEntity contacts) throws URISyntaxException {
        log.debug("REST request to update DeviceContacts : {}", contacts);
        if (contacts.getId() == null) {
            return createContacts(contacts);
        }
        DeviceContactsEntity result = contactsService.save(contacts);
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
    @GetMapping("/devicecontacts")
    @Timed
    public ResponseEntity<List<DeviceContactsEntity>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get a page of DeviceContacts");
        Page<DeviceContactsEntity> page = contactsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devicecontacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contacts/:id : get the "id" contacts.
     *
     * @param id the id of the contacts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contacts, or with status 404 (Not Found)
     */
    @GetMapping("/devicecontacts/{id}")
    @Timed
    public ResponseEntity<DeviceContactsEntity> getContacts(@PathVariable String id) {
        log.debug("REST request to get DeviceContacts : {}", id);
        DeviceContactsEntity contacts = contactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contacts));
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contacts.
     *
     * @param id the id of the contacts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devicecontacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContacts(@PathVariable String id) {
        log.debug("REST request to delete DeviceContacts : {}", id);
        contactsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*=========================================================
    * ================CAC HAM BO SUNG==========================
    * =========================================================*/
    /**
     * Lấy thông tin bản ghi và danh sách liên hệ của User.
     *
     * @param ownerId: id của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/devicecontacts/getinfobyownerid/{ownerId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getInfoFriendsByOwnerId(@PathVariable Long ownerId) {
        log.debug("REST request to get info of DeviceContacts : {}", ownerId);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(ownerId > 0) {
                DeviceContactsEntity dbResults = contactsService.findOneByownerId(ownerId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("successfull");

                String urlRequest = String.format("/devicecontacts/getinfobyownerid/%s", ownerId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_getinfobyownerid_error", "OwnerId cannot not null!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_getinfobyownerid_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Bổ sung liên hệ vào danh sách đang có của User
     *
     * @param contactsVM: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devicecontacts/append")
    @Timed
    public ResponseEntity<IsoResponseEntity> appendFriendsForOwnerId(@RequestBody ContactsVM contactsVM) {
        log.debug("REST request to save DeviceContacts : {}", contactsVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (contactsVM.getMobile() == null && contactsVM.getEmail() == null) {
                //throw new BadRequestAlertException("FriendId cannot not null", ENTITY_NAME, "required");
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("Mobile and email cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_append_error", "Mobile and email cannot not null!");
            }
            else {
                DeviceContactsEntity dbResults = contactsService.appendContactsForOwnerId(contactsVM, responseEntity);
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResults.getId().toString());
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("successfull");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_append_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Cập nhật liên hệ trong danh sách đang có của User
     *
     * @param contactsVM: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devicecontacts/update")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateFriendsForOwnerId(@RequestBody ContactsVM contactsVM) {
        log.debug("REST request to save DeviceContacts : {}", contactsVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (contactsVM.getMobile() == null && contactsVM.getEmail() == null) {
                //throw new BadRequestAlertException("FriendId cannot not null", ENTITY_NAME, "required");
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("Mobile and email cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_update_error", "Mobile and email cannot not null!");
            }
            else {
                DeviceContactsEntity dbResults = contactsService.updateContactsForOwnerId(contactsVM, responseEntity);
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResults.getId().toString());
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("successfull");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "devicecontacts_update_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
