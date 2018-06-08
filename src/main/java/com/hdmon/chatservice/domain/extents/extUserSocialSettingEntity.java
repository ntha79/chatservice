package com.hdmon.chatservice.domain.extents;

public class extUserSocialSettingEntity
{
    private String groupKey;
    private String properties;

    public extUserSocialSettingEntity()
    {
        super();
    }

    public extUserSocialSettingEntity(String partnerId, String properties)
    {
        this.groupKey = groupKey;
        this.properties = properties;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "extUserSocialSettingEntity{"
            + "GroupKey=" + getGroupKey() + ","
            + "Properties=" + getProperties() + ","
            + "}";
    }
}
