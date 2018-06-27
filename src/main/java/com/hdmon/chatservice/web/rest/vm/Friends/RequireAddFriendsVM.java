package com.hdmon.chatservice.web.rest.vm.Friends;

import com.hdmon.chatservice.domain.FriendsEntity;

/**
 * Created by UserName on 6/9/2018.
 * For Rest API: /friends/requireaddfriends
 */
public class RequireAddFriendsVM {
    private String ownerUsername;
    private String friendUsername;

    public RequireAddFriendsVM(RequireAddFriendsVM viewModel) {
        this.ownerUsername = viewModel.getOwnerUsername();
        this.friendUsername = viewModel.getFriendUsername();
    }

    public RequireAddFriendsVM(String ownerUsername, String friendUsername) {
        this.ownerUsername = ownerUsername;
        this.friendUsername = friendUsername;
    }

    public RequireAddFriendsVM() {
        // Empty public constructor used by Jackson.
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public RequireAddFriendsVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public RequireAddFriendsVM friendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
        return this;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    @Override
    public String toString() {
        return "RequireAddFriendsVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", FriendUsername='" + getFriendUsername() +
            '}';
    }
}
