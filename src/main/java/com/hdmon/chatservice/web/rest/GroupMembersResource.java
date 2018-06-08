package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.FanpagesEntity;
import com.hdmon.chatservice.domain.GroupMembersEntity;

import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.repository.GroupMembersRepository;
import com.hdmon.chatservice.service.GroupMembersService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.ChatMessagesVM;
import com.hdmon.chatservice.web.rest.vm.FanpagesVM;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GroupMembers.
 */
@RestController
@RequestMapping("/api")
public class GroupMembersResource {

    private final Logger log = LoggerFactory.getLogger(GroupMembersResource.class);

    private static final String ENTITY_NAME = "groupMembers";

    //private final GroupMembersRepository groupMembersRepository;
    private final GroupMembersService groupMembersService;

    public GroupMembersResource(GroupMembersService groupMembersService) {
        this.groupMembersService = groupMembersService;
    }

    /**
     * POST  /group-members : Create a new groupMembers.
     *
     * @param groupMembers the groupMembers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupMembers, or with status 400 (Bad Request) if the groupMembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-members")
    @Timed
    public ResponseEntity<GroupMembersEntity> createGroupMembers(@RequestBody GroupMembersEntity groupMembers) throws URISyntaxException {
        log.debug("REST request to save GroupMembers : {}", groupMembers);
        if (groupMembers.getId() != null) {
            throw new BadRequestAlertException("A new groupMembers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMembersEntity result = groupMembersService.save(groupMembers);
        return ResponseEntity.created(new URI("/api/group-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-members : Updates an existing groupMembers.
     *
     * @param groupMembers the groupMembers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupMembers,
     * or with status 400 (Bad Request) if the groupMembers is not valid,
     * or with status 500 (Internal Server Error) if the groupMembers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-members")
    @Timed
    public ResponseEntity<GroupMembersEntity> updateGroupMembers(@RequestBody GroupMembersEntity groupMembers) throws URISyntaxException {
        log.debug("REST request to update GroupMembers : {}", groupMembers);
        if (groupMembers.getId() == null) {
            return createGroupMembers(groupMembers);
        }
        GroupMembersEntity result = groupMembersService.save(groupMembers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupMembers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-members : get all the groupMembers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupMembers in body
     */
    @GetMapping("/group-members")
    @Timed
    public ResponseEntity<List<GroupMembersEntity>> getAllGroupMembers(Pageable pageable) {
        log.debug("REST request to get a page of GroupMembers");
        Page<GroupMembersEntity> page = groupMembersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-members/:id : get the "id" groupMembers.
     *
     * @param id the id of the groupMembers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupMembers, or with status 404 (Not Found)
     */
    @GetMapping("/group-members/{id}")
    @Timed
    public ResponseEntity<GroupMembersEntity> getGroupMembers(@PathVariable String id) {
        log.debug("REST request to get GroupMembers : {}", id);
        GroupMembersEntity groupMembers = groupMembersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupMembers));
    }

    /**
     * DELETE  /group-members/:id : delete the "id" groupMembers.
     *
     * @param id the id of the groupMembers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groupmembers/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupMembers(@PathVariable String id) {
        log.debug("REST request to delete GroupMembers : {}", id);
        groupMembersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*=========================================================
    * ================CAC HAM BO SUNG==========================
    * =========================================================*/
    /**
     * POST  /groupmembers/create : Create a new groupmembers.
     *
     * @param viewModel the groupmembers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupmembers, or with status 400 (Bad Request) if the groupmembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupmembers/create")
    @Timed
    public ResponseEntity<IsoResponseEntity> createNewGroupMembers(@RequestBody GroupMembersVM viewModel) {
        log.debug("REST request to create GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getOwnerId() == 0 || viewModel.getGroupName().isEmpty() || viewModel.getGroupType() == null) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("createnew_invalid");
                responseEntity.setException("The fields OwnerId, GroupName, GroupType are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_createnew_invalid", "The fields OwnerId, GroupName, GroupType are not allowed NULL!");
            }
            else {
                //Lấy dữ liệu đầu vào
                GroupMembersEntity groupMembers = new GroupMembersEntity();
                groupMembers.setGroupType(viewModel.getGroupType());
                groupMembers.setGroupName(viewModel.getGroupName());
                groupMembers.setGroupIcon(viewModel.getGroupIcon());
                groupMembers.setGroupSlogan(viewModel.getGroupSlogan());
                groupMembers.setGroupAbout(viewModel.getGroupAbout());
                groupMembers.setOwnerId(viewModel.getOwnerId());
                groupMembers.setOwnerLogin(viewModel.getOwnerLogin());

                GroupMembersEntity dbResult = groupMembersService.create(groupMembers, responseEntity);
                if (dbResult != null && dbResult.getId() != null) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResult.getId());
                } else {
                    responseEntity.setError(ResponseErrorCode.CREATEFAIL.getValue());                 //Create fail
                    responseEntity.setMessage("createnew_fail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_createnew_fail", "Create group fail, please try again!");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Cập nhật thông tin cơ bản của nhóm.
     *
     * @param viewModel: thông tin groupmembers cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/groupmembers/update")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateGroupMembers(@RequestBody GroupMembersVM viewModel) {
        log.debug("REST request to update GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getId() == null || viewModel.getGroupName().isEmpty() || viewModel.getGroupType() == null) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("update_invalid");
                responseEntity.setException("The fields Id, Name, Type are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_update_invalid", "The fields Id, Name, Type are not allowed NULL!");
            }
            else {
                GroupMembersEntity dbResult = groupMembersService.update(viewModel, responseEntity);
                if (dbResult != null && dbResult.getId() != null) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dbResult.getId());
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                 //Update fail
                    responseEntity.setMessage("update_invalid");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_update_invalid", "Update groupmembers fail, please try again!");
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
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /groupmembers/delete : update info for groupmembers item.
     *
     * @param viewModel the groupmembers of the groupmembers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/groupmembers/delete")
    @Timed
    public ResponseEntity<IsoResponseEntity> deleteGroupMembers(@RequestBody GroupMembersVM viewModel) {
        log.debug("REST request to delete GroupMembers by member: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            Boolean blDelete = groupMembersService.delete(viewModel, responseEntity);
            if(blDelete) {
                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(viewModel.getId());
                responseEntity.setMessage("successfull");

                httpHeaders = HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, viewModel.getId());
            }
            else
            {
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue())
                {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                         //UpdateFailt
                    responseEntity.setData(viewModel.getId());
                    responseEntity.setMessage("delete_fail");

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
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /groupmembers/appendmembers : Append listmember to groupmembers.
     *
     * @param viewModel the info to create
     * @return the ResponseEntity with status 200 (OK) and with body the new groupmembers, or with status 400 (Bad Request) if the groupmembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupmembers/appendmembers")
    @Timed
    public ResponseEntity<IsoResponseEntity> appendMembersToGroup(@RequestBody GroupMembersVM viewModel) {
        log.debug("REST request to appendmembers into GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getId() == null || viewModel.getListMembers() == null || viewModel.getListMembers().size() <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("appendmembers_invalid");
                responseEntity.setException("The fields Id, Listmembers are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_appendmembers_invalid", "The fields Id, Listmembers are not allowed NULL!");
            }
            else {
                //Lấy dữ liệu đầu vào
                GroupMembersEntity groupMembers = new GroupMembersEntity();
                groupMembers.setGroupType(viewModel.getGroupType());
                groupMembers.setGroupName(viewModel.getGroupName());
                groupMembers.setGroupIcon(viewModel.getGroupIcon());
                groupMembers.setGroupSlogan(viewModel.getGroupSlogan());
                groupMembers.setGroupAbout(viewModel.getGroupAbout());
                groupMembers.setOwnerId(viewModel.getOwnerId());
                groupMembers.setOwnerLogin(viewModel.getOwnerLogin());

                List<extGroupMemberEntity> dbResults = groupMembersService.appendMembers(viewModel, responseEntity);
                if (dbResults != null && dbResults.size() > 0) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResults.size()));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Append fail
                    responseEntity.setMessage("appendmembers_fail");
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
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "appendmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /groupmembers/removemembers : Remove listmembers from groupmembers.
     *
     * @param viewModel the info to create
     * @return the ResponseEntity with status 200 (OK) and with body the new groupmembers, or with status 400 (Bad Request) if the groupmembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupmembers/removemembers")
    @Timed
    public ResponseEntity<IsoResponseEntity> removeMembersInGroup(@RequestBody GroupMembersVM viewModel) {
        log.debug("REST request to removemembers into GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getId() == null || viewModel.getOwnerId() == null || viewModel.getListMembers().size() <= 0) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("removemembers_invalid");
                responseEntity.setException("The fields Id, OwnerId, ListMembers are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_removemembers_invalid", "The fields Id, OwnerId, ListMembers are not allowed NULL!");
            }
            else {
                List<extGroupMemberEntity> dbResults = groupMembersService.removeMembers(viewModel, responseEntity);
                if (dbResults != null && dbResults.size() > 0) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResults);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResults.size()));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Append fail
                    responseEntity.setMessage("removemembers_fail");
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
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "appendmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /groupmembers/memberLeave : Leave a new groupmembers.
     *
     * @param viewModel the info to leave
     * @return the ResponseEntity with status 200 (OK) and with body the new groupmembers, or with status 400 (Bad Request) if the groupmembers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupmembers/memberLeave")
    @Timed
    public ResponseEntity<IsoResponseEntity> memberLeaveGroup(@RequestBody MembersVM viewModel) {
        log.debug("REST request to memberLeave on GroupMembers: {}", viewModel);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(viewModel.getSourceId() == null || viewModel.getMemberId() == null) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("memberLeave_invalid");
                responseEntity.setException("The fields SourceId, MemberId are not allowed NULL!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "groupmembers_memberLeave_invalid", "The fields SourceId, MemberId are not allowed NULL!");
            }
            else {
                boolean dbResult = groupMembersService.memberLeave(viewModel, responseEntity);
                if (dbResult) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, String.valueOf(dbResult));
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()){
                    responseEntity.setError(ResponseErrorCode.APPENDMEMBER.getValue());                 //Leave fail
                    responseEntity.setMessage("memberLeave_fail");
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
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "appendmembers_system_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}