package com.kist.portal.common.test.service;

import java.util.List;
import java.util.Map;

public interface TestService {
	
	public List<Map<String, ?>> selectBbsList(Map<String, String> param);
	
	public Map<String, ?> selectBbs(String id);
	
	public int insertBbs(Map<String, String> param);
	
	public int updateBbs(Map<String, String> param);
	
	public int deleteBbs(Map<String, String> param);

}
