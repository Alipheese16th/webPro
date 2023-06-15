package com.kist.portal.common.login.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.login.mapper.LoginMapper;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginMapper mapper;
	
	@Override
	public UserDetailDto selectUser(String usrId) {
		return mapper.selectUser(usrId);
	}
	
	@Override
	public int selectDayDiff(String usrId) {
		return mapper.selectDayDiff(usrId);
	}

	@Override
	public List<Map<String, ?>> selectMenuList(Map<String, String> param) {
		return mapper.selectMenuList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApiRole(Map<String, String> param) {
		return mapper.selectApiRole(param);
	}
	
	@Override
	public int insertUserLoginLog(Map<String, String> param) {
		return mapper.insertUserLoginLog(param);
	}
	
	@Override
	public int insertApiLog(Map<String, String> param) {
		return mapper.insertApiLog(param);
	}
	
	@Override
	public int insertSmsNo(Map<String, String> param) {
		return mapper.insertSmsNo(param);
	}
	
	@Override
	public String selectSmsNo(Map<String, String> param) {
		return mapper.selectSmsNo(param);
	}
	
	@Override
	public String selectUserTelNo(Map<String, String> param) {
		return mapper.selectUserTelNo(param);
	}

	@Override
	public String selectUserPassword(String usrId) {
		return mapper.selectUserPassword(usrId);
	}

}
