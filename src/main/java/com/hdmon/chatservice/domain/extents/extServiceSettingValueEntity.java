package com.hdmon.chatservice.domain.extents;

import java.io.Serializable;

public class extServiceSettingValueEntity implements Serializable {
    private Long maxTimeToAction;
    private Long maxMember;
    private boolean allowDelete;
    private boolean allowEdit;
    private boolean allowBlock;
    private boolean allowRemove;
    private boolean allowSendMsgAfterBlock;
    private boolean allowLeave;

    public extServiceSettingValueEntity()
    {
        super();
    }

    public extServiceSettingValueEntity(Long maxTimeToAction, Long maxMember, boolean allowDelete, boolean allowEdit, boolean allowBlock, boolean allowRemove, boolean allowSendMsgAfterBlock, boolean allowLeave)
    {
        this.maxTimeToAction = maxTimeToAction;
        this.maxMember = maxMember;
        this.allowDelete = allowDelete;
        this.allowEdit = allowEdit;
        this.allowBlock = allowBlock;
        this.allowRemove = allowRemove;
        this.allowSendMsgAfterBlock = allowSendMsgAfterBlock;
        this.allowLeave = allowLeave;
    }

    public Long getMaxTimeToAction() {
        return maxTimeToAction;
    }

    public void setMaxTimeToAction(Long maxTimeToAction) {
        this.maxTimeToAction = maxTimeToAction;
    }

    public Long getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Long maxMember) {
        this.maxMember = maxMember;
    }

    public boolean isAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public boolean isAllowBlock() {
        return allowBlock;
    }

    public void setAllowBlock(boolean allowBlock) {
        this.allowBlock = allowBlock;
    }

    public boolean isAllowRemove() {
        return allowRemove;
    }

    public void setAllowRemove(boolean allowRemove) {
        this.allowRemove = allowRemove;
    }

    public boolean isAllowLeave() {
        return allowLeave;
    }

    public void setAllowLeave(boolean allowLeave) {
        this.allowLeave = allowLeave;
    }

    public boolean isAllowSendMsgAfterBlock() {
        return allowSendMsgAfterBlock;
    }

    public void setAllowSendMsgAfterBlock(boolean allowSendMsgAfterBlock) {
        this.allowSendMsgAfterBlock = allowSendMsgAfterBlock;
    }

    @Override
    public String toString() {
        return "extServiceSettingValueEntity{"
            + "MaxTimeToAction=" + getMaxTimeToAction() + ","
            + "MaxMember=" + getMaxMember() + ","
            + "AllowDelete=" + isAllowDelete() + ","
            + "AllowEdit=" + isAllowEdit() + ","
            + "AllowBlock=" + isAllowBlock() + ","
            + "AllowRemove=" + isAllowRemove() + ","
            + "AllowSendMsgAfterBlock=" + isAllowSendMsgAfterBlock() + ","
            + "AllowLeave=" + isAllowLeave() + ","
            + "}";
    }
}
