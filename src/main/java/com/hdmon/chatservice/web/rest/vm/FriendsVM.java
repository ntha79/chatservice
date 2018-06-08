package com.hdmon.chatservice.web.rest.vm;

import ch.qos.logback.classic.Logger;

/**
 * Created by UserName on 6/5/2018.
 */
public class FriendsVM {
    private Long ownerId;
    private String ownerLogin;

    private Long friendId;
    private String friendLogin;
    private String friendName;

    public FriendsVM(FriendsVM friendsVM) {
        this.ownerId = friendsVM.getOwnerId();
        this.ownerLogin = friendsVM.getOwnerLogin();
        this.friendId = friendsVM.getFriendId();
        this.friendLogin = friendsVM.getFriendLogin();
        this.friendName = friendName;
    }

    public FriendsVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setName(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendLogin() {
        return friendLogin;
    }

    public void setFriendLogin(String friendLogin) {
        this.friendLogin = friendLogin;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "FriendsVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", OwnerLogin='" + getOwnerLogin() + '\'' +
            ", FriendId='" + getFriendId() + '\'' +
            ", FriendLogin='" + getFriendLogin() + '\'' +
            ", FriendName='" + getFriendName() + '\'' +
            '}';
    }
}
