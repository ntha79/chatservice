package com.hdmon.chatservice.web.rest.vm.Groups;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 6/9/2018.
 */
public class ActionGroupVM {
    private String actionUsername;
    private String groupId;
    private List<MembersActionGroupVM> listMembers = new ArrayList<>();

    public ActionGroupVM(ActionGroupVM actionGroupVM) {
        this.actionUsername = actionGroupVM.getActionUsername();
        this.groupId = actionGroupVM.getGroupId();
        this.listMembers = actionGroupVM.getListMembers();
    }

    public ActionGroupVM(String actionUsername, String groupId, List<MembersActionGroupVM> listMembers) {
        this.actionUsername = actionUsername;
        this.groupId = groupId;
        this.listMembers = listMembers;
    }

    public ActionGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getActionUsername() {
        return actionUsername;
    }

    public ActionGroupVM actionUsername(String actionUsername) {
        this.actionUsername = actionUsername;
        return this;
    }

    public void setActionUsername(String actionUsername) {
        this.actionUsername = actionUsername;
    }

    public String getGroupId() {
        return groupId;
    }

    public ActionGroupVM groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<MembersActionGroupVM> getListMembers() {
        return listMembers;
    }

    public ActionGroupVM listMembers(List<MembersActionGroupVM> listMembers) {
        this.listMembers = listMembers;
        return this;
    }

    public void setListMembers(List<MembersActionGroupVM> listMembers) {
        this.listMembers = listMembers;
    }

    @Override
    public String toString() {
        return "ActionGroupVM{" +
            "ActionUsername='" + getActionUsername() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", ListMembers='" + getListMembers() + '\'' +
            '}';
    }
}
