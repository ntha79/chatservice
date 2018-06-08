package com.hdmon.chatservice.domain.extents;

import com.hdmon.chatservice.domain.enumeration.ContactStatusEnum;

/**
 * Created by UserName on 6/7/2018.
 */
public class extContactGroupEntity {
    private String id;
    private String name;

    public extContactGroupEntity(){
        super();
    }

    public extContactGroupEntity(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "extContactGroupEntity{"
            + "Id=" + getId() + ","
            + "Name=" + getName() + ","
            + "}";
    }
}
