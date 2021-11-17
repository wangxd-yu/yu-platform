package org.yu.alonelaunch.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.alonelaunch.log.domain.LogLoginDO;
import org.yu.alonelaunch.log.service.LogLoginService;
import org.yu.alonelaunch.pojo.Oauth2TokenDTO;
import org.yu.common.web.util.AddressUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 *
 * @author wangxd
 * @date 2020/7/17
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private LogLoginService logLoginService;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<Oauth2TokenDTO> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        assert oAuth2AccessToken != null;
        Oauth2TokenDTO oauth2TokenDto = Oauth2TokenDTO.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .expiration(oAuth2AccessToken.getExpiration().getTime())
                .tokenHead("Bearer ").build();
        logLoginService.asyncSave(this.map2LogLoginDO(parameters.get("username")));
        return new ResponseEntity<>(oauth2TokenDto, HttpStatus.OK);
    }

    private LogLoginDO map2LogLoginDO(String username) {
        LogLoginDO logLoginDO = new LogLoginDO();
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        logLoginDO.setUsername(username);
        logLoginDO.setBrowser(ua.getBrowser().getName());
        String ip = ServletUtil.getClientIP(request);
        logLoginDO.setIp(ip);
        logLoginDO.setLocation(AddressUtil.getRealAddressByIP(ip));
        logLoginDO.setOs(ua.getOs().getName());
        return logLoginDO;
    }
}
