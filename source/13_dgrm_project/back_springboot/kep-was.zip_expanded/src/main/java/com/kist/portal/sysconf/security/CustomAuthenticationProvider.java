package com.kist.portal.sysconf.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.login.service.LoginService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 인증 Provider (username, password)
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @NonNull
  private final UserDetailsService userDetailsService;

  @NonNull
  private final LoginService loginService;

  @NonNull
  private PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    if (authentication.getPrincipal() == "" || authentication.getPrincipal() == null) {
      // TODO Exception 추가 필요 (아이디 정보 없음)
      throw new BadCredentialsException("아이디 없음");
    }

    if (authentication.getCredentials() == "" || authentication.getCredentials() == null) {
      // TODO Exception 추가 필요 (패스워드 정보 없음)
      throw new BadCredentialsException("패스워드 없음");
    }

    // UserDetails 를 UserDto로 cast
    UserDetailDto user = (UserDetailDto) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

    if (user == null) {
      throw new BadCredentialsException("Not Found User");
    }

    // 90일이상 접속하지 않을 경우...
    int dayDiff = loginService.selectDayDiff(authentication.getPrincipal().toString());
    if (dayDiff > 90) {
      throw new BadCredentialsException("90일 이상 로그인 기록이 없습니다. 본인 인증 후 사용해주시기 바랍니다.");
    }

    // TODO: IP 가져오기 설정 필요.
    // String connectIP = getClientIP(null);
    // user.setIp(connectIP);

    String reqPassword = authentication.getCredentials().toString();

    String userPassword = loginService.selectUserPassword(user.getUserId());

    /*if (!passwordEncoder.matches(reqPassword, userPassword)) {
      // TODO Exception 추가 필요 (사용자 패스워드 맞지 않음)
      throw new BadCredentialsException("Not Found User");
    }*/

    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }

  /**
   * TODO: 일단 옮겨둠 추가적으로 기능 개선때 연결필요.
   * IP 조회 
   * @param request
   * @return
   */
  private String getClientIP(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");

    if (ip == null) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null) {
        ip = request.getRemoteAddr();
    }

    return ip;
}

}