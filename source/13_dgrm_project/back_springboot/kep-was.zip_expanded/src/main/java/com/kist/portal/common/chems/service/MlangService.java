package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

public interface MlangService {
	
	public List<Map<String, ?>> selectMlangList(Map<String, String> param);
	
	public String selectMlangkey(Map<String, String> param);
	
	public int insertMlangList(Map<String, String> param);
	
	public int updateMlangList(Map<String, String> param);
	
	public int deleteMlangList(Map<String, String> param);
	
	public int updateRoleMlangList(Map<String, String> param);
	
	public String selectMlangInfo(Map<String, String> param);
	
	public List<Map<String, String>> selectMlangListInfo(Map<String, Object> param);
}
