package com.kist.portal.sh.hmflmcn.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.hmflmcn.mapper.HmflMcnMapper;

@Service
public class HmflMcnServiceImpl implements HmflMcnService {
	
	@Autowired
	HmflMcnMapper mapper;

	@Override
	public List<Map<String, ?>> selectHmflMcnList(Map<String, String> param) {
		return mapper.selectHmflMcnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectHmflMcnDtl(Map<String, String> param) {
		return mapper.selectHmflMcnDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectHmflMcnChkList(Map<String, String> param) {
		return mapper.selectHmflMcnChkList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectHmflMcnWkplCombo(Map<String, String> param) {
		return mapper.selectHmflMcnWkplCombo(param);
	}
	
	@Override
	public String selectHmflMcnkey(Map<String, String> param) {
		return mapper.selectHmflMcnkey(param);
	}
	
	@Override
	public int insertHmflMcn(Map<String, String> param) {
		return mapper.insertHmflMcn(param);
	}
	
	@Override
	public int insertHmflMcnDtlChkInfo(Map<String, String> param) {
		return mapper.insertHmflMcnDtlChkInfo(param);
	}
	
	@Override
	public int deleteHmflMcnDtlChkInfo(Map<String, String> param) {
		return mapper.deleteHmflMcnDtlChkInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectSftInspRsltList(Map<String, String> param) {
		return mapper.selectSftInspRsltList(param);
	}
	
	@Override
	public String selectSftInspRsltkey(Map<String, String> param) {
		return mapper.selectSftInspRsltkey(param);
	}
	
	@Override
	public List<Map<String, ?>> selectSftInspRsltDtl(Map<String, String> param) {
		return mapper.selectSftInspRsltDtl(param);
	}
	
	@Override
	public int insertSftInspRsltDtl(Map<String, String> param) {
		return mapper.insertSftInspRsltDtl(param);
	}
	
	@Override
	public int insertHmflMcnDtlChkListInfo(Map<String, String> param) {
		return mapper.insertHmflMcnDtlChkListInfo(param);
	}
	
	@Override
	public String selectHmflMcnChkDupChk(Map<String, String> param) {
		return mapper.selectHmflMcnChkDupChk(param);
	}
}
