package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

public interface HmexAbfrService {
		
	public List<Map<String, ?>> selectAbfrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectAbfrDtlList(Map<String, String> param);
	
	public int insertAbfr(Map<String, Object> param);
	
	public int deleteAbfr(Map<String, String> param);

}
	
