package com.hdmon.chatservice.web.rest.vm;

/**
 * Created by UserName on 6/5/2018.
 */
public class ContactsVM {
    private Long ownerId;
    private String ownerLogin;

    private String email;
    private String mobile;
    private String lastName;
    private String firstName;
    private String company;

    public ContactsVM(ContactsVM contactsVM) {
        this.ownerId = contactsVM.getOwnerId();
        this.ownerLogin = contactsVM.getOwnerLogin();
        this.email = contactsVM.getEmail();
        this.mobile = contactsVM.getMobile();
        this.lastName = contactsVM.getLastName();
        this.firstName = contactsVM.getFirstName();
    }

    public ContactsVM() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "ContactsVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", OwnerLogin='" + getOwnerLogin() + '\'' +
            ", Email='" + getEmail() + '\'' +
            ", Mobile='" + getMobile() + '\'' +
            ", FirstName='" + getFirstName() + '\'' +
            ", LastName='" + getLastName() + '\'' +
            '}';
    }
}
