package com.hdmon.chatservice.web.rest.vm.Contacts;

import java.util.List;

/**
 * Created by UserName on 6/29/2018.
 */
public class GetContactListVM {
    private List<FriendInfoVM> listFriends;
    private List<GroupInfoVM> listGroups;
    private List<DeviceContactInfoVM> listDeviceContacts;

    public GetContactListVM()
    {
        super();
    }

    public GetContactListVM(List<FriendInfoVM> listFriends, List<GroupInfoVM> listGroups, List<DeviceContactInfoVM> listDeviceContacts)
    {
        this.listFriends = listFriends;
        this.listGroups = listGroups;
        this.listDeviceContacts = listDeviceContacts;
    }

    public List<FriendInfoVM> getListFriends() {
        return listFriends;
    }

    public GetContactListVM listFriends(List<FriendInfoVM> listFriends) {
        this.listFriends = listFriends;
        return this;
    }

    public void setListFriends(List<FriendInfoVM> listFriends) {
        this.listFriends = listFriends;
    }

    public List<GroupInfoVM> getListGroups() {
        return listGroups;
    }

    public GetContactListVM listGroups(List<GroupInfoVM> listGroups) {
        this.listGroups = listGroups;
        return this;
    }

    public void setListGroups(List<GroupInfoVM> listGroups) {
        this.listGroups = listGroups;
    }

    public List<DeviceContactInfoVM> getListDeviceContacts() {
        return listDeviceContacts;
    }

    public GetContactListVM listDeviceContacts(List<DeviceContactInfoVM> listDeviceContacts) {
        this.listDeviceContacts = listDeviceContacts;
        return this;
    }

    public void setListDeviceContacts(List<DeviceContactInfoVM> listDeviceContacts) {
        this.listDeviceContacts = listDeviceContacts;
    }

    @Override
    public String toString() {
        return "GetContactListVM{" +
            "ListFriends='" + getListFriends() + '\'' +
            ", ListGroups='" + getListGroups() + '\'' +
            ", ListDeviceContacts='" + getListDeviceContacts() + '\'' +
            '}';
    }
}
