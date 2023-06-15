package com.kist.portal.common.chems.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TemplateDto {

  @Getter
  @Setter
  public static class param {
    private String tmptId;
    private String tmptNm;
    private String tmptDivCd;
    private String bizCd;
    private String usgYn;
    private String sessionLang;
    private Map<String, Object> content;
  }

  @Getter
  @Setter
  public static class response {
    private String tmptId;
    private String bizCd;
    private String bizNm;
    private String tmptDivCd;
    private String tmptDivNm;
    private String tmptNm;
    private String tmptCntn;
    private String usgYn;
    private String delYn;
    private String crtUsrId;
    private String crtDt;
    private String uptUsrId;
    private String uptDt;
  }

  @Getter
  @Setter
  public static class info extends TemplateDto.response {
    // response와 같은 데이터이기에 extends 로 처리.
  }

  @Getter
  @Setter
  public static class insertParam {
    private String tmptId;
    private String bizCd;
    private String tmptDivCd;
    private String tmptNm;
    private String tmptCntn;
    private String usgYn;
    private String delYn;
    private String crtUsrId;
    private String crtDt;
    private String uptUsrId;
    private String uptDt;
    private String sessionUserId;
    private String sessionLangCd;
  }

  @Getter
  @Setter
  public static class updateParam {
    private String tmptId;
    private String bizCd;
    private String tmptDivCd;
    private String tmptNm;
    private String tmptCntn;
    private String usgYn;
    private String delYn;
    private String crtUsrId;
    private String crtDt;
    private String uptUsrId;
    private String uptDt;
    private String sessionUserId;
    private String sessionLangCd;
  }

  private String content;
}
