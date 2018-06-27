package com.hdmon.chatservice.web.rest.vm.Groups;

import com.hdmon.chatservice.domain.enumeration.GroupTypeEnum;
import com.hdmon.chatservice.web.rest.vm.GroupMembersVM;
import com.hdmon.chatservice.web.rest.vm.MembersVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 6/9/2018.
 */
public class ActionGroupVM {
    private Long ownerId;
    private String groupId;
    private List<MembersActionGroupVM> listMembers = new ArrayList<>();


    public ActionGroupVM(ActionGroupVM actionGroupVM) {
        this.ownerId = actionGroupVM.getOwnerId();
        this.groupId = actionGroupVM.getGroupId();
        this.listMembers = actionGroupVM.getListMembers();
    }

    public ActionGroupVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<MembersActionGroupVM> getListMembers() {
        return listMembers;
    }

    public void setListMembers(List<MembersActionGroupVM> listMembers) {
        this.listMembers = listMembers;
    }

    @Override
    public String toString() {
        return "ActionGroupVM{" +
            "OwnerId='" + getOwnerId() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", ListMembers='" + getListMembers() + '\'' +
            '}';
    }
}
