package com.kist.portal.common.comm.service;

import java.util.List;
import java.util.Map;

public interface CommService {
	
	public List<Map<String, ?>> selectCdList(Map<String, String> param);
	
	public List<Map<String, ?>> selectCdGrpAllList(Map<String, String> param);

	public List<Map<String, ?>> selectCdAllList(Map<String, String> param);
	
	public int updateConfig(Map<String, String> param);
	
	public List<Map<String, ?>> selectMultiLanguageList(Map<String, String> param);

	public Map<String, String> selectMultiLanguageI18n(Map<String, String> param);
	
	public List<Map<String, ?>> selectTzCode(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplCode(Map<String, String> param);
	
	public int insertPrivacyLog(Map<String, String> param);

}
