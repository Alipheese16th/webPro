package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.st.stndinfr.mapper.InvsMgntMapper;

@Service
public class InvsMgntServiceImpl implements InvsMgntService {
	
	@Autowired
	InvsMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectInvsMgntList(Map<String, String> param) {
		return mapper.selectInvsMgntList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntDtlInfo(Map<String, String> param) {
		return mapper.selectInvsMgntDtlInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntDtlPlan(Map<String, String> param) {
		return mapper.selectInvsMgntDtlPlan(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntDtlRslt(Map<String, String> param) {
		return mapper.selectInvsMgntDtlRslt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntDtlTotRslt(Map<String, String> param) {
		return mapper.selectInvsMgntDtlTotRslt(param);
	}
	
	@Override
	public String selectInvsMgntPass(Map<String, String> param) {
		return mapper.selectInvsMgntPass(param);
	}
	
	@Override
	public int insertInvsMgntDtlInfo(Map<String, String> param) {
		return mapper.insertInvsMgntDtlInfo(param);
	}
	
	@Override
	public int insertInvsMgntDtlPlan(Map<String, String> param) {
		return mapper.insertInvsMgntDtlPlan(param);
	}
	
	@Override
	public int insertInvsMgntDtlRslt(Map<String, String> param) {
		return mapper.insertInvsMgntDtlRslt(param);
	}
	
	@Override
	public int deleteInvsMgntDtlPlan(Map<String, String> param) {
		return mapper.deleteInvsMgntDtlPlan(param);
	}
	
	@Override
	public int deleteInvsMgntDtlRslt(Map<String, String> param) {
		return mapper.deleteInvsMgntDtlRslt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntTotList(Map<String, String> param) {
		return mapper.selectInvsMgntTotList(param);
	}
	
	@Override
	public int updateInvsMgntAppr(Map<String, String> param) {
		return mapper.updateInvsMgntAppr(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntAprvDtl(Map<String, String> param) {
		return mapper.selectInvsMgntAprvDtl(param);
	}
	
	@Override
	public int updateInvsMgntApprAf(Map<String, String> param) {
		return mapper.updateInvsMgntApprAf(param);
	}
	
	@Override
	public String selectInvsMgntPlanPass(Map<String, String> param) {
		return mapper.selectInvsMgntPlanPass(param);
	}
	
	@Override
	public int updateInvsMgntInvsAmt(Map<String, String> param) {
		return mapper.updateInvsMgntInvsAmt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsMgntExcelTemp(Map<String, String> param) {
		return mapper.selectInvsMgntExcelTemp(param);
	}
	
	@Override
	public int deleteInvsMgntDtlInfoDel(Map<String, String> param) {
		return mapper.deleteInvsMgntDtlInfoDel(param);
	}
	
	@Override
	public int deleteInvsMgntDtlPlanAll(Map<String, String> param) {
		return mapper.deleteInvsMgntDtlPlanAll(param);
	}
	
	@Override
	public int deleteInvsMgntDtlRsltAll(Map<String, String> param) {
		return mapper.deleteInvsMgntDtlRsltAll(param);
	}
}
