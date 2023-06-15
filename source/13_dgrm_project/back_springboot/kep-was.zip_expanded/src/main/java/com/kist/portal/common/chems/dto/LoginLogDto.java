package com.kist.portal.common.chems.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginLogDto {
  
  private String userId;
  private String userName;
  private String loginDate;
  private String loginTime;
  private String ipAddress;
  private String roleName;

  private String startDate;
  private String endDate;

  private String SESS_LANG;
}
