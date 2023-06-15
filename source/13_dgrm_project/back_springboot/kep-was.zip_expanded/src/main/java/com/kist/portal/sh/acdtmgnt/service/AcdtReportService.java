package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

public interface AcdtReportService {

	public List<Map<String, ?>> selectAcdtTot1(Map<String, String> param);
	
	public List<Map<String, ?>> selectAcdtTot2(Map<String, String> param);
	
	public List<Map<String, ?>> selectApTot(Map<String, String> param);
	
	public List<Map<String, ?>> selectApWkpl(Map<String, String> param);
	
	public List<Map<String, ?>> selectCat(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkpl(Map<String, String> param);
	
	public List<Map<String, ?>> selecCatWkpl(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtPerQt(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtPerMon(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtType(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtOprn(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtUsftTxt(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtUsftSt(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtCausTgt(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtPrst(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtDept(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtPeriod(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtHh(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtMm(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtDow(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtCareDays(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtAge(Map<String, String> param);
	
	public List<Map<String, ?>> selecAcdtSex(Map<String, String> param);
	
}
