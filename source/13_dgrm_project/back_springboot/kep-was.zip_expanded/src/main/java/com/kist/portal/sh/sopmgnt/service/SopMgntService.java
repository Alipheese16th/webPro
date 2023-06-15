package com.kist.portal.sh.sopmgnt.service;

import java.util.List;
import java.util.Map;

public interface SopMgntService {
		
	public List<Map<String, ?>> selectSopMgntList(Map<String, String> param);
	
	public Map<String, ?> selectSopMgnt(Map<String, String> param);

	public int insertSopMgnt(Map<String, Object> param);
	
	public int updateSopMgnt(Map<String, Object> param);
	
	public int deleteSopMgnt(Map<String, String> param);

	public String selectSopMgntId(Map<String, String> param);
	
	public List<Map<String, ?>> selectSopMgntBaseWkpl(Map<String, String> param);

	public int delSop(Map<String, String> param);

}
	
