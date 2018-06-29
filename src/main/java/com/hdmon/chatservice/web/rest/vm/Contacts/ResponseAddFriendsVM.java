package com.hdmon.chatservice.web.rest.vm.Contacts;

import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;

/**
 * Created by UserName on 6/9/2018.
 */
public class ResponseAddFriendsVM {
    private String ownerUsername;
    private String friendUsername;

    public ResponseAddFriendsVM(ResponseAddFriendsVM viewModel) {
        this.ownerUsername = viewModel.getOwnerUsername();
        this.friendUsername = viewModel.getFriendUsername();
    }

    public ResponseAddFriendsVM(String ownerUsername, String friendUsername) {
        this.ownerUsername = ownerUsername;
        this.friendUsername = friendUsername;
    }

    public ResponseAddFriendsVM() {
        // Empty public constructor used by Jackson.
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public ResponseAddFriendsVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public ResponseAddFriendsVM friendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
        return this;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    @Override
    public String toString() {
        return "ResponseAddFriendsVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", FriendUsername='" + getFriendUsername() + '\'' +
            '}';
    }
}
