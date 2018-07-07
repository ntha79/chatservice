package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.service.ContactsService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.BusinessUtil;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.Contacts.*;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * POST  /contacts : Create a new contacts.
     *
     * @param friends the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<ContactsEntity> createContacts(@RequestBody ContactsEntity friends) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", friends);
        if (friends.getOwnerUserid() == null) {
            throw new BadRequestAlertException("A new contacts can not have an ID Null", ENTITY_NAME, "empty");
        }
        ContactsEntity result = contactsService.save(friends);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getOwnerUserid()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOwnerUserid().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contacts.
     *
     * @param friends the friends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contacts,
     * or with status 400 (Bad Request) if the contacts is not valid,
     * or with status 500 (Internal Server Error) if the contacts couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts")
    @Timed
    public ResponseEntity<ContactsEntity> updateFriends(@RequestBody ContactsEntity friends) throws URISyntaxException {
        log.debug("REST request to update Friends : {}", friends);
        /*
        if (friends.getId() == null) {
            return createFriends(contacts);
        }
        */
        ContactsEntity result = contactsService.save(friends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friends.getOwnerUserid().toString()))
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
    public ResponseEntity<ContactsEntity> getContacts(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteContacts(@PathVariable Long id) {
        log.debug("REST request to delete Contacts : {}", id);
        contactsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /*==================================================================================================================
    * ================CAC HAM BO SUNG===================================================================================
    * ================================================================================================================*/
    /**
     * Lấy thông tin bản ghi và danh sách bạn bè của User.
     *
     * @param ownerUsername: username của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts/getfriendlistbyowner/{ownerUsername}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getFriendlistByOwner(HttpServletRequest request, HttpServletResponse response, @PathVariable String ownerUsername) {
        log.debug("REST request to get info of Contacts : {}", ownerUsername);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(!ownerUsername.isEmpty()) {
                List<extFriendContactEntity> dbResults = contactsService.findOneByOwnerUsername(request, ownerUsername);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_getfriendlistbyowner_successfull");

                String urlRequest = String.format("/contacts/getfriendlistbyowner/%s", ownerUsername);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_getfriendlistbyowner_invalid");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_getfriendlistbyowner_invalid", "OwnerUsername cannot not null!");
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
     * Lấy thông tin bản ghi và danh sách bạn bè của User.
     *
     * @param ownerUsername: username của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts/getchatlistbyowner/{ownerUsername}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getChatlistByOwner(HttpServletRequest request, HttpServletResponse response, @PathVariable String ownerUsername) {
        log.debug("REST request to get info of Contacts : {}", ownerUsername);

        IsoResponseEntity<GetChatListVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(!ownerUsername.isEmpty()) {
                GetChatListVM dbResults = contactsService.findChatListByOwnerUsername(request, ownerUsername);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_getchatlistbyowner_successfull");

                String urlRequest = String.format("/contacts/getchatlistbyowner/%s", ownerUsername);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_getchatlistbyowner_invalid", "OwnerUsername cannot not null!");
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
     * Lấy thông tin bản ghi và danh sách bạn bè của User.
     *
     * @param ownerUsername: username của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts/getcontactlistbyowner/{ownerUsername}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getContactlistByOwner(HttpServletRequest request, HttpServletResponse response, @PathVariable String ownerUsername) {
        log.debug("REST request to get info of Contacts : {}", ownerUsername);

        IsoResponseEntity<GetContactListVM> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(!ownerUsername.isEmpty()) {
                GetContactListVM dbResults = contactsService.findContactListByOwnerUsername(request, ownerUsername);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("contacts_getcontactlistbyowner_successfull");

                String urlRequest = String.format("/contacts/getcontactlistbyowner/%s", ownerUsername);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("contacts_getcontactlistbyowner_invalid");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_getcontactlistbyowner_invalid", "OwnerUsername cannot not null!");
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
    public ResponseEntity<IsoResponseEntity> requireAddFriends(HttpServletRequest request, HttpServletResponse response, @RequestBody RequireAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to send add friend in Contacts : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_requireaddfriends_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_requireaddfriends_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.requireAddFriends(request, viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_requireaddfriends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Chấp nhận yêu cầu kết bạn
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/acceptaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> acceptAddFriends(HttpServletRequest request, HttpServletResponse response, @RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to accept friend in Contacts : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_acceptaddfriends_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_acceptaddfriends_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.acceptAddFriends(request, viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_acceptaddfriends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Từ chối yêu cầu kết bạn (remove người yêu cầu khỏi danh sách luôn)
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/deniedaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> deniedAddFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to denied friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_deniedaddfriends_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.deniedAddFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_deniedaddfriends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Block bạn bè (bạn bè không thể chat, cũng như có các action trên feeds).
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/blockfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> blockFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to block friend in Contacts : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_blockfriends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_blockfriends_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.blockFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_blockfriends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Xóa bạn bè (danh sách người xóa sẽ không còn và danh sách người nhận sẽ ở trạng thái Unknow).
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/deletefriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to delete friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_deletefriends_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_deletefriends_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.deleteFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_deletefriends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Cập nhật thông tin của bạn bè
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/updateinfo")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateFriendInfo(@RequestBody UpdateContactVM viewModel) throws URISyntaxException {
        log.debug("REST request to update friend in Contacts : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null || viewModel.getOwnerUsername().isEmpty()) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_updateinfo_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_updateinfo_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.updateFriendInfo(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_updateinfo_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
     * Cập nhật thông tin của bạn bè
     *
     * @param viewModel: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 200 (OK) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts/addcontact")
    @Timed
    public ResponseEntity<IsoResponseEntity> addNewContact(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdateContactVM viewModel) throws URISyntaxException {
        log.debug("REST request to add a contact in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null || viewModel.getOwnerUsername().isEmpty()) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("contacts_addcontact_invalid");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_addcontact_invalid", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = contactsService.addContact(request, viewModel, responseEntity);
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        int memberCount = dbResults.size();
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("contacts_addcontact_successfull");
                    } else {
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(0));
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("contacts_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_denied", "You are not authorized to perform this action!");
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
