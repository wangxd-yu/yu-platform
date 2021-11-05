package org.yu.alonelaunch.security.token.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.yu.alonelaunch.security.pojo.SecurityUser;

import java.util.Collection;
import java.util.Map;

/**
 * YU jwt token转换器 在构建Authentication对象时手动提取扩展主体
 *
 * @author wangxd
 * @date 2021-09-04
 */
public class YuJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        Authentication userAuthentication = authentication.getUserAuthentication();

        if (userAuthentication != null) {
            if (map.get("user_name") != null) {
                SecurityUser extendedPrincipal = SecurityUser.builder()
                        .id((String) map.get("id"))
                        .clientId((String) map.get("client_id"))
                        .username((String) map.get("user_name"))
                        .deptNo((String) map.get("deptNo"))
                        .tenantId((String) map.get("tenantId"))
                        .build();
                Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();
                userAuthentication = new UsernamePasswordAuthenticationToken(extendedPrincipal, userAuthentication.getCredentials(), authorities);
            }
        }
        return new OAuth2Authentication(authentication.getOAuth2Request(), userAuthentication);
    }
}
