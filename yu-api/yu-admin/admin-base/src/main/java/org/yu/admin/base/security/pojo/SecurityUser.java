package org.yu.admin.base.security.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yu.admin.base.util.TenantUtil;
import org.yu.serve.system.module.user.domain.UserDO;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户信息
 *
 * @author wangxd
 * @date 2021-08-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUser implements UserDetails {

    private String clientId;

    /**
     * ID
     */
    private String id;
    /**
     * 部门编号
     */
    private String deptId;
    /**
     * 租户编号
     */
    private String tenantId;
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

    private Set<String> roles;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserDO userDO) {
        this.setId(userDO.getId());
        this.setDeptId(userDO.getDeptId());
        this.setTenantId(TenantUtil.getTenantId());
        this.setUsername(userDO.getUsername());
        this.setPassword(userDO.getPassword());
        this.setEnabled(userDO.getEnabled());
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