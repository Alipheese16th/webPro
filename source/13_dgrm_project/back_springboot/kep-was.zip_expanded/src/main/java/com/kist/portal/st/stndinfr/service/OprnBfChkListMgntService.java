package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

public interface OprnBfChkListMgntService {

	public List<Map<String, ?>> selectOprnBfChkList(Map<String, String> param);
	
	public int insertOprnBfChkList(Map<String, Object> param);

	public int updateOprnBfChkList(Map<String, Object> param);
	
	public int deleteOprnBfChkList(Map<String, Object> param);
	
}
