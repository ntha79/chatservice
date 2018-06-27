package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.FriendsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;
import com.hdmon.chatservice.domain.extents.extFriendContactEntity;
import com.hdmon.chatservice.domain.extents.extFriendMemberEntity;
import com.hdmon.chatservice.repository.FriendsRepository;
import com.hdmon.chatservice.repository.FriendsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.Friends.RequireAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Friends.ResponseAddFriendsVM;
import com.hdmon.chatservice.web.rest.vm.Friends.UpdateFriendVM;
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
public class FriendsService {
    private final Logger log = LoggerFactory.getLogger(FriendsService.class);

    private final FriendsRepository friendsRepository;

    public FriendsService(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    /**
     * Save a friends.
     *
     * @param friends the entity to save
     * @return the persisted entity
     */
    public FriendsEntity save(FriendsEntity friends) {
        log.debug("Request to save Friends : {}", friends);
        return friendsRepository.save(friends);
    }

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FriendsEntity> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendsRepository.findAll(pageable);
    }

    /**
     * Get one contacts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FriendsEntity findOne(String id) {
        log.debug("Request to get Friends : {}", id);
        return friendsRepository.findOne(id);
    }

    /**
     * Delete the contacts by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Friends : {}", id);
        friendsRepository.delete(id);
    }

    /**
     * Lấy thông tin bạn bè của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<extFriendContactEntity> findOneByOwnerUsername(String ownerUsername)
    {
        List<extFriendContactEntity> responseList = new ArrayList<>();
        FriendsEntity dbInfo = new FriendsEntity();
        if(!ownerUsername.isEmpty())
        {
            dbInfo = friendsRepository.findOneByOwnerUsername(ownerUsername);
            responseList = dbInfo.getFriendLists();

            //sort by inSystem
            Collections.sort(responseList, new Comparator<extFriendContactEntity>() {
                @Override
                public int compare(extFriendContactEntity o1, extFriendContactEntity o2) {
                    return o2.getInSystem() - o1.getInSystem();
                }
            });
        }
        return responseList;
    }

    /**
     * Tìm kiếm thành viên của user hoặc của tất cả user.
     * (Hàm này có thể không cần do số lượng ít nên tìm trên client luôn)
     * (Hàm bổ sung)
     * Create Time: 2018-06-07
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public List<FriendsEntity> searchFriends(String ownerUsername, String friendUsername)
    {
        Sort sortBy = new Sort(Sort.Direction.ASC, "friendLists.username");
        List<FriendsEntity> dbResults = new ArrayList<>();
        if(!ownerUsername.isEmpty())
        {
            dbResults = friendsRepository.findAllByFriendUserNameAndOrderByFriendUserNameAsc(ownerUsername, friendUsername, sortBy);
        }
        else
        {
            dbResults = friendsRepository.findAllByFriendUserNameAndOrderByFriendUserNameAsc(friendUsername, sortBy);
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
    public List<extFriendContactEntity> addContact(UpdateFriendVM viewModel, IsoResponseEntity outputEntity)
    {
        //Thêm mới bản ghi cho người thao tác
        List<extFriendContactEntity> responseList = execAddContactForOwner(viewModel, FriendStatusEnum.FOLLOW, outputEntity);

        return responseList;
    }

    /**
     * Tạo yêu cầu kết bạn, nếu người gửi chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-05
     * Update Time: 2018-06-26
     * @return the list friends entity
     */
    public List<extFriendContactEntity> requireAddFriends(RequireAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        //Thêm mới bản ghi cho người thao tác
        List<extFriendContactEntity> responseList = execAddFriendsForOwner(true, viewModel, FriendStatusEnum.FOLLOW, outputEntity);

        //Thêm mới bản ghi cho người được mời
        RequireAddFriendsVM viewModelFri = new RequireAddFriendsVM(viewModel.getFriendUsername(), viewModel.getOwnerUsername());
        execAddFriendsForOwner(false, viewModelFri, FriendStatusEnum.REQUEST, outputEntity);

        return responseList;
    }

