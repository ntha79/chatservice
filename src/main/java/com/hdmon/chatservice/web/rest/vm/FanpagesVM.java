package com.hdmon.chatservice.web.rest.vm;

/**
 * Created by UserName on 6/6/2018.
 */
public class FanpagesVM {
    private Long ownerId;
    private String ownerLogin;

    private String id;
    private String fanName;
    private String fanUrl = "";
    private String fanAbout;
    private String fanIcon  = "";
    private String fanThumbnail  = "";

    public FanpagesVM(FanpagesVM fanpagesVM) {
        this.ownerId = fanpagesVM.getOwnerId();
        this.ownerLogin = fanpagesVM.getOwnerLogin();
        this.id = fanpagesVM.getId();
        this.fanName = fanpagesVM.getFanName();
        this.fanUrl = fanpagesVM.getFanUrl();
        this.fanAbout = fanpagesVM.getFanAbout();
        this.fanIcon = fanpagesVM.getFanIcon();
        this.fanThumbnail = fanpagesVM.getFanThumbnail();
    }

    public FanpagesVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin)
    {
        this.ownerLogin = ownerLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFanName() {
        return fanName;
    }

    public void setFanName(String fanName) {
        this.fanName = fanName;
    }

    public String getFanUrl() {
        return fanUrl;
    }

    public void setFanUrl(String fanUrl) {
        this.fanUrl = fanUrl;
    }

    public String getFanAbout() {
        return fanAbout;
    }

    public void setFanAbout(String fanAbout) {
        this.fanAbout = fanAbout;
    }

    public String getFanIcon() {
        return fanIcon;
    }

    public void setFanIcon(String fanIcon) {
        this.fanIcon = fanIcon;
    }

    public String getFanThumbnail() {
        return fanThumbnail;
    }

    public void setFanThumbnail(String fanThumbnail) {
        this.fanThumbnail = fanThumbnail;
    }

    @Override
    public String toString() {
        return "FanpagesVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", OwnerLogin='" + getOwnerLogin() + '\'' +
            ", FanName='" + getFanName() + '\'' +
            ", FanUrl='" + getFanUrl() + '\'' +
            ", FanAbout='" + getFanAbout() + '\'' +
            ", FanIcon='" + getFanIcon() + '\'' +
            ", FanThumbnail='" + getFanThumbnail() + '\'' +
            '}';
    }
}
