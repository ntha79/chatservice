package com.hdmon.chatservice.service;

import com.hdmon.chatservice.config.ApplicationProperties;
import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;
import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.repository.ContactsRepository;
import com.hdmon.chatservice.service.dto.User;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.service.util.UserHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.Contacts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Service Implementation for managing Contacts.
 */
@Service
@Transactional
public class ContactsService {
    private final Logger log = LoggerFactory.getLogger(ContactsService.class);

    private final ContactsRepository contactsRepository;
    private final ApplicationProperties applicationProperties;
    private String gatewayUrl;

    public ContactsService(ContactsRepository contactsRepository, ApplicationProperties applicationProperties) {
        this.contactsRepository = contactsRepository;
        this.applicationProperties = applicationProperties;
        this.gatewayUrl = this.applicationProperties.getPortal().getGatewayUrl();
    }

    /**
     * Save a Contacts.
     *
     * @param friends the entity to save
     * @return the persisted entity
     */
    public ContactsEntity save(ContactsEntity friends) {
        log.debug("Request to save Contacts : {}", friends);
        return contactsRepository.save(friends);
    }

    /**
     * Get all the Contacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContactsEntity> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactsRepository.findAll(pageable);
    }

    /**
     * Get one Contacts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ContactsEntity findOne(String id) {
        log.debug("Request to get Contacts : {}", id);
        return contactsRepository.findOne(id);
    }

    /**
     * Delete the Contacts by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Contacts : {}", id);
        contactsRepository.delete(id);
    }

    /**
     * Lấy thông tin bạn bè của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<extFriendContactEntity> findOneByOwnerUsername(HttpServletRequest request, String ownerUsername)
    {
        List<extFriendContactEntity> responseList = new ArrayList<>();
        ContactsEntity dbInfo;
        if(!ownerUsername.isEmpty())
        {
            dbInfo = contactsRepository.findOneByOwnerUsername(ownerUsername);
            if(dbInfo == null)
            {
                //Trường hợp không tồn tại thì tiến hành tạo mới
                CreateNewFriendInfo(request, ownerUsername);
            }
            else {
                responseList = dbInfo.getFriendLists();

                //sort by inSystem
                Collections.sort(responseList, new Comparator<extFriendContactEntity>() {
                    @Override
                    public int compare(extFriendContactEntity o1, extFriendContactEntity o2) {
                        return o2.getInSystem() - o1.getInSystem();
                    }
                });
            }
        }
        return responseList;
    }

    /**
     * Lấy thông tin bạn bè của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time: 2018-06-29
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public GetChatListVM findChatListByOwnerUsername(HttpServletRequest request, String ownerUsername)
    {

        List<FriendInfoVM> friendList = new ArrayList<>();
        List<GroupInfoVM> groupList = new ArrayList<>();

        if(!ownerUsername.isEmpty())
        {
            ContactsEntity dbOwnerInfo = contactsRepository.findOneByOwnerUsername(ownerUsername);
            log.info("dbOwnerInfo: {}", dbOwnerInfo);
            if(dbOwnerInfo == null || dbOwnerInfo.getId().isEmpty())
            {
                //Trường hợp không tồn tại thì tiến hành tạo mới
                CreateNewFriendInfo(request, ownerUsername);
            }
            else {
                //Danh sách bạn bè
                List<extFriendContactEntity> dbFriendLists = dbOwnerInfo.getFriendLists();
                if (dbFriendLists != null && dbFriendLists.size() > 0) {
                    for (extFriendContactEntity item : dbFriendLists) {
                        FriendStatusEnum itemStatus = item.getStatus();
                        if (itemStatus == FriendStatusEnum.FOLLOW || itemStatus == FriendStatusEnum.REQUEST || itemStatus == FriendStatusEnum.FRIEND || itemStatus == FriendStatusEnum.BLOCKED) {
                            FriendInfoVM friendInfoVM = CreateNewFriendInfoVM(item);
                            friendList.add(friendInfoVM);
                        }
                    }
                }

                //Danh sách nhóm
                List<extContactGroupEntity> dbGroupLists = dbOwnerInfo.getGroupLists();
                if (dbGroupLists != null && dbGroupLists.size() > 0) {
                    for (extContactGroupEntity item : dbGroupLists) {
                        GroupInfoVM groupInfoVM = CreateNewGroupInfoVM(item);
                        groupList.add(groupInfoVM);
                    }
                }
            }
        }

        GetChatListVM responseItem = new GetChatListVM(friendList, groupList);
        return responseItem;
    }

    /**
     * Lấy thông tin bạn bè của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time: 2018-06-29
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public GetContactListVM findContactListByOwnerUsername(HttpServletRequest request, String ownerUsername)
    {

        List<FriendInfoVM> friendList = new ArrayList<>();
        List<GroupInfoVM> groupList = new ArrayList<>();
        List<DeviceContactInfoVM> deviceContactList = new ArrayList<>();

        if(!ownerUsername.isEmpty())
        {
            ContactsEntity dbOwnerInfo = contactsRepository.findOneByOwnerUsername(ownerUsername);
            log.info("dbOwnerInfo: {}-{}", ownerUsername, dbOwnerInfo);
            if(dbOwnerInfo == null || dbOwnerInfo.getId().isEmpty())
            {
                //Trường hợp không tồn tại thì tiến hành tạo mới
                CreateNewFriendInfo(request, ownerUsername);
            }
            else {
                //Danh sách bạn bè
                List<extFriendContactEntity> dbFriendLists = dbOwnerInfo.getFriendLists();
                log.info("friendLists: {}", dbFriendLists);
                if (dbFriendLists != null && dbFriendLists.size() > 0) {
                    for (extFriendContactEntity item : dbFriendLists) {
                        FriendStatusEnum itemStatus = item.getStatus();
                        if (itemStatus == FriendStatusEnum.FOLLOW || itemStatus == FriendStatusEnum.REQUEST || itemStatus == FriendStatusEnum.FRIEND || itemStatus == FriendStatusEnum.BLOCKED) {
                            FriendInfoVM friendInfoVM = CreateNewFriendInfoVM(item);
                            friendList.add(friendInfoVM);
                        }
                        else if(itemStatus == FriendStatusEnum.UNKNOW)
                        {
                            DeviceContactInfoVM deviceContactInfoVM = new DeviceContactInfoVM();
                            deviceContactInfoVM.setFullname(item.getFullname());
                            deviceContactInfoVM.setMobile(item.getMobile());
                            deviceContactInfoVM.setEmail(item.getEmail());
                            deviceContactInfoVM.setCompany(item.getCompany());
                            deviceContactInfoVM.setImageUrl("");
                            deviceContactList.add(deviceContactInfoVM);
                        }
                    }
                }

                //Danh sách nhóm
                List<extContactGroupEntity> dbGroupLists = dbOwnerInfo.getGroupLists();
                if (dbGroupLists != null && dbGroupLists.size() > 0) {
                    for (extContactGroupEntity item : dbGroupLists) {
                        GroupInfoVM groupInfoVM = CreateNewGroupInfoVM(item);
                        groupList.add(groupInfoVM);
                    }
                }
            }
        }

        GetContactListVM responseItem = new GetContactListVM(friendList, groupList, deviceContactList);
        log.info("responseItem: {}", responseItem);
        return responseItem;
    }

    /**
     * Tìm kiếm thành viên của user hoặc của tất cả user.
     * (Hàm này có thể không cần do số lượng ít nên tìm trên client luôn)
     * (Hàm bổ sung)
     * Create Time: 2018-06-07
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<ContactsEntity> searchFriends(String ownerUsername, String friendUsername)
    {
        Sort sortBy = new Sort(Sort.Direction.ASC, "friendLists.username");
        List<ContactsEntity> dbResults = new ArrayList<>();
        if(!ownerUsername.isEmpty())
        {
            dbResults = contactsRepository.findAllByFriendUserNameAndOrderByFriendUserNameAsc(ownerUsername, friendUsername, sortBy);
        }
        else
        {
            dbResults = contactsRepository.findAllByFriendUserNameAndOrderByFriendUserNameAsc(friendUsername, sortBy);
        }
        return dbResults;
    }

    /**
     * Thêm mới contact vào danh sách.
     * (Hàm bổ sung)
     * Create Time: 2018-06-26
     * Update Time: 2018-06-26
     * @return the list friends entity
     */
    public List<extFriendContactEntity> addContact(HttpServletRequest request, UpdateContactVM viewModel, IsoResponseEntity outputEntity)
    {
        //Thêm mới bản ghi cho người thao tác
        List<extFriendContactEntity> responseList = execAddContactForOwner(request, viewModel, outputEntity);

        return responseList;
    }

