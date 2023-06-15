package com.kist.portal.sh.riskeval.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.riskeval.mapper.RiskEvalMapper;

@Service
public class RiskEvalServiceImpl implements RiskEvalService {

	@Autowired(required=true)
	RiskEvalMapper mapper;

	@Override
	public List<Map<String, ?>> selectRiskEvalBasicList(Map<String, String> param) {
		return mapper.selectRiskEvalBasicList(param);
	}
	
	@Override
	public Map<String, ?> selectRiskEvalBasic(Map<String, String> param) {
		return mapper.selectRiskEvalBasic(param);
	}

	@Override
	public int insertRiskEvalBasic(Map<String, String> param) {		
		return mapper.insertRiskEvalBasic(param);
	}
	
	@Override
	public int updateRiskEvalBasicFile(Map<String, String> param) {		
		return mapper.updateRiskEvalBasicFile(param);
	}

	@Override
	public int updateRiskEvalBasic(Map<String, String> param) {
		return mapper.updateRiskEvalBasic(param);
	}
	
	@Override
	public int updateRiskEvalBasicStep(Map<String, String> param) {
		return mapper.updateRiskEvalBasicStep(param);
	}
	
	@Override
	public int updateRiskEvalBasicAppr(Map<String, String> param) {
		return mapper.updateRiskEvalBasicAppr(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRiskEvalBasicAppr(Map<String, String> param) {
		return mapper.selectRiskEvalBasicAppr(param);
	}
	
	@Override
	public int updateRiskEvalBasicAprvAf(Map<String, String> param) {
		return mapper.updateRiskEvalBasicAprvAf(param);
	}

	@Override
	public int deleteRiskEvalBasic(Map<String, String> param) {
		return mapper.deleteRiskEvalBasic(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRiskEvalDtlList(Map<String, String> param) {
		return mapper.selectRiskEvalDtlList(param);
	}

	@Override
	public int insertRiskEvalTgtdtlwk(Map<String, String> param) {		
		return mapper.insertRiskEvalTgtdtlwk(param);
	}

	@Override
	public int updateRiskEvalTgtdtlwk(Map<String, String> param) {
		return mapper.updateRiskEvalTgtdtlwk(param);
	}

	@Override
	public int deleteRiskEvalDtl(Map<String, String> param) {
		return mapper.deleteRiskEvalDtl(param);
	}
	
	@Override
	public int updateRiskEvalHarmdngft(Map<String, String> param) {
		return mapper.updateRiskEvalHarmdngft(param);
	}
	
	@Override
	public int updateRiskEvalRiskestmt(Map<String, String> param) {
		return mapper.updateRiskEvalRiskestmt(param);
	}
	
	@Override
	public int updateRiskEvalRiskrdtplan(Map<String, String> param) {
		return mapper.updateRiskEvalRiskrdtplan(param);
	}

	@Override
	public int insertRiskEvalBasicCopy(Map<String, String> param) {		
		return mapper.insertRiskEvalBasicCopy(param);
	}

	@Override
	public int insertRiskEvalTgtdtlwkCopy(Map<String, String> param) {		
		return mapper.insertRiskEvalTgtdtlwkCopy(param);
	}
}
