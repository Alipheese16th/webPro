package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

public interface WkplMgntService {
	
	public List<Map<String, ?>> selectWkplMgntList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntElcList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntSbcnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntApalList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntKey(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplElcMgntDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplTrnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplMgntRgnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplImg(Map<String, String> param);
	
	public String selectWkplMgntKeyPass(Map<String, String> param);
	
	public String selectWkplMgntDtlCatInfo(Map<String, String> param);
	
	public int insertWkplMgnt(Map<String, String> param);
	
	public int insertWkplCdList(Map<String, String> param);
	
	public int insertWkplMgntDtlElcInfo(Map<String, String> param);
	
	public int insertWkplMgntDtlSbcnInfo(Map<String, String> param);
	
	public int insertWkplMgntDtlApalInfo(Map<String, String> param);
	
	public int insertWkplElcTrn(Map<String, String> param);
	
	public int insertWkplMgntDtlRgnInfo(Map<String, String> param);
	
	public int insertWkplMgntDtlCatInfo(Map<String, String> param);
	
	public int deleteWkplMgntDtlElcInfo(Map<String, String> param);
	
	public int deleteWkplMgntDtlSbcnInfo(Map<String, String> param);
	
	public int deleteWkplMgntDtlApalInfo(Map<String, String> param);
	
	public int deleteWkplElcTrn(Map<String, String> param);
	
	public int deleteWkplMgntDtlRgnInfo(Map<String, String> param);
	
	public int deleteWkplMgntDtlCatInfo(Map<String, String> param);
	
	public int deleteWkplElcTrn2(Map<String, String> param);
	
	public int updateCdMlangWkpl(Map<String, String> param);
	
	public int updateWkplElcTrn(Map<String, String> param);

	public int updateWkplElcTrn2(Map<String, String> param);

	public int updateWkplMgntDtlCatInfo(Map<String, String> param);

	public int updateWkplElcAppr(Map<String, Object> param);
}
