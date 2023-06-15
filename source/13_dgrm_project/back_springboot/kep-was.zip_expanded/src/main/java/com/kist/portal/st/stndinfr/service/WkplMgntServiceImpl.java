package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.st.stndinfr.mapper.WkplMgntMapper;

@Service
public class WkplMgntServiceImpl implements WkplMgntService {
	
	@Autowired
	WkplMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectWkplMgntList(Map<String, String> param) {
		return mapper.selectWkplMgntList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntDtl(Map<String, String> param) {
		return mapper.selectWkplMgntDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntElcList(Map<String, String> param) {
		return mapper.selectWkplMgntElcList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplElcMgntDtl(Map<String, String> param) {
		return mapper.selectWkplElcMgntDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplTrnList(Map<String, String> param) {
		return mapper.selectWkplTrnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntSbcnList(Map<String, String> param) {
		return mapper.selectWkplMgntSbcnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntApalList(Map<String, String> param) {
		return mapper.selectWkplMgntApalList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntRgnList(Map<String, String> param) {
		return mapper.selectWkplMgntRgnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplImg(Map<String, String> param) {
		return mapper.selectWkplImg(param);
	}
	
	@Override
	public String selectWkplMgntKeyPass(Map<String, String> param) {
		return mapper.selectWkplMgntKeyPass(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplMgntKey(Map<String, String> param) {
		return mapper.selectWkplMgntKey(param);
	}
	
	@Override
	public String selectWkplMgntDtlCatInfo(Map<String, String> param) {
		return mapper.selectWkplMgntDtlCatInfo(param);
	}
	
	@Override
	public int insertWkplCdList(Map<String, String> param) {
		return mapper.insertWkplCdList(param);
	}
	
	@Override
	public int insertWkplMgnt(Map<String, String> param) {
		return mapper.insertWkplMgnt(param);
	}
	
	@Override
	public int insertWkplMgntDtlElcInfo(Map<String, String> param) {
		return mapper.insertWkplMgntDtlElcInfo(param);
	}
	
	@Override
	public int insertWkplMgntDtlSbcnInfo(Map<String, String> param) {
		return mapper.insertWkplMgntDtlSbcnInfo(param);
	}
	
	@Override
	public int insertWkplMgntDtlApalInfo(Map<String, String> param) {
		return mapper.insertWkplMgntDtlApalInfo(param);
	}
	
	@Override
	public int insertWkplElcTrn(Map<String, String> param) {
		return mapper.insertWkplElcTrn(param);
	}
	
	@Override
	public int insertWkplMgntDtlRgnInfo(Map<String, String> param) {
		return mapper.insertWkplMgntDtlRgnInfo(param);
	}
	
	@Override
	public int insertWkplMgntDtlCatInfo(Map<String, String> param) {
		return mapper.insertWkplMgntDtlCatInfo(param);
	}
	
	@Override
	public int deleteWkplMgntDtlElcInfo(Map<String, String> param) {
		return mapper.deleteWkplMgntDtlElcInfo(param);
	}
	
	@Override
	public int deleteWkplMgntDtlSbcnInfo(Map<String, String> param) {
		return mapper.deleteWkplMgntDtlSbcnInfo(param);
	}
	
	@Override
	public int deleteWkplMgntDtlApalInfo(Map<String, String> param) {
		return mapper.deleteWkplMgntDtlApalInfo(param);
	}
	
	@Override
	public int deleteWkplElcTrn(Map<String, String> param) {
		return mapper.deleteWkplElcTrn(param);
	}
	
	@Override
	public int deleteWkplMgntDtlRgnInfo(Map<String, String> param) {
		return mapper.deleteWkplMgntDtlRgnInfo(param);
	}
	
	@Override
	public int deleteWkplMgntDtlCatInfo(Map<String, String> param) {
		return mapper.deleteWkplMgntDtlCatInfo(param);
	}
	
	@Override
	public int deleteWkplElcTrn2(Map<String, String> param) {
		return mapper.deleteWkplElcTrn2(param);
	}
	
	@Override
	public int updateCdMlangWkpl(Map<String, String> param) {
		return mapper.updateCdMlangWkpl(param);
	}
	
	@Override
	public int updateWkplElcTrn(Map<String, String> param) {
		return mapper.updateWkplElcTrn(param);
	}
	
	@Override
	public int updateWkplElcTrn2(Map<String, String> param) {
		return mapper.updateWkplElcTrn2(param);
	}
	
	@Override
	public int updateWkplMgntDtlCatInfo(Map<String, String> param) {
		return mapper.updateWkplMgntDtlCatInfo(param);
	}
	
	@Override
	public int updateWkplElcAppr(Map<String, Object> param) {
		return mapper.updateWkplElcAppr(param);
	}
	
}
