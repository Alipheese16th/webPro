package com.kist.portal.common.comm.service;

import java.util.List;
import java.util.Map;

public interface CommPopService {
	
	public List<Map<String, ?>> selectSbstCommPopList(Map<String, String> param);

	public List<Map<String, ?>> selectMtrlCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectVndrCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlMultiCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBsendCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBsendUserCommPopList(Map<String, String> param);

}
