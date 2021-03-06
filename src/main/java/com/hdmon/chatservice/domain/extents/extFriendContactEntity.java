package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;

import java.io.Serializable;
import java.util.Date;

public class extFriendContactEntity implements Serializable {
    private Long userId;
    private String username;
    private String fullname;
    private String company;
    private String mobile;
    private String email;
    private FriendStatusEnum status = FriendStatusEnum.UNKNOW;
    private Long createdTime = new Date().getTime();
    private Long lastModifiedTime = new Date().getTime();
    private Integer inSystem = 0;
    private String imageUrl = "";

    public extFriendContactEntity(){
        super();
    }

    public extFriendContactEntity(Long userId, String username, String fullname, String company, String mobile, String email, FriendStatusEnum status, Long createdTime, Long lastModifiedTime, Integer inSystem)
    {
        this.userId = userId;
        this.username = username;
        this.fullname = fullname;
        this.company = company;
        this.mobile = mobile;
        this.email = email;
        this.status = status;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
        this.inSystem = inSystem;
    }

    public Long getUserId() {
        return userId;
    }

    public extFriendContactEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public extFriendContactEntity username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public extFriendContactEntity fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompany() {
        return company;
    }

    public extFriendContactEntity company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobile() {
        return mobile;
    }

    public extFriendContactEntity mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public extFriendContactEntity email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FriendStatusEnum getStatus() {
        return status;
    }

    public extFriendContactEntity status(FriendStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(FriendStatusEnum status) {
        this.status = status;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public extFriendContactEntity createdTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public extFriendContactEntity lastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getInSystem() {
        return inSystem;
    }

    public extFriendContactEntity inSystem(Integer inSystem) {
        this.inSystem = inSystem;
        return this;
    }

    public void setInSystem(Integer inSystem) {
        this.inSystem = inSystem;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public extFriendContactEntity imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "extFriendContactEntity{"
            + "Username=" + getUsername() + ","
            + "Fullname=" + getFullname() + ","
            + "Company=" + getCompany() + ","
            + "Mobile=" + getMobile() + ","
            + "Email=" + getEmail() + ","
            + "Status=" + getStatus() + ","
            + "CreatedTime=" + getCreatedTime() + ","
            + "LastModifiedTime=" + getLastModifiedTime() + ","
            + "InSystem=" + getInSystem() + ","
            + "ImageUrl=" + getImageUrl() + ","
            + "}";
    }
}
