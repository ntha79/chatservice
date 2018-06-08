package com.hdmon.chatservice.domain.responses;

import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;

/**
 * Created by UserName on 6/7/2018.
 */
public class resFriendItemInfo {
    private Long friendId;
    private String friendName;
    private FriendStatusEnum friendStatus;
    private String friendIcon;

    public resFriendItemInfo(){
        super();
    }

    public resFriendItemInfo(Long friendId, String friendName, FriendStatusEnum friendStatus, String friendIcon)
    {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendStatus = friendStatus;
        this.friendIcon = friendIcon;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public FriendStatusEnum getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(FriendStatusEnum friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getFriendIcon() {
        return friendIcon;
    }

    public void setFriendIcon(String friendIcon) {
        this.friendIcon = friendIcon;
    }

    @Override
    public String toString() {
        return "resFriendItemInfo{"
            + "FriendId=" + getFriendId() + ","
            + "FriendName=" + getFriendName() + ","
            + "FriendStatus=" + getFriendStatus() + ","
            + "FriendIcon=" + getFriendIcon() + ","
            + "}";
    }
}
