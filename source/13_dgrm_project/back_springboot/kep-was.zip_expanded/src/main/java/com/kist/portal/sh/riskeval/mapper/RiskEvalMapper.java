package com.kist.portal.sh.riskeval.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RiskEvalMapper {
	
	public List<Map<String, ?>> selectRiskEvalBasicList(Map<String, String> param);
	
	public Map<String, ?> selectRiskEvalBasic(Map<String, String> param);

	public int insertRiskEvalBasic(Map<String, String> param);
	
	public int updateRiskEvalBasic(Map<String, String> param);
	
	public int updateRiskEvalBasicFile(Map<String, String> param);
	
	public int updateRiskEvalBasicStep(Map<String, String> param);
	
	public int updateRiskEvalBasicAppr(Map<String, String> param);
	
	public List<Map<String, ?>> selectRiskEvalBasicAppr(Map<String, String> param);
	
	public int updateRiskEvalBasicAprvAf(Map<String, String> param);

	public int deleteRiskEvalBasic(Map<String, String> param);
	
	public List<Map<String, ?>> selectRiskEvalDtlList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRiskEvalDtlListForMail(Map<String, String> param);
	
	public int insertRiskEvalTgtdtlwk(Map<String, String> param);
	
	public int updateRiskEvalTgtdtlwk(Map<String, String> param);

	public int deleteRiskEvalDtl(Map<String, String> param);
	
	public int updateRiskEvalHarmdngft(Map<String, String> param);
	
	public int updateRiskEvalRiskestmt(Map<String, String> param);
	
	public int updateRiskEvalRiskrdtplan(Map<String, String> param);
	
	public int insertRiskEvalBasicCopy(Map<String, String> param);

	public int insertRiskEvalTgtdtlwkCopy(Map<String, String> param);
}
