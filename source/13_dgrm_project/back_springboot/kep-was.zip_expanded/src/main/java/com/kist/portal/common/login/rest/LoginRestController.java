package com.kist.portal.common.login.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginRestController {

  /**
   * 로그인 실패시 호출
   * 
   * @return
   */
  @GetMapping("/fail")
  public String fail() {
    return "fail";
  }

  /**
   * 로그인 성공시 호출
   * 
   * @return
   */
  @GetMapping("/success")
  public UserDetailDto success() {
    return SessionUtil.getSession();
  }

  @GetMapping("/session")
  // public String session(Principal principal) {
  public String session(@AuthenticationPrincipal UserDetailDto userDto) {

    // SessionUtil session = new SessionUtil();
    // UserDto userDto = session.getSession();

    // return userDto.getUsername() + " / " + userDto.getPassword();
    return userDto.getUserId();
  }
}