    /**
     * Tạo yêu cầu kết bạn, nếu người gửi chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-26
     * @return the list friends entity
     */
    public List<extFriendContactEntity> requireAddFriends(HttpServletRequest request, RequireAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        //Thêm mới bản ghi cho người thao tác
        List<extFriendContactEntity> responseList = execAddFriendsForOwner(request, true, viewModel, FriendStatusEnum.FOLLOW, outputEntity);

        //Thêm mới bản ghi cho người được mời
        RequireAddFriendsVM viewModelFri = new RequireAddFriendsVM(viewModel.getFriendUsername(), viewModel.getOwnerUsername());
        execAddFriendsForOwner(request, false, viewModelFri, FriendStatusEnum.REQUEST, outputEntity);

        return responseList;
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the entity
     */
    public List<extFriendContactEntity> acceptAddFriends(HttpServletRequest request, ResponseAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        //Cập nhật cho người thao tác
        List<extFriendContactEntity> responseList = execAcceptFriendsForOwner(request, true, viewModel, FriendStatusEnum.FRIEND, outputEntity);

        //Cập nhật cho người bạn
        ResponseAddFriendsVM viewModelFri = new ResponseAddFriendsVM(viewModel.getFriendUsername(), viewModel.getOwnerUsername());
        execAcceptFriendsForOwner(request, false, viewModelFri, FriendStatusEnum.FRIEND, outputEntity);

        return  responseList;
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * Update Time:  2018-06-26
     * @return the entity
     */
    public List<extFriendContactEntity> deniedAddFriends(ResponseAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> friendLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            friendLists = dbRequireInfo.getFriendLists();
            if(friendLists != null && friendLists.size() > 0) {
                for (extFriendContactEntity exists : friendLists) {
                    if (exists.getUsername().equals(viewModel.getFriendUsername()) && exists.getStatus() == FriendStatusEnum.REQUEST) {
                        blItemExists = true;
                        friendLists.remove(exists);
                        break;
                    }
                }

                dbRequireInfo.setFriendLists(friendLists);
                dbRequireInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbRequireInfo);
            }
        }

        //Trường hợp không có dữ liệu
        if(!blItemExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("friends_info_notfound");
            outputEntity.setException("The friend info is notfound!");
        }

        return friendLists;
    }

