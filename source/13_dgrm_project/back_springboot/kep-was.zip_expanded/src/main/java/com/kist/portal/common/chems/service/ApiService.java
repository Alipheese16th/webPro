package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

public interface ApiService {

	public List<Map<String, ?>> selectApiList(Map<String, String> param);
	
	public List<Map<String, ?>> selectApiRoleList(Map<String, String> param);
	
	public String selectApiKey(Map<String, String> param);
	
	public int insertApiList(Map<String, String> param);
	
	public int updateApiList(Map<String, String> param);
	
	public int deleteApiList(Map<String, String> param);
	
	public int deleteApiRole(Map<String, String> param);
	
	public int insertApiRole(Map<String, String> param);
	
	public int updateApiRole(Map<String, String> param);
}
