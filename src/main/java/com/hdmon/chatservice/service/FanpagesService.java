package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.FanpagesEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.GroupMemberRoleEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.repository.FanpageStatisticsRepository;
import com.hdmon.chatservice.repository.FanpagesRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.FanpagesVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by UserName on 6/6/2018.
 */
@Service
@Transactional
public class FanpagesService {
    private final Logger log = LoggerFactory.getLogger(FanpagesService.class);

    private final FanpagesRepository fanpagesRepository;
    private final FanpageStatisticsRepository fanpageStatisticsRepository;

    public FanpagesService(FanpagesRepository fanpagesRepository, FanpageStatisticsRepository fanpageStatisticsRepository) {
        this.fanpagesRepository = fanpagesRepository;
        this.fanpageStatisticsRepository = fanpageStatisticsRepository;
    }

    /**
     * Save a fanpages.
     *
     * @param fanpages the entity to save
     * @return the persisted entity
     */
    public FanpagesEntity save(FanpagesEntity fanpages) {
        log.debug("Request to save Fanpages : {}", fanpages);
        return fanpagesRepository.save(fanpages);
    }

    /**
     * Get all the fanpages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FanpagesEntity> findAll(Pageable pageable) {
        log.debug("Request to get all Fanpages");
        return fanpagesRepository.findAll(pageable);
    }

    /**
     * Get one fanpages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FanpagesEntity findOne(String id) {
        log.debug("Request to get Fanpages : {}", id);
        return fanpagesRepository.findOne(id);
    }

    /**
     * Delete the fanpages by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Fanpages : {}", id);
        fanpagesRepository.delete(id);
    }

    /**
     * Lấy thông tin liên hệ của User.
     *
     * Create Time: 2018-06-05
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<FanpagesEntity> findAllByownerId(Long ownerId)
    {
        log.debug("Request to get all Fanpages by ownerId: {}", ownerId);
        List<FanpagesEntity> dbList = new ArrayList<>();
        if(ownerId > 0)
        {
            dbList = fanpagesRepository.findAllByownerId(ownerId);
        }
        return dbList;
    }

    /**
     * User gửi lệnh tạo fanpage mới lên server (06/06/2018).
     * (Hàm bổ sung)
     * @param inputFanpages: entity chứa thông tin của fanpage
     * @param outResult: id của thành viên thực hiện xóa
     */
    public FanpagesEntity create(FanpagesEntity inputFanpages, IsoResponseEntity outResult)
    {
        //Bổ sung người tạo là thành viên
        List<extGroupMemberEntity> inputMemberLists = new ArrayList<>();
        extGroupMemberEntity memberItem = new extGroupMemberEntity();
        memberItem.setMemberId(inputFanpages.getOwnerId());
        memberItem.setMemberRole(GroupMemberRoleEnum.ADMIN);
        memberItem.setMemberStatus(GroupMemberStatusEnum.NORMAL);
        memberItem.setJoinTime(Calendar.getInstance().toInstant());
        inputMemberLists.add(memberItem);

        //Danh sách thành viên
        inputFanpages.setMemberList(inputMemberLists);
        inputFanpages.setMemberCount(inputMemberLists.size());

        //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
        inputFanpages.setLastModifiedBy(inputFanpages.getOwnerId().toString());
        inputFanpages.setLastModifiedDate(Calendar.getInstance().toInstant());
        inputFanpages.setLastModifiedBy(inputFanpages.getOwnerId().toString());
        inputFanpages.setLastModifiedDate(Calendar.getInstance().toInstant());

        //Dữ liệu thống kê
        FanpageStatisticsService fgStatisticsService = new FanpageStatisticsService(fanpageStatisticsRepository);
        fgStatisticsService.increaseStatistics();
        inputFanpages.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

        return  fanpagesRepository.save(inputFanpages);
    }

