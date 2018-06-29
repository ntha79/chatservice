package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.FanpagesEntity;
import com.hdmon.chatservice.domain.ChatGroupsEntity;

import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.repository.ChatGroupsRepository;
import com.hdmon.chatservice.service.ChatGroupsService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.BusinessUtil;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.ChatMessagesVM;
import com.hdmon.chatservice.web.rest.vm.FanpagesVM;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;
import com.hdmon.chatservice.web.rest.vm.Groups.*;
import com.hdmon.chatservice.web.rest.vm.MembersVM;
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
 * REST controller for managing GroupMembers.
 */
@RestController
@RequestMapping("/api")
public class ChatGroupsResource {

    private final Logger log = LoggerFactory.getLogger(ChatGroupsResource.class);

    private static final String ENTITY_NAME = "chatGroups";

    private final ChatGroupsService chatGroupsService;

    public ChatGroupsResource(ChatGroupsService chatGroupsService) {
        this.chatGroupsService = chatGroupsService;
    }

    /**
     * POST  /chatgroups : Create a new chatGroups.
     *
     * @param groupMembers the chatGroups to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chatGroups, or with status 400 (Bad Request) if the chatGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroups")
    @Timed
    public ResponseEntity<ChatGroupsEntity> createGroupMembers(@RequestBody ChatGroupsEntity groupMembers) throws URISyntaxException {
        log.debug("REST request to save ChatGroups : {}", groupMembers);
        if (groupMembers.getId() != null) {
            throw new BadRequestAlertException("A new chatGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatGroupsEntity result = chatGroupsService.save(groupMembers);
        return ResponseEntity.created(new URI("/api/chatgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /chatgroups : Updates an existing chatGroups.
     *
     * @param groupMembers the chatGroups to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chatGroups,
     * or with status 400 (Bad Request) if the chatGroups is not valid,
     * or with status 500 (Internal Server Error) if the chatGroups couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chatgroups")
    @Timed
    public ResponseEntity<ChatGroupsEntity> updateGroupMembers(@RequestBody ChatGroupsEntity groupMembers) throws URISyntaxException {
        log.debug("REST request to update ChatGroups : {}", groupMembers);
        if (groupMembers.getId() == null) {
            return createGroupMembers(groupMembers);
        }
        ChatGroupsEntity result = chatGroupsService.save(groupMembers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupMembers.getId()))
            .body(result);
    }

    /**
     * GET  /chatgroups : get all the chatGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chatGroups in body
     */
    @GetMapping("/chatgroups")
    @Timed
    public ResponseEntity<List<ChatGroupsEntity>> getAllGroupMembers(Pageable pageable) {
        log.debug("REST request to get a page of ChatGroups");
        Page<ChatGroupsEntity> page = chatGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chatgroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /chatgroups/:id : delete the "id" chatGroups.
     *
     * @param id the id of the chatGroups to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chatgroups/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMembers(@PathVariable String id) {
        log.debug("REST request to delete ChatGroups : {}", id);
        chatGroupsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*==================================================================================================================
    * ================CAC HAM BO SUNG===================================================================================
    * ================================================================================================================*/
    /**
     * POST  /chatgroups/create : Create a new chatGroups.
     *
     * @param viewModel the chatGroups to create
     * @return the ResponseEntity with status 200 (Created) and with body the new chatGroups, or with status 400 (Bad Request) if the chatGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroups/create")
    @Timed
    public ResponseEntity<IsoResponseEntity> createNewChatGroups(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateNewGroupVM viewModel) {
        log.debug("REST request to create ChatGroups: {}", viewModel);

        IsoResponseEntity<ChatGroupsEntity> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(BusinessUtil.checkAuthenticationValid(viewModel.getOwnerUsername())) {
                if (viewModel.getOwnerUsername().isEmpty() || viewModel.getGroupName().isEmpty() || viewModel.getGroupType() == null) {
                    responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                    responseEntity.setMessage("chatgroups_createnew_invalid");
                    responseEntity.setException("The fields OwnerUsername, GroupName, GroupType are not allowed NULL!");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_createnew_invalid", "The fields OwnerUsername, GroupName, GroupType are not allowed NULL!");
                } else {
                    ChatGroupsEntity dbResult = chatGroupsService.create(request, viewModel, responseEntity);
                    if (dbResult != null && dbResult.getId() != null) {
                        responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                        responseEntity.setData(dbResult);
                        responseEntity.setMessage("chatgroups_successfull");
                        httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResult.getId());
                    } else {
                        responseEntity.setError(ResponseErrorCode.CREATEFAIL.getValue());                 //Create fail
                        responseEntity.setMessage("chatgroups_createnew_fail");
                        httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_createnew_fail", "Create group fail, please try again!");
                    }
                }
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.DENIED.getValue());
                responseEntity.setMessage("chatgroups_denied");
                responseEntity.setException("You are not authorized to perform this action!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_denied", "You are not authorized to perform this action!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatgroups_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Cập nhật thông tin cơ bản của nhóm.
     *
     * @param viewModel: thông tin chatGroups cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/chatgroups/update")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateChatGroups(@RequestBody UpdateGroupVM viewModel) {
        log.debug("REST request to update ChatGroups: {}", viewModel);

        IsoResponseEntity<ChatGroupsEntity> responseEntity = new IsoResponseEntity<>();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getGroupId() == null || viewModel.getGroupName().isEmpty() || viewModel.getOwnerUsername() == null) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("chatgroups_update_invalid");
                responseEntity.setException("The fields GroupId, GroupName, OwnerUsername are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_update_invalid", "The fields GroupId, GroupName, OwnerUsername are not allowed NULL!");
            }
            else {
                ChatGroupsEntity dbResult = chatGroupsService.update(viewModel, responseEntity);
                if (dbResult != null && dbResult.getId() != null) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("chatgroups_update_successfull");
                    httpHeaders = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dbResult.getId());
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                 //Update fail
                    responseEntity.setMessage("chatgroups_update_fail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_update_fail", "Update groupmembers fail, please try again!");
                }
                else
                {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("chatgroups_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "chatgroups_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /groupmembers/delete : update info for chatGroups item.
     *
     * @param viewModel the chatGroups of the chatGroups to delete
     * @return the Id of group with status 200 (OK)
     */
    @PostMapping("/chatgroups/delete")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteGroupMembers(@RequestBody DeleteGroupVM viewModel) {
        log.debug("REST request to delete GroupMembers by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            Boolean blDelete = chatGroupsService.delete(viewModel, responseEntity);
            if(blDelete) {
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(viewModel.getGroupId());
                responseEntity.setMessage("groupmembers_successfull");

                httpHeaders = HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, viewModel.getGroupId());
            }
            else
            {
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue())
                {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                         //UpdateFailt
                    responseEntity.setData(viewModel.getGroupId());
                    responseEntity.setMessage("groupmembers_delete_fail");

                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_delete_fail", "Delete groupmembers failed!");
                }
                else   {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("groupmembers_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /chatgroups/appendmembers : Append listmember to chatGroups.
     *
     * @param viewModel the info to create
     * @return the ResponseEntity with status 200 (OK) and with body the new chatGroups, or with status 400 (Bad Request) if the chatGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroups/appendmembers")
    @Timed
    public ResponseEntity<IsoResponseEntity> appendMembersToGroup(@RequestBody ActionGroupVM viewModel) {
        log.debug("REST request to appendmembers into GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getGroupId() == null || viewModel.getListMembers() == null || viewModel.getListMembers().size() <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("groupmembers_appendmembers_invalid");
                responseEntity.setException("The fields GroupId, Listmembers are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_appendmembers_invalid", "The fields GroupId, Listmembers are not allowed NULL!");
            }
            else {
                List<extGroupMemberEntity> dbResults = chatGroupsService.appendMembers(viewModel, responseEntity);
                if (dbResults != null && dbResults.size() > 0) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("groupmembers_successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResults.size()));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Append fail
                    responseEntity.setMessage("groupmembers_appendmembers_fail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_appendmembers_fail", "Append members to this group fail, please try again!");
                }
                else
                {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("groupmembers_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /chatgroups/removemembers : Remove listmembers from chatGroups.
     *
     * @param viewModel the info to create
     * @return the ResponseEntity with status 200 (OK) and with body the new chatGroups, or with status 400 (Bad Request) if the chatGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroups/removemembers")
    @Timed
    public ResponseEntity<IsoResponseEntity> removeMembersInGroup(@RequestBody ActionGroupVM viewModel) {
        log.debug("REST request to removemembers in GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getGroupId() == null || viewModel.getOwnerId() == null || viewModel.getListMembers().size() <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("groupmembers_removemembers_invalid");
                responseEntity.setException("The fields GroupId, OwnerId, ListMembers are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_removemembers_invalid", "The fields GroupId, OwnerId, ListMembers are not allowed NULL!");
            }
            else {
                List<extGroupMemberEntity> dbResults = chatGroupsService.removeMembers(viewModel, responseEntity);
                if (dbResults != null && dbResults.size() > 0) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("groupmembers_successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResults.size()));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Append fail
                    responseEntity.setMessage("groupmembers_removemembers_fail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_removemembers_fail", "Remove members to this group fail, please try again!");
                }
                else
                {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("groupmembers_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "appendmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /chatgroups/memberLeave : Leave a new groupmembers.
     *
     * @param viewModel the info to leave
     * @return the ResponseEntity with status 200 (OK) and with body the new chatGroups, or with status 400 (Bad Request) if the chatGroups has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chatgroups/memberLeave")
    @Timed
    public ResponseEntity<IsoResponseEntity> memberLeaveGroup(@RequestBody MembersLeaveGroupVM viewModel) {
        log.debug("REST request to memberLeave on GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getGroupId() == null || viewModel.getMemberId() == null) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("groupmembers_memberLeave_invalid");
                responseEntity.setException("The fields GroupId, MemberId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_memberLeave_invalid", "The fields GroupId, MemberId are not allowed NULL!");
            }
            else {
                boolean dbResult = chatGroupsService.memberLeave(viewModel, responseEntity);
                if (dbResult) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("groupmembers_successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResult));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Leave fail
                    responseEntity.setMessage("groupmembers_memberLeave_fail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_memberLeave_fail", "Members leave this group fail, please try again!");
                }
                else
                {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, responseEntity.getMessage(), responseEntity.getException());
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("groupmembers_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "appendmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * GET  /chatgroups/getInfo/:id : get the "id" chatGroups.
     *
     * @param groupId the groupId of the chatGroups to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chatGroups, or with status 404 (Not Found)
     */
    @GetMapping("/chatgroups/getinfo/{groupId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getGroupMembersInfo(@PathVariable String groupId) {
        log.debug("REST request to get GroupMembers : {}", groupId);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(!groupId.isEmpty()) {
                ChatGroupsEntity dbResults = chatGroupsService.findOne(groupId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("groupmembers_successfull");

                String urlRequest = String.format("/groupmembers/getInfo/%s", groupId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("The fields GroupId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_invalid_data", "The fields GroupId are not allowed NULL!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("groupmembers_system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
