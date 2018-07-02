package com.hdmon.chatservice.web.rest.vm.Messages;

import com.hdmon.chatservice.domain.enumeration.ChatMessageTypeEnum;

/**
 * Created by UserName on 6/30/2018.
 */
public class ActionMessageVM {
    private String msgId;
    private String actionUserName;

    public ActionMessageVM()
    {
        super();
    }

    public ActionMessageVM(ActionMessageVM entity)
    {
        this.msgId = entity.getMsgId();
        this.actionUserName = entity.getActionUserName();
    }

    public ActionMessageVM(String msgId, String actionUserName)
    {
        this.msgId = msgId;
        this.actionUserName = actionUserName;
    }

    public String getMsgId() {
        return msgId;
    }

    public ActionMessageVM msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public ActionMessageVM actionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
        return this;
    }

    @Override
    public String toString() {
        return "ActionMessageVM{" +
            "MsgId='" + getMsgId() + '\'' +
            ", ActionUserName='" + getActionUserName() + '\'' +
            '}';
    }
}
