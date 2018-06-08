package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.ContactStatusEnum;

import java.io.Serializable;
import java.util.Date;

public class extDeviceContactEntity implements Serializable {
    private String firstName;
    private String lastName;
    private String company;
    private String mobile;
    private String email;
    private ContactStatusEnum status;
    private Long createUnixTime =  new Date().getTime();

    public extDeviceContactEntity(){
        super();
    }

    public extDeviceContactEntity(String firstName, String lastName, String company, String mobile, String email, ContactStatusEnum status, Long createUnixTime)
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

    public ContactStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ContactStatusEnum status) {
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
        return "extContactEntity{"
            + "FirstName=" + getFirstName() + ","
            + "LastName=" + getLastName() + ","
            + "Company=" + getCompany() + ","
            + "Mobile=" + getMobile() + ","
            + "Email=" + getEmail() + ","
            + "Status=" + getStatus() + ","
            + "CreateUnixTime=" + getCreateUnixTime() + ","
            + "}";
    }
}
