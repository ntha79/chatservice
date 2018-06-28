package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.FriendsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.service.FriendsService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.BusinessUtil;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.Friends.RequireAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Friends.ResponseAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Friends.UpdateFriendVM;
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
public class FriendsResource {

    private final Logger log = LoggerFactory.getLogger(FriendsResource.class);

    private static final String ENTITY_NAME = "friends";

    private final FriendsService friendsService;

    public FriendsResource(FriendsService friendsService) {
        this.friendsService = friendsService;
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
    public ResponseEntity<FriendsEntity> createFriends(@RequestBody FriendsEntity friends) throws URISyntaxException {
        log.debug("REST request to save Friends : {}", friends);
        if (friends.getId() != null) {
            throw new BadRequestAlertException("A new friends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendsEntity result = friendsService.save(friends);
        return ResponseEntity.created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /friends : Updates an existing friends.
     *
     * @param friends the friends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friends,
     * or with status 400 (Bad Request) if the contacts is not valid,
     * or with status 500 (Internal Server Error) if the friends couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friends")
    @Timed
    public ResponseEntity<FriendsEntity> updateFriends(@RequestBody FriendsEntity friends) throws URISyntaxException {
        log.debug("REST request to update Friends : {}", friends);
        if (friends.getId() == null) {
            return createFriends(friends);
        }
        FriendsEntity result = friendsService.save(friends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friends.getId()))
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
    public ResponseEntity<List<FriendsEntity>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<FriendsEntity> page = friendsService.findAll(pageable);
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
    public ResponseEntity<FriendsEntity> getContacts(@PathVariable String id) {
        log.debug("REST request to get Friends : {}", id);
        FriendsEntity friends = friendsService.findOne(id);
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
    public ResponseEntity<Void> deleteContacts(@PathVariable String id) {
        log.debug("REST request to delete Friends : {}", id);
        friendsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*=========================================================
    * ================CAC HAM BO SUNG==========================
    * =========================================================*/
    /**
     * Lấy thông tin bản ghi và danh sách bạn bè của User.
     *
     * @param ownerUsername: id của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/friends/getfriendlistbyowner/{ownerUsername}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getFriendlistByOwner(@PathVariable String ownerUsername) {
        log.debug("REST request to get info of Friends : {}", ownerUsername);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(!ownerUsername.isEmpty()) {
                List<extFriendContactEntity> dbResults = friendsService.findOneByOwnerUsername(ownerUsername);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("friends_successfull");

                String urlRequest = String.format("/contacts/getfriendlistbyowner/%s", ownerUsername);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "OwnerUsername cannot not null!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
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
    @PostMapping("/friends/requireaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> requireAddFriends(@RequestBody RequireAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to send add friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("friends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = friendsService.requireAddFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("friends_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
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
    @PostMapping("/friends/acceptaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> acceptAddFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to accept friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("friends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "contacts_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = friendsService.acceptAddFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("friends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("friends_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
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
    @PostMapping("/friends/deniedaddfriends")
    @Timed
    public ResponseEntity<IsoResponseEntity> deniedAddFriends(@RequestBody ResponseAddFriendsVM viewModel) throws URISyntaxException {
        log.debug("REST request to denied friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("friends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = friendsService.deniedAddFriends(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("friends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("friends_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
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
    @PostMapping("/friends/updateinfo")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateFriendInfo(@RequestBody UpdateFriendVM viewModel) throws URISyntaxException {
        log.debug("REST request to update friend in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null || viewModel.getOwnerUsername().isEmpty()) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("friends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = friendsService.updateFriendInfo(viewModel, responseEntity);
                    int memberCount = dbResults.size();
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("friends_successfull");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("friends_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
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
    @PostMapping("/friends/addcontact")
    @Timed
    public ResponseEntity<IsoResponseEntity> addNewContact(HttpServletRequest request, HttpServletResponse response, @RequestBody UpdateFriendVM viewModel) throws URISyntaxException {
        log.debug("REST request to add a contact in Friends : {}", viewModel);

        IsoResponseEntity<List<extFriendContactEntity>> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername() == null || viewModel.getFriendUsername() == null || viewModel.getOwnerUsername().isEmpty()) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("friends_invalid_data");
                    responseEntity.setException("The fields OwnerUsername, FriendUsername are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_invalid_data", "The fields OwnerUsername, FriendUsername are not allowed NULL!");
                } else {
                    List<extFriendContactEntity> dbResults = friendsService.addContact(request, viewModel, responseEntity);
                    if (responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                        int memberCount = dbResults.size();
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(memberCount));
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                        responseEntity.setData(dbResults);
                        responseEntity.setMessage("friends_successfull");
                    } else {
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(0));
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("friends_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("friends_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "friends_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
