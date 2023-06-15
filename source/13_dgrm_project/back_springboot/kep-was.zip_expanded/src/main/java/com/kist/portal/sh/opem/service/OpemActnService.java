package com.kist.portal.sh.opem.service;

import java.util.List;
import java.util.Map;

public interface OpemActnService {
		
	public List<Map<String, ?>> selectOpemActnList(Map<String, String> param);
	
	public Map<String, ?> selectOpemActn(Map<String, Object> param);
	
	public int insertOpemActn(Map<String, Object> param);
	
	public int updateOpemActn(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteOpemActn(Map<String, String> param);
	
	public String selectOpemActnId(Map<String, String> param);

	public List<Map<String, ?>> selectOpemActnAprvDtl(Map<String, Object> param);

	public int updateOpemActnAprvAf(Map<String, Object> param);
}
	