    /**
     * Chấp nhận yêu cầu kết bạn, nếu người nhận chưa có thông tin thì tạo mới.
     * (Hàm bổ sung)
     * Create Time: 2018-06-08
     * @return the entity
     */
    public List<extFriendContactEntity> acceptAddFriends(ResponseAddFriendsVM viewModel, IsoResponseEntity outputEntity)
    {
        //Cập nhật cho người thao tác
        List<extFriendContactEntity> responseList = execAcceptFriendsForOwner(true, viewModel, FriendStatusEnum.FRIEND, outputEntity);

        //Cập nhật cho người bạn
        ResponseAddFriendsVM viewModelFri = new ResponseAddFriendsVM(viewModel.getFriendUsername(), viewModel.getOwnerUsername());
        execAcceptFriendsForOwner(false, viewModelFri, FriendStatusEnum.FRIEND, outputEntity);

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
        FriendsEntity dbRequireInfo = friendsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
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
                friendsRepository.save(dbRequireInfo);
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
     * Cập nhật thông tin của bạn bè.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the entity
     */
    public List<extFriendContactEntity> updateFriendInfo(UpdateFriendVM viewModel, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> friendLists = new ArrayList<>();
        FriendsEntity dbRequireInfo = friendsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
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
                friendsRepository.save(dbRequireInfo);
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
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAddContactForOwner(UpdateFriendVM viewModel, FriendStatusEnum statusEnum, IsoResponseEntity outputEntity)
    {
        //Kiểm tra nếu chưa có thông tin thì thêm mới
        FriendsEntity dbInfo = friendsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extFriendContactEntity> friendExists = dbInfo.getFriendLists();
            if(friendExists != null && friendExists.size() > 0)
            {
                //Check if it is exists in list
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
                extFriendContactEntity friendItem = new extFriendContactEntity();
                friendItem.setUsername(viewModel.getFriendUsername());
                friendItem.setFullname(viewModel.getFriendFullname());
                friendItem.setCompany(viewModel.getFriendCompany());
                friendItem.setMobile(viewModel.getFriendMobile());
                friendItem.setEmail(viewModel.getFriendEmail());
                friendItem.setStatus(statusEnum);
                friendItem.setInSystem(0);                          //Chỗ này cần kiểm tra xem user có trong hệ thống chưa
                friendExists.add(friendItem);

                dbInfo.setFriendLists(friendExists);
                friendsRepository.save(dbInfo);

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
            List<extFriendContactEntity> friendLists = new ArrayList<>();
            extFriendContactEntity friendItem = new extFriendContactEntity();
            friendItem.setUsername(viewModel.getFriendUsername());
            friendItem.setFullname(viewModel.getFriendFullname());
            friendItem.setCompany(viewModel.getFriendCompany());
            friendItem.setMobile(viewModel.getFriendMobile());
            friendItem.setEmail(viewModel.getFriendEmail());
            friendItem.setStatus(statusEnum);
            friendItem.setInSystem(0);                          //Chỗ này cần kiểm tra xem user có trong hệ thống chưa
            friendLists.add(friendItem);

            FriendsEntity input = new FriendsEntity();
            input.setOwnerUsername(viewModel.getOwnerUsername());
            input.setFriendLists(friendLists);
            input.setGroupLists(new ArrayList<>());
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
            friendsRepository.save(input);

            return friendLists;
        }
    }

    /**
     * Thực hiện kiểm tra và ghi dữ liệu vào DB.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAddFriendsForOwner(boolean isOwnerRequest, RequireAddFriendsVM viewModel, FriendStatusEnum statusEnum, IsoResponseEntity outputEntity)
    {
        List<extFriendContactEntity> requireMemberLists = new ArrayList<>();
        FriendsEntity dbRequireInfo = friendsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
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
                friendsRepository.save(dbRequireInfo);
            }
            else {
                if(isOwnerRequest) {
                    outputEntity.setError(ResponseErrorCode.EXISTS.getValue());
                    outputEntity.setMessage("friends_list_notfound");
                    outputEntity.setException("The friend list is not found!");
                }
                else
                {
                    extFriendContactEntity memberItem = new extFriendContactEntity();
                    memberItem.setUsername(viewModel.getFriendUsername());
                    memberItem.setStatus(statusEnum);
                    requireMemberLists.add(memberItem);

                    dbRequireInfo.setFriendLists(requireMemberLists);
                    dbRequireInfo.setLastModifiedTime(new Date().getTime());
                    friendsRepository.save(dbRequireInfo);
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
                List<extFriendContactEntity> memberLists = new ArrayList<>();
                extFriendContactEntity memberItem = new extFriendContactEntity();
                memberItem.setUsername(viewModel.getFriendUsername());
                memberItem.setStatus(statusEnum);
                memberLists.add(memberItem);

                FriendsEntity input = new FriendsEntity();
                input.setOwnerUsername(viewModel.getOwnerUsername());
                input.setFriendLists(memberLists);
                input.setGroupLists(new ArrayList<>());
                input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());
                friendsRepository.save(input);
            }
        }
        return requireMemberLists;
    }

    /**
     * Thực hiện ghi nhận trạng thái chấp nhận là bạn bè DB.
     * (Hàm bổ sung)
     * Create Time: 2018-06-09
     * Update Time:  2018-06-26
     * @return the list friends entity
     */
    private List<extFriendContactEntity> execAcceptFriendsForOwner(boolean isForOwner, ResponseAddFriendsVM viewModel, FriendStatusEnum statusEnum, IsoResponseEntity outputEntity)
    {
        boolean blItemExists = false;
        List<extFriendContactEntity> requireFriendLists = new ArrayList<>();
        FriendsEntity dbRequireInfo = friendsRepository.findOneByOwnerUsername(viewModel.getOwnerUsername());
        if(dbRequireInfo != null && dbRequireInfo.getId() != null)
        {
            requireFriendLists = dbRequireInfo.getFriendLists();
            for (extFriendContactEntity exists : requireFriendLists) {
                if(isForOwner) {
                    if ((isForOwner && exists.getUsername().equals(viewModel.getFriendUsername()) && exists.getStatus() == FriendStatusEnum.REQUEST)
                        || (!isForOwner && exists.getUsername().equals(viewModel.getFriendUsername()))) {
                        blItemExists = true;
                        exists.setStatus(statusEnum);
                        exists.setLastModifiedTime(new Date().getTime());
                        break;
                    }
                }
            }

            dbRequireInfo.setFriendLists(requireFriendLists);
            dbRequireInfo.setLastModifiedTime(new Date().getTime());
            friendsRepository.save(dbRequireInfo);
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
    //====================================================PRIVATE END===================================================
    //==================================================================================================================
}
