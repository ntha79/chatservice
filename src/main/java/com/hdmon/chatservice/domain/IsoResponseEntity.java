package com.hdmon.chatservice.domain;

import com.hdmon.chatservice.web.rest.errors.ResponseErrorCode;

import java.io.Serializable;

/**
 * Created by UserName on 6/5/2018.
 */
public class IsoResponseEntity implements Serializable {
    private int error = ResponseErrorCode.UNKNOW_ERROR.getValue();         //no action
    private Object data = null;                                            //no action
    private String message = "";                                           //no action
    private String exception = "";                                         //no action

    public IsoResponseEntity()
    {
        super();
    }

    public IsoResponseEntity(int error, Object data, String message, String exception)
    {
        this.error = error;
        this.data = data;
        this.message = message;
        this.exception = exception;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "IsoResponseEntity{"
            + "error=" + error + ","
            + "data=" + data + ","
            + "message=" + message + ","
            + "exception=" + exception + ","
            + "}";
    }
}
