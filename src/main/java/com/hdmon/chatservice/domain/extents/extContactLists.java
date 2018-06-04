package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.ContactStatus;

import java.io.Serializable;

public class extContactLists implements Serializable {
    private String firstName;
    private String lastName;
    private String company;
    private String mobile;
    private String email;
    private ContactStatus status;
    private Long createUnixTime;

    public extContactLists(){
        super();
    }

    public extContactLists(String firstName, String lastName, String company, String mobile, String email, ContactStatus status, Long createUnixTime)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.mobile = mobile;
        this.email = email;
        this.status = status;
        this.createUnixTime = createUnixTime;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactStatus getStatus() {
        return status;
    }

    public void setStatus(ContactStatus status) {
        this.status = status;
    }

    public Long getCreateUnixTime() {
        return createUnixTime;
    }

    public void setCreateUnixTime(Long createUnixTime) {
        this.createUnixTime = createUnixTime;
    }

    @Override
    public String toString() {
        return "extContactLists{"
            + "FirstName=" + firstName + ","
            + "LastName=" + lastName + ","
            + "Company=" + company + ","
            + "Mobile=" + mobile + ","
            + "Email=" + email + ","
            + "Status=" + status + ","
            + "CreateUnixTime=" + createUnixTime + ","
            + "}";
    }
}
