package com.kist.portal.sh.riskeval.service;

import java.util.List;
import java.util.Map;

public interface RiskEvalWkplPrcsMgntService {
	
	public List<Map<String, ?>> selectRiskEvalWkplPrcsMgntList(Map<String, String> param);
	
	public String selectDngpId(Map<String, String> param);

	public int insertRiskEvalWkplPrcsMgnt(Map<String, String> param);
	
	public int insertRiskEvalWkplPrcsMgnt2(Map<String, String> param);
	
	public int updateRiskEvalWkplPrcsMgnt(Map<String, String> param);

	public int deleteRiskEvalWkplPrcsMgnt(Map<String, String> param);

	public List<Map<String, ?>> selectRiskEvalWkplPrcsMgnt(Map<String, String> param);

	public List<Map<String, ?>> selectRiskEvalWkplDngpList(Map<String, String> param);
}
