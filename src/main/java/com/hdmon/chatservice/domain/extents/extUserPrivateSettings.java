package com.hdmon.chatservice.domain.extents;

import java.io.Serializable;

public class extUserPrivateSettings implements Serializable {
    private String code;
    private boolean allowPlay;

    public extUserPrivateSettings()
    {
        super();
    }

    public extUserPrivateSettings(String code, boolean allowPlay)
    {
        this.code = code;
        this.allowPlay = allowPlay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAllowPlay() {
        return allowPlay;
    }

    public void setAllowPlay(boolean allowPlay) {
        this.allowPlay = allowPlay;
    }
}
