package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.acdtmgnt.mapper.AcdtReportMapper;

@Service
public class AcdtReportServiceImpl implements AcdtReportService {
	
	@Autowired(required=true)
	AcdtReportMapper mapper;

	@Override
	public List<Map<String, ?>> selectAcdtTot1(Map<String, String> param) {
		return mapper.selectAcdtTot1(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAcdtTot2(Map<String, String> param) {
		return mapper.selectAcdtTot2(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApTot(Map<String, String> param) {
		return mapper.selectApTot(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApWkpl(Map<String, String> param) {
		return mapper.selectApWkpl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectCat(Map<String, String> param) {
		return mapper.selectCat(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkpl(Map<String, String> param) {
		return mapper.selectWkpl(param);
	}
	
	@Override
	public List<Map<String, ?>> selecCatWkpl(Map<String, String> param) {
		return mapper.selecCatWkpl(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtPerQt(Map<String, String> param) {
		return mapper.selecAcdtPerQt(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtPerMon(Map<String, String> param) {
		return mapper.selecAcdtPerMon(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtType(Map<String, String> param) {
		return mapper.selecAcdtType(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtOprn(Map<String, String> param) {
		return mapper.selecAcdtOprn(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtUsftTxt(Map<String, String> param) {
		return mapper.selecAcdtUsftTxt(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtUsftSt(Map<String, String> param) {
		return mapper.selecAcdtUsftSt(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtCausTgt(Map<String, String> param) {
		return mapper.selecAcdtCausTgt(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtPrst(Map<String, String> param) {
		return mapper.selecAcdtPrst(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtDept(Map<String, String> param) {
		return mapper.selecAcdtDept(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtPeriod(Map<String, String> param) {
		return mapper.selecAcdtPeriod(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtHh(Map<String, String> param) {
		return mapper.selecAcdtHh(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtMm(Map<String, String> param) {
		return mapper.selecAcdtMm(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtDow(Map<String, String> param) {
		return mapper.selecAcdtDow(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtCareDays(Map<String, String> param) {
		return mapper.selecAcdtCareDays(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtAge(Map<String, String> param) {
		return mapper.selecAcdtAge(param);
	}
	
	@Override
	public List<Map<String, ?>> selecAcdtSex(Map<String, String> param) {
		return mapper.selecAcdtSex(param);
	}
	
}
