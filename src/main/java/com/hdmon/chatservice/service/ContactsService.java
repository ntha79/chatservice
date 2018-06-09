package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.repository.ContactsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.Contacts.RequireAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Contacts.ResponseAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Contacts.UpdateFriendVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Contacts.
 */
@Service
@Transactional
public class ContactsService {
    private final Logger log = LoggerFactory.getLogger(ContactsService.class);

    private final ContactsRepository contactsRepository;

    public ContactsService(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save
     * @return the persisted entity
     */
    public ContactsEntity save(ContactsEntity contacts) {
        log.debug("Request to save Contacts : {}", contacts);
        return contactsRepository.save(contacts);
    }

    /**
     * Get all the contacts.
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
     * Get one contacts by id.
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
     * Delete the contacts by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Contacts : {}", id);
        contactsRepository.delete(id);
    }

    /**
     * Lấy thông tin bạn bè của User.
     *
     * Create Time: 2018-06-05
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public ContactsEntity findOneByownerId(Long ownerId)
    {
        ContactsEntity dbInfo = new ContactsEntity();
        if(ownerId > 0)
        {
            dbInfo = contactsRepository.findOneByownerId(ownerId);
        }
        return dbInfo;
    }

    /**
     * Tìm kiếm thành viên của user hoặc của tất cả user.
     *
     * Create Time: 2018-06-07
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<ContactsEntity> searchContacts(Long ownerId, String loginName)
    {
        Sort sortBy = new Sort(Sort.Direction.ASC, "ownerLogin");
        List<ContactsEntity> dbResults = new ArrayList<>();
        if(ownerId > 0)
        {
            dbResults = contactsRepository.findAllByLoginNameAndOrderByLoginNameAsc(ownerId, loginName, sortBy);
        }
        else
        {
            dbResults = contactsRepository.findAllByLoginNameAndOrderByLoginNameAsc(loginName, sortBy);
        }
        return dbResults;
    }

    /**
     * Tạo yêu cầu kết bạn, nếu người gửi chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-09
     * @return the list friends entity
     */
    public List<extFriendMemberEntity> requireAddFriends(RequireAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        //Thêm mới bản ghi cho người thao tác
        String chatRoomId = viewModel.getOwnerId() + "#" + viewModel.getFriendId();
        List<extFriendMemberEntity> responseList = execAddFriendsForOwner(viewModel, chatRoomId, FriendStatusEnum.FOLLOW, true, outputEntity);

        //Thêm mới bản ghi cho người được mời
        RequireAddFriendsVM viewModelFri = new RequireAddFriendsVM(viewModel.getFriendId(), viewModel.getFriendName(), viewModel.getOwnerId(), viewModel.getOwnerName());
        execAddFriendsForOwner(viewModelFri, chatRoomId, FriendStatusEnum.WAIT, false, outputEntity);

        return responseList;
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the entity
     */
    public List<extFriendMemberEntity> acceptAddFriends(ResponseAddFriendsVM viewModel)
    {
        //Cập nhật cho người thao tác
        List<extFriendMemberEntity> responseList = execAcceptFriendsForOwner(viewModel, FriendStatusEnum.FRIEND);

        //Cập nhật cho người bạn
        ResponseAddFriendsVM viewModelFri = new ResponseAddFriendsVM(viewModel.getFriendId(), viewModel.getOwnerId());
        execAcceptFriendsForOwner(viewModelFri, FriendStatusEnum.FRIEND);

        return  responseList;
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the entity
     */
    public List<extFriendMemberEntity> deniedAddFriends(ResponseAddFriendsVM viewModel)
    {
        List<extFriendMemberEntity> responseList = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByownerId(viewModel.getOwnerId());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            responseList = dbRequireInfo.getFriendLists();
            for (extFriendMemberEntity exists : responseList) {
                if(exists.getFriendId() == viewModel.getFriendId())
                {
                    responseList.remove(exists);
                    break;
                }
            }

            dbRequireInfo.setFriendLists(responseList);
            dbRequireInfo.setLastModifiedUnixTime(new Date().getTime());
            contactsRepository.save(dbRequireInfo);
        }
        return responseList;
    }

    /**
     * Cập nhật thông tin của bạn bè.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * @return the entity
     */
    public List<extFriendMemberEntity> updateFriend(UpdateFriendVM viewModel)
    {
        List<extFriendMemberEntity> responseList = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByownerId(viewModel.getOwnerId());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            responseList = dbRequireInfo.getFriendLists();
            for (extFriendMemberEntity exists : responseList) {
                if(exists.getFriendId() == viewModel.getFriendId())
                {
                    exists.setFriendName(viewModel.getFriendName());
                    exists.setLastModifiedUnixTime(new Date().getTime());
                    break;
                }
            }

            dbRequireInfo.setFriendLists(responseList);
            dbRequireInfo.setLastModifiedUnixTime(new Date().getTime());
            contactsRepository.save(dbRequireInfo);
        }
        return responseList;
    }

    //==================================================================================================================
    //===================================================PRIVATE START==================================================
    /**
     * Thực hiện kiểm tra và ghi dữ liệu vào DB.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * @return the list friends entity
     */
    private List<extFriendMemberEntity> execAddFriendsForOwner(RequireAddFriendsVM viewModel, String chatRoomId, FriendStatusEnum statusEnum, boolean allowOutput, IsoResponseEntity outputEntity)
    {
        //Kiểm tra nếu chưa có thông tin thì thêm mới
        ContactsEntity dbInfo = contactsRepository.findOneByownerId(viewModel.getOwnerId());
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extFriendMemberEntity> memberExists = dbInfo.getFriendLists();
            if(memberExists != null && memberExists.size() > 0)
            {
                //Check if it is exists in list
                for (extFriendMemberEntity exists : memberExists) {
                    if(exists.getFriendId() == viewModel.getFriendId())
                    {
                        isExists = true;
                        break;
                    }
                }
            }
            else
            {
                memberExists = new ArrayList<>();
            }

            //insert if it is not exists
            if(!isExists)
            {
                extFriendMemberEntity memberItem = new extFriendMemberEntity();
                memberItem.setChatRoomChatId(chatRoomId);
                memberItem.setFriendId(viewModel.getFriendId());
                memberItem.setFriendName(viewModel.getFriendName());
                memberItem.setStatus(statusEnum);
                memberExists.add(memberItem);

                dbInfo.setFriendLists(memberExists);
                dbInfo.setLastModifiedUnixTime(new Date().getTime());
                contactsRepository.save(dbInfo);

                return memberExists;
            }
            else {
                if(allowOutput) {
                    outputEntity.setError(ResponseErrorCode.EXISTS.getValue());
                    outputEntity.setMessage("contacts_friendId_exists");
                    outputEntity.setException("This person already exists in list!");
                    return new ArrayList<>();
                }
                return null;
            }
        }
        else
        {
            //Trường hợp không tồn tại thì tiến hành tạo mới
            List<extFriendMemberEntity> memberLists = new ArrayList<>();
            extFriendMemberEntity memberItem = new extFriendMemberEntity();
            memberItem.setChatRoomChatId(chatRoomId);
            memberItem.setFriendId(viewModel.getFriendId());
            memberItem.setFriendName(viewModel.getFriendName());
            memberItem.setStatus(statusEnum);
            memberLists.add(memberItem);

            ContactsEntity input = new ContactsEntity();
            input.setOwnerId(viewModel.getOwnerId());
            input.setFriendLists(memberLists);
            input.setGroupLists(new ArrayList<>());
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            contactsRepository.save(input);

            return memberLists;
        }
    }

    /**
     * Thực ghi nhận trạng thái chấp nhận là bạn bè DB.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * @return the list friends entity
     */
    private List<extFriendMemberEntity> execAcceptFriendsForOwner(ResponseAddFriendsVM viewModel, FriendStatusEnum statusEnum)
    {
        List<extFriendMemberEntity> requireMemberLists = new ArrayList<>();
        ContactsEntity dbRequireInfo = contactsRepository.findOneByownerId(viewModel.getOwnerId());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            requireMemberLists = dbRequireInfo.getFriendLists();
            for (extFriendMemberEntity exists : requireMemberLists) {
                if(exists.getFriendId() == viewModel.getFriendId())
                {
                    exists.setStatus(statusEnum);
                    exists.setLastModifiedUnixTime(new Date().getTime());
                    break;
                }
            }

            dbRequireInfo.setFriendLists(requireMemberLists);
            dbRequireInfo.setLastModifiedUnixTime(new Date().getTime());
            contactsRepository.save(dbRequireInfo);
        }
        return requireMemberLists;
    }
    //====================================================PRIVATE END===================================================
    //==================================================================================================================
}
