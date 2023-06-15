package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

public interface HmexRsltService {
		
	public List<Map<String, ?>> selectRsltList(Map<String, String> param);
	
	public Map<String, ?> selectRslt(Map<String, String> param);
	
	public int insertHmexRslt(Map<String, Object> param);
	
	public int deleteHmexRslt(Map<String, String> param);

}
	
