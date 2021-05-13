package org.yu.serve.auth.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yu.serve.auth.domain.UserDO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 登录用户信息
 * Created by yu on 2020/6/19.
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private String id;
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 租户编号
     */
    private Integer tenantId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserDO userDO) {
        this.setId(userDO.getId());
        this.setDeptNo(userDO.getDeptNo());
        this.setTenantId(userDO.getTenantId());
        this.setUsername(userDO.getUsername());
        this.setPassword(userDO.getPassword());
        this.setEnabled(userDO.getEnabled());
        if (userDO.getRoles() != null) {
            authorities = new ArrayList<>();
            userDO.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getCode())));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
