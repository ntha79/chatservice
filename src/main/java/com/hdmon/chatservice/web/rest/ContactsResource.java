package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.service.ContactsService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.Contacts.RequireAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Contacts.ResponseAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Contacts.UpdateFriendVM;
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

    private final ContactsService contactsService;

    public ContactsResource(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    /**
     * POST  /Contacts : Create a new contacts.
     *
     * @param contacts the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<ContactsEntity> createContacts(@RequestBody ContactsEntity contacts) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", contacts);
        if (contacts.getId() != null) {
            throw new BadRequestAlertException("A new contacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactsEntity result = contactsService.save(contacts);
        return ResponseEntity.created(new URI("/api/Contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
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
    public ResponseEntity<ContactsEntity> updateContacts(@RequestBody ContactsEntity contacts) throws URISyntaxException {
        log.debug("REST request to update Contacts : {}", contacts);
        if (contacts.getId() == null) {
            return createContacts(contacts);
        }
        ContactsEntity result = contactsService.save(contacts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contacts.getId()))
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
    public ResponseEntity<List<ContactsEntity>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get a page of Contacts");
        Page<ContactsEntity> page = contactsService.findAll(pageable);
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
    public ResponseEntity<ContactsEntity> getContacts(@PathVariable String id) {
        log.debug("REST request to get Contacts : {}", id);
        ContactsEntity contacts = contactsService.findOne(id);
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
        contactsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*=========================================================
    * ================CAC HAM BO SUNG==========================
    * =========================================================*/
    /**
     * Lấy thông tin bản ghi và danh sách bạn bè của User.
     *
     * @param ownerId: id của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts/getinfobyownerid/{ownerId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getInfoContactsByOwnerId(@PathVariable Long ownerId) {
        log.debug("REST request to get info of Contacts : {}", ownerId);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(ownerId > 0) {
                ContactsEntity dbResults = contactsService.findOneByownerId(ownerId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_successfull");

                String urlRequest = String.format("/contacts/getinfobyownerid/%s", ownerId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "OwnerId cannot not null!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("contacts_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Gửi yêu cầu kết bạn (nếu người gửi chưa có contact thì tạo mới)
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/requireaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> requireAddFriends(@RequestBody RequireAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to send add friend in Contacts : {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (viewModel.getOwnerId() == null || viewModel.getFriendId() == null ) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_invalid_data");
                responseEntity.setException("The fields OwnerId, FriendId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "The fields OwnerId, FriendId are not allowed NULL!");
            }
            else {
                List<extFriendMemberEntity> dbResults = contactsService.requireAddFriends(viewModel, responseEntity);
                int memberCount = dbResults.size();
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
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
            responseEntity.setMessage("contacts_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Chấp nhận yêu cầu kết bạn (nếu người chấp nhận chưa có contact thì tạo mới)
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/acceptaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> acceptAddFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to accept friend in Contacts : {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (viewModel.getOwnerId() == null || viewModel.getFriendId() == null ) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_invalid_data");
                responseEntity.setException("The fields OwnerId, FriendId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "The fields OwnerId, FriendId are not allowed NULL!");
            }
            else {
                List<extFriendMemberEntity> dbResults = contactsService.acceptAddFriends(viewModel);
                int memberCount = dbResults.size();
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_successfull");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("Contacts_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Từ chối yêu cầu kết bạn (nếu người chấp nhận chưa có contact thì tạo mới)
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/deniedaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> deniedAddFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to denied friend in Contacts : {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (viewModel.getOwnerId() == null || viewModel.getFriendId() == null ) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_invalid_data");
                responseEntity.setException("The fields OwnerId, FriendId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "The fields OwnerId, FriendId are not allowed NULL!");
            }
            else {
                List<extFriendMemberEntity> dbResults = contactsService.deniedAddFriends(viewModel);
                int memberCount = dbResults.size();
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_successfull");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("contacts_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Từ chối yêu cầu kết bạn (nếu người chấp nhận chưa có contact thì tạo mới)
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/updatefriend")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateFriend(@RequestBody UpdateFriendVM viewModel) throws URISyntaxException {
        log.debug("REST request to update friend in Contacts : {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (viewModel.getOwnerId() == null || viewModel.getFriendId() == null || viewModel.getFriendName().isEmpty()) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_invalid_data");
                responseEntity.setException("The fields OwnerId, FriendId, FriendName are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "The fields OwnerId, FriendId, FriendName are not allowed NULL!");
            }
            else {
                List<extFriendMemberEntity> dbResults = contactsService.updateFriend(viewModel);
                int memberCount = dbResults.size();
                httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_successfull");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("contacts_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
