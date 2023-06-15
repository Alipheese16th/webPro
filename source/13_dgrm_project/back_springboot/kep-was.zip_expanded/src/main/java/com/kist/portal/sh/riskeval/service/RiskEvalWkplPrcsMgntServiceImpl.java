package com.kist.portal.sh.riskeval.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.riskeval.mapper.RiskEvalWkplPrcsMgntMapper;

@Service
public class RiskEvalWkplPrcsMgntServiceImpl implements RiskEvalWkplPrcsMgntService {

	@Autowired(required=true)
	RiskEvalWkplPrcsMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectRiskEvalWkplPrcsMgntList(Map<String, String> param) {
		return mapper.selectRiskEvalWkplPrcsMgntList(param);
	}
	
	@Override
	public String selectDngpId(Map<String, String> param) {
		return mapper.selectDngpId(param);
	}

	@Override
	public int insertRiskEvalWkplPrcsMgnt(Map<String, String> param) {		
		return mapper.insertRiskEvalWkplPrcsMgnt(param);
	}
	
	@Override
	public int insertRiskEvalWkplPrcsMgnt2(Map<String, String> param) {		
		return mapper.insertRiskEvalWkplPrcsMgnt2(param);
	}

	@Override
	public int updateRiskEvalWkplPrcsMgnt(Map<String, String> param) {
		return mapper.updateRiskEvalWkplPrcsMgnt(param);
	}

	@Override
	public int deleteRiskEvalWkplPrcsMgnt(Map<String, String> param) {
		return mapper.deleteRiskEvalWkplPrcsMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectRiskEvalWkplPrcsMgnt(Map<String, String> param) {
		return mapper.selectRiskEvalWkplPrcsMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectRiskEvalWkplDngpList(Map<String, String> param) {
		return mapper.selectRiskEvalWkplDngpList(param);
	}
}
