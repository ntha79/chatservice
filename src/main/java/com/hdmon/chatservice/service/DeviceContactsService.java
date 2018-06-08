package com.hdmon.chatservice.service;

import com.hdmon.chatservice.domain.DeviceContactsEntity;
import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.ContactStatusEnum;
import com.hdmon.chatservice.domain.extents.extDeviceContactEntity;
import com.hdmon.chatservice.repository.DeviceContactsRepository;
import com.hdmon.chatservice.repository.DeviceContactsRepository;
import com.hdmon.chatservice.service.util.DataTypeHelper;
import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;
import com.hdmon.chatservice.web.rest.vm.ContactsVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by UserName on 6/6/2018.
 */
@Service
@Transactional
public class DeviceContactsService {
    private final Logger log = LoggerFactory.getLogger(DeviceContactsService.class);

    private final DeviceContactsRepository deviceContactsRepository;

    public DeviceContactsService(DeviceContactsRepository deviceContactsRepository) {
        this.deviceContactsRepository = deviceContactsRepository;
    }

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save
     * @return the persisted entity
     */
    public DeviceContactsEntity save(DeviceContactsEntity contacts) {
        log.debug("Request to save DeviceContacts : {}", contacts);
        return deviceContactsRepository.save(contacts);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DeviceContactsEntity> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceContacts");
        return deviceContactsRepository.findAll(pageable);
    }

    /**
     * Get one contacts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DeviceContactsEntity findOne(String id) {
        log.debug("Request to get DeviceContacts : {}", id);
        return deviceContactsRepository.findOne(id);
    }

    /**
     * Delete the contacts by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete DeviceContacts : {}", id);
        deviceContactsRepository.delete(id);
    }

    /**
     * Lấy thông tin liên hệ của User.
     *
     * Create Time: 2018-06-05
     * @return the list entity
     */
    @Transactional(readOnly = true)
    public DeviceContactsEntity findOneByownerId(Long ownerId)
    {
        log.debug("Request to get all DeviceContacts by ownerId: {}", ownerId);
        DeviceContactsEntity dbInfo = new DeviceContactsEntity();
        if(ownerId > 0)
        {
            dbInfo = deviceContactsRepository.findOneByownerId(ownerId);
        }
        return dbInfo;
    }

    /**
     * Bổ sung liên hệ vào danh sách đang có của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-06
     * @return the entity
     */
    public DeviceContactsEntity appendContactsForOwnerId(ContactsVM contactsVM, IsoResponseEntity responseEntity)
    {
        log.debug("Append a DeviceContacts for ownerId.: {}", contactsVM);

        //Check exists
        DeviceContactsEntity dbInfo = deviceContactsRepository.findOneByownerId(contactsVM.getOwnerId());
        if(dbInfo != null && dbInfo.getId() != null) {
            boolean isExists = false;
            List<extDeviceContactEntity> memberExists = dbInfo.getContactLists();
            if(memberExists.size() > 0)
            {
                //Check if it is exists in list
                for (extDeviceContactEntity exists : memberExists) {
                    if(exists.getEmail().equals(contactsVM.getEmail()) || exists.getMobile().equals(contactsVM.getMobile()))
                    {
                        isExists = true;
                        break;
                    }
                }
            }

            //insert if it is not exists
            if(!isExists)
            {
                extDeviceContactEntity memberItem = new extDeviceContactEntity();
                memberItem.setFirstName(contactsVM.getFirstName());
                memberItem.setLastName(contactsVM.getLastName());
                memberItem.setCompany(contactsVM.getCompany());
                memberItem.setEmail(contactsVM.getEmail());
                memberItem.setMobile(contactsVM.getMobile());
                memberItem.setStatus(ContactStatusEnum.ADD);
                memberExists.add(memberItem);

                dbInfo.setContactLists(memberExists);
                dbInfo.setContactCount(memberExists.size());
                return deviceContactsRepository.save(dbInfo);
            }
            else {
                responseEntity.setError(ResponseErrorCode.EXISTS.getValue());
                responseEntity.setMessage("devicecontacts_mobile_email_exists");
                responseEntity.setException("This person already exists in list!");
                return dbInfo;
            }
        }
        else
        {
            List<extDeviceContactEntity> memberLists = new ArrayList<>();
            extDeviceContactEntity memberItem = new extDeviceContactEntity();
            memberItem.setFirstName(contactsVM.getFirstName());
            memberItem.setLastName(contactsVM.getLastName());
            memberItem.setCompany(contactsVM.getCompany());
            memberItem.setEmail(contactsVM.getEmail());
            memberItem.setMobile(contactsVM.getMobile());
            memberItem.setStatus(ContactStatusEnum.ADD);
            memberLists.add(memberItem);

            DeviceContactsEntity input = new DeviceContactsEntity();
            input.setOwnerId(contactsVM.getOwnerId());
            input.setOwnerLogin(contactsVM.getOwnerLogin());
            input.setContactLists(memberLists);
            input.setContactCount(memberLists.size());
            input.setReportDay(DataTypeHelper.ConvertDateTimeToReportDay());

            return deviceContactsRepository.insert(input);
        }
    }

    /**
     * Sửa liên hệ đã tồn tại trong danh sách đang của User.
     * (Hàm bổ sung)
     * Create Time: 2018-06-06
     * @return the entity
     */
    public DeviceContactsEntity updateContactsForOwnerId(ContactsVM contactsVM, IsoResponseEntity responseEntity)
    {
        log.debug("Update a DeviceContacts for ownerId.: {}", contactsVM);

        //Check exists
        boolean isExists = false;
        DeviceContactsEntity dbInfo = deviceContactsRepository.findOneByownerId(contactsVM.getOwnerId());
        if(dbInfo != null && dbInfo.getId() != null) {
            List<extDeviceContactEntity> memberExists = dbInfo.getContactLists();
            if(memberExists.size() > 0)
            {
                for (extDeviceContactEntity exists : memberExists) {
                    if(exists.getEmail().equals(contactsVM.getEmail()) && exists.getMobile().equals(contactsVM.getMobile()))
                    {
                        //Cập nhật lại thông tin
                        exists.setFirstName(contactsVM.getFirstName());
                        exists.setLastName(contactsVM.getLastName());
                        exists.setCompany(contactsVM.getCompany());

                        isExists = true;
                        break;
                    }
                }
            }

            //insert if it is not exists
            if(isExists)
            {
                dbInfo.setContactLists(memberExists);
                dbInfo.setLastModifiedUnixTime(new Date().getTime());
                return deviceContactsRepository.save(dbInfo);
            }
        }

        //Trường hợp không tìm thấy
        if(!isExists)
        {
            responseEntity.setError(ResponseErrorCode.NOTFOUND.getValue());                                     //notfound
            responseEntity.setMessage("devicecontacts_notfound_error");
            responseEntity.setException("The record is not found!");
        }
        return dbInfo;
    }
}
