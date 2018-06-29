package com.hdmon.chatservice.service;

import com.hdmon.chatservice.config.ApplicationProperties;
import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.ChatGroupsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.GroupMemberRoleEnum;
import com.hdmon.chatservice.domain.enumeration.GroupMemberStatusEnum;
import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.domain.enumeration.MessageReceiverStatusEnum;
import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extGroupMemberEntity;
import com.hdmon.chatservice.domain.extents.extMessageReceiverEntity;
import com.hdmon.chatservice.repository.ContactsRepository;
import com.hdmon.chatservice.repository.GroupMemberStatisticsRepository;
import com.hdmon.chatservice.repository.ChatGroupsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.service.util.UserHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;
import com.hdmon.chatservice.web.rest.vm.Groups.*;
import com.hdmon.chatservice.web.rest.vm.MembersVM;
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
    private final GroupMemberStatisticsRepository groupMemberStatisticsRepository;
    private final ApplicationProperties applicationProperties;
    private String gatewayUrl;

    public ChatGroupsService(ChatGroupsRepository chatGroupsRepository, GroupMemberStatisticsRepository groupMemberStatisticsRepository, ContactsRepository contactsRepository, ApplicationProperties applicationProperties) {
        this.chatGroupsRepository = chatGroupsRepository;
        this.groupMemberStatisticsRepository = groupMemberStatisticsRepository;
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
        log.debug("Request to save GroupMembers : {}", groupMembers);
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
        log.debug("Request to get all GroupMembers");
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
        log.debug("Request to get GroupMembers : {}", id);
        return chatGroupsRepository.findOne(id);
    }

    /**
     * Delete the groupMembers by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete GroupMembers : {}", id);
        chatGroupsRepository.delete(id);
    }

    /**
     * Create messagereceiverlists
     *
     * @param id the id of the entity
     * @return the entity
     */
    public List<extMessageReceiverEntity> createMessageReceiverLists(String id, ChatGroupsEntity dbInfoGroup)
    {
        List<extMessageReceiverEntity> responseList = new ArrayList<>();
        ChatGroupsEntity dbInfo = findOne(id);
        if(dbInfo != null && dbInfo.getId() != null)
        {
            List<extGroupMemberEntity> groupMemberLists = dbInfo.getMemberLists();
            if(groupMemberLists != null && groupMemberLists.size() > 0)
            {
                for (extGroupMemberEntity memberItem : groupMemberLists) {
                    extMessageReceiverEntity receiverItem = new extMessageReceiverEntity();
                    receiverItem.setReceiverId(memberItem.getMemberId());
                    receiverItem.setStatus(MessageReceiverStatusEnum.NEW);
                    receiverItem.setUpdateUnixTime(new Date().getTime());
                    responseList.add(receiverItem);
                }
            }

            dbInfoGroup.setGroupType(dbInfo.getGroupType());
        }
        return responseList;
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
        ChatGroupsEntity inputChatGroups = new ChatGroupsEntity();

        //Lấy thông tin tài khoản người tạo
        Long ownerUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 1, viewModel.getOwnerUsername(), "");
        if(ownerUserId > 0) {
            Integer maxMember = applicationProperties.getChatService().getChatgroupMaxMember();
            inputChatGroups.setCreatedById(ownerUserId);
            inputChatGroups.setCreatedBy(viewModel.getOwnerUsername());
            inputChatGroups.setId(viewModel.getGroupId());
            inputChatGroups.setGroupType(viewModel.getGroupType());
            inputChatGroups.setGroupName(viewModel.getGroupName());
            inputChatGroups.setGroupIcon(viewModel.getGroupIcon());
            inputChatGroups.setGroupBackground(viewModel.getGroupBackground());
            inputChatGroups.setGroupSlogan(viewModel.getGroupSlogan());
            inputChatGroups.setGroupSumary(viewModel.getGroupSumary());
            inputChatGroups.setMaxMember(maxMember);

            //Bổ sung người tạo là thành viên
            List<extGroupMemberEntity> inputMemberLists = new ArrayList<>();
            extGroupMemberEntity memberItem = new extGroupMemberEntity();
            memberItem.setMemberId(ownerUserId);
            memberItem.setMemberUsername(viewModel.getOwnerUsername());
            memberItem.setMemberRole(GroupMemberRoleEnum.ADMIN);
            memberItem.setMemberStatus(GroupMemberStatusEnum.NORMAL);
            memberItem.setJoinTime(Calendar.getInstance().toInstant());
            inputMemberLists.add(memberItem);

            //Danh sách thành viên
            inputChatGroups.setMemberLists(inputMemberLists);

            //Số thành viên tối đa của nhóm bí mật (chưa chốt số cuối)
            if (inputChatGroups.getGroupType() == GroupTypeEnum.SECRET) {
                maxMember = applicationProperties.getChatService().getChatgroupSecretMaxMember();
                inputChatGroups.setMaxMember(maxMember);
            }

            //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
            inputChatGroups.setLastModifiedBy(viewModel.getOwnerUsername());

            //Dữ liệu thống kê
            GroupMemberStatisticsService gmStatisticsService = new GroupMemberStatisticsService(groupMemberStatisticsRepository);
            gmStatisticsService.increaseStatistics();
            inputChatGroups.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

            ChatGroupsEntity newGroupInfo = chatGroupsRepository.save(inputChatGroups);

            //Thêm group mới vào danh sách cho người gửi yêu cầu
            addGroupIntoContactGroupList(viewModel.getOwnerUsername(), newGroupInfo.getId(), newGroupInfo.getGroupName());

            return newGroupInfo;
        }
        else
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("chatgroups_create_notfound");
            outputEntity.setException("The chatgroup info is notfound!");
        }
        return inputChatGroups;
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
        ChatGroupsEntity dbInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbInfo != null && dbInfo.getId() != null)
        {
            boolean isExists = false;
            List<extGroupMemberEntity> memberLists = dbInfo.getMemberLists();
            if(viewModel.getOwnerUsername() != dbInfo.getCreatedBy()) {
                for (extGroupMemberEntity currentMbr : memberLists) {
                    if (currentMbr.getMemberUsername() == viewModel.getOwnerUsername()) {
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
                    dbInfo.setGroupName(viewModel.getGroupName());
                //Ảnh đại diện
                if (!viewModel.getGroupIcon().isEmpty())
                    dbInfo.setGroupIcon(viewModel.getGroupIcon());
                //Ảnh nền
                if (!viewModel.getGroupBackground().isEmpty())
                    dbInfo.setGroupBackground(viewModel.getGroupBackground());
                //Câu khẩu hiệu
                if (!viewModel.getGroupSlogan().isEmpty())
                    dbInfo.setGroupSlogan(viewModel.getGroupSlogan());
                //Giới thiệu nhóm
                if (!viewModel.getGroupSumary().isEmpty())
                    dbInfo.setGroupSumary(viewModel.getGroupSumary());

                //Người sửa, ngày sửa || người tạo, ngày tạo (log-history)
                dbInfo.setLastModifiedBy(viewModel.getOwnerUsername().toString());

                //Cập nhật tên group trong danh sách của từng thành viên
                for (extGroupMemberEntity currentMbr : memberLists) {
                    updateGroupIntoContactGroupList(currentMbr.getMemberId(), dbInfo.getId(), dbInfo.getGroupName());
                }

                return chatGroupsRepository.save(dbInfo);
            }
            else
            {
                outResult.setError(ResponseErrorCode.DENIED.getValue());                                    //denied
                outResult.setMessage("chatgroups_denied_error");
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
     * Admin thực hiện xóa group (08/06/2018).
     * (Hàm bổ sung)
     * @param viewModel: entity chứa thông tin của group
     * @param outResult: entity của thông tin sẽ trả về cho client
     */
    public boolean delete(DeleteGroupVM viewModel, IsoResponseEntity outResult)
    {
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getId() != null)
        {
            if(dbSourceInfo.getCreatedById() == viewModel.getOwnerId()) {
                //Loại bỏ group cho toàn bộ danh sách thành viên của group
                List<extGroupMemberEntity> removeMemberLists = dbSourceInfo.getMemberLists();
                for (extGroupMemberEntity removeMember : removeMemberLists) {
                    removeGroupInContactGroupList(removeMember.getMemberId(), dbSourceInfo.getId());
                }

                //Loại bỏ group
                chatGroupsRepository.delete(viewModel.getGroupId());
                return true;
            }
            else
            {
                outResult.setError(ResponseErrorCode.DENIED.getValue());                                    //denied
                outResult.setMessage("groupmembers_denied_error");
                outResult.setException("Request to delete this group is rejected!");
                return false;
            }
        }
        else {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                      //notfound
            outResult.setMessage("groupmembers_notfound_error");
            outResult.setException("The record is not found!");
            return false;
        }
    }

    /**
     * Thêm thành viên vào group, có thể thêm 1 hoặc nhiều
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the list members
     */
    public List<extGroupMemberEntity> appendMembers(ActionGroupVM viewModel, IsoResponseEntity outResult)
    {
        //Kiểm tra nếu tồn tại thì cập nhật, nếu không tồn tại thì bổ sung vào
        List<extGroupMemberEntity> memberLists = new ArrayList<>();
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getId() != null) {
            memberLists = dbSourceInfo.getMemberLists();

            List<MembersActionGroupVM> toAppendLists = viewModel.getListMembers();
            int totalAllMember = memberLists.size() + toAppendLists.size();
            if(totalAllMember < dbSourceInfo.getMaxMember()) {
                for (MembersActionGroupVM appendItem : toAppendLists) {
                    boolean isExists = false;
                    for (extGroupMemberEntity currentMbr : memberLists) {
                        if (currentMbr.getMemberId() == appendItem.getMemberId()) {
                            isExists = true;
                            break;
                        }
                    }

                    //insert if it is not exists
                    if (!isExists) {
                        extGroupMemberEntity memberItem = new extGroupMemberEntity();
                        memberItem.setMemberId(appendItem.getMemberId());
                        memberItem.setMemberRole(GroupMemberRoleEnum.MEMBER);
                        memberItem.setMemberStatus(GroupMemberStatusEnum.NORMAL);
                        memberItem.setJoinTime(Calendar.getInstance().toInstant());
                        memberLists.add(memberItem);

                        //Cập nhật bản ghi cho người gửi yêu cầu
                        addGroupIntoContactGroupList(appendItem.getMemberId().toString(), dbSourceInfo.getId(), dbSourceInfo.getGroupName());
                    }
                }

                //Lưu thông tin vào trong bảng GroupMembers
                dbSourceInfo.setMemberLists(memberLists);
                dbSourceInfo.setLastModifiedTime(new Date().getTime());
                chatGroupsRepository.save(dbSourceInfo);
            }
            else
            {
                outResult.setError(ResponseErrorCode.DENIED.getValue());                                 //denied
                outResult.setMessage("groupmembers_denied_error");
                outResult.setException("Request to append the list members is rejected!");
            }
        }
        else
        {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                    //notfound
            outResult.setMessage("groupmembers_notfound_error");
            outResult.setException("The record is not found!");
        }

        return  memberLists;
    }

    /**
     * Loại bỏ thành viên khỏi group (chỉ ADMIN hoặc MANAGER mới có quyền)
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the list members
     */
    public List<extGroupMemberEntity> removeMembers(ActionGroupVM viewModel, IsoResponseEntity outResult)
    {
        //Kiểm tra nếu tồn tại thì cập nhật, nếu không tồn tại thì bổ sung vào
        boolean allowRemove = false;
        List<extGroupMemberEntity> memberLists = new ArrayList<>();
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getId() != null) {
            memberLists = dbSourceInfo.getMemberLists();

            //Kiểm tra xem người thực hiện có quyền xóa không?
            if ((viewModel.getOwnerId() == dbSourceInfo.getCreatedById())) {
                allowRemove = true;
            }
            else {
                for (extGroupMemberEntity exists : memberLists) {
                    if (exists.getMemberId() == viewModel.getOwnerId() && exists.getMemberRole() == GroupMemberRoleEnum.MANAGER) {
                        allowRemove = true;
                        break;
                    }
                }
            }

            //Nếu có quyền thì thực hiện loại bỏ từng thành viên
            if(allowRemove) {
                List<MembersActionGroupVM> removeMemberLists = viewModel.getListMembers();
                for (MembersActionGroupVM removeMember : removeMemberLists) {
                    boolean isExists = false;
                    for (extGroupMemberEntity currentMbr : memberLists) {
                        if (currentMbr.getMemberId() != viewModel.getOwnerId() && currentMbr.getMemberId() == removeMember.getMemberId()) {
                            memberLists.remove(currentMbr);
                            isExists = true;
                            break;
                        }
                    }

                    //Loại bỏ group trong danh sách group của User
                    if (isExists) {
                        removeGroupInContactGroupList(removeMember.getMemberId(), dbSourceInfo.getId());
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
            outResult.setError(ResponseErrorCode.DENIED.getValue());                                    //denied
            outResult.setMessage("groupmembers_denied_error");
            outResult.setException("Request to append the list members is rejected!");
        }

        return  memberLists;
    }

    /**
     * Thành viên tự rời khỏi group
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the list members
     */
    public boolean memberLeave(MembersLeaveGroupVM viewModel, IsoResponseEntity outResult)
    {
        //Kiểm tra nếu tồn tại thì cập nhật, nếu không tồn tại thì bổ sung vào
        boolean isExists = false;
        ChatGroupsEntity dbSourceInfo = chatGroupsRepository.findOne(viewModel.getGroupId());
        if(dbSourceInfo != null && dbSourceInfo.getId() != null) {
            List<extGroupMemberEntity> memberLists = dbSourceInfo.getMemberLists();

            for (extGroupMemberEntity currentMbr : memberLists) {
                if (currentMbr.getMemberId() == viewModel.getMemberId()) {
                    memberLists.remove(currentMbr);
                    isExists = true;
                    break;
                }
            }

            //Cập nhật bản ghi cho người cần remove
            if (isExists) {
                //Loại bỏ group trong danh sách group của User
                removeGroupInContactGroupList(viewModel.getMemberId(), dbSourceInfo.getId());

                //Cập nhật lại danh sách thành viên của bảng GroupMembers
                dbSourceInfo.setMemberLists(memberLists);
                dbSourceInfo.setLastModifiedTime(new Date().getTime());
                chatGroupsRepository.save(dbSourceInfo);

                return true;
            }
        }

        //Nếu không tìm thấy thì báo lỗi
        if(!isExists)
        {
            outResult.setError(ResponseErrorCode.NOTFOUND.getValue());                                    //notfound
            outResult.setMessage("groupmembers_notfound_error");
            outResult.setException("The record is not found!");
        }
        return false;
    }

    //*****************************************************************************
    //********************************CÁC HÀM NỘI BỘ*******************************

    /**
     * Loại bỏ group trong danh sách group của User
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the action result
     */
    private boolean removeGroupInContactGroupList(Long memberId, String groupKeyId)
    {
//        log.debug("removeGroupInContactGroupList {}-{}", memberId, groupKeyId);
//        ContactsEntity dbContactInfo = contactsRepository.findOneByownerId(memberId);
//        if (dbContactInfo != null && dbContactInfo.getId() != null) {
//            List<extContactGroupEntity> existGroupLists = dbContactInfo.getGroupLists();
//            if (existGroupLists != null && existGroupLists.size() > 0) {
//                for (extContactGroupEntity currentGrp : existGroupLists) {
//                    if(currentGrp.getId().equals(groupKeyId))
//                    {
//                        existGroupLists.remove(currentGrp);
//                        break;
//                    }
//                }
//
//                //Ghi nhận lại thông tin Group
//                dbContactInfo.setGroupLists(existGroupLists);
//                dbContactInfo.setLastModifiedUnixTime(new Date().getTime());
//                contactsRepository.save(dbContactInfo);
//            }
//        }
        return true;
    }

    /**
     * Thêm group vào danh sách group của User
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time: 2018-06-29
     * @return the action result
     */
    private boolean addGroupIntoContactGroupList(String ownerUsername, String groupKeyId, String groupName)
    {
        ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerUsername(ownerUsername);
        if (dbContactInfo != null && dbContactInfo.getId() != null) {
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
        ContactsEntity dbContactInfo = contactsRepository.findOneByOwnerId(ownerUserid);
        if (dbContactInfo != null && dbContactInfo.getId() != null) {
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