    /**
     * Block bạn bè (bạn bè không thể chat, cũng như có các action trên feeds).
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time:  2018-06-29
     * @return the entity
     */
    public List<extFriendContactEntity> blockFriends(ResponseAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> friendLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            friendLists = dbRequireInfo.getFriendLists();
            if(friendLists != null && friendLists.size() > 0) {
                for (extFriendContactEntity exists : friendLists) {
                    if (exists.getUsername().equals(viewModel.getFriendUsername()) && exists.getStatus() == FriendStatusEnum.FRIEND) {
                        blItemExists = true;
                        exists.setStatus(FriendStatusEnum.BLOCKED);
                        break;
                    }
                }

                dbRequireInfo.setFriendLists(friendLists);
                dbRequireInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbRequireInfo);
            }
        }

        //Trường hợp không có dữ liệu
        if(!blItemExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("friends_info_notfound");
            outputEntity.setException("The friend info is notfound!");
        }

        return friendLists;
    }

    /**
     * Xóa bạn bè (danh sách người xóa sẽ không còn và danh sách người nhận sẽ ở trạng thái Unknow).
     * (Hàm bổ sung)
     * Create Time: 2018-06-29
     * Update Time:  2018-06-29
     * @return the entity
     */
    public List<extFriendContactEntity> deleteFriends(ResponseAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> actionFriendLists = new ArrayList<>();
        ContactsEntity dbActionInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbActionInfo != null && dbActionInfo.getId() != null)
        {
            actionFriendLists = dbActionInfo.getFriendLists();
            if(actionFriendLists != null && actionFriendLists.size() > 0) {
                for (extFriendContactEntity exists : actionFriendLists) {
                    if (exists.getUsername().equals(viewModel.getFriendUsername()) && exists.getStatus() == FriendStatusEnum.FRIEND) {
                        blItemExists = true;
                        actionFriendLists.remove(exists);
                        break;
                    }
                }

                dbActionInfo.setFriendLists(actionFriendLists);
                dbActionInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbActionInfo);

                //Cập nhật cho bạn bè
                ContactsEntity dbReferInfo = contactsRepository.findOneByOwnerUsername(viewModel.getFriendUsername());
                if(dbReferInfo != null && dbReferInfo.getId() != null) {
                    List<extFriendContactEntity> referFriendLists1 = dbReferInfo.getFriendLists();
                    if (referFriendLists1 != null && referFriendLists1.size() > 0) {
                        for (extFriendContactEntity exists : referFriendLists1) {
                            if (exists.getUsername().equals(viewModel.getOwnerUsername()) && exists.getStatus() == FriendStatusEnum.FRIEND) {
                                exists.setStatus(FriendStatusEnum.FOLLOW);
                                break;
                            }
                        }

                        dbReferInfo.setFriendLists(referFriendLists1);
                        dbReferInfo.setLastModifiedTime(new Date().getTime());
                        contactsRepository.save(dbReferInfo);
                    }
                }
            }
        }

        //Trường hợp không có dữ liệu
        if(!blItemExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("friends_info_notfound");
            outputEntity.setException("The friend info is notfound!");
        }

        return actionFriendLists;
    }

    /**
     * Cập nhật thông tin của bạn bè.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the entity
     */
    public List<extFriendContactEntity> updateFriendInfo(UpdateContactVM viewModel, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> friendLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            friendLists = dbRequireInfo.getFriendLists();
            if(friendLists != null && friendLists.size() > 0) {
                for (extFriendContactEntity exists : friendLists) {
                    if (exists.getUsername().equals(viewModel.getFriendUsername())) {
                        blItemExists = true;
                        exists.setFullname(viewModel.getFriendFullname());
                        exists.setCompany(viewModel.getFriendCompany());
                        exists.setMobile(viewModel.getFriendMobile());
                        exists.setEmail(viewModel.getFriendEmail());
                        exists.setLastModifiedTime(new Date().getTime());
                        break;
                    }
                }

                dbRequireInfo.setFriendLists(friendLists);
                dbRequireInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbRequireInfo);
            }
        }

        //Trường hợp không có dữ liệu
        if(!blItemExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("friends_info_notfound");
            outputEntity.setException("The friend info is notfound!");
        }

        return friendLists;
    }

    //==================================================================================================================
    //===================================================PRIVATE START==================================================
    /**
     * Thực hiện kiểm tra và ghi dữ liệu vào DB.
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAddContactForOwner(HttpServletRequest request, UpdateContactVM viewModel, IsoResponseEntity outputEntity)
    {
        Integer intInSystem = 0;
        FriendStatusEnum statusEnum = FriendStatusEnum.UNKNOW;
        ContactsEntity dbInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extFriendContactEntity> friendExists = dbInfo.getFriendLists();
            if(friendExists != null && friendExists.size() > 0)
            {
                for (extFriendContactEntity exists : friendExists) {
                    if(exists.getUsername().equals(viewModel.getFriendUsername()))
                    {
                        isExists = true;
                        break;
                    }
                }
            }

            //insert if it is not exists
            if(!isExists)
            {
                Long friendUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 3, viewModel.getFriendUsername(), viewModel.getFriendMobile());
                if(friendUserId > 0) {
                    intInSystem = 1;
                    statusEnum = FriendStatusEnum.FOLLOW;
                }

                extFriendContactEntity friendItem = new extFriendContactEntity();
                friendItem.setUserId(friendUserId);
                friendItem.setUsername(viewModel.getFriendUsername());
                friendItem.setFullname(viewModel.getFriendFullname());
                friendItem.setCompany(viewModel.getFriendCompany());
                friendItem.setMobile(viewModel.getFriendMobile());
                friendItem.setEmail(viewModel.getFriendEmail());
                friendItem.setStatus(statusEnum);
                friendItem.setInSystem(intInSystem);
                friendExists.add(friendItem);

                dbInfo.setFriendLists(friendExists);
                contactsRepository.save(dbInfo);

                return friendExists;
            }
            else {
                outputEntity.setError(ResponseErrorCode.EXISTS.getValue());
                outputEntity.setMessage("friends_username_exists");
                outputEntity.setException("This person already exists in list!");
                return new ArrayList<>();
            }
        }
        else
        {
            //Trường hợp không tồn tại thì tiến hành tạo mới
            Long friendUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 3, viewModel.getFriendUsername(), viewModel.getFriendMobile());
            if(friendUserId > 0) {
                intInSystem = 1;
                statusEnum = FriendStatusEnum.FOLLOW;
            }

            List<extFriendContactEntity> friendLists = new ArrayList<>();
            extFriendContactEntity friendItem = new extFriendContactEntity();
            friendItem.setUserId(friendUserId);
            friendItem.setUsername(viewModel.getFriendUsername());
            friendItem.setFullname(viewModel.getFriendFullname());
            friendItem.setCompany(viewModel.getFriendCompany());
            friendItem.setMobile(viewModel.getFriendMobile());
            friendItem.setEmail(viewModel.getFriendEmail());
            friendItem.setStatus(statusEnum);
            friendItem.setInSystem(intInSystem);
            friendLists.add(friendItem);

            ContactsEntity input = new ContactsEntity();
            input.setOwnerUsername(viewModel.getOwnerUsername());
            input.setFriendLists(friendLists);
            input.setGroupLists(new ArrayList<>());
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            contactsRepository.save(input);

            return friendLists;
        }
    }

    /**
     * Thực hiện kiểm tra và ghi dữ liệu vào DB.
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAddFriendsForOwner(HttpServletRequest request, boolean isOwnerRequest, RequireAddFriendsVM viewModel, FriendStatusEnum statusEnum, IsoResponseEntity outputEntity)
    {
        List<extFriendContactEntity> requireMemberLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            boolean isExists = false;
            requireMemberLists = dbRequireInfo.getFriendLists();
            if(requireMemberLists != null && requireMemberLists.size() > 0)
            {
                //Check if it is exists in list
                for (extFriendContactEntity exists : requireMemberLists) {
                    if(exists.getUsername().equals(viewModel.getFriendUsername()))
                    {
                        isExists = true;
                        exists.setStatus(statusEnum);
                        exists.setLastModifiedTime(new Date().getTime());
                        break;
                    }
                }
            }
            else
            {
                requireMemberLists = new ArrayList<>();
            }

            //Trường hợp tồn tại trong danh sách thì ghi nhận lại trạng thái
            if(isExists)
            {
                dbRequireInfo.setFriendLists(requireMemberLists);
                dbRequireInfo.setLastModifiedTime(new Date().getTime());
                contactsRepository.save(dbRequireInfo);
            }
            else {
                if(isOwnerRequest) {
                    outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
                    outputEntity.setMessage("friends_list_notfound");
                    outputEntity.setException("The friend list is not found!");
                }
                else
                {
                    Long friendUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 1, viewModel.getFriendUsername(), "");
                    if(friendUserId > 0) {
                        extFriendContactEntity memberItem = new extFriendContactEntity();
                        memberItem.setUserId(friendUserId);
                        memberItem.setUsername(viewModel.getFriendUsername());
                        memberItem.setStatus(statusEnum);
                        memberItem.setInSystem(1);
                        requireMemberLists.add(memberItem);

                        dbRequireInfo.setFriendLists(requireMemberLists);
                        dbRequireInfo.setLastModifiedTime(new Date().getTime());
                        contactsRepository.save(dbRequireInfo);
                    }
                    else
                    {
                        outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
                        outputEntity.setMessage("friends_info_notfound");
                        outputEntity.setException("The friend info is not found!");
                    }
                }
            }
        }
        else
        {
            if(isOwnerRequest) {
                outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
                outputEntity.setMessage("friends_info_notfound");
                outputEntity.setException("The friend info is notfound!");
            }
            else
            {
                //Trường hợp không tồn tại thì tiến hành tạo mới
                Long friendUserId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 1, viewModel.getFriendUsername(), "");
                if(friendUserId > 0) {
                    List<extFriendContactEntity> memberLists = new ArrayList<>();
                    extFriendContactEntity memberItem = new extFriendContactEntity();
                    memberItem.setUserId(friendUserId);
                    memberItem.setUsername(viewModel.getFriendUsername());
                    memberItem.setStatus(statusEnum);
                    memberItem.setInSystem(1);
                    memberLists.add(memberItem);

                    ContactsEntity input = new ContactsEntity();
                    input.setOwnerUsername(viewModel.getOwnerUsername());
                    input.setFriendLists(memberLists);
                    input.setGroupLists(new ArrayList<>());
                    input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
                    contactsRepository.save(input);
                }
            }
        }
        return requireMemberLists;
    }

    /**
     * Thực hiện ghi nhận trạng thái chấp nhận là bạn bè DB.
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAcceptFriendsForOwner(HttpServletRequest request, boolean isForOwner, ResponseAddFriendsVM viewModel, FriendStatusEnum statusEnum, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> requireFriendLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            requireFriendLists = dbRequireInfo.getFriendLists();
            for (extFriendContactEntity exists : requireFriendLists) {
                if ((isForOwner && exists.getUsername().equals(viewModel.getFriendUsername()) && exists.getStatus() == FriendStatusEnum.REQUEST)
                    || (!isForOwner && exists.getUsername().equals(viewModel.getFriendUsername()))) {
                    blItemExists = true;
                    exists.setStatus(statusEnum);
                    exists.setLastModifiedTime(new Date().getTime());

                    //Lấy thông tin
                    if(isForOwner && exists.getFullname().isEmpty()) {
                        User friendInfo = UserHelper.getUserInfoFromUaaByUsername(request, gatewayUrl, viewModel.getFriendUsername());

                        exists.setFullname(friendInfo.getLastName() + " " + friendInfo.getFirstName());
                        exists.setEmail(friendInfo.getEmail());
                        exists.setMobile(friendInfo.getMobile());
                    }
                    break;
                }
            }

            dbRequireInfo.setFriendLists(requireFriendLists);
            dbRequireInfo.setLastModifiedTime(new Date().getTime());
            contactsRepository.save(dbRequireInfo);
        }

        //Trường hợp không tồn tại
        if(!blItemExists)
        {
            outputEntity.setError(ResponseErrorCode.NOTFOUND.getValue());
            outputEntity.setMessage("friends_info_notfound");
            outputEntity.setException("The friend info is notfound!");
        }

        return requireFriendLists;
    }

    /**
     * Tạo mới một bản ghi về thông tin liên hệ trong trường hợp không có dữ liệu
     * Create Time: 2018-06-29
     * Update Time:  2018-06-29
     */
    private void CreateNewFriendInfo(HttpServletRequest request, String ownerUsername)
    {
        Long lngOwnerId = UserHelper.execCheckUserExistsInSystem(request, gatewayUrl, 1, ownerUsername, "");
        if(lngOwnerId > 0) {
            ContactsEntity dbOwnerInfo = new ContactsEntity();
            dbOwnerInfo.setOwnerUserid(lngOwnerId);
            dbOwnerInfo.setOwnerUsername(ownerUsername);
            dbOwnerInfo.setFriendLists(new ArrayList<>());
            dbOwnerInfo.setGroupLists(new ArrayList<>());
            dbOwnerInfo.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            contactsRepository.save(dbOwnerInfo);
        }
    }

    /**
     * Hàm tạo mới Entity FriendInfoVM
     * Create Time: 2018-06-29
     * Update Time:  2018-06-29
     */
    private FriendInfoVM CreateNewFriendInfoVM(extFriendContactEntity item)
    {
        FriendInfoVM friendInfoVM = new FriendInfoVM();
        friendInfoVM.setUsername(item.getUsername());
        friendInfoVM.setFullname(item.getFullname());
        friendInfoVM.setEmail(item.getEmail());
        friendInfoVM.setMobile(item.getMobile());
        friendInfoVM.setImageUrl("");
        friendInfoVM.setStatus(item.getStatus());
        return friendInfoVM;
    }

    /**
     * Hàm tạo mới Entity GroupInfoVM
     * Create Time: 2018-06-29
     * Update Time:  2018-06-29
     */
    private GroupInfoVM CreateNewGroupInfoVM(extContactGroupEntity item)
    {
        GroupInfoVM groupInfoVM = new GroupInfoVM();
        groupInfoVM.setGroupId(item.getGroupId());
        groupInfoVM.setGroupName(item.getGroupName());
        groupInfoVM.setImageUrl("");
        //TODO: tính toán số thành viên của group ở đây (nếu cần)
        groupInfoVM.setMemberCount(0);
        return groupInfoVM;
    }

    //====================================================PRIVATE END===================================================
    //==================================================================================================================
}
