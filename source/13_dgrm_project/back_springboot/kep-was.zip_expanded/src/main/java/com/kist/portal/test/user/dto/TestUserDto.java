package com.kist.portal.test.user.dto;

import lombok.Data;

@Data
public class TestUserDto {
	private String userKey;
	// 사원번호
	private String userId;
	// 사원명
    private String userName;
	// 사원명 영어
	private String userEnglishName;
	// 사원 등록일
	private String userRegistDate;
	// 사용여부
	private String useYn;
	// 최초생성일시
	private String createDateTime;
	// 최종수정일시
	private String updateDateTime;
	
	// 검색조건
	// 시작날짜
	private String sDate;
	// 끝날짜
	private String eDate;
	
	
}
