package com.hdmon.chatservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hdmon.chatservice.domain.FanpagesEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.service.FanpagesService;
import com.hdmon.chatservice.web.rest.errors.BadRequestAlertException;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.util.HeaderUtil;
import com.hdmon.chatservice.web.rest.util.PaginationUtil;
import com.hdmon.chatservice.web.rest.vm.FanpagesVM;
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
 * REST controller for managing Fanpages.
 */
@RestController
@RequestMapping("/api")
public class FanpagesResource {

    private final Logger log = LoggerFactory.getLogger(FanpagesResource.class);

    private static final String ENTITY_NAME = "fanpages";

    //private final FanpagesRepository fanpagesRepository;
    private final FanpagesService fanpagesService;

    public FanpagesResource(FanpagesService fanpagesService) {
        this.fanpagesService = fanpagesService;
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
    public ResponseEntity<FanpagesEntity> createFanpages(@RequestBody FanpagesEntity fanpages) throws URISyntaxException {
        log.debug("REST request to save Fanpages : {}", fanpages);
        if (fanpages.getId() != null) {
            throw new BadRequestAlertException("A new fanpages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FanpagesEntity result = fanpagesService.save(fanpages);
        return ResponseEntity.created(new URI("/api/fanpages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
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
    public ResponseEntity<FanpagesEntity> updateFanpages(@RequestBody FanpagesEntity fanpages) throws URISyntaxException {
        log.debug("REST request to update Fanpages : {}", fanpages);
        if (fanpages.getId() == null) {
            return createFanpages(fanpages);
        }
        FanpagesEntity result = fanpagesService.save(fanpages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fanpages.getId()))
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
    public ResponseEntity<List<FanpagesEntity>> getAllFanpages(Pageable pageable) {
        log.debug("REST request to get a page of Fanpages");
        Page<FanpagesEntity> page = fanpagesService.findAll(pageable);
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
    public ResponseEntity<FanpagesEntity> getFanpages(@PathVariable String id) {
        log.debug("REST request to get Fanpages : {}", id);
        FanpagesEntity fanpages = fanpagesService.findOne(id);
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
        fanpagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /*=========================================================
    * ================CAC HAM BO SUNG==========================
    * =========================================================*/
    /**
     * Lấy danh sách fanpage của User.
     *
     * @param ownerId: id của User cần lấy
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/fanpages/getlistbyownerid/{ownerId}")
    @Timed
    public ResponseEntity<IsoResponseEntity> getAllFanpagesByOwnerId(@PathVariable Long ownerId) {
        log.debug("REST request to get list Fanpages : {}", ownerId);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(ownerId > 0) {
                List<FanpagesEntity> dbResults = fanpagesService.findAllByownerId(ownerId);

                responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                responseEntity.setData(dbResults);
                responseEntity.setMessage("successfull");

                String urlRequest = String.format("/fanpages/getlistbyownerid/%s", ownerId);
                httpHeaders = HeaderUtil.createAlert(ENTITY_NAME, urlRequest);
            }
            else
            {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_getlistbyownerid_error", "OwnerId cannot not null!");
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_getlistbyownerid_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Hàm cho phép User thực hiện tạo fanpage.
     *
     * @param fanpagesVM: thông tin fanpage cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/fanpages/create")
    @Timed
    public ResponseEntity<IsoResponseEntity> createNewFanpages(@RequestBody FanpagesVM fanpagesVM) {
        log.debug("REST request to create Fanpages: {}", fanpagesVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(fanpagesVM.getOwnerId() == 0 || fanpagesVM.getFanName().isEmpty() || fanpagesVM.getFanAbout().isEmpty()) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId/FanName/FanAbout cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_createnewfanpages_error", "OwnerId/FanName/FanAbout cannot not null!");
            }
            else {
                //Lấy dữ liệu đầu vào
                FanpagesEntity fanpages = new FanpagesEntity();
                fanpages.setFanName(fanpagesVM.getFanName());
                fanpages.setFanUrl("");
                fanpages.setFanAbout(fanpagesVM.getFanAbout());
                fanpages.setFanIcon(fanpagesVM.getFanIcon());
                fanpages.setFanThumbnail(fanpagesVM.getFanThumbnail());
                fanpages.setOwnerId(fanpagesVM.getOwnerId());
                fanpages.setOwnerLogin(fanpagesVM.getOwnerLogin());

                FanpagesEntity dbResult = fanpagesService.create(fanpages, responseEntity);
                if (dbResult != null && dbResult.getId() != null) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityCreationAlert(ENTITY_NAME, dbResult.getId());
                } else {
                    responseEntity.setError(ResponseErrorCode.CREATEFAIL.getValue());                 //Create fail
                    responseEntity.setMessage("createfail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_create_invalid", "Create fanpage fail, please try again!");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_create_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Hàm cho phép User thực hiện cập nhật fanpage.
     *
     * @param fanpagesVM: thông tin fanpage cần gửi lên
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/fanpages/update")
    @Timed
    public ResponseEntity<IsoResponseEntity> updateFanpages(@RequestBody FanpagesVM fanpagesVM) {
        log.debug("REST request to update Fanpages: {}", fanpagesVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if(fanpagesVM.getOwnerId() == 0 || fanpagesVM.getFanName().isEmpty() || fanpagesVM.getFanAbout().isEmpty()) {
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("OwnerId/FanName/FanAbout cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_updatefanpages_error", "OwnerId/FanName/FanAbout cannot not null!");
            }
            else {
                FanpagesEntity dbResult = fanpagesService.update(fanpagesVM, responseEntity);
                if (dbResult != null && dbResult.getId() != null) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());                 //Success
                    responseEntity.setData(dbResult);
                    responseEntity.setMessage("successfull");
                    httpHeaders = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dbResult.getId());
                } else if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.UPDATEFAIL.getValue());                 //Update fail
                    responseEntity.setMessage("updatefail");
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_update_invalid", "Update fanpage fail, please try again!");
                }
                else
                {
                    httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_update_notfound", "The record is not found, please check again!");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_update_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }

    /**
     * Bổ sung thành viên vào fanpages
     *
     * @param membersVM: json chứa thông tin gửi lên
     * @return the ResponseEntity with status 201 (Created) and with body the new friends, or with status 400 (Bad Request) if the friends has already an ID
     */
    @PostMapping("/fanpages/appendmember")
    @Timed
    public ResponseEntity<IsoResponseEntity> appendMember(@RequestBody MembersVM membersVM) {
        log.debug("REST request to save Fanpages : {}", membersVM);

        IsoResponseEntity responseEntity = new IsoResponseEntity();
        HttpHeaders httpHeaders;

        try {
            if (membersVM.getMemberId() == null) {
                //throw new BadRequestAlertException("FriendId cannot not null", ENTITY_NAME, "required");
                responseEntity.setError(ResponseErrorCode.INVALIDDATA.getValue());
                responseEntity.setMessage("invalid_data");
                responseEntity.setException("FriendId cannot not null!");
                httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_append_error", "MemberId cannot not null!");
            }
            else {
                FanpagesEntity dbResults = fanpagesService.appendMember(membersVM.getSourceId(), membersVM.getMemberId(), membersVM.getMemberLogin(), responseEntity);
                httpHeaders = HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dbResults.getId());
                if(responseEntity.getError() == ResponseErrorCode.UNKNOW_ERROR.getValue()) {
                    responseEntity.setError(ResponseErrorCode.SUCCESSFULL.getValue());
                    responseEntity.setData(dbResults.getMemberList());
                    responseEntity.setMessage("successfull");
                }
            }
        }
        catch (Exception ex)
        {
            responseEntity.setError(ResponseErrorCode.SYSTEM_ERROR.getValue());
            responseEntity.setMessage("system_error");
            responseEntity.setException(ex.getMessage());

            httpHeaders = HeaderUtil.createFailureAlert(ENTITY_NAME, "fanpages_append_error", ex.getMessage());
        }

        return new ResponseEntity<>(responseEntity, httpHeaders, HttpStatus.OK);
    }
}
