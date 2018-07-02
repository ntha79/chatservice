package com.hdmon.chatservice.web.rest.vm.Contacts;

/**
 * Created by UserName on 6/9/2018.
 * For Rest API: /friends/addfriends
 */
public class UpdateContactVM {
    private String ownerUsername;
    private String friendUsername;
    private String friendFullname;
    private String friendCompany;
    private String friendMobile;
    private String friendEmail;
    //private String friendStatus;

    public UpdateContactVM(UpdateContactVM viewModel) {
        this.ownerUsername = viewModel.getOwnerUsername();
        this.friendUsername = viewModel.getFriendUsername();
        this.friendFullname = viewModel.getFriendFullname();
        this.friendCompany = viewModel.getFriendCompany();
        this.friendMobile = viewModel.getFriendMobile();
        this.friendEmail = viewModel.getFriendEmail();
        //this.friendStatus = viewModel.getFriendStatus();
    }

    public UpdateContactVM(String ownerUsername, String friendUsername, String friendFullname, String friendCompany, String friendMobile, String friendEmail) {
        this.ownerUsername = ownerUsername;
        this.friendUsername = friendUsername;
        this.friendFullname = friendFullname;
        this.friendCompany = friendCompany;
        this.friendMobile = friendMobile;
        this.friendEmail = friendEmail;
        //this.friendStatus = friendStatus;
    }

    public UpdateContactVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getOwnerUsername(){
        return ownerUsername;
    }

    public UpdateContactVM ownerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public UpdateContactVM friendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
        return this;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendFullname() {
        return friendFullname;
    }

    public UpdateContactVM friendFullname(String friendFullname) {
        this.friendFullname = friendFullname;
        return this;
    }

    public void setFriendFullname(String friendFullname) {
        this.friendFullname = friendFullname;
    }

    public String getFriendCompany() {
        return friendCompany;
    }

    public UpdateContactVM friendCompany(String friendCompany) {
        this.friendCompany = friendCompany;
        return this;
    }

    public void setFriendCompany(String friendCompany) {
        this.friendCompany = friendCompany;
    }

    public String getFriendMobile() {
        return friendMobile;
    }

    public UpdateContactVM friendMobile(String friendMobile) {
        this.friendMobile = friendMobile;
        return this;
    }

    public void setFriendMobile(String friendMobile) {
        this.friendMobile = friendMobile;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public UpdateContactVM friendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
        return this;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

//    public String getFriendStatus() {
//        return friendStatus;
//    }
//
//    public UpdateFriendVM friendStatus(String friendStatus) {
//        this.friendStatus = friendStatus;
//        return this;
//    }
//
//    public void setFriendStatus(String friendStatus) {
//        this.friendStatus = friendStatus;
//    }

    @Override
    public String toString() {
        return "UpdateContactVM{" +
            "OwnerUsername='" + getOwnerUsername() + '\'' +
            ", FriendUsername='" + getFriendUsername() + '\'' +
            ", friendFullname='" + getFriendFullname() + '\'' +
            ", friendCompany='" + getFriendCompany() + '\'' +
            ", friendMobile='" + getFriendMobile() + '\'' +
            ", friendEmail='" + getFriendEmail() + '\'' +
            //", friendStatus='" + getFriendStatus() + '\'' +
            '}';
    }
}
