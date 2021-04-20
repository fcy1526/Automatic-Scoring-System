package com.fcy.uaaservice.entity;

import com.fcy.uaaservice.enums.SexEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/2/21 23:21
 */
@Data
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 9210812223193135500L;
    // 学号
    private String userId;
    // 密码
    private String password;
    // 真实姓名
    private String trueName;
    // 性别
    private SexEnum sex;
    // 角色信息
    private List<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
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
        return true;
    }
}
