package com.kist.portal.common.login.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDto implements UserDetails {

  final String ROLE_PREFIX = "ROLE_";

  // user_id
  // private String username;
  // password
  // private String password;
  // 권한
  // private String role;

  private String userId;
  private String userName;
  private String departmentCode;
  private String departmentName;
  private String companyName;
  private String companyCode;
  private String wkplId;
  private String wkplNm;
  private String languageCode;
  private String timeZone;
  private String timeZoneCode;
  private String workspaceCode;
  private String roles;
  private String roleTypeCodes;
  private String userEmail;
  private String userCellPhoneNo;
  private String departmentHeadUserId;
  private String lastLoginDateTime;
  private String lastIpAddress;
  private String ip;
  private Map<String, String> apiInfo;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + this.roles));
    return authorities;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  public String getPassword() {
    return "";
  }

  /**
   * TODO: 내부 정책 수립후 구체화 예정
   * 계정만료여부
   */
  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  /**
   * TODO: 내부 정책 수립후 구체화 예정
   * 계정잠김 여부
   */
  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  /**
   * TODO: 내부 정책 수립후 구체화 예정
   * 비밀번호 만료 여부
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  /**
   * TODO: 내부 정책 수립후 구체화 예정
   * 계정 활성화 여부
   */
  @Override
  public boolean isEnabled() {
    return false;
  }
}
