package com.kist.portal.sh.mtng.service;

import java.util.List;
import java.util.Map;

public interface MtngService {
		
	public List<Map<String, ?>> selectMtngList(Map<String, String> param);
	
	public Map<String, ?> selectMtng(Map<String, Object> param);

	public int insertMtng(Map<String, Object> param);
	
	public int updateMtng(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteMtng(Map<String, String> param);

	public String selectMtngId(Map<String, String> param);

	public List<Map<String, ?>> selectMtngAprvDtl(Map<String, Object> param);

	public int updateMtngAprvAf(Map<String, Object> param);

	public int delMtng(Map<String, String> param);
}
	
