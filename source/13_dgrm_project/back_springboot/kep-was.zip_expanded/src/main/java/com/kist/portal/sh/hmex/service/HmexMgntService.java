package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

public interface HmexMgntService {
		
	public List<Map<String, ?>> selectMstrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrgpList(Map<String, String> param);
	
	public List<Map<String, ?>> selectCurrTrgpList(Map<String, String> param);
	
	public int insertHmexMgnt(Map<String, Object> param);
	
	public int deleteHmexMgnt(Map<String, String> param);

	public int insertHmexMgnt2(Map<String, Object> param);

}
	
