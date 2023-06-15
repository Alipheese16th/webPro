package com.kist.portal.sh.trnmgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.trnmgnt.mapper.TrnMgntMapper;

@Service
public class TrnMgntServiceImpl implements TrnMgntService {
	
	@Autowired
	TrnMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectTrnList(Map<String, String> param) {
		return mapper.selectTrnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnDtl(Map<String, String> param) {
		return mapper.selectTrnDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnUserList(Map<String, String> param) {
		return mapper.selectTrnUserList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnAuthChk(Map<String, String> param) {
		return mapper.selectTrnAuthChk(param);
	}
	
	@Override
	public String selectTrnUserKey(Map<String, String> param) {
		return mapper.selectTrnUserKey(param);
	}
	
	@Override
	public String selectTrnKey(Map<String, String> param) {
		return mapper.selectTrnKey(param);
	}
	
	@Override
	public int insertTrnDtl(Map<String, String> param) {
		return mapper.insertTrnDtl(param);
	}
	
	@Override
	public int insertTrnUserDtl(Map<String, String> param) {
		return mapper.insertTrnUserDtl(param);
	}
	
	@Override
	public int deleteTrnUserDtl(Map<String, String> param) {
		return mapper.deleteTrnUserDtl(param);
	}
	
	@Override
	public int updateTrnAppr(Map<String, String> param) {
		return mapper.updateTrnAppr(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnAprvInfo(Map<String, String> param) {
		return mapper.selectTrnAprvInfo(param);
	}
	
	@Override
	public int updateTrnApprAf(Map<String, String> param) {
		return mapper.updateTrnApprAf(param);
	}
	
	@Override
	public int updateTrnDtlCnt(Map<String, String> param) {
		return mapper.updateTrnDtlCnt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnMailUserInfo(Map<String, String> param) {
		return mapper.selectTrnMailUserInfo(param);
	}
	
	@Override
	public int updateTrnDtlDel(Map<String, String> param) {
		return mapper.updateTrnDtlDel(param);
	}
}
