package com.kist.portal.sh.htcrrm.service;

import java.util.List;
import java.util.Map;

public interface HtcrRmMgntService {
		
	public List<Map<String, ?>> selectHtcrRmList(Map<String, String> param);
	
	public int insertHtcrRm(Map<String, Object> param);
	
	public int deleteHtcrRm(Map<String, String> param);

}
	
