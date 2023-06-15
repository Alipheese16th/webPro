package com.kist.portal.sh.riskeval.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RiskEvalAftActMapper {
	
	public List<Map<String, ?>> selectRiskEvalAftActList(Map<String, String> param);
	
	public int updateRiskEvalAftAct(Map<String, String> param);
	
	public int updateRiskEvalAftActPrst(Map<String, String> param);
	
	public int updateRiskEvalAftActAppr(Map<String, String> param);
	
	public List<Map<String, ?>> selectRiskEvalAftActAppr(Map<String, String> param);
	
	public int updateRiskEvalAftActAprvAf(Map<String, String> param);
	
}
