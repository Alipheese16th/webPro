package com.kist.portal.sh.hmflmcn.service;

import java.util.List;
import java.util.Map;

public interface HmflMcnService {
	
	public List<Map<String, ?>> selectHmflMcnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectHmflMcnDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectHmflMcnChkList(Map<String, String> param);
	
	public List<Map<String, ?>> selectHmflMcnWkplCombo(Map<String, String> param);
	
	public String selectHmflMcnkey(Map<String, String> param);
	
	public int insertHmflMcn(Map<String, String> param);
	
	public int insertHmflMcnDtlChkInfo(Map<String, String> param);
	
	public int deleteHmflMcnDtlChkInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectSftInspRsltList(Map<String, String> param);
	
	public String selectSftInspRsltkey(Map<String, String> param);
	
	public List<Map<String, ?>> selectSftInspRsltDtl(Map<String, String> param);
	
	public int insertSftInspRsltDtl(Map<String, String> param);
	
	public int insertHmflMcnDtlChkListInfo(Map<String, String> param);
	
	public String selectHmflMcnChkDupChk(Map<String, String> param);
}
