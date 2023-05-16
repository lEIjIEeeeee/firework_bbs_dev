package com.wlj.firework.core.modular.common.model.dto;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wlj
 * @date 2023-05-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginUser implements UserDetails {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "角色id，可能存在多个角色，逗号分割")
    private String roleId;

    @ApiModelProperty(value = "角色id集合")
    private List<String> roleIds;

    @ApiModelProperty(value = "角色编码集合")
    private Set<String> roleCodes;

    @ApiModelProperty(value = "拥有的权限集合")
    private List<String> permissions;

    @ApiModelProperty(value = "token")
    private String authToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtil.isEmpty(this.getPermissions())) {
            return Collections.emptyList();
        }
        return this.getPermissions()
                   .stream()
                   .map(SimpleGrantedAuthority::new)
                   .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
