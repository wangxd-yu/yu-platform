package org.yu.admin.base.security.service;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yu.admin.base.constant.MessageConstant;
import org.yu.admin.base.security.pojo.SecurityUser;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.repository.UserRepository;
import org.yu.serve.system.module.user.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-31
 */
@Service("authUserService")
public class AuthUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthUserServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userRepository.findByUsername(username);
        if (userDO == null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        } else if (!userDO.getEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        }
        SecurityUser securityUser = new SecurityUser(userDO);
        Set<String> roles = userService.getRoleCodesByUserId(userDO.getId());
        securityUser.setAuthorities(roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
