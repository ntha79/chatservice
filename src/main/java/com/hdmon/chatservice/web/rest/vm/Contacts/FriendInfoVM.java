package com.hdmon.chatservice.web.rest.vm.Contacts;

import com.hdmon.chatservice.domain.enumeration.FriendStatusEnum;

/**
 * Created by UserName on 6/29/2018.
 */
public class FriendInfoVM {
    private String username;
    private String fullname;
    private String mobile;
    private String email;
    private String imageUrl;
    private FriendStatusEnum status;

    public FriendInfoVM()
    {
        super();
    }

    public FriendInfoVM(FriendInfoVM friendInfoVM)
    {
        this.username = friendInfoVM.getUsername();
        this.fullname = friendInfoVM.getFullname();
        this.mobile = friendInfoVM.getMobile();
        this.email = friendInfoVM.getEmail();
        this.imageUrl = friendInfoVM.getImageUrl();
        this.status = friendInfoVM.getStatus();
    }

    public FriendInfoVM(String username, String fullname, String mobile, String email, String imageUrl, FriendStatusEnum status)
    {
        this.username = username;
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public FriendInfoVM username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public FriendInfoVM fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public FriendInfoVM mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public FriendInfoVM email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FriendInfoVM imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public FriendStatusEnum getStatus() {
        return status;
    }

    public FriendInfoVM status(FriendStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(FriendStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendInfoVM{" +
            "Username='" + getUsername() + '\'' +
            ", Fullname='" + getFullname() + '\'' +
            ", Mobile='" + getMobile() + '\'' +
            ", Email='" + getEmail() + '\'' +
            ", ImageUrl='" + getImageUrl() + '\'' +
            ", Status='" + getStatus() + '\'' +
            '}';
    }
}
