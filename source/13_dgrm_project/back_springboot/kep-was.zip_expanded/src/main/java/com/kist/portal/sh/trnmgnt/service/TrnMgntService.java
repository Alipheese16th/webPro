package com.kist.portal.sh.trnmgnt.service;

import java.util.List;
import java.util.Map;

public interface TrnMgntService {
	
	public List<Map<String, ?>> selectTrnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnUserList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnAuthChk(Map<String, String> param);
	
	public String selectTrnKey(Map<String, String> param);
	
	public String selectTrnUserKey(Map<String, String> param);
	
	public int insertTrnDtl(Map<String, String> param);
	
	public int insertTrnUserDtl(Map<String, String> param);
	
	public int deleteTrnUserDtl(Map<String, String> param);
	
	public int updateTrnAppr(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnAprvInfo(Map<String, String> param);
	
	public int updateTrnApprAf(Map<String, String> param);
	
	public int updateTrnDtlCnt(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnMailUserInfo(Map<String, String> param);
	
	public int updateTrnDtlDel(Map<String, String> param);
}
