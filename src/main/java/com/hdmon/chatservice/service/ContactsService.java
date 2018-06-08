package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.ContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;
import com.hdmon.chatservice.domain.extents.extContactGroupEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.domain.responses.resFriendItemInfo;
import com.hdmon.chatservice.repository.ContactsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.FriendsVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        log.debug("Request to get all Contacts by ownerId: {}", ownerId);
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
        log.debug("Request to searc all Contacts by loginName: {}-{}", ownerId, loginName);

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
     * @return the entity
     */
    public List<extFriendMemberEntity> requireAddFriends(FriendsVM friendsVm, IsoResponseEntity responseEntity)
    {
        log.debug("Send add friend request for ownerId.: {}", friendsVm);

        //Kiểm tra
        ContactsEntity dbInfo = contactsRepository.findOneByownerId(friendsVm.getOwnerId());
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extFriendMemberEntity> memberExists = dbInfo.getFriendLists();
            if(memberExists != null && memberExists.size() > 0)
            {
                //Check if it is exists in list
                for (extFriendMemberEntity exist : memberExists) {
                    if(exist.getFriendId() == friendsVm.getFriendId())
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
                String chatRoomId = friendsVm.getOwnerId() + "#" + friendsVm.getFriendId();
                memberItem.setChatRoomChatId(chatRoomId);
                memberItem.setFriendId(friendsVm.getFriendId());
                memberItem.setFriendLogin(friendsVm.getFriendLogin());
                memberItem.setFriendName(friendsVm.getFriendName());
                memberItem.setStatus(FriendStatusEnum.FOLLOW);
                memberExists.add(memberItem);

                dbInfo.setFriendLists(memberExists);
                dbInfo.setFriendCount(memberExists.size());
                dbInfo.setLastModifiedUnixTime(new Date().getTime());
                contactsRepository.save(dbInfo);

                return memberExists;
            }
            else {
                responseEntity.setError(ResponseErrorCode.EXISTS.getValue());
                responseEntity.setMessage("friendId_exists");
                responseEntity.setException("This person already exists in list!");
                return new ArrayList<>();
            }
        }
        else
        {
            //Trường hợp không tồn tại thì tiến hành tạo mới
            List<extFriendMemberEntity> memberLists = new ArrayList<>();
            extFriendMemberEntity memberItem = new extFriendMemberEntity();
            String chatRoomId = friendsVm.getOwnerId() + "#" + friendsVm.getFriendId();
            memberItem.setChatRoomChatId(chatRoomId);
            memberItem.setFriendId(friendsVm.getFriendId());
            memberItem.setFriendLogin(friendsVm.getFriendLogin());
            memberItem.setFriendName(friendsVm.getFriendName());
            memberItem.setStatus(FriendStatusEnum.FOLLOW);
            memberLists.add(memberItem);

            ContactsEntity input = new ContactsEntity();
            input.setOwnerId(friendsVm.getOwnerId());
            input.setOwnerLogin(friendsVm.getOwnerLogin());
            input.setFriendLists(memberLists);
            input.setFriendCount(memberLists.size());
            input.setGroupLists(new ArrayList<>());
            input.setGroupCount(0);
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            contactsRepository.save(input);

            return memberLists;
        }
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the entity
     */
    public List<extFriendMemberEntity> acceptAddFriends(FriendsVM friendsVm, IsoResponseEntity responseEntity)
    {
        log.debug("Send accept friend request for ownerId.: {}", friendsVm);

        //Kiểm tra nếu tồn tại thì cập nhật, nếu không tồn tại thì bổ sung vào
        List<extFriendMemberEntity> memberLists = new ArrayList<>();
        ContactsEntity dbAcceptInfo = contactsRepository.findOneByownerId(friendsVm.getOwnerId());
        if(dbAcceptInfo != null && dbAcceptInfo.getId() != null) {
            boolean isExists = false;

            if(dbAcceptInfo.getFriendLists() != null && dbAcceptInfo.getFriendLists().size() > 0)
            {
                memberLists = dbAcceptInfo.getFriendLists();
                for (extFriendMemberEntity exist : memberLists) {
                    if(exist.getFriendId() == friendsVm.getFriendId())
                    {
                        exist.setStatus(FriendStatusEnum.FRIEND);
                        exist.setLastModifiedUnixTime(new Date().getTime());
                        isExists = true;
                        break;
                    }
                }
            }

            //insert if it is not exists
            if(!isExists)
            {
                extFriendMemberEntity memberItem = new extFriendMemberEntity();
                String chatRoomId = friendsVm.getFriendId() + "#" + friendsVm.getOwnerId();
                memberItem.setChatRoomChatId(chatRoomId);
                memberItem.setFriendId(friendsVm.getFriendId());
                memberItem.setFriendLogin(friendsVm.getFriendLogin());
                memberItem.setFriendName(friendsVm.getFriendName());
                memberItem.setStatus(FriendStatusEnum.FRIEND);
                memberLists.add(memberItem);
            }

            dbAcceptInfo.setFriendLists(memberLists);
            dbAcceptInfo.setFriendCount(memberLists.size());
            dbAcceptInfo.setLastModifiedUnixTime(new Date().getTime());
            contactsRepository.save(dbAcceptInfo);
        }
        else
        {
            //Trường hợp không tồn tại thì tiến hành tạo mới cho người đồng ý
            extFriendMemberEntity memberItem = new extFriendMemberEntity();
            String chatRoomId = friendsVm.getFriendId() + "#" + friendsVm.getOwnerId();
            memberItem.setChatRoomChatId(chatRoomId);
            memberItem.setFriendId(friendsVm.getFriendId());
            memberItem.setFriendLogin(friendsVm.getFriendLogin());
            memberItem.setFriendName(friendsVm.getFriendName());
            memberItem.setStatus(FriendStatusEnum.FRIEND);
            memberLists.add(memberItem);

            ContactsEntity input = new ContactsEntity();
            input.setOwnerId(friendsVm.getOwnerId());
            input.setOwnerLogin(friendsVm.getOwnerLogin());
            input.setFriendLists(memberLists);
            input.setFriendCount(memberLists.size());
            input.setGroupLists(new ArrayList<>());
            input.setGroupCount(0);
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            contactsRepository.save(input);
        }

        //Cập nhật bản ghi cho người gửi yêu cầu
        ContactsEntity dbRequireInfo = contactsRepository.findOneByownerId(friendsVm.getFriendId());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            List<extFriendMemberEntity> requireMemberLists = dbRequireInfo.getFriendLists();
            for (extFriendMemberEntity exist : requireMemberLists) {
                if(exist.getFriendId() == friendsVm.getOwnerId())
                {
                    exist.setStatus(FriendStatusEnum.FRIEND);
                    exist.setLastModifiedUnixTime(new Date().getTime());
                    break;
                }
            }
            dbRequireInfo.setFriendLists(requireMemberLists);
            dbRequireInfo.setFriendCount(requireMemberLists.size());
            dbRequireInfo.setLastModifiedUnixTime(new Date().getTime());
            contactsRepository.save(dbRequireInfo);
        }

        return  memberLists;
    }
}
