package com.hdmon.chatservice.web.rest.vm.Contacts;

import java.util.List;

/**
 * Created by UserName on 6/29/2018.
 */
public class DeviceContactInfoVM {
    private String fullname;
    private String mobile;
    private String email;
    private String company;
    private String imageUrl;

    public DeviceContactInfoVM()
    {
        super();
    }

    public DeviceContactInfoVM(String fullname, String mobile, String email, String company, String imageUrl)
    {
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.company = company;
        this.imageUrl = imageUrl;
    }

    public String getFullname() {
        return fullname;
    }

    public DeviceContactInfoVM fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public DeviceContactInfoVM mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public DeviceContactInfoVM email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public DeviceContactInfoVM company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public DeviceContactInfoVM imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "DeviceContactInfoVM{" +
            "Fullname='" + getFullname() + '\'' +
            ", Mobile='" + getMobile() + '\'' +
            ", Email='" + getEmail() + '\'' +
            ", Company='" + getCompany() + '\'' +
            ", ImageUrl='" + getImageUrl() + '\'' +
            '}';
    }
}
