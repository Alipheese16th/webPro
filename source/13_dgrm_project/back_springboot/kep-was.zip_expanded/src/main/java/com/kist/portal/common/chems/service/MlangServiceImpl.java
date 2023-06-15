package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.MlangMapper;

@Service
public class MlangServiceImpl implements MlangService {

	@Autowired
	MlangMapper mapper;

	@Override
	public List<Map<String, ?>> selectMlangList(Map<String, String> param) {
		return mapper.selectMlangList(param);
	}

	@Override
	public String selectMlangkey(Map<String, String> param) {
		return mapper.selectMlangkey(param);
	}

	@Override
	public int insertMlangList(Map<String, String> param) {
		return mapper.insertMlangList(param);
	}

	@Override
	public int updateMlangList(Map<String, String> param) {
		return mapper.updateMlangList(param);
	}

	@Override
	public int deleteMlangList(Map<String, String> param) {
		return mapper.deleteMlangList(param);
	}
	
	@Override
	public int updateRoleMlangList(Map<String, String> param) {
		return mapper.updateRoleMlangList(param);
	}
	
	@Override
	public String selectMlangInfo(Map<String, String> param) {
		return mapper.selectMlangInfo(param);
	}
	
	@Override
	public List<Map<String, String>> selectMlangListInfo(Map<String, Object> param) {
		return mapper.selectMlangListInfo(param);
	}
}
