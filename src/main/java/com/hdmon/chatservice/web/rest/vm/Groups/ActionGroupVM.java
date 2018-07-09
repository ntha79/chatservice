package com.hdmon.chatservice.web.rest.vm.Groups;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UserName on 6/9/2018.
 */
public class ActionGroupVM {
    private String userLogin;
    private String groupId;
    private List<MembersActionGroupVM> listMembers = new ArrayList<>();

    public ActionGroupVM(ActionGroupVM actionGroupVM) {
        this.userLogin = actionGroupVM.getUserLogin();
        this.groupId = actionGroupVM.getGroupId();
        this.listMembers = actionGroupVM.getListMembers();
    }

    public ActionGroupVM(String userLogin, String groupId, List<MembersActionGroupVM> listMembers) {
        this.userLogin = userLogin;
        this.groupId = groupId;
        this.listMembers = listMembers;
    }

    public ActionGroupVM() {
        // Empty public constructor used by Jackson.
        super();
    }

    public String getUserLogin() {
        return userLogin;
    }

    public ActionGroupVM userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
            "UserLogin='" + getUserLogin() + '\'' +
            ", GroupId='" + getGroupId() + '\'' +
            ", ListMembers='" + getListMembers() + '\'' +
            '}';
    }
}