    /**
     * User gửi lệnh cập nhật fanpage mới lên server (06/06/2018).
     * (Hàm bổ sung)
     * @param inputFanpages: entity chứa thông tin của fanpage
     * @param outResult: id của thành viên thực hiện xóa
     */
    public FanpagesEntity update(FanpagesVM inputFanpages, IsoResponseEntity outResult)
    {
        //Kiểm tra từ DB
        FanpagesEntity dbInfo = fanpagesRepository.findOne(inputFanpages.getId());
        if(dbInfo != null && dbInfo.getId() != null) {
            //Tên fanpage
            if (!inputFanpages.getFanName().isEmpty())
                dbInfo.setFanName(inputFanpages.getFanName());
            //Giới thiệu
            if(!inputFanpages.getFanAbout().isEmpty())
                dbInfo.setFanAbout(inputFanpages.getFanAbout());
            //Đường dẫn
            if(!inputFanpages.getFanUrl().isEmpty())
                dbInfo.setFanUrl(inputFanpages.getFanUrl());
            //Ảnh đại diện
            if(!inputFanpages.getFanIcon().isEmpty())
                dbInfo.setFanIcon(inputFanpages.getFanIcon());
            //Ảnh thumbnail
            if(!inputFanpages.getFanThumbnail().isEmpty())
                dbInfo.setFanThumbnail(inputFanpages.getFanThumbnail());

            //Ngày cập nhật
            dbInfo.setLastModifiedUnixTime(new Date().getTime());

            //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
            dbInfo.setLastModifiedBy(inputFanpages.getOwnerId().toString());
            dbInfo.setLastModifiedDate(Calendar.getInstance().toInstant());
            dbInfo.setLastModifiedBy(inputFanpages.getOwnerId().toString());
            dbInfo.setLastModifiedDate(Calendar.getInstance().toInstant());

            return fanpagesRepository.save(dbInfo);
        }
        else
        {
            //Trả về lỗi không tìm thấy thông tin
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                     //notfound
            outResult.setMessage("fanpages_notfound_error");
            outResult.setException("The record is not found!");
            return  dbInfo;
        }
    }

    /**
     * User gửi lệnh cập nhật fanpage mới lên server (07/06/2018).
     * (Hàm bổ sung)
     * @param id: id của bản ghi tương ứng với fanpage
     * @param memberId: id của thành viên muốn thêm vào fanpage
     * @param memberLogin: tên đăng nhập của thành viên muốn thêm vào fanpage
     * @param outResult: id của thành viên thực hiện xóa
     */
    public FanpagesEntity appendMember(String id, Long memberId, String memberLogin, IsoResponseEntity outResult)
    {
        FanpagesEntity dbInfo = fanpagesRepository.findOne(id);
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extGroupMemberEntity> memberLists = dbInfo.getMemberList();
            for (extGroupMemberEntity memberItem : memberLists) {
                if(memberItem.getMemberId() == memberId)
                {
                    isExists = true;
                    break;
                }
            }

            //Trường hợp chưa tồn tại thì add vào danh sách
            if(!isExists) {
                extGroupMemberEntity newMember = new extGroupMemberEntity();
                newMember.setMemberId(memberId);
                newMember.setMemberStatus(GroupMemberStatusEnum.NORMAL);
                newMember.setMemberRole(GroupMemberRoleEnum.MEMBER);
                newMember.setJoinTime(Calendar.getInstance().toInstant());
                memberLists.add(newMember);

                dbInfo.setMemberList(memberLists);
                dbInfo.setMemberCount(memberLists.size());
                dbInfo.setLastModifiedUnixTime(new Date().getTime());

                return fanpagesRepository.save(dbInfo);
            }
            else
            {
                //Trả về lỗi không tìm thấy thông tin
                outResult.setError(ResponseErrorCode.EXISTS.getValue());                                     //notfound
                outResult.setMessage("fanpages_exists_error");
                outResult.setException("The member is exists in list!");
                return  null;
            }
        }
        else
        {
            //Trả về lỗi không tìm thấy thông tin
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                     //notfound
            outResult.setMessage("fanpages_notfound_error");
            outResult.setException("The record is not found!");
            return  null;
        }
    }
}
