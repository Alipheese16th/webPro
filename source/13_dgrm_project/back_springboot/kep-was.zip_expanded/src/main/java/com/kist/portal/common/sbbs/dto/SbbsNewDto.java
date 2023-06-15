package com.kist.portal.common.sbbs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SbbsNewDto {
	private String boardId;
	private String title;
	private String content;
	private String createUser;
	private String createDate;
}
