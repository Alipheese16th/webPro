package com.kist.portal.sh.riskeval.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.riskeval.mapper.RiskEvalAftActMapper;

@Service
public class RiskEvalAftActServiceImpl implements RiskEvalAftActService {

	@Autowired(required=true)
	RiskEvalAftActMapper mapper;

	@Override
	public List<Map<String, ?>> selectRiskEvalAftActList(Map<String, String> param) {
		return mapper.selectRiskEvalAftActList(param);
	}

	@Override
	public int updateRiskEvalAftAct(Map<String, String> param) {
		return mapper.updateRiskEvalAftAct(param);
	}
	
	@Override
	public int updateRiskEvalAftActPrst(Map<String, String> param) {
		return mapper.updateRiskEvalAftActPrst(param);
	}
	
	@Override
	public int updateRiskEvalAftActAppr(Map<String, String> param) {
		return mapper.updateRiskEvalAftActAppr(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRiskEvalAftActAppr(Map<String, String> param) {
		return mapper.selectRiskEvalAftActAppr(param);
	}
	
	@Override
	public int updateRiskEvalAftActAprvAf(Map<String, String> param) {
		return mapper.updateRiskEvalAftActAprvAf(param);
	}
}
