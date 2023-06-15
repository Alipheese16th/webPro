package com.kist.portal.common.login.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {
  
  @Getter
  @Setter
  public static class loginParam {
    private String username;
    private String password;
    private String authCd;
  }
}
