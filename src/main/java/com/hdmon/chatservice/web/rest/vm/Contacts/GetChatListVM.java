package com.hdmon.chatservice.web.rest.vm.Contacts;

import java.util.List;

/**
 * Created by UserName on 6/29/2018.
 */
public class GetChatListVM {
    private List<FriendInfoVM> listFriends;
    private List<GroupInfoVM> listGroups;

    public GetChatListVM()
    {
        super();
    }

    public GetChatListVM(GetChatListVM getChatListVM)
    {
        this.listFriends = getChatListVM.getListFriends();
        this.listGroups = getChatListVM.getListGroups();
    }

    public GetChatListVM(List<FriendInfoVM> listFriends, List<GroupInfoVM> listGroups)
    {
        this.listFriends = listFriends;
        this.listGroups = listGroups;
    }

    public List<FriendInfoVM> getListFriends() {
        return listFriends;
    }

    public GetChatListVM listFriends(List<FriendInfoVM> listFriends) {
        this.listFriends = listFriends;
        return this;
    }

    public void setListFriends(List<FriendInfoVM> listFriends) {
        this.listFriends = listFriends;
    }

    public List<GroupInfoVM> getListGroups() {
        return listGroups;
    }

    public GetChatListVM listGroups(List<GroupInfoVM> listGroups) {
        this.listGroups = listGroups;
        return this;
    }

    public void setListGroups(List<GroupInfoVM> listGroups) {
        this.listGroups = listGroups;
    }

    @Override
    public String toString() {
        return "GetChatListVM{" +
            "ListFriends='" + getListFriends() + '\'' +
            ", ListGroups='" + getListGroups() + '\'' +
            '}';
    }
}
