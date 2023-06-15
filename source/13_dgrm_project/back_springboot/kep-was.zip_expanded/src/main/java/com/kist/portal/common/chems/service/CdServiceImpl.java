package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.CdMapper;

@Service
public class CdServiceImpl implements CdService {

	@Autowired
	CdMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectCdGrpList(Map<String, String> param) {
		return mapper.selectCdGrpList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectCdList(Map<String, String> param) {
		return mapper.selectCdList(param);
	}
	
	@Override
	public String selectBizCdKey(Map<String, String> param) {
		return mapper.selectBizCdKey(param);
	}
	
	@Override
	public int insertCdGrpList(Map<String, String> param) {
		return mapper.insertCdGrpList(param);
	}
	
	@Override
	public int updateCdGrpList(Map<String, String> param) {
		return mapper.updateCdGrpList(param);
	}
	
	@Override
	public int deleteCdGrpList(Map<String, String> param) {
		return mapper.deleteCdGrpList(param);
	}
	
	@Override
	public int deleteCdList(Map<String, String> param) {
		return mapper.deleteCdList(param);
	}
	
	@Override
	public int insertCdList(Map<String, String> param) {
		return mapper.insertCdList(param);
	}
	
	@Override
	public int updateCdList(Map<String, String> param) {
		return mapper.updateCdList(param);
	}
	
	@Override
	public int deleteCd(Map<String, String> param) {
		return mapper.deleteCd(param);
	}
	
	@Override
	public int insertCdMlangList(Map<String, String> param) {
		return mapper.insertCdMlangList(param);
	}
	
	@Override
	public String selectMlangCdGrpKey(Map<String, String> param) {
		return mapper.selectMlangCdGrpKey(param);
	}
	
	@Override
	public int updateCdMlangList(Map<String, String> param) {
		return mapper.updateCdMlangList(param);
	}
	
	@Override
	public int deleteCdMlangList(Map<String, String> param) {
		return mapper.deleteCdMlangList(param);
	}

	@Override
	public int deleteMlangAllCdList(Map<String, String> param) {
		return mapper.deleteMlangAllCdList(param);
	}
	
	@Override
	public Map<String, ?> selectCdDetail(Map<String, String> param) {
		return mapper.selectCdDetail(param);
	}
	
	@Override
	public Map<String, ?> selectCdMlangNoInfo(Map<String, String> param) {
		return mapper.selectCdMlangNoInfo(param);
	}
}
