package com.kist.portal.common.comm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.comm.mapper.CommMapper;

@Service
public class CommServiceImpl implements CommService {
	
	@Autowired
	CommMapper mapper;

	@Override
	public List<Map<String, ?>> selectCdList(Map<String, String> param) {
		return mapper.selectCdList(param);
	}

	@Override
	public List<Map<String, ?>> selectCdGrpAllList(Map<String, String> param) {
		return mapper.selectCdGrpAllList(param);
	}

	@Override
	public List<Map<String, ?>> selectCdAllList(Map<String, String> param) {
		return mapper.selectCdAllList(param);
	}
	
	@Override
	public int updateConfig(Map<String, String> param) {
		return mapper.updateConfig(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMultiLanguageList(Map<String, String> param) {
		return mapper.selectMultiLanguageList(param);
	}

	@Override
	public Map<String, String> selectMultiLanguageI18n(Map<String, String> param) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<Map<String, ?>> koList = mapper.selectMultiLanguageList(param);
		
		Map<String, String> langMap = new HashMap<String, String>();
		for (Map<String, ?> map : koList) {
			langMap.put((String) map.get("mlang_no"), (String) map.get("mlang_cntn"));
		}

		return langMap;
	}
	
	@Override
	public List<Map<String, ?>> selectTzCode(Map<String, String> param) {
		return mapper.selectTzCode(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWkplCode(Map<String, String> param) {
		return mapper.selectWkplCode(param);
	}

	@Override
	public int insertPrivacyLog(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.insertPrivacyLog(param);
	}
}
