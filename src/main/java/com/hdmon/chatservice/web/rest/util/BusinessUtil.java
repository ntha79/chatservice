package com.hdmon.chatservice.web.rest.util;

import com.hdmon.chatservice.security.SecurityUtils;
import com.hdmon.chatservice.service.util.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by UserName on 6/28/2018.
 */
public class BusinessUtil {
    private final static Logger log = LoggerFactory.getLogger(BusinessUtil.class);

    /**
     * Kiểm tra tài khoản thao tác với tài khoản đăng nhập có giống nhau không.
     *
     * @param actionUsername the actionUsername to compare
     * @return the persisted entity
     */
    public static boolean checkAuthenticationValid(String actionUsername)
    {
        boolean blResult = true;
        Optional<String> curUsername = SecurityUtils.getCurrentUserLogin();
        if(!actionUsername.equals(curUsername.get()))
            blResult = false;

        return blResult;
    }
}
