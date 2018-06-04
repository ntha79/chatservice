package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.FriendType;

public class extUserSocialSettings
{
    private String partnerId;
    private FriendType partnerType;
    private String properties;

    public extUserSocialSettings()
    {
        super();
    }

    public extUserSocialSettings(String partnerId,FriendType partnerType,String properties)
    {
        this.partnerId = partnerId;
        this.partnerType = partnerType;
        this.properties = properties;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public FriendType getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(FriendType partnerType) {
        this.partnerType = partnerType;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
