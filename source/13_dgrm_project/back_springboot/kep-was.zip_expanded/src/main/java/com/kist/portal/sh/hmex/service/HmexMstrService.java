package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

public interface HmexMstrService {
		
	public List<Map<String, ?>> selectDeptList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUserList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtraList(Map<String, String> param);
	
	public int insertHmexMstr(Map<String, Object> param);

}
	
