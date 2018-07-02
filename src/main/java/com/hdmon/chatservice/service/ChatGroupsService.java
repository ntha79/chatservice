package com.hdmon.chatservice.service;

import com.hdmon.chatservice.config.ApplicationProperties;
import com.hdmon.chatservice.domain.ChatGroupsEntity;
import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.GroupMemberRoleEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.enumeration.UserFindTypeEnum;
import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.repository.ChatGroupsRepository;
import com.hdmon.chatservice.repository.ContactsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.service.util.UserHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.Groups.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by UserName on 6/5/2018.
 */
@Service
@Transactional
public class ChatGroupsService {
    private final Logger log = LoggerFactory.getLogger(ChatGroupsService.class);

    private final ContactsRepository contactsRepository;
    private final ChatGroupsRepository chatGroupsRepository;
    private final ChatGroupStatisticsService chatGroupStatisticsService;
    private final ApplicationProperties applicationProperties;
    private String gatewayUrl;

    public ChatGroupsService(ChatGroupsRepository chatGroupsRepository, ChatGroupStatisticsService chatGroupStatisticsService, ContactsRepository contactsRepository, ApplicationProperties applicationProperties) {
        this.chatGroupsRepository = chatGroupsRepository;
        this.chatGroupStatisticsService = chatGroupStatisticsService;
        this.contactsRepository = contactsRepository;
        this.applicationProperties = applicationProperties;
        this.gatewayUrl = this.applicationProperties.getPortal().getGatewayUrl();
    }

    /**
     * Save a groupMembers.
     *
     * @param groupMembers the entity to save
     * @return the persisted entity
     */
    public ChatGroupsEntity save(ChatGroupsEntity groupMembers) {
        log.debug("Request to save ChatGroups : {}", groupMembers);
        return chatGroupsRepository.save(groupMembers);
    }

