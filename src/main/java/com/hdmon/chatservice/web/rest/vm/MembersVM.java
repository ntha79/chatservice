package com.hdmon.chatservice.web.rest.vm;

/**
 * Created by UserName on 6/7/2018.
 */
public class MembersVM {
    private Long memberId;
    private String memberLogin;

    private String sourceId;

    public MembersVM(MembersVM membersVM) {
        this.memberId = membersVM.getMemberId();
        this.memberLogin = membersVM.getMemberLogin();
        this.sourceId = membersVM.getSourceId();
    }

    public MembersVM() {
        // Empty public constructor used by Jackson.
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberLogin() {
        return memberLogin;
    }

    public void setMemberLogin(String memberLogin)
    {
        this.memberLogin = memberLogin;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setParentId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "MembersVM{" +
            "MemberId='" + getMemberId() + '\'' +
            ", MemberLogin='" + getMemberLogin() + '\'' +
            ", SourceId='" + getSourceId() + '\'' +
            '}';
    }
}
