package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

public interface BizPcService {
	
	public List<Map<String, ?>> selectBizPcGrpList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBizPcDtlList(Map<String, String> param);
	
	public String selectBizPckey(Map<String, String> param);
	
	public int saveBizPcGrp(Map<String, String> param);
	
	public int deleteBizPcGrp(Map<String, String> param);
	
	public int saveBizPc(Map<String, String> param);
	
	public int deleteBizPc(Map<String, String> param);


}
