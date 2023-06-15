package com.kist.portal.common.login.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kist.portal.common.login.dto.UserDetailDto;

@Mapper
public interface LoginMapper {
	
	// Return Type 변경 UserInfo => USerDetailDto
	public UserDetailDto selectUser(String usrId);
	
	public int selectDayDiff(String usrId);
	
	public List<Map<String, ?>> selectMenuList(Map<String, String> param);
	
	public List<Map<String, ?>> selectApiRole(Map<String, String> param);
	
	public int insertUserLoginLog(Map<String, String> param);
	
	public int insertApiLog(Map<String, String> param);
	
	public int insertSmsNo(Map<String, String> param);
	
	public String selectSmsNo(Map<String, String> param);
	
	public String selectUserTelNo(Map<String, String> param);

	// (추가) 패스워드 조회
	public String selectUserPassword(String usrId);
}
