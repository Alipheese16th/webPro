package com.kist.portal.st.stndinfr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvsMgntMapper {
	
	public List<Map<String, ?>> selectInvsMgntList(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntDtlInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntDtlPlan(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntDtlRslt(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntDtlTotRslt(Map<String, String> param);
	
	public String selectInvsMgntPass(Map<String, String> param);
	
	public int insertInvsMgntDtlInfo(Map<String, String> param);
	
	public int insertInvsMgntDtlPlan(Map<String, String> param);
	
	public int insertInvsMgntDtlRslt(Map<String, String> param);
	
	public int deleteInvsMgntDtlPlan(Map<String, String> param);
	
	public int deleteInvsMgntDtlRslt(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntTotList(Map<String, String> param);
	
	public int updateInvsMgntAppr(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntAprvDtl(Map<String, String> param);
	
	public int updateInvsMgntApprAf(Map<String, String> param);
	
	public String selectInvsMgntPlanPass(Map<String, String> param);
	
	public int updateInvsMgntInvsAmt(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsMgntExcelTemp(Map<String, String> param);
	
	public int deleteInvsMgntDtlInfoDel(Map<String, String> param);
	
	public int deleteInvsMgntDtlPlanAll(Map<String, String> param);
	
	public int deleteInvsMgntDtlRsltAll(Map<String, String> param);
}
