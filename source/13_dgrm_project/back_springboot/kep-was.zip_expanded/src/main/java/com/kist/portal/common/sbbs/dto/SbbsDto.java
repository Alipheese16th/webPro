package com.kist.portal.common.sbbs.dto;

import lombok.Getter;
import lombok.Setter;

public class SbbsDto {

  @Getter
  @Setter
  public static class param {
    private String title;
    private String user;
  }

  @Getter
  @Setter
  public static class response {
    private String boardId;
    private String title;
    private String content;
    private String createUser;
    private String createDate;
  }

  @Getter
  @Setter
  public static class info extends SbbsDto.response {
    // response와 같은 데이터이기에 extends 로 처리.
  }

  @Getter
  @Setter
  public static class insertParam {
    private String boardId;
    private String title;
    private String content;
  }

  @Getter
  @Setter
  public static class updateParam {
    private String boardId;
    private String title;
    private String content;
  }

  @Getter
  @Setter
  public static class deleteParam {
    private String boardId;
  }

}
