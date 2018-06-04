package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.FriendStatus;
import com.hdmon.chatservice.domain.enumeration.FriendType;

import java.io.Serializable;

public class extFriendMemberLists implements Serializable {
    private Long friendId;
    private FriendType friendType;
    private FriendStatus status;

    public extFriendMemberLists(){
        super();
    }

    public extFriendMemberLists(Long friendId, FriendType friendType, FriendStatus status)
    {
        this.friendId = friendId;
        this.friendType = friendType;
        this.status = status;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public FriendType getFriendType() {
        return friendType;
    }

    public void setFriendType(FriendType friendType) {
        this.friendType = friendType;
    }

    public FriendStatus getStatus()
    {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "extFriendMemberLists{"
            + "FriendId=" + friendId + ","
            + "FriendType=" + friendType + ","
            + "Status=" + status + ","
            + "}";
    }
}
