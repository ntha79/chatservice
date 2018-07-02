package com.hdmon.chatservice.service.util;

import com.hdmon.chatservice.domain.IsoResponseEntity;
import com.hdmon.chatservice.domain.enumeration.UserFindTypeEnum;
import com.hdmon.chatservice.service.dto.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by UserName on 6/28/2018.
 */
public class UserHelper {
    private final static Logger log = LoggerFactory.getLogger(UserHelper.class);

    /**
     * Sends a request to get userinfo
     *
     * @param username the username to use to get userinfo.
     * @return the new, refreshed access token.
     */
    public static User getUserInfoFromUaaByUsername(HttpServletRequest request, String gatewayUrl, String username)
    {
        try {
            log.debug("Get more info of user: username={}", username);
            String theUrl = gatewayUrl + "/uaa/api/usersbyusername/" + username;
            String accessToken = extractHeaderToken(request);
            RestTemplate restTemplate = new RestTemplate();

            String encodedAuth = OAuth2AccessToken.BEARER_TYPE + " " + accessToken;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", encodedAuth);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<IsoResponseEntity> responseEntity = restTemplate.exchange(theUrl, HttpMethod.GET, entity, IsoResponseEntity.class);

            ObjectMapper mapper = new ObjectMapper();
            String userAsString = mapper.writeValueAsString(responseEntity.getBody().getData());
            User resUser1 = mapper.readValue(userAsString, User.class);
            return resUser1;
        }
        catch (Exception ex)
        {
            log.info("Loi trong khi goi ham UserHelper.getUserInfoFromUaaByUsername({},{})", gatewayUrl, username);
            log.error(ex.getMessage() + "\\r\\n" + ex.getStackTrace());
            return null;
        }
    }

    /**
     * Sends a request to get userinfo
     *
     * @param usermobile the usermobile to use to get userinfo.
     * @return the new, refreshed access token.
     */
    public static User getUserInfoFromUaaByMobile(HttpServletRequest request, String gatewayUrl, String usermobile)
    {
        try {
            log.debug("Get more info of user: usermobile={}", usermobile);
            String theUrl = gatewayUrl + "/uaa/api/usersbymobile/" + usermobile;
            String accessToken = extractHeaderToken(request);
            RestTemplate restTemplate = new RestTemplate();

            String encodedAuth = OAuth2AccessToken.BEARER_TYPE + " " + accessToken;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", encodedAuth);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<IsoResponseEntity> responseEntity = restTemplate.exchange(theUrl, HttpMethod.GET, entity, IsoResponseEntity.class);

            ObjectMapper mapper = new ObjectMapper();
            String userAsString = mapper.writeValueAsString(responseEntity.getBody().getData());
            User resUser1 = mapper.readValue(userAsString, User.class);
            return resUser1;
        }
        catch (Exception ex)
        {
            log.info("Loi trong khi goi ham UserHelper.getUserInfoFromUaaByMobile({},{})", gatewayUrl, usermobile);
            log.error(ex.getMessage() + "\\r\\n" + ex.getStackTrace());
            return null;
        }
    }

    /**
     * Thực hiện kiểm tra xem bạn bè có trong danh sách chưa?
     * Create Time: 2018-06-09
     * Update Time:  2018-06-28
     * @return the list friends entity
     */
    public static Long execCheckUserExistsInSystem(HttpServletRequest request, String gatewayUrl, UserFindTypeEnum findType, String username, String mobile)
    {
        Long lngResult = 0L;
        try {
            User userInfo = null;
            if (findType == UserFindTypeEnum.USERNAME) {
                userInfo = getUserInfoFromUaaByUsername(request, gatewayUrl, username);
            } else if (findType == UserFindTypeEnum.MOBILE) {
                userInfo = getUserInfoFromUaaByMobile(request, gatewayUrl, mobile);
            } else {
                userInfo = getUserInfoFromUaaByUsername(request, gatewayUrl, username);

                if (userInfo == null || userInfo.getId() <= 0)
                    userInfo = getUserInfoFromUaaByMobile(request, gatewayUrl, mobile);
            }

            if (userInfo != null && userInfo.getId() > 0)
                lngResult = userInfo.getId();
        }
        catch (Exception ex)
        {
            log.info("Co loi xay ra goi ham FriendsService.execCheckUserExistsInSystem(request, {},{},{})", findType, username, mobile);
            log.error(ex.getMessage() + "\\r\\n" + ex.getStackTrace());
        }
        return lngResult;
    }

    /**
     * Sends a request to get userinfo
     *
     * @param request the request to use to get accesstoken.
     * @return the new, refreshed access token.
     */
    protected static String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
                    value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
