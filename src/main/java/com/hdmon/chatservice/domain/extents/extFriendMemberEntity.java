package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;

import java.io.Serializable;
import java.util.Date;

public class extFriendMemberEntity implements Serializable {
    private String chatRoomChatId;
    private Long friendId;
    private String friendLogin;
    private String friendName;
    private FriendStatusEnum status = FriendStatusEnum.FOLLOW;
    private Long lastModifiedUnixTime = new Date().getTime();

    public extFriendMemberEntity(){
        super();
    }

    public extFriendMemberEntity(String chatRoomChatId, Long friendId, String friendLogin, String friendName, FriendStatusEnum status, Long lastModifiedUnixTime)
    {
        this.chatRoomChatId = chatRoomChatId;
        this.friendId = friendId;
        this.friendLogin = friendLogin;
        this.friendName = friendName;
        this.lastModifiedUnixTime = lastModifiedUnixTime;
        this.status = status;
    }

    public String getChatRoomChatId() {
        return chatRoomChatId;
    }

    public void setChatRoomChatId(String chatRoomChatId) {
        this.chatRoomChatId = chatRoomChatId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendLogin(){
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

    public FriendStatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(FriendStatusEnum status) {
        this.status = status;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    @Override
    public String toString() {
        return "extFriendMemberEntity{"
            + "ChatRoomChatId=" + getChatRoomChatId() + ","
            + "FriendId=" + getFriendId() + ","
            + "FriendLogin=" + getFriendLogin() + ","
            + "FriendName=" + getFriendName() + ","
            + "Status=" + getStatus() + ","
            + "LastModifiedUnixTime=" + getLastModifiedUnixTime() + ","
            + "}";
    }
}
