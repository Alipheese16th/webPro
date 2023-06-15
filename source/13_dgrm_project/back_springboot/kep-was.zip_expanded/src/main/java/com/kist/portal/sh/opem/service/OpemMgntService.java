package com.kist.portal.sh.opem.service;

import java.util.List;
import java.util.Map;

public interface OpemMgntService {
		
	public List<Map<String, ?>> selectOpemMgntList(Map<String, String> param);
	
	public Map<String, ?> selectOpemMgnt(Map<String, Object> param);

	public int insertOpemMgnt(Map<String, Object> param);
	
	public int updateOpemMgnt(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteOpemMgnt(Map<String, String> param);

	public List<Map<String, ?>> selectOpemMgntAprvDtl(Map<String, Object> param);

	public int updateOpemMgntAprvAf(Map<String, Object> param);

}
	
