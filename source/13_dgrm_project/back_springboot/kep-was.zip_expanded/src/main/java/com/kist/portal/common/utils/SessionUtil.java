package com.kist.portal.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.login.model.UserInfo;

public class SessionUtil {

  // TODO: 로그인 정보 없을 경우 예외처리 추가 필요.
  public static UserDetailDto getSession() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDetailDto userDetails = (UserDetailDto) principal;
    return userDetails;
  }

  /**
   * 기존 소스 기준 UserInfo convert 추가
   * @return
   */
  public static UserInfo getSessionUserInfo() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDetailDto userDetails = (UserDetailDto) principal;

    return UserInfo.clone(userDetails);
  }

}
