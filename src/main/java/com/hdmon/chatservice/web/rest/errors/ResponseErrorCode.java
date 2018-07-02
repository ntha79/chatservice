package com.hdmon.chatservice.web.rest.errors;

/**
 * Created by UserName on 6/7/2018.
 */
public enum ResponseErrorCode {
    MEMBERLEAVEFAIL(-55),
    REMOVEMEMBERFAIL(-54),
    APPENDMEMBERFAIL(-53),
    DELETEFAIL(-52),
    UPDATEFAIL(-51),
    CREATEFAIL(-50),
    LIKEFAIL(-49),
    UNLIKEFAIL(-48),
    SUCCESSFULL(1),
    UNKNOW_ERROR(0),
    INVALIDDATA(-1),
    EXISTS(-2),
    NOTFOUND(-3),
    DENIED(-4),
    NOMEMBER(-5),
    REJECTED(-6),
    SYSTEM_ERROR(-100);

    private final int value;

    ResponseErrorCode(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
