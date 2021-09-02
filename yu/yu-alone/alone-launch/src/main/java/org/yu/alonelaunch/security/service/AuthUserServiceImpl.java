package org.yu.alonelaunch.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yu.alonelaunch.security.pojo.AuthUser;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.repository.UserRepository;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-31
 */
@Service("authUserService")
public class AuthUserServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthUserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userRepository.findByUsername(username);
        return new AuthUser(userDO.getUsername(), userDO.getPassword());
    }
}
