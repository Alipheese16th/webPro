package com.kist.portal.test.user.service;

import java.util.List;
import java.util.Map;

import com.kist.portal.test.user.dto.TestUserDto;

public interface TestUserService {
	
	public List<TestUserDto> selectTestUserList(TestUserDto param);
	
	public String selectTestUserkey();
	
	public int insertTestUser(Map<String, String> param);
	
	public int updateTestUser(Map<String, String> param);
	
	public int deleteTestUser(Map<String, String> param);
	
	public int idConfirm(String uesrId);
	
}
