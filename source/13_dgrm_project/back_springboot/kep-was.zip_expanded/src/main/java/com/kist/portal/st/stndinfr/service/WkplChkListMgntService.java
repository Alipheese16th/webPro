package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

public interface WkplChkListMgntService {
	
	public List<Map<String, ?>> selectWkplChkList(Map<String, String> param);
	
	public String selectWkplChkKeyPass(Map<String, String> param);
	
	public int insertWkplChkList(Map<String, String> param);
	
	public int deleteWkplChkList(Map<String, String> param);
}