    /**
     * Get all the groupMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChatGroupsEntity> findAll(Pageable pageable) {
        log.debug("Request to get all ChatGroups");
        return chatGroupsRepository.findAll(pageable);
    }

    /**
     * Get one groupMembers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ChatGroupsEntity findOne(String id) {
        log.debug("Request to get ChatGroups : {}", id);
        return chatGroupsRepository.findOne(id);
    }

    /**
     * Delete the groupMembers by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ChatGroups : {}", id);
        chatGroupsRepository.delete(id);
    }

    /**
     * Thành viên thực hiện tạo group mới).
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time: 2018-06-29
     * @param viewModel: entity chứa thông tin cơ bản của group
     * @return the entity
     */
    public ChatGroupsEntity create(HttpServletRequest request, CreateNewGroupVM viewModel, IsoResponseEntity outputEntity)
    {
        ChatGroupsEntity dbSourceInfo = new ChatGroupsEntity();

        //Lấy thông tin tài khoản người tạo
        Long ownerUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, viewModel.getOwnerUsername(), "");
        if(ownerUserId > 0) {
            Integer maxMember = applicationProperties.getChatService().getChatgroupMaxMember();
            dbSourceInfo.setCreatedById(ownerUserId);
            dbSourceInfo.setCreatedBy(viewModel.getOwnerUsername());
            dbSourceInfo.setGroupId(viewModel.getGroupId());
            dbSourceInfo.setGroupType(viewModel.getGroupType());
            dbSourceInfo.setGroupName(viewModel.getGroupName());
            dbSourceInfo.setGroupIcon(viewModel.getGroupIcon());
            dbSourceInfo.setGroupBackground(viewModel.getGroupBackground());
            dbSourceInfo.setGroupSlogan(viewModel.getGroupSlogan());
            dbSourceInfo.setGroupSumary(viewModel.getGroupSumary());
            dbSourceInfo.setMaxMember(maxMember);

            //Bổ sung người tạo là thành viên
            List<extGroupMemberEntity> inputMemberLists = new ArrayList<>();
            extGroupMemberEntity memberItem = new extGroupMemberEntity();
            memberItem.setMemberId(ownerUserId);
            memberItem.setMemberUsername(viewModel.getOwnerUsername());
            memberItem.setMemberRole(GroupMemberRoleEnum.ADMIN);
            memberItem.setMemberStatus(GroupMemberStatusEnum.NORMAL);
            memberItem.setJoinTime(Calendar.getInstance().toInstant());
            memberItem.setActionNote("Người tạo nhóm");
            inputMemberLists.add(memberItem);

            //Danh sách thành viên
            dbSourceInfo.setMemberLists(inputMemberLists);

            //Số thành viên tối đa của nhóm bí mật (chưa chốt số cuối)
            if (dbSourceInfo.getGroupType() == GroupTypeEnum.SECRET) {
                maxMember = applicationProperties.getChatService().getChatgroupSecretMaxMember();
                dbSourceInfo.setMaxMember(maxMember);
            }

            //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
            dbSourceInfo.setLastModifiedBy(viewModel.getOwnerUsername());

            //Dữ liệu thống kê
            chatGroupStatisticsService.increaseStatistics();
            dbSourceInfo.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

            ChatGroupsEntity newGroupInfo = chatGroupsRepository.save(dbSourceInfo);

            //Thêm group mới vào danh sách cho người gửi yêu cầu
            addGroupIntoContactGroupList(ownerUserId, newGroupInfo.getGroupId(), newGroupInfo.getGroupName());

            return newGroupInfo;
        }
        else
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("chatgroups_create_notfound");
            outputEntity.setException("The user info is notfound!");
        }
        return dbSourceInfo;
    }

    /**
     * Thành viên cập nhật thông tin group.
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time: 2018-06-29
     * @param viewModel: entity chứa thông tin của group
     * @param outResult: entity của thông tin sẽ trả về cho client
     */
    public ChatGroupsEntity update(UpdateGroupVM viewModel, IsoResponseEntity outResult)
    {
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null)
        {
            boolean isExists = false;
            List<extGroupMemberEntity> memberLists = dbSourceInfo.getMemberLists();
            if(viewModel.getOwnerUsername() != dbSourceInfo.getCreatedBy()) {
                for (extGroupMemberEntity currentMbr : memberLists) {
                    if (currentMbr.getMemberUsername().equals(viewModel.getOwnerUsername())) {
                        isExists = true;
                        break;
                    }
                }
            }
            else
            {
                isExists = true;
            }

            //Trường hợp là thành viên của nhóm mới được quyền sửa
            if(isExists) {
                //Tên nhóm
                if (!viewModel.getGroupName().isEmpty())
                    dbSourceInfo.setGroupName(viewModel.getGroupName());
                //Ảnh đại diện
                if (!viewModel.getGroupIcon().isEmpty())
                    dbSourceInfo.setGroupIcon(viewModel.getGroupIcon());
                //Ảnh nền
                if (!viewModel.getGroupBackground().isEmpty())
                    dbSourceInfo.setGroupBackground(viewModel.getGroupBackground());
                //Câu khẩu hiệu
                if (!viewModel.getGroupSlogan().isEmpty())
                    dbSourceInfo.setGroupSlogan(viewModel.getGroupSlogan());
                //Giới thiệu nhóm
                if (!viewModel.getGroupSumary().isEmpty())
                    dbSourceInfo.setGroupSumary(viewModel.getGroupSumary());

                //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
                dbSourceInfo.setLastModifiedBy(viewModel.getOwnerUsername().toString());

                //Cập nhật tên group trong danh sách của từng thành viên
                for (extGroupMemberEntity currentMbr : memberLists) {
                    updateGroupIntoContactGroupList(currentMbr.getMemberId(), dbSourceInfo.getGroupId(), dbSourceInfo.getGroupName());
                }

                return chatGroupsRepository.save(dbSourceInfo);
            }
            else
            {
                outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //denied
                outResult.setMessage("chatgroups_update_rejected");
                outResult.setException("Request to update this group is rejected!");
                return null;
            }
        }
        else {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                 //notfound
            outResult.setMessage("chatgroups_notfound_error");
            outResult.setException("The group info is not found!");
            return null;
        }
    }

    /**
     * Admin thực hiện xóa group.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @param viewModel: entity chứa thông tin của group
     * @param outResult: entity của thông tin sẽ trả về cho client
     */
    public boolean delete(DeleteGroupVM viewModel, IsoResponseEntity outResult)
    {
        boolean blResult = false;
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null)
        {
            if(dbSourceInfo.getCreatedBy().equals(viewModel.getOwnerUsername())) {
                //Loại bỏ group cho toàn bộ danh sách thành viên của group
                List<extGroupMemberEntity> removeMemberLists = dbSourceInfo.getMemberLists();
                for (extGroupMemberEntity removeMember : removeMemberLists) {
                    removeGroupInContactGroupList(removeMember.getMemberId(), dbSourceInfo.getGroupId());
                }

                //Loại bỏ group
                chatGroupsRepository.delete(viewModel.getGroupId());
                blResult = true;
            }
            else
            {
                outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //denied
                outResult.setMessage("chatgroups_delete_rejected");
                outResult.setException("Request to delete this group is rejected!");
            }
        }
        else {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                      //notfound
            outResult.setMessage("chatgroups_delete_notfound");
            outResult.setException("The chatgroup info is not found!");
        }
        return blResult;
    }

    /**
     * Thêm thành viên vào group, có thể thêm 1 hoặc nhiều
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @return the list members
     */
    public List<extGroupMemberEntity> appendMembers(HttpServletRequest request, ActionGroupVM viewModel, IsoResponseEntity outResult)
    {
        List<extGroupMemberEntity> memberLists = new ArrayList<>();
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null) {
            memberLists = dbSourceInfo.getMemberLists();

            List<MembersActionGroupVM> toAppendLists = viewModel.getListMembers();
            int totalAllMember = memberLists.size() + toAppendLists.size();
            if(totalAllMember < dbSourceInfo.getMaxMember()) {
                for (MembersActionGroupVM appendItem : toAppendLists) {
                    boolean isExists = false;
                    for (extGroupMemberEntity currentMbr : memberLists) {
                        if (currentMbr.getMemberUsername().equals(appendItem.getMemberUsername())) {
                            isExists = true;
                            break;
                        }
                    }

                    //insert if it is not exists
                    if (!isExists) {
                        Long memberUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, UserFindTypeEnum.USERNAME, appendItem.getMemberUsername(), "");
                        if(memberUserId > 0) {
                            extGroupMemberEntity memberItem = new extGroupMemberEntity();
                            memberItem.setMemberId(memberUserId);
                            memberItem.setMemberUsername(appendItem.getMemberUsername());
                            memberItem.setMemberRole(GroupMemberRoleEnum.MEMBER);
                            memberItem.setMemberStatus(GroupMemberStatusEnum.INVITING);
                            memberItem.setJoinTime(Calendar.getInstance().toInstant());
                            memberItem.setActionNote("Được thêm bởi " + viewModel.getActionUsername());
                            memberLists.add(memberItem);

                            //Cập nhật bản ghi cho người gửi yêu cầu
                            addGroupIntoContactGroupList(memberUserId, dbSourceInfo.getGroupId(), dbSourceInfo.getGroupName());
                        }
                    }
                }

                //Lưu thông tin vào trong bảng GroupMembers
                dbSourceInfo.setMemberLists(memberLists);
                dbSourceInfo.setLastModifiedTime(new Date().getTime());
                chatGroupsRepository.save(dbSourceInfo);
            }
            else
            {
                outResult.setError(ResponseErrorCode.REJECTED.getValue());                                 //denied
                outResult.setMessage("chatgroups_appendmembers_rejected");
                outResult.setException("Request to append the list members is rejected!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                    //notfound
            outResult.setMessage("chatgroups_notfound_error");
            outResult.setException("The chatgroup info is not found!");
        }

        return  memberLists;
    }

    /**
     * Loại bỏ thành viên khỏi group (chỉ ADMIN hoặc MANAGER mới có quyền)
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @return the list members
     */
    public List<extGroupMemberEntity> removeMembers(ActionGroupVM viewModel, IsoResponseEntity outResult)
    {
        boolean allowRemove = false;
        List<extGroupMemberEntity> memberLists = new ArrayList<>();
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null) {
            memberLists = dbSourceInfo.getMemberLists();

            //Kiểm tra xem người thực hiện có quyền xóa không?
            if (viewModel.getActionUsername().equals(dbSourceInfo.getCreatedBy())) {
                allowRemove = true;
            }
            else {
                for (extGroupMemberEntity exists : memberLists) {
                    if (exists.getMemberUsername().equals(viewModel.getActionUsername()) && exists.getMemberRole() == GroupMemberRoleEnum.MANAGER) {
                        allowRemove = true;
                        break;
                    }
                }
            }

            //Nếu có quyền thì thực hiện loại bỏ từng thành viên
            if(allowRemove) {
                List<MembersActionGroupVM> removeMemberLists = viewModel.getListMembers();
                for (MembersActionGroupVM removeMember : removeMemberLists) {
                    Long memberId = 0L;
                    boolean isExists = false;
                    for (extGroupMemberEntity currentMbr : memberLists) {
                        if (!currentMbr.getMemberUsername().equals(viewModel.getActionUsername()) && currentMbr.getMemberUsername().equals(removeMember.getMemberUsername())) {
                            memberId = currentMbr.getMemberId();
                            memberLists.remove(currentMbr);
                            isExists = true;
                            break;
                        }
                    }

                    //Loại bỏ group trong danh sách group của User
                    if (isExists) {
                        removeGroupInContactGroupList(memberId, dbSourceInfo.getGroupId());
                    }
                }

                //Cập nhật lại danh sách thành viên của bảng GroupMembers
                dbSourceInfo.setMemberLists(memberLists);
                dbSourceInfo.setLastModifiedTime(new Date().getTime());
                chatGroupsRepository.save(dbSourceInfo);
            }
        }

        //Trường hợp không có quyền thực hiện thì báo lỗi
        if(!allowRemove)
        {
            outResult.setError(ResponseErrorCode.REJECTED.getValue());                                    //denied
            outResult.setMessage("chatgroups_removemembers_rejected");
            outResult.setException("Request to remove the list members is rejected!");
        }

        return  memberLists;
    }

    /**
     * Thành viên tự rời khỏi group
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @return the boolean
     */
    public boolean memberLeave(MembersLeaveGroupVM viewModel, IsoResponseEntity outResult)
    {
        boolean isExists = false;
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null) {
            List<extGroupMemberEntity> memberLists = dbSourceInfo.getMemberLists();
            if(memberLists != null && memberLists.size() > 0) {
                Long memberId = 0L;
                for (extGroupMemberEntity currentMbr : memberLists) {
                    if (currentMbr.getMemberUsername().equals(viewModel.getActionUsername())) {
                        memberId = currentMbr.getMemberId();
                        memberLists.remove(currentMbr);
                        isExists = true;
                        break;
                    }
                }

                //Cập nhật bản ghi cho người cần remove
                if (isExists) {
                    //Loại bỏ group trong danh sách group của User
                    removeGroupInContactGroupList(memberId, dbSourceInfo.getGroupId());

                    //Cập nhật lại danh sách thành viên của bảng GroupMembers
                    dbSourceInfo.setMemberLists(memberLists);
                    dbSourceInfo.setLastModifiedTime(new Date().getTime());
                    chatGroupsRepository.save(dbSourceInfo);

                    return true;
                }
            }
        }

        //Nếu không tìm thấy thì báo lỗi
        if(!isExists)
        {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());
            outResult.setMessage("chatgroups_notfound_error");
            outResult.setException("The chatgroup info is not found!");
        }
        return false;
    }

    /**
     * Đồng ý tham gia vào group được mời.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @return the entity
     */
    public List<extContactGroupEntity> acceptJoinChatGroups(HttpServletRequest request, MembersLeaveGroupVM viewModel, IsoResponseEntity outputEntity)
    {
        //Cập nhật cho người thao tác
        List<extContactGroupEntity> responseList = new ArrayList<>();
        boolean isExists = false;
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getGroupId() != null) {
            List<extGroupMemberEntity> memberLists = dbSourceInfo.getMemberLists();
            if(memberLists != null && memberLists.size() > 0) {
                Long memberId = 0L;
                for (extGroupMemberEntity currentMbr : memberLists) {
                    if (currentMbr.getMemberUsername().equals(viewModel.getActionUsername()) && currentMbr.getMemberStatus() == GroupMemberStatusEnum.INVITING) {
                        memberId = currentMbr.getMemberId();
                        currentMbr.setMemberStatus(GroupMemberStatusEnum.NORMAL);
                        isExists = true;
                        break;
                    }
                }

                //Cập nhật bản ghi cho người chấp nhận
                if (isExists) {
                    dbSourceInfo.setMemberLists(memberLists);
                    dbSourceInfo.setLastModifiedTime(new Date().getTime());
                    chatGroupsRepository.save(dbSourceInfo);

                    //Lấy danh sách group mới nhất của thành viên này
                    ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerUserid(memberId);
                    if (dbContactInfo != null && dbContactInfo.getOwnerUsername() != null) {
                        boolean existsInGroup = false;
                        List<extContactGroupEntity> existsGroupLists = dbContactInfo.getGroupLists();
                        if(existsGroupLists != null && existsGroupLists.size() > 0) {
                            for (extContactGroupEntity existsItem : existsGroupLists){
                                if(existsItem.getGroupId().equals(viewModel.getGroupId()))
                                {
                                    existsItem.setStatus(GroupMemberStatusEnum.NORMAL);
                                    existsInGroup = true;
                                    break;
                                }
                            }
                        }

                        //Nếu tồn tại thì thực hiện ghi nhận lại thông tin
                        if(existsInGroup) {
                            dbContactInfo.setGroupLists(existsGroupLists);
                            dbContactInfo.setLastModifiedTime(new Date().getTime());
                            contactsRepository.save(dbContactInfo);
                        }

                        responseList = existsGroupLists;
                    }
                }
            }
        }

        //Nếu không tìm thấy thì báo lỗi
        if(!isExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("chatgroups_notfound_error");
            outputEntity.setException("The chatgroup info is not found!");
        }

        return  responseList;
    }

    //*****************************************************************************
    //********************************CÁC HÀM NỘI BỘ*******************************

    /**
     * Loại bỏ group trong danh sách group của User
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-30
     * @return the action result
     */
    private boolean removeGroupInContactGroupList(Long memberId, String groupKeyId)
    {
        ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerUserid(memberId);
        if (dbContactInfo != null && dbContactInfo.getOwnerUsername() != null) {
            List<extContactGroupEntity> existGroupLists = dbContactInfo.getGroupLists();
            if (existGroupLists != null && existGroupLists.size() > 0) {
                for (extContactGroupEntity currentGrp : existGroupLists) {
                    if(currentGrp.getGroupId().equals(groupKeyId))
                    {
                        existGroupLists.remove(currentGrp);
                        break;
                    }
                }

                //Ghi nhận lại thông tin Group
                dbContactInfo.setGroupLists(existGroupLists);
                dbContactInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbContactInfo);
            }
        }
        return true;
    }

    /**
     * Thêm group vào danh sách group của User
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-29
     * @return the action result
     */
    private boolean addGroupIntoContactGroupList(Long ownerUserid, String groupKeyId, String groupName)
    {
        ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerUserid(ownerUserid);
        if (dbContactInfo != null && dbContactInfo.getOwnerUsername() != null) {
            boolean existsInGroup = false;
            List<extContactGroupEntity> existsGroupLists = dbContactInfo.getGroupLists();
            if (existsGroupLists == null) existsGroupLists = new ArrayList<>();
            if(existsGroupLists != null && existsGroupLists.size() > 0) {
                for (extContactGroupEntity existsItem : existsGroupLists){
                    if(existsItem.getGroupId().equals(groupKeyId))
                    {
                        existsInGroup = true;
                        break;
                    }
                }
            }

            //Nếu chưa có group trong danh sách thì thêm vào
            if(!existsInGroup) {
                extContactGroupEntity newGroup = new extContactGroupEntity();
                newGroup.setGroupId(groupKeyId);
                newGroup.setGroupName(groupName);
                newGroup.setStatus(GroupMemberStatusEnum.INVITING);
                existsGroupLists.add(newGroup);

                dbContactInfo.setGroupLists(existsGroupLists);
                dbContactInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbContactInfo);
            }
        }
        return true;
    }

    /**
     * Thêm group vào danh sách group của User
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time: 2018-06-29
     * @return the action result
     */
    private boolean updateGroupIntoContactGroupList(Long ownerUserid, String groupKeyId, String groupName)
    {
        ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerUserid(ownerUserid);
        if (dbContactInfo != null && dbContactInfo.getOwnerUsername() != null) {
            boolean existsInGroup = false;
            List<extContactGroupEntity> existsGroupLists = dbContactInfo.getGroupLists();
            if(existsGroupLists != null && existsGroupLists.size() > 0) {
                for (extContactGroupEntity existsItem : existsGroupLists){
                    if(existsItem.getGroupId().equals(groupKeyId))
                    {
                        existsItem.setGroupName(groupName);
                        existsInGroup = true;
                        break;
                    }
                }
            }

            //Nếu tồn tại thì thực hiện ghi nhận lại thông tin
            if(existsInGroup) {
                dbContactInfo.setGroupLists(existsGroupLists);
                dbContactInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbContactInfo);
            }
        }
        return true;
    }
}
